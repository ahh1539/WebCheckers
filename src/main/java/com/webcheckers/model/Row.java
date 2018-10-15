package com.webcheckers.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An Iterable object representing one of the Rows of the board object.
 */
public class Row implements Iterable<Space>{
    //
    // Attributes
    //
    private final int ROW_LENGTH = 8;
    private Space[] row = new Space[ROW_LENGTH];
    private int index;



    /**
     * Create a Row made of red and white Spaces
     * @param index
     *      The index of the Row
     */
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

    /**
     * Gets the row
     * @return
     *      A list of Space {@link Space} objects representing a row of the board
     */
    public Space[] getRow() {
        return row;
    }

    /**
     * Gets the index of the row
     * @return
     *      The index of the row
     */
    public int getIndex() {
        return index;
    }

    /**
     * Creates a new RowIterator
     * @return
     *      a RowIterator object
     */
    @Override
    public Iterator<Space> iterator() {
        return new RowIterator();
    }

    /**
     * A private class that implements Iterator so that you can iterate through
     * the Spaces comprising it.
     */
    private class RowIterator implements Iterator<Space> {
        //
        // Attributes
        //
        private int cursor;

        /**
         * Initializes the cursor to 0
         */
        public RowIterator() {
            this.cursor = 0;
        }

        /**
         * Checks if there are still Spaces to iterate through in the row
         * @return
         *      True if the cursor is within the ROW_LENGTH, 8
         *      False otherwise
         */
        public boolean hasNext() {
            return this.cursor < ROW_LENGTH;
        }

        /**
         * Gets the next Space in the Row if one exists
         * @return
         *      The next Space {@link Space} in the Row
         */
        public Space next() {
            if(this.hasNext()) {
                int current = cursor;
                cursor ++;
                return row[current];
            }
            throw new NoSuchElementException();
        }

        /**
         * It is not possible to remove a row, throws an exception.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
