package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Message;
import spark.*;

import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route{

    // Attributes
    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /backupMove} HTTP request.
     *
     * @param gson
     *   gson object to hold necessary message
     */
    public PostBackupMoveRoute(final Gson gson) {
        // No configuration required, set up logger
        LOG.config("PostBackupMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

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
