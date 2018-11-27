package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class MessageTest {

    @Test
    @DisplayName("type")
    void testType(){
        Message m = new Message(Message.Type.error, "error");
        assertEquals(Message.Type.error, m.getType());
    }

    @Test
    @DisplayName("message string")
    void testMessageString(){
        Message m = new Message(Message.Type.error, "error");
        assertEquals("error", m.getText());
    }


}
