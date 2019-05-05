/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.model;

/**
 *
 * @author antlammi
 */
public class Pawn implements Piece {

    /**
     *
     */
    public final Color color;
    private Square current;
    private Square[][] boardstate;
    private final Double baseValue;
    private Double value;

    /**
     *
     * @param color
     * @param initial
     * @param boardstate
     */
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

    /**
     *
     * @param newSquare
     */
    @Override
    public void setSquare(Square newSquare) {
        this.current = newSquare;
    }

    /**
     * Provides an array of possible moves based on Pawn's movement rules in the
     * position Calls either getMovesWhite() or getMovesBlack() to provide moves
     * for correct color only
     *
     * @return
     */
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

    /**
     * Provides an array of possible moves based on Pawn's movement rules in the
     * position for a white pawn
     *
     * @return
     */
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

    /**
     * Provides an array of possible moves based on Pawn's movement rules in the
     * position for a black pawn
     *
     * @return
     */
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

    /**
     * Checks for legality in array of moves provided by getMoves If a Square is
     * unreachable (blocked), or contains a Piece of the same color, a move is
     * considered illegal. Accounts for pawns only being able to move diagonally
     * in a capture.
     *
     * @return
     */
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
                 if (!boardstate[dr][df].isEmpty()) { //jos määränpää ei ole tyhjä
                        if (boardstate[dr][df].getPiece().getColor() != this.color) { //ja kyseessä ei ole oma palanen
                            currentMove.setPawnCapture();       // pawn erikoiskäyttäytyminen otetaan huomion
                            legalMoves[legalcount] = currentMove;
                            legalcount++;
                        }
                    }

            }
        }
        return legalMoves;
    }

    /**
     * Updates value of a pawn based on its rank and file, closer to
     * Queening/Central is considered more valuable.
     *
     * @param rank
     */
    @Override
    public void updateValue() {
        Integer r = current.getRank() - 1;
        Integer f = current.getFile() - 1;
        Double rankValue;
        Double filefactor;

        if (f == 3 || f == 4) {
            filefactor = 10.0; //makes the engine prefer pushing pawns in the middle, especially early on. Leads to better openings at least.
            if (this.color == Color.WHITE) {
                rankValue = 1.0 * r - 1;
                this.value = (rankValue / filefactor) + this.baseValue;
            } else {
                rankValue = -1.0 * (r - 6);
                this.value = (rankValue / filefactor) + this.baseValue;
            }
        } else if (f == 5 || f == 6 || f == 7) { //Engine isn't given extra incentive to push these pawns early on to keep kingside castle safe
            if (r < 5 && this.color == Color.WHITE || r > 2 && this.color == Color.BLACK) {
                this.value = this.baseValue;
            } else {
                filefactor = 10.0;
                if (this.color == Color.WHITE) {
                    rankValue = 1.0 * r - 1;
                    this.value = (rankValue / filefactor) + this.baseValue;
                } else {
                    rankValue = -1.0 * (r - 6);
                    this.value = (rankValue / filefactor) + this.baseValue;
                }
            }
        } else {
            if (r < 5 && this.color == Color.WHITE || r > 2 && this.color == Color.BLACK) { //Queenside pawns can be pushed early but not given a lot of incentive to do so.
                filefactor = 100.0;
            } else {
                filefactor = 10.0;

            }
            if (this.color == Color.WHITE) {
                rankValue = 1.0 * r - 1;
                this.value = (rankValue / filefactor) + this.baseValue;
            } else {
                rankValue = -1.0 * (r - 6);
                this.value = (rankValue / filefactor) + this.baseValue;
            }
        }

    }

    /**
     *
     * @return
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     *
     * @return
     */
    @Override
    public Double getValue() {
        return this.value;
    }

    /**
     *
     * @param value
     */
    @Override
    public void setValue(Double value
    ) {
        this.value = value;
    }
}
