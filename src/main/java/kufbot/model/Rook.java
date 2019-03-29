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
class Rook implements Piece {

    public final Color color;
    public Rook(Color color) {
        this.color = color;
    }
    
    @Override
    public Move[] getMoves(Square current, Square[][] boardstate) {
        Integer rank = current.getRank();
        Move[] possibleMoves = new Move[16];
        for (int f=0; f<=7; f++){
            
            possibleMoves[f] = new Move(boardstate);
            possibleMoves[f].constructMove(this, current, boardstate[rank-1][f]);
        }
        
        Integer file = current.getFile();
        for (int r=0; r<=7; r++){
            possibleMoves[r+8] = new Move(boardstate);
            possibleMoves[r+8].constructMove(this, current, boardstate[r][file-1]);
        }
 
        return possibleMoves;
    }

    
    @Override 
    public String toString(){
        return color + " ROOK"; 
    }

    @Override
    public Move[] getLegalMoves(Square current, Square[][] boardstate) {
        Move[] movesToCheck = this.getMoves(current, boardstate);
        Move[] legalMoves = new Move[movesToCheck.length];
        int legalcount = 0;
        
        for (int i=0; i<movesToCheck.length; i++){
           Square destination = movesToCheck[i].getDestinationSquare();
           if (current.getRank() == destination.getRank()){     //jos määränpään rivi on sama tutkimme vain sarakkeita
               int rank = current.getRank()-1;
               int cf = current.getFile()-1;
               int df = destination.getFile()-1;
               if (cf > df){  //jos alkuruudun sarake on suurempi kuin määränpään
                   for (int f=cf; f >= df; f--){ //lähestytään määränpäätä
                       if (!boardstate[rank][f].isEmpty()){ //jos tämänhetkisellä ruudulla on palanen
                           if (f==df){  //jos nykyinen sarake on vastaa määränpäätä
                               if (this.color != destination.getPiece().getColor()){ //jos palaset ovat eri värisiä, voidaan se syödä ja siirtyä ruudulle
                                   legalMoves[legalcount] = movesToCheck[i];
                                   legalcount++;
                               }
                           } else { //tiellä on jokin palanen, jonka yli ei voida siirtyä
                               break;
                           }
                       } else if  (f==df){  //jos ruutu on tyhjä ja vastaa saraketta, voidaan ruudulle siirtyä
                           legalMoves[legalcount] = movesToCheck[i];
                           legalcount++;
                       }
                   }
                  
               } else {
                   for (int f=cf; f<=df; f++){
                       if (!boardstate[rank][f].isEmpty()){
                           if (f==df){
                               if (this.color != destination.getPiece().getColor()){
                                   legalMoves[legalcount] = movesToCheck[i];
                                   legalcount++;
                               }
                           } else {
                               break;
                           }
                       } else if (f==df){
                           legalMoves[legalcount] = movesToCheck[i];
                           legalcount++;
                       }
                   }
               }
           } else {
               int file = current.getFile()-1;
               int cr = current.getRank()-1;
               int dr = destination.getRank()-1;
                if (cr > dr){
                   for (int r=cr; r >= dr; r--){
                       if (!boardstate[r][file].isEmpty()){
                           if (r==dr){
                               if (this.color != destination.getPiece().getColor()){
                                   legalMoves[legalcount] = movesToCheck[i];
                                   legalcount++;
                               }
                           } else {
                               break;
                           }
                       }else if (r==dr){
                           legalMoves[legalcount] = movesToCheck[i];
                           legalcount++;
                       }
                   }
                  
               } else {
                    for (int r=cr; r <= dr; r++){
                       if (!boardstate[r][file].isEmpty()){
                           if (r==dr){
                               if (this.color != destination.getPiece().getColor()){
                                   legalMoves[legalcount] = movesToCheck[i];
                                   legalcount++;
                                   
                               }
                           } else {
                               break;
                           }
                       }else if (r==dr){
                           legalMoves[legalcount] = movesToCheck[i];
                           legalcount++;
                       }
                   }
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
