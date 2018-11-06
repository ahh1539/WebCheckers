package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import spark.ModelAndView;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;



import java.util.ArrayList;

public class PlayerLobbyTest extends java.lang.Object {

    Player first = new Player("Alex");
    Player second = new Player("Daria");
    Player third = new Player("Eli");
    PlayerLobby lobby = new PlayerLobby();

    public void testPlayerLobby(){
        lobby.addPlayer(first);
        lobby.addPlayer(second);
        lobby.addPlayer(third);

        assertTrue(lobby.hasPlayer(first), "Player added successfully");
        assertTrue(lobby.hasPlayer(third), "Player added successfully");

        lobby.removePlayer(first);
        assertFalse(lobby.hasPlayer(first), "Player removed successfully");

    }




}
