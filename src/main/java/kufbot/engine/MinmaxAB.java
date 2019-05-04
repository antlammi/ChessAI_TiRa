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
public class MinmaxAB implements Engine {

    private Player maxplayer;
    private Player opponent;
    private Square[][] state;
    private Integer maxdepth;
    private java.util.Random random;
    private Boolean dynamicdepth;
    private Boolean prints;
    private Double variance;
    /**
     *
     * @param player
     * @param opponent
     * @param state
     * @param initialdepth
     * @param dynamicdepth
     */
    public MinmaxAB(Player player, Player opponent, Square[][] state, Integer initialdepth, Boolean dynamicdepth, Boolean prints) {
        this.state = state;
        this.maxplayer = player;
        this.opponent = opponent;
        this.maxdepth = initialdepth;
        this.random = new java.util.Random();
        this.dynamicdepth = dynamicdepth;
        this.prints = prints;
        this.variance = 0.0049; //determines how large difference in evaluation can be for moves to be considered equal
    }

    /**
     *
     * @param depth
     */
    public void setMaxDepth(Integer depth) {
        this.maxdepth = depth;
    }

    /**
     *
     * @return
     */
    public Integer getMaxDepth() {
        return this.maxdepth;
    }

    /**
     *
     * @return Move chosen by the Engine
     */
    @Override
    public Move getMove() {
        long initialTime = System.currentTimeMillis();

        Move move = minimaxAB(this.state, 0, this.maxplayer, null, -Double.MAX_VALUE, Double.MAX_VALUE);
        Move finalmove;
        if (move != null) {
            finalmove = move.cloneMove(this.state);
        } else {
            finalmove = null;
        }
        long finalTime = System.currentTimeMillis();
        long timeTaken = finalTime - initialTime;
        if (prints) {
            System.out.println("Finding the move took " + timeTaken + " milliseconds.");
        }
        if (dynamicdepth == true) {
            if (timeTaken > 20000 && maxdepth < 7) {      //arbitrary cap
                if (prints) {
                    System.out.println("Decreasing maximum depth for " + maxplayer.getColor() + "...");
                }

                this.maxdepth--;
            } else if (timeTaken < 1000 && maxdepth > 1) {
                if (prints) {
                    System.out.println("Increasing maximum depth for " + maxplayer.getColor() + "...");
                }
                this.maxdepth++;
            }
        }

        return finalmove;
    }

    /**
     * Uses minimax algorithm with Alpha-Beta pruning to find best move
     * available, recursively calls itself until terminal node or maximum depth
     * is reached
     *
     * @param nodestate current boardstate
     * @param depth depth
     * @param currentPlayer Player whose turn it is at given depth
     * @param latestMove Previous move that was made
     * @param alpha Score that maximizing player is assured of in a given line
     * @param beta Score that minimizing player is assured of in a given line
     * @return Best move in current line
     */
    public Move minimaxAB(Square[][] nodestate, Integer depth, Player currentPlayer, Move latestMove, Double alpha, Double beta) {
        Square[][] copyState = Board.copyBoardstate(nodestate);

        Move last;
        if (latestMove != null) {

            last = latestMove.cloneMove(copyState);
            last.execute();
        } else {
            last = null;
        }

        Player currentClone = currentPlayer.clonePlayer(copyState);
        Move[] moves = currentClone.getLegalMoves();
        if (moves.length == 0) {
            if (latestMove == null) {
                return null;
            }
            if (checkForMate(currentClone, copyState)) { //mate
                latestMove.getPlayer().setScore(1000000.0 - depth); //Changed from max value to one million to ensure loop works properly, still far larger than any game state could otherwise produce.
            } else { //Stalemate
                latestMove.getPlayer().setScore(0.0);
                currentPlayer.setScore(0.0);
            }
            return latestMove;
        }

        if (depth == maxdepth) {
            currentClone.updatePlayer();
            last.setPlayer(latestMove.getPlayer().clonePlayer(copyState));

            return last;
        }

        Move[] bestMoves = new Move[moves.length];
        Integer bmcount = 0;
        if (currentPlayer.getColor() == maxplayer.getColor()) {
            double maxScore = -Double.MAX_VALUE;
            for (int i = 0; i < moves.length; i++) {
                Player minimizing = opponent.clonePlayer(copyState);
                Move currentMove = moves[i].cloneMove(copyState);

                currentMove.setPlayer(currentClone);
                Double moveScore;
                Move oMove = minimaxAB(copyState, depth + 1, minimizing, currentMove, alpha, beta);

                if (oMove.getPlayer().getColor() == minimizing.getColor()) {
                    moveScore = oMove.getPlayer().getScore();
                } else {
                    moveScore = oMove.getPlayer().getScore();
                }
                Double scoreDifferential = moveScore - maxScore;

                if (moveScore > maxScore && scoreDifferential > variance) {  //if the move score is better by more than variance 
                    maxScore = moveScore;
                    bmcount = 0;
                    bestMoves = new Move[moves.length];
                    bestMoves[bmcount] = currentMove;
                } else if (scoreDifferential <= variance && scoreDifferential >= -variance) {//if the moves are practically equal
                    bmcount++;
                    bestMoves[bmcount] = currentMove;
                }
                if (maxScore > alpha) {
                    alpha = maxScore;
                }
                if (alpha > beta) {
                    break;
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
                Move oMove = minimaxAB(copyState, depth + 1, maximizing, currentMove, alpha, beta);
                Double moveScore;
                if (oMove.getPlayer().getColor() == maximizing.getColor()) {
                    oMove.execute();
                    moveScore = oMove.getPlayer().getScore();
                    

                } else {
                    moveScore = -oMove.getPlayer().getScore();
                   
                }
                Double scoreDifferential = moveScore - minScore;
                if (moveScore < minScore && scoreDifferential < -variance) { 
                    minScore = moveScore;
                    bmcount = 0;
                    bestMoves = new Move[moves.length];
                    bestMoves[bmcount] = currentMove;
                } else if (scoreDifferential >= -variance && scoreDifferential <= variance) {
                    bmcount++;
                    bestMoves[bmcount] = currentMove;
                }
                if (minScore < beta) {
                    beta = minScore;
                }
                if (alpha > beta) {
                    break;
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

    /**
     *
     */
    @Override
    public void update() {
        this.maxplayer.updatePlayer();
    }

    /**
     *
     * @return
     */
    @Override
    public Player getPlayer() {
        return this.maxplayer;
    }

}
