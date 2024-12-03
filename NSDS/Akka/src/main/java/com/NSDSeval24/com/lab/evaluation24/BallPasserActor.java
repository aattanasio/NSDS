package com.NSDSeval24.com.lab.evaluation24;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.Props;

public class BallPasserActor extends AbstractActorWithStash {
    
    // ring
    private ActorRef next;
    private ActorRef prev;
    
    // data
    private int R;
    private int W;
    private int passed;
    private int settedAside;
    
    public BallPasserActor() {
        this.passed = 0;
        this.settedAside = 0;
    }

    @Override
	public Receive createReceive() {
		return playing();
	}

    private final Receive playing() {
		return receiveBuilder()
                .match(ConfigMsg.class, this::configure)
                .match(StartBallMsg.class, this::onStartBallMsg)
                .match(BallMsg.class, this::pass)
                .build();
	}

    private final Receive resting() {
		return receiveBuilder()
               .match(BallMsg.class, this::putAside)
               .match(StartBallMsg.class, this::startWhileResting)
               .build();
	}

    private void startWhileResting(StartBallMsg msg){
        System.out.println("BallPasserActor(" + getSelf().path().name() + "): Received start ball while resting!");
        BallMsg tmp = new BallMsg(self(), msg.getDirection());
        putAside(tmp);
    }

    private void onStartBallMsg(StartBallMsg msg){
        System.out.println("BallPasserActor(" + getSelf().path().name() + "): Received start ball message!");
        if(msg.getDirection() == BallMsg.CLOCKWISE){
            System.out.println("BallPasserActor(" + getSelf().path().name() + "): Ball has been passed to " + next.path().name() + "!");
            next.tell(new BallMsg(self(), BallMsg.CLOCKWISE), getSelf());
        } else if(msg.getDirection() == BallMsg.COUNTERCLOCKWISE) {
            System.out.println("BallPasserActor(" + getSelf().path().name() + "): Ball has been passed to " + prev.path().name() + "!");
            prev.tell(new BallMsg(self(), BallMsg.COUNTERCLOCKWISE), getSelf());
        }
    }

    private void configure(ConfigMsg msg) {
        System.out.println("BallPasserActor(" + getSelf().path().name() + "): Received configuration message!");
        this.R = msg.getR();
        this.W = msg.getW();
        this.next = msg.getNext();
        this.prev = msg.getPrev();
	}

    private void putAside(BallMsg msg){
        settedAside++;
        msg.setValid(false);
        if(settedAside == R){
            System.out.println("BallPasserActor(" + getSelf().path().name() + "): Time to play!");
            settedAside = 0;
            passed = 0;
            stash();
            resumePlay();
        } else {
            System.out.println("BallPasserActor(" + getSelf().path().name() + "): Setting aside ball message...");
            stash();
        }
    }

    private void resumePlay(){
        System.out.println("BallPasserActor(" + getSelf().path().name() + "): Resuming play!");
        getContext().become(playing());
        unstashAll();
    }
    
    private void pass(BallMsg msg) {
        System.out.println("BallPasserActor(" + getSelf().path().name() + "): Received ball message!");
        System.out.println(getSelf().path().name() +"is valid? " + msg.isValid());
        if(msg.getStarter() == self()){
            System.out.println("BallPasserActor(" + getSelf().path().name() + "): Ball has returned to its starter!");
            return;
        }

        if(msg.isValid()){
            passed++;
        }

        System.out.println("BallPasserActor(" + getSelf().path().name() + "): Ball has been passed " + passed + " times!");
        if(passed > W) {
            System.out.println("BallPasserActor(" + getSelf().path().name() + "): Time to rest!");
            msg.setValid(false);
            this.settedAside++;
            stash();
            getContext().become(resting());
        } else {
            if(msg.getDirection() == BallMsg.CLOCKWISE){
                System.out.println("BallPasserActor(" + getSelf().path().name() + "): Ball has been passed to " + next.path().name() + "!");
                next.tell(msg, getSelf());
            } else if(msg.getDirection() == BallMsg.COUNTERCLOCKWISE) {
                System.out.println("BallPasserActor(" + getSelf().path().name() + "): Ball has been passed to " + prev.path().name() + "!");
                prev.tell(msg, getSelf());
            } 
        }
    }
    
    public static Props props() {
        return Props.create(BallPasserActor.class);
    }

}
