package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostResignGameRoute implements Route{

    private static final Logger LOG = Logger.getLogger(GetStartGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    static final String TITLE_ATTR = "title";
    static final String TITLE = "Game Page";



    public PostResignGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
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
     *   message stating sucessful resignation
     */
    @Override
    public Message handle(Request request, Response response) {
        LOG.finer("GetResignGame is invoked.");

        // Retrieves the HTTP session and necessary player/game info
        final Session session = request.session();
        Player player = session.attribute(PostSignInRoute.PLAYER);


        Game game = this.gameCenter.getGameLobby().getGame(player);

        game.setLoser(player);

        if (player.getColor() == Player.Color.WHITE){
            game.setWinner(game.getRedPlayer());
            game.getRedPlayer().leaveGame();
        }
        else {
            game.setWinner(game.getWhitePlayer());
            game.getWhitePlayer().leaveGame();
        }
        player.leaveGame();

        if (!game.getWhitePlayer().inGame() || !game.getRedPlayer().inGame()){
            return new Message(Message.Type.INFO, "Resignation was a success");
        }
        else {
            return new Message(Message.Type.ERROR, "Did not resign successfully" );
        }
    }
}



