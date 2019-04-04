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
public class Player {
    private Square[] occupiedSquares; //which Squares are this players pieces occupying
    private Color color;
    private Piece[] pieces; //remaining pieces for the player
    private Integer piececount;
    private Square[][] boardstate;
    
    public Player(Color color, Square[][] boardstate){
        this.color = color;
        this.piececount = 16;
        this.occupiedSquares = new Square[piececount];
        this.pieces = new Piece[piececount];
        this.boardstate = boardstate;
        if (this.color == Color.WHITE){
            initializeWhitePlayer();
        } else {
            initializeBlackPlayer();
        }
    }
    
    private void initializeWhitePlayer(){
        int count = 0;
        for (int r=0; r<2; r++){
            for (int f=0; f<=7; f++){
                occupiedSquares[count] = this.boardstate[r][f];
                pieces[count] = this.boardstate[r][f].getPiece();
                count++;
            }
        }
    }
    
    private void initializeBlackPlayer(){
        int count = 0;
        for (int r=6; r<8; r++){
            for (int f=0; f<=7; f++){
                occupiedSquares[count] = this.boardstate[r][f];
                pieces[count] = this.boardstate[r][f].getPiece();
                count++;
            }
        }
    }
    public void updatePlayer(Square[][] boardstate){
        this.occupiedSquares = new Square[piececount];
        this.pieces = new Piece[piececount];
        int count =0;
        for (int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                if (!boardstate[r][f].isEmpty() && this.color == boardstate[r][f].getPiece().getColor()){
                    occupiedSquares[count] = this.boardstate[r][f];
                    pieces[count] = this.boardstate[r][f].getPiece();
                    count++;
                }
            }
        }
        if (count != piececount){ //if since last update a piece has been captured update number of pieces and clone arrays to new ones with correct size
            this.piececount = count; 
            Square[] occupiedSquaresClone = new Square[piececount];
            Piece[] piecesClone = new Piece[piececount];
            for (int i=0; i<piececount; i++){
                occupiedSquaresClone[i] = this.occupiedSquares[i];
                piecesClone[i] = this.pieces[i];
            }
            this.occupiedSquares = occupiedSquaresClone;
            this.pieces = piecesClone;
        }
    }
    public Move[] possibleMoves(){
        Move[][] moves = new Move[16][];
        int totalmoves = 0;
        for (int i=0; i<occupiedSquares.length; i++){
            if (occupiedSquares[i] != null){
                moves[i]=occupiedSquares[i].getPiece().getLegalMoves(occupiedSquares[i], boardstate);
                for(int m=0; m<moves[i].length; m++){
                    if (moves[i][m] == null){
                        break;
                    } else {
                        totalmoves++;
                    }
                }
            }
        }
        Move[] movesToReturn = new Move[totalmoves];
        int moveindex = 0;
        for (int i=0; i<occupiedSquares.length; i++){
            for(int m=0; m<moves[i].length; m++){
                if (moves[i][m] == null){
                    break;
                } else {
                    movesToReturn[moveindex] = moves[i][m];
                    moveindex++;
                }
            }
        }
        return movesToReturn;
     }
}
