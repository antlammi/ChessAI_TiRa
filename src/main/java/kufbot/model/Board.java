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

public class Board {
    
private Square[][] squares;
private PieceFactory pf;
    public Board(){
        squares = new Square[8][8];
        pf = new PieceFactory();
        initializeSquares();
        initializePieces(Color.WHITE);
        initializePieces(Color.BLACK);
    }

    private void initializeSquares() {
        
        for (int r=0; r<=7; r++){ //r for rank
            for(int f=0; f<=7; f++){ //f for file
                squares[r][f] = new Square(r+1, f+1);
            }
        }
    }
    private void initializePieces(Color color) {
        int backrank;
        int firstrank;
        if (color == Color.WHITE){
            backrank = 0;
            firstrank = 1;
        } else {
            backrank = 7;
            firstrank = 6;
        }
        
        for (int f=0; f<=7; f++){
            Piece frp = pf.getPiece("PAWN", color, squares[firstrank][f], squares); //first rank piece
            Piece brp; //back rank piece
            if (f==0 ||f==7){
                brp = pf.getPiece("ROOK", color, squares[backrank][f], squares);
            } else if (f==1||f==6){
                brp = pf.getPiece("KNIGHT", color, squares[backrank][f], squares);
            } else if (f==2||f==5){
                brp = pf.getPiece("BISHOP", color, squares[backrank][f], squares);
            } else if (f==3){
                brp = pf.getPiece("QUEEN", color, squares[backrank][f], squares);
            } else {
                brp = pf.getPiece("KING", color, squares[backrank][f], squares);
            }
            
            squares[firstrank][f].enter(frp);
            squares[backrank][f].enter(brp);       
        }
    }
    
    public Square[][] getBoardState(){
        return this.squares;
    }
    public static Square[][] copyBoardstate(Square[][] currentstate){ //moved from Player. Required to prevent fringe case bug in King.
        PieceFactory copyFactory = new PieceFactory();
        Square[][] copy = new Square[8][8];
        for(int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                copy[r][f] = new Square(r+1,f+1);
                
            }
        }
          for(int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                if (!currentstate[r][f].isEmpty()){
                    Piece toClone = currentstate[r][f].getPiece();
                    Piece clone = copyFactory.getPiece(toClone.toString().substring(6), toClone.getColor(), copy[r][f], copy);
                    copy[r][f].enter(clone);
                }
               
                
            }
        }
        
        return copy;
    }
    

}
