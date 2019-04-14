/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.engine;

import kufbot.model.Board;
import kufbot.model.King;
import kufbot.model.Move;
import kufbot.model.Player;
import kufbot.model.Square;

/**
 *
 * @author antlammi
 */
public class Minmax implements Engine {

    private Player maxplayer;
    private Player opponent;
    private Square[][] state;
    private Integer maxdepth;
    private java.util.Random random;

    public Minmax(Player player, Player opponent, Square[][] state) { //Refactor project and fix, currently problem in players scores
        this.state = state;
        this.maxplayer = player;
        this.opponent = opponent;
        this.maxdepth = 3;
        this.random = new java.util.Random();

    }

    public void setMaxDepth(Integer depth) {
        this.maxdepth = depth;
    }

    @Override
    public Move getMove() {
        Move move = minimax(this.state, 0, this.maxplayer, null).cloneMove(state);
        return move;
    }

    public Move minimax(Square[][] nodestate, Integer depth, Player currentPlayer, Move latestMove) {
        Square[][] copyState = Board.copyBoardstate(nodestate);

        Move last;
        if (latestMove != null) {

            last = latestMove.cloneMove(copyState);
            last.execute();
        } else {
            last = null;
        }
        Player currentClone = currentPlayer.clonePlayer(copyState);
        if (depth == maxdepth) {
            /*System.out.println("Maximizing: " + currentPlayer.getScore());
            System.out.println("Minimizing: " + latestMove.getPlayer().getScore());
            System.out.println("Depth " + depth + " : " );
            printStateGraphic(copyState);*/
            return latestMove;
        }

        if (currentClone.getLegalMoves().length == 0) {
            if (checkForMate(currentClone, copyState)) { //mate
                latestMove.getPlayer().setScore(Double.MAX_VALUE);
            } else { //Stalemate
                latestMove.getPlayer().setScore(0.0);
                currentPlayer.setScore(0.0);
            }
            return latestMove;
        }

        Move[] moves = currentClone.getLegalMoves();
        Move[] bestMoves = new Move[moves.length];
        Integer bmcount = 0;
        if (currentPlayer.getColor() == maxplayer.getColor()) {
            double maxScore = -Double.MAX_VALUE;
            for (int i = 0; i < moves.length; i++) {
                Player minimizing = opponent.clonePlayer(copyState);
                Move currentMove = moves[i].cloneMove(copyState);
                currentMove.setPlayer(currentClone);
                Double maximizingScore = currentClone.getScore();
                Move oMove = minimax(copyState, depth + 1, minimizing, currentMove);

                Double minimizingScore;
                if (oMove.getPlayer() == minimizing) {
                    minimizingScore = oMove.getPlayer().getScore();
                } else if (oMove.getPlayer() == currentClone) {

                    copyState = oMove.getState();

                    Player min = opponent.clonePlayer(copyState);
                    minimizingScore = min.getScore();
                } else {
                    minimizingScore = null;
                }
                Double moveScore = maximizingScore - minimizingScore;
                if (moveScore > maxScore) {
                    //System.out.println(currentMove.toString() + ": " + moveScore);
                    maxScore = moveScore;
                    bmcount = 0;
                    bestMoves = new Move[moves.length];
                    bestMoves[bmcount] = currentMove;
                } else if (moveScore == maxScore) {
                    bmcount++;
                    bestMoves[bmcount] = currentMove;
                }
            }
            if (bmcount == 0) {

                //System.out.println("Maximizing, depth " + depth + ": " + bestMoves[0]);
                return bestMoves[0];
            } else {
                //System.out.println("Maximizing, depth " + depth + ":" + bestMoves[random.nextInt(bmcount)]);
                return bestMoves[random.nextInt(bmcount)];
            }
        } else {
            double minScore = Double.MAX_VALUE;
            for (int i = 0; i < moves.length; i++) {
                Player maximizing = maxplayer.clonePlayer(copyState);
                Move currentMove = moves[i].cloneMove(copyState);
                currentMove.setPlayer(currentPlayer);
                Double minimizingScore = currentPlayer.getScore();
                Double maximizingScore = minimax(copyState, depth + 1, maximizing, currentMove).getPlayer().getScore();

                Double moveScore = minimizingScore - maximizingScore;
                if (moveScore < minScore) {
                    minScore = moveScore;
                    bmcount = 0;
                    bestMoves = new Move[moves.length];
                    bestMoves[bmcount] = currentMove;
                } else if (moveScore == minScore) {
                    bmcount++;
                    bestMoves[bmcount] = currentMove;
                }

            }
            if (bmcount == 0) {
                //System.out.println("Minimizing, depth " + depth +": " + bestMoves[0]);
                return bestMoves[0];
            } else {
                //System.out.println("Minimizing, depth " + depth + ": " + bestMoves[random.nextInt(bmcount)]);
                return bestMoves[random.nextInt(bmcount)];
            }

        }
    }

    private Boolean checkForMate(Player clone, Square[][] copy) {
        /* System.out.println("checkForMate: ");
        System.out.println(depth + ": ");
        System.out.println("latestMove: " + latestMove);
        printStateGraphic(copy);
         */

        Square kingLocation = copy[clone.getKingRank()][clone.getKingFile()];
        King king = (King) kingLocation.getPiece();
        if (king.isInCheck(kingLocation)) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        this.maxplayer.updatePlayer();
    }

    @Override
    public Player getPlayer() {
        return this.maxplayer;
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
