package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class GameTest {
    // holds player object to test
    Player redPlayer = new Player("FriendlyRed");
    Player whitePlayer = new Player("FriendlyWhite");
    private final Game CuT = new Game(redPlayer, whitePlayer);

    @Test
    public void testGame(){
        assertTrue(CuT.getActiveColor() == Piece.Color.RED);
        assertNull(CuT.getWinner());
        assertEquals(CuT.getRedBoard(), new BoardView(redPlayer));
        assertEquals(CuT.getWhiteBoard(), new BoardView(whitePlayer));
    }

    /**
     * Tests the getRedPlayer method
     */
    @Test
    public void testGetRedPlayer(){
        assertEquals(CuT.getRedPlayer(), redPlayer);
    }

    /**
     * Tests the getWhitePlayer method
     */
    @Test
    public void testGetWhitePlayer(){
        assertEquals(CuT.getWhitePlayer(), whitePlayer);
    }

    @Test
    public void testGetPlayerColor(){
        assertEquals(CuT.getPlayerColor(redPlayer.getName()), redPlayer.getColor());
        assertEquals(CuT.getPlayerColor(whitePlayer.getName()), whitePlayer.getColor());
        assertNull(CuT.getPlayerColor("Garbage"));
    }
    @Test
    public void testSetWhitePlayer(){
        CuT.setWhitePlayer("Test");
        assertEquals(CuT.getWhitePlayer().getName(), "Test");
    }

    @Test
    public void testSetRedPlayer(){
        CuT.setRedPlayer("Test");
        assertEquals(CuT.getRedPlayer().getName(), "Test");
    }

    @Test
    public void testHasGame(){
        boolean hasGameRed = CuT.hasGame(redPlayer);
        boolean hasGameWhite = CuT.hasGame(whitePlayer);

        assertTrue(hasGameRed);
        assertTrue(hasGameWhite);
    }

    @Test
    public void testGetRedBoard(){
        assertEquals(CuT.getRedBoard(), new BoardView(redPlayer));
    }

    @Test
    public void testGetWhiteBoard(){
        assertEquals(CuT.getWhiteBoard(), new BoardView(whitePlayer));
    }

    @Test
    public void testActiveColor(){
        assertEquals(CuT.getActiveColor(), Piece.Color.RED);
    }

    @Test
    public void testActiveColorIsWhite(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        CuT_copy.toggleActiveColor();
        assertEquals(CuT_copy.getActiveColor(), Piece.Color.WHITE);
    }

    @Test
    public void testToggleActiveColor(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        assertEquals(CuT_copy.getActiveColor(), Piece.Color.RED);
        CuT_copy.toggleActiveColor();
        assertEquals(CuT_copy.getActiveColor(), Piece.Color.WHITE);
    }
    @Test
    public void testIsActive(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        assertTrue(CuT_copy.isActive(redPlayer));
        CuT_copy.toggleActiveColor();
        assertTrue(CuT_copy.isActive(whitePlayer));
    }

    @Test
    public void testSetWinner(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        CuT_copy.setWinner(redPlayer);
        assertEquals(CuT_copy.getWinner(), redPlayer);
    }

    @Test
    public void testSetLoser(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        CuT_copy.setLoser(redPlayer);
        assertEquals(CuT_copy.getWinner(), whitePlayer);
    }

    @Test
    public void testHasWinner(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        assertFalse(CuT_copy.hasWinner());
        CuT_copy.setWinner(redPlayer);
        assertTrue(CuT_copy.hasWinner());
    }
}
