package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import com.webcheckers.model.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import spark.ModelAndView;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.Game;



import java.util.ArrayList;

@Tag("Application-tier")
public class GameLobbyTest {

    private Player number1 = new Player("Eli");
    private Player number2 = new Player("Daria");
    private Player number3 = new Player("Paula");
    private Player number4 = new Player("Lilian");

    private Game first = new Game(number1, number2, number1.getName()+number2.getName());
    private Game second = new Game(number3, number4, number3.getName()+number4.getName());
    private GameLobby lobby = new GameLobby();

    @Test
    @DisplayName("Object is a GameLobby")
    public void testGameLobbyConstructor(){
        assertNotNull(lobby);
    }
    @Test
    @DisplayName("AddGame")
    public void testAddGame(){
        final GameLobby CuT = new GameLobby();
        CuT.addGame(first);
        assertTrue(CuT.hasGame(number1));
    }
    @Test
    @DisplayName("HasGame")
    public void testHasGame(){
        final GameLobby CuT = new GameLobby();
        assertFalse(CuT.hasGame(number1));
        CuT.addGame(first);
        assertTrue(CuT.hasGame(number1));
        assertTrue(CuT.hasGame(number2));
    }
    @Test
    @DisplayName("getGameBoard")
    public void testGetGameBoard(){
        final GameLobby CuT = new GameLobby();
        CuT.addGame(first);
        assertNotNull(CuT.getGameBoard(number1));
    }
    @Test
    @DisplayName("getGame")
    public void testGetGame(){
        final GameLobby CuT = new GameLobby();
        assertNull(CuT.getGame(number1));
        CuT.addGame(first);
        assertNotNull(CuT.getGame(number1));
    }

    @Test
    @DisplayName("getGameWhite")
    public void testGetGameWhite(){
        final Player player = new Player("Eli");
        final Player player1 = new Player("Daria");
        player.assignColor(Color.WHITE);
        player1.assignColor(Color.WHITE);
        player.joinGame();
        player1.joinGame();
        Game first = new Game(player, player1, player.getName()+player1.getName());
        final GameLobby CuT = new GameLobby();
        assertNull(CuT.getGame(player));
        CuT.addGame(first);
        assertNotNull(CuT.getGame(player));
        assertNotNull(CuT.getGameBoard(player));
    }

    @Test
    @DisplayName("getGameWhite")
    public void testGetGameBoardNull(){
        final GameLobby CuT = new GameLobby();
        assertNull(CuT.getGameBoard(number1));
    }
    @Test
    @DisplayName("removeGame")
    public void testRemoveGame(){
        final GameLobby CuT = new GameLobby();
        CuT.addGame(first);
        assertTrue(CuT.hasGame(number1));
        CuT.removeGame(number1);
        assertFalse(CuT.hasGame(number1));
    }

    @Test
    @DisplayName("removeGame")
    public void testGetGame2(){
        final GameLobby CuT = new GameLobby();
        CuT.addGame(first);
        assertTrue(CuT.hasGame(number1));
        assertNotNull(CuT.getGame2(number1.getName()+number2.getName()));
        assertEquals(CuT.getGame2(number1.getName()+number2.getName()).getId(), number1.getName()+number2.getName());
    }
}
