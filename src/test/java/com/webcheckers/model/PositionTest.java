package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class PositionTest {
    private final int ROW_IDX = 0;
    private final int CELL_IDX = 0;
    final Position CuT = new Position(ROW_IDX, CELL_IDX);

    @Test
    @DisplayName("Position not null")
    public void testPosition(){
        assertNotNull(CuT);
    }

    @Test
    @DisplayName("GetRow works")
    public void testGetRow(){
        assertEquals(CuT.getRow(), ROW_IDX);
    }

    @Test
    @DisplayName("GetCol works")
    public void testGetCell(){
        assertEquals(CuT.getCell(), CELL_IDX);
    }
}
