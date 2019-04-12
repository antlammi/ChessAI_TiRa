/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.model;

import kufbot.engine.Random;
import java.util.concurrent.TimeUnit;
import kufbot.engine.Engine;

/**
 *
 * @author antlammi
 */
public class Game {

    private Engine playerW;
    private Engine playerB;
    private Square[][] state;
    private Boolean fastSim;

    public Game(String engineTypeW, String engineTypeB, Boolean fastSim) {
        Board board = new Board();
        this.state = board.getBoardState();

        Player w = new Player(Color.WHITE, state);
        Player b = new Player(Color.BLACK, state);
        if (engineTypeW == "Random") {
            this.playerW = (Random) new Random(w);
        }
        if (engineTypeB == "Random") {
            this.playerB = (Random) new Random(b);
        }
        this.fastSim = fastSim;

    }

    public void run() throws InterruptedException {

        String outcome = "";
        Integer totalMoves = 0;
        for (int i = 0; i < 500; i++) {  //Simuloi shakkiottelun satunnaisilla siirroilla. Ei ota huomioon 50-siirron sääntöä tai liian vähäistä materiaalia matitukseen.
            Player white = playerW.getPlayer();
            if (white.getLegalMoves().length == 0) {
                int wkr = white.getKingRank();
                int wkf = white.getKingFile();
                King wKing = (King) state[wkr][wkf].getPiece();
                if (wKing.isInCheck(state[wkr][wkf])) {
                    outcome = "Black wins";
                } else {
                    printStateGraphic(state);
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
                printStateGraphic(state);
                TimeUnit.MILLISECONDS.sleep(250);
            }
            playerW.update(state);
            playerB.update(state);

            Player black = playerB.getPlayer();

            if (black.getLegalMoves().length == 0) {
                int bkr = black.getKingRank();
                int bkf = black.getKingFile();
                King bKing = (King) state[bkr][bkf].getPiece();
                if (bKing.isInCheck(state[bkr][bkf])) {
                    outcome = "White wins";
                } else {
                    printStateGraphic(state);
                    outcome = "Black to move, draw by stalemate";
                }
                totalMoves = i + 1;
                break;
            }
            Move moveChosenBlack = playerB.getMove();  
            moveChosenBlack.execute();
            
            if (!fastSim){
                System.out.println(i + 1 + ": ");
                System.out.println(moveChosenBlack);
                printStateGraphic(state);
                TimeUnit.MILLISECONDS.sleep(250);
            }
            playerW.update(state);
            playerB.update(state);

            
            if (i == 499) {
                outcome = "Draw";
                totalMoves = i + 1;
            }
        }

        printStateGraphic(state);
        System.out.println(outcome + " in " + totalMoves + " moves.");
    }

    public void printState(Square[][] state) {

        String[] files = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int r = 0; r <= 7; r++) {
            for (int f = 0; f <= 7; f++) {
                System.out.println(files[f] + (r + 1) + "| " + state[r][f].toString());
            }
        }
    }

    public void printStateGraphic(Square[][] state) {

        String[] files = {"A", "B", "C", "D", "E", "F", "G", "H"};
        System.out.print("|");
        for (int i = 0; i < files.length; i++) {
            System.out.print(" " + files[i] + "  |");
        }
        System.out.print("\n");
        for (int r = 7; r >= 0; r--) {
            System.out.print("|");
            for (int f = 0; f <= 7; f++) {

                if (state[r][f].isEmpty()) {
                    System.out.print("    |");
                } else if (state[r][f].getPiece().toString().contains("KNIGHT")) {
                    System.out.print(" " + state[r][f].toString().substring(0, 1) + state[r][f].toString().substring(6, 8) + "|");
                } else {
                    System.out.print(" " + state[r][f].toString().substring(0, 1) + state[r][f].toString().substring(6, 7) + " |");
                }
            }
            System.out.println(" " + (r + 1) + "\n");

        }
    }
}