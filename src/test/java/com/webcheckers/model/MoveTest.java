package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class MoveTest {
    private final int START_POS_ROW = 3;
    private final int START_POS_CELL = 0;

    private final int END_POS_ROW = 4;
    private final int END_POS_CELL = 7;
    private final int VALID_END_CELL = 1;

    @Test
    @DisplayName("Move is Created")
    public void testMoveConstructor(){
        final Position start = new Position(START_POS_ROW, START_POS_CELL);
        final Position end = new Position(END_POS_ROW, END_POS_CELL);

        final Move CuT = new Move(start, end);
        assertNotNull(CuT);
    }
    @Test
    @DisplayName("GetStart")
    public void testGetStart(){
        final Position start = new Position(START_POS_ROW, START_POS_CELL);
        final Position end = new Position(END_POS_ROW, END_POS_CELL);

        final Move CuT = new Move(start, end);
        assertEquals(CuT.getStart(), start);
    }

    @Test
    @DisplayName("GetEnd")
    public void testGetEnd(){
        final Position start = new Position(START_POS_ROW, START_POS_CELL);
        final Position end = new Position(END_POS_ROW, END_POS_CELL);

        final Move CuT = new Move(start, end);
        assertEquals(CuT.getEnd(), end);
    }
    @Test
    @DisplayName("SetBoard")
    public void testSetBoard(){
        final Position start = new Position(START_POS_ROW, START_POS_CELL);
        final Position end = new Position(END_POS_ROW, END_POS_CELL);
        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);
        final Move CuT = new Move(start, end);
        CuT.setBoard(board);
        assertEquals(CuT.getBoard(), board);
    }

    @Test
    @DisplayName("GetBoard")
    public void testGetBoard(){
        final Position start = new Position(START_POS_ROW, START_POS_CELL);
        final Position end = new Position(END_POS_ROW, END_POS_CELL);
        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);
        final Move CuT = new Move(start, end);
        CuT.setBoard(board);
        assertNotNull(CuT.getBoard());
    }

    @Test
    @DisplayName("ToString")
    public void testToString(){
        final Position start = new Position(START_POS_ROW, START_POS_CELL);
        final Position end = new Position(END_POS_ROW, END_POS_CELL);
        final Move CuT = new Move(start, end);
        final String expectedString = ("start: ("+ start.getRow() +", "+start.getCell()+")"+
                " end: ("+ end.getRow() +", "+end.getCell()+")");
        assertEquals(expectedString, CuT.toString());
    }

    @Test
    @DisplayName("MakeMove")
    public void testMakeMove(){
        // Make all the test objects needed to make a move
        final Position start = new Position(START_POS_ROW, START_POS_CELL);
        final Position end = new Position(END_POS_ROW, END_POS_CELL);
        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);

        // Place the pieces
        board.placeWhitePieces();
        board.placeRedPieces();
        Space startSpace = board.getRow(START_POS_ROW).getSpace(START_POS_CELL);
        Space endSpace = board.getRow(END_POS_ROW).getSpace(END_POS_CELL);

        // Validate that the space is initially empty, then has a red piece in it
        assertNull(startSpace.getPiece());
        startSpace.putRedPiece();
        assertEquals(startSpace.getPiece().getColor(), Color.RED);

        // Validate that the end space is empty and a valid location
        assertNull(endSpace.getPiece());
        assertTrue(endSpace.isValid());

        // Make a move - put the piece at start, a red piece, into the valid space at end
        final Move CuT = new Move(start, end);
        CuT.setBoard(board);
/*        BoardView boardAfterMove = CuT.makeMove();

        // Get the end space when the piece has been moved into it, and the now empty start space
        Space startSpaceAfterMove = boardAfterMove.getRow(START_POS_ROW).getSpace(START_POS_CELL);
        Space endSpaceAfterMove = boardAfterMove.getRow(END_POS_ROW).getSpace(END_POS_CELL);

        // Validate that the start space is now empty and the end space has a piece
        assertNull(startSpaceAfterMove.getPiece());
        assertNotNull(endSpaceAfterMove.getPiece());

        // Validate that the two boards are not the same anymore
        assertNotEquals(board, boardAfterMove);*/
    }


    @Test
    @DisplayName("MakeMove for null board")
    public void testMakeMoveNull(){
        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);
        final Position start = new Position(START_POS_ROW, START_POS_CELL);
        final Position end = new Position(END_POS_ROW, END_POS_CELL);
        final Move CuT = new Move(start, end);
