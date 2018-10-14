package com.webcheckers.model;

/**
 * Represents a space on the board
 */
public class Space {
    // private attributes
    private Piece piece;
    private boolean isValid;
    private int cellIdx;
    private Color color;
    public enum Color{ RED, WHITE}

    /**
     * Create a Space object
     * @param piece Piece
     * @param isValid Boolean representing if the space is valid
     * @param cellIdx Integer representing the cell index value
     */
    public Space(Piece piece, boolean isValid, int cellIdx){
        this.piece = piece;
        this.cellIdx = cellIdx;
        this.isValid = isValid;
        this.color = Color.RED;
    }

    /**
     * Returns the Piece that is on the Space
     * @return the Piece on the Space
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     *
     * @return
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    public void putWhitePiece() {
        this.piece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
    }

    public void putRedPiece() {
        this.piece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
    }

    public void makeSpaceWhite(){
        this.color = Color.RED;
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
