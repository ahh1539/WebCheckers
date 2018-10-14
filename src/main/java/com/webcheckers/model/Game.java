package com.webcheckers.model;

import java.util.Objects;

public class Game {

    private Player redPlayer;
    private Player whitePlayer;
    private Board board;

    public Game(Player redPlayer, Player whitePlayer){
        Objects.requireNonNull(redPlayer, "redPlayer must not be null");
        Objects.requireNonNull(whitePlayer, "whitePlayer must not be null");

        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new Board();
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public boolean hasGame(Player player){
        return this.redPlayer.equals(player) | this.whitePlayer.equals(player);
    }

    public boolean hasMove(Player player){
        return false;
    }

    public Board getBoard(){
        return this.board;
    }
}