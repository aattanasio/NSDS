package com.Sleep;

import java.util.Optional;

import com.email.GetMsg;
import com.email.PutMsg;
import com.email.ReplyMsg;

import akka.actor.AbstractActorWithStash;
import akka.actor.Props;

public class ServerActor extends AbstractActorWithStash{

	@Override
	public Receive createReceive() {
		return awake();
	}

    private final Receive awake() {
		return receiveBuilder()
               .match(TextMsg.class, this::echo)
               .match(SleepMsg.class, this::goToSleep)
               .build();
	}

    private final Receive sleepy() {
		return receiveBuilder()
               .match(TextMsg.class, this::putAside)
               .match(WakeUpMsg.class, this::wakeUp)
               .build();
	}

    private void echo(TextMsg msg){
        System.out.println("SERVER: Echo-ing back msg to " + 
                            msg.getSender() +
                            " with text: " +
                            msg.getText());
        msg.getSender().tell(msg, self());
    }

    private void goToSleep(SleepMsg msg){		
        System.out.println("SERVER: Going to sleep... ");
        getContext().become(sleepy());
    }

    private void putAside(TextMsg msg){
        System.out.println("SERVER: Setting aside msg...");
        stash();
    }

    private void wakeUp(WakeUpMsg msg){
        System.out.println("SERVER: Waking up!");
        getContext().become(awake());
        unstashAll();
    }

    void sleep(GetMsg msg) {
		System.out.println("SERVER SLEEP");
        stash();
	}

	static Props props() {
		return Props.create(ServerActor.class);
	}
    
}
