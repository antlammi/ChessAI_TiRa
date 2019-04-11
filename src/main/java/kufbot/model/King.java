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
public class King implements Piece {
    public final Color color;
    private Boolean moved;
    private Square current;
    private Square[][] boardstate;
    public King(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.moved = false;
        this.current = initial;
        this.boardstate = boardstate;
    }

    @Override
    public void setSquare(Square newSquare){
        this.current = newSquare;
    }
   
    @Override 
    public String toString(){
        return color + " KING"; 
    }
    public Boolean getMoved(){
        return this.moved;
    }
    public void wasMoved(){
        this.moved = true;
    }
    public Boolean isInCheck(Square kingLocation){
        for (int r=0; r<=7; r++){
            for (int f=0; f<=7; f++){
                Square location = boardstate[r][f]; 
                if (!location.isEmpty()){
                    if (location.getPiece().getColor() != this.color){ //opponent's piece
                        if (location.getPiece().toString().contains("KING")){ //Special case to prevent infinite loop
                            for (int i=-1; i<=1; i++){
                                if (kingLocation.getRank()-1 == r+i){
                                    if (kingLocation.getFile()-1 == f-1 ||kingLocation.getFile()-1 == f 
                                            ||kingLocation.getFile()-1 == f+1){ //not sure this actually works yet, to be tested
                                        return true;
                                    }
                                }
                            }
                            
                        } else {
                            Move[] moves = location.getPiece().getLegalMoves(); //list of legal moves for the piece
                            
                            for (int i=0; i<moves.length; i++){
                                if (moves[i] == null){
                                    break;
                                }
                                if (moves[i].getDestinationSquare() == kingLocation){ //King is in check if opponent can capture it with a legal move
                                   if (!moves[i].getPiece().toString().contains("PAWN")){ //every other piece can capture with all their legal moves
                                        return true;
                                   } else if (moves[i].getPawnCapture()){ //if the piece is a a pawn and the move is a capture return true, otherwise ignore
                                       return true;
                                   }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
     
    @Override
    public Move[] getMoves() {
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
    public Move[] getLegalMoves() {
        Move[] movesToCheck = getMoves();
        Move[] legalMoves = new Move[movesToCheck.length];
        Integer legalcount = 0;
        
        for (int i=0; i<movesToCheck.length; i++){
            if (movesToCheck[i] == null){
                break;
            }
            
            Move currentMove = movesToCheck[i];
            Square destination = currentMove.getDestinationSquare();
            if (destination.isEmpty()){
                if (!isInCheck(destination)){
                    legalMoves[legalcount] = currentMove;
                    legalcount++;
                }
            } else if(destination.getPiece().getColor() != this.color){
                if (!isInCheck(destination)){
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
