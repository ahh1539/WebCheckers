package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
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

    //Allows player to see current players only if signed in
    if(playerLobby.hasPlayer(session.attribute(PostSignInRoute.PLAYER))){
        vm.put("currentPlayer", session.attribute(PostSignInRoute.PLAYER));
        vm.put("players", playerLobby.getPlayerLobby());
    }
    else{
        vm.put("errorMsg", "Sign in to see the list of current players.");
    }
    return templateEngine.render(new ModelAndView(vm, ROUTE_NAME));
  }

}