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
public class King implements Piece {

    /**
     *
     */
    public final Color color;
    private Boolean moved;
    private Square current;
    private Square[][] boardstate;
    private Double baseValue;
    private Double value;
    private Boolean castled;
    
    /**
     *
     * @param color
     * @param initial
     * @param boardstate
     */
    public King(Color color, Square initial, Square[][] boardstate) {
        this.color = color;
        this.moved = false;
        this.current = initial;
        this.boardstate = boardstate;
        this.baseValue = 15.0;
        this.value = baseValue;
        this.castled = false;
    }

    /**
     *
     * @param newSquare
     */
    @Override
    public void setSquare(Square newSquare) {
        this.current = newSquare;
    }

    @Override
    public String toString() {
        return color + " KING";
    }

    /**
     *
     * @return
     */
    public Boolean getMoved() {
        return this.moved;
    }

    /**
     *
     * @param moved
     */
    public void setMoved(Boolean moved) {
        if (!this.castled) {
            this.baseValue = 14.0; //If King has not yet castled, makes engine not want to move the King.
            updateValue();
        }
        this.moved = moved;
    }

    /**
     *
     * @param castled
     */
    public void setCastled(Boolean castled) {
        this.castled = castled;

        if (castled) {
            this.baseValue = 16.0; //Higher basevalue makes engine utilize castling more
            updateValue();
        } else {
            this.baseValue = 15.0;
            updateValue();
        }
    }

