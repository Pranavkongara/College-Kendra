package com.LogicBuild.collegekendra;

public class notice {
    String message;

    public notice(){

    }

    public notice(String message){
        this.message=message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message=message;
    }
}
