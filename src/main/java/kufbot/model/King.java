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
class King implements Piece {
    public final Color color;

    public King(Color color) {
        this.color = color;
    }

    @Override
    public ArrayList<Move> getMoves() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Move> getLegalMoves() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Boolean isInCheck(){
        return false;
    }
    @Override 
    public String toString(){
        return color + " KING"; 
    }
}
