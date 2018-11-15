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
    private SpaceColor spaceColor;

    public enum SpaceColor{ BLACK, WHITE}

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
        this.spaceColor = SpaceColor.BLACK;
    }

    /**
     * Copy constructor for Space. Creates a new Space object from other
     * @param other The Space object to copy
     */
    public Space(Space other){
        this.piece = other.piece;
        this.cellIdx = other.cellIdx;
        this.isValid = other.isValid;
        this.spaceColor = other.spaceColor;
    }

    public String toString(){
        return " Piece: " + this.piece + ", position: (" + this.cellIdx + ")";
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
            this.piece = new Piece(Color.WHITE, cellIdx);
        }
        this.isValid = false;
    }
    /**
     * Place a red piece on the Space. The Space is no longer a valid spot
     */
    public void putRedPiece() {
        if(this.isValid) {
            this.piece = new Piece(Color.RED, cellIdx);
        }
        this.isValid = false;
    }

    /**
     * Replaces current piece with a king piece of the same color
     * @param kingPiece; the newly created king piece
     */
    public void replaceWithKing(KingPiece kingPiece) {
        this.piece = kingPiece;
    }

    /**
     * Make the Space color WHITE
     */
    public void makeSpaceWhite(){
        this.spaceColor = SpaceColor.WHITE;
    }

    /**
     * get the color of this space
     * @return color
     *      The color {@link Color} of the space, either WHITE or BLACK
     */
    public SpaceColor getColor(){
        return this.spaceColor;
    }

    /**
     * Remove a piece from the Space by setting it to null and making valid again
     */
    public void removePiece(){
        this.piece = null;
        this.isValid = true;
    }

//    public boolean isPieceNull(Piece p){
//        if (p == null){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

    /**
     * Puts Piece object in this space
     * @param piece Piece object to put
     */
    public void putPiece(Piece piece){
        this.piece = piece;
        this.isValid = false;
    }

    /**
     * Returns a boolean of whether the space is valid or not
     * @return True if the space is valid and does not currently contain a piece
     */
    public boolean isValid(){
        return this.isValid && (this.piece == null);
    }
}
