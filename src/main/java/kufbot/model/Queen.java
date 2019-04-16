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
public class Queen implements Piece {

    public final Color color;
    private Square current;
    private Square[][] boardstate;
    private Double value;
    private final Double baseValue;
    public Queen(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.current = initial;
        this.boardstate = boardstate;
        this.baseValue = 9.0;
        this.value = baseValue;
        
    }
    @Override
    public void setSquare(Square newSquare){
        this.current = newSquare;
    }
    @Override
    public String toString() {
        return color + " QUEEN";
    }

    @Override
    public Move[] getMoves() {
        Rook rook = new Rook(this.color, current, boardstate);
        Bishop bishop = new Bishop(this.color, current, boardstate);
        
        //Kuningattaren mahdolliset siirrot ovat yhdistelmä lähetin ja tornin siirtoja, hyöydynnetään näiden siirtologiikkaa.
        
        Move[] possibleMoves = new Move[27];
        Move[] rookMoves = rook.getMoves();
        Move[] bishopMoves = bishop.getMoves();

        for (int i = 0; i < 14; i++) {
            possibleMoves[i] = new Move(boardstate);
            possibleMoves[i].constructMove(this, current, rookMoves[i].getDestinationSquare());
        }
        
        for (int i=14; i<possibleMoves.length; i++){
            if (bishopMoves[i-14] == null){     //toisin kuin tornin, lähetin siirtojen määrä ei ole vakio
                break;
            }
            possibleMoves[i] = new Move(boardstate);
            possibleMoves[i].constructMove(this, current, bishopMoves[i-14].getDestinationSquare());
        }
        return possibleMoves;
    }

    @Override
    public Move[] getLegalMoves() {
        Rook rook = new Rook(this.color, current, boardstate);
        Bishop bishop = new Bishop(this.color, current, boardstate);
        
        Move[] legalMoves = new Move[27];
        Move[] legalRookMoves = rook.getLegalMoves();
        Move[] legalBishopMoves = bishop.getLegalMoves();
        
        Integer rookmovecount = 0;
        
        for (int i=0; i<legalRookMoves.length; i++){
            if (legalRookMoves[i] == null){
                break;
            }
            legalMoves[i] = new Move(boardstate);
            legalMoves[i].constructMove(this, current, legalRookMoves[i].getDestinationSquare());
            rookmovecount++;
        }
        
        for(int i=0; i<legalBishopMoves.length; i++){
            if (legalBishopMoves[i] == null){
                break;
            }
            
            legalMoves[i+rookmovecount] = new Move(boardstate);     
            legalMoves[i+rookmovecount].constructMove(this, current, legalBishopMoves[i].getDestinationSquare());
            
        }
       
        return legalMoves;
    }
    @Override
    public void updateValue(Integer moves){
        this.value = baseValue+(1.0*moves/150);
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