    /**
     * Checks if the King is in check in a given position
     * @param toCheck Square king is in checked scenario, typically the same as current but for King's it may be different
     * @return true if King is in check, otherwise false
     */
    public Boolean isInCheck(Square toCheck) {
        Square[][] copystate = Board.copyBoardstate(boardstate);
        Move copymove = new Move(copystate);

        Square curSQCopy = copystate[current.getRank() - 1][current.getFile() - 1];

        Square desSQ = toCheck;
        Square desSQCopy = copystate[desSQ.getRank() - 1][desSQ.getFile() - 1];
        copymove.constructMove(curSQCopy.getPiece(), curSQCopy, desSQCopy);
        copymove.execute();

        Square kingLocation = desSQCopy;
        for (int r = 0; r <= 7; r++) {
            for (int f = 0; f <= 7; f++) {
                Square location = copystate[r][f];
                if (!location.isEmpty()) {
                    if (location.getPiece().getColor() != this.color) { //opponent's piece
                        if (location.getPiece().toString().contains("KING")) { //Special case to prevent infinite loop
                            for (int i = -1; i <= 1; i++) {
                                if (kingLocation.getRank() - 1 == r + i) {
                                    if (kingLocation.getFile() - 1 == f - 1 || kingLocation.getFile() - 1 == f
                                            || kingLocation.getFile() - 1 == f + 1) {
                                        return true;
                                    }
                                }
                            }

                        } else {
                            Move[] moves = location.getPiece().getLegalMoves(); //list of legal moves for opponents piece that is being looked at

                            for (int i = 0; i < moves.length; i++) {
                                if (moves[i] == null) {
                                    break;
                                }

                                if (moves[i].getDestinationSquare() == kingLocation) { //King is in check if opponent can capture it with a legal move
                                    if (!moves[i].getPiece().toString().contains("PAWN")) { //every other piece can capture with all their legal moves
                                        return true;
                                    } else if (moves[i].getPawnCapture()) { //if the piece is a a pawn and the move is a capture return true, otherwise ignore
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Provides an array of possible moves based on Bishop's movement rules in the position
     * @return Move[]
     */
    @Override
    public Move[] getMoves() {
        Move[] possibleMoves = new Move[10];
        Integer movecount = 0;

        Integer rank = current.getRank() - 1;
        Integer file = current.getFile() - 1;

        for (int f = file - 1; f <= file + 1; f++) { //yhden ruudun verran vasemmalta yhden ruudun verran oikealle
            for (int r = rank - 1; r <= rank + 1; r++) { //yhden ruudun verran alhaalta yhden ruudun verran ylös
                if ((f >= 0 && f <= 7) && (r >= 0 && r <= 7)) { //jos määränpää on pöydän rajojen sisällä
                    if (f != file || r != rank) { //ja määränpää ei ole nykyinen ruutu
                        possibleMoves[movecount] = new Move(boardstate);
                        possibleMoves[movecount].constructMove(this, current, boardstate[r][f]);
                        movecount++;
                    }
                }
            }
        }//erikoissäännöt tornitukseen liittyen
        if (!this.moved) {  //onko kuningas liikkunut
            Square rookLocation = boardstate[rank][0];
            for (int i = 0; i < 2; i++) { //ensimmmäisellä kierroksella tarkistetaan onko ensimmäisen sarakkeen torni liikutettu, toisella viimeisen sarakkeen
                if (!rookLocation.isEmpty()) {
                    if (rookLocation.toString().equals(this.color + " ROOK")) {
                        Rook rook = (Rook) rookLocation.getPiece();
                        if (!rook.getMoved()) {
                            possibleMoves[movecount] = new Move(boardstate);
                            possibleMoves[movecount].constructCastle(this, rook, current, rookLocation);
                            movecount++;
                        }
                    }
                }
                rookLocation = boardstate[rank][7];
            }

        }
        return possibleMoves;
    }

    /**
     * Checks for legality in array of moves provided by getMoves.
     * If a Square is unreachable (blocked), or contains a Piece of the same color,
     * a move is considered illegal.
     * @return
     */
    @Override
    public Move[] getLegalMoves() {
        Move[] movesToCheck = getMoves();
        Move[] legalMoves = new Move[movesToCheck.length];
        Integer legalcount = 0;

        for (int i = 0; i < movesToCheck.length; i++) {
            if (movesToCheck[i] == null) {
                break;
            }

            Move currentMove = movesToCheck[i];
            Square destination = currentMove.getDestinationSquare();
            if (!currentMove.getCastle()) {
                if (destination.isEmpty()) {
                    if (!isInCheck(destination)) {
                        legalMoves[legalcount] = currentMove;
                        legalcount++;
                    }
                } else if (destination.getPiece().getColor() != this.color) {
                    if (!isInCheck(destination)) {
                        legalMoves[legalcount] = currentMove;
                        legalcount++;
                    }
                }
            } else {
                Integer cr = currentMove.getCurrentSquare().getRank() - 1;

                Integer df = currentMove.getDestinationSquare().getFile() - 1;
                if (!isInCheck(currentMove.getCurrentSquare())) {
                    if (df == 0) {  //Tarkistetaan onko tornitus kuningattaren puolella mahdollista
                        if (boardstate[cr][df + 1].isEmpty() //ruudut tornin ja kuninkaan välissä ovat tyhjät
                                && boardstate[cr][df + 2].isEmpty()
                                && boardstate[cr][df + 3].isEmpty()
                                && !isInCheck(boardstate[cr][df + 2]) //kuningas ei ole shakissa matkalla määränpäähänsä
                                && !isInCheck(boardstate[cr][df + 3])) {
                            legalMoves[legalcount] = currentMove;
                            legalcount++;
                        }

                    } else if (boardstate[cr][df - 1].isEmpty() //Kuninkaan puolella sama, paitsi että yksi ruutu vähemmän oltava tyhjänä
                            && boardstate[cr][df - 2].isEmpty()
                            && !isInCheck(boardstate[cr][df - 1]) && !isInCheck(boardstate[cr][df - 2])) {
                        legalMoves[legalcount] = currentMove;
                        legalcount++;
                    }
                }
            }
        }
        return legalMoves;
    }

    /**
     * 
     * @return
     */
    public Boolean getCastled() {
        return this.castled;
    }

    /**
     * Just leaves value as it is to not generally make king moves desired. 
     * Would be nice for lategame scenarios, though.
     * @param moves
     */
    @Override
    public void updateValue() {
        this.value = baseValue;
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
