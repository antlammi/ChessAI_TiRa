/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeltest;

import kufbot.model.Board;
import kufbot.model.Knight;
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
public class KnightTest {
    private Board board;
    private Square[][] state;
    public KnightTest() {
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
    public void knightHasCorrectLegalOpeningMoves(){
        Knight knight = (Knight) state[0][6].getPiece();
        Move[] knightLegalMoves = knight.getLegalMoves();
        
        String[] correctInitialMoves = {"g1f3","g1h3"};
        
        assertEquals(knightLegalMoves[0].toString(), correctInitialMoves[0]);
        assertEquals(knightLegalMoves[1].toString(), correctInitialMoves[1]);
        for (int i=2; i<knightLegalMoves.length; i++){
            assertNull(knightLegalMoves[i]);
        }
    }
}
