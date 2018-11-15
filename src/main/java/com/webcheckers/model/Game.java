package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
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
    private Color activeColor;
    private BoardView redBoard;
    private BoardView whiteBoard;

    private final int NUM_ROWS_COLS = 7;

    private List<Move> moves = new ArrayList<Move>();

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
        this.activeColor = Color.RED;
        this.winner = null;
        this.redBoard = new BoardView(redPlayer);
        this.whiteBoard = new BoardView(whitePlayer);
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

    public Color getPlayerColor(String username){
        if(username.equals(redPlayer.getName())){
            return Color.RED;
        }
        else if( username.equals(whitePlayer.getName())){
            return Color.WHITE;
        }
        else{
            return null;
        }
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
     * Gets the Board for this Game if current player is red
     * @return
     *      Board {@link BoardView} representation for this Game
     */
    public BoardView getRedBoard(){

        return this.redBoard;
    }

    /**
     * Gets the Board for this Game if current player is white
     * @return
     *      Board {@link BoardView} representation for this Game
     */
    public BoardView getWhiteBoard(){

        return this.whiteBoard;
    }

    /**
     * Gets the active color for this game - which player is legally allowed to move
     * @return
     *      The activeColor, either RED or WHITE
     */
    public Color getActiveColor(){
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
        boolean isRedPlayer = player.equals(this.redPlayer) && this.activeColor.equals(Color.RED);
        boolean isWhitePlayer = player.equals(this.whitePlayer) && this.activeColor.equals(Color.WHITE);
        return isRedPlayer || isWhitePlayer;
    }

    /**
     * Sets the activeColor to the opposite color
     */
    public void toggleActiveColor(){
        if(this.activeColor == Color.RED){
            this.activeColor = Color.WHITE;
        }
        else{
            this.activeColor = Color.RED;
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
        player.addWin();
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

    /**
     * Updates both boards for a red player's turn, adds move to list of moves
     * @param m
     *      the Move submitted
     */
    public void updateBoardRedTurn(Move m) {
        // Adds move to the ongoing list of moves
        moves.add(m);

        // RED BOARD

        // Removes red piece from given start space
        Row startRow = this.redBoard.getRow(m.getStart().getRow());
        Space startSpace = startRow.getSpace(m.getStart().getCell());
        Piece piece = startSpace.removePiece();

        // Adds red piece to given end space
        Row endRow = this.redBoard.getRow(m.getEnd().getRow());
        Space endSpace = endRow.getSpace(m.getEnd().getCell());
        endSpace.putPiece(piece);

        // WHITE BOARD
        // Overwrites all variables used for red board

        // Removes red piece from given start space

        startRow = this.whiteBoard.getRow(NUM_ROWS_COLS - m.getStart().getRow());
        startSpace = startRow.getSpace(NUM_ROWS_COLS - m.getStart().getCell());
        piece = startSpace.removePiece();

        // Adds red piece to given end space

        endRow = this.whiteBoard.getRow(NUM_ROWS_COLS - m.getEnd().getRow());
        endSpace = endRow.getSpace(NUM_ROWS_COLS - m.getEnd().getCell());
        endSpace.putPiece(piece);

    }

    /**
     * Updates both boards for a white player's turn, removes move from list of moves
     * @param m
     *      the Move submitted
     */
    public void updateBoardWhiteTurn(Move m) {
        // Removes last move
        moves.remove(moves.size() - 1);

        // WHITE BOARD

        // Removes white piece from given start space
        Row startRow = this.whiteBoard.getRow(m.getStart().getRow());
        Space startSpace = startRow.getSpace(m.getStart().getCell());
        Piece piece = startSpace.removePiece();

        // Adds white piece to given end space
        Row endRow = this.whiteBoard.getRow(m.getEnd().getRow());
        Space endSpace = endRow.getSpace(m.getEnd().getCell());
        endSpace.putPiece(piece);

        // RED BOARD
        // Overwrites all variables used for white board

        // Removes white piece from given start space

        startRow = this.redBoard.getRow(NUM_ROWS_COLS - m.getStart().getRow());
        startSpace = startRow.getSpace(NUM_ROWS_COLS - m.getStart().getCell());
        piece = startSpace.removePiece();

        // Adds white piece to given end space

        endRow = this.redBoard.getRow(NUM_ROWS_COLS - m.getEnd().getRow());
        endSpace = endRow.getSpace(NUM_ROWS_COLS - m.getEnd().getCell());
        endSpace.putPiece(piece);

    }

}
