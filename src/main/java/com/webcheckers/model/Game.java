package com.webcheckers.model;

import java.util.Objects;

/**
 * A single Game, containing the information about the red and white players,
 * the winner, active color, and the board.
 */
public class Game {

    //
    // Attributes
    //

    private Player redPlayer;
    private Player whitePlayer;
    private Player winner;
    private Piece.Color activeColor;
    private BoardView board;

    public enum ViewMode { PLAY, SPECTATOR, REPLAY }

    /**
     * Create a Game with the red and white players, where the active color is
     * red and the winner is null.
     * @param redPlayer
     *      Player {@link Player} representing the red Player
     * @param whitePlayer
     *      Player {@link Player} representing the white Player
     */
    public Game(Player redPlayer, Player whitePlayer){
        Objects.requireNonNull(redPlayer, "redPlayer must not be null");
        Objects.requireNonNull(whitePlayer, "whitePlayer must not be null");

        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = Piece.Color.RED;
        this.winner = null;
        this.board = new BoardView();
    }

    /**
     * Gets the red player
     * @return
     *      Player {@link Player} representing the red Player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Gets the white player
     * @return
     *      Player {@link Player} representing the white Player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Queries whether the provided player has a game - If they are either
     * the red or the white player
     * @param player
     *      Player {@link Player} to check if they are in a game
     * @return
     *      True if the provided player is the red or white player.
     *      False otherwise
     */
    public boolean hasGame(Player player){
        return this.redPlayer.equals(player) | this.whitePlayer.equals(player);
    }

    /**
     * Checks if the player has a valid move. Will be implemented fully in later sprints.
     * @param player
     * @return
     */
    public boolean hasMove(Player player){
        return false;
    }

    /**
     * Gets the board for the game
     * @return
     *      The Board {@link BoardView} for the current Game
     */
    public BoardView getBoard(){
        return this.board;
    }

    /**
     * Gets the current active color
     * @return
     *      The activeColor, {@link Piece.Color} either Red or White
     */
    public Piece.Color getActiveColor(){
        return this.activeColor;
    }

    /**
     * Checks if the given player is active in the Game.
     * @param player
     *      Player {@link Player} to check if they are active
     * @return
     *      True if the provided player is either the red or white player.
     *      False otherwise.
     */
    public boolean isActive(Player player){
        boolean isRedPlayer = player.equals(this.redPlayer) && this.activeColor.equals(Piece.Color.RED);
        boolean isWhitePlayer = player.equals(this.whitePlayer) && this.activeColor.equals(Piece.Color.WHITE);
        return isRedPlayer || isWhitePlayer;
    }

    /**
     * Toggles the active color to the opposite color
     */
    public void toggleActiveColor(){
        if(this.activeColor == Piece.Color.RED){
            this.activeColor = Piece.Color.WHITE;
        }
        else{
            this.activeColor = Piece.Color.RED;
        }
    }

    /**
     * The player has won, so set this.winner equal to whichever player they are
     * @param player
     *      The Player who has won
     */
    public void setWinner(Player player){
        if(this.redPlayer.equals(player)) this.winner = this.redPlayer;
        else this.winner = this.whitePlayer;
    }

    /**
     * The player has lost, so set this.winner equal to whichever player they are not
     * @param player
     *      The Player who has lost
     */
    public void setLoser(Player player){
        if(this.redPlayer.equals(player)) this.winner = this.whitePlayer;
        else this.winner = this.redPlayer;
    }

    /**
     * Queries whether the current game has a winner
     * @return
     *      True if the winner variable is not null.
     *      False otherwise
     */
    public boolean hasWinner(){
        return this.winner != null;
    }

    /**
     * Returns the current game's winner
     * @return
     *      The player who is the winner, either the Red or White player.
     */
    public Player getWinner() {
        return this.winner;
    }
}
