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
    private String[] files = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private PieceFactory pf;
    private Boolean pawnCapture;
    private Player player;
    private Piece captured;

    public Move(Square[][] boardstate) {
        this.boardstate = boardstate;
        this.pf = new PieceFactory();
        this.pawnCapture = false;
    }

    public void constructMove(Piece piece, Square current, Square destination) {
        this.piece = piece;
        this.current = current;
        this.destination = destination;

    }

    public void setPawnCapture() {
        this.pawnCapture = true;
    }

    //Scores should probably be calculated only within this class. set/get player only for access to scores. Future refactor perhaps.
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void promotePawn(Piece piece, String pieceType) {
        Piece promotedPiece = pf.getPiece(pieceType, piece.getColor(), this.current, this.boardstate);
        current.leave();
        current.enter(promotedPiece);
        this.piece = promotedPiece;
    }

    /*public void constructKingSideCastle(Piece piece, Square current, Square destination){
   
    }
    public void constructQueenSideCastle(Piece piece, Square current, Square destination){
    }
     */
    public void execute() {
        if (current.getPiece().toString().contains("ROOK")) {
            Rook rook = (Rook) current.getPiece();
            rook.wasMoved();
        }
        Boolean isKing = false;
        if (current.getPiece().toString().contains("KING")) {
            King king = (King) current.getPiece();
            king.wasMoved();
            isKing = true;
        }

        if (current.getPiece().toString().equals("WHITE PAWN") && current.getRank() == 7) {
            promotePawn(current.getPiece(), "QUEEN"); //automatically promotes to queen
        } else if (current.getPiece().toString().equals("BLACK PAWN") && current.getRank() == 2) {
            promotePawn(current.getPiece(), "QUEEN");
        }
        current.leave();
        if (destination.isEmpty()) {
            destination.enter(piece);
            piece.setSquare(destination);

        } else {
            this.captured = destination.getPiece();
            destination.capture(piece);
            piece.setSquare(destination);
        }
        if (!isKing) {
            finetunePieceValue();
        }
    }

    public void rollback() {
        destination.leave();
        current.enter(piece);
        piece.setSquare(current);
        if (captured != null) {
            destination.enter(captured);
            captured.setSquare(destination);
        }

    }

    private void finetunePieceValue() {
        if (this.piece.toString().contains("PAWN")) {
            this.piece.setValue(destination.getRank());
        } else {
            Integer movecount = 0;
            Move[] moves = this.piece.getLegalMoves();
            for (int i = 0; i < moves.length; i++) {
                if (moves[i] != null) {
                    movecount++;
                } else {
                    break;
                }
            }
            this.piece.setValue(movecount);
        }
    }

    //Tämä koodinpätkä alkoi esiintyä monessa paikkaa, tämä tuntui loogiselta paikalta sille.
    public Move cloneMove(Square[][] boardstatecopy) {
        Move clone = new Move(boardstatecopy);
        Square curSQCopy = boardstatecopy[current.getRank() - 1][current.getFile() - 1];
        Square desSQCopy = boardstatecopy[destination.getRank() - 1][destination.getFile() - 1];
        clone.constructMove(curSQCopy.getPiece(), curSQCopy, desSQCopy);

        return clone;
    }

    public Square[][] getState() {
        return this.boardstate;
    }

    public Piece getPiece() {
        return piece;
    }

    public Square getCurrentSquare() {
        return current;
    }

    public Square getDestinationSquare() {
        return destination;
    }

    public Boolean getPawnCapture() {
        return this.pawnCapture;
    }

    @Override
    public String toString() {
        return files[this.current.getFile() - 1] + this.current.getRank() + files[this.destination.getFile() - 1] + this.destination.getRank();
    }
}
