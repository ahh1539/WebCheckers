package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Piece} component
 * @author Paula Register (per4521)
 */
@Tag("Model-tier")
public class PieceTest {

    private static final Color redColor = Color.RED;
    private static final Color whiteColor = Color.WHITE;

    private static final int index = 0;

    /**
     * Testing constructor for single red piece
     */
    @Test
    public void testRedPiece(){
        final Piece CuT = new Piece(redColor, index);
        assertEquals(Color.RED, CuT.getColor());
        assertEquals(index, CuT.getIndex());
    }

    /**
     * Testing constructor for single white piece
     */
    @Test
    public void testWhiteSingle(){
        final Piece CuT = new Piece(whiteColor, index);
        assertEquals(Color.WHITE, CuT.getColor());
        assertEquals(index, CuT.getIndex());
    }

    /**
     * Testing the .equals method of Piece to check it outputs True
     */
    @Test
    public void testEqualsTrue(){
        final Piece CuT = new Piece(redColor, index);
        final Piece CuT2 = new Piece(redColor, index);
        assertTrue(CuT.equals(CuT2));
    }

    /**
     * Testing the .equals method of Piece to check it outputs False
     */
    @Test
    public void testEqualsFalse(){
        final Piece CuT = new Piece(redColor, index);
        final Piece CuT2 = new Piece(whiteColor, 1);
        assertFalse(CuT.equals(CuT2));
    }

    /**
     * Testing the toString method of Piece
     */
    @Test
    public void testToString(){
        final Piece CuT = new Piece(redColor, index);
        String toString = "Piece Object:\tColor: " + CuT.getColor() + ", index: " + CuT.getIndex()
                + ", Type: " + CuT.getType() + "\n";
        assertEquals(CuT.toString(), toString);
    }

    /**
     * Testing the makeKing() method
     */
    @Test
    public void testMakeKing(){
        final Piece CuT = new Piece(redColor, index);
        assertEquals(CuT.getType(), Piece.Type.SINGLE);
        final KingPiece CuTKing = CuT.makeKing();
        assertEquals(CuTKing.getType(), Piece.Type.KING);
    }
}
