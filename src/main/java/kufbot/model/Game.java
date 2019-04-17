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
    public String outcome;

    public Game(String engineTypeW, String engineTypeB, Integer initialdepth, Boolean fastSim, Boolean dynamicdepth) {
        Board board = new Board();
        this.state = board.getBoardState();

        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);
        if (engineTypeW == "Random") {
            this.playerW = new Random(w, state);
        } else if (engineTypeW == "HighestScore") {
            this.playerW = new HighestScore(w, b, state);
        } else if (engineTypeW == "Minmax") {
            this.playerW = new Minmax(w, b, state, initialdepth, dynamicdepth);
        } else if (engineTypeW == "MinmaxAB"){
            this.playerW = new MinmaxAB(w,b,state,initialdepth,dynamicdepth);
        }

        if (engineTypeB == "Random") {
            this.playerB = new Random(b, state);
        } else if (engineTypeB == "HighestScore") {
            this.playerB = new HighestScore(b, w, state);
        } else if (engineTypeB == "Minmax") {
            this.playerB = new Minmax(b, w, state, initialdepth, dynamicdepth);
        } else if (engineTypeB == "MinmaxAB"){
            this.playerB = new MinmaxAB(w,b,state,initialdepth,dynamicdepth);
        }

        this.fastSim = fastSim;

    }

    public void run() throws InterruptedException {

        outcome = "";
        Integer totalMoves = 0;
        for (int i = 0; i < 150; i++) {  //Chess game for 150 moves. No repetition draws, 50 move rule or insufficient material checks
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
