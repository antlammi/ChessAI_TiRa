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
    private Integer kingRank;
    private Integer kingFile;
    private PieceFactory pf;
    public Player(Color color, Square[][] boardstate){
        this.color = color;
        this.piececount = 16;
        this.occupiedSquares = new Square[piececount];
        this.pieces = new Piece[piececount];
        this.boardstate = boardstate;
        this.pf = new PieceFactory();
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
                if (this.boardstate[r][f].getPiece().toString().contains("KING")){
                    this.kingRank = r;
                    this.kingFile = f;
                }
                count++;
            }
        }
    }
    
    private void initializeBlackPlayer(){
        int count = 0;
        for (int r=6; r<8; r++){
            for (int f=0; f<8; f++){
                occupiedSquares[count] = this.boardstate[r][f];
                pieces[count] = this.boardstate[r][f].getPiece();
                if (this.boardstate[r][f].getPiece().toString().contains("KING")){
                    this.kingRank = r;
                    this.kingFile = f;
                }
                count++;              
            }
        }
    }
    public void updatePlayer(Square[][] boardstate){
        this.occupiedSquares = new Square[piececount];
        this.pieces = new Piece[piececount];
        int count =0;
        for (int r=0; r<8  && count < piececount; r++){
            for (int f=0; f<8; f++){
                if (!boardstate[r][f].isEmpty() && this.color == boardstate[r][f].getPiece().getColor()){
                    occupiedSquares[count] = this.boardstate[r][f];
                    pieces[count] = this.boardstate[r][f].getPiece();
                    if (this.boardstate[r][f].getPiece().toString().contains("KING")){
                        this.kingRank = r;
                        this.kingFile = f;
                    }
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
                moves[i]=occupiedSquares[i].getPiece().getLegalMoves();
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
    
    public Move[] legalMoves(){ //checks possible moves for king being in check after
        Move[] moves = possibleMoves();
        Move[] legalMoves = new Move[moves.length];
        Integer legalcount = 0;
        for (int i=0; i<moves.length; i++){
            Integer kr = this.kingRank;
            Integer kf = this.kingFile;
            Move current = moves[i];
            if (current.getPiece().toString().contains("KING")){  //if move in question is for king, update location
                kr = current.getDestinationSquare().getRank()-1;
                kf = current.getDestinationSquare().getFile()-1;
            }
            //All the required variables are copied so the internal state stays in tact.
            Square[][] stateToCheck = copyBoardstate();
            
            Integer cr = current.getCurrentSquare().getRank()-1;
            Integer cf = current.getCurrentSquare().getFile()-1;
            Integer dr = current.getDestinationSquare().getRank()-1;
            Integer df = current.getDestinationSquare().getFile()-1;
            
            Move moveToCheck = new Move(stateToCheck);
            
            moveToCheck.constructMove(stateToCheck[cr][cf].getPiece(), stateToCheck[cr][cf], stateToCheck[dr][df]);
            
            moveToCheck.execute();
            King king = (King) stateToCheck[kr][kf].getPiece();
            if (!king.isInCheck(stateToCheck[kr][kf])){
                legalMoves[legalcount] = moves[i];
                legalcount++;
            }
            
        }
        Move[] movesToReturn = new Move[legalcount];
        for (int i=0; i<legalcount; i++){
            movesToReturn[i] = legalMoves[i];
        }
        return movesToReturn;
    }
    private Square[][] copyBoardstate(){
        Square[][] copy = new Square[8][8];
        for(int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                copy[r][f] = new Square(r+1,f+1);
                
            }
        }
          for(int r=0; r<8; r++){
            for (int f=0; f<8; f++){
                if (!this.boardstate[r][f].isEmpty()){
                    Piece toClone = this.boardstate[r][f].getPiece();
                    Piece clone = pf.getPiece(toClone.toString().substring(6), toClone.getColor(), copy[r][f], copy);
                    copy[r][f].enter(clone);
                }
               
                
            }
        }
        
        return copy;
    }
    public Integer getKingRank(){
        return this.kingRank;
    }
    public Integer getKingFile(){
        return this.kingFile;
    }
}
