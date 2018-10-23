package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import com.webcheckers.ui.GetSignInRoute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import spark.*;

/**
 * Test class for GetSignInRoute (UI tier component)
 * @author Daria Chaplin (dxc4643)
 */
@Tag("UI-tier")
public class GetSignInRouteTester {

    /**
     * The component-under-test (CuT).
     */
    private GetSignInRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        // Create a unique CuT for each test
        CuT = new GetSignInRoute(engine);
    }
    
    @Test
    public void testSomething() {
        // test something
    }

}
