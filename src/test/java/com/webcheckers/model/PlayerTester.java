package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class PlayerTester {


    // holds player object to test
    Player player = new Player("Friendly");

    @Test
    @DisplayName("Object is a Player")
    void testPlayerConstructor(){
        assertTrue(player instanceof Player);
    }

    @Test
    @DisplayName("Uname is Friendly")
    void testUsername(){
        assertEquals("Friendly", player.getName());
    }

    @Test
    @DisplayName("Default player color = RED")
    void testDefaultColor(){
        assertEquals(Player.Color.RED, player.getColor());
    }

    @Test
    @DisplayName("Changing color to WHITE")
    void testChangingToWhite(){
        player.assignColor(Player.Color.WHITE);
        assertEquals(Player.Color.WHITE, player.getColor());
    }

    @Test
    @DisplayName("getName()")
    void testNameGetter(){
        assertEquals("Friendly", player.getName());
    }

    @Test
    @DisplayName("getTotalGames")
    void testTotalGamesGetter(){
        assertEquals(0, player.getTotalGames());
    }

    @Test
    @DisplayName("getWins")
    void testWonGamesGetter(){
        assertEquals(0, player.getWins());
    }

    @Test
    @DisplayName("addGame")
    void testAddGame(){
        player.addGame();
        assertEquals(1, player.getTotalGames());
    }

    @Test
    @DisplayName("addWin")
    void testAddWin(){
        player.addWin();
        assertEquals(1, player.getWins());
    }

    @Test
    @DisplayName("aveWins")
    void testAverageWins(){
        Player dummy = new Player("dummy");
        dummy.addWin();
        dummy.addGame();
        assertEquals(1, dummy.getAverage());
    }

    @Test
    @DisplayName("inGame default false")
    void testDefaultGameStatus(){
        assertFalse(player.inGame());
    }

    @Test
    @DisplayName("joinGame")
    void testJoinGame(){
        player.joinGame();
        assertTrue(player.inGame());
    }

    @Test
    @DisplayName("leaveGame")
    void testLeaveGame(){
        player.leaveGame();
        assertFalse(player.inGame());
    }

    @Test
    @DisplayName("test equals fail")
    void testEqualsFail(){
        Player dummy = new Player("dummy");
        assertFalse(dummy.equals(player));
    }

    @Test
    @DisplayName("test equals pass")
    void testEqualsPass(){
        Player dummy1 = new Player("dummy");
        Player dummy2 = new Player("dummy");
        assertTrue(dummy1.equals(dummy2));
    }

}
