package com.counter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class CounterNew {

	private static final int numThreads = 10;
	private static final int numMessages = 100;
	
    public static final int INCREMENT = 1;
    public static final int DECREMENT = 0;

	public static void main(String[] args) {

		final ActorSystem sys = ActorSystem.create("System");
		final ActorRef counter = sys.actorOf(CounterActor.props(), "counter");

		// Send messages from multiple threads in parallel
		final ExecutorService exec = Executors.newFixedThreadPool(numThreads);

		/* EXERCISE ONE
		for (int i = 0; i < numMessages; i++) {
			/* for different classes of messages
			exec.submit(() -> counter.tell(new SimpleMessage(), ActorRef.noSender()));
            exec.submit(() -> counter.tell(new OtherMessage(), ActorRef.noSender()));
			*/

			/* for message selectors that predicate on the content of the message 
			exec.submit(() -> counter.tell(new ComplexMessage(INCREMENT), ActorRef.noSender()));
            exec.submit(() -> counter.tell(new ComplexMessage(DECREMENT), ActorRef.noSender()));
		}
		*/
		
		/* EXERCISE TWO */
		exec.submit(() -> counter.tell(new ComplexMessage(DECREMENT), ActorRef.noSender()));
		exec.submit(() -> counter.tell(new ComplexMessage(DECREMENT), ActorRef.noSender()));
		exec.submit(() -> counter.tell(new ComplexMessage(INCREMENT), ActorRef.noSender()));
		exec.submit(() -> counter.tell(new ComplexMessage(DECREMENT), ActorRef.noSender()));
		exec.submit(() -> counter.tell(new ComplexMessage(INCREMENT), ActorRef.noSender()));
		exec.submit(() -> counter.tell(new ComplexMessage(INCREMENT), ActorRef.noSender()));

		// Wait for all messages to be sent and received
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		exec.shutdown();
		sys.terminate();

	}

}
