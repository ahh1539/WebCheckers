package com.webcheckers.model;

import java.io.Serializable;

/**
 * Represents a move as made in the WebCheckers application, completes processing
 * for validation as well as completing the necessary changes to the board if applicable.
 */
public class Move implements Serializable {

    // Attributes for use in isValid; booleans determine in isValidSetup

    private Position start;
    private Position end;
    private BoardView board;

    private int startRowIndex;
    private int endRowIndex;
    private int startCell;
    private int endCell;
    private int targetRow;
    private int targetCell;

    private boolean isJumpRow;
    private boolean isJumpCell;
    private boolean isJumpRowPos;
    private boolean isJumpCellPos;
    private boolean correctRows;
    private boolean correctCell;
    private boolean correctCellPos;
    private boolean correctRowsPos;

    /**
     * Basic constructor to represent Move
     * @param start
     *   beginning position of piece
     * @param end
     *   end position of piece
     */
    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    /**
     * Accessor method for start
     * @return start
     *   beginning position of piece
     */
    public Position getStart(){
        return start;
    }

    /**
     * Accessor method for end
     * @return end
     *   final position of piece
     */
    public Position getEnd(){
        return end;
    }

    // Returns the start piece's space given its row
    private Space getStartSpace(Row startRow) {
        return startRow.getSpace(this.startCell);
    }

    // Returns the end piece's space given its row
    private Space getEndSpace(Row endRow){
        return endRow.getSpace(this.endCell);
    }

    // Sets the board attribute based on given board
    public void setBoard(BoardView board) {
        this.board = board;
    }

    // Returns the board currently stored
    public BoardView getBoard() {
        return new BoardView(this.board);
    }

    /**
     * Transfer the successfully validated move to a copy of the board
     * @return
     *   new BoardView with changes made
     */
    public BoardView makeMove(){
        BoardView copyBoard = new BoardView(this.board);
        Space startSpace = getStartSpace(copyBoard.getRow(this.startRowIndex));
        Space endSpace = getEndSpace(copyBoard.getRow(this.endRowIndex));
        Piece movingPiece = startSpace.getPiece();
        Space targetSpace = copyBoard.getRow(targetRow).getSpace(targetCell);

        // Remove piece at starting position and place piece at ending position
        startSpace.removePiece();
        endSpace.putPiece(movingPiece);

        // Check if move is a jump
        if(this.isJump()) {
            // Capture piece
            targetSpace.removePiece();
        }
        if((endRowIndex == 7 || endRowIndex == 0) && endSpace.getPiece().getType() == Piece.Type.SINGLE) {
            endSpace.putPiece(movingPiece.makeKingPiece());
        }
        return copyBoard;
    }

    /**
     * Helper function to set up values for use in isValid
     */
    private void isValidSetup() {

        // Grabs attributes from this object
        Position start = this.getStart();
        Position end = this.getEnd();

        // Applies various rules to determine info about attempted move
        startRowIndex = this.start.getRow();
        endRowIndex = this.end.getRow();
        startCell =  this.start.getCell();
        endCell = this.end.getCell();
        targetRow = (startRowIndex + endRowIndex) / 2;
        targetCell = (startCell + endCell) / 2;
        isJumpRow = (startRowIndex - 2) == endRowIndex;
        isJumpCell = (startCell - 2 == endCell);
        isJumpRowPos = (startRowIndex + 2) == endRowIndex;
        isJumpCellPos = (startCell + 2 == endCell);
        correctRows = (startRowIndex - 1 == endRowIndex);
        correctCell = (startCell-1 == endCell);
        correctCellPos = (startCell+1 == endCell);
        correctRowsPos = (startRowIndex+1 == endRowIndex);
    }

    /**
     * Checks if the current move is valid. Currently VERY ugly, should be refactored. just wanted to
     * write out the logic and get it in written form for future reference.
     * @return
     */
    public boolean isValid(BoardView board){

        isValidSetup();

        boolean valid = false;

        // Get the starting space and end space
        Space startSpace = getStartSpace(board.getRow(this.startRowIndex));
        System.out.println("start space is " + startSpace);
        Space endSpace = getEndSpace(board.getRow(this.endRowIndex));
        Piece movingPiece = startSpace.getPiece();
        Space targetSpace = board.getRow(targetRow).getSpace(targetCell);

        // Check if the piece is single
        if(movingPiece.getType() == Piece.Type.SINGLE){
            if((correctCellPos || correctCell) && correctRows){
                if(endSpace.isValid()){
                    valid = true;
                }
            }
            else if (isJumpRow && (isJumpCell || isJumpCellPos)){
                if(endSpace.isValid() && !targetSpace.isValid()){
                    if(targetSpace.getPiece().getColor() != movingPiece.getColor()){
                        valid = true;
                    }
                }
            }
        }
        // If the piece is a King
        else {
            // Check if it is regular jump
            if (((correctRows && (correctCellPos || correctCell)) ||
                    (correctRowsPos && (correctCellPos || correctCell)))) {
                if(endSpace.isValid()){
                    valid = true;
                }
            }
            else if((isJumpRow && (isJumpCell || isJumpCellPos)) ||
                    (isJumpRowPos && (isJumpCell || isJumpCellPos))) {
                if(endSpace.isValid() && !targetSpace.isValid()){
                    if(targetSpace.getPiece().getColor() != startSpace.getPiece().getColor()) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    /**
     * Gives a message regarding the result of the move
     * @return
     *   message pertaining to the success of the given move
     */
    public Message isValidMessage(BoardView board){
        if( isValid( board)){
            return new Message(Message.Type.INFO, "good choice");
        }
        return new Message(Message.Type.ERROR, "bad choice");
    }

    /**
     * Checks if the current move is a jump move
     * @return returns True if the move is a jump move
     */
    public boolean isJump() {

        boolean valid = false;
        Space startSpace = getStartSpace(this.board.getRow(this.startRowIndex));
        Space endSpace = getEndSpace(this.board.getRow(this.endRowIndex));
        Piece movingPiece = startSpace.getPiece();
        Space targetSpace = this.board.getRow(targetRow).getSpace(targetCell);

        // If the moving piece is single
        if (movingPiece.getType() == Piece.Type.SINGLE) {
            // Check if move is a jump
            if (isJumpRow && (isJumpCell || isJumpCellPos)) {
                // Check if there is piece to capture
                if (endSpace.isValid() && !targetSpace.isValid()) {
                    if (targetSpace.getPiece().getColor() != startSpace.getPiece().getColor()) {
                        valid = true;
                    }
                }
            }
        }
        // Otherwise, if the moving piece is king
        else {
            if ((isJumpRow || isJumpRowPos)&& (isJumpCell || isJumpCellPos)) {
                // Check if there is piece to capture
                if (endSpace.isValid() && !targetSpace.isValid()) {
                    if (targetSpace.getPiece().getColor() != startSpace.getPiece().getColor()) {
                        valid = true;
                    }
                }
            }
        }

        return valid;

    }

    // toString method for the move attributes
    public String toString(){
        return "start: (" + start.getRow() + ", " + start.getCell() + ")" +
                " end: (" + end.getRow() + ", " + end.getCell() + ")";
    }
}
