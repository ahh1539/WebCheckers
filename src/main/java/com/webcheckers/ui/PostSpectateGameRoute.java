package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import com.google.gson.Gson;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
//import javafx.geometry.Pos;
import spark.*;
import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;



/**
 * The UI Controller to GET the Home page.
 *
 */
public class PostSpectateGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSpectateGameRoute.class.getName());

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
    public PostSpectateGameRoute(final TemplateEngine templateEngine, GameCenter gameCenter) {
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

        final Session session = request.session();
        Gson gson = new Gson();
        GameLobby gameLobby = gameCenter.getGameLobby();
        Player spectator = session.attribute(PostSignInRoute.PLAYER);
        final String id = spectator.getThing();
        Game game = gameLobby.getGame2(id);
        Message message = new Message(Message.Type.error, "false");

        if (game.changedTurn() == true){
            message = new Message(Message.Type.info, "true");
            game.changeTurn();
        }

        String rJson = gson.toJson(message);

        return rJson;

    }
}
