package com.Sleep;

import akka.actor.ActorRef;

public class TextMsg extends Msg{
    private String text;
    private ActorRef sender;

    public TextMsg(String text){
        this.text = text;
        this.sender = null;
    }

    public TextMsg(String text, ActorRef sender){
        this.text = text;
        this.sender = sender;
    }

    public String getText(){
        return this.text;
    }

    public ActorRef getSender(){
        return this.sender;
    }

    public void setSender(ActorRef sender){
        this.sender = sender;
    }
}