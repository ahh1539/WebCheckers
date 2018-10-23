package com.webcheckers.model;

/**
 * A Piece on the board, representing a Checkers piece
 */
public class SimplePiece implements Piece{

    //
    // Attributes
    //

    private Color color;
    private int index;



    /**
     * Create a piece with the associated Color and Type and index
     * @param color
     *      The Color {@link Color} of the piece, either RED or WHITE
     * @param idx
     *      The index associated with the placement on the board
     */
    public SimplePiece(Color color, int idx){
        this.color = color;
        this.index = idx;
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
     * Get the index of the piece
     * @return int index
     */
    public int getIndex(){
        return this.index;
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
        if(object instanceof SimplePiece){
            SimplePiece piece = (SimplePiece) object;
            return index == piece.index && color.equals(piece.color);
        }
        return false;
    }
}
