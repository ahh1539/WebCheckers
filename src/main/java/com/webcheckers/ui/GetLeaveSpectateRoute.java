package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Color;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
//import javafx.geometry.Pos;
import spark.*;

/**
 * The UI Controller to GET the Home page.
 *
 */
public class GetLeaveSpectateRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSpectateGameRoute.class.getName());

    static final String ROUTE_NAME = "game.ftl";
    static final String SPECTATOR = "currentPlayer";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String BOARD_ATTR = "board";
    static final String MESSAGE = "message";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String TITLE_ATTR = "title";
    static final String TITLE = "Game Page";

    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetLeaveSpectateRoute(final TemplateEngine templateEngine, GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(templateEngine, "gameCenter must not be null");
        //
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        //
        LOG.config("GetSpectateRoute is initialized.");
    }

    /**
     * Render the WebCheckers Spectate Game page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSpectateRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        final Session session = request.session();
        Player spectator = session.attribute(PostSignInRoute.PLAYER);
        spectator.leaveGame();
        response.redirect(WebServer.HOME_URL);
        return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));

    }
}

