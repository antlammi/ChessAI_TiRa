/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.model;

import java.util.ArrayList;

/**
 *
 * @author antlammi
 */
class Bishop implements Piece {

    private final Color color;
    private Square current;
    private Square[][] boardstate;
    public Bishop(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.current = initial;
        this.boardstate = boardstate;
    }

    @Override
    public String toString() {
        return color + " BISHOP";
    }
    @Override
    public void setSquare(Square newSquare){
        this.current = newSquare;
    }
    @Override
    public Move[] getMoves() {
        Integer rank = current.getRank() - 1;
        Integer file = current.getFile() - 1;
        Move[] possibleMoves = new Move[13];
        Integer moveCount = 0;
        for (int d = 1; d <= 7; d++) {
            if ((rank - d >= 0 && rank - d <= 7) && (file - d >= 0 && file - d <= 7)) { //vasemmalle ja alas d askelta on pöydän rajojen sisällä
                possibleMoves[moveCount] = new Move(boardstate);
                possibleMoves[moveCount].constructMove(this, current, boardstate[rank - d][file - d]);
                moveCount++;

            }
            if ((rank + d >= 0 && rank + d <= 7) && (file - d >= 0 && file - d <= 7)) { //vasemmalle ja ylös d askelta on pöydän rajojen sisällä
                possibleMoves[moveCount] = new Move(boardstate);
                possibleMoves[moveCount].constructMove(this, current, boardstate[rank + d][file - d]);
                moveCount++;

            }
            if ((rank - d >= 0 && rank - d <= 7) && (file + d >= 0 && file + d <= 7)) { //oikealle ja alas d askelta on pöydän rajojen sisällä
                possibleMoves[moveCount] = new Move(boardstate);
                possibleMoves[moveCount].constructMove(this, current, boardstate[rank - d][file + d]);
                moveCount++;
            }
            if ((rank + d >= 0 && rank + d <= 7) && (file + d >= 0 && file + d <= 7)) { //oikealle ja ylös d askelta on pöydän rajojen sisällä
                possibleMoves[moveCount] = new Move(boardstate);
                possibleMoves[moveCount].constructMove(this, current, boardstate[rank + d][file + d]);
                moveCount++;
            }

        }
        return possibleMoves;
    }

    @Override
    public Move[] getLegalMoves() {
        Move[] movesToCheck = getMoves();
        Move[] legalMoves = new Move[movesToCheck.length];
        int legalcount = 0;

        int cr = current.getRank()-1;
        int cf = current.getFile()-1;
        for (int i = 0; i < movesToCheck.length; i++) {
            Move currentMove = movesToCheck[i];
            if (currentMove == null){
                return legalMoves;
            }
            Square destination = currentMove.getDestinationSquare();
            int dr = destination.getRank()-1;
            int df = destination.getFile()-1;
            if (dr > cr && df > cf) {
                if (checkTopRightDiagonal(boardstate, cr, cf, dr, df) == true) {
                    legalMoves[legalcount] = currentMove;
                    legalcount++;
                }
            } else if (dr < cr && df > cf) {
                if (checkBottomRightDiagonal(boardstate, cr, cf, dr, df) == true) {
                    legalMoves[legalcount] = currentMove;
                    legalcount++;
                }
            } else if (dr > cr && df < cf) {
                if (checkTopLeftDiagonal(boardstate, cr, cf, dr, df) == true) {
                    legalMoves[legalcount] = currentMove;
                    legalcount++;
                }
            } else if (dr < cr && df < cf) {
                if (checkBottomLeftDiagonal(boardstate, cr, cf, dr, df) == true) {
                    legalMoves[legalcount] = currentMove;
                    legalcount++;
                }
            }

        }
        return legalMoves;
    }

    public Boolean checkTopRightDiagonal(Square[][] boardstate, Integer cr, Integer cf, Integer dr, Integer df) {

        for (int d = 1; d <= 7; d++) {
            if (cr + d == dr && cf + d == df) {        //destination square reached
                Square destination = boardstate[dr][df];
                if (destination.isEmpty()) {
                    return true;
                } else if (destination.getPiece().getColor() != this.color) {
                    return true;
                } else {
                    return false;
                }
            }
            if (!boardstate[cr + d][cf + d].isEmpty()) {
                return false;
            }
        }
        return false;
    }

    public Boolean checkBottomRightDiagonal(Square[][] boardstate, Integer cr, Integer cf, Integer dr, Integer df) {
        for (int d = 1; d <= 7; d++) {
            if (cr - d == dr && cf + d == df) {        //destination square reached
                Square destination = boardstate[dr][df];
                if (destination.isEmpty()) {
                    return true;
                } else if (destination.getPiece().getColor() != this.color) {
                    return true;
                } else {
                    return false;
                }
            }
            if (!boardstate[cr - d][cf + d].isEmpty()) {
                return false;
            }
        }
        return false;
    }

    public Boolean checkTopLeftDiagonal(Square[][] boardstate, Integer cr, Integer cf, Integer dr, Integer df) {
        for (int d = 1; d <= 7; d++) {
            if (cr + d == dr && cf - d == df) {        //destination square reached
                Square destination = boardstate[dr][df];
                if (destination.isEmpty()) {
                    return true;
                } else if (destination.getPiece().getColor() != this.color) {
                    return true;
                } else {
                    return false;
                }
            }
            if (!boardstate[cr + d][cf - d].isEmpty()) {
                return false;
            }
        }
        return false;
    }

    public Boolean checkBottomLeftDiagonal(Square[][] boardstate, Integer cr, Integer cf, Integer dr, Integer df) {
        for (int d = 1; d <= 7; d++) {
            if (cr - d == dr && cf - d == df) {        //destination square reached
                Square destination = boardstate[dr][df];
                if (destination.isEmpty()) {
                    return true;
                } else if (destination.getPiece().getColor() != this.color) {
                    return true;
                } else {
                    return false;
                }
            }
            if (!boardstate[cr - d][cf - d].isEmpty()) {
                return false;
            }
        }
        return false;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

}
