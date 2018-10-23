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

    public enum Color{ BLACK, WHITE}

    /**
     * Create a Space object
     * this.piece defaults to null!
     *
     * @param isValid Boolean representing if the space is valid
     * @param cellIdx Integer representing the cell index value
     */
    public Space(boolean isValid, int cellIdx){
        this.piece = null;
        this.cellIdx = cellIdx;
        this.isValid = isValid;
        this.color = Color.BLACK;
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
        if(this.isValid) {
            this.piece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE, cellIdx);
        }
        this.isValid = false;
    }
    /**
     * Place a red piece on the Space. The Space is no longer a valid spot
     */
    public void putRedPiece() {
        if(this.isValid) {
            this.piece = new Piece(Piece.Color.RED, Piece.Type.SINGLE, cellIdx);
        }
        this.isValid = false;
    }

    /**
     * Make the Space color WHITE
     */
    public void makeSpaceWhite(){
        this.color = Color.WHITE;
    }

    /**
     * get the color of this space
     * @return color
     *      The color {@link Color} of the space, either WHITE or BLACK
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Remove a piece from the Space by setting it to null and making valid again
     */
    public void removePiece(){
        this.piece = null;
        this.isValid = true;
    }

    /**
     * Returns a boolean of whether the space is valid or not
     * @return True if the space is valid and does not currently contain a piece
     */
    public boolean isValid(){
        return this.isValid && (this.piece == null);
    }
}
