package com.webcheckers.model;

import java.io.Serializable;

public class Move implements Serializable {

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

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
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

    public Message isValid(){
        return new Message(Message.Type.INFO, "good choice");
    }

    public Position getStart(){
        return start;
    }

    public Position getEnd(){
        return end;
    }

    public void setBoard(BoardView board) {
        this.board = board;
    }

    public BoardView getBoard() {
        return new BoardView(this.board);
    }

    public String toString(){
        return "start: ("+ start.getRow() +", "+start.getCell()+")"+
                " end: ("+ end.getRow() +", "+end.getCell()+")";
    }

    private Space getStartSpace(Row startRow) {
        return startRow.getSpace(this.startCell);
    }

    private Space getEndSpace(Row endRow){
        return endRow.getSpace(this.endCell);
    }
    /**
     * Make a move
     * @return
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
     * Checks if the current move is valid. Currently VERY ugly, should be refactored. just wanted to
     * write out the logic and get it in written form for future reference.
     * @return
     */
    public boolean isValid(){
        boolean valid = false;
        // Get the starting space and end space
        Space startSpace = getStartSpace(this.board.getRow(this.startRowIndex));
        Space endSpace = getEndSpace(this.board.getRow(this.endRowIndex));
        Piece movingPiece = startSpace.getPiece();
        Space targetSpace = this.board.getRow(targetRow).getSpace(targetCell);

        // Check if the piece is single
        if(movingPiece.getType() == Piece.Type.SINGLE){
            // If it is Red
            if(movingPiece.getColor() == Piece.Color.RED){
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
            // If it is White
            else{
                if(correctRowsPos && (correctCellPos || correctCell)){
                    if(endSpace.isValid()) {
                        valid = true;
                    }
                }
                // check if jump
                else if (isJumpRowPos && (isJumpCell || isJumpCellPos)){
                    if(endSpace.isValid() && !targetSpace.isValid()){
                        if (targetSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                            valid = true;
                        }
                    }
                }
            }
        }
        // If the piece is a King
        else {
            // Check if it is regular jump
            if (((correctRows && (correctCellPos ||
                    correctCell)) || (correctRowsPos &&
                    (correctCellPos || correctCell)))) {
                if(endSpace.isValid()){
                    valid = true;
                }
            }
            else if((isJumpRow && (isJumpCell ||
                    isJumpCellPos)) || (isJumpRowPos &&
                    (isJumpCell || isJumpCellPos))) {
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
            // If the moving piece is RED
            if (movingPiece.getColor() == Piece.Color.RED) {
                // Check if move is a jump
                if (isJumpRow && (isJumpCell || isJumpCellPos)) {
                    // Check if there is piece to capture
                    if (endSpace.isValid() && !targetSpace.isValid()) {
                        if (targetSpace.getPiece().getColor() != startSpace.getPiece().getColor()) {
                            valid = true;
                        }
                    }
                }
                // Otherwise, if the moving piece is WHITE
            } else {
                // Check if move is a jump
                if (isJumpRowPos && (isJumpCell || isJumpCellPos)) {
                    // Check if there is piece to capture
                    if (endSpace.isValid() && !targetSpace.isValid()) {
                        if (targetSpace.getPiece().getColor() != startSpace.getPiece().getColor()) {
                            valid = true;
                        }
                    }
                }
            }
            // Otherwise, if the moving piece is KING
        } else {
            if ((isJumpRow && (isJumpCell || isJumpCellPos)) || (isJumpRowPos && (isJumpCell || isJumpCellPos))){
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

}
