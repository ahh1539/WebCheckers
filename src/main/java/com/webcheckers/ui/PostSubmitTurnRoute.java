package com.webcheckers.ui;

import com.google.gson.Gson;
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
        // TODO: implement this
        return null;
    }
}
