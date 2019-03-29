/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot;
import kufbot.model.*;
/**
 *
 * @author antlammi
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Board board = new Board();
        Square[][] state = board.getBoardState();
        
        printState(state);
        Move firstmove = new Move(state);
        firstmove.constructMove(state[1][4].getPiece(), state[1][4], state[3][4]);
        firstmove.execute();
        printState(state);
        
        Move[] movesForRookA1 = state[0][0].getPiece().getMoves(state[0][0], state);
        String[] files = { "a", "b", "c", "d", "e", "f", "g", "h" };
        System.out.println("Possible moves for Rook A1");
        for (int i=0; i<movesForRookA1.length; i++){
            System.out.println(movesForRookA1[i]);
        }
        System.out.println("Legal moves for rook A1");
        Move[] legalMovesForRookA1 = state[0][0].getPiece().getLegalMoves(state[0][0], state);
        for (int i=0; i<legalMovesForRookA1.length; i++){
                if (legalMovesForRookA1[i] != null){
                System.out.println(legalMovesForRookA1[i]);
            }
        }
    }
    
    public static void printState(Square[][] state){
        
        String[] files = { "A", "B", "C", "D", "E", "F", "G", "H" };
        for (int r=0; r<=7; r++){
            for (int f=0; f<=7; f++){
                System.out.println(files[f] + (r+1)  + "| " + state[r][f].toString());
            }
        }
    }
    
}
