package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    static final String MESSAGE_ATTR = "message";
    static final String MESSAGE_TYPE_ATTR = "messageType";
    public static final String TITLE_ATTR = "title";
    public static final String TITLE = "Sign In!";
    public static final String PLAYER = "player";
    static final String ERROR_TYPE = "error";


    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostCheckTurnRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        //
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        //
        LOG.config("PostCheckTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

//        Map<String, Object> vm = new HashMap<>();
//
//        vm.put(MESSAGE_ATTR, "true");
//        vm.put(MESSAGE_TYPE_ATTR, Message.Type.info);

        final Session session = request.session();
        Gson gson = new Gson();

        // get current player and game to compare active color with
        Player player = session.attribute(PostSignInRoute.PLAYER);
        Game game = gameCenter.getGameLobby().getGame(player);

        // default behavior returns Not My Turn values
        Message message = new Message(Message.Type.error, "false");
        LOG.info(message.getText());
        LOG.info("curr: "+ player + ", active: " + game.getActiveColor());

        // if it is current player's turn, change messages values
        if(game.getActiveColor() == player.getColor()) {
            LOG.info("activeColor == player's Color");
            message = new Message(Message.Type.info, "true");
        }

        // convert message to JSON for response
        String rJson = gson.toJson(message);

        return rJson;
    }
}
