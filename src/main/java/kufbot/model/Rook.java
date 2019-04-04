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
class Rook implements Piece {

    public final Color color;
    private int legalcount;
    private Move[] legalMoves;

    public Rook(Color color) {
        this.color = color;
    }

    @Override
    public Move[] getMoves(Square current, Square[][] boardstate) {
        Integer rank = current.getRank();
        Integer file = current.getFile();
        Move[] possibleMoves = new Move[14];
        Integer moveCount = 0;
        for (int f = 0; f <= 7; f++) {
            if (f != file - 1) {
                possibleMoves[moveCount] = new Move(boardstate);
                possibleMoves[moveCount].constructMove(this, current, boardstate[rank - 1][f]);
                moveCount++;
            }
        }

        for (int r = 0; r <= 7; r++) {
            if (r != rank - 1) {
                possibleMoves[moveCount] = new Move(boardstate);
                possibleMoves[moveCount].constructMove(this, current, boardstate[r][file - 1]);
                moveCount++;
            }
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return color + " ROOK";
    }

    public void findLegalMovesOnSameRank(Square current, Square[][] boardstate, Move[] movesToCheck) {
        for (int i = 0; i < movesToCheck.length / 2 - 1; i++) {
            Square destination = movesToCheck[i].getDestinationSquare();

            int rank = current.getRank() - 1;
            int cf = current.getFile() - 1;
            int df = destination.getFile() - 1;
            if (cf > df) {  //jos alkuruudun sarake on suurempi kuin määränpään
                for (int f = cf; f >= df; f--) { //lähestytään määränpäätä
                    if (!boardstate[rank][f].isEmpty()) { //jos tämänhetkisellä ruudulla on palanen
                        if (f == df) {  //jos nykyinen sarake on vastaa määränpäätä
                            if (this.color != destination.getPiece().getColor()) { //jos palaset ovat eri värisiä, voidaan se syödä ja siirtyä ruudulle
                                legalMoves[legalcount] = movesToCheck[i];
                                legalcount++;
                            }
                        } else { //tiellä on jokin palanen, jonka yli ei voida siirtyä
                            break;
                        }
                    } else if (f == df) {  //jos ruutu on tyhjä ja vastaa saraketta, voidaan ruudulle siirtyä
                        legalMoves[legalcount] = movesToCheck[i];
                        legalcount++;
                    }
                }

            } else {
                for (int f = cf + 1; f <= df; f++) {
                    if (!boardstate[rank][f].isEmpty()) {
                        if (f == df) {
                            if (this.color != destination.getPiece().getColor()) {
                                legalMoves[legalcount] = movesToCheck[i];
                                legalcount++;
                            }
                        } else {
                            break;
                        }
                    } else if (f == df) {
                        legalMoves[legalcount] = movesToCheck[i];
                        legalcount++;
                    }
                }
            }

        }

    }

    public void findLegalMovesOnSameFile(Square current, Square[][] boardstate, Move[] movesToCheck) {
        for (int i = movesToCheck.length / 2; i < movesToCheck.length; i++) {
            Square destination = movesToCheck[i].getDestinationSquare();
            int file = current.getFile() - 1;
            int cr = current.getRank() - 1;
            int dr = destination.getRank() - 1;
            if (cr > dr) {
                for (int r = cr; r >= dr; r--) {
                    if (!boardstate[r][file].isEmpty()) {
                        if (r == dr) {
                            if (this.color != destination.getPiece().getColor()) {
                                legalMoves[legalcount] = movesToCheck[i];
                                legalcount++;
                            }
                        } else {
                            break;
                        }
                    } else if (r == dr) {
                        legalMoves[legalcount] = movesToCheck[i];
                        legalcount++;
                    }
                }

            } else {
                for (int r = cr + 1; r <= dr; r++) {
                    if (!boardstate[r][file].isEmpty()) {
                        if (r == dr) {
                            if (this.color != destination.getPiece().getColor()) {
                                legalMoves[legalcount] = movesToCheck[i];
                                legalcount++;

                            }
                        } else {
                            break;
                        }
                    } else if (r == dr) {
                        legalMoves[legalcount] = movesToCheck[i];
                        legalcount++;
                    }
                }
            }
        }
    }

    @Override
    public Move[] getLegalMoves(Square currentsquare, Square[][] boardstate) {
        Move[] movesToCheck = this.getMoves(currentsquare, boardstate);
        this.legalMoves = new Move[movesToCheck.length];
        this.legalcount = 0;
        findLegalMovesOnSameRank(currentsquare, boardstate, movesToCheck);
        findLegalMovesOnSameFile(currentsquare, boardstate, movesToCheck);
        return legalMoves;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

}
