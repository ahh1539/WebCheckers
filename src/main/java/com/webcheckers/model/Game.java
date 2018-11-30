package com.webcheckers.model;

import javafx.geometry.Pos;

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
    private Player loser;
    private Color activeColor;
    private BoardView redBoard;
    private BoardView whiteBoard;
    private int redCaptured = 0;
    private int whiteCaptured = 0;
    private boolean canDeleteGame = false;

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
     * Checks for overall move ability, helps look for win by stagnation
     * @return
     *      whether this player can make any valid moves
     */
    private boolean hasMove() {
        return (hasSimpleMove() || hasJumpMove());
    }

    /**
     * Check to see if current player has any moves available

     * @return
     */
    public boolean hasSimpleMove(){
        if( whiteCaptured == 12 || redCaptured == 12){
            return false;
        }
        // iterate over rows and spaces
        for(Row r : whiteBoard){
            for(Space s : r){

                // if space has piece, see if there's a valid move in any direction
                if( s.getPiece() != null){
                    if( s.getPiece().getColor() == activeColor){

                        ArrayList<Position> positions = new ArrayList<>();
                        // if piece belongs to player, check if it is movable
                        Position current = new Position(r.getIndex(), s.getCellIdx());
                        Position simpleLF = new Position(current.getRow()+1, current.getCell()-1);
                        if( simpleLF.isOnBoard()) positions.add(simpleLF);

                        Position simpleRF = new Position(current.getRow()+1, current.getCell()+1);
                        if( simpleRF.isOnBoard()) positions.add(simpleRF);

                        Position kingLF = new Position(current.getRow()-1, current.getCell()-1);
                        if( kingLF.isOnBoard()) positions.add(kingLF);

                        Position kingRF = new Position(current.getRow()-1, current.getCell()+1);
                        if( kingRF.isOnBoard()) positions.add(kingRF);

                        for(Position p : positions){
                            Move move = new Move(current, p);
                            if( activeColor == Color.WHITE) {
                                if (move.isValid(whiteBoard)) return true;
                            }
                            else{
                                if( move.isValid(redBoard)) return true;
                            }
                        }
                    }
                }
            }
        }
        // default to false if no valid move is found
        return false;
    }

    public boolean hasJumpMove(){

        if( whiteCaptured == 12 || redCaptured == 12){
            return false;
        }
        // iterate over rows and spaces
        for(Row r : whiteBoard){
            for(Space s : r){

                // if space has piece, see if there's a valid move in any direction
                if( s.getPiece() != null){
                    if( s.getPiece().getColor() == activeColor){

                        ArrayList<Position> positions = new ArrayList<>();
                        // if piece belongs to player, check if it is movable
                        Position current = new Position(r.getIndex(), s.getCellIdx());

                        Position jumpLF = new Position(current.getRow()+2, current.getCell()-2);
                        if( jumpLF.isOnBoard()) positions.add(jumpLF);

                        Position jumpRF = new Position(current.getRow()+2, current.getCell()+2);
                        if( jumpRF.isOnBoard()) positions.add(jumpRF);

                        Position kingJumpLF = new Position(current.getRow()-2, current.getCell()-2);
                        if( kingJumpLF.isOnBoard()) positions.add(kingJumpLF);

                        Position kingJumpRF = new Position(current.getRow()-2, current.getCell()+2);
                        if( kingJumpRF.isOnBoard()) positions.add(kingJumpRF);

                        for(Position p : positions){
                            Move move = new Move(current, p);
                            if( activeColor == Color.WHITE) {
                                if (move.isValid(whiteBoard)) return true;
                            }
                            else{
                                if( move.isValid(redBoard)) return true;
                            }
                        }
                    }
                }
            }
        }
        // default to false if no valid move is found
        return false;
    }

    /**
     *  checks whether player can perform another jump in from their current position
     * @param p position of player currently
     * @return true if another jump can be made
     */
    public boolean hasNextJump(Position p){

        ArrayList<Position> positions = new ArrayList<>();
        // if piece belongs to player, check if it is movable

        Position jumpLF = new Position(p.getRow()+2, p.getCell()-2);
        if( jumpLF.isOnBoard()) positions.add(jumpLF);

        Position jumpRF = new Position(p.getRow()+2, p.getCell()+2);
        if( jumpRF.isOnBoard()) positions.add(jumpRF);

        Position kingJumpLF = new Position(p.getRow()-2, p.getCell()-2);
        if( kingJumpLF.isOnBoard()) positions.add(kingJumpLF);

        Position kingJumpRF = new Position(p.getRow()-2, p.getCell()+2);
        if( kingJumpRF.isOnBoard()) positions.add(kingJumpRF);

        for(Position ps : positions){
            Move move = new Move(p, ps);
            if( activeColor == Color.WHITE) {
                if (move.isValid(whiteBoard)) return true;
            }
            else{
                if( move.isValid(redBoard)) return true;
            }
        }

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
     * Toggles whether a finished game can be deleted
     * Gets called when each player is redirected to home
     */
    public void toggleCanDeleteGame() {
        this.canDeleteGame = !this.canDeleteGame;
    }

    /**
     * Return whether the game is safe to be deleted
     */
    public boolean safeToDelete() { return this.canDeleteGame; }

    /**
     * Sets the winner and loser of the game to either the Red or White player
     * @param player
     *      The winning player
     */
    public void setWinner(Player player){
        if(this.redPlayer.equals(player)) {
            this.winner = this.redPlayer;
            this.loser = this.whitePlayer;
        } else {
            this.winner = this.whitePlayer;
            this.loser = this.redPlayer;
        }

        player.addWin();

        this.redPlayer.leaveGame();
        this.whitePlayer.leaveGame();
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
    public List<Move> getTempMoves() {
        List<Move> lightingMoves = new ArrayList<>(tempMoves);
        return lightingMoves;
    }

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

//        if( !tempMoves.isEmpty()){
//            System.out.println("GAME.JAVA 286: previous RED move: "+ tempMoves.get(0).toString());
//        }
//        System.out.println("GAME.JAVA 286: previous RED move: none");
//        System.out.println("GAME.JAVA 288: current RED move: "+ m.toString());


        // if first move of turn, check if jump is possible & force if so
        if( tempMoves.isEmpty()) {
//
//            if( hasJumpMove() && !m.isJump()){
//                return new Message(Message.Type.error, "You have a jump possible.");
//         }
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
            else {
                return new Message(Message.Type.error, "One simple move per turn....CHEATER");
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

//        if( !tempMoves.isEmpty()){
//            System.out.println("GAME.JAVA 286: previous WHITE move: "+ tempMoves.get(0).toString());
//        }
//        System.out.println("GAME.JAVA 286: previous WHITE move: none");
//        System.out.println("GAME.JAVA 288: current WHITE move: "+ m.toString());

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
            else {
                return new Message(Message.Type.error, "One simple move per turn....CHEATER");
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

    /**
     * Checks if either player has captured all of opponent's pieces or has run out of moves
     */
    public void checkForWin() {
        if(whiteCaptured == 12 || !hasMove()) setWinner(this.redPlayer);
        else if(redCaptured == 12 || !hasMove()) setWinner(this.whitePlayer);
    }

}
