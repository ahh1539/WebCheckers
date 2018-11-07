package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {

    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    // Attributes
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
    private Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /submitTurn} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSubmitTurnRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        //
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;

        // No configuration required, set up logger
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

        final Session session = request.session();

        // get current player and game to compare active color with
        Player player = session.attribute(PostSignInRoute.PLAYER);
        Game game = gameCenter.getGameLobby().getGame(player);
        Message msg;

        // TODO: complete implementation with specific message based on validateMove results

        if(true) {
            // if the turn is valid and processed
            msg = new Message(Message.Type.INFO, "Valid move successfully processed");
            LOG.info("current player: " + player +", active color: " + game.getActiveColor());
            game.toggleActiveColor();
            LOG.info("current player: " + player +", active color: " + game.getActiveColor());

        } else {
            // turn is invalid/not complex enough- need specific reason to be given, switch statements?
            msg = new Message(Message.Type.ERROR, "Invalid move. [Reason]. " +
                    "Please backup your move and try again.");
        }

        gson = new Gson();
        String rJSON = gson.toJson(msg);
        return rJSON;
    }
}
