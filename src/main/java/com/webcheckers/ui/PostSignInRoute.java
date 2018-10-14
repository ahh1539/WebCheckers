package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.webcheckers.application.PlayerLobby;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The {@code POST /guess} route handler.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 */
public class PostSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    //
    // Constants
    //

    // Values used in the view-model map for rendering the game view after a guess.
    static final String USERNAME = "username";
    static final String MESSAGE_ATTR = "message";
    static final String MESSAGE_TYPE_ATTR = "messageType";

    static final String ERROR_TYPE = "error";
    static final String VIEW_NAME = "signin.ftl";

    //
    // Static methods
    //

    /**
     * Make an error message when the username is invalid.
     */
    static String makeBadArgMessage(final String usernameStr) {
        return String.format("Usernames must be alphanumeric, spaces optional. '%s' is invalid.", usernameStr);
    }

    /**
     * Make an error message when the username is already in use.
     */
    static String makeInvalidArgMessage(final String usernameStr) {
        return String.format("%s is already in use - try again.", usernameStr);
    }

    //
    // Attributes
    //

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    //
    // Constructor
    //

    /**
     * The constructor for the {@code POST /signin} route handler.
     *
     * @param PlayerLobby    {@Link PlayerLobby} that holds over statistics
     * @param templateEngine template engine to use for rendering HTML page
     * @throws NullPointerException when the {@code gameCenter} or {@code templateEngine} parameter is null
     */
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    private ModelAndView error(final Map<String, Object> vm, final String message) {
        vm.put(MESSAGE_ATTR, message);
        vm.put(MESSAGE_TYPE_ATTR, ERROR_TYPE);
        return new ModelAndView(vm, VIEW_NAME);
    }

    //
    // TemplateViewRoute method
    //

    /**
     * {@inheritDoc}
     *
     * @throws NoSuchElementException when an invalid result is returned after making a guess
     */
    @Override
    public String handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Sign In");

        // retrieve request parameter
        final String username = request.queryParams(USERNAME);
        // Check if the username is alphanumeric
        Pattern p = Pattern.compile("[^a-zA-Z0-9\\s]");
        boolean isAlphaNumeric = !p.matcher(username).find();
        System.out.println(isAlphaNumeric);
        if(!isAlphaNumeric){
            System.out.println(error(vm, makeBadArgMessage(username)));
            return templateEngine.render(error(vm, makeBadArgMessage(username)));
        }
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}
