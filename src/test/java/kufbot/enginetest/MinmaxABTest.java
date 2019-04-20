/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enginetest;

import kufbot.engine.Minmax;
import kufbot.engine.MinmaxAB;
import kufbot.model.Bishop;
import kufbot.model.Board;
import kufbot.model.Color;
import kufbot.model.King;
import kufbot.model.Knight;
import kufbot.model.Move;
import kufbot.model.Pawn;
import kufbot.model.Piece;
import kufbot.model.Player;
import kufbot.model.Queen;
import kufbot.model.Rook;
import kufbot.model.Square;
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
public class MinmaxABTest {

    public MinmaxABTest() {
    }
    private Square[][] state;
    private MinmaxAB playerW;
    private MinmaxAB playerB;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Board board = new Board();
        this.state = board.getBoardState();
        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);
        this.playerW = new MinmaxAB(w, b, state, 2, false);
        this.playerB = new MinmaxAB(b, w, state, 2, false);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void doesNotThrowClassCastExceptionScenario1() {
        King bKing = (King) state[7][4].getPiece();
        King wKing = (King) state[0][4].getPiece();
        Pawn wp1 = (Pawn) state[1][0].getPiece();
        Pawn wp2 = (Pawn) state[1][1].getPiece();
        Pawn bp = (Pawn) state[6][0].getPiece();
        Knight wKnight = (Knight) state[0][1].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[6][5].enter(bKing);
        Move toF7 = new Move(state);
        toF7.constructMove(bKing, state[6][5], state[6][5]);
        toF7.execute();

        state[2][5].enter(wKing);
        Move toF3 = new Move(state);
        toF3.constructMove(wKing, state[2][5], state[2][5]);
        toF3.execute();

        state[4][4].enter(wp1);
        Move toE5 = new Move(state);
        toE5.constructMove(wp1, state[4][4], state[4][4]);
        toE5.execute();

        state[2][1].enter(wp2);
        Move toB3 = new Move(state);
        toB3.constructMove(wp2, state[2][1], state[2][1]);
        toB3.execute();

        state[3][1].enter(bp);
        Move toB4 = new Move(state);
        toB4.constructMove(wp2, state[3][1], state[3][1]);
        toB4.execute();

        state[0][1].enter(wKnight);
        Move toB1 = new Move(state);
        toB1.constructMove(wp2, state[0][1], state[0][1]);
        toB1.execute();

        playerW.getPlayer().updatePlayer();

        playerW.getMove();
        assert (true); //Only purpose of the test is to see if an exception is thrown, if this line is reached it was not.

    }

    @Test
    public void doesNotHangQueenScenario1Depth2() { //Sees that by capturing pawn on a5 it will get captured by the rook
        Piece one = state[1][0].getPiece();
        state[1][0].leave();
        state[3][0].enter(one);
        one.setSquare(state[3][0]);

        Piece two = state[6][0].getPiece();
        state[6][0].leave();
        state[4][0].enter(two);
        two.setSquare(state[4][0]);

        Piece three = state[1][3].getPiece();
        state[1][3].leave();
        state[3][3].enter(three);
        three.setSquare(state[3][3]);

        Piece four = state[6][3].getPiece();
        state[6][3].leave();
        state[4][3].enter(four);
        four.setSquare(state[4][3]);

        Piece five = state[0][3].getPiece();
        state[0][3].leave();
        state[1][3].enter(five);
        five.setSquare(state[1][3]);

        Piece six = state[6][5].getPiece();
        state[6][5].leave();
        state[4][5].enter(six);
        six.setSquare(state[4][5]);

        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);

        Minmax min = new Minmax(w, b, state, 2, false);
        Board.printStateGraphic(state);

        String[] incorrectMoves = {"d2a5", "d2b4", "d2b5", "d2h6"};
        Boolean incorrectMoveMade = false;
        for (int i = 0; i < 1; i++) {
            Move move = min.getMove();
            System.out.println(move);
            for (int j = 0; j < incorrectMoves.length; j++) {
                if (move.toString().equals(incorrectMoves[j])) {
                    incorrectMoveMade = true;
                    break;
                }
            }

        }
        assertFalse(incorrectMoveMade);
    }

    @Test
    public void hangsQueenScenario1Depth1() {    //Only sees material gain by capturing pawn so it is considered best move
        Piece one = state[1][0].getPiece();
        state[1][0].leave();
        state[3][0].enter(one);
        one.setSquare(state[3][0]);

        Piece two = state[6][0].getPiece();
        state[6][0].leave();
        state[4][0].enter(two);
        two.setSquare(state[4][0]);

        Piece three = state[1][3].getPiece();
        state[1][3].leave();
        state[3][3].enter(three);
        three.setSquare(state[3][3]);

        Piece four = state[6][3].getPiece();
        state[6][3].leave();
        state[4][3].enter(four);
        four.setSquare(state[4][3]);

        Piece five = state[0][3].getPiece();
        state[0][3].leave();
        state[1][3].enter(five);
        five.setSquare(state[1][3]);

        Piece six = state[6][5].getPiece();
        state[6][5].leave();
        state[4][5].enter(six);
        six.setSquare(state[4][5]);

        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);

        Minmax min = new Minmax(w, b, state, 1, false);

        String[] incorrectMoves = {"d2a5", "d2b4", "d2b5", "d2h6"};
        Boolean incorrectMoveMade = false;
        for (int i = 0; i < 1; i++) {
            Move move = min.getMove();
            for (int j = 0; j < incorrectMoves.length; j++) {
                if (move.toString().equals(incorrectMoves[j])) {
                    incorrectMoveMade = true;
                    break;
                }
            }

        }
        assertTrue(incorrectMoveMade);
    }

    @Test
    public void doesNotHangQueenScenario3Depth3() { //fringe scenario where queen was hang, did not re-occur. Lowered variance in minmaxAB should fix.
        Piece p = state[1][6].getPiece();
        state[1][6].leave();
        p.setSquare(state[2][6]);
        state[2][6].enter(p);

        p = state[0][5].getPiece();
        state[0][5].leave();
        p.setSquare(state[1][6]);
        state[1][6].enter(p);

        p = state[1][3].getPiece();
        state[1][3].leave();
        p.setSquare(state[2][3]);
        state[2][3].enter(p);

        p = state[0][2].getPiece();
        state[0][2].leave();
        p.setSquare(state[1][3]);
        state[1][3].enter(p);

        p = state[0][1].getPiece();
        state[0][1].leave();
        p.setSquare(state[2][2]);
        state[2][2].enter(p);

        p = state[0][6].getPiece();
        state[0][6].leave();
        p.setSquare(state[1][4]);
        state[1][4].enter(p);

        p = state[0][4].getPiece();
        state[0][4].leave();
        p.setSquare(state[0][6]);
        state[0][6].enter(p);

        p = state[0][7].getPiece();
        state[0][7].leave();
        p.setSquare(state[0][5]);
        state[0][5].enter(p);

        p = state[7][6].getPiece();
        state[7][6].leave();
        p.setSquare(state[4][2]);
        state[4][2].enter(p);

        p = state[7][2].getPiece();
        state[7][2].leave();
        p.setSquare(state[3][6]);
        state[3][6].enter(p);

        p = state[7][3].getPiece();
        state[7][3].leave();
        p.setSquare(state[5][3]);
        state[5][3].enter(p);

        state[6][3].leave();
        state[1][2].leave();

        playerW.update();
        playerB.update();
        playerB.setMaxDepth(3);
        for (int i = 0; i < 3; i++) {
            Move move = playerB.getMove();
            assertNotEquals("d6f4",move.toString());
        }
    }

    @Test
    public void doesNotHangQueenScenario2Depth4() {
        /*Finding the move took 29395 milliseconds.
7: 
h1g1
| A  | B  | C  | D  | E  | F  | G  | H  |
| BR |    | BB |    | BK |    | BKN| BR | 8

| BP | BP | BP | BP |    | BP | BP | BP | 7

|    |    |    |    |    |    |    |    | 6

|    |    | BB |    | BP |    |    |    | 5

|    | BKN|    |    |    |    |    |    | 4

|    |    |    |    | WP | BQ | WP | WP | 3

| WP | WP | WP | WP | WKN| WP |    |    | 2

| WR | WKN| WB | WQ | WK | WB | WR |    | 1

Finding the move took 62053 milliseconds.
7: 
f3g4
| A  | B  | C  | D  | E  | F  | G  | H  |
| BR |    | BB |    | BK |    | BKN| BR | 8

| BP | BP | BP | BP |    | BP | BP | BP | 7

|    |    |    |    |    |    |    |    | 6

|    |    | BB |    | BP |    |    |    | 5

|    | BKN|    |    |    |    | BQ |    | 4

|    |    |    |    | WP |    | WP | WP | 3

| WP | WP | WP | WP | WKN| WP |    |    | 2

| WR | WKN| WB | WQ | WK | WB | WR |    | 1*/
    }

    @Test
    public void doesNotThrowArrayIndexOutOfBoundsExceptionScenario1() {

        Queen wq = (Queen) state[0][3].getPiece();
        Rook wr = (Rook) state[0][0].getPiece();
        Knight wkn = (Knight) state[0][1].getPiece();
        Bishop wb = (Bishop) state[0][2].getPiece();
        Pawn wp1 = (Pawn) state[1][0].getPiece();
        Pawn wp2 = (Pawn) state[1][3].getPiece();
        Pawn wp3 = (Pawn) state[1][4].getPiece();
        King wk = (King) state[0][4].getPiece();
        Knight bkn = (Knight) state[7][6].getPiece();
        Rook br = (Rook) state[7][7].getPiece();
        Pawn bp1 = (Pawn) state[6][7].getPiece();
        Pawn bp2 = (Pawn) state[6][6].getPiece();
        King bk = (King) state[7][4].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[7][0].enter(wq);
        wq.setSquare(state[7][0]);

        state[4][1].enter(wr);
        wr.setSquare(state[4][1]);

        state[2][0].enter(wkn);
        wkn.setSquare(state[2][0]);

        state[4][4].enter(wb);
        wb.setSquare(state[4][4]);

        state[1][0].enter(wp1);
        wp1.setSquare(state[1][0]);

        state[1][3].enter(wp2);
        wp2.setSquare(state[1][3]);

        state[1][4].enter(wp3);
        wp3.setSquare(state[1][4]);

        state[1][2].enter(wk);
        wk.setSquare(state[1][2]);

        state[7][6].enter(bkn);
        bkn.setSquare(state[7][6]);

        state[4][7].enter(br);
        br.setSquare(state[4][7]);

        state[5][6].enter(bp1);
        bp1.setSquare(state[5][6]);

        state[3][6].enter(bp2);
        bp2.setSquare(state[3][6]);

        state[4][2].enter(bk);
        bk.setSquare(state[4][2]);

        playerB.getMove();
        assert (true); //if reached, no exception was thrown

    }

    @Test
    public void findsCastlingMoveScenario1() { //Move with castling is mate in one so if the engine is capable of finding it, it will.

        Bishop wb1 = (Bishop) state[0][2].getPiece();
        King wk = (King) state[0][4].getPiece();
        Rook wr = (Rook) state[0][7].getPiece();
        Knight wkn = (Knight) state[0][1].getPiece();
        Pawn wp = (Pawn) state[1][6].getPiece();
        Bishop bb = (Bishop) state[7][2].getPiece();
        Pawn bp1 = (Pawn) state[6][4].getPiece();
        Pawn bp2 = (Pawn) state[6][7].getPiece();
        Pawn bp3 = (Pawn) state[6][6].getPiece();
        Pawn bp4 = (Pawn) state[6][5].getPiece();
        King bk = (King) state[7][4].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[3][0].enter(wb1);
        wb1.setSquare(state[3][0]);

        state[3][3].enter(wkn);
        wkn.setSquare(state[3][3]);

        state[0][4].enter(wk);
        wk.setSquare(state[0][4]);

        state[0][7].enter(wr);
        wr.setSquare(state[0][7]);

        state[1][6].enter(wp);
        wp.setSquare(state[1][6]);

        state[6][4].enter(bp1);
        bp1.setSquare(state[6][4]);

        state[6][7].enter(bp2);
        bp2.setSquare(state[6][7]);

        state[5][6].enter(bp3);
        bp3.setSquare(state[5][6]);

        state[6][6].enter(bp4);
        bp4.setSquare(state[6][6]);

        state[7][6].enter(bb);
        bb.setSquare(state[7][6]);

        state[6][5].enter(bk);
        bk.setSquare(state[6][5]);
        Board.printStateGraphic(state);

        playerW.update();
        Move move = playerW.getMove();
        assertEquals("e1h1", move.toString());

    }

    @Test
    public void findsCastlingMoveScenario2() {
        Pawn wp1 = (Pawn) state[1][5].getPiece();
        Pawn wp2 = (Pawn) state[1][6].getPiece();
        Pawn wp3 = (Pawn) state[1][7].getPiece();
        Rook wr = (Rook) state[0][7].getPiece();
        King wk = (King) state[0][4].getPiece();
        King bk = (King) state[7][4].getPiece();
        Bishop bb = (Bishop) state[7][2].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[1][5].enter(wp1);
        wp1.setSquare(state[1][5]);

        state[2][6].enter(wp2);
        wp2.setSquare(state[2][6]);

        state[1][7].enter(wp3);
        wp3.setSquare(state[1][7]);

        state[0][7].enter(wr);
        wr.setSquare(state[0][7]);

        state[0][4].enter(wk);
        wk.setSquare(state[0][4]);

        state[0][7].enter(wr);
        wp1.setSquare(state[0][7]);

        state[2][5].enter(bb);
        bb.setSquare(state[2][5]);

        state[7][4].enter(bk);
        bk.setSquare(state[7][4]);

        Board.printStateGraphic(state);
        playerW.update();
        Move move = playerW.getMove();
        assertEquals("e1h1", move.toString());

    }

    @Test
    public void findMateInOneScenario1Depth1() {
        Rook wr = (Rook) state[0][7].getPiece();
        Pawn wp = (Pawn) state[1][4].getPiece();
        King wk = (King) state[0][4].getPiece();
        Queen wq = (Queen) state[0][3].getPiece();
        King bk = (King) state[7][4].getPiece();
        Queen bq = (Queen) state[7][3].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[4][1].enter(wr);
        wr.setSquare(state[4][1]);

        state[6][1].enter(wq);
        wq.setSquare(state[6][1]);

        state[1][2].enter(wk);
        wk.setSquare(state[1][2]);

        state[1][4].enter(wp);
        wp.setSquare(state[1][4]);

        state[3][0].enter(bk);
        bk.setSquare(state[3][0]);

        state[1][5].enter(bq);
        bq.setSquare(state[1][5]);

        playerW.update();
        playerW.setMaxDepth(1);

        Move move = playerW.getMove();

        assertEquals("b7a6", move.toString());
    }

    @Test
    public void findMateInOneScenario1Depth2() {
        Rook wr = (Rook) state[0][7].getPiece();
        Pawn wp = (Pawn) state[1][4].getPiece();
        King wk = (King) state[0][4].getPiece();
        Queen wq = (Queen) state[0][3].getPiece();
        King bk = (King) state[7][4].getPiece();
        Queen bq = (Queen) state[7][3].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[4][1].enter(wr);
        wr.setSquare(state[4][1]);

        state[6][1].enter(wq);
        wq.setSquare(state[6][1]);

        state[1][2].enter(wk);
        wk.setSquare(state[1][2]);

        state[1][4].enter(wp);
        wp.setSquare(state[1][4]);

        state[3][0].enter(bk);
        bk.setSquare(state[3][0]);

        state[1][5].enter(bq);
        bq.setSquare(state[1][5]);

        playerW.update();
        playerW.setMaxDepth(2);

        Move move = playerW.getMove();

        assertEquals("b7a6", move.toString());
    }

    @Test
    public void findMateInOneScenario1Depth3() {
        Rook wr = (Rook) state[0][7].getPiece();
        Pawn wp = (Pawn) state[1][4].getPiece();
        King wk = (King) state[0][4].getPiece();
        Queen wq = (Queen) state[0][3].getPiece();
        King bk = (King) state[7][4].getPiece();
        Queen bq = (Queen) state[7][3].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[4][1].enter(wr);
        wr.setSquare(state[4][1]);

        state[6][1].enter(wq);
        wq.setSquare(state[6][1]);

        state[1][2].enter(wk);
        wk.setSquare(state[1][2]);

        state[1][4].enter(wp);
        wp.setSquare(state[1][4]);

        state[3][0].enter(bk);
        bk.setSquare(state[3][0]);

        state[1][5].enter(bq);
        bq.setSquare(state[1][5]);

        playerW.update();
        playerW.setMaxDepth(3);

        Move move = playerW.getMove();

        assertEquals("b7a6", move.toString());
    }

    @Test
    public void findMateInOneScenario1Depth4() {
        Rook wr = (Rook) state[0][7].getPiece();
        Pawn wp = (Pawn) state[1][4].getPiece();
        King wk = (King) state[0][4].getPiece();
        Queen wq = (Queen) state[0][3].getPiece();
        King bk = (King) state[7][4].getPiece();
        Queen bq = (Queen) state[7][3].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[4][1].enter(wr);
        wr.setSquare(state[4][1]);

        state[6][1].enter(wq);
        wq.setSquare(state[6][1]);

        state[1][2].enter(wk);
        wk.setSquare(state[1][2]);

        state[1][4].enter(wp);
        wp.setSquare(state[1][4]);

        state[3][0].enter(bk);
        bk.setSquare(state[3][0]);

        state[1][5].enter(bq);
        bq.setSquare(state[1][5]);

        playerW.update();
        playerW.setMaxDepth(4);

        Move move = playerW.getMove();

        assertEquals("b7a6", move.toString());
    }

    /* @Test
    public void findMateInOneScenario2(){
Move made: 
21: e2c4 
I assume that the mate in one was not found because a forced mate was found as a followup to the move
e2c4 -- does this even need to be fixed?
| A  | B  | C  | D  | E  | F  | G  | H  |
|    |    |    |    |    | BR | BK |    | 8

|    |    |    |    |    |    |    | BP | 7

|    |    |    |    |    |    |    |    | 6

| BP |    |    |    | WQ | WKN| BP |    | 5

|    | BP |    | WP |    |    |    |    | 4

|    |    |    |    | WP |    |    |    | 3

| WP | WP | WP |    | WB | WK | WP | WP | 2

| WR |    |    |    |    |    |    | WR | 1
}*/
    @Test
    public void doesNotFindMateInTwoScenario1Depth2() {
        Rook wr = (Rook) state[0][7].getPiece();
        King wk = (King) state[0][4].getPiece();
        Queen wq = (Queen) state[0][3].getPiece();
        King bk = (King) state[7][4].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }
        state[7][3].enter(bk);
        bk.setSquare(state[7][3]);

        state[1][6].enter(wk);
        wk.setSquare(state[1][6]);

        state[5][0].enter(wq);
        wq.setSquare(state[5][0]);

        state[3][1].enter(wr);
        wr.setSquare(state[3][1]);

        playerW.update();
        playerW.setMaxDepth(2);
        Move move = playerW.getMove();

        assertNotEquals("b4b7", move.toString());

    }

    @Test
    public void findsMateInTwoScenario1Depth3() {
        Rook wr = (Rook) state[0][7].getPiece();
        King wk = (King) state[0][4].getPiece();
        Queen wq = (Queen) state[0][3].getPiece();
        King bk = (King) state[7][4].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }
        state[7][3].enter(bk);
        bk.setSquare(state[7][3]);

        state[1][6].enter(wk);
        wk.setSquare(state[1][6]);

        state[5][0].enter(wq);
        wq.setSquare(state[5][0]);

        state[3][1].enter(wr);
        wr.setSquare(state[3][1]);

        playerW.update();
        playerW.setMaxDepth(3);
        Move move = playerW.getMove();

        assertEquals("b4b7", move.toString());

    }

    @Test
    public void findsMateInTwoScenario1Depth4() {
        Rook wr = (Rook) state[0][7].getPiece();
        King wk = (King) state[0][4].getPiece();
        Queen wq = (Queen) state[0][3].getPiece();
        King bk = (King) state[7][4].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }
        state[7][3].enter(bk);
        bk.setSquare(state[7][3]);

        state[1][6].enter(wk);
        wk.setSquare(state[1][6]);

        state[5][0].enter(wq);
        wq.setSquare(state[5][0]);

        state[3][1].enter(wr);
        wr.setSquare(state[3][1]);

        playerW.update();
        playerW.setMaxDepth(4);
        Move move = playerW.getMove();

        assertEquals("b4b7", move.toString());

    }

    @Test
    public void findsOpeningMoveFasterThanMinmaxDepth2() {
        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);
        Minmax min = new Minmax(w, b, state, playerW.getMaxDepth(), false);

        long initialTimeMin = System.currentTimeMillis();

        min.getMove();

        long finalTimeMin = System.currentTimeMillis();
        long timeElapsedMin = finalTimeMin - initialTimeMin;

        long initialTimeMinAB = System.currentTimeMillis();
        playerW.getMove();
        long finalTimeMinAB = System.currentTimeMillis();
        long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
        System.out.println("AB: " + timeElapsedMinAB);
        System.out.println("Min" + timeElapsedMin);
        assert (timeElapsedMinAB < timeElapsedMin);

    }

    @Test
    public void findsOpeningMoveFasterThanMinmaxDepth3() {
        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);
        playerW.setMaxDepth(3);
        Minmax min = new Minmax(w, b, state, playerW.getMaxDepth(), false);

        long initialTimeMin = System.currentTimeMillis();

        min.getMove();

        long finalTimeMin = System.currentTimeMillis();
        long timeElapsedMin = finalTimeMin - initialTimeMin;

        long initialTimeMinAB = System.currentTimeMillis();
        playerW.getMove();
        long finalTimeMinAB = System.currentTimeMillis();
        long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
        System.out.println("AB: " + timeElapsedMinAB);
        System.out.println("Min" + timeElapsedMin);
        assert (timeElapsedMinAB < timeElapsedMin);

    }

    /* Commented out by default as running it takes up to two minutes currently.
    @Test
    public void findsOpeningMoveFasterThanMinmaxDepth4() {
        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);
        playerW.setMaxDepth(4);
        Minmax min = new Minmax(w, b, state, playerW.getMaxDepth(), false);
        
        long initialTimeMin = System.currentTimeMillis();

        min.getMove();

        long finalTimeMin = System.currentTimeMillis();
        long timeElapsedMin = finalTimeMin - initialTimeMin;

        long initialTimeMinAB = System.currentTimeMillis();
        playerW.getMove();
        long finalTimeMinAB = System.currentTimeMillis();
        long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
        System.out.println("AB: "+ timeElapsedMinAB);
        System.out.println("Min" + timeElapsedMin);
        assert (timeElapsedMinAB < timeElapsedMin);
    }
     */
}
