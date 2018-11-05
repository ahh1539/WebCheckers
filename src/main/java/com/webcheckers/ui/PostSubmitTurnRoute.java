package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Message;
import spark.*;

import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {

    // Attributes
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
    private Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /submitTurn} HTTP request.
     *
     * @param gson
     *   gson object to hold necessary message
     */
    public PostSubmitTurnRoute(final Gson gson) {
        // No configuration required, set up logger
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

        Message msg;

        // TODO: complete implementation with specific message based on validateMove results

        if(true) {
            // if the turn is valid and processed
            msg = new Message(Message.Type.INFO, "Valid move successfully processed");
        } else {
            // turn is invalid/not complex enough- need specific reason to be given, switch statements?
            msg = new Message(Message.Type.ERROR, "Invalid move. [Reason]. " +
                    "Please backup your move and try again.");
        }

        gson = new Gson();
        gson.toJson(msg);
        return gson;
    }
}
