/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeltest;

import kufbot.model.Bishop;
import kufbot.model.Board;
import kufbot.model.King;
import kufbot.model.Knight;
import kufbot.model.Move;
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
    public void knightHasCorrectLegalOpeningMoves() {
        Knight knight = (Knight) state[0][6].getPiece();
        Move[] knightLegalMoves = knight.getLegalMoves();

        String[] correctInitialMoves = {"g1f3", "g1h3"};

        assertEquals(knightLegalMoves[0].toString(), correctInitialMoves[0]);
        assertEquals(knightLegalMoves[1].toString(), correctInitialMoves[1]);
        for (int i = 2; i < knightLegalMoves.length; i++) {
            assertNull(knightLegalMoves[i]);
        }
    }
    @Test
    public void knightCanCaptureQueenOnE7(){
        Knight bkn = (Knight) state[7][1].getPiece();
        King bk = (King) state[7][4].getPiece();
        Queen wq = (Queen) state[0][3].getPiece();
        Bishop wb = (Bishop) state[0][2].getPiece();
        for (int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                state[r][f].leave();
            }
        }
        
        state[5][2].enter(bkn);
        bkn.setSquare(state[5][2]);
        
        state[7][4].enter(bk);
        bk.setSquare(state[7][4]);
        
        state[6][4].enter(wq);
        wq.setSquare(state[0][3]);
        
        state[5][5].enter(wb);
        wb.setSquare(state[5][5]);
        
        Move[] moves = bkn.getLegalMoves();
        Boolean moveExists = false;
        
        for (int i=0; i<moves.length; i++){
            System.out.println(moves[i]);
            if (moves[i] == null){
                break;
            }
            if (moves[i].toString().equals("c6e7")){
                moveExists = true;
                break;
            }
        }
        assertTrue(moveExists);
    }
}
