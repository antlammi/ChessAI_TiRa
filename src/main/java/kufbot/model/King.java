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
    private Square current;
    
    public King(Color color) {
        this.color = color;
    }

    
   
    @Override 
    public String toString(){
        return color + " KING"; 
    }

     public Boolean isInCheck(Square toCheck, Square[][] boardstate){
        return false;
    }
     
    @Override
    public Move[] getMoves(Square current, Square[][] boardstate) {
        Move[] possibleMoves = new Move[8];
        Integer movecount = 0;
        
        Integer rank = current.getRank()-1;
        Integer file = current.getFile()-1;
        
        for (int f=file-1; f<=file+1; f++){ //yhden ruudun verran vasemmalta yhden ruudun verran oikealle
            for (int r=rank-1; r<=rank+1; r++){ //yhden ruudun verran alhaalta yhden ruudun verran ylös
                if ((f>=0 && f<= 7) && (r>=0 && r<=7)){ //jos määränpää on pöydän rajojen sisällä
                    if (f != file|| r != rank){ //ja määränpää ei ole nykyinen ruutu
                        possibleMoves[movecount] = new Move(boardstate);
                        possibleMoves[movecount].constructMove(this, current, boardstate[r][f]);
                        movecount++;
                    }
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public Move[] getLegalMoves(Square current, Square[][] boardstate) {
        Move[] movesToCheck = getMoves(current, boardstate);
        Move[] legalMoves = new Move[movesToCheck.length];
        Integer legalcount = 0;
        
        for (int i=0; i<movesToCheck.length; i++){
            if (movesToCheck[i] == null){
                break;
            }
            
            Move currentMove = movesToCheck[i];
            Square destination = currentMove.getDestinationSquare();
            if (destination.isEmpty()){
                if (!isInCheck(destination, boardstate)){
                    legalMoves[legalcount] = currentMove;
                    legalcount++;
                }
            } else if(destination.getPiece().getColor() != this.color){
                if (!isInCheck(destination, boardstate)){
                    legalMoves[legalcount] = currentMove;
                    legalcount++;
                }
            }
        }
        return legalMoves;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

   
}
