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

    public Piece getPiece(String pieceType, Color color) {
        switch (pieceType) {
            case "KING":
                return new King(color);
            case "QUEEN":
                return new Queen(color);
            case "ROOK":
                return new Rook(color);
            case "BISHOP":
                return new Bishop(color);
            case "KNIGHT":
                return new Knight(color);
            case "PAWN":
                return new Pawn(color);
            default:
                return null;
        }
    }
}
