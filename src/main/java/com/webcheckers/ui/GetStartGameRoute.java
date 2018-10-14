package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetStartGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetStartGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    static final String GAME_NAME = "game.ftl";
    static final String TITLE_ATTR = "title";
    static final String TITLE = "Game Page";
    static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String BOARD_ATTR = "board";


    public GetStartGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        // Validation and configuration
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Start Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetStartGameRoute is invoked.");
        // retrieve the HTTP session
        final Session session = request.session();
        Player player = session.attribute(PostSignInRoute.PLAYER);
        Game game = this.gameCenter.getGameLobby().getGame(player);

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Start Game");

        if(game == null){
            vm.put(GetStartGameRoute.TITLE_ATTR, GetStartGameRoute.TITLE);
            vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, player);
            return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));
        }
        vm.put(BOARD_ATTR, game.getBoard());
        vm.put(CURRENT_PLAYER_ATTR, player);
        vm.put(TITLE_ATTR, TITLE);
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}