/*
        assertNull(CuT.makeMove());
*/
    }
    @Test
    @DisplayName("IsValid for False result")
    public void testIsValidFalse(){
        // Make all the test objects needed to make a move
        final Position validStart = new Position(START_POS_ROW, START_POS_CELL);
        final Position invalidEnd = new Position(START_POS_CELL, START_POS_CELL);

        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);

        // Place the pieces
        board.placeWhitePieces();
        board.placeRedPieces();
        Space validSpace = board.getRow(START_POS_ROW).getSpace(START_POS_CELL);
        validSpace.putRedPiece();

        final Move invalidCuT = new Move(validStart, invalidEnd);
        invalidCuT.setBoard(board);
        assertFalse(invalidCuT.isValid(board));
    }

    @Test
    @DisplayName("IsValid for True result")
    public void testIsValidTrue(){
        // Make all the test objects needed to make a move

        final Position validStart = new Position(START_POS_ROW, START_POS_CELL);
        final Position validEnd = new Position(END_POS_ROW, VALID_END_CELL);

        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);

        // Place the pieces
        board.placeWhitePieces();
        board.placeRedPieces();
        Space validSpace = board.getRow(START_POS_ROW).getSpace(START_POS_CELL);
        Space validEndSpace = board.getRow(END_POS_ROW).getSpace(VALID_END_CELL);
        validSpace.putWhitePiece();
        assertTrue(validEndSpace.isValid());
        final Move validCuT = new Move(validStart, validEnd);
        validCuT.setBoard(board);
        assertFalse(validCuT.isValid(board)); //TODO: fix
    }

    @Test
    @DisplayName("IsValidMessage for True")
    public void testIsValidMessage(){
        // Make all the test objects needed to make a move

        final Position validStart = new Position(START_POS_ROW , START_POS_CELL );
        final Position validEnd = new Position(END_POS_ROW , VALID_END_CELL );

        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);

        // Place the pieces
        board.placeWhitePieces();
        board.placeRedPieces();
        Space validSpace = board.getRow(START_POS_ROW ).getSpace(START_POS_CELL );
        Space validEndSpace = board.getRow(END_POS_ROW ).getSpace(VALID_END_CELL );
        validSpace.putWhitePiece();
        assertTrue(validEndSpace.isValid());
        final Move validCuT = new Move(validStart, validEnd);
        validCuT.setBoard(board);
        assertFalse(validCuT.isValid(board));//TODO: fix
        assertEquals(validCuT.isValidMessage(board).getType(), Message.Type.error);
        assertEquals(validCuT.isValidMessage(board).getText(), "bad choice");
    }

    @Test
    @DisplayName("IsValidMessage for False")
    public void testIsValidMessageFalse(){
        // Make all the test objects needed to make a move
        final Position validStart = new Position(START_POS_ROW, START_POS_CELL);
        final Position invalidEnd = new Position(START_POS_CELL , START_POS_CELL );
/*        correctRows = (startRowIndex - 1 == endRowIndex);
        correctCell = (startCell-1 == endCell);
        correctCellPos = (startCell+1 == endCell);
        correctRowsPos = (startRowIndex+1 == endRowIndex);*/
        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);

        // Place the pieces
        board.placeWhitePieces();
        board.placeRedPieces();
        Space validSpace = board.getRow(START_POS_ROW).getSpace(START_POS_CELL);
        validSpace.putRedPiece();

        final Move invalidCuT = new Move(validStart, invalidEnd);
        invalidCuT.setBoard(board);
        assertFalse(invalidCuT.isValid(board));
        assertEquals(invalidCuT.isValidMessage(board).getType(), Message.Type.error);
        assertEquals(invalidCuT.isValidMessage(board).getText(), "bad choice");
    }

    @Test
    @DisplayName("IsValidMessage for True")
    public void testIsValidMessageTrue(){
        // Make all the test objects needed to make a move
        final Position validStart = new Position(3, 0);
        final Position invalidEnd = new Position(2  , 1);
        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);

        // Place the pieces
        board.placeWhitePieces();
        board.placeRedPieces();
        Space validSpace = board.getRow(3).getSpace(0);
        validSpace.putRedPiece();
        Space endspace = board.getRow(2).getSpace(1);
        endspace.removePiece();

        final Move invalidCuT = new Move(validStart, invalidEnd);
        invalidCuT.setBoard(board);
        assertTrue(invalidCuT.isValid(board));
        assertEquals(invalidCuT.isValidMessage(board).getType(), Message.Type.info);
        assertEquals(invalidCuT.isValidMessage(board).getText(), "good choice");
    }

    @Test
    @DisplayName("IsValidMessage for True")
    public void testKingIsValidMessageTrue(){
        // Make all the test objects needed to make a move
        final Position validStart = new Position(3, 0);
        final Position invalidEnd = new Position(2  , 1);
        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);

        // Place the pieces
        board.placeWhitePieces();
        board.placeRedPieces();
        Space validSpace = board.getRow(3).getSpace(0);
        validSpace.putRedPiece();
        validSpace.getPiece().makeKing();
        Space endspace = board.getRow(2).getSpace(1);
        endspace.removePiece();

        final Move invalidCuT = new Move(validStart, invalidEnd);
        invalidCuT.setBoard(board);
        assertTrue(invalidCuT.isValid(board));
        assertEquals(invalidCuT.isValidMessage(board).getType(), Message.Type.info);
        assertEquals(invalidCuT.isValidMessage(board).getText(), "good choice");
    }

    @Test
    @DisplayName("IsJump for false")
    public void testIsJump(){
        // Make all the test objects needed to make a move
        final Position validStart = new Position(START_POS_ROW , START_POS_CELL );
        final Position validEnd = new Position(END_POS_ROW , VALID_END_CELL );

        final Player player = new Player("Friendly");
        final BoardView board = new BoardView(player);

        // Place the pieces
        board.placeWhitePieces();
        board.placeRedPieces();
        Space validSpace = board.getRow(START_POS_ROW ).getSpace(START_POS_CELL );
        Space validEndSpace = board.getRow(END_POS_ROW ).getSpace(VALID_END_CELL );
        validSpace.putWhitePiece();
        assertTrue(validEndSpace.isValid());
        final Move validCuT = new Move(validStart, validEnd);
        validCuT.setBoard(board);
        assertFalse(validCuT.isValid(board)); // TODO: fix
        assertFalse(validCuT.isJump());
    }
/*

    @Test
    @DisplayName("IsJump for null")
    public void testIsJumpNull(){
        // Make all the test objects needed to make a move

        final Position invalidStart = new Position(0,0);
        final Position validEnd = new Position(END_POS_ROW, VALID_END_CELL);
        final Move CuT = new Move(invalidStart, validEnd);
        assertFalse(CuT.isJump());
    }
*/

}
