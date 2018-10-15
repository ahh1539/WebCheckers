package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostStartGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostStartGameRoute.class.getName());

    //
    // Constants
    //

    // Values used to build the view model and display the game

    static final String PLAYER = "currentPlayer";
    static final String VIEW_MODE = "viewMode";
    static final String MODE_OPTIONS = "modeOptionsAsJSON";
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";
    static final String MESSAGE = "message";

    //
    // Attributes
    //

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code POST /game} route handler.
     *
     * @param gameCenter coordinates web app
     * @param templateEngine template engine to use for rendering HTML page
     * @throws NullPointerException when the {@code gameCenter} or {@code templateEngine} parameter is null
     */
    public PostStartGameRoute(GameCenter gameCenter, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.gameCenter = gameCenter;
        this.templateEngine = templateEngine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();
        Map<String, Object> vm = new HashMap<>();

        // Finds current player and sends to game.ftl

        Player player = session.attribute(PostSignInRoute.PLAYER);
        player.joinGame();
        vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, player);
        // Sets up board and sets view mode to Play
        vm.put(VIEW_MODE, Game.ViewMode.PLAY);

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}