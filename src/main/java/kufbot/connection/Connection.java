/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import kufbot.engine.MinmaxAB;
import kufbot.model.Move;
import kufbot.model.*;

/**
 * Class handles XBoard connection
 *
 * @author antlammi
 */
public class Connection {

    private Player p;
    private Player o;
    private MinmaxAB engine;
    private Square[][] state;
    private PrintWriter response;
    private final String[] files = {"a", "b", "c", "d", "e", "f", "g", "h"};

    /**
     * By default assumes it is to play as Black.
     *
     * @throws IOException
     */
    public Connection() throws IOException {
        Board board = new Board();
        this.state = board.getBoardState();
        this.o = new Player(Color.WHITE, this.state);
        this.p = new Player(Color.BLACK, this.state);
        engine = new MinmaxAB(p, o, this.state, 4, false, false);

        this.response = new PrintWriter(System.out);

        runXBoard();

    }

    /**
     * Loop that receives inputs from XBoard app via System.in and sends outputs
     * to XBoard via System.out Also handles updating internal AI state
     */
    public void runXBoard() {
        Scanner input = new Scanner(System.in);
        Boolean start = false;
        while (!start) {
            String command = input.nextLine();

            if (command.startsWith("new")) {
                start = true;
            }
            if (command.startsWith("go")) {
                Player pClone = this.p.clonePlayer(state);
                this.p = this.o;
                this.o = pClone;
                this.engine = new MinmaxAB(p, o, this.state, 4, false, false);
                Move enginemove = engine.getMove();
                enginemove.execute();
                System.out.append("move " + enginemove + "\n");
                System.out.flush();
                o.updatePlayer();
                p.updatePlayer();
                start = true;

            }

        }
        Move enginemove = null;

        while (true) {

            String nextinput = input.nextLine();
            String command = nextinput.trim();

            try {
                if (checkForMove(command)) {

                    if (command.equals("e1g1")) {
                        if (state[0][4].getPiece().toString().equals("WHITE KING")) {
                            command = "e1h1";
                        }

                    } else if (command.equals("e1c1")) {
                        if (state[0][4].getPiece().toString().equals("WHITE KING")) {
                            command = ("e1a1");
                        }
                    } else if (command.equals("e8g8")) {
                        if (state[7][4].getPiece().toString().equals("BLACK KING")) {
                            command = ("e1a1");
                        }
                        command = "e8h8";
                    } else if (command.equals("e8c8")) {
                        if (state[7][4].getPiece().toString().equals("BLACK KING")) {
                            command = "e8a8";
                        }
                    }

                    if (command.length() == 5) {
                        command = command.substring(0, 4);
                    }
                    Move[] moves = o.getLegalMoves();
                    Move move = convertToMove(command, moves);

                    if (move != null) {
                        move.execute();
                        this.o.updatePlayer();
                        this.p.updatePlayer();

                        enginemove = engine.getMove();
                        enginemove.execute();

                        this.o.updatePlayer();
                        this.p.updatePlayer();

                        if (enginemove.getCastle()) {
                            String output = adjustForCastlingSyntax(enginemove.toString());
                            response.write("move " + output + "\n");
                            response.flush();
                        } else if (checkForPawnPromotion(enginemove)) {
                            String output = enginemove.toString();
                            output = output + "q";
                            response.write("move " + enginemove + "\n");
                            response.flush();

                        } else {
                            response.write("move " + enginemove + "\n");
                            response.flush();
                        }
                    } else {
                        response.write("Illegal move: " + command + "\n");
                        response.flush();
                    }

                }
            } catch (Exception e) {
            }

        }

    }

    //Checks if engine move is a pawn promotion, which requires an adjusted syntax for XBoard
    private Boolean checkForPawnPromotion(Move enginemove) {
        Boolean result = (enginemove.getPiece().toString().contains("PAWN")
                && (enginemove.getDestinationSquare().getRank() == 1 || enginemove.getDestinationSquare().getRank() == 8));
        return result;
    }

    //Makes engine's syntax for castling compatible with XBoard
    private String adjustForCastlingSyntax(String enginemove) {
        String output = "";
        if (enginemove.equals("e1h1")) {
            output = "e1g1";
        } else if (enginemove.equals("e1a1")) {
            output = "e1c1";
        } else if (enginemove.equals("e8h8")) {
            output = "e8g8";
        } else if (enginemove.equals("e8a8")) {
            output = "e8c8";
        }
        return output;
    }

    //Checks if received command was a move
    private Boolean checkForMove(String command) {
        Integer r1 = -1;
        Integer r2 = -1;

        String c1 = (command.charAt(1) + "").substring(0, 1);

        if (c1.matches("[0-9]+")) {
            r1 = Integer.parseInt(c1);
        }
        String c2 = (command.charAt(3) + "").substring(0, 1);
        if (c2.matches("[0-9]+")) {
            r2 = Integer.parseInt(c2);
        }

        if ((r1 > 0 && r1 < 9) && (r2 > 0 && r2 < 9)) {
            return true;
        }
        return false;
    }

    //Converts command received from XBoard to a Move class object
    private Move convertToMove(String input, Move[] legalmoves) {
        if (input.length() > 4 || input.length() < 4) {
            return null;
        }
        Integer[] movefiles = new Integer[2];
        Integer fc = 0;

        Integer[] moveranks = new Integer[2];
        Integer rc = 0;

        for (int i = 0; i < input.length() && i < 6; i++) {
            String c = input.substring(i, i + 1);
            if (i % 2 == 0) {

                for (int f = 0; f < files.length; f++) {
                    if (c.equals(files[f])) {
                        movefiles[fc] = f;
                        break;
                    }
                }
                if (movefiles[fc] == null) {
                    return null;
                }
                fc++;
            } else if (i < 4) {
                Integer rank = Integer.parseInt(c) - 1;
                if (rank >= 0 && rank < 8) {
                    moveranks[rc] = rank;
                    rc++;
                } else {
                    return null;
                }
            } else {
            }
        }
        Square current = this.state[moveranks[0]][movefiles[0]];
        Square destination = this.state[moveranks[1]][movefiles[1]];
        Piece piece;
        if (!current.isEmpty()) {
            piece = current.getPiece();
        } else {
            return null;
        }

        Move move = new Move(this.state);

        if (piece.toString().contains("KING")) {
            if (input.equals("e1h1") || input.equals("e8h8") || input.equals("e1a1") || input.equals("e8a8")) {
                if (!destination.isEmpty()) {
                    if (destination.getPiece().toString().contains("ROOK")) {
                        Rook rook = (Rook) destination.getPiece();
                        King king = (King) piece;
                        move.constructCastle(king, rook, current, destination);
                        for (int i = 0; i < legalmoves.length; i++) {
                            if (legalmoves[i] == null) {
                                break;
                            }
                            if (legalmoves[i].toString().equals(move.toString())) {
                                if (legalmoves[i].getCastle() == true) {
                                    return move;
                                }
                            }
                        }
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }

        move.constructMove(piece, current, destination);
        for (int i = 0; i < legalmoves.length; i++) {
            if (legalmoves[i].toString().equals(move.toString())) {
                if (legalmoves[i] == null) {
                    break;
                }
                return move;
            }
        }

        return null;
    }
}
