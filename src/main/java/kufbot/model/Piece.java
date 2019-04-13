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
    public Move[] getMoves();
    public Move[] getLegalMoves();
    public void setSquare(Square square);
    public Color getColor();
    public Double getValue();
    public void setValue(Integer moves);
}
