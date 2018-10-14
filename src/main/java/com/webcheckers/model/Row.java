package com.webcheckers.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Row implements Iterable<Space>{
    private Space[] row;
    private int index;

    private final int ROW_LENGTH = 8;


    public Row(int index){

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
