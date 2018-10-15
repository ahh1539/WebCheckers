package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
public class BoardView implements Iterable<Row>{
    private Row[] gameBoard;
    private final int BOARD_LENGTH = 8;

    public BoardView() {
        this.gameBoard = new Row[BOARD_LENGTH];
        for(int i = 0; i < BOARD_LENGTH; i++) {
            this.gameBoard[i] = new Row(i);
        }
        placePieces();
    }

    /**
     * initialize all pieces on the board
     *
     */
    public void placePieces(){
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

    //TODO add functions to place pieces differently for different player

    //iterates through all gameboard spaces
    private class BoardIterator implements Iterator<Row> {
        int curr = 0;
        @Override
        public boolean hasNext() {
            return (curr < BoardView.this.gameBoard.length);
        }
        public Row next(){
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            return gameBoard[curr++];
        }
    }

    //returns a gameboard with all spaces
    @Override
    public Iterator<Row> iterator() {
        return new BoardIterator();
    }

}
