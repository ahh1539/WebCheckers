package com.webcheckers.application;
import java.util.Objects;

/**
 * The object to coordinate the state of the Web Application.
 */
public class GameCenter {

    //
    // Attributes
    //
    private PlayerLobby playerLobby;
    private GameLobby gameLobby;

    /**
     * Creates a GameCenter for coordinating the web app.
     * @param playerLobby
     *      The PlayerLobby {@link PlayerLobby}, a list of all of the current Players
     * @param gameLobby
     *      The GameLobby {@link GameLobby}, a list of all of the current Games.
     */
    public GameCenter(PlayerLobby playerLobby, GameLobby gameLobby){
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(gameLobby, "gameLobby must not be null");
        this.playerLobby = playerLobby;
        this.gameLobby = gameLobby;
    }

    /**
     * Get the PlayerLobby object
     * @return
     *      The list of current Players
     */
    public PlayerLobby getPlayerLobby() {
        return playerLobby;
    }

    /**
     * Get the GameLobby object
     * @return
     *      The list of current Games
     */
    public GameLobby getGameLobby() {
        return gameLobby;
    }
}
