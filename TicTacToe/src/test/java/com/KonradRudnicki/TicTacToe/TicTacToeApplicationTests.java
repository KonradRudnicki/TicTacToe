package com.KonradRudnicki.TicTacToe;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TicTacToeApplicationTests {

	@Test
	void WinnerCheckTest() {
        Board board = new Board();
        FieldEnum[][] defaultBoard = new FieldEnum[3][3];

        for (int i = 0; i < defaultBoard.length; i++) {
            for (int j = 0; j < defaultBoard[i].length; j++) {
                defaultBoard[i][j] = FieldEnum.EMPTY;
            }
        }
        defaultBoard[1][0]= FieldEnum.X;
        defaultBoard[1][1]= FieldEnum.X;
        defaultBoard[1][2]= FieldEnum.X;

        board.setFieldGrid(defaultBoard);

        FieldEnum result =  WinnerCheck.winnerCheck(board);
        Assert.assertEquals(FieldEnum.X,result);
	}

}