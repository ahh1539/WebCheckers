package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

@Tag("Model-tier")
public class BoardViewTest {

    // holds player object to test
    private Player player = new Player("Friendly");

    // holds player object to test
    private final BoardView CuT = new BoardView(player);
    private final BoardView CuT_copy = new BoardView(CuT);
    private final Iterator CuT_iterator = CuT.iterator();
    private final int BOARD_LENGTH = 8;

    @Test
    @DisplayName("Rows are not null")
    public void testRows(){
        for (int i = 0; i < BOARD_LENGTH; i++) {
            assertTrue(CuT.getRow(i) != null);
        }
    }

    @Test
    @DisplayName("Iterators are not null")
    public void testIterators(){
        assertTrue(CuT_iterator.hasNext());
        for (int i = 0; i < BOARD_LENGTH; i++) {
            assertTrue(CuT_iterator.next() != null);
        }
        assertFalse(CuT_iterator.hasNext());
    }

    @Test
    @DisplayName("CopyBoard equals Board")
    public void testCopyBoard(){
        for (int i = 0; i <BOARD_LENGTH ; i++) {
            assertEquals(CuT.getRow(i), CuT_copy.getRow(i));
        }
    }
    @Test
    @DisplayName("placeRedPieces places Red pieces correctly")
    public void testPlaceRedPieces(){
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if ( i < 3){
                for(Space space : CuT.getRow(i)){
                    if(space.getColor() == Space.SpaceColor.WHITE){
                        assertTrue(space.getPiece().getColor() == Color.WHITE);
                    }
                }
            }
            if ( i > 4 ){
                for(Space space : CuT.getRow(i)){
                    if(space.getColor() == Space.SpaceColor.WHITE){
                        assertTrue(space.getPiece().getColor() == Color.RED);
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("placeWhitePieces places white pieces correctly")
    public void testPlaceWhitePieces(){
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if ( i < 3){
                for(Space space : CuT.getRow(i)){
                    if(space.getColor() == Space.SpaceColor.WHITE){
                        assertTrue(space.getPiece().getColor() == Color.WHITE);
                    }
                }
            }
            if ( i > 4 ){
                for(Space space : CuT.getRow(i)){
                    if(space.getColor() == Space.SpaceColor.WHITE){
                        assertTrue(space.getPiece().getColor() == Color.RED);
                    }
                }
            }
        }
    }
}
