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
     * Checks if the current move is valid.
     * @return
     */
    public boolean isValid(){
        boolean valid = false;
        // Get the starting space and end space
        Row startRow = this.board.getRow(this.start.getRow());
        Space startSpace = startRow.getSpace(this.start.getCell());
        Row endRow = this.board.getRow(this.end.getRow());
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
        boolean isJumpCell = (startCell-2 == endCell);
        boolean isJumpRowPos = (startRowIndex + 2) == endRowIndex;
        boolean isJumpCellPos = (startCell+2 == endCell);
        boolean correctRows = (startRowIndex-1 == endRowIndex);
        boolean correctCell = (startCell+1 == endCell);
        boolean correctRowsOther = (startRowIndex+1 == endRowIndex);
        boolean correctCellOther = (startCell-1 == endCell);

        // Check if the piece is single
        if(movingPiece.getType() == Piece.Type.SINGLE){
            // If it is Red
            if(movingPiece.getColor() == Piece.Color.RED){
                if((correctCell || correctCellOther) && correctRows){
                    if(endSpace.isValid()){
                        valid = true;
                    }
                }
                else if (isJumpRow && (isJumpCell || startCell+2 == endCell)){
                    if(endSpace.isValid() && !targetSpace.isValid()){
                        if(targetSpace.getPiece().getColor() != movingPiece.getColor()){
                            valid = true;
                        }
                    }
                }
            }
            // If it is White
            else{
                if(correctRowsOther && (correctCell || correctCellOther)){
                    if(endSpace.isValid()) {
                        valid = true;
                    }
                }
                // check if jump
                else if (isJumpCellPos && (isJumpCell || isJumpCellPos)){
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
            if (((correctRows && (correctCell ||
                    correctCellOther)) || (correctRowsOther &&
                    (correctCell || correctCellOther)))) {
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
}
