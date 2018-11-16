package com.webcheckers.ui;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
/**
 * Test class for GetGameRoute (UI tier component)
 * @author Paula Register (per4521)
 */
@Tag("UI-tier")
public class GetGameRouteTest {
    /**
     * The component-under-test (CuT).
     */
    private GetGameRoute CuT;

    /**
     * Mock objects
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player;
    private GameCenter gameCenter;
    private GameLobby gameLobby;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        gameCenter = mock(GameCenter.class);
        gameLobby = mock(GameLobby.class);
        // set up friendly objects
        player = new Player("friendly");
        this.playerLobby = mock(PlayerLobby.class);
        GameLobby gameLobby = mock(GameLobby.class);
        GameCenter gameCenter = mock(GameCenter.class);

        // create a unique CuT for each test
        CuT = new GetGameRoute(engine, gameCenter);
    }

    /**
     * Test that the user
     */
    @Test
    public void getGameTest() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);
        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data

        testHelper.assertViewModelAttribute(GetSignOutRoute.TITLE_ATTR, GetGameRoute.TITLE);
    }

    /**
     * Test that the user
     */
    @Test
    public void getGameWithPlayerTest() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player1 = new Player("user");
        Player player2 = new Player("opp");
        player1.assignColor(Color.WHITE);
        player2.assignColor(Color.WHITE);

        player1.joinGame();
        player2.joinGame();
        playerLobby.addPlayer(player1);
        playerLobby.addPlayer(player2);
        when(request.queryParams(eq("opponent"))).thenReturn(player2.getName());
        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(player1);
        when(gameCenter.getGameLobby()).thenReturn(gameLobby);

        CuT.handle(request, response);
        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data

        testHelper.assertViewModelAttribute(GetSignOutRoute.TITLE_ATTR, GetGameRoute.TITLE);
    }
}
