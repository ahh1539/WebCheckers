package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A representation of a single Game, with two players and a Board.
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
    private ArrayList<Piece> pieces;

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
        this.pieces = new ArrayList<>();
    }

    /**
     * Add a piece to the ongoing list of pieces
     * @param piece
     *      Piece {@link Piece} to add to the list of Pieces
     */
    public void addPiece(Piece piece){
        pieces.add(piece);
    }

    /**
     * Remove piece from board once it has been defeated
     * @param piece
     *      Piece {@link Piece} to remove from the list of Pieces
     */
    public void removePiece(Piece piece){
        pieces.remove(piece);
    }

    /**
     * Gets the Red Player from the Game
     * @return
     *      redPlayer, the Player {@link Player} assigned to RED for this game
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Gets the white Player from the Game
     * @return
     *      whitePlayer, the Player {@link Player} assigned to WHITE for this game
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Sets the white player to a new player created from the provided username
     * @param username
     *      A string representation of the new player's username
     */
    public void setWhitePlayer(String username){
        this.whitePlayer = new Player(username);
    }

    /**
     * Sets the red player to a new player created from the provided username
     * @param username
     *      A string representation of the new player's username
     */
    public void setRedPlayer(String username){
        this.redPlayer = new Player(username);
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
     * Will be fully implemented in later sprints.
     * @param player
     * @return
     */
    public boolean hasMove(Player player){
        return false;
    }

    /**
     * Gets the Board for this Game
     * @return
     *      Board {@link BoardView} representation for this Game
     */
    public BoardView getBoard(){
        return this.board;
    }

    /**
     * Gets the active color for this game - which player is legally allowed to move
     * @return
     *      The activeColor, either RED or WHITE
     */
    public Piece.Color getActiveColor(){
        return this.activeColor;
    }

    /**
     * Checks if the provided player is currently active - if their color and the activeColor match
     * @param player
     *      Player to check if they are active or not
     * @return
     *      True if the player is red and activeColor is red, or vice versa for white
     *      False otherwise
     */
    public boolean isActive(Player player){
        boolean isRedPlayer = player.equals(this.redPlayer) && this.activeColor.equals(Piece.Color.RED);
        boolean isWhitePlayer = player.equals(this.whitePlayer) && this.activeColor.equals(Piece.Color.WHITE);
        return isRedPlayer || isWhitePlayer;
    }

    /**
     * Sets the activeColor to the opposite color
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
     * Sets the winner of the game to either the Red or White player
     * @param player
     *      The winning player
     */
    public void setWinner(Player player){
        if(this.redPlayer.equals(player)) this.winner = this.redPlayer;
        else this.winner = this.whitePlayer;
    }

    /**
     * Sets the loser of the game to either the Red or White player
     * @param player
     *      The losing player
     */
    public void setLoser(Player player){
        if(this.redPlayer.equals(player)) this.winner = this.whitePlayer;
        else this.winner = this.redPlayer;
    }

    /**
     * Checks whether a winner has been declared yet
     * @return
     *      True if winner is not null
     *      False otherwise
     */
    public boolean hasWinner(){
        return this.winner != null;
    }

    /**
     * Gets the Game's winner
     * @return
     *      The winner of the Game, either the Red or White player
     */
    public Player getWinner() {
        return this.winner;
    }


}
