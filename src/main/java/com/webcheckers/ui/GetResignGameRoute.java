package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class GetResignGameRoute implements Route{

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


    public GetResignGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        // Validation and configuration
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        LOG.config("GetResignGameRoute is initialized.");
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
    public Message handle(Request request, Response response) {
        LOG.finer("GetResignGame is invoked.");

        // Retrieves the HTTP session and necessary player/game info

        final Session session = request.session();
        Player player = session.attribute(PostSignInRoute.PLAYER);


        Game game = this.gameCenter.getGameLobby().getGame(player);

        game.setLoser(player);
        if (player.getColor() == Color.WHITE){
            game.setWinner(game.getRedPlayer());
            game.getRedPlayer().leaveGame();
        }
        else {
            game.setWinner(game.getWhitePlayer());
            game.getWhitePlayer().leaveGame();
        }
        player.leaveGame();

        // Handles a null game object

        response.redirect(WebServer.HOME_URL);
        return new Message(Message.Type.info, "Player sucessfully resigned" );
    }
}



