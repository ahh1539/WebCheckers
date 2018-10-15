package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
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
    static final String BOARD_ATTR = "board";
    static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String VIEW_MODE_ATTR = "viewMode";

    static final String MESSAGE = "message";


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


        PlayerLobby.getPlayer(player.getName()).joinGame();




        Game game = this.gameCenter.getGameLobby().getGame(player);

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Start Game");

        if(game == null){
            vm.put(TITLE_ATTR, TITLE);
            vm.put(GetHomeRoute.NUM_PLAYERS, this.gameCenter.getPlayerLobby().getNumberOfPlayers());
            vm.put(CURRENT_PLAYER_ATTR, player);
            vm.put("lobby", this.gameCenter.getPlayerLobby().getPlayerLobby());
            return templateEngine.render(new ModelAndView(vm, GetHomeRoute.ROUTE_NAME));
        }
        vm.put(BOARD_ATTR, game.getBoard());
        vm.put(CURRENT_PLAYER_ATTR, player);
        vm.put(TITLE_ATTR, TITLE);
        vm.put(VIEW_MODE_ATTR, Game.ViewMode.PLAY);
        vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
        vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
