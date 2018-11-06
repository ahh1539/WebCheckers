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

public class GetGameRoute implements Route {

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


    public GetGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        // Validation and configuration
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Render the WebCheckers Game page.
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

        /*
        This action returns the current state of the game.

        This Route is used whenever the Game View transitions from one player to the next. So when the active
        player submits their turn, a successful response to this Ajax request results in the browser rerendering
        GET /game. Likewise, when a player is waiting for their turn the Game View client-side code will
        periodically check with the server to see if it's their turn. When the response is true then the
        browser code redirects to the GET /game Route so the page refreshes with the move(s) their opponent
        just submitted. The client-side code handles these redirections.

        This Route must also handle the use case when one player has resigned or the game has reached a standard
        end-game condition. When the request is made, if there is a Game but it has ended, this Route must
        redirect the player back to the Home page with a message that informs the player how the game ended.
         */

        LOG.finer("GetGameRoute is invoked.");

        // Retrieves the HTTP session and necessary player/game info

        final Session session = request.session();
        Player player = session.attribute(PostSignInRoute.PLAYER);
        PlayerLobby.getPlayer(player.getName()).joinGame();
        Game game = this.gameCenter.getGameLobby().getGame(player);




        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        System.out.println("===========================================");
        // Handles a null game object
        if (player.resigned() == true){
            System.out.println("I was invoked");
            vm.put(MESSAGE, "Game is over fool");
            return templateEngine.render(new ModelAndView(vm, GetHomeRoute.ROUTE_NAME));
        }
        //if (game.hasWinner()){
        //    response.redirect(WebServer.HOME_URL);
        //}
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        // Configures view model to set up template based on player and opponent info
        vm.put(BOARD_ATTR, game.getRedBoard());
        vm.put(BOARD_ATTR, game.getWhiteBoard());
        vm.put(CURRENT_PLAYER_ATTR, player);
        vm.put(TITLE_ATTR, TITLE);
        vm.put(VIEW_MODE_ATTR, Game.ViewMode.PLAY);
        vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
        vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());
        return templateEngine.render(new ModelAndView(vm , GetStartGameRoute.GAME_NAME));
    }
}

