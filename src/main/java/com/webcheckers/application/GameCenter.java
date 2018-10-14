package com.webcheckers.application;


import java.util.Objects;

public class GameCenter {
    private PlayerLobby playerLobby;
    private GameLobby gameLobby;

    public GameCenter(PlayerLobby playerLobby, GameLobby gameLobby){
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(gameLobby, "gameLobby must not be null");
        this.playerLobby = playerLobby;
        this.gameLobby = gameLobby;
    }

    public PlayerLobby getPlayerLobby() {
        return playerLobby;
    }

    public GameLobby getGameLobby() {
        return gameLobby;
    }
}
