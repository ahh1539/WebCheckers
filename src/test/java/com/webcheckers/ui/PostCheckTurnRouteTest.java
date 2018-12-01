package com.webcheckers.ui;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;
import static org.mockito.Mockito.*;

/**
 * Test class for PostCheckTurnRoute (UI tier component)
 * @author Paula Register (per4521)
 */
@Tag("UI-tier")
public class PostCheckTurnRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private PostCheckTurnRoute CuT;

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
        CuT = new PostCheckTurnRoute(engine, gameCenter);
    }

    /**
     * Test that turn is checked appropriately.
     */
    @Test
    public void postCheckTurnRouteTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player1 = new Player("user");
        Player player2 = new Player("opp");
        Game g = new Game(player1, player2, player1.getName()+player2.getName());
        when(request.queryParams(eq("opponent"))).thenReturn(player1.toString());
        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(player2);
        when(gameLobby.getGame(eq(player2))).thenReturn(g);
        CuT.handle(request, response);
    }
}
