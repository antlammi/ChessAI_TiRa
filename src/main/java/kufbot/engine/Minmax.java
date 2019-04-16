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
    private Boolean dynamicdepth;

    public Minmax(Player player, Player opponent, Square[][] state, Integer initialdepth, Boolean dynamicdepth) {
        this.state = state;
        this.maxplayer = player;
        this.opponent = opponent;
        this.maxdepth = initialdepth;
        this.random = new java.util.Random();
        this.dynamicdepth = dynamicdepth;
    }

    public void setMaxDepth(Integer depth) {
        this.maxdepth = depth;
    }

    @Override
    public Move getMove() {
        long initialTime = System.currentTimeMillis();
        
        Move move = minimax(this.state, 0, this.maxplayer, null).cloneMove(state);
        
        long finalTime = System.currentTimeMillis();
        long timeTaken = finalTime - initialTime;
        System.out.println("Finding the move took " + timeTaken + " milliseconds.");
        if (dynamicdepth == true) {
            if (timeTaken > 20000 && maxdepth < 7) {      //arbitrary cap
                System.out.println("Decreasing maximum depth for " + maxplayer.getColor() + "...");
                this.maxdepth--;
            } else if (timeTaken < 1000 && maxdepth > 1) {
                System.out.println("Increasing maximum depth for " + maxplayer.getColor() + "...");
                this.maxdepth++;
            }
        }

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
            currentClone.updatePlayer();
            last.setPlayer(latestMove.getPlayer().clonePlayer(copyState));

            return last;
        }

        if (currentClone.getLegalMoves().length == 0) {
            if (checkForMate(currentClone, copyState)) { //mate
                latestMove.getPlayer().setScore(1000000.0); //Changed from max value to one million to ensure loop works properly, still far larger than any game state could otherwise produce.
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
                Double moveScore;
                Move oMove = minimax(copyState, depth + 1, minimizing, currentMove);

                if (oMove.getPlayer().getColor() == minimizing.getColor()) {
                    moveScore = oMove.getPlayer().getScore();
                } else {
                    moveScore = oMove.getPlayer().getScore();
                    oMove.rollback();
                }
                if (moveScore > maxScore) {
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
                bestMoves[0].getPlayer().setScore(maxScore);
                return bestMoves[0];
            } else {
                Move best = bestMoves[random.nextInt(bmcount)];
                best.getPlayer().setScore(maxScore);
                return best;
            }
        } else {
            double minScore = Double.MAX_VALUE;
            for (int i = 0; i < moves.length; i++) {
                Player maximizing = maxplayer.clonePlayer(copyState);
                Move currentMove = moves[i].cloneMove(copyState);
                currentMove.setPlayer(currentPlayer);
                Move oMove = minimax(copyState, depth + 1, maximizing, currentMove);
                Double moveScore;
                if (oMove.getPlayer().getColor() == maximizing.getColor()) {
                    oMove.execute();
                    moveScore = oMove.getPlayer().getScore();
                    oMove.rollback();

                } else {
                    moveScore = -oMove.getPlayer().getScore();
                    oMove.rollback();
                }

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
                bestMoves[0].getPlayer().setScore(minScore);
                return bestMoves[0];
            } else {
                Move best = bestMoves[random.nextInt(bmcount)];
                best.getPlayer().setScore(minScore);
                return best;
            }

        }
    }

    private Boolean checkForMate(Player clone, Square[][] copy) {
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
