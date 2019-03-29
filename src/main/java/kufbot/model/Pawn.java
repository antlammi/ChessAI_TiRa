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
class Pawn implements Piece {
    public final Color color;
    private Square current;

    public Pawn(Color color) {
        this.color = color;
    }

    
    @Override 
    public String toString(){
        return color + " PAWN"; 
    }

    
    @Override
    public Move[] getMoves(Square current, Square[][] boardstate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Move[] getLegalMoves(Square current, Square[][] boardstate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Color getColor() {
        return this.color;
    }

}
