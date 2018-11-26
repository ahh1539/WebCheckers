package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;

import com.webcheckers.model.Player;
import spark.*;

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
    public static final String TITLE_ATTR = "title";
    public static final String TITLE = "Sign In!";
    public static final String PLAYER = "player";
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

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;

    //
    // Constructor
    //

    /**
     * The constructor for the {@code POST /signin} route handler.
     *
     * @param gameCenter
     * @param templateEngine template engine to use for rendering HTML page
     * @throws NullPointerException when the {@code gameCenter} or {@code templateEngine} parameter is null
     */
    public PostSignInRoute(TemplateEngine templateEngine, GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.gameCenter = gameCenter;
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
        final Session session = request.session();
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);

        // retrieve request parameter
        final String username = request.queryParams(USERNAME);

        if (username.trim().length() <= 0){
            return templateEngine.render(error(vm, makeBadArgMessage(username)));
        }
        final String usr = username.replaceAll("\\s+","");
        // Check if the username is alphanumeric
        Pattern p = Pattern.compile("[^a-zA-Z0-9\\s]");
        boolean isAlphaNumeric = !p.matcher(usr).find();

        if(!isAlphaNumeric){
            return templateEngine.render(error(vm, makeBadArgMessage(usr)));
        }

        // Check if the player's name has been taken
        Player player = new Player(usr);
        PlayerLobby playerLobby = gameCenter.getPlayerLobby();
        if(playerLobby.hasPlayer(player)){
            return templateEngine.render(error(vm, makeInvalidArgMessage(usr)));
        }

        // If it passed all the checks, add the player to the lobby
        playerLobby.addPlayer(player);
        session.attribute(PLAYER, player);
        vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, player);
        vm.put(GetHomeRoute.LOBBY_ATTR, playerLobby);
        response.redirect(WebServer.HOME_URL);
        return templateEngine.render(new ModelAndView(vm, GetHomeRoute.ROUTE_NAME));
    }
}
