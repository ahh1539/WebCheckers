package com.webcheckers.ui;

import com.google.gson.Gson;
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
        // TODO: implement this
        return null;
    }
}
