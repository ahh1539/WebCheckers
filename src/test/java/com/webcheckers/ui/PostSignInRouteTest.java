package com.webcheckers.ui;


import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSignInRouteTest {

    private static final String USERNAME = "user 2";
    private static final String SAME_USERNAME = "  user   2";
    private static final String DIFF_USERNAME = "diff user";
    private static final String INVALID_USERNAME = "user!";


    /**
     * The component-under-test (CuT).
     *
     * <p>
     * This is a stateless component so we only need one.
     * The {@link GameCenter} component is thoroughly tested so
     * we can use it safely as a "friendly" dependency.
     */
    private PostSignInRoute CuT;

    // friendly objects
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private GameLobby gameLobby;

    // attributes holding mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);

        // build the Service and Model objects
        // the GameCenter and PlayerLobby and GameLobby are friendly
        playerLobby = new PlayerLobby();
        gameLobby = new GameLobby();
        gameCenter = new GameCenter(playerLobby, gameLobby);

        // but mock up the PlayerService
        // ^^^ left in as a possible reminder to do something?
        // ^^^ found it in the PostGuessRouteTest

        // create a unique CuT for each test
        CuT = new PostSignInRoute(engine, gameCenter);
    }

    @Test
    public void bad_u_name_1(){

        // arrange test scenario: User tries to sign in without uName
        playerLobby.addPlayer(new Player("user"));
        when(request.queryParams(eq(PostSignInRoute.USERNAME))).thenReturn("invalid name");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(
                GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);
        //testHelper.assertViewModelAttribute(
          //      GetStartGameRoute.CURRENT_PLAYER_ATTR, );
        //testHelper.assertViewModelAttribute(
         //       GetHomeRoute.LOBBY_ATTR, PostSignInRoute.ERROR_TYPE);
        //   * test view name
        testHelper.assertViewName(GetHomeRoute.ROUTE_NAME);

    }

    @Test
    public void u_name_in_use(){

    }



}
