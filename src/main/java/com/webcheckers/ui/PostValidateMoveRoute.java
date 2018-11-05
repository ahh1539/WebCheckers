package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

//    private final TemplateEngine templateEngine;
//    private final GameCenter gameCenter;

    private Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param gson
     *   the HTML template rendering engine
     */
    public PostValidateMoveRoute(final Gson gson) {
//        // validation
//        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
//        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
//        //
//        this.templateEngine = templateEngine;
//        this.gameCenter = gameCenter;
//        //
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

//        // code from Ajax slides
//        get("/path", new GetRoute(gson));

        final Gson gson = new Gson();
        final String json = request.body();
        final Move move = gson.fromJson(json, Move.class);

        LOG.info(move.toString());

        return new Message(Message.Type.INFO, "valid move");

    }
}
