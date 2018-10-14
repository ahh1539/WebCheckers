package com.webcheckers.application;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*

import java.util.HashMap;
import java.util.Map;
// Note from Paula: I was going to implement this as a hashMap but I found some useful resources for arrayLists. If
we decide a hashMap is better, we can always change this implementation.
*/

public class PlayerLobby {
    /*    Map<String, Player> playerLobby = new HashMap<String, Player>();
    */

    // Private variables

    private static ArrayList<Player> playerLobby;

    /**
     * Constructor for PlayerLobby, initializing the lobby to an empty arrayList
     */
    public PlayerLobby(){
        playerLobby = new ArrayList<Player>();
    }

    /**
     * Add a player to the playerLobby. Does not check validity of name, caller is responsible for this.
     * @param p A Player object to add to the list of players
     */
    public void addPlayer(Player p){
        playerLobby.add(p);
    }

    /**
     * Finds and returns a Player if there exists one with the correct username. Returns null otherwise
     * @param username String username to search for
     * @return
     *      Player with matching username if one exists
     *      Null if no Player matching that username exists
     */
    public static Player getPlayer(String username) {
        for(Player p : playerLobby){
            if(p.getUsername().equals(username)){
                return p;
            }
        }
        return null;
    }

    /**
     * Removes a Player from the lobby
     * @param p Player to remove
     */
    public static void removePlayer(Player p){
        playerLobby.remove(p);
    }

    /**
     * Checks if Player p is in the PlayerLobby
     * @param p The Player to check if it is in PlayerLobby
     * @return True if playerLobby has Player, false otherwise
     */
    public boolean hasPlayer(Player p) {
       return playerLobby.contains(p);
    }

    /**
     * For debugging purposes, prints out the contents of the playerLobby
     */
    public void printPlayerLobby(){
        for(Player p : playerLobby){
            System.out.println(p.getUsername());
        }
    }

    public List<Player> getPlayerLobby(){
        return Collections.unmodifiableList(playerLobby);
    }
}
