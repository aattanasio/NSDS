package com.NSDSeval24.com.lab.evaluation24;

import akka.actor.ActorRef;

public class BallMsg {
	
	private ActorRef starter;
	private int direction;
	private boolean isValid;

	public static final int COUNTERCLOCKWISE = 0;
	public static final int CLOCKWISE = 1;

	public BallMsg(ActorRef starter, int direction) {
		this.starter = starter;
		this.direction = direction;
		this.isValid = true;
	}

	public ActorRef getStarter() {
		return starter;
	}

	public int getDirection() {
		return direction;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
		
}
