package com.webcheckers.ui;


import com.webcheckers.application.GameCenter;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
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
     *   the rendered HTML for the Signin page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignInRoute is invoked.");
        Session session = request.session();
        Player player = session.attribute(PostSignInRoute.PLAYER);
        session.removeAttribute(PostSignInRoute.PLAYER);
        GameLobby gameLobby = this.gameCenter.getGameLobby();
        PlayerLobby playerLobby = this.gameCenter.getPlayerLobby();
        if(gameLobby.hasGame(player)){
            gameLobby.getGame(player).setLoser(player);
            PlayerLobby.removePlayer(player);
        }
        else if(playerLobby.hasPlayer(player)){
            PlayerLobby.removePlayer(player);
        }
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Signout");
        vm.put(GetHomeRoute.NUM_PLAYERS, playerLobby.getNumberOfPlayers());
        response.redirect(WebServer.HOME_URL);
        return templateEngine.render(new ModelAndView(vm , "signout.ftl"));
    }

}
