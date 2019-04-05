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
    private String[] files = { "a", "b", "c", "d", "e", "f", "g", "h" };
    private PieceFactory pf;
    
    public Move(Square[][] boardstate){
         this.boardstate = boardstate;
         this.pf = new PieceFactory();
    }
    
    public void constructMove(Piece piece, Square current, Square destination){
        this.piece = piece;
        this.current = current;
        this.destination = destination;
       
    }
    
    public void promotePawn(Piece piece, String pieceType){
        Piece promotedPiece = pf.getPiece(pieceType, piece.getColor(), this.current, this.boardstate);
        current.leave();
        current.enter(promotedPiece);
        this.piece = promotedPiece;
    }
    
    public void execute(){
        if (current.getPiece().toString().contains("ROOK")){
            Rook rook = (Rook) current.getPiece();
            rook.wasMoved();
        }
     
        if (current.getPiece().toString().contains("KING")){
            King king = (King) current.getPiece();
            king.wasMoved();
        }
        
        
        if (current.getPiece().toString().equals("WHITE PAWN") && current.getRank() == 7){
            promotePawn(current.getPiece(), "QUEEN"); //automatically promotes to queen
        } else if (current.getPiece().toString().equals("BLACK PAWN") && current.getRank() == 2){
            promotePawn(current.getPiece(), "QUEEN");
        }
        current.leave();
        if (destination.isEmpty()){
            destination.enter(piece);
            piece.setSquare(destination);
        } else {
            destination.capture(piece);
            piece.setSquare(destination);
        }
        
    }
    public Square getCurrentSquare(){
        return current;
    }
    public Square getDestinationSquare(){
        return destination;
    }
    @Override
    public String toString(){
        return files[this.current.getFile()-1] + this.current.getRank() + files[this.destination.getFile()-1]+ this.destination.getRank();
    }
}
