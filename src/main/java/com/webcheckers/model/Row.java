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
     * Create a Row made of black and white Spaces
     * @param index
     *      The index of the Row
     */
    public Row(int index){
        this.index = index;


        if( index % 2 == 0){
            for( int i = 0; i < ROW_LENGTH; i++){
                if( i % 2 == 1){
                    row[i] = new Space(true,  i);
                    row[i].makeSpaceWhite();
                }
                else{
                    row[i] = new Space(false,  i);
                }
            }
        }
        else{
            for( int i = 0; i < ROW_LENGTH; i++){
                if( i % 2 == 0){
                    row[i] = new Space(true, i);
                    row[i].makeSpaceWhite();
                }
                else{
                    row[i] = new Space(false,  i);
                }
            }
        }
    }

    public Row (Row other) {
        this.index = other.index;
        this.row = new Space[ROW_LENGTH];
        for (int i = 0; i < ROW_LENGTH; i++) {
            this.row[i] = new Space(other.getSpace(i));
        }
    }

    /**
     * Check the equality of two objects and see if the provided object
     * is equal to the Row
     * @param object
     *      Object (hopefully a Row) to compare if it is equal to the Row
     * @return
     *      True if the index and individual Spaces match the provided Object index and Spaces
     *      False otherwise
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Row){
            Row row = (Row) object;
            if(index != row.getIndex()){
                return false;
            }
            Space[] rows = row.getRow();
            for (int i = 0; i < ROW_LENGTH; i++) {
                if(!rows[i].equals(this.row[i])){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    /**
     * Return the Space object at the index
     * @param index Location of the Space object to get
     * @return The Space object at index
     */
    public Space getSpace(int index){
        return this.row[index];
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
