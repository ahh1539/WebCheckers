package com.webcheckers.application;

import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;

public class GameLobby {
    private static ArrayList<Game> gameLobby;

    public GameLobby(){
        this.gameLobby = new ArrayList<Game>();
    }

    public void addGame(Game game){
        this.gameLobby.add(game);
    }

    public boolean hasGame(Player player){
        for(Game game : gameLobby){
            if(game.hasGame(player)){
                return true;
            }
        }
        return false;
    }

    public Board getGameBoard(Player player){
        for(Game game : gameLobby){
            if(game.hasGame(player)){
                return game.getBoard();
            }
        }
        return null;
    }

    public Game getGame(Player player){
        for(Game game : gameLobby){
            if(game.hasGame(player)){
                return game;
            }
        }
        return null;
    }

    public void removeGame(Player player){
        gameLobby.remove(getGame(player));
    }

}
