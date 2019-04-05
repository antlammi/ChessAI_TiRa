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
        this.files = new String[]{ "a", "b", "c", "d", "e", "f", "g", "h" };
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void rookHasCorrectPossibleMovesInitially(){
        Move[] movesForRookA1 = state[0][0].getPiece().getMoves();
        String[] correctInitialMoves = {"a1b1", "a1c1", "a1d1", "a1e1", "a1f1", "a1g1", "a1h1",
                                         "a1a2","a1a3", "a1a4", "a1a5", "a1a6", "a1a7", "a1a8"};
        for (int i=0; i<movesForRookA1.length; i++){
            assertEquals(correctInitialMoves[i], movesForRookA1[i].toString());
        
        }
    }
    
    @Test
    public void rookHasNoLegalMovesInitially(){
        Move[] legalMovesForRookA1 = state[0][0].getPiece().getLegalMoves();
       
        for (int i=0; i<legalMovesForRookA1.length; i++){
            assertEquals(legalMovesForRookA1[i], null);
        
        }
    }
    
    @Test
    public void rookHasCorrectLegalMovesAfterOpeningSequence(){
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
        for (int i=2; i<legalMovesForRookA1.length; i++){
            assertEquals(legalMovesForRookA1[i], null);
        }
        
    }
}
