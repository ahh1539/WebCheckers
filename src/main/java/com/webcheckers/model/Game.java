package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;

import java.util.Objects;

public class Game {

    private Player redPlayer;
    private Player whitePlayer;
    private Player winner;
    private Piece.Color activeColor;
    private BoardView board;
    private PlayerLobby pLobby;

    public Game(){

        this.redPlayer = null;
        this.whitePlayer = null;
        this.activeColor = Piece.Color.RED;
        this.winner = null;
        this.board = new BoardView();
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(String username){
        this.whitePlayer = new Player(username);
    }

    public boolean hasGame(Player player){
        return this.redPlayer.equals(player) | this.whitePlayer.equals(player);
    }

    public boolean hasMove(Player player){
        return false;
    }

    public BoardView getBoard(){
        return this.board;
    }

    public Piece.Color getActiveColor(){
        return this.activeColor;
    }

    public boolean isActive(Player player){
        boolean isRedPlayer = player.equals(this.redPlayer) && this.activeColor.equals(Piece.Color.RED);
        boolean isWhitePlayer = player.equals(this.whitePlayer) && this.activeColor.equals(Piece.Color.WHITE);
        return isRedPlayer || isWhitePlayer;
    }

    public void toggleActiveColor(){
        if(this.activeColor == Piece.Color.RED){
            this.activeColor = Piece.Color.WHITE;
        }
        else{
            this.activeColor = Piece.Color.RED;
        }
    }

    public void setWinner(Player player){
        if(this.redPlayer.equals(player)) this.winner = this.redPlayer;
        else this.winner = this.whitePlayer;
    }

    public void setLoser(Player player){
        if(this.redPlayer.equals(player)) this.winner = this.whitePlayer;
        else this.winner = this.redPlayer;
    }

    public boolean hasWinner(){
        return this.winner != null;
    }

    public Player getWinner() {
        return this.winner;
    }
}
