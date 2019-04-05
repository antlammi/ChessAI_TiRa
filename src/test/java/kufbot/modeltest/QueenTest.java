/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeltest;

import kufbot.model.Board;
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
    public void QueenHasAllRookMovesAvailable(){
        
    }
    @Test
    public void QueenHasAllBishopMovesAvailable(){
        
    }
    @Test 
    public void QueenHasNoLegalMovesInitially(){
        
    }
}
