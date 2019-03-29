/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.model;

/**
 *
 * @author antlammi
 */
public class Move {
    private Piece piece;
    private Square current;
    private Square destination;
    private Square[][] boardstate;
    
    public Move(Square[][] boardstate){
         this.boardstate = boardstate;
    }
    
    public void constructMove(Piece piece, Square current, Square destination){
        this.piece = piece;
        this.current = current;
        this.destination = destination;
       
    }
 
    
    public void execute(){
        current.leave();
        if (destination.isEmpty()){
            destination.enter(piece);
        } else {
            destination.capture(piece);
        }
        
    }
    public Square getCurrentSquare(){
        return current;
    }
    public Square getDestinationSquare(){
        return destination;
    }
    
}
