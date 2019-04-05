/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeltest;

import kufbot.model.Board;
import kufbot.model.Move;
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
public class BishopTest {
    private Board board;
    private Square[][] state;
    private String[] files;
    public BishopTest() {
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
        this.files = new String[]{ "a", "b", "c", "d", "e", "f", "g", "h" };
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void BishopHasCorrectPossibleMovesInitially(){
        Move[] movesForBishopF1 = state[0][5].getPiece().getMoves();
        String[] correctInitialMoves = {"f1e2","f1g2","f1d3","f1h3","f1c4","f1b5","f1a6"};
        for (int i=0; i<7; i++){
            assertEquals(correctInitialMoves[i], movesForBishopF1[i].toString());
        
        }
        for (int i=7; i<movesForBishopF1.length; i++){
            assertEquals(movesForBishopF1[i], null);
        }
    }
    
    
    @Test
    public void BishopHasNoLegalMovesInitially(){
        Move[] legalMovesForBishopF1 = state[0][5].getPiece().getLegalMoves();
        for (int i=0; i<legalMovesForBishopF1.length; i++){
            assertEquals(legalMovesForBishopF1[i], null);
        
        }
    }
    
    @Test
    public void BishopHasCorrectLegalMovesAfterOpeningSequence(){
        Move firstw = new Move(this.state);
        firstw.constructMove(state[1][4].getPiece(), state[1][4], state[3][4]);
        firstw.execute();
        
        Move firstb = new Move(this.state);
        firstb.constructMove(state[6][2].getPiece(), state[6][2], state[4][2]);
        firstb.execute();
        
        Move[] movesForBishopF1 = state[0][5].getPiece().getLegalMoves();
        String[] correctInitialMoves = {"f1e2" ,"f1d3", "f1c4", "f1b5", "f1a6"};
        for (int i=0; i<5; i++){ 
            assertEquals(correctInitialMoves[i], movesForBishopF1[i].toString());
        
        }
        for (int i=5; i<movesForBishopF1.length; i++){
            assertEquals(movesForBishopF1[i], null);
        }
        
    }
}
