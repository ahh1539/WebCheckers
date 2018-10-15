package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import javafx.geometry.Pos;
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

  static final String PLAYER = "currentPlayer";
  static final String PLAYER_LIST = "players";
  static final String NUM_PLAYERS = "numPlayers";

  private final TemplateEngine templateEngine;
  private final GameCenter gameCenter;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
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
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    Session session = request.session();
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    PlayerLobby playerLobby = this.gameCenter.getPlayerLobby();
    vm.put("players", playerLobby.getPlayerLobby());

    // Allows player to see current players only if signed in

    if(playerLobby.hasPlayer(session.attribute(PostSignInRoute.PLAYER))){
        Player player = session.attribute(PostSignInRoute.PLAYER);
        vm.put(GetStartGameRoute.CURRENT_PLAYER_ATTR, player);
        vm.put(PLAYER_LIST, playerLobby.getPlayerLobby());
        final String username = request.queryParams("opponent");
      System.out.println("Opp: ");
      System.out.println(username);
      System.out.println(player.getUsername());
        if(username != null){
          Player opponent = PlayerLobby.getPlayer(username);
          if(!this.gameCenter.getGameLobby().hasGame(opponent)){
            this.gameCenter.getGameLobby().addGame(new Game(player, opponent));
            vm.put(GetStartGameRoute.VIEW_MODE_ATTR, "PLAY");
            vm.put(GetStartGameRoute.RED_PLAYER_ATTR, player);
            vm.put(GetStartGameRoute.WHITE_PLAYER_ATTR, opponent);
            vm.put(GetStartGameRoute.ACTIVE_COLOR_ATTR, this.gameCenter.getGameLobby().getGame(player).getActiveColor());
            vm.put(GetStartGameRoute.BOARD_ATTR, this.gameCenter.getGameLobby().getGameBoard(player));
            return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));
          }
        }
        if(this.gameCenter.getGameLobby().hasGame(player)){
          if(!this.gameCenter.getGameLobby().getGame(player).hasWinner()){
            vm.put(GetStartGameRoute.VIEW_MODE_ATTR, "PLAY");
            vm.put(GetStartGameRoute.RED_PLAYER_ATTR, this.gameCenter.getGameLobby().getGame(player).getRedPlayer());
            vm.put(GetStartGameRoute.WHITE_PLAYER_ATTR, this.gameCenter.getGameLobby().getGame(player).getWhitePlayer());
            vm.put(GetStartGameRoute.ACTIVE_COLOR_ATTR, this.gameCenter.getGameLobby().getGame(player).getActiveColor());
            vm.put(GetStartGameRoute.BOARD_ATTR, this.gameCenter.getGameLobby().getGameBoard(player));
            return templateEngine.render(new ModelAndView(vm, GetStartGameRoute.GAME_NAME));
          }
        }
    } else {
      vm.put(NUM_PLAYERS, playerLobby.getNumberOfPlayers());
      vm.put(LOBBY_ATTR, null);
    }

    return templateEngine.render(new ModelAndView(vm, ROUTE_NAME));
  }

}