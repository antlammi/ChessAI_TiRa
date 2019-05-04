/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.model;

import kufbot.engine.Random;
import java.util.concurrent.TimeUnit;
import kufbot.engine.Engine;
import kufbot.engine.HighestScore;
import kufbot.engine.Human;
import kufbot.engine.Minmax;
import kufbot.engine.MinmaxAB;

/**
 *
 * @author antlammi
 */
public class Game {

    private Engine playerW;
    private Engine playerB;
    private Square[][] state;
    private Boolean fastSim;

    /**
     *
     */
    public String outcome;

    /**
     * 
     * @param engineTypeW The type of Engine used by White
     * @param engineTypeB The type of Engine used by Black
     * @param initialdepth Initial search depth for Minmax and MinmaxAB if used
     * @param fastSim Only include prints for results
     * @param dynamicdepth Adjust search depth in Engines dynamically based on search time
     */
    public Game(String engineTypeW, String engineTypeB, Integer initialdepth, Boolean fastSim, Boolean dynamicdepth) {
        Board board = new Board();
        this.state = board.getBoardState();

        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);
        if (engineTypeW.equals("Random")) {
            this.playerW = new Random(w, state);
        } else if (engineTypeW.equals("HighestScore")) {
            this.playerW = new HighestScore(w, b, state);
        } else if (engineTypeW.equals("Minmax")) {
            this.playerW = new Minmax(w, b, state, initialdepth, dynamicdepth);
        } else if (engineTypeW.equals("MinmaxAB")) {
            this.playerW = new MinmaxAB(w, b, state, initialdepth, dynamicdepth, true);
        } else if (engineTypeW.equals("Human")){
            this.playerW = new Human(w, state);
            Board.printStateGraphic(state);
        }

        if (engineTypeB.equals("Random")) {
            this.playerB = new Random(b, state);
        } else if (engineTypeB.equals("HighestScore")) {
            this.playerB = new HighestScore(b, w, state);
        } else if (engineTypeB.equals("Minmax")) {
            this.playerB = new Minmax(b, w, state, initialdepth, dynamicdepth);
        } else if (engineTypeB.equals("MinmaxAB")) {
            this.playerB = new MinmaxAB(b, w, state, initialdepth, dynamicdepth, true);
        } else if (engineTypeB.equals("Human")){
             this.playerB = new Human(b, state);
             Board.printStateGraphic(state);
        }

        this.fastSim = fastSim;

    }

    /**
     * Runs a game of chess for 150 moves, checks for mates and stalemates.
     * Does not include draw by repetition or insufficient material.
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        outcome = "";
        Integer totalMoves = 0;
        for (int i = 0; i < 150; i++) {  
            Player white = playerW.getPlayer();
            if (white.getLegalMoves().length == 0) {
                int wkr = white.getKingRank();
                int wkf = white.getKingFile();
                King wKing = (King) state[wkr][wkf].getPiece();
                if (wKing.isInCheck(state[wkr][wkf])) {
                    outcome = "Black wins";
                } else {
                    Board.printStateGraphic(state);
                    outcome = "White to move, draw by stalemate";
                }

                totalMoves = i + 1;
                break;
            }
            Move moveChosenWhite = playerW.getMove();

            moveChosenWhite.execute();
            if (!fastSim) {
                System.out.println(i + 1 + ": ");
                System.out.println(moveChosenWhite);
                Board.printStateGraphic(state);
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            playerW.update();
            playerB.update();

            Player black = playerB.getPlayer();

            if (black.getLegalMoves().length == 0) {
                int bkr = black.getKingRank();
                int bkf = black.getKingFile();
                King bKing = (King) state[bkr][bkf].getPiece();
                if (bKing.isInCheck(state[bkr][bkf])) {
                    outcome = "White wins";
                } else {
                    Board.printStateGraphic(state);
                    outcome = "Black to move, draw by stalemate";
                }
                totalMoves = i + 1;
                break;
            }
            Move moveChosenBlack = playerB.getMove();
            moveChosenBlack.execute();

            if (!fastSim) {
                System.out.println(i + 1 + ": ");
                System.out.println(moveChosenBlack);
                Board.printStateGraphic(state);
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            playerW.update();
            playerB.update();

            if (i == 149) {
                outcome = "Draw";
                totalMoves = i + 1;
            }
        }

        Board.printStateGraphic(state);
        System.out.println(outcome + " in " + totalMoves + " moves.");
    }
}
