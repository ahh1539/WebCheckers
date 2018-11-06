package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    private Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostValidateMoveRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        //
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        //
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

        // grab game from session
        final Session session = request.session();

        // get current player and game to compare active color with
        Player player = session.attribute(PostSignInRoute.PLAYER);
        Game game = gameCenter.getGameLobby().getGame(player);
        BoardView board;
        if(player.getColor() == Color.RED){
            board = game.getRedBoard();
        }
        else{
            board = game.getWhiteBoard();
        }

        // grab move from request
        final Gson gson = new Gson();
        final String json = request.body();
        final Move move = gson.fromJson(json, Move.class);

        // check if it's valid, and format response message to JSON
        Message message = move.isValidMessage(board);
        String rjson = gson.toJson(message);

        return rjson;
    }
}
