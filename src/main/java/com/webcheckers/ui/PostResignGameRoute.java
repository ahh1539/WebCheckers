package com.webcheckers.ui;

import com.google.gson.Gson;
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
    static final String GAME = "/game";
    static final String RESIGN_URL = "/resignGame";



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
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     * @return
     *   message stating sucessful resignation
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetResignGame is invoked.");
        System.out.println("RESIGN GAME WAS INVOKED");

        // Retrieves the HTTP session and necessary player/game info
        final Session session = request.session();
        Player player = session.attribute(PostSignInRoute.PLAYER);

        Gson gson = new Gson();
        Game game = this.gameCenter.getGameLobby().getGame(player);

        //sets winner and loser for game and removes both players
        game.setLoser(player);

        if (player.getColor() == Player.Color.WHITE){
            game.setWinner(game.getRedPlayer());
            game.getRedPlayer().leaveGame();
        }
        else {
            game.setWinner(game.getWhitePlayer());
            game.getWhitePlayer().leaveGame();
        }
        player.resigned();
        player.leaveGame();

        // checks whether or not players successfully left the game, returns Json representing this
        if (!game.getWhitePlayer().inGame() || !game.getRedPlayer().inGame()){
            Message message = new Message(Message.Type.INFO, "true");
            String rJson = gson.toJson(message);
            return rJson;
        }
        else {
            Message message1 = new Message(Message.Type.ERROR, "false" );
            String rJson2 = gson.toJson(message1);
            return rJson2;
        }
    }
}



