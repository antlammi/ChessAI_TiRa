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
public class Square {
    private Piece piece;
    private int rank;
    private int file;

    /**
     *
     * @param rank
     * @param file
     */
    public Square(int rank, int file){
        this.piece = null;
        this.rank = rank;
        this.file = file;
    }
    
    /**
     *
     */
    public void leave(){
        this.piece = null;
    }
    

    /**
     * New piece enters the Square
     * @param piece
     */
    public void enter(Piece piece){
        this.piece = piece;
    }

    /**
     * Identical to enter but improves code readability.
     * @param piece
     */
    public void capture(Piece piece){
        this.piece = piece;
    }
    
    /**
     *
     * @return
     */
    public Piece getPiece(){
        return this.piece;
    }
   

    /**
     *
     * @return
     */
    public Boolean isEmpty(){
        return this.piece == null;
    }
    
    /**
     *
     * @return
     */
    public Integer getRank(){
        return this.rank;
    }
    
    /**
     *
     * @return
     */
    public Integer getFile(){
        return this.file;
    }
    
    @Override 
    public String toString(){
        if (isEmpty()){
            return "EMPTY";
        } else {
            return piece.toString();
        }
    }
}
