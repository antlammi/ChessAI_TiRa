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
    public Pawn(Color color) {
        this.color = color;
    }

    
    @Override 
    public String toString(){
        return color + " PAWN"; 
    }

    
    @Override
    public Move[] getMoves(Square current, Square[][] boardstate) { 
        Move[] possibleMoves;
        if (this.color == Color.WHITE){
            possibleMoves = getMovesWhite(current, boardstate);
        } else {
            possibleMoves = getMovesBlack(current, boardstate);
        }
        return possibleMoves;
    }
    
    public Move[] getMovesWhite(Square current, Square[][] boardstate){
        Integer rank = current.getRank();
        Integer file = current.getFile();
        Move[] possibleMovesWhite = new Move[4];
        Integer moveCount = 0;
        
        for (int f=file-2; f<=file; f++){
            possibleMovesWhite[moveCount] = new Move(boardstate);
            possibleMovesWhite[moveCount].constructMove(this, current, boardstate[rank][f]);
            moveCount++;     
        }
        if (rank == 2){
            possibleMovesWhite[moveCount] = new Move(boardstate);
            possibleMovesWhite[moveCount].constructMove(this, current, boardstate[rank+1][file-1]);
            moveCount++;
        }
        return possibleMovesWhite;
    }
    
    public Move[] getMovesBlack(Square current, Square[][] boardstate){
        Integer rank = current.getRank();
        Integer file = current.getFile();
        Move[] possibleMovesBlack = new Move[4];
        Integer moveCount = 0;
        for (int f=file-2; f<=file; f++){
                possibleMovesBlack[moveCount] = new Move(boardstate);
                possibleMovesBlack[moveCount].constructMove(this, current, boardstate[rank-2][f]);
                moveCount++;
        }
        if (rank==6){ 
                possibleMovesBlack[moveCount] = new Move(boardstate);
                possibleMovesBlack[moveCount].constructMove(this, current, boardstate[rank-3][file-1]);
                moveCount++;
        }
       
        return possibleMovesBlack;
    }
    
    @Override
    public Move[] getLegalMoves(Square current, Square[][] boardstate) {
        Move[] movesToCheck = getMoves(current,  boardstate);
        Move[] legalMoves = new Move[movesToCheck.length];
        
        int legalcount = 0;
        
        for (int i=0; i<movesToCheck.length; i++){
            
            Move currentMove = movesToCheck[i];
            if (currentMove != null){
                Square destination = currentMove.getDestinationSquare();
                int df = destination.getFile()-1;
                int dr = destination.getRank()-1;
                
                if (current.getFile() == destination.getFile()){ //jos siirrytään eteenpäin
                    if (dr-current.getRank()-1 == 2){  //jos aloitussiirto
                        if (current.getRank() == 2){ //jos aloitussiirto valkoisilla
                            if (boardstate[dr][df].isEmpty() && boardstate[dr-1][df].isEmpty()){ //jos välissä oleva ruutu ja määränpää tyhjiä
                                legalMoves[legalcount] = currentMove;
                                legalcount++;
                            }
                        } else if (current.getRank() == 6){ //jos aloitussiirto mustilla
                            if (boardstate[dr][df].isEmpty() && boardstate[dr+1][df].isEmpty()){ //jos välissä oleva ruutu ja määränpää tyhjiä
                                legalMoves[legalcount] = currentMove;
                                legalcount++;
                            }
                        }

                    }
                    if (boardstate[dr][df].isEmpty()){ //jos määränpää tyhjä
                        legalMoves[legalcount] = currentMove;
                        legalcount++;
                    }
                } else { // jos siirrytään vinosuuntaan
                    if (!boardstate[dr][df].isEmpty()){ //jos määränpää ei ole tyhjä
                        if (boardstate[dr][df].getPiece().getColor() != this.color){ //ja kyseessä ei ole oma palanen
                            legalMoves[legalcount] = currentMove;
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
