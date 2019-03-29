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
    public void pawnHasCorrectPossibleMovesInitially(){
        Move[] movesForPawn = state[1][4].getPiece().getMoves(state[1][4], state);
        String[] correctInitialMoves = {"e2d3", "e2e3", "e2f3", "e2e4"};
        for (int i=0; i<movesForPawn.length; i++){
            assertEquals(correctInitialMoves[i], movesForPawn[i].toString());
        }
    }
    
    @Test
    public void pawnHasCorrectLegalMovesInitially(){
        Move[] legalMovesForPawn = state[1][4].getPiece().getLegalMoves(state[1][4], state);
        String[] correctInitialMoves = {"e2e3", "e2e4"};
        assertEquals(correctInitialMoves[0], legalMovesForPawn[0].toString());
        assertEquals(correctInitialMoves[1], legalMovesForPawn[1].toString());
        for (int i=2; i<legalMovesForPawn.length; i++){
            assertEquals(legalMovesForPawn[i], null);
        }
    }
    
    @Test
    public void pawnHasCorrectLegalMovesAfterOpening(){
        Move firstw = new Move(this.state);
        firstw.constructMove(state[1][4].getPiece(), state[1][4], state[3][4]);
        firstw.execute();
        
        Move firstb = new Move(this.state);
        firstb.constructMove(state[6][3].getPiece(), state[6][3], state[4][3]);
        firstb.execute();
        
        String[] correctLegalMovesAfterOpening = {"e4d5","e4e5"};
        Move[] legalMovesForPawn = state[3][4].getPiece().getLegalMoves(state[3][4], state);
        
        assertEquals(correctLegalMovesAfterOpening[0], legalMovesForPawn[0].toString());
        assertEquals(correctLegalMovesAfterOpening[1], legalMovesForPawn[1].toString());
        for (int i=2; i<legalMovesForPawn.length; i++){
            assertEquals(legalMovesForPawn[i], null);
        }
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
