/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeltest;

import kufbot.model.Board;
import kufbot.model.King;
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
public class KingTest {
    private Board board;
    private Square[][] state;
    public KingTest() {
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
    public void kingHasNoLegalMovesInitially(){
        King king = (King) state[0][4].getPiece();
        Move[] kingLegalMoves = king.getLegalMoves();
        
        for (int i=0; i<kingLegalMoves.length; i++){
            assertNull(kingLegalMoves[i]);
        }
    }
    
    @Test
    public void kingHasNotBeenMovedInitially(){
        King king = (King) state[0][4].getPiece();
        assertFalse(king.getMoved());
    }
    @Test 
    public void kingHasCorrectMovesInitially(){
        King king = (King) state[0][4].getPiece();
        Move[] kingMoves = king.getMoves();
        String[] correctInitialMoves = {"e1d1","e1d2","e1e2","e1f1","e1f2"};
        for (int i=0; i<5; i++){
            assertEquals(correctInitialMoves[i],kingMoves[i].toString());
        }
        
    }
}
