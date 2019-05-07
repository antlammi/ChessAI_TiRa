/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enginetest;

import kufbot.engine.MinmaxAB;
import kufbot.model.Board;
import kufbot.model.Color;
import kufbot.model.Piece;
import kufbot.model.Player;
import kufbot.model.Square;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author antlammi
 */
public class PerformanceTest {

    private Square[][] state;
    private MinmaxAB playerW;
    private MinmaxAB playerB;

    public PerformanceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Board board = new Board();
        this.state = board.getBoardState();
        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);
        this.playerW = new MinmaxAB(w, b, state, 2, false, false);
        this.playerB = new MinmaxAB(b, w, state, 2, false, false);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void openingMoveSpeedWhiteDepth1() {
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(1);
        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }

    @Test
    public void openingMoveSpeedWhiteDepth2() {
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(2);
        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }

    @Test
    public void openingMoveSpeedWhiteDepth3() {
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(3);
        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }
    /*
    @Test
    public void openingMoveSpeedWhiteDepth4() {
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(4);
        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }

    
    @Test
    public void openingMoveSpeedWhiteDepth5() {
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(5);
        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }
     */
    @Test
    public void openingMoveSpeedBlackDepth1() {
        Integer count = 0;
        long sum = 0;
        playerW.getMove().execute();
        playerB.update();
        playerB.setMaxDepth(1);

        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerB.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerB.getMaxDepth() + ", average speed: " + avgRounded);
    }

    @Test
    public void openingMoveSpeedBlackDepth2() {
        Integer count = 0;
        long sum = 0;
        playerW.getMove().execute();
        playerB.update();
        playerB.setMaxDepth(2);

        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerB.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerB.getMaxDepth() + ", average speed: " + avgRounded);
    }

    @Test
    public void openingMoveSpeedBlackDepth3() {
        Integer count = 0;
        long sum = 0;
        playerW.getMove().execute();
        playerB.update();
        playerB.setMaxDepth(3);

        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerB.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerB.getMaxDepth() + ", average speed: " + avgRounded);
    }

    /*
    @Test
    public void openingMoveSpeedBlackDepth4() {
        Integer count = 0;
        long sum = 0;
        playerW.getMove().execute();
        playerB.update();
        playerB.setMaxDepth(4);

        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerB.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerB.getMaxDepth() + ", average speed: " + avgRounded);
    }

    
    @Test
    public void openingMoveSpeedBlackDepth5() {
        Integer count = 0;
        long sum = 0;
        playerW.getMove().execute();
        playerB.update();
        playerB.setMaxDepth(5);

        for (int i = 0; i < 10; i++) {

            long initialTimeMinAB = System.currentTimeMillis();
            playerB.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerB.getMaxDepth() + ", average speed: " + avgRounded);
    }
     */
    @Test
    public void midgameScenarioSpeedDepth1() {
        setUpMidgameScenario();
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(1);
        for (int i = 0; i < 10; i++) {
            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }

    @Test
    public void midgameScenarioSpeedDepth2() {
        setUpMidgameScenario();
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(2);
        for (int i = 0; i < 10; i++) {
            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }

    @Test
    public void midgameScenarioSpeedDepth3() {
        setUpMidgameScenario();
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(3);
        for (int i = 0; i < 10; i++) {
            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }

    /*
    @Test
    public void midgameScenarioSpeedDepth4() {
        setUpMidgameScenario();
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(4);
        for (int i = 0; i < 10; i++) {
            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }

    
    @Test
    public void midgameScenarioSpeedDepth5() {
        setUpMidgameScenario();
        Integer count = 0;
        long sum = 0;
        playerW.setMaxDepth(5);
        for (int i = 0; i < 10; i++) {
            long initialTimeMinAB = System.currentTimeMillis();
            playerW.getMove();
            long finalTimeMinAB = System.currentTimeMillis();
            long timeElapsedMinAB = finalTimeMinAB - initialTimeMinAB;
            sum += timeElapsedMinAB;
            System.out.println("AB: " + timeElapsedMinAB);
            count++;
        }
        long avgRounded = sum / count;
        System.out.println("depth: " + playerW.getMaxDepth() + ", average speed: " + avgRounded);
    }
     */
    public void setUpMidgameScenario() {
        Piece p = state[1][6].getPiece();
        state[1][6].leave();
        p.setSquare(state[2][6]);
        state[2][6].enter(p);

        p = state[0][5].getPiece();
        state[0][5].leave();
        p.setSquare(state[1][6]);
        state[1][6].enter(p);

        p = state[1][3].getPiece();
        state[1][3].leave();
        p.setSquare(state[2][3]);
        state[2][3].enter(p);

        p = state[0][2].getPiece();
        state[0][2].leave();
        p.setSquare(state[1][3]);
        state[1][3].enter(p);

        p = state[0][1].getPiece();
        state[0][1].leave();
        p.setSquare(state[2][2]);
        state[2][2].enter(p);

        p = state[0][6].getPiece();
        state[0][6].leave();
        p.setSquare(state[1][4]);
        state[1][4].enter(p);

        p = state[0][4].getPiece();
        state[0][4].leave();
        p.setSquare(state[0][6]);
        state[0][6].enter(p);

        p = state[0][7].getPiece();
        state[0][7].leave();
        p.setSquare(state[0][5]);
        state[0][5].enter(p);

        p = state[7][6].getPiece();
        state[7][6].leave();
        p.setSquare(state[4][2]);
        state[4][2].enter(p);

        p = state[7][2].getPiece();
        state[7][2].leave();
        p.setSquare(state[3][6]);
        state[3][6].enter(p);

        p = state[7][3].getPiece();
        state[7][3].leave();
        p.setSquare(state[5][3]);
        state[5][3].enter(p);

        state[6][3].leave();
        state[1][2].leave();
    }
}
