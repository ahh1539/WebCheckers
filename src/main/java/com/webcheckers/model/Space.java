package com.webcheckers.model;

/**
 * Represents a space on the board
 */
public class Space {
    //
    // Attributes
    //
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
     * Gets the cellID of the Space
     * @return
     *      The integer representing the ID of the 'cell', the Space
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * Place a white piece on the Space. The Space is no longer a valid spot
     */
    public void putWhitePiece() {
        this.piece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
        this.isValid = false;
    }
    /**
     * Place a red piece on the Space. The Space is no longer a valid spot
     */
    public void putRedPiece() {
        this.piece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
        this.isValid = false;
    }

    /**
     * Make the Space color WHITE
     */
    public void makeSpaceWhite(){
        this.color = Color.WHITE;
    }

    /**
     * Make the Space color RED
     */
    public void makeSpaceRed(){
        this.color = Color.RED;
    }

    /**
     * Remove a piece from the Space by setting it to null
     */
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
