package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.NUM_PLAYERS;
import static com.webcheckers.ui.GetStartGameRoute.CURRENT_PLAYER_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.ui.GetHomeRoute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

/**
 * Test class for GetSignInRoute (UI tier component)
 * @author Daria Chaplin (dxc4643)
 */
@Tag("UI-tier")
public class GetHomeRouteTest
{
    /**
     * The component-under-test (CuT).
     */
    private GetHomeRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GameCenter gameCenter;
    private GameLobby gameLobby;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        gameCenter = mock(GameCenter.class);
        gameLobby = mock(GameLobby.class);

        // create a unique CuT for each test
        CuT = new GetHomeRoute(engine, gameCenter);
    }

    /**
     * Test that the GameHomeRoute will show current players only if signed in
     */
    @Test
    public void showPlayers()
    {
        PlayerLobby pLobby = new PlayerLobby();
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        //Mock of the 'render' method
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Invoke the test (ignore the output)
        CuT.handle(request, response);

        //Analyze the content passed into the render method
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttributeIsAbsent(CURRENT_PLAYER_ATTR);
    }

    /**
     * Test that GameHomeRoute will create a new game only if the Opponent is
     * free to play.
     */
    @Test
    public void freeToPlay() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        //Invoke the test (ignore the output)
        CuT.handle(request, response);

        //Analyze the content passed into the render method
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttributeIsAbsent(CURRENT_PLAYER_ATTR);
    }
}
