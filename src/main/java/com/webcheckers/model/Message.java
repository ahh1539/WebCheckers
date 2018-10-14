package com.webcheckers.model;

public class Message {
    public enum Type {INFO, ERROR}

    private Type type;
    private String text;

    /**
     *
     * @param type
     * @param text
     */
    public Message(Type type, String text){
        this.type = type;
        this.text = text;
    }

    /**
     *
     * @return
     */
    public Type getType(){
        return this.type;
    }

    public String getText(){
        return this.text;
    }

}
