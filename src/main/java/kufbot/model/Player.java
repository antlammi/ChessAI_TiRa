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
    private Double score;

    public Player(Color color, Square[][] boardstate) {
        this.color = color;
        this.piececount = 16;
        this.occupiedSquares = new Square[piececount];
        this.pieces = new Piece[piececount];
        this.boardstate = boardstate;
        this.score = 0.0;
        updatePlayer();
    }

    public Square[][] getState() {
        return this.boardstate;
    }

    public void updatePlayer() {
        this.occupiedSquares = new Square[piececount];
        this.pieces = new Piece[piececount];
        int count = 0;
        this.score = 0.0;
        double opponentScore = 0.0;
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                if (!boardstate[r][f].isEmpty()) {
                    if (this.color == boardstate[r][f].getPiece().getColor()) {
                        occupiedSquares[count] = boardstate[r][f];

                        pieces[count] = boardstate[r][f].getPiece();

                        if (boardstate[r][f].getPiece().toString().contains("KING")) {
                            this.kingRank = r;
                            this.kingFile = f;
                        }
                        this.score += pieces[count].getValue();

                        count++;
                    } else {
                        opponentScore += boardstate[r][f].getPiece().getValue();
                    }
                }
            }
        }
        this.score = this.score - opponentScore;
        if (count != piececount) { //if since last update a piece has been captured update number of pieces and clone arrays to new ones with correct size
            this.piececount = count;
            Square[] occupiedSquaresClone = new Square[piececount];
            Piece[] piecesClone = new Piece[piececount];
            for (int i = 0; i < piececount; i++) {
                occupiedSquaresClone[i] = this.occupiedSquares[i];
                piecesClone[i] = this.pieces[i];
            }
            this.occupiedSquares = occupiedSquaresClone;
            this.pieces = piecesClone;
        }
    }

    public Move[] getPossibleMoves() {
        Move[][] moves = new Move[16][];
        int totalmoves = 0;
        for (int i = 0; i < occupiedSquares.length; i++) {
            if (occupiedSquares[i] != null) {
                moves[i] = occupiedSquares[i].getPiece().getLegalMoves();
                for (int m = 0; m < moves[i].length; m++) {
                    if (moves[i][m] == null) {
                        break;
                    } else {
                        totalmoves++;
                    }
                }
            }
        }
        Move[] movesToReturn = new Move[totalmoves];
        int moveindex = 0;
        for (int i = 0; i < occupiedSquares.length; i++) {
            for (int m = 0; m < moves[i].length; m++) {
                if (moves[i][m] == null) {
                    break;
                } else {
                    movesToReturn[moveindex] = moves[i][m];
                    moveindex++;
                }
            }
        }
        return movesToReturn;
    }

    public Move[] getLegalMoves() { //checks possible moves for king being in check after
        Move[] moves = getPossibleMoves();
        Move[][] legalMoves = new Move[3][];
        legalMoves[0] = new Move[moves.length]; //Low priority moves
        legalMoves[1] = new Move[moves.length]; //Medium priority moves
        legalMoves[2] = new Move[moves.length]; //High priority moves
        Integer capturecount = 0;
        Integer pawncapturecount = 0;
        Integer emptycount = 0;
        Integer legalcount = 0;
        for (int i = 0; i < moves.length; i++) {
            Integer kr = this.kingRank;
            Integer kf = this.kingFile;
            Move current = moves[i];
            if (current.getPiece().toString().contains("KING")) {  //if move in question is for king, update location
                kr = current.getDestinationSquare().getRank() - 1;  //rank always is the same as move destination
                if (current.getCastle()) {  //if move is a castling move, king doesnt move to the destination file
                    if (current.getDestinationSquare().getFile() - 1 == 7) {
                        kf = 6;
                    } else {
                        kf = 2;
                    }
                } else {

                    kf = current.getDestinationSquare().getFile() - 1;
                }
            }
            //All the required variables are copied so the internal state stays in tact.
            Square[][] stateToCheck = Board.copyBoardstate(this.boardstate);

            Move moveToCheck = current.cloneMove(stateToCheck);

            moveToCheck.execute();

            King king = (King) stateToCheck[kr][kf].getPiece();
            if (!king.isInCheck(stateToCheck[kr][kf])) {
                if (current.getDestinationSquare().isEmpty()) {
                    legalMoves[0][emptycount] = moves[i];
                    emptycount++;
                    legalcount++;
                } else if (current.getDestinationSquare().getPiece().toString().contains("PAWN")){
                    legalMoves[1][pawncapturecount] = moves[i];
                    pawncapturecount++;
                    legalcount++;
                } else if (!current.getDestinationSquare().getPiece().toString().contains("KING")) { //Kings are never captured in a game of chess.
                    legalMoves[2][capturecount] = moves[i];
                    capturecount++;
                    legalcount++;
                }
            }
        }
        //Moves including captures are added to the front of array, pawn captures in middle improves performance of minmaxAB
        Move[] movesToReturn = new Move[legalcount];
        for (int i=0; i<capturecount; i++){
            movesToReturn[i] = legalMoves[2][i];
        }
        for (int i= capturecount; i<(capturecount+pawncapturecount); i++){
            movesToReturn[i] = legalMoves[1][i-capturecount];
        }
        for (int i = (capturecount+pawncapturecount); i < legalcount; i++) {
            movesToReturn[i] = legalMoves[0][i-(pawncapturecount +capturecount)];
        }
        return movesToReturn;
    }

    public Player clonePlayer(Square[][] stateCopy) {
        return new Player(this.color, stateCopy);
    }

    public Double getScore() {
        return this.score;
    }

    public Color getColor() {
        return this.color;
    }

    public void setScore(Double score) { //intended to be used only in case of mates
        this.score = score;
    }

    public Integer getPieceCount() {
        return this.piececount;
    }

    public Integer getKingRank() {
        return this.kingRank;
    }

    public Integer getKingFile() {
        return this.kingFile;
    }
}
