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
    private final Game CuT = new Game(redPlayer, whitePlayer, redPlayer.getName()+whitePlayer.getName());

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
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        CuT_copy.toggleActiveColor();
        assertEquals(CuT_copy.getActiveColor(), Color.WHITE);
    }

    @Test
    @DisplayName("ToggleActiveColor works")
    public void testToggleActiveColor(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        assertEquals(CuT_copy.getActiveColor(), Color.RED);
        CuT_copy.toggleActiveColor();
        assertEquals(CuT_copy.getActiveColor(), Color.WHITE);
    }


    @Test
    @DisplayName("ToggleActiveColor works")
    public void testToggleActiveColorOpposite(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        CuT_copy.toggleActiveColor();
        assertEquals(CuT_copy.getActiveColor(), Color.WHITE);
        CuT_copy.toggleActiveColor();
        assertEquals(CuT_copy.getActiveColor(), Color.RED);
    }

    @Test
    @DisplayName("IsActive works")
    public void testIsActive(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        assertTrue(CuT_copy.isActive(redPlayer));
        CuT_copy.toggleActiveColor();
        assertTrue(CuT_copy.isActive(whitePlayer));
    }

    @Test
    @DisplayName("SetWinner works")
    public void testSetWinner(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        CuT_copy.setWinner(redPlayer);
        assertEquals(CuT_copy.getWinner(), redPlayer);
    }

    @Test
    @DisplayName("SetLoser works")
    public void testSetLoser(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        CuT_copy.setLoser(redPlayer);
        assertEquals(CuT_copy.getWinner(), whitePlayer);
    }

    @Test
    @DisplayName("HasWinner works")
    public void testHasWinner(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        assertFalse(CuT_copy.hasWinner());
        CuT_copy.setWinner(redPlayer);
        assertTrue(CuT_copy.hasWinner());
    }

    @Test
    @DisplayName("HasMove works")
    public void testHasMove(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        assertFalse(CuT_copy.changedTurn());
        CuT_copy.changeTurn();
        assertTrue(CuT_copy.changedTurn());
        CuT_copy.changeTurn();
        assertFalse(CuT_copy.changedTurn());
    }

    @Test
    @DisplayName("getId works")
    public void testGetId(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        assertEquals(CuT_copy.getId(), redPlayer.getName()+whitePlayer.getName());
    }

    @Test
    @DisplayName("resetTempMoves works")
    public void testResetTemptMoves(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        CuT_copy.resetTempMoves();
    }

    @Test
    @DisplayName("hasGame2 works")
    public void testHasGame2(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        assertFalse(CuT_copy.hasGame2("FalseTest"));
        assertTrue(CuT_copy.hasGame2(redPlayer.getName()+whitePlayer.getName()));
    }

    @Test
    @DisplayName("changedTurn works")
    public void testChangedTurn(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        assertFalse(CuT_copy.hasMove(redPlayer));
    }


    @Test
    @DisplayName("AddMove works")
    public void testAddMove(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        final Position startPosition = new Position(0,0);
        final Position endPosition = new Position(0,0);
        final Move move = new Move(startPosition, endPosition);
        assertEquals(CuT_copy.getMoves().size(), 0);
/*        CuT_copy.addMove(move);
        assertEquals(CuT_copy.getMoves().size(), 1)*/;
    }

    @Test
    @DisplayName("AddMove works")
    public void testWhiteCaptured(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        CuT_copy.getRedBoard().placeRedPieces();
        final Position startPosition = new Position(0,0);
        CuT_copy.whiteCaptured(startPosition);
    }

    @Test
    @DisplayName("redCaptured works")
    public void testRedCaptured(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        CuT_copy.getWhiteBoard().placeWhitePieces();
        final Position startPosition = new Position(0,0);
        CuT_copy.redCaptured(startPosition);
    }

    @Test
    @DisplayName("redCaptured works")
    public void testBackupRedTurn(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        CuT_copy.getRedBoard().placeRedPieces();
        CuT_copy.getWhiteBoard().placeWhitePieces();
        final Position startPosition = new Position(1,0);
        final Position endPosition = new Position(0,0);
        final Move move = new Move(startPosition, endPosition);
        assertEquals(CuT_copy.updateBoardRedTurn(move).getType(), Message.Type.info);
        CuT_copy.backupRedTurn();
    }
    @Test
    @DisplayName("redCaptured works")
    public void testBackupWhiteTurn(){
        final Game CuT_copy = new Game(redPlayer, whitePlayer,redPlayer.getName()+whitePlayer.getName());
        CuT_copy.getRedBoard().placeRedPieces();
        CuT_copy.getWhiteBoard().placeWhitePieces();
        final Position startPosition = new Position(1,0);
        final Position endPosition = new Position(0,0);
        final Move move = new Move(startPosition, endPosition);
        assertEquals(CuT_copy.updateBoardWhiteTurn(move).getType(), Message.Type.info);
        CuT_copy.backupWhiteTurn();

        final Position jumpStartPosition = new Position(5,4);
        final Position jumpEndPosition = new Position(7,2);
        final Move jumpMove = new Move(jumpStartPosition, jumpEndPosition);
        jumpMove.setBoard(CuT_copy.getWhiteBoard());
        assertEquals(CuT_copy.updateBoardWhiteTurn(jumpMove).getType(), Message.Type.info);
        CuT_copy.backupWhiteTurn();
    }
}
