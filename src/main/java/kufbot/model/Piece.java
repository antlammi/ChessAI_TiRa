/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.model;
import kufbot.model.Color;
import java.util.ArrayList;

/**
 *
 * @author antlammi
 */
public interface Piece {
    public ArrayList<Move> getMoves();
    public ArrayList<Move> getLegalMoves();
    
}
