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
    public BoardView(String color) {
        this.gameBoard = new Row[BOARD_LENGTH];
        for(int i = 0; i < BOARD_LENGTH; i++) {
            this.gameBoard[i] = new Row(i);
        }
        if( color.equals("red")) {
            placeRedPieces();
        }
        else{
            placeWhitePieces();
        }
    }

    //
    // Public Methods
    //


    /**
     * Initializes all pieces on the board and places them according to the colors of the Spaces.
     */
    public void placeRedPieces(){
        for(int i = 0; i < BOARD_LENGTH; i++ ){
            if( i < 3){
                for (Space space: gameBoard[i]) {
                    if (space.getColor() == Space.Color.WHITE) {
                        space.putWhitePiece();
                    }
                }
            }
            if( i > 4){
                for (Space space: gameBoard[i]) {
                    if (space.getColor() == Space.Color.WHITE) {
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
                    if (space.getColor() == Space.Color.WHITE) {
                        space.putRedPiece();
                    }
                }
            }
            if( i > 4){
                for (Space space: gameBoard[i]) {
                    if (space.getColor() == Space.Color.WHITE) {
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
