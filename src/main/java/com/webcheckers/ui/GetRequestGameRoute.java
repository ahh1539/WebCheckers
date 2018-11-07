package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class GetRequestGameRoute implements Route{
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    public static final String TITLE_ATTR = "title";
    public static final String TITLE = "Go!";

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /requestGame} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetRequestGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter){
        this.gameCenter = gameCenter;
        this.templateEngine = templateEngine;
    }

    /**
     * Handle the Request to start a game
     * @param request
     *      the HTTP request
     * @param response
     *      the HTTP response
     * @return
     *      the correctly rendered template for a game
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        final Session session = request.session();

        // Sets up player and opponent player based on selection

        Player player1 =  session.attribute(PostSignInRoute.PLAYER);
        String secondPlayer = request.queryParams("opponent");

        // Checks if the players are already in the lobby, if not it adds them.
        PlayerLobby playerLobby = gameCenter.getPlayerLobby();
        Player player2 = new Player(secondPlayer);
        player2.assignColor(Color.WHITE);
        if(!playerLobby.hasPlayer(player1)){
            playerLobby.addPlayer(player1);
        }
        if(!playerLobby.hasPlayer(player2)){
            playerLobby.addPlayer(player2);
        }

        // Checks whether the opponent is already playing a game

        if (PlayerLobby.getPlayer(player1.getName()).inGame()|| PlayerLobby.getPlayer(player2.getName()).inGame()){
            String msg = "The player you have selected is already in a game. Select another player.";
            Message message = new Message(Message.Type.ERROR, msg);
            vm.put("errorMsg", msg);
            response.redirect(WebServer.HOME_URL);
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

        vm.put(GetGameRoute.MESSAGE, message);
        vm.put(GetGameRoute.CURRENT_PLAYER_ATTR, player1);
        vm.put(GetGameRoute.VIEW_MODE_ATTR, Game.ViewMode.PLAY);
        vm.put(GetGameRoute.RED_PLAYER_ATTR, player1);
        vm.put(GetGameRoute.WHITE_PLAYER_ATTR, player2);
        vm.put(GetGameRoute.ACTIVE_COLOR_ATTR, gameLobby.getGame(player1).getActiveColor());
        vm.put(GetGameRoute.BOARD_ATTR, gameLobby.getGameBoard(player1));

        return templateEngine.render(new ModelAndView(vm, "/game.ftl"));
    }
}
