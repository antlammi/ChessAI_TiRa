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
public interface Piece {
    public Move[] getMoves(Square current, Square[][] boardstate);
    public Move[] getLegalMoves(Square current, Square[][] boardstate);
    public Color getColor();
    
}
