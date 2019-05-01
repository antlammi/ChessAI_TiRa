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

    /**
     * Returns all possible moves by Piece in question without checks for legality.
     * @return Move[]
     */
    public Move[] getMoves();

    /**
     * Checks for legality
     * @return Move[]
     */
    public Move[] getLegalMoves();

    /**
     * 
     * @param square
     */
    public void setSquare(Square square);

    /**
     *
     * @return
     */
    public Color getColor();

    /**
     * 
     * @return Value of the piece
     */
    public Double getValue();

    /**
     * Updates value of the piece based on position.
     * @param moves
     */
    public void updateValue(Integer moves); 

    /**
     * Sets a value for the piece, used when cloning pieces.
     * @param value
     */
    public void setValue(Double value);    
    
}
