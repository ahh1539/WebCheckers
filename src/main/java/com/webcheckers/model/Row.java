package com.webcheckers.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Row implements Iterable<Space>{
    private Space[] row;
    private int index;

    private final int ROW_LENGTH = 8;


    public Row(int index){
        this.index = index;
        int idx = ROW_LENGTH * index;
        if( index % 2 == 0){
            for( int i = 0; i < ROW_LENGTH; i++){
                if( i % 2 == 1){
                    row[i] = new Space(null, false, idx);
                    row[i].makeSpaceWhite();
                    idx++;
                }
                else{
                    row[i] = new Space(null, true, idx);
                    idx++;
                }
            }
        }
        else{
            for( int i = 0; i < ROW_LENGTH; i++){
                if( i % 2 == 0){
                    row[i] = new Space(null, false, idx);
                    row[i].makeSpaceWhite();
                }
                else{
                    row[i] = new Space(null, true, idx);
                    idx++;
                }
            }
        }
    }

    public Space[] getRow() {
        return row;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return new RowIterator();
    }

    private class RowIterator implements Iterator<Space> {
        private int cursor;

        public RowIterator() {
            this.cursor = 0;
        }

        public boolean hasNext() {
            return this.cursor < ROW_LENGTH;
        }

        public Space next() {
            if(this.hasNext()) {
                int current = cursor;
                cursor ++;
                return row[current];
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
