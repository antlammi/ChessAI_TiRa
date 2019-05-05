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
public class Bishop implements Piece {

    private final Color color;
    private Square current;
    private Square[][] boardstate;
    private final Double baseValue;
    private Double value;

    /**
     *
     * @param color
     * @param initial Square at time of creation
     * @param boardstate
     */
    public Bishop(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.current = initial;
        this.boardstate = boardstate;
        this.baseValue = 3.0;
        this.value = baseValue;

    }

    @Override
    public String toString() {
        return color + " BISHOP";
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
     * Provides an array of possible moves based on Bishop's movement rules in
     * the position, also checks for legality
     *
     * @return Move[]
     */
    @Override
    public Move[] getMoves() {
        Integer rank = current.getRank() - 1;
        Integer file = current.getFile() - 1;
        Move[] possibleMoves = new Move[13];

        Boolean topleft = true;
        Boolean bottomleft = true;
        Boolean topright = true;
        Boolean bottomright = true;

        Integer moveCount = 0;
        for (int d = 1; d <= 7; d++) {
            if (bottomleft) {
                if ((rank - d >= 0 && rank - d <= 7) && (file - d >= 0 && file - d <= 7)) { //vasemmalle ja alas d askelta on pöydän rajojen sisällä
                    if (boardstate[rank - d][file - d].isEmpty()) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank - d][file - d]);
                        moveCount++;
                    } else if (boardstate[rank - d][file - d].getPiece().getColor() != this.color) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank - d][file - d]);
                        moveCount++;
                        bottomleft = false;
                    } else {
                        bottomleft = false;
                    }
                }
            }
            if (topleft) {
                if ((rank + d >= 0 && rank + d <= 7) && (file - d >= 0 && file - d <= 7)) { //vasemmalle ja ylös d askelta on pöydän rajojen sisällä
                    if (boardstate[rank + d][file - d].isEmpty()) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank + d][file - d]);
                        moveCount++;
                    } else if (boardstate[rank + d][file - d].getPiece().getColor() != this.color) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank + d][file - d]);
                        moveCount++;
                        topleft = false;
                    } else {
                        topleft = false;
                    }
                }
            }
            if (bottomright) {
                if ((rank - d >= 0 && rank - d <= 7) && (file + d >= 0 && file + d <= 7)) { //oikealle ja alas d askelta on pöydän rajojen sisällä
                    if (boardstate[rank - d][file + d].isEmpty()) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank - d][file + d]);
                        moveCount++;
                    } else if (boardstate[rank - d][file + d].getPiece().getColor() != this.color) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank - d][file + d]);
                        moveCount++;
                        bottomright = false;
                    } else {
                        bottomright = false;
                    }
                }
            }
            if (topright) {
                if ((rank + d >= 0 && rank + d <= 7) && (file + d >= 0 && file + d <= 7)) { //oikealle ja ylös d askelta on pöydän rajojen sisällä
                    if (boardstate[rank + d][file + d].isEmpty()) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank + d][file + d]);
                        moveCount++;
                    } else if (boardstate[rank + d][file + d].getPiece().getColor() != this.color) {
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank + d][file + d]);
                        moveCount++;
                        topright = false;
                    } else {
                        topright = false;
                    }
                }
            }

        }
        return possibleMoves;
    }

    /**
     * Used to check for legality, functionality was moved to getMoves for efficiency. 
     * This simply exists for other classes to call now.
     * @return Move[]
     */
    @Override
    public Move[] getLegalMoves() {
        Move[] legalMoves = getMoves();
        return legalMoves;
    }

   
    /**
     * Updates the value of the piece based on how many moves it has available
     *
     * @param moves
     */
    @Override
    public void updateValue() {
       
        this.value = this.baseValue + (1.0 * getLegalMoves().length / 67.5);
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
