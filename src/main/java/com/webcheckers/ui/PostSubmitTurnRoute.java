package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import spark.*;
import static spark.Spark.halt;

import java.lang.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        // Get current player and game to compare active color with

        Player player = session.attribute(PostSignInRoute.PLAYER);
        Game game = gameCenter.getGameLobby().getGame(player);
        Message msg;

        // Checks for a captured piece and removes

        List<Move> moves = game.getMoves();
        Move lastMove = moves.get(moves.size() - 1);
        Position start = lastMove.getStart();
        Position end = lastMove.getEnd();

        if(Math.abs(start.getRow() - end.getRow()) > 1) {
            // Quick implementation of single jump capture
            int targetRow = Math.abs(start.getRow() + end.getRow()) / 2;
            int targetCell = Math.abs(start.getCell() + end.getCell()) / 2;
            Position target = new Position(targetRow, targetCell);

            if(player.getColor().equals(Color.RED)) {
                game.whiteCaptured(target);
            } else {
                game.redCaptured(target);
            }
        }

        // TODO: complete implementation with specific message based on validateMove results
        // TODO: Refresh /game if not error

        if(true) {

            // if the turn is valid and processed
            msg = new Message(Message.Type.INFO, "Valid move successfully processed");
            LOG.info("current player: " + player +", active color: " + game.getActiveColor());
            game.toggleActiveColor();
            LOG.info("current player: " + player +", active color: " + game.getActiveColor());
            //response.redirect(WebServer.GAME_URL);
            //return templateEngine.render(new ModelAndView(vm, GetGameRoute.GAME_NAME));

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
