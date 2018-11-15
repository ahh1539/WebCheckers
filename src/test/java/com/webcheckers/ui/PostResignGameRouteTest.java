package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;
import static org.mockito.Mockito.*;

/**
 * Test class for PostResignGameRoute (UI tier component)
 * @author Paula Register (per4521)
 */
@Tag("UI-tier")
public class PostResignGameRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private PostResignGameRoute CuT;

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
        gameLobby = mock(GameLobby.class);
        GameCenter gameCenter = new GameCenter(playerLobby, gameLobby);

        // create a unique CuT for each test
        CuT = new PostResignGameRoute(engine, gameCenter);
    }

    /**
     * Test that the resign is properly posted for red players
     */
    @Test
    public void postResignGameRouteTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player1 = new Player("user");
        Player player2 = new Player("opp");
        Game g = new Game(player1, player2);
        when(request.queryParams(eq("opponent"))).thenReturn(player1.toString());
        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(player2);
        when(gameLobby.getGame(eq(player2))).thenReturn(g);
        CuT.handle(request, response);
    }

    /**
     * Test that the resign is properly posted for white players
     */
    @Test
    public void postResignGameRouteTestWhite(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player1 = new Player("user");
        Player player2 = new Player("opp");
        player1.assignColor(Color.WHITE);
        player2.assignColor(Color.WHITE);

        Game g = new Game(player1, player2);
        player1.joinGame();
        player2.joinGame();

        when(request.queryParams(eq("opponent"))).thenReturn(player1.toString());
        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(player2);

        when(gameLobby.getGame(eq(player2))).thenReturn(g);
        CuT.handle(request, response);
    }
}
