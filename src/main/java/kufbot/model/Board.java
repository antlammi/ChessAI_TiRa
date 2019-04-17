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
public class Board {

    private Square[][] squares;
    private PieceFactory pf;

    public Board() {
        squares = new Square[8][8];
        pf = new PieceFactory();
        initializeSquares();
        initializePieces(Color.WHITE);
        initializePieces(Color.BLACK);
    }

    private void initializeSquares() {

        for (int r = 0; r <= 7; r++) { //r for rank
            for (int f = 0; f <= 7; f++) { //f for file
                squares[r][f] = new Square(r + 1, f + 1);
            }
        }
    }

    private void initializePieces(Color color) {
        int backrank;
        int firstrank;
        if (color == Color.WHITE) {
            backrank = 0;
            firstrank = 1;
        } else {
            backrank = 7;
            firstrank = 6;
        }

        for (int f = 0; f <= 7; f++) {
            Piece frp = pf.getPiece("PAWN", color, squares[firstrank][f], squares); //first rank piece
            Piece brp; //back rank piece
            if (f == 0 || f == 7) {
                brp = pf.getPiece("ROOK", color, squares[backrank][f], squares);
            } else if (f == 1 || f == 6) {
                brp = pf.getPiece("KNIGHT", color, squares[backrank][f], squares);
            } else if (f == 2 || f == 5) {
                brp = pf.getPiece("BISHOP", color, squares[backrank][f], squares);
            } else if (f == 3) {
                brp = pf.getPiece("QUEEN", color, squares[backrank][f], squares);
            } else {
                brp = pf.getPiece("KING", color, squares[backrank][f], squares);
            }

            squares[firstrank][f].enter(frp);
            squares[backrank][f].enter(brp);
        }
    }

    public Square[][] getBoardState() {
        return this.squares;
    }

    public static void printStateGraphic(Square[][] state) {

        String[] files = {"A", "B", "C", "D", "E", "F", "G", "H"};
        System.out.print("|");
        for (int i = 0; i < files.length; i++) {
            System.out.print(" " + files[i] + "  |");
        }
        System.out.print("\n");
        for (int r = 7; r >= 0; r--) {
            System.out.print("|");
            for (int f = 0; f <= 7; f++) {

                if (state[r][f].isEmpty()) {
                    System.out.print("    |");
                } else if (state[r][f].getPiece().toString().contains("KNIGHT")) {
                    System.out.print(" " + state[r][f].toString().substring(0, 1) + state[r][f].toString().substring(6, 8) + "|");
                } else {
                    System.out.print(" " + state[r][f].toString().substring(0, 1) + state[r][f].toString().substring(6, 7) + " |");
                }
            }
            System.out.println(" " + (r + 1) + "\n");

        }
    }

    public static Boolean compareStates(Square[][] state1, Square[][] state2) {
        Boolean equals = true;
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                if (!state1[r][f].toString().equals(state2[r][f].toString())) {
                    equals = false;
                    break;
                }
            }
        }
        return equals;
    }

    public static Square[][] copyBoardstate(Square[][] currentstate) { //moved from Player. Required to prevent fringe case bug in King.
        PieceFactory copyFactory = new PieceFactory();
        Square[][] copy = new Square[8][8];
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                copy[r][f] = new Square(r + 1, f + 1);

            }
        }
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                if (!currentstate[r][f].isEmpty()) {
                    Piece toClone = currentstate[r][f].getPiece();
                    Piece clone = copyFactory.getPiece(toClone.toString().substring(6), toClone.getColor(), copy[r][f], copy);
                    clone.setValue(toClone.getValue());
                    if (clone.toString().contains("KING")) {
                        King king = (King) toClone;

                        King kingClone = (King) clone;
                        kingClone.setCastled(king.getCastled());

                        kingClone.setMoved(king.getMoved());

                        copy[r][f].enter(kingClone);
                    } else if (clone.toString().contains("ROOK")) {
                        Rook rook = (Rook) toClone;

                        Rook rookClone = (Rook) clone;
                        rookClone.setMoved(rook.getMoved());

                        copy[r][f].enter(rookClone);
                    } else {
                        copy[r][f].enter(clone);
                    }
                }

            }
        }

        return copy;
    }

}
