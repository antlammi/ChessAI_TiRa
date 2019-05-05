/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeltest;

import kufbot.model.Bishop;
import kufbot.model.Board;
import kufbot.model.Color;
import kufbot.model.King;
import kufbot.model.Move;
import kufbot.model.Pawn;
import kufbot.model.Player;
import kufbot.model.Rook;
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
public class PlayerTest {

    private Board board;
    private Square[][] state;

    public PlayerTest() {
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
    public void whitePlayerHasCorrectLegalMovesInitially() {
        Player white = new Player(Color.WHITE, state);
        Move[] openingMoves = white.getLegalMoves();
        String[] correctMoves = {"b1a3", "b1c3", "g1f3", "g1h3",
            "a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
            "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4"
        };
        for (int i = 0; i < openingMoves.length; i++) {
            assertEquals(correctMoves[i], openingMoves[i].toString());
        }
    }

    @Test
    public void blackPlayerHasCorrectLegalMovesInitially() {
        Player black = new Player(Color.BLACK, state);
        Move[] openingMoves = black.getLegalMoves();
        String[] correctMoves = {
            "b8a6", "b8c6", "g8f6", "g8h6",
            "a7a6", "a7a5", "b7b6", "b7b5", "c7c6", "c7c5", "d7d6", "d7d5",
            "e7e6", "e7e5", "f7f6", "f7f5", "g7g6", "g7g5", "h7h6", "h7h5",
            
        };
        for (int i = 0; i < openingMoves.length; i++) {
            assertEquals(correctMoves[i], openingMoves[i].toString());
        }
    }

    @Test
    public void whitePlayerCannotMakeMovesThatPutOwnKingInCheck1() {
        Player white = new Player(Color.WHITE, state);
        Rook bRook = (Rook) state[7][7].getPiece();
        Bishop wBishop = (Bishop) state[0][2].getPiece();
        King wKing = (King) state[0][4].getPiece();
        King bKing = (King) state[7][4].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[7][7].enter(bRook);    //setup scenario on board
        Move toE8 = new Move(state);
        toE8.constructMove(bRook, state[7][7], state[7][4]);
        toE8.execute();

        state[0][2].enter(wBishop);
        Move toE3 = new Move(state);
        toE3.constructMove(wBishop, state[0][2], state[2][4]);
        toE3.execute();

        state[0][5].enter(wKing);
        Move toE1 = new Move(state);
        toE1.constructMove(wKing, state[0][5], state[0][4]);
        toE1.execute();

        state[7][6].enter(bKing);
        Move toF8 = new Move(state);
        toF8.constructMove(bKing, state[7][6], state[7][5]);
        toF8.execute();

        white.updatePlayer();
        Move[] legalMoves = white.getLegalMoves();
        Move[] possibleMoves = white.getPossibleMoves();
        Boolean moveContained = false;
        String move = "e3f4";
        for (int i = 0; i < possibleMoves.length; i++) {
            if (possibleMoves[i].toString().equals(move)) {
                moveContained = true;
            }
        }
        assert (moveContained);
        for (int i = 0; i < legalMoves.length; i++) {

            assertNotEquals(move, legalMoves[i]);

        }
    }

    @Test
    public void blackPlayerCannotMakeMovesThatPutOwnKingInCheck1() {
        Player black = new Player(Color.BLACK, state);
        Rook wRook = (Rook) state[0][0].getPiece();
        Bishop bBishop = (Bishop) state[7][5].getPiece();
        King wKing = (King) state[0][4].getPiece();
        King bKing = (King) state[7][4].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }
        state[0][0].enter(wRook);    //setup scenario on board
        Move toE1 = new Move(state);
        toE1.constructMove(wRook, state[0][0], state[0][4]);
        toE1.execute();

        state[7][5].enter(bBishop);
        Move toE7 = new Move(state);
        toE7.constructMove(bBishop, state[7][5], state[6][4]);
        toE7.execute();

        state[0][6].enter(wKing);
        Move toF1 = new Move(state);
        toF1.constructMove(wKing, state[0][6], state[0][5]);
        toF1.execute();

        state[7][3].enter(bKing);
        Move toE8 = new Move(state);
        toE8.constructMove(bKing, state[7][3], state[7][4]);
        toE8.execute();
        black.updatePlayer();
        Move[] legalMoves = black.getLegalMoves();
        Move[] possibleMoves = black.getPossibleMoves();
        Boolean moveContained = false;
        String move = "e7f8";
        for (int i = 0; i < possibleMoves.length; i++) {
            if (possibleMoves[i].toString().equals(move)) {
                moveContained = true;
            }
        }
        assert (moveContained);
        for (int i = 0; i < legalMoves.length; i++) {
            assertNotEquals(move, legalMoves[i]);
        }
    }
    @Test
    public void whitePawnCannotCaptureKingOnSameFileThroughPlayerClassScenario1() {
        Pawn wp = (Pawn) state[1][4].getPiece();
        King bKing = (King) state[7][4].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[4][4].enter(wp);
        wp.setSquare(state[4][4]);

        state[5][4].enter(bKing);
        bKing.setSquare(state[5][4]);
        Player black = new Player(Color.BLACK, state);
        
        Move[] moves = black.getLegalMoves();

        for (int i = 0; i < moves.length; i++) {
            if (moves[i] != null) {
                assertNotEquals("e4e5", moves[i].toString());
            }
        }
    }
    @Test
    public void whitePawnCannotCaptureKingOnSameFileThroughPlayerClassScenario2() {
        Pawn wp = (Pawn) state[1][4].getPiece();
        King bKing = (King) state[7][4].getPiece();
        King wKing = (King) state[0][4].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[4][4].enter(wp);
        wp.setSquare(state[4][4]);

        state[5][4].enter(bKing);
        bKing.setSquare(state[5][4]);
        
        state[2][5].enter(wKing);
        wKing.setSquare(state[5][4]);
        
        Player black = new Player(Color.BLACK, state);
        
        Move[] moves = black.getLegalMoves();

        for (int i = 0; i < moves.length; i++) {
            if (moves[i] != null) {
                assertNotEquals("e4e5", moves[i].toString());
            }
        }
    }


    @Test
    public void doesNotThrowNullPointerExceptionFollowingCastling(){
        Rook wr = (Rook) state[0][7].getPiece();
        King wk = (King) state[0][4].getPiece();
        King bk = (King) state[7][4].getPiece();
        for (int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                state[r][f].leave();
            }
        }
        
        state[0][7].enter(wr);
        wr.setSquare(state[0][7]);
        
        state[0][4].enter(wk);
        wk.setSquare(state[0][4]);
        
        state[7][4].enter(bk);
        bk.setSquare(state[7][4]);
        
        Player white = new Player(Color.WHITE, state);
        
        Move move = new Move(state);
        move.constructCastle(wk, wr, state[0][4], state[0][7]);
        move.execute();
        white.updatePlayer();
        white.getLegalMoves();
        
        assert(true);   //if reached, exception was not thrown
    }
}
