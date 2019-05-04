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
public class Queen implements Piece {

    /**
     *
     */
    public final Color color;
    private Square current;
    private Square[][] boardstate;
    private Double value;
    private final Double baseValue;

    /**
     *
     * @param color
     * @param initial
     * @param boardstate
     */
    public Queen(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.current = initial;
        this.boardstate = boardstate;
        this.baseValue = 9.0;
        this.value = baseValue;
        
    }

    /**
     *
     * @param newSquare
     */
    @Override
    public void setSquare(Square newSquare){
        this.current = newSquare;
    }
    @Override
    public String toString() {
        return color + " QUEEN";
    }

    /**
     * Provides an array of possible moves based on Queen's movement rules in the position
     * @return
     */
    @Override
    public Move[] getMoves() {
        Rook rook = new Rook(this.color, current, boardstate);
        Bishop bishop = new Bishop(this.color, current, boardstate);
        
        //Kuningattaren mahdolliset siirrot ovat yhdistelmä lähetin ja tornin siirtoja, hyöydynnetään näiden siirtologiikkaa.
        
        Move[] possibleMoves = new Move[27];
        Move[] rookMoves = rook.getMoves();
        Move[] bishopMoves = bishop.getMoves();

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

    /**
     * Checks for legality in array of moves provided by getMoves.
     * If a Square is unreachable (blocked), or contains a Piece of the same color,
     * a move is considered illegal.
     * @return Move[]
     */
    @Override
    public Move[] getLegalMoves() {
        Rook rook = new Rook(this.color, current, boardstate);
        Bishop bishop = new Bishop(this.color, current, boardstate);
        
        Move[] legalMoves = new Move[27];
        Move[] legalRookMoves = rook.getLegalMoves();
        Move[] legalBishopMoves = bishop.getLegalMoves();
        
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

    /**
     * Updates piece value based on number of moves available.
     * @param moves
     */
    @Override
    public void updateValue(){
        this.value = baseValue+(1.0*getLegalMoves().length/200);
    }
    
    /**
     *
     * @return
     */
    @Override
    public Color getColor() {
        return this.color;
    }
    
    /**
     *
     * @return
     */
    @Override
    public Double getValue() {
        return this.value;
    }

    /**
     *
     * @param value
     */
    @Override
    public void setValue(Double value) {
        this.value = value;
    }
}
