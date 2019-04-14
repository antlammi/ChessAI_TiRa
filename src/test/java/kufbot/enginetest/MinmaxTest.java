/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enginetest;

import kufbot.engine.Engine;
import kufbot.engine.Minmax;
import kufbot.model.Board;
import kufbot.model.Color;
import kufbot.model.King;
import kufbot.model.Knight;
import kufbot.model.Move;
import kufbot.model.Pawn;
import kufbot.model.Piece;
import kufbot.model.Player;
import kufbot.model.Square;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author antlammi
 */
public class MinmaxTest {
    private Square[][] state;
    private Minmax playerW;
    private Minmax playerB;
    public MinmaxTest() {
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
        this.playerW = new Minmax(w,b, state);
        this.playerB = new Minmax(b,w,state);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void doesNotThrowClassCastExceptionScenario1(){
        King bKing = (King) state[7][4].getPiece();
        King wKing = (King) state[0][4].getPiece();
        Pawn wp1 = (Pawn) state[1][0].getPiece();
        Pawn wp2 = (Pawn) state[1][1].getPiece();
        Pawn bp = (Pawn) state[6][0].getPiece();
        Knight wKnight = (Knight) state[0][1].getPiece();
        
        for (int r=0; r<8; r++){
            for (int f=0; f<8; f++){
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
        
        playerW.update();
        
        playerW.getMove();
        assert(true); //Only purpose of the test is to see if an exception is thrown, if this line is reached it was not.
        
        
    }
    
    @Test
    public void doesNotHangQueenScenario1(){
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
        
        Minmax min = new Minmax(w,b,state);
        min.printStateGraphic(state);
        min.setMaxDepth(3);
        
        String[] incorrectMoves = {"d2b4", "d2b5", "d2h6"};
        Boolean incorrectMoveMade = false;
        for (int i=0; i<50; i++){
            Move move = min.getMove();
            System.out.println(move);
            for (int j=0; j<incorrectMoves.length; j++){
                if (move.toString().equals(incorrectMoves[j])){
                    incorrectMoveMade = true;
                    break;
                }
            }
            
        }
        assertFalse(incorrectMoveMade);
    }
}
