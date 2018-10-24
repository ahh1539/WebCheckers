package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class MessageTester {

    @Test
    @DisplayName("type")
    void testType(){
        Message m = new Message(Message.Type.ERROR, "error");
        assertEquals(Message.Type.ERROR, m.getType());
    }

    @Test
    @DisplayName("message string")
    void testMessageString(){
        Message m = new Message(Message.Type.ERROR, "error");
        assertEquals("error", m.getText());
    }


}
