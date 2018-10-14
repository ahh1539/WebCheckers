package com.webcheckers.model;

public class Board {

    private final int LENGTH = 5;
    private final int WIDTH = 5;
    private int gameBoard[][] = new int [LENGTH][WIDTH];


    public Board() {
        for (int i = 0; i < LENGTH; i++)
            for (int j = 0; j < WIDTH; j++)
            {
                gameBoard[i][j] = 0; //TODO initialized array to dummy values
            }
    }


    public int[][] updateBoard() {
        //TODO update the board from the ui tier
        return null;
    }

    public int getOccupied(int length, int width)
    {
        return gameBoard[length][width];
    }
}
