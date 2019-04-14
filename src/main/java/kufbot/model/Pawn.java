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
public class Pawn implements Piece {

    public final Color color;
    private Square current;
    private Square[][] boardstate;
    private final Double baseValue;
    private Double value;

    public Pawn(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.current = initial;
        this.boardstate = boardstate;
        this.baseValue = 1.0;
        this.value = baseValue;
    }

    @Override
    public String toString() {
        return color + " PAWN";
    }

    @Override
    public void setSquare(Square newSquare) {
        this.current = newSquare;
    }

    @Override
    public Move[] getMoves() {
        Move[] possibleMoves;
        if (this.color == Color.WHITE) {
            possibleMoves = getMovesWhite();
        } else {
            possibleMoves = getMovesBlack();
        }
        return possibleMoves;
    }

    public Move[] getMovesWhite() {
        Integer rank = current.getRank();
        Integer file = current.getFile();
        Move[] possibleMovesWhite = new Move[4];
        Integer moveCount = 0;

        for (int f = file - 2; f <= file && f <= 7; f++) {
            if (f >= 0) {
                if (rank < 8) {
                    possibleMovesWhite[moveCount] = new Move(boardstate);
                    possibleMovesWhite[moveCount].constructMove(this, current, boardstate[rank][f]);
                    moveCount++;
                }
            }
        }
        if (rank == 2) {
            possibleMovesWhite[moveCount] = new Move(boardstate);
            possibleMovesWhite[moveCount].constructMove(this, current, boardstate[rank + 1][file - 1]);
            moveCount++;
        }
        return possibleMovesWhite;
    }

    public Move[] getMovesBlack() {
        Integer rank = current.getRank();
        Integer file = current.getFile();
        Move[] possibleMovesBlack = new Move[4];
        Integer moveCount = 0;
        for (int f = file - 2; f <= file && f <= 7; f++) {
            if (f >= 0) {
                if ((rank > 1)) {
                    possibleMovesBlack[moveCount] = new Move(boardstate);
                    possibleMovesBlack[moveCount].constructMove(this, current, boardstate[rank - 2][f]);
                    moveCount++;
                }
            }
        }
        if (rank == 7) {
            possibleMovesBlack[moveCount] = new Move(boardstate);
            possibleMovesBlack[moveCount].constructMove(this, current, boardstate[rank - 3][file - 1]);
            moveCount++;
        }

        return possibleMovesBlack;
    }

    @Override
    public Move[] getLegalMoves() {
        Move[] movesToCheck = getMoves();
        Move[] legalMoves = new Move[movesToCheck.length];

        int legalcount = 0;

        for (int i = 0; i < movesToCheck.length; i++) {

            Move currentMove = movesToCheck[i];
            if (currentMove != null) {
                Square destination = currentMove.getDestinationSquare();
                int df = destination.getFile() - 1;
                int dr = destination.getRank() - 1;

                if (current.getFile() == destination.getFile()) { //jos siirrytään eteenpäin
                    if (dr - current.getRank() - 1 == 0 || current.getRank() - 1 - dr == 2) {
                        if (current.getRank() == 2) { //jos aloitussiirto valkoisilla
                            if (boardstate[dr][df].isEmpty() && boardstate[dr - 1][df].isEmpty()) { //jos välissä oleva ruutu ja määränpää tyhjiä
                                legalMoves[legalcount] = currentMove;
                                legalcount++;
                            }
                        } else if (current.getRank() == 7) { //jos aloitussiirto mustilla
                            if (boardstate[dr][df].isEmpty() && boardstate[dr + 1][df].isEmpty()) { //jos välissä oleva ruutu ja määränpää tyhjiä
                                legalMoves[legalcount] = currentMove;
                                legalcount++;
                            }
                        }

                    } else if (boardstate[dr][df].isEmpty()) { //jos määränpää tyhjä ja ei aloitussiirto
                        legalMoves[legalcount] = currentMove;
                        legalcount++;
                    }
                } else // jos siirrytään vinosuuntaan
                {
                    if (!boardstate[dr][df].isEmpty()) { //jos määränpää ei ole tyhjä
                        if (boardstate[dr][df].getPiece().getColor() != this.color) { //ja kyseessä ei ole oma palanen
                            currentMove.setPawnCapture();       // pawn erikoiskäyttäytyminen otetaan huomion
                            legalMoves[legalcount] = currentMove;
                            legalcount++;
                        }
                    }
                }

            }
        }
        return legalMoves;
    }

    @Override   //finetuning later
    public void setValue(Integer rank) {
        Double rankValue;
        if (this.color == Color.WHITE) {
            rankValue = 1.0*current.getRank() - 2;
            this.value = (rankValue / 11) + this.baseValue;
        } else {
            rankValue = -1.0*(current.getRank() - 1 - 6);
            this.value = (rankValue / 11) + this.baseValue;
        }
        
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public Double getValue() {
        return this.value;
    }
}
