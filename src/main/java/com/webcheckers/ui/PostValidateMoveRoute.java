package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {

    // Attributes
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;
    private Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /validateMove} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostValidateMoveRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        // Validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        // Configuration
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        // Grab game from session
        final Session session = request.session();

        // Get current player and game to compare active color with
        Player player = session.attribute(PostSignInRoute.PLAYER);
        Color playerColor = player.getColor();
        Game game = gameCenter.getGameLobby().getGame(player);
        BoardView board;

        if (playerColor.equals(Color.RED)) {
            board = game.getRedBoard();
        } else {
            board = game.getWhiteBoard();
        }

        // Grab move from request
        final Gson gson = new Gson();
        final String json = request.body();
        final Move move = gson.fromJson(json, Move.class);

        // Check if it's valid, and format response message to JSON
        // Add valid moves to arraylist in Game
        Message message = move.isValidMessage(board);
        if (!(message.getType() == Message.Type.error)) {
            if (playerColor.equals(Color.RED)) {
                message = game.updateBoardRedTurn(move);
            } else {
                message = game.updateBoardWhiteTurn(move);
            }
        }

        return gson.toJson(message);
    }
}
