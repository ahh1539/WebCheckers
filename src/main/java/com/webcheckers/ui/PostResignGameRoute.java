package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostResignGameRoute implements Route {

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
        LOG.config("PostResignGameRoute is initialized.");
    }

    /**
     * Render the WebCheckers Start Game page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return message stating sucessful resignation
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetResignGame is invoked.");
        // Retrieves the HTTP session and necessary player/game info
        final Session session = request.session();
        Player player = session.attribute(PostSignInRoute.PLAYER);

        Gson gson = new Gson();
        Game game = this.gameCenter.getGameLobby().getGame(player);

        //sets winner and loser for game and removes both players
        if(player.getColor() == Color.RED) game.setWinner(game.getWhitePlayer());
        else game.setWinner(game.getRedPlayer());

        if (player.getColor() == Color.WHITE){
            game.setWinner(game.getRedPlayer());
            game.getRedPlayer().leaveGame();
            game.getRedPlayer().hasResigned();
        }
        else {
            game.setWinner(game.getWhitePlayer());
            game.getWhitePlayer().leaveGame();
            game.getWhitePlayer().hasResigned();
        }
        player.hasResigned();
        player.leaveGame();
        gameCenter.getGameLobby().removeGame(player);

        // checks whether or not players successfully left the game
        if (!game.getWhitePlayer().inGame() || !game.getRedPlayer().inGame()){
            Message message = new Message(Message.Type.info, "true");
            return gson.toJson(message);
        } else {
            Message message1 = new Message(Message.Type.error, "false");
            return gson.toJson(message1);
        }
    }
}



