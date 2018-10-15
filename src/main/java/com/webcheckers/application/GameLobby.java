package com.webcheckers.application;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;

/**
 * An ArrayList of Games, representing all of the current ongoing games
 */
public class GameLobby {
    //
    // Attributes
    //
    private static ArrayList<Game> gameLobby;

    //
    // Constructors
    //

    /**
     * Creates a new ArrayList for gameLobby
     */
    public GameLobby(){
        gameLobby = new ArrayList<Game>();
    }

    //
    // Public methods
    //

    /**
     * Add a game to the list of ongoing games, gameLobby
     * @param game
     *     The Game {@link Game} to add to gameLobby
     */
    public void addGame(Game game){
        gameLobby.add(game);
    }

    /**
     * Checks whether the given Player is currently playing a game.
     * @param player
     *      Player {@link Player} to check if they are in a game
     * @return
     *      True if the player is in a game, false otherwise.
     */
    public boolean hasGame(Player player){
        for(Game game : gameLobby){
            if(game.hasGame(player)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the game board for the provided Player
     * @param player
     *      Player {@link Player} to get the game board for
     * @return
     *      a BoardView {@link BoardView} of the given player, if one exists
     *      If none exists, it returns null
     */
    public BoardView getGameBoard(Player player){
        for(Game game : gameLobby){
            if(game.hasGame(player)){
                return game.getBoard();
            }
        }
        return null;
    }

    /**
     * Gets the game for a provided player
     * @param player
     *      Player {@link Player} to get the game for
     * @return
     *      Game {@link Game} associated with a player, if one exists
     *      If none exists, it returns null
     */
    public Game getGame(Player player){
        for(Game game : gameLobby){
            if(game.hasGame(player)){
                return game;
            }
        }
        return null;
    }

    /**
     * Removes a game that the provided Player is in from the gameLobby
     * @param player
     *      Player {@link Player} whose game should be removed.
     */
    public void removeGame(Player player){
        gameLobby.remove(getGame(player));
    }

}
