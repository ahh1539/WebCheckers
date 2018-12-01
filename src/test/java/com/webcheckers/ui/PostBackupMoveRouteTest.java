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
 * Test class for PostBackupMoveRoute (UI tier component)
 * @author Paula Register (per4521)
 */
@Tag("UI-tier")
public class PostBackupMoveRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private PostBackupMoveRoute CuT;

    /**
     * Mock objects
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GameCenter gameCenter;
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
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        gson = new Gson();
        this.playerLobby = new PlayerLobby();
        player = new Player("player");
        this.playerLobby.addPlayer(player);
        gameLobby = mock(GameLobby.class);
        GameCenter gameCenter = new GameCenter(playerLobby, gameLobby);

        // create a unique CuT for each test
        CuT = new PostBackupMoveRoute(gameCenter);
    }

    /**
     * Test that backupMove handle can be called
     */
    @Test
    public void postBackupMoveRouteTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player1 = new Player("user");
        Player player2 = new Player("opp");
        player1.assignColor(Color.RED);
        player2.assignColor(Color.RED);
        Game g = new Game(player1, player2, player1.getName()+player2.getName());
        g.getRedBoard().placeRedPieces();
        when(request.queryParams(eq("opponent"))).thenReturn(player1.toString());
        when(session.attribute(eq(PostSignInRoute.PLAYER))).thenReturn(player2);
        when(gameLobby.getGame(eq(player2))).thenReturn(g);
        when(request.queryParams(eq("gameID"))).thenReturn(player1.getName()+player2.getName());
        when(gameLobby.getGame2(eq(player1.getName()+player2.getName()))).thenReturn(g);

        Position startPosition = new Position(0,1);
        Position endPosition = new Position(1,1);
        Move move = new Move(startPosition, endPosition);
        g.updateBoardRedTurn(move);
        CuT.handle(request, response);
    }
}
