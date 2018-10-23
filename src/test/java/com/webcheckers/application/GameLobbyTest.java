package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

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

    Player number1 = new Player("Eli");
    Player number2 = new Player("Daria");
    Player number3 = new Player("Paula");
    Player number4 = new Player("Lilian");

    Game first = new Game(number1, number2);
    Game second = new Game(number3, number4);
    GameLobby lobby = new GameLobby();

    @Test
    public void testPlayerLobby(){
        lobby.addGame(first);
        lobby.addGame(second);

        assertTrue(lobby instanceof GameLobby, "is an instance of player lobby");

        assertTrue(lobby.hasGame(number1), "Player game created successfully");
        assertTrue(lobby.hasGame(number2), "Player game created successfully");

        assertTrue(lobby.getGame(number3) == second, "Successfully found player");

        lobby.removeGame(number1);
        assertFalse(lobby.hasGame(number1), "Player removed successfully");

        assertNotNull(lobby.getGameBoard(number2));

    }
}
