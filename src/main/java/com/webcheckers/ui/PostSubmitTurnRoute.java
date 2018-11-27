package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import spark.*;

import static spark.Spark.halt;

import java.lang.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {

    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;


    // Attributes
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /submitTurn} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
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

        // Get current player and game to compare active color with

        Player player = session.attribute(PostSignInRoute.PLAYER);
        Game game = gameCenter.getGameLobby().getGame(player);
        Message msg;

        // TODO: complete implementation with specific message based on validateMove results
        // TODO: Refresh /game if not error

        if (true) {

            // If the turn is valid and processed
            msg = new Message(Message.Type.info, "Valid move successfully processed");
            LOG.info("current player: " + player + ", active color: " + game.getActiveColor());
            game.toggleActiveColor();
            LOG.info("current player: " + player + ", active color: " + game.getActiveColor());

            // Checks for captured piece(s) and removes

            List<Move> moves = game.getMoves();
            Move lastMove = moves.get(moves.size() - 1);
            Position start = lastMove.getStart();
            Position end = lastMove.getEnd();

            if (Math.abs(start.getRow() - end.getRow()) > 1) {
                // Quick implementation of single jump capture
                int targetRow = (start.getRow() + end.getRow()) / 2;
                int targetCell = (start.getCell() + end.getCell()) / 2;
                Position target = new Position(targetRow, targetCell);

                // Need to determine all targets and call capture method for each
                if (player.getColor().equals(Color.RED)) {
                    game.whiteCaptured(target);
                } else {
                    game.redCaptured(target);
                }
            }

            //response.redirect(WebServer.GAME_URL);
            //return templateEngine.render(new ModelAndView(vm, GetGameRoute.GAME_NAME));

        } else {
            // turn is invalid/not complex enough- need specific reason to be given, switch statements?
            msg = new Message(Message.Type.error, "Invalid move. [Reason]. " +
                    "Please backup your move and try again.");
        }

        Gson gson = new Gson();
        return gson.toJson(msg);
    }
}
