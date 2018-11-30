package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class SpaceTest {

    @Test
    @DisplayName("default piece")
    void testPieceDefault(){
        Space space = new Space(true, 0);
        assertNull(space.getPiece());
    }

    @Test
    @DisplayName("makeWhite")
    void testMakeWhite(){
        Space space = new Space(true, 0);
        space.makeSpaceWhite();
        assertEquals(Space.SpaceColor.WHITE, space.getColor());
    }

    @Test
    @DisplayName("default color")
    void testPieceColor(){
        Space space = new Space(true, 0);
        assertEquals(Space.SpaceColor.BLACK, space.getColor());
    }

    @Test
    @DisplayName("dropping RED piece on valid space")
    void testRedPieceDroppedValid(){
        Space space = new Space(true, 0);
        space.putRedPiece();
        assertTrue(space.getPiece().getColor() == Color.RED);
        assertFalse(space.isValid());
    }

    @Test
    @DisplayName("dropping WHITE piece on valid space")
    void testWhitePieceDroppedValid(){
        Space space = new Space(true, 0);
        space.putWhitePiece();
        assertTrue(space.getPiece().getColor() == Color.WHITE);
        assertFalse(space.isValid());
    }

    @Test
    @DisplayName("dropping WHITE piece on valid space")
    void testPieceDroppedInvalid(){
        Space space = new Space(false, 0);
        space.putWhitePiece();
        assertTrue(space.getPiece() == null);
        space.putRedPiece();
        assertTrue(space.getPiece() == null);
    }

    @Test
    @DisplayName("idx")
    void testIndex(){
        Space space = new Space(true, 0);
        assertEquals(0,space.getCellIdx());
    }

    @Test
    @DisplayName("RemovePiece")
    void testRemovePiece(){
        Space space = new Space(true, 0);
        space.putRedPiece();
        assertNotNull(space.getPiece());
        assertFalse(space.isValid());

        space.removePiece();

        assertNull(space.getPiece());
        assertTrue(space.isValid());
    }

    @Test
    @DisplayName("test isValid")
    void testIsValid(){
        Space space = new Space(true, 0);
        assertTrue(space.isValid());

        space.putWhitePiece();
        assertFalse(space.isValid());

        space.removePiece();
        assertTrue(space.isValid());

    }

    /**
     * Tests that equals works with non Space objects
     */
    @Test
    @DisplayName("Space equals with non-Space object")
    public void testEqualsWrongIndex() {
        final Space CuT = new Space(true, 0);
        final Player player = new Player("Friendly");
        assertFalse(CuT.equals(player));
    }

    /**
     * Tests that replaceWithKing works
     */
    @Test
    @DisplayName("Space replace with king")
    public void testReplaceWithKing() {
        final Space CuT = new Space(true, 0);
        CuT.putRedPiece();
        assertEquals(CuT.getPiece().getType(), Piece.Type.SINGLE);
        final KingPiece kingPiece = new KingPiece(Color.RED, 0);
        CuT.replaceWithKing(kingPiece);
        assertEquals(CuT.getPiece().getType(), Piece.Type.KING);
    }
}
