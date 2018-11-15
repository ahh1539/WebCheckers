package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.Player;


/**
 * Test class for PlayerLobby
 * @author Alex Hurley
 */


public class PlayerLobbyTest {

    private Player first = new Player("Alex");
    private Player second = new Player("Daria");
    private Player third = new Player("Eli");
    private PlayerLobby lobby = new PlayerLobby();


    @Test
    @DisplayName("Object is a PlayerLobby")
    void testPlayerLobbyConstructor(){
        assertNotNull(lobby);
    }

    @Test
    @DisplayName("Playerlobby adds player successfuly")
    void testPlayerAdded(){
        lobby.addPlayer(first);
        assertTrue(lobby.hasPlayer(first), "Player Alex Successfully added");
    }

    @Test
    @DisplayName("Correct player returned")
    void testGetPlayernull(){
        assertTrue(lobby.getPlayer(third.getName()) == null, "Successfully found player");
    }

    @Test
    @DisplayName("Player removed from player")
    void testPlayerRemoved(){
        lobby.addPlayer(second);
        lobby.removePlayer(second);
        assertFalse(lobby.hasPlayer(second), "Player successfully removed");
    }

    @Test
    @DisplayName("Correct player returned")
    void testGetPlayer(){
        lobby.addPlayer(third);
        assertTrue(lobby.getPlayer("Eli") == third, "Successfully found player");
    }

    @Test
    @DisplayName("Playerlobby size correct")
    void testGetNumberOfPlayers(){
        lobby.addPlayer(first);
        lobby.addPlayer(second);
        assertTrue(lobby.getNumberOfPlayers() == 2, "Returned correct amount of players");
    }


}
