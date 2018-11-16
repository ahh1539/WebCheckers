package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class GameTest {
    // holds player object to test
    private Player redPlayer = new Player("FriendlyRed");
    private Player whitePlayer = new Player("FriendlyWhite");
    private final Game CuT = new Game(redPlayer, whitePlayer);

    @Test
    @DisplayName("Game is Initialized")
    public void testGame(){
        assertTrue(CuT.getActiveColor() == Color.RED);
        assertNull(CuT.getWinner());
    }

    /**
     * Tests the getRedPlayer method
     */
    @Test
    @DisplayName("GetRedPlayer works")
    public void testGetRedPlayer(){
        assertEquals(CuT.getRedPlayer(), redPlayer);
    }

    /**
     * Tests the getWhitePlayer method
     */
    @Test
    @DisplayName("GetWhitePlayer works")
    public void testGetWhitePlayer(){
        assertEquals(CuT.getWhitePlayer(), whitePlayer);
    }

    @Test
    @DisplayName("GetPlayerColor works")
    public void testGetPlayerColor(){
        whitePlayer.assignColor(Color.WHITE);
        assertEquals(CuT.getPlayerColor(redPlayer.getName()), redPlayer.getColor());
        assertEquals(CuT.getPlayerColor(whitePlayer.getName()), whitePlayer.getColor());
        assertNull(CuT.getPlayerColor("Garbage"));
    }
    @Test
    @DisplayName("SetWhitePlayer works")
    public void testSetWhitePlayer(){
        CuT.setWhitePlayer("Test");
        assertEquals(CuT.getWhitePlayer().getName(), "Test");
    }

    @Test
    @DisplayName("SetRedPlayer works")
    public void testSetRedPlayer(){
        CuT.setRedPlayer("Test");
        assertEquals(CuT.getRedPlayer().getName(), "Test");
    }

    @Test
    @DisplayName("hasGame works")
    public void testHasGame(){
        boolean hasGameRed = CuT.hasGame(redPlayer);
        boolean hasGameWhite = CuT.hasGame(whitePlayer);

        assertTrue(hasGameRed);
        assertTrue(hasGameWhite);
    }

    @Test
    @DisplayName("GetRedBoard works")
    public void testGetRedBoard(){
        assertEquals(CuT.getRedBoard(), new BoardView(redPlayer));
    }

    @Test
    @DisplayName("GetWhiteBoard works")
    public void testGetWhiteBoard(){
        assertEquals(CuT.getWhiteBoard(), new BoardView(whitePlayer));
    }

    @Test
    @DisplayName("GetActiveColor works")
    public void testActiveColor(){
        assertEquals(CuT.getActiveColor(), Color.RED);
    }

    @Test
    @DisplayName("GetActiveColor works after color is toggled")
    public void testActiveColorIsWhite(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        CuT_copy.toggleActiveColor();
        assertEquals(CuT_copy.getActiveColor(), Color.WHITE);
    }

    @Test
    @DisplayName("ToggleActiveColor works")
    public void testToggleActiveColor(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        assertEquals(CuT_copy.getActiveColor(), Color.RED);
        CuT_copy.toggleActiveColor();
        assertEquals(CuT_copy.getActiveColor(), Color.WHITE);
    }
    @Test
    @DisplayName("IsActive works")
    public void testIsActive(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        assertTrue(CuT_copy.isActive(redPlayer));
        CuT_copy.toggleActiveColor();
        assertTrue(CuT_copy.isActive(whitePlayer));
    }

    @Test
    @DisplayName("SetWinner works")
    public void testSetWinner(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        CuT_copy.setWinner(redPlayer);
        assertEquals(CuT_copy.getWinner(), redPlayer);
    }

    @Test
    @DisplayName("SetLoser works")
    public void testSetLoser(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        CuT_copy.setLoser(redPlayer);
        assertEquals(CuT_copy.getWinner(), whitePlayer);
    }

    @Test
    @DisplayName("HasWinner works")
    public void testHasWinner(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer);
        assertFalse(CuT_copy.hasWinner());
        CuT_copy.setWinner(redPlayer);
        assertTrue(CuT_copy.hasWinner());
    }
}
