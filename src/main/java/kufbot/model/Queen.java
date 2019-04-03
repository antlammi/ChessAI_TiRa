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
class Queen implements Piece {

    public final Color color;
    private Rook rook;
    private Bishop bishop;
    public Queen(Color color) {
        this.color = color;
        this.rook = new Rook(this.color);
        this.bishop = new Bishop(this.color);
    }

    @Override
    public String toString() {
        return color + " QUEEN";
    }

    @Override
    public Move[] getMoves(Square current, Square[][] boardstate) {
      
        
        //Kuningattaren mahdolliset siirrot ovat yhdistelmä lähetin ja tornin siirtoja, hyöydynnetään näiden siirtologiikkaa.
        
        Move[] possibleMoves = new Move[27];
        Move[] rookMoves = rook.getMoves(current, boardstate);
        Move[] bishopMoves = bishop.getMoves(current, boardstate);

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
    public Move[] getLegalMoves(Square current, Square[][] boardstate) {
        Move[] legalMoves = new Move[27];
        Move[] legalRookMoves = rook.getLegalMoves(current, boardstate);
        Move[] legalBishopMoves = bishop.getLegalMoves(current, boardstate);
        
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
    public Color getColor() {
        return this.color;
    }

}
