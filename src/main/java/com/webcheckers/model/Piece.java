package com.webcheckers.model;

/**
 * A Piece on the board, representing a Checkers piece
 */
public class Piece {
    //
    // Attributes
    //
    public enum Color {RED, WHITE}
    public enum Type {SINGLE, KING}

    private Color color;
    private Type type;
    private int index;

    /**
     * Create a piece with the associated Color and Type
     * @param color
     *      The Color {@link Color} of the piece, either RED or WHITE
     * @param type
     *      The Type {@link Type} of the piece, either SINGLE or KING
     */
    public Piece(Color color, Type type){
        this.color = color;
        this.type = type;
    }

    /**
     * Create a piece with the associated Color and Type and index
     * @param color
     *      The Color {@link Color} of the piece, either RED or WHITE
     * @param type
     *      The Type {@link Type} of the piece, either SINGLE or KING
     * @param idx
     *      The index associated with the placement on the board
     */
    public Piece(Color color, Type type, int idx){
        this.color = color;
        this.type = type;
        this.index = idx;
    }

    /**
     * Crown a piece and make it a KING type
     * @return
     *      The newly crowned Piece
     */
    public Piece crownPiece(){
        this.type = Type.KING;
        return this;
    }

    /**
     * Gets the color of the Piece
     * @return
     *      the Color {@link Color} of the Piece, RED or WHITE
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the type of the Piece
     * @return
     *      the Type {@link Type} of the Piece, SINGLE or KING
     */
    public Type getType() {
        return type;
    }

    /**
     * Check the equality of two objects and see if the provided object
     * is equal to the Piece
     * @param object
     *      Object (hopefully a Piece) to compare if it is equal to the Piece
     * @return
     *      True if the Piece Type and Color match the provided Object Type and Color
     *      False otherwise
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Piece){
            Piece piece = (Piece) object;
            return type.equals(piece.type) && color.equals(piece.color);
        }
        return false;
    }
}
