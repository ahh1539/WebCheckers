package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class GetRequestGameRoute implements Route{
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    public GetRequestGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter){
        this.gameCenter = gameCenter;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Go!");
        final Session session = request.session();
        Player player1 =  session.attribute(PostSignInRoute.PLAYER);
        String secondPlayer = request.queryParams("opponent");
        PlayerLobby playerLobby = gameCenter.getPlayerLobby();
        Player player2 = new Player(secondPlayer);
        if(!playerLobby.hasPlayer(player1)){
            playerLobby.addPlayer(player1);
        }
        if(!playerLobby.hasPlayer(player2)){
            playerLobby.addPlayer(player2);
        }
        Game game = new Game(player1, player2);
        gameCenter.getGameLobby().addGame(game);
        Message message = new Message(Message.Type.ERROR, "Text");
        vm.put(GetStartGameRoute.MESSAGE, message);
        vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, player1);
        vm.put(GetStartGameRoute.VIEW_MODE_ATTR, Game.ViewMode.PLAY);
        vm.put(GetStartGameRoute.RED_PLAYER_ATTR, player1);
        vm.put(GetStartGameRoute.WHITE_PLAYER_ATTR, player2);
        vm.put(GetStartGameRoute.ACTIVE_COLOR_ATTR, this.gameCenter.getGameLobby().getGame(player1).getActiveColor());
        vm.put(GetStartGameRoute.BOARD_ATTR, this.gameCenter.getGameLobby().getGameBoard(player1));
        return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));
    }
}
