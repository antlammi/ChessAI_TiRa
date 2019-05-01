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
public class PieceFactory {

    /**
     * Used when building new Pieces throughout app.
     * @param pieceType
     * @param color
     * @param current
     * @param boardstate
     * @return
     */
    public Piece getPiece(String pieceType, Color color, Square current, Square[][] boardstate) {
        switch (pieceType) {
            case "KING":
                return new King(color, current, boardstate);
            case "QUEEN":
                return new Queen(color, current, boardstate);
            case "ROOK":
                return new Rook(color, current, boardstate);
            case "BISHOP":
                return new Bishop(color, current, boardstate);
            case "KNIGHT":
                return new Knight(color, current, boardstate);
            case "PAWN":
                return new Pawn(color, current, boardstate);
            default:
                return null;
        }
    }
}
