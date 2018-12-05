package com.webcheckers.model;

/**
 *
 * The Player entity to store info about players
 *
 */
public class Player {
    //
    // Attributes
    //
    //public enum Color {RED, WHITE}

    private String name;
    private double totalGames;
    private double wins;
    private boolean inGame;
    private Color color;
    private boolean hasresigned = false;
    private String gameID = "empty";

    /**
     * Create a Player with the provided username. 0 total games and wins, they are not
     * currently in a game, and color defaults to RED
     * @param name
     *      Unique alphanumeric string representing the Player
     */
    public Player(String name){
        this.name = name;
        this.totalGames = 0;
        this.wins = 0;
        this.inGame = false;
        this.color = Color.RED;
    }
    public String getGameID(){
        return gameID;
    }
    public void setGameID(String id){
        gameID = id;
    }

    /**
     * Get the Player's username
     * @return
     *      String username of the Player
     */
    public String getName(){
        return name;
    }

    /**
     * Get the Player's color
     * @return
     *      The color of the Player, either Color.RED or .WHITE
     */
    public Color getColor() {
        return color;
    }

    public String toString(){
        return name + " is "+ color;
    }

    /**
     * Update player's color
     * @param color
     *      The Color {@link Color} assigned to the Player, RED or WHITE
     */
    public void assignColor(Color color){
        this.color = color;
    }


    /**
     * Get total number of games player has participated in
     * @return totalGames
     *      The number of games the Player has participated in
     */
    public double getTotalGames(){
        return totalGames;
    }

    /**
     * get number of games player has won
     * @return wins
     */
    public double getWins(){
        return wins;
    }

    /**
     * Joins the player by indicating they are in a game
     */
    public void joinGame() {
        inGame = true;
    }


    /**
     * Player leaves game by indicating they are no longer in a game
     */
    public void leaveGame() { inGame = false; }

    /**
     * Determine whether the player is currently in a game
     * @return inGame
     *      Boolean representing whether the Player is in a game
     */
    public boolean inGame() { return inGame; }


    /**
     * Increment number of games by 1
     */
    public void addGame(){
        totalGames++;
    }

    /**
     * Increments number of wins by 1
     */
    public void addWin(){
        wins++;
    }

    /**
     * Calculates the average number of wins a player has
     * @return
     *      A double representing the average number of wins
     */
    public double getAverage(){
        return wins/totalGames;
    }

    /**
     * Void; when called it changes the status of the player to resigned
     */
    public void hasResigned(){
        this.hasresigned = !this.hasresigned;
    }

    /**
     *
     * @return boolean representing whether or not player has resigned
     */
    public boolean resigned(){
        return hasresigned;
    }

    /**
     * Compares two players based on username
     * @param obj given player
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
        return this.name.equals(p.name);
    }

    /**
     * creates unique hashcode based on username
     * @return hashCode
     */
    @Override
    public int hashCode(){
        return name.hashCode();
    }

}
