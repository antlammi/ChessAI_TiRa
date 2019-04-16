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
public class Knight implements Piece {
    public final Color color;
    private Square current;
    private Square[][] boardstate;
    private final Double baseValue;
    private Double value;
    public Knight(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.current = initial;
        this.boardstate = boardstate;
        this.baseValue = 3.0;
        this.value = baseValue;
    }

    
    @Override 
    public String toString(){
        return color + " KNIGHT"; 
    }

    @Override
    public void setSquare(Square newSquare){
        this.current = newSquare;
    }

    @Override
    public Move[] getMoves() {
        Integer rank = current.getRank() - 1;
        Integer file = current.getFile() - 1;
        Move[] possibleMoves = new Move[8];
        Integer moveCount = 0;
        for (int f = file -2; f<file+2; f++){
            if (f>=0 && f<=7){
                if (file-f == 2 ||f-file ==2){
                    if (rank-1 >= 0){
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank-1][f]);
                        moveCount++;
                    }
                    if (rank+1 <= 7){
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank+1][f]);
                        moveCount++;
                    }
                } else if (file -f == 1 ||f - file == 1){
                    if (rank -2 >= 0){
                        possibleMoves[moveCount]=new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank-2][f]);
                        moveCount++;
                    }
                    if (rank +2 <= 7){
                        possibleMoves[moveCount] = new Move(boardstate);
                        possibleMoves[moveCount].constructMove(this, current, boardstate[rank+2][f]);
                        moveCount++;
                    }
                    
                }
            }
            
        }
        return possibleMoves;
    }

    @Override
    public Move[] getLegalMoves() {
        Move[] movesToCheck = getMoves();
        Move[] legalMoves = new Move[movesToCheck.length];
        int legalcount = 0;
        for (int i=0; i<movesToCheck.length; i++){
            if (movesToCheck[i] == null){
                return legalMoves;
            }
            Square destination = movesToCheck[i].getDestinationSquare();
            if (destination.isEmpty()){
                legalMoves[legalcount] = movesToCheck[i];
                legalcount++;
            } else {
                if (destination.getPiece().getColor() != this.color){
                    legalMoves[legalcount] = movesToCheck[i];
                    legalcount++;
                }
            }
        }
        return legalMoves;
    }
    @Override
    public void updateValue(Integer moves){
        this.value = this.baseValue + (1.0*moves/100);
    }
    
    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }
}
