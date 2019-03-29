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
        String[] files = {"a","b","c","d","e","f","g","h"};
        Board board = new Board();
        Square[][] state = board.getBoardState();
        
        Move firstw = new Move(state);
        firstw.constructMove(state[1][4].getPiece(), state[1][4], state[3][4]);
        firstw.execute();
        
        Move firstb = new Move(state);
        firstb.constructMove(state[6][2].getPiece(), state[6][2], state[4][2]);
        firstb.execute();
        
        Move secondw = new Move(state);
        secondw.constructMove(state[1][0].getPiece(), state[1][0], state[3][0]);
        secondw.execute();
        
        Move secondb = new Move(state);
        secondb.constructMove(state[6][3].getPiece(), state[6][3], state[4][3]);
        secondb.execute();
        
        Move[] legalMovesForRookA1 = state[0][0].getPiece().getLegalMoves(state[0][0], state);
        for (int r=0; r<=7; r++){
            for (int f=0; f<=7; f++){
                System.out.println(files[f] + (r+1) +state[r][f].toString());
            }
        }
        for (int i=0; i<legalMovesForRookA1.length; i++){
            System.out.println(legalMovesForRookA1[i]);
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
