package com.webcheckers.application;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.Player;


/**
 * Test class for GameCenter
 * @author Alex Hurley
 */

public class GameCenterTest {

    private Player first = new Player("Alex");
    private Player second = new Player("Daria");
    private Player third = new Player("Eli");

    private PlayerLobby playerLobby = new PlayerLobby();
    private GameLobby gameLobby = new GameLobby();
    private Game game = new Game(first,second, first.getName()+second.getName());

    private GameCenter gc = new GameCenter(playerLobby,gameLobby);

    @Test
    @DisplayName("instanceOfGameCenter")
    void testInstanceOfGameCenter(){
        playerLobby.addPlayer(first);
        playerLobby.addPlayer(second);
        playerLobby.addPlayer(third);
        assertTrue(gc instanceof GameCenter, "gc is instance of GameCenter");
    }

    @Test
    @DisplayName("getPlayerLobby")
    void testGetPlayerLobby(){
        playerLobby.addPlayer(first);
        playerLobby.addPlayer(second);
        playerLobby.addPlayer(third);
        assertTrue(gc.getPlayerLobby() == playerLobby, "Successfully returned correct PlayerLobby");
    }

    @Test
    @DisplayName("getGameLobby")
    void testGetGameLobby(){
        gameLobby.addGame(game);
        assertTrue(gc.getGameLobby() == gameLobby, "Successfully returned correct GameLobby");
    }
}
