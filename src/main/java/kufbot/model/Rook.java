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
public class Rook implements Piece {

    /**
     *
     */
    public final Color color;
    private Move[] legalMoves;
    private Boolean moved;
    private Square current;
    private Square[][] boardstate;
    private Double baseValue;
    private Double value;

    /**
     *
     * @param color
     * @param initial
     * @param boardstate
     */
    public Rook(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.current = initial;
        this.boardstate = boardstate;
        this.moved = false;
        this.baseValue = 5.0;
        this.value = baseValue;
    }

    /**
     *
     * @return true if Rook has been moved, false if not.
     */
    public Boolean getMoved() {
        return this.moved;
    }

    /**
     *
     * @param moved
     */
    public void setMoved(Boolean moved) {
        this.moved = moved;
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
     * Provides an array of possible moves based on Rook's movement rules in
     * the position, checks for legality
     *
     * @return Move[]
     */
    @Override
    public Move[] getMoves() {
        Integer rank = current.getRank() - 1;
        Integer file = current.getFile() - 1;
        Move[] possibleMoves = new Move[14];

        Boolean top = true;
        Boolean bottom = true;
        Boolean left = true;
        Boolean right = true;

        Integer moveCount = 0;
        for (int d = 1; d <= 7; d++) {
            if (top) {
                if ((rank + d <= 7)) { //ylös d askelta on pöydän rajojen sisällä
                    if (boardstate[rank+d][file].isEmpty()) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank+d][file]);
                        moveCount++;
                    } else if (boardstate[rank+d][file].getPiece().getColor() != this.color) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank+d][file]);
                        moveCount++;
                        top = false;
                    } else {
                        top = false;
                    }
                }
            }
            if (bottom) {
                if ((rank - d >= 0)) { //alas d askelta on pöydän rajojen sisällä
                    if (boardstate[rank-d][file].isEmpty()) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank-d][file]);
                        moveCount++;
                    } else if (boardstate[rank-d][file].getPiece().getColor() != this.color) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank-d][file]);
                        moveCount++;
                        bottom = false;
                    } else {
                        bottom = false;
                    }
                }
            }
            if (left) {
                if (file - d >= 0) { //vasemmalle d askelta on pöydän rajojen sisällä
                    if (boardstate[rank][file-d].isEmpty()) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank][file-d]);
                        moveCount++;
                    } else if (boardstate[rank][file-d].getPiece().getColor() != this.color) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank][file-d]);
                        moveCount++;
                        left = false;
                    } else {
                        left = false;
                    }
                }
            }
             if (right) {
                if (file+d <= 7) { //oikealle d askelta on pöydän rajojen sisällä
                    if (boardstate[rank][file+d].isEmpty()) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank][file+d]);
                        moveCount++;
                    } else if (boardstate[rank][file+d].getPiece().getColor() != this.color) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank][file+d]);
                        moveCount++;
                        right = false;
                    } else {
                        right = false;
                    }
                }
            }

        }
        return possibleMoves;
    }

    @Override
    public String toString() {
        return color + " ROOK";
    }

    /**
     * Used to check for legality, but that functionality was moved to getMoves
     * for efficiency. Only exists to be called by other classes.
     * @return Move[]
     */
    @Override
    public Move[] getLegalMoves() {
        this.legalMoves = getMoves();
        return legalMoves;
    }

    /**
     *
     * @param moves
     */
    @Override
    public void updateValue() {
        this.value = (baseValue + (1.0 * this.getLegalMoves().length / 500));

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
    public void setValue(Double value) {
        this.value = value;
    }
}
