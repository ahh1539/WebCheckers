package com.webcheckers.model;

/**
 *
 * The Player entity to store info about players
 *
 */

public class Player {

    private String username;
    private int totalGames;
    private int wins;

    public Player(String username){
        this.username = username;
        this.totalGames = 0;
        this.wins = 0;
    }

    /**
     * get username
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * get total number of games player has participated in
     * @return
     */
    public int getTotalGames(){
        return totalGames;
    }

    /**
     * get number of games player has won
     * @return
     */
    public int getWins(){
        return wins;
    }

    /**
     * increment number of games by 1
     */
    public void addGame(){
        totalGames++;
    }

    /**
     * increments number of wins by 1
     */
    public void addWin(){
        wins++;
    }

    /**
     * calculates the average number of wins a player has
     * @return avg. wins
     */
    public double getAverage(){
        return wins/totalGames;
    }

    /**
     * compares two players based on username
     * @param obj
     * @return true if players have same username
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(! (obj instanceof Player)){
            return false;
        }
        Player p = (Player)obj;
        return this.username.equals(p.username);
    }

    /**
     * creates unique hashcode based on username
     * @return hashCode
     */
    @Override
    public int hashCode(){
        return username.hashCode();
    }

}
