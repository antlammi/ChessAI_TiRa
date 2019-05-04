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
public class QueenTest {

    private Board board;
    private Square[][] state;

    public QueenTest() {
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
    }

    @After
    public void tearDown() {
    }

    @Test
    public void queenHasNoLegalMovesInitially() {
        Queen queen = (Queen) state[0][3].getPiece();
        Move[] queenLegalMoves = queen.getLegalMoves();

        for (int i = 0; i < queenLegalMoves.length; i++) {
            assertNull(queenLegalMoves[i]);
        }

    }

    @Test
    public void queenHasCorrectMovesWhenAloneInD5() {
        Queen queen = (Queen) state[0][3].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[0][3].enter(queen);
        Move toD4 = new Move(state);
        toD4.constructMove(queen, state[0][3], state[3][3]);
        toD4.execute();

        Move[] possible = queen.getMoves();
        Move[] legal = queen.getLegalMoves();

        for (int i = 0; i < possible.length; i++) {
            assertEquals(possible[i].toString(), legal[i].toString());
        }
    }

    @Test
    public void queenHasCorrectLegalMovesAfterSetOpening() {
        for (int i = 0; i <= 3; i++) {
            Move moveW = new Move(state);
            moveW.constructMove(state[1][2 + i].getPiece(), state[1][2 + i], state[3][2 + i]);

            moveW.execute();

            Move moveB = new Move(state);
            moveB.constructMove(state[6][2 + i].getPiece(), state[6][2 + i], state[4][2 + i]);

            moveB.execute();
        }

        Queen queen = (Queen) state[0][3].getPiece();
        Move[] queenMoves = queen.getLegalMoves();

        String[] correctQueenMoves = {"d1d2", "d1d3", "d1c2", "d1e2", "d1b3", "d1f3", "d1a4", "d1g4", "d1h5"};
        for (int i = 0; i < queenMoves.length; i++) {
            if (queenMoves[i] != null) {
                assertEquals(correctQueenMoves[i].toString(), queenMoves[i].toString());
            }
        }

    }
}
