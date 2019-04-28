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
        state = board.getBoardState();
        this.w = new Player(Color.WHITE, state);
        this.b = new Player(Color.BLACK, state);
        engine = new MinmaxAB(b, w, state, 2, true);
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
        while (true) {

            String command = "?";
            try {
                command = this.in.readLine();
           
            } catch (Exception e) {
            }

            if (command.equals("quit")) {
                break;
            } else if (command.startsWith("sd")) {
                //do nothing for now
            } else if (command.startsWith("st")) {
                //do nothing for now
            } else if (command.startsWith("variant")) {
                //do nothing for now
            } else if (command.startsWith("force")) {
                // do nothing for now
            } else if (command.startsWith("go")) {
                //do nothing for now
            } else if (command.startsWith("level")) {
                //do nothing for now
            } else if (command.startsWith("time")) {
                //do nothing for now
            } else if (command.startsWith("otim")) {
                //do nothing for now
            } else if (command.startsWith("draw")) {
                //do nothing for now
            } else if (command.startsWith("result")) {
                //do nothing for now
            } else if (command.startsWith("hint")) {
                //do nothing for now
            } else if (command.startsWith("white")) {
                //do nothing for now
            } else if (command.startsWith("black")) {
                //do nothing for now
            } else if (command.startsWith("?")) {
                //do nothing for now
            } else if (command.startsWith("edit")) {
                //do nothing for now
            } else if (command.startsWith("bk")) {
                //do nothing for now
            } else if (command.startsWith("undo")) {
                //do nothing for now
            } else if (command.startsWith("remove")) {
                //do nothing for now
            } else if (command.startsWith("hard")) {
                //do nothing for now
            } else if (command.startsWith("easy")) {
                //do nothing for now
            } else if (command.startsWith("post")) {
                //do nothing for now
            } else if (command.startsWith("nopost")) {
                //do nothing for now
            } else if (command.startsWith("name")) {
                //do nothing for now
            } else if (command.startsWith("rating")) {
                //do nothing for now
            } else if (command.startsWith("computer")) {
                //do nothing for now
            } else if (command.startsWith("analyze")) {

            } else {

                Move move = convertToMove(command.substring(0, 4), w);
                if (move != null) {
                    move.execute();
                    w.updatePlayer();
                    b.updatePlayer();

                    Move enginemove = engine.getMove();
                    enginemove.execute();
                    w.updatePlayer();
                    b.updatePlayer();

                    response.write("move " + enginemove + "\n");
                    response.flush();

                }

            }

        }

    }

    private Move convertToMove(String input, Player player) {
        if (input.length() > 4 || input.length() < 4) {
            return null;
        }
        Integer[] movefiles = new Integer[2];
        Integer fc = 0;

        Integer[] moveranks = new Integer[2];
        Integer rc = 0;

        Boolean promoteToQueen = false;
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
                promoteToQueen = true;
            }
        }
        Square current = state[moveranks[0]][movefiles[0]];
        Square destination = state[moveranks[1]][movefiles[1]];
        Piece piece;
        if (!current.isEmpty()) {
            piece = current.getPiece();
        } else {
            return null;
        }
        Move move = new Move(state);
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
