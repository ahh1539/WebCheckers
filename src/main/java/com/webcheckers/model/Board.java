package com.webcheckers.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board implements Iterable<Row>{
    private Row[] gameBoard;
    private final int BOARD_LENGTH = 8;
/*
    private final int LENGTH = 5;
    private final int WIDTH = 5;
    private int gameBoard[][] = new int [LENGTH][WIDTH];
*/


    public Board() {
        this.gameBoard = new Row[BOARD_LENGTH];
        for(int i = 0; i < BOARD_LENGTH; i++) {
            this.gameBoard[i] = new Row(i);
        }
/*        for (int i = 0; i < LENGTH; i++)
            for (int j = 0; j < WIDTH; j++)
            {
                gameBoard[i][j] = 0; //TODO initialized array to dummy values
            }*/
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

/*    public int getOccupied(int length, int width)
    {
        return gameBoard[length][width];
    }*/
}
