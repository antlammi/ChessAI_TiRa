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
public class BoardTest {
    private Board board;
    private Square[][] initialState;
    public BoardTest() {
        
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
        this.initialState = board.getBoardState();
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void BoardInitializedWithPawnsInCorrectLocations(){  
        int r = 1;
        for (int f=0; f<=7; f++){
            assertEquals("WHITE PAWN", initialState[r][f].toString());
        }
        r=6;
        for (int f=0; f<=7; f++){
            assertEquals("BLACK PAWN", initialState[r][f].toString());
        }
    }
        
    @Test
    public void BoardInitializedWithEmptySquaresInCorrectLocations(){
        for (int r=3; r<6; r++){
            for (int f=0; f<=7; f++){
                assertEquals("EMPTY", initialState[r][f].toString());
            }
        }
    }
    @Test
    public void BoardInitializedWithRooksInCorrectLocations(){
        assertEquals("WHITE ROOK", initialState[0][0].toString());
        assertEquals("WHITE ROOK", initialState[0][7].toString());
        assertEquals("BLACK ROOK", initialState[7][0].toString());
        assertEquals("BLACK ROOK", initialState[7][7].toString());
    }
    
    @Test
    public void BoardInitializedWithKnightsInCorrectLocations(){
        assertEquals("WHITE KNIGHT", initialState[0][1].toString());
        assertEquals("WHITE KNIGHT", initialState[0][6].toString());
        assertEquals("BLACK KNIGHT", initialState[7][1].toString());
        assertEquals("BLACK KNIGHT", initialState[7][6].toString());
    }
    @Test
    public void BoardInitializedWithBishopsInCorrectLocations(){
        assertEquals("WHITE BISHOP", initialState[0][2].toString());
        assertEquals("WHITE BISHOP", initialState[0][5].toString());
        assertEquals("BLACK BISHOP", initialState[7][2].toString());
        assertEquals("BLACK BISHOP", initialState[7][5].toString());
    }
    
    @Test
    public void BoardInitializedWithQueensInCorrectLocations(){
        assertEquals("WHITE QUEEN", initialState[0][3].toString());
        assertEquals("BLACK QUEEN", initialState[7][3].toString());
    }
    @Test
    public void BoardInitializedWithKingsInCorrectLocations(){
        assertEquals("WHITE KING", initialState[0][4].toString());
        assertEquals("BLACK KING", initialState[7][4].toString());
    }
}
