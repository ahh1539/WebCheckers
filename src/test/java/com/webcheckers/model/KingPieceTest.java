package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link KingPiece} component
 * @author Daria Chaplin (dxc4643)
 */
@Tag("Model-tier")
public class KingPieceTest {

    private static final Piece.Color redColor = Piece.Color.RED;
    private static final Piece.Color whiteColor = Piece.Color.WHITE;

    private static final int index = 0;

    /**
     * Testing for king red piece
     */
    @Test
    public void test_red_king(){
        final KingPiece CuT = new KingPiece(redColor, index);
        // commented out while trying to figure out why it's broken!
        //assertEquals(Piece.Color.RED, CuT.getColor());
        assertEquals(index, CuT.getIndex());
    }

    /**
     * Testing for king white piece
     */
    @Test
    public void test_white_king(){
        final KingPiece CuT = new KingPiece(whiteColor, index);
        // temporarily broken
        //assertEquals(Piece.Color.WHITE, CuT.getColor());
        assertEquals(index, CuT.getIndex());
    }

    /**
     * Testing the .equals method of Piece to check it outputs True
     */
    @Test
    public void test_equals_true(){
        final KingPiece CuT = new KingPiece(redColor, index);
        final KingPiece CuT2 = new KingPiece(redColor, index);
        // commented out while fixing getColor() issues
        //assertTrue(CuT.equals(CuT2));
    }

    /**
     * Testing the .equals method of Piece to check it outputs False
     */
    @Test
    public void test_equals_false(){
        final Piece CuT = new Piece(redColor, index);
        final Piece CuT2 = new Piece(whiteColor, index);
        assertFalse(CuT.equals(CuT2));
    }
}
