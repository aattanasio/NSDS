package com.Sleep;

import akka.actor.ActorRef;

public class ConfigMsg {
    private ActorRef server;

    public ConfigMsg(ActorRef server){
        this.server = server;
    }

    public ActorRef getServer(){
        return this.server;
    }

}
