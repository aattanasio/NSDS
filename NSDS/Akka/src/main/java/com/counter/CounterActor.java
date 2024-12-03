package com.counter;

import akka.actor.AbstractActor;
import akka.actor.AbstractActorWithStash;
import akka.actor.Props;

public class CounterActor extends AbstractActorWithStash {

	private int counter;
	
    public static final int INCREMENT = 1;
    public static final int DECREMENT = 0;

	public CounterActor() {
		this.counter = 0;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(ComplexMessage.class, this::onMessage)
				.match(SimpleMessage.class, this::onMessage)
				.match(OtherMessage.class, this::onMessage)
				.build();
	}

	void onMessage(SimpleMessage msg) {
		++counter;
		System.out.println("Counter increased to " + counter);
		if(counter > 0){
			unstashAll();
		}
	}

	void onMessage(OtherMessage msg) {
		if(counter == 0){
			stash();
		} else if( counter > 0){
			--counter;
			System.out.println("Counter decreased to " + counter);
		}
	}

	void onMessage(ComplexMessage msg){
		if (msg.getOperation() == INCREMENT){
			++counter;
			System.out.println("Counter increased to " + counter);
			if(counter > 0){
				unstashAll();
			}
		} else if(msg.getOperation() == DECREMENT) {
			if(counter == 0){
				stash();
			} else if( counter > 0){
				--counter;
				System.out.println("Counter decreased to " + counter);
			}
		}
	}

	static Props props() {
		return Props.create(CounterActor.class);
	}

}
