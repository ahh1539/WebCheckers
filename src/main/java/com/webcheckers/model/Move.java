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
        // Check if the piece is single
        if(movingPiece.getType() == Piece.Type.SINGLE){
            // If it is Red
            if(startSpace.getPiece().getColor() == Piece.Color.RED){
                // check if regular move and if destination is valid
                int startRowIndex = this.start.getRow();
                int endRowIndex = this.end.getRow();
                int startCell = this.start.getCell();
                int endCell = this.end.getCell();

                boolean isJumpRow = (startRowIndex - 2) == endRowIndex;
                boolean isJumpCell = (startCell-2 == endCell);
                boolean correctRows = (startRowIndex-1 == endRowIndex);
                boolean correctCell = (startCell+1 == endCell);

                if((correctCell && correctRows) || (startCell-1 == endCell)){
                    if(endSpace.isValid()){
                        valid = true;
                    }
                }
                else if ((isJumpRow && isJumpCell) || (startCell+2 == endCell)){
                    int targetRow = (startRowIndex + endRowIndex) / 2;
                    int targetCell = (startCell + endCell) / 2;
                    Space targetSpace = this.board.getRow(targetRow).getSpace(targetCell);
                    if(endSpace.isValid() && !targetSpace.isValid()){
                        if(targetSpace.getPiece().getColor() != movingPiece.getColor()){
                            valid = true;
                        }
                    }
                }
            }
            // If it is White
        }
        // If the piece is a King
        else {
            // Check if it is regular jump
        }
        return valid;
    }
}
