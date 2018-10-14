package com.webcheckers.model;

public class Space {
    private Piece piece;
    private boolean isValid;
    private int cellIdx;

    public Space(Piece piece, boolean isValid, int cellIdx){
        this.piece = piece;
        this.cellIdx = cellIdx;
        this.isValid = isValid;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public void putPiece(Piece piece) {
        this.piece = piece;
    }
    public void removePiece(){
        this.piece = null;
    }

    /**
     * Returns a boolean of whether the space is valid or not
     * @return True if the space is valid and does not currently contain a piece
     */
    public boolean isValid(){
        return this.isValid && (this.piece == null);
    }
}
