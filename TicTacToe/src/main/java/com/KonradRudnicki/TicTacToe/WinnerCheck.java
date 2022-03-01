package com.KonradRudnicki.TicTacToe;

public class WinnerCheck {
    public static FieldEnum winnerCheck(Board board){
        FieldEnum[][] currentBoard = board.getFieldGrid();
        int boardSize = currentBoard.length;
        int neededToWin = 5;
        FieldEnum currentChar = board.getFieldChar();
        FieldEnum result = FieldEnum.EMPTY;

        //iteration
        for(int x = 0; x < boardSize; x++){
            for (int y = 0; y < boardSize; y++){
                //every directrion check
                if (currentBoard[x][y] == currentChar){
                    int row = 1;
                    int column = 1;
                    int diagonal = 1;

                    for (;row <= neededToWin && y + row <= boardSize; row++){
                        if (currentBoard[x][y + row - 1] != currentChar){
                            break;
                        }
                    }

                    for (;column <= neededToWin && x + column <= boardSize; column++){
                        if (currentBoard[x + column - 1][y] != currentChar){
                            break;
                        }
                    }

                    for (;diagonal <= neededToWin && x + diagonal <= boardSize && y + diagonal <= boardSize; diagonal++) {
                        if (currentBoard[x + diagonal - 1][y + diagonal - 1] != currentChar) {
                            break;
                        }
                    }

                    if (row - 1 == neededToWin || column - 1 == neededToWin || diagonal - 1 == neededToWin) {
                        result = currentChar;
                    }
                }


            }
        }

        return result;
    }
}