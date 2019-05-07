/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enginetest;

import kufbot.engine.Engine;
import kufbot.engine.HighestScore;
import kufbot.engine.Random;
import kufbot.model.Bishop;
import kufbot.model.Board;
import kufbot.model.Color;
import kufbot.model.Game;
import kufbot.model.King;
import kufbot.model.Move;
import kufbot.model.Pawn;
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
public class HighestScoreTest {

    private Engine playerW;
    private Engine playerB;
    private Square[][] state;

    public HighestScoreTest() {
    }

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
        this.playerW = new HighestScore(w, b, state);
        this.playerB = new HighestScore(b, w, state);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void winsVsRandomMovesAsWhite() throws InterruptedException {
        Game game = new Game("HighestScore", "Random", null, true, false);
        game.run();

        assertEquals("White wins", game.outcome);
    }

    @Test
    public void winsVsRandomMovesAsBlack() throws InterruptedException {
        Game game = new Game("Random", "HighestScore", null, true, false);
        game.run();

        assertEquals("Black wins", game.outcome);
    }

    @Test
    public void whiteDoesNotHangRookInEndgame() {
        Rook wRook = (Rook) state[0][0].getPiece();
        King bKing = (King) state[7][4].getPiece();
        King wKing = (King) state[0][4].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[1][1].enter(wRook);
        Move toA2 = new Move(state);
        toA2.constructMove(wRook, state[1][1], state[1][0]);
        toA2.execute();

        state[0][3].enter(bKing);
        Move toB2 = new Move(state);
        toB2.constructMove(bKing, state[0][3], state[0][2]);
        toB2.execute();

        state[3][5].enter(wKing);
        Move toE4 = new Move(state);
        toE4.constructMove(wKing, state[3][5], state[3][4]);
        toE4.execute();

        playerW.update();
        playerB.update();

        for (int i = 0; i < 100; i++) {
            Move move = playerW.getMove();
            assertNotEquals("a2b2", move.toString());
            assertNotEquals("a2c2", move.toString());
            assertNotEquals("a2d2", move.toString());
        }

    }
    /* No longer is desired behaviour as required piece evaluations make other AI types worse
    @Test
    public void whiteAdvancesPawnToAdvancePosition(){
        King bKing = (King) state[7][4].getPiece();
        King wKing = (King) state[0][4].getPiece();
        Queen wQueen = (Queen) state[0][3].getPiece();
        Pawn wp1 = (Pawn) state[1][1].getPiece();
        Pawn wp2 = (Pawn) state[1][2].getPiece();
        Bishop wb = (Bishop) state[0][5].getPiece();
        
        for (int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                state[r][f].leave();
            }
        }
        
       state[0][3].enter(bKing);
       Move toC1 = new Move(state);
       toC1.constructMove(bKing, state[0][3], state[0][2]);
       toC1.execute();
       
       state[3][4].enter(wQueen);
       Move toD4 = new Move(state);
       toD4.constructMove(wQueen, state[3][4], state[3][3]);
       toD4.execute();
       
       state[0][5].enter(wKing);
       Move toE1 = new Move(state);
       toE1.constructMove(wKing, state[0][5], state[0][4]);
       toE1.execute();
       
       state[2][4].enter(wp1);
       Move toE2 = new Move(state);
       toE2.constructMove(wp1, state[2][4], state[1][4]);
       toE2.execute();
       
       state[1][7].enter(wp2);
       Move toH3 = new Move(state);
       toH3.constructMove(wp2, state[1][7], state[2][7]);
       toH3.execute();
       
       state[1][6].enter(wb);
       Move toF1 = new Move(state);
       toF1.constructMove(wb, state[1][6], state[0][5]);
       toF1.execute();
       
       playerW.update();
       playerB.update();
       
       Boolean pawnMoveFound = false;
       for (int i=0; i<100; i++){
           Move move = playerW.getMove();
           String[] pawnMoves = {"e2e3", "e2e4", "h3h4"};
           
           for (int j=0; j<pawnMoves.length; j++){
               if (move.toString().equals(pawnMoves[j])){
                   pawnMoveFound=true;
               }
           }
       }
        assertTrue(pawnMoveFound);
    }*/

}
