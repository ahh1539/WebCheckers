package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetStartGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetStartGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    public GetStartGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
        // Validation and configuration
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        LOG.config("GetHomeRoute is initialized.");
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
    public Object handle(Request request, Response response) {
        LOG.finer("GetStartGameRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Start Game");
        // TODO: make correct ftl file for starting a game
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}
