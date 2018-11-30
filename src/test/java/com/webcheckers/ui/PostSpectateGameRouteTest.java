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
 * Test class for GetSpectateGameRoute (UI tier component)
 * @author Paula Register (per4521)
 */
@Tag("UI-tier")
public class PostSpectateGameRouteTest {
    /**
     * The component-under-test (CuT).
     */
    private PostSpectateGameRoute CuT;

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
    private Gson gson;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        gson = new Gson();
        when(request.session()).thenReturn(session);
        when(request.body()).thenReturn("{'start':{'row':2,'cell':1},'end':{'row':3,'cell':2}}");

        engine = mock(TemplateEngine.class);
        // set up friendly objects
        this.playerLobby = new PlayerLobby();
        player = new Player("player");
        this.playerLobby.addPlayer(player);
        gameLobby = mock(GameLobby.class);
        GameCenter gameCenter = new GameCenter(playerLobby, gameLobby);

        // create a unique CuT for each test
        CuT = new PostSpectateGameRoute(engine, gameCenter);
    }
    /**
     * Test that move is properly validated for the white board.
     */
    @Test
    public void testGetSpectateGameRoute(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player1 = new Player("user");
        Player player2 = new Player("opp");
        player1.assignColor(Color.RED);
        player2.assignColor(Color.RED);
        Game g = new Game(player1, player2, player1.getName()+player2.getName());
        g.getRedBoard().placeRedPieces();
        player2.setThing(player1.getName()+player2.getName());
        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(player2);
        when(gameLobby.getGame(eq(player2))).thenReturn(g);
        when(gameLobby.getGame2(eq(player1.getName()+player2.getName()))).thenReturn(g);
        g.changeTurn();
        CuT.handle(request, response);
    }
}
