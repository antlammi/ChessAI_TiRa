/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import kufbot.engine.MinmaxAB;
import kufbot.model.Move;
import kufbot.model.*;

/**
 *
 * @author antlammi
 */
public class Connection {

    private Player w;
    private Player b;
    private MinmaxAB engine;
    private Square[][] state;
    private BufferedReader in;
    private PrintWriter response;
    private final String[] files = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public Connection() throws IOException {
        Board board = new Board();
        this.state = board.getBoardState();
        this.w = new Player(Color.WHITE, this.state);
        this.b = new Player(Color.BLACK, this.state);
        engine = new MinmaxAB(b, w, this.state, 3, false);
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.response = new PrintWriter(System.out);

        runXBoard();

    }

    public void runXBoard() {
        Scanner input = new Scanner(System.in);
        Boolean start = false;
        while (!start) {
            String command = input.nextLine();

            if (command.startsWith("new")) {

                if (engine.getPlayer().getColor().equals(Color.WHITE)) {
                    Move enginemove = engine.getMove();
                    enginemove.execute();
                    System.out.append("move " + enginemove + "\n");
                    System.out.flush();
                    w.updatePlayer();
                    b.updatePlayer();
                }
                start = true;
            }

        }
        Move enginemove = null;
        Integer i = 0;
        String command = "";
        while (true) {

            command = input.nextLine();
            try {
                if (checkForMove(command)) {
                    i++;
                    if (i == 2 && command.length() == 4) {
                        Move debug = convertToMove(command.substring(0,4), this.w);
                        if (debug == null) {
                            response.write("move d2d4\n");
                            response.flush();

                        } else {
                            response.write("move d2d3\n");
                            response.flush();
                        }
                        
                        
                    }
                    Move move = convertToMove(command.substring(0, 4), w);
                    if (move != null) {
                        move.execute();
                        this.w.updatePlayer();
                        engine.update();
                        enginemove = engine.getMove();
                        enginemove.execute();
                        this.w.updatePlayer();
                        engine.update();

                        response.write("move " + enginemove + "\n");
                        response.flush();
                    }

                }
            } catch (Exception e) {
            }

        }

    }

    private Boolean checkForMove(String command) {
        Integer r1 = Integer.parseInt("" + command.charAt(1));
        Integer r2 = Integer.parseInt("" + command.charAt(3));
        if ((r1 > 0 && r1 < 9) && (r2 > 0 && r2 < 9)) {
            return true;
        }
        return false;
    }

    private Move convertToMove(String input, Player player) {
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
        Move[] legalmoves = player.getLegalMoves();

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
                return move;
            }
        }

        return null;
    }
}
