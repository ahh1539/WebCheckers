package com.webcheckers.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Row implements Iterable<Space>{
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

    @Override
    public Iterator<Row> iterator() {
        return new RowIterator();
    }

    private class RowIterator implements Iterator<Row> {
        private int cursor;

        public RowIterator() {
            this.cursor = 0;
        }

        public boolean hasNext() {
            return this.cursor < 8;
        }

        public Integer next() {
            if(this.hasNext()) {
                int current = cursor;
                cursor ++;
                return current;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
