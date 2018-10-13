package com.webcheckers.application;

import java.util.HashMap;
import java.util.Set;

import webcheckers.model.Player;

public class PlayerLobby {

    private HashMap<String, webcheckers.model.Player> players;


    /**
     *
     * Add a new player to the current players online
     *
     * @param player to be added
     * @return null if successful, current user if username is already taken
     */
    public Player addPlayer(Player player){
        Player current = players.putIfAbsent(player.getUsername(), player);

        return current;
    }



    /**
     * get the usernames of current players
     * @return
     */
    public Set<String> getUsernames(){
        return players.keySet();
    }

    /**
     * get the number of current players
     * @return
     */
    public int getNumPlayers(){
        return players.size();
    }

}
