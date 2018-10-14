package com.webcheckers.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
public class Board implements Iterable<Row>{
    private Row[] gameBoard;
    private final int BOARD_LENGTH = 8;

    public Board() {
        this.gameBoard = new Row[BOARD_LENGTH];
        for(int i = 0; i < BOARD_LENGTH; i++) {
            this.gameBoard[i] = new Row(i);
        }
    }
    private class BoardIterator implements Iterator<Row> {
        int curr = 0;
        @Override
        public boolean hasNext() {
            return (curr < Board.this.gameBoard.length);
        }
        public Row next(){
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            return gameBoard[curr++];
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return new BoardIterator();
    }

    public int[][] updateBoard() {
        //TODO update the board from the ui tier
        return null;
    }
}
