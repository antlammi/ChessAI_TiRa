/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeltest;

import kufbot.model.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author antlammi
 */
public class RookTest {

    private Board board;
    private Square[][] state;
    private String[] files;

    public RookTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.board = new Board();
        this.state = board.getBoardState();
        this.files = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
    }

    @After
    public void tearDown() {
    }

    @Test
    public void rookHasNoLegalMovesInitially() {
        Move[] legalMovesForRookA1 = state[0][0].getPiece().getLegalMoves();

        for (int i = 0; i < legalMovesForRookA1.length; i++) {
            assertEquals(legalMovesForRookA1[i], null);

        }
    }

    @Test
    public void blackRookHasCorrectLegalMovesAfterOpeningSequence() {
        Move firstw = new Move(this.state);
        firstw.constructMove(state[1][4].getPiece(), state[1][4], state[3][4]);
        firstw.execute();

        Move firstb = new Move(this.state);
        firstb.constructMove(state[6][7].getPiece(), state[6][7], state[4][7]);
        firstb.execute();

        Move secondw = new Move(this.state);
        secondw.constructMove(state[1][0].getPiece(), state[1][0], state[3][0]);
        secondw.execute();

        Move secondb = new Move(this.state);
        secondb.constructMove(state[6][6].getPiece(), state[6][6], state[5][6]);
        secondb.execute();

        Move thirdw = new Move(this.state);
        thirdw.constructMove(state[0][0].getPiece(), state[0][0], state[2][0]);
        thirdw.execute();

        Move[] legalMovesForRookH8 = state[7][7].getPiece().getLegalMoves();
        assertEquals(legalMovesForRookH8[0].toString(), "h8h7");
        assertEquals(legalMovesForRookH8[1].toString(), "h8h6");
        

    }

    @Test
    public void whiteRookHasCorrectLegalMovesAfterOpeningSequence() {
        Move firstw = new Move(this.state);
        firstw.constructMove(state[1][4].getPiece(), state[1][4], state[3][4]);
        firstw.execute();

        Move firstb = new Move(this.state);
        firstb.constructMove(state[6][2].getPiece(), state[6][2], state[4][2]);
        firstb.execute();

        Move secondw = new Move(this.state);
        secondw.constructMove(state[1][0].getPiece(), state[1][0], state[3][0]);
        secondw.execute();

        Move secondb = new Move(this.state);
        secondb.constructMove(state[6][3].getPiece(), state[6][3], state[4][3]);
        secondb.execute();

        Move[] legalMovesForRookA1 = state[0][0].getPiece().getLegalMoves();

        assertEquals(legalMovesForRookA1[0].toString(), "a1a2");
        assertEquals(legalMovesForRookA1[1].toString(), "a1a3");
        for (int i = 2; i < legalMovesForRookA1.length; i++) {
            assertNull(legalMovesForRookA1[i]);
        }

    }
}
