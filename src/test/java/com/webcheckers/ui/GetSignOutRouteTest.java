package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
 * Test class for GetSignInRoute (UI tier component)
 * @author Daria Chaplin (dxc4643)
 */
@Tag("UI-tier")
public class GetSignOutRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private GetSignOutRoute CuT;

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

        // component-under-test
        CuT = new GetSignOutRoute(engine, gameCenter);
    }

    @Test
    public void signOutTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);


        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetSignOutRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER, null);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST, null);
        //   * test view name
        testHelper.assertViewName(GetHomeRoute.ROUTE_NAME);
        testHelper.assertViewModelAttribute(GetHomeRoute.NUM_PLAYERS, playerLobby.getNumberOfPlayers());
    }

}


/*
package com.webcheckers.ui;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.Test;
import org.junit.Before;
import spark.*;
import org.junit.jupiter.api.Tag;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Tag("UI-tier")
public class GetSignOutRouteTest {
    private GetSignOutRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player;

    @Before
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

        // component-under-test
        CuT = new GetSignOutRoute(engine, gameCenter);
    }

    @Test
    public void signOutTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);


        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER, null);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST, null);
        //   * test view name
        testHelper.assertViewName(GetHomeRoute.ROUTE_NAME);
        //   * verify that a player service object are stored in the session.
        verify(session).attribute(eq(GetHomeRoute.LOBBY_ATTR), any(PlayerLobby.class));

    }
}
*/
