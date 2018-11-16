package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route{

    // Attributes
    private final GameCenter gameCenter;
    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /backupMove} HTTP request.
     *
     * @param gameCenter
     *      used to obtain Game being updated
     */
    public PostBackupMoveRoute(final GameCenter gameCenter) {
        // Validation and configuration
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gameCenter = gameCenter;
        // Set up logger
        LOG.config("PostBackupMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

        final Session session = request.session();
        Player player = session.attribute(PostSignInRoute.PLAYER);
        Game game = gameCenter.getGameLobby().getGame(player);
        Message msg;

        // TODO (from docs): "update the player's turn in the user's game state"

        // TODO: add in actual conditionals, tell user what the action did
        if(true) {
            // backup was successful
            msg = new Message(Message.Type.INFO, "Successful backup");
        } else {
            // backup failed
            msg = new Message(Message.Type.ERROR, "Backup failed");
        }

        gson = new Gson();
        return gson.toJson(msg);
    }
}
