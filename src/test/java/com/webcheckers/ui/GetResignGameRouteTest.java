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
 * Test class for GetResignGameRoute (UI tier component)
 * @author Paula Register (per4521)
 */
@Tag("UI-tier")
public class GetResignGameRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private GetResignGameRoute CuT;

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
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        // set up friendly objects
        this.playerLobby = new PlayerLobby();
        player = new Player("player");
        this.playerLobby.addPlayer(player);
        gameLobby = mock(GameLobby.class);
        gameCenter = mock(GameCenter.class);

        // create a unique CuT for each test
        CuT = new GetResignGameRoute(engine, gameCenter);
    }

    /**
     * Test that the user can properly resign
     */
    @Test
    public void resignTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player = new Player("test");
        Player opponent = new Player("opponent");
        GameLobby gameLobby =new GameLobby();
        gameLobby.addGame(new Game(player, opponent));
        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(player);
        when(gameCenter.getGameLobby()).thenReturn(gameLobby);

        CuT.handle(request, response);

    }

    /**
     * Test that the user can properly resign
     */
    @Test
    public void resignTestWhite(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player = new Player("test");
        Player opponent = new Player("opponent");
        player.assignColor(Color.WHITE);
        opponent.assignColor(Color.WHITE);
        GameLobby gameLobby = new GameLobby();
        gameLobby.addGame(new Game(player, opponent));
        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(player);
        when(gameCenter.getGameLobby()).thenReturn(gameLobby);

        CuT.handle(request, response);

    }
}

