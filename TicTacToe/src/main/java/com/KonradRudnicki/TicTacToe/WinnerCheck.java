package com.KonradRudnicki.TicTacToe;

public class WinnerCheck {
    public static FieldEnum winnerCheck(Board board){
        FieldEnum[][] currentBoard = board.getFieldGrid();
        int boardSize = currentBoard.length;
        int neededToWin = 3;
        FieldEnum currentChar = board.getFieldChar();
        FieldEnum result = FieldEnum.EMPTY;

        //iteration
        for(int x = 0; x < boardSize; x++){
            for (int y = 0; y < boardSize; y++){
                //every directrion check
                if (currentBoard[x][y] == currentChar){
                    int row = 1;
//                    int column = 1;
//                    int diagonal = 1;

                    for (;row <= neededToWin && y + row <= boardSize; row++){
                        if (currentBoard[x][y + row - 1] != currentChar){
                            break;
                        }
                    }
                    if (row == neededToWin) {
                        result = currentChar;
                    }
                }


            }
        }

        return result;
    }
}