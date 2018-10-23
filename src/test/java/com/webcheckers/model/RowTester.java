package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import spark.ModelAndView;


/**
 * Test class for Row class
 * @author Daria Chaplin (dxc4643)
 */
public class RowTester {

    @Test
    public void testSomething() {
        for(int i = 0; i < 8; i++)
        {
            Row tester = new Row(i);
        }

        // Test some things
    }

}

