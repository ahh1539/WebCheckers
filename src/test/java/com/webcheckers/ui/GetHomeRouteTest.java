
package com.webcheckers.ui;

import static com.webcheckers.ui.GetGameRoute.CURRENT_PLAYER_ATTR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;


/**
 * Test class for GetSignInRoute (UI tier component)
 */

@Tag("UI-tier")
public class GetHomeRouteTest {
    /**
     * The component-under-test (CuT).
     */
    private GetHomeRoute CuT;

    /**
     * Mock objects
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);

        // set up friendly objects
        this.playerLobby = new PlayerLobby();
        player = new Player("player");
        this.playerLobby.addPlayer(player);
        GameLobby gameLobby = new GameLobby();
        GameCenter gameCenter = new GameCenter(playerLobby, gameLobby);

        // create a unique CuT for each test
        CuT = new GetHomeRoute(engine, gameCenter);
    }
    /*
     * Test that the GameHomeRoute will show current players only if signed in
     */
    @Test
    public void showPlayers()
    {
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

    /*
     * Test that GameHomeRoute will create a new game only if the Opponent is
     * free to play.
     */
    @Test
    public void freeToPlay() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        //Invoke the test (ignore the output)
        CuT.handle(request, response);

        //Analyze the content passed into the render method
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttributeIsAbsent(CURRENT_PLAYER_ATTR);
    }
}
