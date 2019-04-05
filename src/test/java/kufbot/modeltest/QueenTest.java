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
    public void queenHasAllRookMovesAvailable(){
        Queen queen = (Queen) state[0][3].getPiece();
        Rook rook = new Rook(queen.getColor(), state[0][3], state);
        Move[] rookmoves = rook.getMoves();
        Move[] queenmoves = queen.getMoves();
        
        for (int i=0; i<rookmoves.length; i++){
            assertEquals(rookmoves[i].toString(), queenmoves[i].toString());
        }
    }
    @Test
    public void queenHasAllBishopMovesAvailable(){
        Queen queen = (Queen) state[0][3].getPiece();
        Bishop bishop = new Bishop(queen.getColor(), state[0][3], state);
        
        Move[] bishopmoves = bishop.getMoves();
        Move[] queenmoves = queen.getMoves();
       
        for (int i=0; i<bishopmoves.length; i++){
            if (bishopmoves[i] != null ||queenmoves[i+14] != null){
                assertEquals(bishopmoves[i].toString(), queenmoves[i+14].toString());
            }
        }
    }
    @Test 
    public void queenHasNoLegalMovesInitially(){
        Queen queen = (Queen) state[0][3].getPiece();
        Move[] queenLegalMoves = queen.getLegalMoves();
        
        for (int i=0; i<queenLegalMoves.length; i++){
            assertNull(queenLegalMoves[i]);
        }
        
       
    }
    
    @Test
    public void queenHasCorrectLegalMovesAfterSetOpening(){
         for (int i=0; i<=3; i++){
            Move moveW = new Move(state);
            moveW.constructMove(state[1][2+i].getPiece(), state[1][2+i], state[3][2+i]);
            
            moveW.execute();
            
            Move moveB = new Move(state);
            moveB.constructMove(state[6][2+i].getPiece(), state[6][2+i], state[4][2+i]);
            
            moveB.execute();
        }
         
         Queen queen = (Queen) state[0][3].getPiece();
         Move[] queenMoves = queen.getLegalMoves();
         
         String[] correctQueenMoves = {"d1d2","d1d3","d1c2", "d1e2","d1b3","d1f3","d1a4","d1g4","d1h5"};
         for (int i=0;i<queenMoves.length; i++){
             if (queenMoves[i] != null){
                 assertEquals(correctQueenMoves[i].toString(), queenMoves[i].toString());
             }
         }
         
    }
}
