package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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

    /**
     * Mock objects
     */
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

    // Tests the handle method
    @Test
    public void handleTest() {
        // Allows for capturing of ModelAndView data passed to template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invokes the test
        CuT.handle(request, response);

        // Analyzes the results

        // Checks that model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // Checks that model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);
    }

}
