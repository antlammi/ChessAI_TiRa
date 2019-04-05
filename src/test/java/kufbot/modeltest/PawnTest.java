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
public class PawnTest {
    private Board board;
    private Square[][] state;
    private String[] files;
    public PawnTest() {
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
    public void whitePawnHasCorrectPossibleMovesInitially(){
        Move[] movesForPawn = state[1][4].getPiece().getMoves();
        String[] correctInitialMoves = {"e2d3", "e2e3", "e2f3", "e2e4"};
        for (int i=0; i<movesForPawn.length; i++){
            assertEquals(correctInitialMoves[i], movesForPawn[i].toString());
        }
        
    }
    @Test
    public void blackPawnHasCorrectPossibleMovesInitially(){
        Move[] movesForPawn = state[6][4].getPiece().getMoves();
        String[] correctInitialMoves = {"e7d6", "e7e6", "e7f6", "e7e5"};
        for (int i=0; i<movesForPawn.length; i++){
            assertEquals(correctInitialMoves[i], movesForPawn[i].toString());
        } 
    }
    @Test
    public void whitePawnHasCorrectLegalMovesInitially(){
        Move[] legalMovesForPawn = state[1][4].getPiece().getLegalMoves();
        String[] correctInitialMoves = {"e2e3", "e2e4"};
        assertEquals(correctInitialMoves[0], legalMovesForPawn[0].toString());
        assertEquals(correctInitialMoves[1], legalMovesForPawn[1].toString());
        for (int i=2; i<legalMovesForPawn.length; i++){
            assertEquals(legalMovesForPawn[i], null);
        }
    }
    
    @Test
    public void whitePawnHasCorrectLegalMovesAfterOpening(){
        Move firstw = new Move(this.state);
        firstw.constructMove(state[1][4].getPiece(), state[1][4], state[3][4]);
        firstw.execute();
        
        Move firstb = new Move(this.state);
        firstb.constructMove(state[6][3].getPiece(), state[6][3], state[4][3]);
        firstb.execute();
        
        String[] correctLegalMovesAfterOpening = {"e4d5","e4e5"};
        Move[] legalMovesForPawn = state[3][4].getPiece().getLegalMoves();
        
        assertEquals(correctLegalMovesAfterOpening[0], legalMovesForPawn[0].toString());
        assertEquals(correctLegalMovesAfterOpening[1], legalMovesForPawn[1].toString());
        for (int i=2; i<legalMovesForPawn.length; i++){
            assertEquals(legalMovesForPawn[i], null);
        }
    }
    @Test
    public void blackPawnHasCorrectLegalMovesAfterOpening(){
        Move firstw = new Move(this.state);
        firstw.constructMove(state[1][4].getPiece(), state[1][4], state[3][4]);
        firstw.execute();
        
        Move firstb = new Move(this.state);
        firstb.constructMove(state[6][3].getPiece(), state[6][3], state[4][3]);
        firstb.execute();
        
        Move secondW = new Move(this.state);
        secondW.constructMove(state[1][0].getPiece(), state[1][0], state[3][0]);
        secondW.execute();
        
        String[] correctLegalMovesAfterOpening = {"d5d4","d5e4"};
        Move[] legalMovesForPawn = state[4][3].getPiece().getLegalMoves();
        
        assertEquals(correctLegalMovesAfterOpening[0], legalMovesForPawn[0].toString());
        assertEquals(correctLegalMovesAfterOpening[1], legalMovesForPawn[1].toString());
        for (int i=2; i<legalMovesForPawn.length; i++){
            assertEquals(legalMovesForPawn[i], null);
        }
    }
    
    @Test
    public void whitePawnCannotMoveOverAnotherPieceAfterOpening(){
        Move firstw = new Move(this.state);
        firstw.constructMove(state[0][6].getPiece(), state[0][6], state[2][5]);
        firstw.execute();
        
        Move firstb = new Move(this.state);
        firstb.constructMove(state[6][3].getPiece(), state[6][3], state[4][3]);
        firstb.execute();
        
        
        Move[] legalMovesForPawn = state[1][5].getPiece().getLegalMoves();
        
        for (int i=0; i<legalMovesForPawn.length; i++){
            if (legalMovesForPawn[i] != null){
                assertTrue(!legalMovesForPawn[i].toString().equals("f2f4"));
            }
        }
        
        
    }
    
    @Test
    public void blackPawnCannotMoveOverAnotherPieceAfterOpening(){
        Move firstw = new Move(this.state);
        firstw.constructMove(state[1][4].getPiece(), state[1][4], state[3][4]);
        firstw.execute();
        
        Move firstb = new Move(this.state);
        firstb.constructMove(state[7][6].getPiece(), state[7][6], state[5][5]);
        firstb.execute();
        
       Move secondw = new Move(this.state);
       secondw.constructMove(state[3][4].getPiece(), state[3][4], state[4][4]);
        
        
        Move[] legalMovesForPawn = state[7][5].getPiece().getLegalMoves();
        
        for (int i=0; i<legalMovesForPawn.length; i++){
            if (legalMovesForPawn[i] != null){
                assertTrue(!legalMovesForPawn[i].toString().equals("f8f6"));
            }
        }
        
        
    }
}
