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
public class Rook implements Piece {

    /**
     *
     */
    public final Color color;
    private int legalcount;
    private Move[] legalMoves;
    private Boolean moved;
    private Square current; 
    private Square[][] boardstate;
    private Double baseValue;
    private Double value;
    
    /**
     *
     * @param color
     * @param initial
     * @param boardstate
     */
    public Rook(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.current = initial;
        this.boardstate = boardstate;
        this.moved = false;
        this.baseValue = 5.0;
        this.value = baseValue;
    }

    /**
     * 
     * @return true if Rook has been moved, false if not.
     */
    public Boolean getMoved(){
        return this.moved;
    }

    /**
     *
     * @param moved
     */
    public void setMoved(Boolean moved){
          this.moved = moved;
    }

    /**
     *
     * @param newSquare
     */
    @Override
    public void setSquare(Square newSquare){
        this.current = newSquare;
    }

    /**
     * Provides an array of possible moves based on Queen's movement rules in the position
     * @return Move[]
     */
    @Override
    public Move[] getMoves() {
        Integer rank = current.getRank();
        Integer file = current.getFile();
        Move[] possibleMoves = new Move[14];
        Integer moveCount = 0;
        for (int f = 0; f <= 7; f++) {
            if (f != file - 1) {
                possibleMoves[moveCount] = new Move(boardstate);
                possibleMoves[moveCount].constructMove(this, current, boardstate[rank - 1][f]);
                moveCount++;
            }
        }

        for (int r = 0; r <= 7; r++) {
            if (r != rank - 1) {
                possibleMoves[moveCount] = new Move(boardstate);
                possibleMoves[moveCount].constructMove(this, current, boardstate[r][file - 1]);
                moveCount++;
            }
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return color + " ROOK";
    }

    /**
     * Checks legality for moves on the same rank.
     * @param movesToCheck
     */
    public void findLegalMovesOnSameRank(Move[] movesToCheck) {
        for (int i = 0; i < movesToCheck.length / 2 ; i++) {
            Square destination = movesToCheck[i].getDestinationSquare();

            int rank = current.getRank() - 1;
            int cf = current.getFile() - 1;
            int df = destination.getFile() - 1;
           
            if (cf > df) {  //jos alkuruudun sarake on suurempi kuin määränpään
                for (int f = cf-1; f >= df; f--) { //lähestytään määränpäätä
                    if (!boardstate[rank][f].isEmpty()) { //jos tämänhetkisellä ruudulla on palanen
                        if (f == df) {  //jos nykyinen sarake on vastaa määränpäätä
                            if (this.color != destination.getPiece().getColor()) { //jos palaset ovat eri värisiä, voidaan se syödä ja siirtyä ruudulle
                                legalMoves[legalcount] = movesToCheck[i];
                                legalcount++;
                            }
                        } else { //tiellä on jokin palanen, jonka yli ei voida siirtyä
                            break;
                        }
                    } else if (f == df) {  //jos ruutu on tyhjä ja vastaa saraketta, voidaan ruudulle siirtyä
                        legalMoves[legalcount] = movesToCheck[i];
                        legalcount++;
                    }
                }

            } else {
                for (int f = cf + 1; f <= df; f++) {
                    if (!boardstate[rank][f].isEmpty()) {
                        if (f == df) {
                            if (this.color != destination.getPiece().getColor()) {
                                legalMoves[legalcount] = movesToCheck[i];
                                legalcount++;
                            }
                        } else {
                            break;
                        }
                    } else if (f == df) {
                        legalMoves[legalcount] = movesToCheck[i];
                        legalcount++;
                    }
                }
            }

        }

    }

    /**
     * Checks legality for moves on same file
     * @param movesToCheck candidate moves to check
     */
    public void findLegalMovesOnSameFile(Move[] movesToCheck) {
        
        for (int i = movesToCheck.length / 2; i < movesToCheck.length; i++) {
            Square destination = movesToCheck[i].getDestinationSquare();
            int file = current.getFile() - 1;
            int cr = current.getRank() - 1;
            int dr = destination.getRank() - 1;
            if (cr > dr) {
                for (int r = cr-1; r >= dr; r--) {
                    if (!boardstate[r][file].isEmpty()) {
                        if (r == dr) {
                            if (this.color != destination.getPiece().getColor()) {
                                legalMoves[legalcount] = movesToCheck[i];
                                legalcount++;
                            }
                        } else {
                            break;
                        }
                    } else if (r == dr) {
                        legalMoves[legalcount] = movesToCheck[i];
                        legalcount++;
                    }
                }

            } else {
                for (int r = cr + 1; r <= dr; r++) {
                    if (!boardstate[r][file].isEmpty()) {
                        if (r == dr) {
                            if (this.color != destination.getPiece().getColor()) {
                                legalMoves[legalcount] = movesToCheck[i];
                                legalcount++;

                            }
                        } else {
                            break;
                        }
                    } else if (r == dr) {
                        legalMoves[legalcount] = movesToCheck[i];
                        legalcount++;
                    }
                }
            }
        }
    }
  
    /**
     ** Checks for legality in array of moves provided by getMoves.
     * If a Square is unreachable (blocked), or contains a Piece of the same color,
     * a move is considered illegal. Calls findLegalMovesOnSameRank() and 
     * findLegalMovesONSameFile()
     * @return Move[]
     */
    @Override
    public Move[] getLegalMoves() {
        Move[] movesToCheck = this.getMoves();
        this.legalMoves = new Move[movesToCheck.length];
        this.legalcount = 0;
        findLegalMovesOnSameRank(movesToCheck);
        findLegalMovesOnSameFile(movesToCheck);
        return legalMoves;
    }

    /**
     *
     * @param moves
     */
    @Override
    public void updateValue(Integer moves){    
        this.value = (baseValue+(1.0*moves/500));
       
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
