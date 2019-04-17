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
    private Rook rook;
    private Boolean rookPreviouslyMoved;
    private Boolean castle;
    private Boolean previouslyMoved;

    public Move(Square[][] boardstate) {
        this.boardstate = boardstate;
        this.pf = new PieceFactory();
        this.pawnCapture = false;
        this.castle = false;
        this.previouslyMoved = false;
    }

    public void constructMove(Piece piece, Square current, Square destination) {
        this.piece = piece;
        this.current = current;
        this.destination = destination;

    }

    public void setPawnCapture() {
        this.pawnCapture = true;
    }

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

    public void constructCastle(Piece king, Rook rookH, Square current, Square destination) {
        this.piece = king;
        this.rook = rookH;
        this.current = current;
        this.destination = destination;
        this.castle = true;
    }

    private void executeCastle() {
        rookPreviouslyMoved = rook.getMoved();
        Integer r = current.getRank() - 1;
        current.leave();
        destination.leave();
        King king = (King) piece;
        if (destination.getFile() - 1 == 0) {
            boardstate[r][2].enter(piece);
            piece.setSquare(boardstate[r][2]);
            boardstate[r][3].enter(rook);
            rook.setSquare(boardstate[r][3]);

        } else {
            boardstate[r][6].enter(piece);
            piece.setSquare(boardstate[r][6]);
            boardstate[r][5].enter(rook);
            rook.setSquare(boardstate[r][5]);
        }
        king.setCastled(true);

    }

    public Boolean getCastle() {
        return this.castle;
    }

    public void execute() {
        if (current.getPiece().toString().contains("ROOK")) {

            Rook rookToMove = (Rook) current.getPiece();
            previouslyMoved = rookToMove.getMoved();
            rookToMove.setMoved(true);
        }
        Boolean isKing = false;
        if (current.getPiece().toString().contains("KING")) {
            King king = (King) current.getPiece();
            previouslyMoved = king.getMoved();
            if (this.castle) {
                executeCastle();
                king.setMoved(true);
                return;
            }
            king.setMoved(true);
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

    private void rollbackCastle() {
        destination.leave();
        current.leave();

        current.enter(piece);
        piece.setSquare(current);

        destination.enter(rook);
        rook.setSquare(destination);
        King king = (King) piece;
        king.setCastled(false);
        if (!previouslyMoved) {
            king.setMoved(false);
        }
        if (!rookPreviouslyMoved) {
            rook.setMoved(false);
        }
    }

    public void rollback() {

        if (castle) {
            rollbackCastle();
            return;
        }
        destination.leave();
        current.enter(piece);
        piece.setSquare(current);
        if (!previouslyMoved) {
            if (current.getPiece().toString().contains("ROOK")) {
                Rook movedrook = (Rook) piece;
                movedrook.setMoved(false);
            } else if (current.getPiece().toString().contains("KING")) {
                King movedking = (King) piece;
                movedking.setMoved(false);
            }
        }

        if (captured != null) {
            destination.enter(captured);
            captured.setSquare(destination);
        }

    }

    private void finetunePieceValue() {
        if (this.piece.toString().contains("PAWN")) {
            this.piece.updateValue(destination.getRank());
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
            this.piece.updateValue(movecount);
        }
    }

    //Tämä koodinpätkä alkoi esiintyä monessa paikkaa, tämä tuntui loogiselta paikalta sille.
    public Move cloneMove(Square[][] boardstatecopy) {
        Move clone = new Move(boardstatecopy);
        Square curSQCopy = boardstatecopy[current.getRank() - 1][current.getFile() - 1];
        Square desSQCopy = boardstatecopy[destination.getRank() - 1][destination.getFile() - 1];
        if (castle) {
            clone.constructCastle(curSQCopy.getPiece(), (Rook) desSQCopy.getPiece(), curSQCopy, desSQCopy);
            clone.previouslyMoved = this.previouslyMoved;
            clone.rookPreviouslyMoved = this.rookPreviouslyMoved;
        } else {
            clone.constructMove(curSQCopy.getPiece(), curSQCopy, desSQCopy);
            clone.previouslyMoved = this.previouslyMoved;
        }
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
