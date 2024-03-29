package com.webcheckers.ui;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;
import static org.mockito.Mockito.*;

/**
 * Test class for PostStartGameRoute (UI tier component)
 * @author Paula Register (per4521)
 */
@Tag("UI-tier")
public class PostStartGameRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private PostStartGameRoute CuT;

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
        GameLobby gameLobby = mock(GameLobby.class);
        GameCenter gameCenter = new GameCenter(playerLobby, gameLobby);

        // create a unique CuT for each test
        CuT = new PostStartGameRoute(gameCenter, engine);
    }

    /**
     * Test that the user is properly logged out when the GetSignOutRoute is called
     */
    @Test
    public void signOutTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(new Player("test"));

        CuT.handle(request, response);


        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data

//        testHelper.assertViewModelAttribute(GetStartGameRoute.TITLE_ATTR, GetStartGameRoute.TITLE);
        //   * The player and playerList has been removed from the home view
     /*   testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER, null);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST, null);
        //   * test view name as it redirects back to home
        testHelper.assertViewName(GetHomeRoute.ROUTE_NAME);
        //   * test that the number of players is correct
        testHelper.assertViewModelAttribute(GetHomeRoute.NUM_PLAYERS, playerLobby.getNumberOfPlayers());*/
    }
}
