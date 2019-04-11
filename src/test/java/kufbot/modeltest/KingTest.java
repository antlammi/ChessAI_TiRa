/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeltest;

import kufbot.model.Board;
import kufbot.model.Color;
import kufbot.model.King;
import kufbot.model.Move;
import kufbot.model.PieceFactory;
import kufbot.model.Queen;
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
    private PieceFactory pf;
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
        this.pf = new PieceFactory();
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
    @Test
    public void kingHasCorrectMovesWhenAloneInTheMiddle(){
        King king = (King) state[0][4].getPiece();
        Move toMiddle = new Move(state);
        toMiddle.constructMove(king, state[0][4], state[3][4]);
        toMiddle.execute();
        String[] correctMoves = {"e4d3","e4d4", "e4d5", "e4e3", "e4e5", "e4f3", "e4f4", "e4f5"};
        Move[] movesAvailable = king.getLegalMoves();
        for (int i=0; i<8; i++){
            assertEquals(correctMoves[i], movesAvailable[i].toString());
        }
        
    }
    
    @Test
    public void kingHasCorrectMovesWhenAloneInSquareG1(){
        King king = (King) state[0][4].getPiece();
        for (int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                state[r][f].leave();
            }
        }
        
        state[0][4].enter(king);
        Move toG1 = new Move(state);
        toG1.constructMove(king, state[0][4], state[0][6]);
        toG1.execute();
        
        String[] correctMoves = {"g1f1", "g1f2", "g1g2", "g1h1", "g1h2"};
        Move[] movesAvailable = king.getLegalMoves();
        for (int i=0; i<correctMoves.length; i++){
            assertEquals(correctMoves[i], movesAvailable[i].toString());
        }
        for (int i=correctMoves.length; i<movesAvailable.length; i++){
            assertNull(movesAvailable[i]);
        }
        
    }
    
     @Test
    public void kingHasCorrectMovesInSquareG1WithQueenOnH8(){
        King wKing = (King) state[0][4].getPiece();
        Queen bQueen = (Queen) state[7][3].getPiece();
        for (int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                state[r][f].leave();
            }
        }
       
        state[0][4].enter(wKing);
        Move toG1 = new Move(state);
        toG1.constructMove(wKing, state[0][4], state[0][6]);
        toG1.execute();
        
        state[7][3].enter(bQueen);
        Move toE8 = new Move(state);
        toE8.constructMove(bQueen, state[7][3], state[7][7]);
        toE8.execute();
        
        String[] correctMoves = {"g1f1", "g1f2", "g1g2"};
        Move[] movesAvailable = wKing.getLegalMoves();
     
        for (int i=0; i<correctMoves.length; i++){
            assertEquals(correctMoves[i], movesAvailable[i].toString());
        }
        for (int i=correctMoves.length; i<movesAvailable.length; i++){
            assertNull(movesAvailable[i]);
        }
        
    }
}
