/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeltest;

import kufbot.model.Board;
import kufbot.model.King;
import kufbot.model.Knight;
import kufbot.model.Move;
import kufbot.model.Pawn;
import kufbot.model.Queen;
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
    public void kingHasNoLegalMovesInitially() {
        King king = (King) state[0][4].getPiece();
        Move[] kingLegalMoves = king.getLegalMoves();

        for (int i = 0; i < kingLegalMoves.length; i++) {
            assertNull(kingLegalMoves[i]);
        }
    }

    @Test
    public void kingHasNotBeenMovedInitially() {
        King king = (King) state[0][4].getPiece();
        assertFalse(king.getMoved());
    }

    @Test
    public void kingHasCorrectMovesInitially() {
        King king = (King) state[0][4].getPiece();
        Move[] kingMoves = king.getMoves();
        String[] correctInitialMoves = {"e1d1", "e1d2", "e1e2", "e1f1", "e1f2"};
        for (int i = 0; i < 5; i++) {
            assertEquals(correctInitialMoves[i], kingMoves[i].toString());
        }

    }

    @Test
    public void kingHasCorrectMovesWhenAloneInTheMiddle() {
        King king = (King) state[0][4].getPiece();
        Move toMiddle = new Move(state);
        toMiddle.constructMove(king, state[0][4], state[3][4]);
        toMiddle.execute();
        String[] correctMoves = {"e4d3", "e4d4", "e4d5", "e4e3", "e4e5", "e4f3", "e4f4", "e4f5"};
        Move[] movesAvailable = king.getLegalMoves();
        for (int i = 0; i < 8; i++) {
            assertEquals(correctMoves[i], movesAvailable[i].toString());
        }

    }

    @Test
    public void kingHasCorrectMovesWhenAloneInSquareG1() {
        King king = (King) state[0][4].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[0][4].enter(king);
        Move toG1 = new Move(state);
        toG1.constructMove(king, state[0][4], state[0][6]);
        toG1.execute();

        String[] correctMoves = {"g1f1", "g1f2", "g1g2", "g1h1", "g1h2"};
        Move[] movesAvailable = king.getLegalMoves();
        for (int i = 0; i < correctMoves.length; i++) {
            assertEquals(correctMoves[i], movesAvailable[i].toString());
        }
        for (int i = correctMoves.length; i < movesAvailable.length; i++) {
            assertNull(movesAvailable[i]);
        }

    }

    @Test
    public void kingHasCorrectMovesInSquareG1WithQueenOnH8() {
        King wKing = (King) state[0][4].getPiece();
        Queen bQueen = (Queen) state[7][3].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
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

        for (int i = 0; i < correctMoves.length; i++) {
            assertEquals(correctMoves[i], movesAvailable[i].toString());
        }
        for (int i = correctMoves.length; i < movesAvailable.length; i++) {
            assertNull(movesAvailable[i]);
        }

    }

    @Test
    public void kingCanCapturePawnOnH4WhileInCheck() {
        King wk = (King) state[0][4].getPiece();
        Knight bkn = (Knight) state[7][1].getPiece();
        Pawn bp = (Pawn) state[6][7].getPiece();
        King bk = (King) state[7][4].getPiece();
        for (int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                state[r][f].leave();
            }
        }
        state[3][6].enter(wk);
        wk.setSquare(state[3][6]);
        
        state[5][5].enter(bkn);
        bkn.setSquare(state[5][5]);
        
        state[3][7].enter(bp);
        bp.setSquare(state[3][7]);
        
        state[1][3].enter(bk);
        bk.setSquare(state[1][3]);
        
        Move[] kingmoves = wk.getLegalMoves();
        Boolean moveAvailable = false;
        for (int i=0; i<kingmoves.length; i++){
            if (kingmoves[i] == null){
                break;
            }
            if (kingmoves[i].toString().equals("g4h4")){
                moveAvailable = true;
            }
        }
        assertTrue(moveAvailable);
    }

    @Test
    public void kingIsInCheckScenario1() {
        King wKing = (King) state[0][4].getPiece();
        Queen bQueen = (Queen) state[7][3].getPiece();
        Pawn bPawn = (Pawn) state[6][7].getPiece();
        King bKing = (King) state[7][4].getPiece();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[0][4].enter(wKing);
        Move toH3 = new Move(state);
        toH3.constructMove(wKing, state[0][4], state[2][7]);
        toH3.execute();

        state[7][3].enter(bQueen);
        Move toG3 = new Move(state);
        toG3.constructMove(bQueen, state[7][3], state[2][6]);
        toG3.execute();

        state[6][7].enter(bPawn);
        Move toH4 = new Move(state);
        toH4.constructMove(bPawn, state[6][7], state[3][7]);
        toH4.execute();

        state[7][4].enter(bKing);
        Move toG5 = new Move(state);
        toG5.constructMove(bKing, state[7][4], state[4][6]);
        toG5.execute();
        Move[] movesAvailable = wKing.getLegalMoves();
        for (int i = 0; i < movesAvailable.length; i++) {
            assertNull(movesAvailable[i]);
        }

        assertEquals(true, wKing.isInCheck(state[2][7]));

    }

    @Test
    public void kingCanCastleKingside() {
        King wKing = (King) state[0][4].getPiece();
        Rook rookH1 = (Rook) state[0][7].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[0][4].enter(wKing);
        state[0][7].enter(rookH1);

        Boolean containsCastle = false;
        Move[] kingmoves = wKing.getLegalMoves();

        for (int i = 0; i < kingmoves.length; i++) {
            if (kingmoves[i] == null) {
                break;
            }
            if (kingmoves[i].toString().equals("e1h1")) {
                containsCastle = true;
            }
        }
        assert (containsCastle);
    }

    @Test
    public void kingCanCastleQueenside() {
        King wKing = (King) state[0][4].getPiece();
        Rook rookA1 = (Rook) state[0][0].getPiece();

        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                state[r][f].leave();
            }
        }

        state[0][4].enter(wKing);
        state[0][0].enter(rookA1);

        Boolean containsCastle = false;
        Board.printStateGraphic(state);
        Move[] kingmoves = wKing.getLegalMoves();

        for (int i = 0; i < kingmoves.length; i++) {
            System.out.println(kingmoves[i]);
            if (kingmoves[i] == null) {
                break;
            }
            if (kingmoves[i].toString().equals("e1a1")) {
                containsCastle = true;
            }
        }
        assert (containsCastle);
    }
    @Test
    public void kingCanCastleQueensideWithPiecesInPlay() {
        King wKing = (King) state[0][4].getPiece();
        Rook rookA1 = (Rook) state[0][0].getPiece();

        for (int r = 0; r < 6; r++) {
            for (int f = 0; f < 6; f++) {
                state[r][f].leave();
            }
        }

        state[0][4].enter(wKing);
        state[0][0].enter(rookA1);

        Boolean containsCastle = false;
        Board.printStateGraphic(state);
        Move[] kingmoves = wKing.getLegalMoves();

        for (int i = 0; i < kingmoves.length; i++) {
            System.out.println(kingmoves[i]);
            if (kingmoves[i] == null) {
                break;
            }
            if (kingmoves[i].toString().equals("e1a1")) {
                containsCastle = true;
            }
        }
        assert (containsCastle);
    }
}
