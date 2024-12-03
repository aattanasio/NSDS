package com.NSDSeval24.com.lab.evaluation24;

import akka.actor.ActorRef;

public class ConfigMsg {
        
        private int R;
        private int W;
        private ActorRef next;
        private ActorRef prev;
        
        public ConfigMsg(int R, int W, ActorRef next, ActorRef prev) {
            this.R = R;
            this.W = W;
            this.next = next;
            this.prev = prev;
        }
    
        public int getR() {
            return R;
        }
    
        public int getW() {
            return W;
        }
    
        public ActorRef getNext() {
            return next;
        }
    
        public ActorRef getPrev() {
            return prev;
        }
}
