package com.webcheckers.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An array of Iterable Rows comprised of Spaces that make up the board.
 * Creates the board with Red and White Spaces.
 */
public class BoardView implements Iterable<Row>{

    //
    // Attributes
    //
    private Row[] gameBoard;
    private final int BOARD_LENGTH = 8;

    //
    // Constructor
    //

    /**
     * Creates a new Board with 8 Rows then places the Pieces in the correct spots.
     */
    public BoardView(Player player) {
        this.gameBoard = new Row[BOARD_LENGTH];
        for(int i = 0; i < BOARD_LENGTH; i++) {
            this.gameBoard[i] = new Row(i);
        }
        if( player.getColor() == Color.RED) {
            placeRedPieces();
        }
        else{
            System.out.println("placing white pieces");
            placeWhitePieces();
        }
    }

    /**
     * Create a copy of the Board object for use by the Move object
     * @param other The board to make a copy of
     */
    public BoardView(BoardView other) {
        this(other.getRow(0), other.getRow(1), other.getRow(2), other.getRow(3),
                other.getRow(4), other.getRow(5), other.getRow(6), other.getRow(7));
    }

    public BoardView(Row row1,Row row2,Row row3,Row row4,Row row5,Row row6,Row row7,Row row8){
        this.gameBoard = new Row[BOARD_LENGTH];
        this.gameBoard[0] = new Row(row1);
        this.gameBoard[1] = new Row(row2);
        this.gameBoard[2] = new Row(row3);
        this.gameBoard[3] = new Row(row4);
        this.gameBoard[4] = new Row(row5);
        this.gameBoard[5] = new Row(row6);
        this.gameBoard[6] = new Row(row7);
        this.gameBoard[7] = new Row(row8);

    }

    //
    // Public Methods
    //

    public Row getRow(int index){
        return this.gameBoard[index];
    }

    /**
     * Initializes all pieces on the board and places them according to the colors of the Spaces.
     */
    public void placeRedPieces(){
        for(int i = 0; i < BOARD_LENGTH; i++ ){
            if( i < 3){
                for (Space space: gameBoard[i]) {
                    if (space.getColor() == Space.SpaceColor.WHITE) {
                        space.putWhitePiece();
                    }
                }
            }
            if( i > 4){
                for (Space space: gameBoard[i]) {
                    if (space.getColor() == Space.SpaceColor.WHITE) {
                        space.putRedPiece();
                    }
                }
            }
        }
    }

    /**
     * Initializes all pieces on the board and places them according to the colors of the Spaces.
     */
    public void placeWhitePieces(){
        for(int i = 0; i < BOARD_LENGTH; i++ ){
            if( i < 3){
                for (Space space: gameBoard[i]) {
                    if (space.getColor() == Space.SpaceColor.WHITE) {
                        space.putRedPiece();
                    }
                }
            }
            if( i > 4){
                for (Space space: gameBoard[i]) {
                    if (space.getColor() == Space.SpaceColor.WHITE) {
                        space.putWhitePiece();
                    }
                }
            }
        }
    }

    /**
     * Provides a way to Iterate through each Row of the Board
     */
    private class BoardIterator implements Iterator<Row> {
        //
        // Attributes
        //
        int curr = 0;

        /**
         * Checks if the current value is still valid within the confines of the Board
         * @return
         *      True if curr < the length of the board
         *      False otherwise
         */
        @Override
        public boolean hasNext() {
            return (curr < gameBoard.length);
        }

        /**
         * Gets the next Row of the board if one exists. If not, it throws a NoSuchElementException
         * @return
         *      The next Row in the Board
         */
        public Row next(){
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            return gameBoard[curr++];
        }
    }

    //returns a gameboard with all spaces

    /**
     * Creates a new BoardIterator, providing a way to iterate through the game board
     * @return
     *      BoardIterator {@link BoardIterator} a private Iterator class with hasNext and next() methods to
     *      iterate through the Rows of the board.
     */
    @Override
    public Iterator<Row> iterator() {
        return new BoardIterator();
    }

}
