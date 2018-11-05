package com.webcheckers.model;

import java.io.Serializable;

public class Move implements Serializable {

    private Position start;
    private Position end;
    private BoardView board;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
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

    public BoardView makeMove(){
        BoardView copyBoard = new BoardView(this.board);
        //Piece piece = copyBoard.getRow(this.start.getRow())
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
        Row startRow = this.board.getRow(this.start.getRow());
        Row endRow = this.board.getRow(this.end.getRow());
        Space startSpace = startRow.getSpace(this.start.getCell());
        Space endSpace = endRow.getSpace(this.end.getCell());
        Piece movingPiece = startSpace.getPiece();

        // check if regular move and if destination is valid
        int startRowIndex = this.start.getRow();
        int endRowIndex = this.end.getRow();
        int startCell = this.start.getCell();
        int endCell = this.end.getCell();

        int targetRow = (startRowIndex + endRowIndex) / 2;
        int targetCell = (startCell + endCell) / 2;

        Space targetSpace = this.board.getRow(targetRow).getSpace(targetCell);

        boolean isJumpRow = (startRowIndex - 2) == endRowIndex;
        boolean isJumpCell = (startCell - 2 == endCell);
        boolean isJumpRowPos = (startRowIndex + 2) == endRowIndex;
        boolean isJumpCellPos = (startCell + 2 == endCell);
        boolean correctRows = (startRowIndex - 1 == endRowIndex);
        boolean correctCell = (startCell-1 == endCell);
        boolean correctCellPos = (startCell+1 == endCell);
        boolean correctRowsPos = (startRowIndex+1 == endRowIndex);

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
        Row startRow = this.board.getRow(this.start.getRow());
        Row endRow = this.board.getRow(this.end.getRow());
        Space startSpace = startRow.getSpace(this.start.getCell());
        Space endSpace = endRow.getSpace(this.end.getCell());
        Piece movingPiece = startSpace.getPiece();

        int startRowIndex = this.start.getRow();
        int endRowIndex = this.end.getRow();
        int startCell = this.start.getCell();
        int endCell = this.end.getCell();

        int targetRow = (startRowIndex + endRowIndex) / 2;
        int targetCell = (startCell + endCell) / 2;

        Space targetSpace = this.board.getRow(targetRow).getSpace(targetCell);

        boolean isJumpRow = (startRowIndex - 2) == endRowIndex;
        boolean isJumpCell = (startCell - 2 == endCell);
        boolean isJumpRowPos = (startRowIndex + 2) == endRowIndex;
        boolean isJumpCellPos = (startCell + 2 == endCell);
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
