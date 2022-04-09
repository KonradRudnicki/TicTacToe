package com.KonradRudnicki.TicTacToe;

public class GameStatus {
    Board board;
    FieldEnum gameStatus;

    public GameStatus(Board board, FieldEnum gameStatus) {
        this.board = board;
        this.gameStatus = gameStatus;
    }

    public Board getBoard() {
        return board;
    }

    public FieldEnum getGameStatus() {
        return gameStatus;
    }
}