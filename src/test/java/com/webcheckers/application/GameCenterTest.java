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

    Player first = new Player("Alex");
    Player second = new Player("Daria");
    Player third = new Player("Eli");

    PlayerLobby playerLobby = new PlayerLobby();
    GameLobby gameLobby = new GameLobby();
    Game game = new Game(first,second);

    GameCenter gc = new GameCenter(playerLobby,gameLobby);

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
