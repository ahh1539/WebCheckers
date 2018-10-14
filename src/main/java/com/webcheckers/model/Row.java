package com.webcheckers.model;

public class Row /*implements Iterable<Space>*/{
    private Space[] row;
    private int index;

    public Row(int index){

    }

    public Space[] getRow() {
        return row;
    }

    public int getIndex() {
        return index;
    }
}
