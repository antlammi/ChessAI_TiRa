/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.engine;

import java.util.Scanner;
import kufbot.model.King;
import kufbot.model.Move;
import kufbot.model.Piece;
import kufbot.model.Player;
import kufbot.model.Rook;
import kufbot.model.Square;

/**
 *
 * @author antlammi
 */
public class Human implements Engine {

    private Scanner scanner;
    private final String[] files = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private Player player;
    private Square[][] state;

    /**
     *
     * @param player Player object representing the side being played for
     * @param state Boardstate at time of creation
     */
    public Human(Player player, Square[][] state) {
        this.scanner = new Scanner(System.in);
        this.state = state;
        this.player = player;
    }

    /**
     * Asks for a move from the User, and converts it to a Move class object
     * @return Move chosen
     */
    @Override
    public Move getMove() {
        Move move = null;
        while (move == null) {
            String moveString = askForString();
            move = convertToMove(moveString);
            if (move == null) {

                System.out.println("Invalid move! \n");
            }

        }
        return move;
    }

    private String askForString() {
        System.out.println("Enter your move");
        String moveString = scanner.nextLine();
        return moveString;
    }

    private Move convertToMove(String input) {
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

    /**
     * Update state of player object
     */
    @Override
    public void update() {
        this.player.updatePlayer();
    }

    /**
     *
     * @return
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

}
