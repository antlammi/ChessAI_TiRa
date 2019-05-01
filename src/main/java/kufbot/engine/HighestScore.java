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
 * Looks at own best move with 1 followup move from opponent considered. Was mostly used as a stepping stone to implementing Minimax. 
 * @author antlammi
 */
public class HighestScore implements Engine {

    private Player player;
    private Player opponent;
    private Square[][] state;
    private java.util.Random random;
    
    

    /**
     *
     * @param player
     * @param opponent
     * @param state
     */
    
    public HighestScore(Player player, Player opponent, Square[][] state) {
        this.player = player;
        this.opponent = opponent;
        this.state = state;
        this.random = new java.util.Random(); //games were too predictable, allows for choosing random moves when equal outcomes are present
    }

    /**
     *
     * @return Best move considering opponent's next move.
     */
    @Override
    public Move getMove() {
        Move[] moves = player.getLegalMoves();
        double maxScore = -Double.MAX_VALUE;

        Move[] bestMoves = new Move[moves.length];
        Integer movecount = 0;
        for (int i = 0; i < moves.length; i++) {
           
            Square[][] copy = Board.copyBoardstate(state);
            Move cloneMove = moves[i].cloneMove(copy);
            cloneMove.execute();
            
            Player playerClone = player.clonePlayer(copy);
            Player opponentClone = opponent.clonePlayer(copy);

            Boolean stalemate = false;
            if (opponentClone.getLegalMoves().length == 0) {
                if (checkForMate(opponentClone, copy)) {
                    return moves[i]; //if opponent is mated, move is best one possible

                } else {
                    stalemate = true; //stalemate and game ends in a draw
                }
            }
            double moveScore;
            if (!stalemate) {
                Move oMove = opponentMove(opponentClone, playerClone, copy).cloneMove(copy); //opponentMove would make the move on a copy of the boardstate
                                                                                             //this way it is actually made where it matters
                oMove.execute();
                playerClone.updatePlayer();
                opponentClone.updatePlayer();
                moveScore = playerClone.getScore();
            } else {
                moveScore = 0.0;
            }

            if (moveScore > maxScore) {
                maxScore = moveScore;
                movecount = 0;
                bestMoves = new Move[moves.length];
                bestMoves[movecount] = moves[i];
            } else if (moveScore == maxScore) {
                movecount++;
                bestMoves[movecount] = moves[i];
            }

        }
        if (movecount > 0) {
            return bestMoves[random.nextInt(movecount)];
        } else {
            return bestMoves[0];
        }
    }

    private Move opponentMove(Player player, Player opponent, Square[][] state) {
        Square[][] initialCopy = Board.copyBoardstate(state);
        Player pClone = player.clonePlayer(initialCopy);

        Move[] moves = pClone.getLegalMoves();

        double maxScore = -Double.MAX_VALUE;
        Move[] bestMoves = new Move[moves.length];
        Integer movecount = 0;

        for (int i = 0; i < moves.length; i++) {
            Square[][] copy = Board.copyBoardstate(initialCopy);
            Move cloneMove = moves[i].cloneMove(copy);
            cloneMove.execute();
            Player playerClone = pClone.clonePlayer(copy);
            Player opponentClone = opponent.clonePlayer(copy);
            double moveScore = playerClone.getScore();

            if (opponentClone.getPossibleMoves().length == 0) {
                if (checkForMate(opponentClone, copy)) {
                    return moves[i]; //if opponent is mated, move is best one possible

                } else {
                    moveScore = 0.0; //stalemate and game ends in a draw
                }
            }
            if (moveScore > maxScore) {
                maxScore = moveScore;
                movecount = 0;
                bestMoves = new Move[moves.length];
                bestMoves[movecount] = moves[i];
            } else if (moveScore == maxScore) {
                movecount++;
                bestMoves[movecount] = moves[i];
            }

        }
        if (movecount > 0) {
            return bestMoves[random.nextInt(movecount)];
        } else {
            return bestMoves[0];
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
    public void update() {
        player.updatePlayer();
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return this.player;
    }
}
