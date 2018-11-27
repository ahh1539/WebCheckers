package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import com.webcheckers.model.Color;
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
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        final Session session = request.session();
        GameLobby gameLobby = gameCenter.getGameLobby();
        PlayerLobby playerLobby = gameCenter.getPlayerLobby();

        // Sets up player and opponent player based on selection

        Player player1 = session.attribute(PostSignInRoute.PLAYER);

         //Checks if there is already a game, if so it refreshes the game state
        if (player1.resigned()){
            response.redirect(WebServer.HOME_URL);
            return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));
        }
        if (gameLobby.hasGame(player1)) {
            Game game = gameLobby.getGame(player1);
            if (player1.getColor() == Color.RED) {
                LOG.finer("red board building");
                vm.put(BOARD_ATTR, game.getRedBoard());
            }
            if (player1.getColor() == Color.WHITE) {
                LOG.finer("white board building");
                vm.put(BOARD_ATTR, game.getWhiteBoard());
            }
            //vm.put(GetStartGameRoute.MESSAGE, message);
            vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, player1);
            vm.put(GetStartGameRoute.VIEW_MODE_ATTR, Game.ViewMode.PLAY);
            vm.put(GetStartGameRoute.RED_PLAYER_ATTR, game.getRedPlayer());
            vm.put(GetStartGameRoute.WHITE_PLAYER_ATTR, game.getWhitePlayer());
            vm.put(GetStartGameRoute.ACTIVE_COLOR_ATTR, gameLobby.getGame(player1).getActiveColor());
            return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));

        } else {
            //creates a new game
            String secondPlayer = request.queryParams("opponent");
            System.out.println(secondPlayer);
            Player player2 = new Player(secondPlayer);
            if (PlayerLobby.getPlayer(player2.getName()).inGame()) {
                String msg = "The player you have selected is already in a game. Select another player.";
                vm.put(GetHomeRoute.ERROR, msg);
                vm.put(GetHomeRoute.PLAYER_LIST, playerLobby.getPlayerLobby());
                vm.put(GetHomeRoute.LOBBY_ATTR, playerLobby);
                vm.put(GetHomeRoute.PLAYER, player1);
                return templateEngine.render(new ModelAndView(vm, GetHomeRoute.ROUTE_NAME));
            }
            player2.assignColor(Color.WHITE);
            Game game = new Game(player1, player2);
            PlayerLobby.getPlayer(player1.getName()).joinGame();
            PlayerLobby.getPlayer(player2.getName()).joinGame();

            // Configures view model for new game
            gameLobby.addGame(game);

            Message message = new Message(Message.Type.error, "Text");

            if (player1.getColor() == Color.RED) {
                LOG.finer("red board building");
                vm.put(BOARD_ATTR, game.getRedBoard());
            }
            if (player1.getColor() == Color.WHITE) {
                LOG.finer("white board building");
                vm.put(BOARD_ATTR, game.getWhiteBoard());
            }

            vm.put(GetStartGameRoute.MESSAGE, message);
            vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, player1);
            vm.put(GetStartGameRoute.VIEW_MODE_ATTR, Game.ViewMode.PLAY);
            vm.put(GetStartGameRoute.RED_PLAYER_ATTR, player1);
            vm.put(GetStartGameRoute.WHITE_PLAYER_ATTR, player2);
            vm.put(GetStartGameRoute.ACTIVE_COLOR_ATTR, gameLobby.getGame(player1).getActiveColor());

            return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));
        }
    }
}
