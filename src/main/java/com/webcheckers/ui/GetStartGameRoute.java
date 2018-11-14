package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
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

        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        final Session session = request.session();

        // Sets up player and opponent player based on selection

        Player player1 =  session.attribute(PostSignInRoute.PLAYER);
        String secondPlayer = request.queryParams("opponent");

        // Checks if the players are already in the lobby, if not it adds them.
        PlayerLobby playerLobby = gameCenter.getPlayerLobby();
        Player player2 = new Player(secondPlayer);
        if(!playerLobby.hasPlayer(player1)){
            playerLobby.addPlayer(player1);
        }
        if(!playerLobby.hasPlayer(player2)){
            playerLobby.addPlayer(player2);
        }

        // Checks whether the opponent is already playing a game

        if (PlayerLobby.getPlayer(player1.getName()).inGame()|| PlayerLobby.getPlayer(player2.getName()).inGame()){
            String msg = "The player you have selected is already in a game. Select another player.";
            vm.put(GetHomeRoute.ERROR, msg);
            vm.put(GetHomeRoute.PLAYER_LIST, playerLobby.getPlayerLobby());
            vm.put(GetHomeRoute.LOBBY_ATTR, playerLobby);
            vm.put(GetHomeRoute.PLAYER, player1);
            return templateEngine.render(new ModelAndView(vm, GetHomeRoute.ROUTE_NAME));
        }

        // Instantiates new game and marks both players as being participants

        Game game = new Game(player1, player2);
        PlayerLobby.getPlayer(player1.getName()).joinGame();
        PlayerLobby.getPlayer(player2.getName()).joinGame();

        // Configures view model for new game
        GameLobby gameLobby = gameCenter.getGameLobby();
        gameLobby.addGame(game);
        Message message = new Message(Message.Type.ERROR, "Text");

        vm.put(GetStartGameRoute.MESSAGE, message);
        vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, player1);
        vm.put(GetStartGameRoute.VIEW_MODE_ATTR, Game.ViewMode.PLAY);
        vm.put(GetStartGameRoute.RED_PLAYER_ATTR, player1);
        vm.put(GetStartGameRoute.WHITE_PLAYER_ATTR, player2);
        vm.put(GetStartGameRoute.ACTIVE_COLOR_ATTR, gameLobby.getGame(player1).getActiveColor());
        vm.put(GetStartGameRoute.BOARD_ATTR, gameLobby.getGameBoard(player1));

        return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));

        // Retrieves the HTTP session and necessary player/game info

//        final Session session = request.session();
//        Player player = session.attribute(PostSignInRoute.PLAYER);
//        PlayerLobby.getPlayer(player.getName()).joinGame();
//        Game game = this.gameCenter.getGameLobby().getGame(player);
//
//        Map<String, Object> vm = new HashMap<>();
//        vm.put(TITLE_ATTR, TITLE);
//
//        // Handles a null game object
//
//        if(game == null){
//            vm.put(TITLE_ATTR, TITLE);
//            vm.put(GetHomeRoute.NUM_PLAYERS, this.gameCenter.getPlayerLobby().getNumberOfPlayers());
//            vm.put(CURRENT_PLAYER_ATTR, player);
//            vm.put(GetHomeRoute.LOBBY_ATTR, this.gameCenter.getPlayerLobby().getPlayerLobby());
//            return templateEngine.render(new ModelAndView(vm, GetHomeRoute.ROUTE_NAME));
//        }
//
//        // Configures view model to set up template based on player and opponent info
//
//        vm.put(BOARD_ATTR, game.getBoard());
//        vm.put(CURRENT_PLAYER_ATTR, player);
//        vm.put(TITLE_ATTR, TITLE);
//        vm.put(VIEW_MODE_ATTR, Game.ViewMode.PLAY);
//        vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
//        vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
//        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());
//        return templateEngine.render(new ModelAndView(vm , GetStartGameRoute.GAME_NAME));
    }
}
