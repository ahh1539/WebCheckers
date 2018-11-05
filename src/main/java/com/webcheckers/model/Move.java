package com.webcheckers.model;

import java.io.Serializable;

public class Move implements Serializable {

    private Position start;
    private Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Message isValid(){
        return new Message(Message.Type.INFO, "good choice");
    }

    public Position getStart(){
        return start;
    }

    public Position getEnd(){
        return end;
    }

    public String toString(){
        return "start: ("+ start.getRow() +", "+start.getCell()+")"+
                " end: ("+ end.getRow() +", "+end.getCell()+")";
    }
}
