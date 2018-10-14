package com.webcheckers.model;

public class Piece {
    public enum Color {RED, WHITE}
    public enum Type {SINGLE, KING}

    private Color color;
    private Type type;

    public Piece(Color color, Type type){
        this.color = color;
        this.type = type;
    }

    public Piece crownPiece(){
        this.type = Type.KING;
        return this;
    }
    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Piece){
            Piece piece = (Piece) object;
            return type.equals(piece.type) && color.equals(piece.color);
        }
        return false;
    }
}
