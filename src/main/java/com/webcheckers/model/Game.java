package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SplittableRandom;

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
    private int redCaptured = 0;
    private int whiteCaptured = 0;
    private String id;

    private final int NUM_ROWS_COLS = 7;

    private List<Move> moves = new ArrayList<Move>();
    private List<Move> tempMoves = new ArrayList<>();

    public enum ViewMode { PLAY, SPECTATOR, REPLAY }



    /**
     * Create a Game with the red and white players, where the active color is
     * red and the winner is null.
     * @param redPlayer
     *      Player {@link Player} representing the red Player
     * @param whitePlayer
     *      Player {@link Player} representing the white Player
     */
    public Game(Player redPlayer, Player whitePlayer, String id){
        Objects.requireNonNull(redPlayer, "redPlayer must not be null");
        Objects.requireNonNull(whitePlayer, "whitePlayer must not be null");

        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = Color.RED;
        this.winner = null;
        this.redBoard = new BoardView(redPlayer);
        this.whiteBoard = new BoardView(whitePlayer);
        this.id = id;
    }

    /**
     * Gets the Red Player from the Game
     * @return
     *      redPlayer, the Player {@link Player} assigned to RED for this game
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    public String getId(){return id; }

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
     * Gets the list of moves made so far in the game
     * @return
     *      The current list of moves
     */
    public List<Move> getMoves() { return this.moves; }

    /**
     * Gets the list of moves made so far in the turn
     * @return
     *      The current list of moves in the turn
     */
    public List<Move> getTempMoves() { return this.tempMoves; }

    public void resetTempMoves(){
        while(!tempMoves.isEmpty()){
            tempMoves.remove(0);
        }
    }


    /**
     * Completes removal of white captured piece and adds to captured count
     * @param target
     *      location of piece being captured
     */
    public void whiteCaptured(Position target) {
        whiteCaptured++;

        // Remove from red board
        Row targetRow = this.redBoard.getRow(target.getRow());
        Space targetSpace = targetRow.getSpace(target.getCell());
        targetSpace.removePiece();

        // Remove from white board
        targetRow = this.whiteBoard.getRow(NUM_ROWS_COLS - target.getRow());
        targetSpace = targetRow.getSpace(NUM_ROWS_COLS - target.getCell());
        targetSpace.removePiece();
    }

    /**
     * Completes removal of red captured piece and adds to captured count
     * @param target
     *      location of piece being captured
     */
    public void redCaptured(Position target) {
        redCaptured++;

        // Remove from white board
        Row targetRow = this.whiteBoard.getRow(target.getRow());
        Space targetSpace = targetRow.getSpace(target.getCell());
        targetSpace.removePiece();

        // Remove from red board
        targetRow = this.redBoard.getRow(NUM_ROWS_COLS - target.getRow());
        targetSpace = targetRow.getSpace(NUM_ROWS_COLS - target.getCell());
        targetSpace.removePiece();
    }

    /**
     * Updates both boards for a red player's turn, adds move to list of moves,
     *  makes pieces into kings as necessary
     * @param m
     *      the Move submitted
     */
    public Message updateBoardRedTurn(Move m) {

        // if first move of turn, add
        if( tempMoves.isEmpty()) {

            // Add move to the ongoing list of moves
            moves.add(m);
            tempMoves.add(m);
        }
        // if previous move is jump check if curr move is jump
        else if( tempMoves.get(0).isJump()) {
            if( m.isJump()){
                // Add move to the ongoing list of moves
                moves.add(m);
                tempMoves.add(m);
            }
        }
        // else the first move was a simple move and you can't keep going
        else{
            return new Message(Message.Type.error, "One simple move per turn....CHEATER");
        }

        // RED BOARD

        // Removes red piece from given start space
        Row startRow = this.redBoard.getRow(m.getStart().getRow());
        Space startSpace = startRow.getSpace(m.getStart().getCell());
        Piece piece = startSpace.removePiece();

        // Adds red piece to given end space
        Row endRow = this.redBoard.getRow(m.getEnd().getRow());
        Space endSpace = endRow.getSpace(m.getEnd().getCell());
        endSpace.putPiece(piece);

        // Checks for new king piece
        if(piece.getType().equals(Piece.Type.SINGLE) && endRow.getIndex() == 0) {
            endSpace.putPiece(piece.makeKingPiece());
        }

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

        // Checks for new king piece
        if(piece.getType().equals(Piece.Type.SINGLE) && endRow.getIndex() == NUM_ROWS_COLS) {
            endSpace.putPiece(piece.makeKingPiece());
        }
        return new Message(Message.Type.info, "Well played");
    }

    /**
     * Updates both boards for a white player's turn, removes move from list of moves,
     *  makes pieces into kings as necessary
     * @param m
     *      the Move submitted
     */
    public Message updateBoardWhiteTurn(Move m) {

        // if first move of turn, add
        if( tempMoves.isEmpty()) {

            // Add move to the ongoing list of moves
            moves.add(m);
            tempMoves.add(m);
        }
        // if previous move is jump check if curr move is jump
        else if( tempMoves.get(0).isJump()) {
            if( m.isJump()){
                // Add move to the ongoing list of moves
                moves.add(m);
                tempMoves.add(m);
            }
        }
        // else the first move was a simple move and you can't keep going
        else{
            return new Message(Message.Type.error, "One simple move per turn....CHEATER");
        }

        // WHITE BOARD

        // Removes white piece from given start space
        Row startRow = this.whiteBoard.getRow(m.getStart().getRow());
        Space startSpace = startRow.getSpace(m.getStart().getCell());
        Piece piece = startSpace.removePiece();

        // Adds white piece to given end space
        Row endRow = this.whiteBoard.getRow(m.getEnd().getRow());
        Space endSpace = endRow.getSpace(m.getEnd().getCell());
        endSpace.putPiece(piece);

        // Checks for new king piece
        if(piece.getType().equals(Piece.Type.SINGLE) && endRow.getIndex() == 0) {
            endSpace.putPiece(piece.makeKingPiece());
        }

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

        // Checks for new king piece
        if(piece.getType().equals(Piece.Type.SINGLE) && endRow.getIndex() == NUM_ROWS_COLS) {
            endSpace.putPiece(piece.makeKingPiece());
        }
        return new Message(Message.Type.info, "Well played.");
    }

    /**
     * Backs up the previously made move by the red player
     */
    public void backupRedTurn() {
        // Removes previously made move from move list
        Move move = tempMoves.remove(tempMoves.size() - 1);


        // RED BOARD

        // Removes red piece from given ending space
        Row finalRow = this.redBoard.getRow(move.getEnd().getRow());
        Space finalSpace = finalRow.getSpace(move.getEnd().getCell());
        Piece piece = finalSpace.removePiece();

        // Adds red piece to original space
        Row originalRow = this.redBoard.getRow(move.getStart().getRow());
        Space originalSpace = originalRow.getSpace(move.getStart().getCell());
        originalSpace.putPiece(piece);

        // WHITE BOARD
        // Overwrites all variables used for red board

        // Removes red piece from given start space

        finalRow = this.whiteBoard.getRow(NUM_ROWS_COLS - move.getEnd().getRow());
        finalSpace = finalRow.getSpace(NUM_ROWS_COLS - move.getEnd().getCell());
        piece = finalSpace.removePiece();

        // Adds red piece to given end space

        originalRow = this.whiteBoard.getRow(NUM_ROWS_COLS - move.getStart().getRow());
        originalSpace = originalRow.getSpace(NUM_ROWS_COLS - move.getStart().getCell());
        originalSpace.putPiece(piece);
    }

    /**
     * Backs up the previously made move by the white player
     */
    public void backupWhiteTurn() {
        // Removes previously made move from move list
        Move move = tempMoves.remove(tempMoves.size() - 1);

        // WHITE BOARD

        // Removes white piece from given ending space
        Row finalRow = this.whiteBoard.getRow(move.getEnd().getRow());
        Space finalSpace = finalRow.getSpace(move.getEnd().getCell());
        Piece piece = finalSpace.removePiece();

        // Adds white piece to original space
        Row originalRow = this.whiteBoard.getRow(move.getStart().getRow());
        Space originalSpace = originalRow.getSpace(move.getStart().getCell());
        originalSpace.putPiece(piece);

        // RED BOARD
        // Overwrites all variables used for white board

        // Removes white piece from given start space

        finalRow = this.redBoard.getRow(NUM_ROWS_COLS - move.getEnd().getRow());
        finalSpace = finalRow.getSpace(NUM_ROWS_COLS - move.getEnd().getCell());
        piece = finalSpace.removePiece();

        // Adds white piece to given end space

        originalRow = this.redBoard.getRow(NUM_ROWS_COLS - move.getStart().getRow());
        originalSpace = originalRow.getSpace(NUM_ROWS_COLS - move.getStart().getCell());
        originalSpace.putPiece(piece);
    }

}
