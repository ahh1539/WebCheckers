package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.Tag;

/**
 * The unit test suite for the {@link Piece} component
 */
@Tag("Model-tier")
public class PieceTest {

    private static final Piece.Color redColor = Piece.Color.RED;
    private static final Piece.Color whiteColor = Piece.Color.WHITE;

    private static final Piece.Type singleType = Piece.Type.SINGLE;
    private static final Piece.Type kingType = Piece.Type.KING;

    private static final int index = 0;



    /**
     * Test that the constructor works with two arguments
     */
    @Test
    public void ctor_two_args() {
        final Piece CuT = new Piece(redColor, singleType);
        assertEquals(redColor, CuT.getColor());
        assertEquals(singleType, CuT.getType());
    }


    /**
     * Test that the constructor works with three arguments
     */
    @Test
    public void ctor_three_args() {
        final Piece CuT = new Piece(redColor, singleType, index);
        assertEquals(redColor, CuT.getColor());
        assertEquals(singleType, CuT.getType());
        assertEquals(index, CuT.getIndex());
    }

    /**
     * Testing for single red piece
     */
    @Test
    public void test_red_single(){
        final Piece CuT = new Piece(redColor, singleType);
        assertEquals(Piece.Color.RED, CuT.getColor());
        assertEquals(Piece.Type.SINGLE, CuT.getType());
    }

    /**
     * Testing for single white piece
     */
    @Test
    public void test_white_single(){
        final Piece CuT = new Piece(whiteColor, singleType);
        assertEquals(Piece.Color.WHITE, CuT.getColor());
        assertEquals(Piece.Type.SINGLE, CuT.getType());
    }

    /**
     * Testing for king red piece
     */
    @Test
    public void test_red_king(){
        final Piece CuT = new Piece(redColor, kingType);
        assertEquals(Piece.Color.RED,CuT.getColor());
        assertEquals(Piece.Type.KING,CuT.getType());
    }

    /**
     * Testing for king white piece
     */
    @Test
    public void test_white_king(){
        final Piece CuT = new Piece(whiteColor, kingType);
        assertEquals(Piece.Color.WHITE,CuT.getColor());
        assertEquals(Piece.Type.KING,CuT.getType());
    }
    /*
    Testing the .equals methof of Piece to ensure it outputs true
     */
    @Test
    public void test_equals_true(){
        final Piece CuT = new Piece(redColor,singleType);
        final Piece CuT2 = new Piece(redColor,singleType);
        assertEquals(true, CuT.equals(CuT2));
    }

    /*
    Testing the .equals methof of Piece to ensure it outputs false
     */
    @Test
    public void test_equals_false(){
        final Piece CuT = new Piece(redColor,singleType);
        final Piece CuT2 = new Piece(whiteColor, kingType);
        assertEquals(false, CuT.equals(CuT2));
    }
}
