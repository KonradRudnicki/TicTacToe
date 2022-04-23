package com.KonradRudnicki.TicTacToe;

public class WinnerCheck {
    public static FieldEnum winnerCheck(Board board) {
        FieldEnum[][] currentBoard = board.getFieldGrid();
        int boardSize = currentBoard.length;
        int neededToWin = 5;
        FieldEnum currentChar = board.getFieldChar();
        FieldEnum result = FieldEnum.EMPTY;

        //iteration
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                //every directrion check
                if (currentBoard[x][y] == currentChar) {
                    int row = 1;
                    int column = 1;
                    int diagonalUp = 1;
                    int diagonalDown = 1;

                    for (; row <= neededToWin && y + row <= boardSize; row++) {
                        if (currentBoard[x][y + row - 1] != currentChar) {
                            break;
                        }
                    }

                    for (; column <= neededToWin && x + column <= boardSize; column++) {
                        if (currentBoard[x + column - 1][y] != currentChar) {
                            break;
                        }
                    }

                    if (y <= boardSize - neededToWin + 1) {
                        for (; diagonalDown <= neededToWin && x + diagonalDown <= boardSize && y + diagonalDown <= boardSize; diagonalDown++) {
                            if (currentBoard[x + diagonalDown - 1][y + diagonalDown - 1] != currentChar) {
                                break;
                            }
                        }
                    }

                    if (y >= neededToWin - 1) {
                        for (; diagonalUp <= neededToWin && x + diagonalUp <= boardSize && y + diagonalUp <= boardSize; diagonalUp++) {
                            if (currentBoard[x + diagonalUp - 1][y - diagonalUp + 1] != currentChar) {
                                break;
                            }
                        }
                    }

                    if (row - 1 == neededToWin || column - 1 == neededToWin || diagonalUp - 1 == neededToWin || diagonalDown - 1 == neededToWin) {
                        result = currentChar;
                    }
                }
            }
        }

        return result;
    }
}