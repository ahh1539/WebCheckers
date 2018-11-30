package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import spark.ModelAndView;


/**
 * Test class for Row class
 */
@Tag("Model-tier")
public class RowTest {

    private static final int ROW_LENGTH = 8;
    private static final int EVEN_NUM = 4;
    private static final int ODD_NUM = 5;

    /**
     * Tests the constructor when passed an even index
     */
    @Test
    @DisplayName("constructor for even Row")
    public void evenRow() {
        Row testRow = new Row(EVEN_NUM);
        assertNotNull(testRow);
    }

    /**
     * Tests the constructor when passed an odd index
     */
    @Test
    @DisplayName("constructor for odd Row")
    public void oddRow() {
        Row testRow = new Row(ODD_NUM);
        assertNotNull(testRow);
    }

    /**
     * Tests getRow and correct setup for even Row
     */
    @Test
    @DisplayName("getRow and proper even set-up")
    public void getRowEven() {
        Row testRow = new Row(EVEN_NUM);
        Space[] spaces = testRow.getRow();
        assertEquals(spaces.length, ROW_LENGTH);

        // Assert that the row alternates, white and black
        assertEquals(spaces[EVEN_NUM].getColor(), Space.SpaceColor.BLACK);
        assertEquals(spaces[ODD_NUM].getColor(), Space.SpaceColor.WHITE);
    }

    /**
     * Tests getRow and correct setup for odd Row
     */
    @Test
    @DisplayName("getRow and proper odd set-up")
    public void getRowOdd() {
        Row testRow = new Row(ODD_NUM);
        Space[] spaces = testRow.getRow();
        assertEquals(ROW_LENGTH, spaces.length);

        // Assert that the row alternates, black and white
        assertEquals(Space.SpaceColor.WHITE, spaces[EVEN_NUM].getColor());
        assertEquals(Space.SpaceColor.BLACK, spaces[ODD_NUM].getColor());
    }

    /**
     * Tests the getIndex function
     */
    @Test
    @DisplayName("getIndex")
    public void getIndex() {
        // Arbitrarily uses even number, does not affect functionality here
        Row testRow = new Row(EVEN_NUM);
        assertEquals(EVEN_NUM, testRow.getIndex());
    }

    /**
     * Tests the constructor of RowIterator
     */
    @Test
    @DisplayName("create RowIterator")
    public void createRowIterator() {
        // Arbitrarily uses even number, does not affect functionality here
        Row testRow = new Row(EVEN_NUM);
        Iterator<Space> testRowIterator = testRow.iterator();
        assertNotNull(testRowIterator);
    }

    /**
     * Tests that next method runs as expected
     */
    @Test
    @DisplayName("RowIterator next and hasNext methods")
    public void rowIteratorNext() {
        Row testRow = new Row(EVEN_NUM);
        Iterator<Space> testRowIterator = testRow.iterator();
        Space space;

        for(int i = 0; i < ROW_LENGTH; i++) {
            // next method calls hasNext, so this is already inherently tested
            space = testRowIterator.next();
            assertNotNull(space);
        }

        assertThrows(NoSuchElementException.class, testRowIterator::next);
    }

    /**
     * Tests that remove() throws an exception for a RowIterator
     */
    @Test
    @DisplayName("RowIterator remove throws exception")
    public void removeException() {
        Row testRow = new Row(EVEN_NUM);
        Iterator<Space> testRowIterator = testRow.iterator();
        assertThrows(UnsupportedOperationException.class, testRowIterator::remove);
    }

    /**
     * Tests that equals works with non Row objects
     */
    @Test
    @DisplayName("Row equals works with non-Row")
    public void testEqualsNonRow() {
        Row testRow = new Row(EVEN_NUM);
        Player player = new Player("Friendly");
        assertFalse(testRow.equals(player));
    }


    /**
     * Tests that equals works with non-matching row indexes.
     */
    @Test
    @DisplayName("Row Equals works with non-matching Index")
    public void testEqualsWrongIndex() {
        Row testEvenRow = new Row(EVEN_NUM);
        Row testOddRow = new Row(ODD_NUM);
        assertFalse(testEvenRow.equals(testOddRow));
    }
}

