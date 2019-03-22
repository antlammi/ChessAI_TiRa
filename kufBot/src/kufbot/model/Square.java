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
    public Square(){
        this.piece = null;
    }
    
    public void leave(){
        this.piece = null;
    }
    //mutulla sanoisin että myöhemmin hyödyllistä olla kaksi seuraavaa erillisinä funktioina,
    //mikäli materiaalista pidetään jossakin kirjaa. Tällä hetkellä toki identtiset.
    public void enter(Piece piece){
        this.piece = piece;
    }
    public void capture(Piece piece){
        this.piece = piece;
    }
    
    public Piece getPiece(){
        return this.piece;
    }
    //Luultavasti helpottaa koodin luettavuutta muualla.
    public Boolean isEmpty(){
        return this.piece == null;
    }
    
    @Override 
    public String toString(){
        if (isEmpty()){
            return "Empty";
        } else {
            return piece.toString();
        }
    }
}
