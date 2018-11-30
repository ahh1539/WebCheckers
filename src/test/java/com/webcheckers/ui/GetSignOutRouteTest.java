package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;
import static org.mockito.Mockito.*;

/**
 * Test class for GetSignInRoute (UI tier component)
 * @author Paula Register (per4521)
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
    private GameLobby gameLobby;
    private GameCenter gameCenter;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);

        // set up friendly objects
        this.playerLobby = new PlayerLobby();
        player = new Player("player");
        this.playerLobby.addPlayer(player);
        gameLobby = mock(GameLobby.class);
        gameCenter = new GameCenter(playerLobby, gameLobby);

        // create a unique CuT for each test
        CuT = new GetSignOutRoute(engine, gameCenter);
    }

    /**
     * Test that the user is properly logged out when the GetSignOutRoute is called
     */
    @Test
    public void signOutTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player = new Player("test");
        Player opponent = new Player("opponent");
        player.assignColor(Color.WHITE);
        opponent.assignColor(Color.WHITE);

        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(player);
        when(gameLobby.hasGame(eq(player))).thenReturn(true);
        when(gameLobby.getGame(eq(player))).thenReturn(new Game(player,opponent, player.getName()+opponent.getName()));

        CuT.handle(request, response);


        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data

        testHelper.assertViewModelAttribute(GetSignOutRoute.TITLE_ATTR, GetSignOutRoute.TITLE);
        //   * The player and playerList has been removed from the home view
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER, null);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST, null);
        //   * test view name as it redirects back to home
        testHelper.assertViewName(GetHomeRoute.ROUTE_NAME);
        //   * test that the number of players is correct
        testHelper.assertViewModelAttribute(GetHomeRoute.NUM_PLAYERS, playerLobby.getNumberOfPlayers());
    }

}
