package com.email;

public class PutMsg {
    private String name;
    private String email;

    public PutMsg(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }
}
