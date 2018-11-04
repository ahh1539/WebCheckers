package com.webcheckers.application;

import com.webcheckers.model.Position;

public class Move {
    private Position start;
    private Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart(){
        return start;
    }

    public Position getEnd(){
        return end;
    }
}
