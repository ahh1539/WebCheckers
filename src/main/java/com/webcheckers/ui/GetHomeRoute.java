package com.webcheckers.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Color;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
//import javafx.geometry.Pos;
import spark.*;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    public static final String ROUTE_NAME = "home.ftl";
    public static final String LOBBY_ATTR = "lobby";
    public static final String TITLE_ATTR = "title";
    public static final String TITLE = "Welcome!";

    static final String PLAYER = "currentPlayer";
    static final String PLAYER_LIST = "players";
    static final String GAME_LIST = "games";
    static final String NUM_PLAYERS = "numPlayers";
    static final String ERROR = "errorMsg";
    static final String ERROR_IN_GAME = "The player you have selected is already in a game. Select another player.";

    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetHomeRoute(final TemplateEngine templateEngine, GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(templateEngine, "gameCenter must not be null");
        //
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");
        Session session = request.session();
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);

        PlayerLobby playerLobby = this.gameCenter.getPlayerLobby();
        GameLobby gameLobby = this.gameCenter.getGameLobby();

        //vm.put(PLAYER_LIST, playerLobby.getPlayerLobby()); TODO is this redundant?

        // Allows player to see current players only if signed in
//        if (gameLobby.getGameLobby() != null){
//            vm.put(GAME_LIST, gameLobby.getGameLobby());
//        }
        Player player = session.attribute(PostSignInRoute.PLAYER);
        if (playerLobby.hasPlayer(player)) {
            if (!gameLobby.getGameLobby().isEmpty()){
                vm.put(GAME_LIST, gameLobby.getGameLobby());
            }
            vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, player);
            vm.put(PLAYER_LIST, playerLobby.getPlayerLobby());
            final String username = request.queryParams("opponent");

            // Check if the opponent is not null, then get the player from the PlayerLobby based on their username
            if (username != null) {
                Player opponent = PlayerLobby.getPlayer(username);
                opponent.assignColor(Color.WHITE);

                if (!gameLobby.hasGame(opponent)) {

                    // Add a new game if the opponent is free to play
                    String id1 = player.getName();
                    String id2 = opponent.getName();
                    Game game = new Game(player, opponent, id1 + id2);
                    gameLobby.addGame(game);

                    vm.put(GetStartGameRoute.VIEW_MODE_ATTR, Game.ViewMode.PLAY);
                    vm.put(GetStartGameRoute.RED_PLAYER_ATTR, player);
                    vm.put(GetStartGameRoute.WHITE_PLAYER_ATTR, opponent);
                    vm.put(GetStartGameRoute.ACTIVE_COLOR_ATTR, game.getActiveColor());
                    vm.put(GetStartGameRoute.BOARD_ATTR, gameLobby.getGameBoard(player));

                    return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));

                }

            }
            // If the player is already in a Game
            if (gameLobby.hasGame(player)) {

                Game game = gameLobby.getGame(player);
                player.assignColor(game.getPlayerColor(player.getName()));

                if (!game.hasWinner()) {


                    vm.put(GetStartGameRoute.VIEW_MODE_ATTR, "PLAY");
                    vm.put(GetStartGameRoute.RED_PLAYER_ATTR, game.getRedPlayer());
                    vm.put(GetStartGameRoute.WHITE_PLAYER_ATTR, game.getWhitePlayer());
                    vm.put(GetStartGameRoute.ACTIVE_COLOR_ATTR, game.getActiveColor());
                    vm.put(GetStartGameRoute.BOARD_ATTR, gameLobby.getGameBoard(player));
                    return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));

                }
            }
        }
        // If the player is not logged in, they can only see the number of other players but not a list of them
        else {
            vm.put(NUM_PLAYERS, playerLobby.getNumberOfPlayers());
            vm.put(LOBBY_ATTR, null);
        }

        return templateEngine.render(new ModelAndView(vm, ROUTE_NAME));

    }

}