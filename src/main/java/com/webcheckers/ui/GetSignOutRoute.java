package com.webcheckers.ui;


import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class GetSignOutRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetStartGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    public static final String TITLE_ATTR = "title";
    public static final String TITLE = "Signout";
    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignOutRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        // Validation and configuration
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        //
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        LOG.config("GetSignOutRoute is initialized.");
    }

    /**
     * Render the WebCheckers SignOut page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     * @return
     *   the rendered HTML for the SignOut page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignOutRoute is invoked.");
        Session session = request.session();
        Map<String, Object> vm = new HashMap<>();

        // Get the player and remove them from the attributes
        Player player = session.attribute(PostSignInRoute.PLAYER);
        session.removeAttribute(PostSignInRoute.PLAYER);

        // Remove the player from a Game, if they are in one by setting them as the loser. Remove from playerLobby
        GameLobby gameLobby = this.gameCenter.getGameLobby();
        PlayerLobby playerLobby = this.gameCenter.getPlayerLobby();
        // If the player was in a game
        if (gameLobby.hasGame(player)) {
            Game game = gameLobby.getGame(player);
            Player opponent = player.getColor() == Color.RED ? game.getWhitePlayer():game.getRedPlayer();

            // The player loses and the opponent wins
            game.setWinner(opponent);
            response.redirect(WebServer.RESIGN_GAME_URL);

            vm.put(GetHomeRoute.ROUTE_NAME, opponent);

            // Both players leave the game
            opponent.leaveGame();
            player.leaveGame();
            gameLobby.removeGame(player);

            // The player leaves the playerLobby
            PlayerLobby.removePlayer(player);
        } else if (playerLobby.hasPlayer(player)) {
            PlayerLobby.removePlayer(player);
        }

        vm.put(TITLE_ATTR, TITLE);
        vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, null);
        vm.put(GetHomeRoute.NUM_PLAYERS, playerLobby.getNumberOfPlayers());

        return templateEngine.render(new ModelAndView(vm, GetHomeRoute.ROUTE_NAME));
    }

}
