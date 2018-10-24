package com.webcheckers.ui;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import spark.*;


/**
 * Test class for GetStartGameRoute
 * @author Alex Hurley
 */

public class TestGetStartGameRoute {

    Player first = new Player("Alex");
    Player second = new Player("Daria");
    Player third = new Player("Eli");
    private GameLobby gameLobby = new GameLobby();
    private PlayerLobby playerLobby = new PlayerLobby();
    private GameCenter gc;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GetStartGameRoute startGame;


    @BeforeEach
    public void setup(){
        engine = mock(TemplateEngine.class);
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby.addPlayer(first);
        playerLobby.addPlayer(second);
        playerLobby.addPlayer(third);


        gc = new GameCenter(playerLobby,gameLobby);

        startGame = new GetStartGameRoute(engine,gc);

    }

    @Test
    @DisplayName("Object is instance of getstartgameroute")
    void testPlayerLobbyConstructor(){
        assertTrue(startGame instanceof GetStartGameRoute);
    }

    @Test
    @DisplayName("HandlerTest")
    void testHandler(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute(PostSignInRoute.PLAYER)).thenReturn(first);
        startGame.handle(request,response);


        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetStartGameRoute.WHITE_PLAYER_ATTR, null);
        testHelper.assertViewModelAttribute(GetStartGameRoute.TITLE_ATTR, GetStartGameRoute.TITLE);
    }
}
