/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot;
import java.util.Random;
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
        Player playerW = new Player(Color.WHITE, state);
        Player playerB = new Player(Color.BLACK, state);
        Random random = new Random();
       
       
        for (int i=0; i<100; i++){  //Simuloi 100 random siirtoa peliä, shakkeja ei juuri oteta huomioon (kuningas ei liiku shakkiin, mutta shakista ei tapahdu mitään)
            Move[] movesForWhite = playerW.possibleMoves();
            
            Move moveChosenWhite = movesForWhite[random.nextInt(movesForWhite.length)];
            System.out.println(i+1 + ": ");
            System.out.println(moveChosenWhite);
            
            moveChosenWhite.execute();
            playerW.updatePlayer(state);
            playerB.updatePlayer(state);
            
            Move[] movesForBlack = playerB.possibleMoves();
            
            Move moveChosenBlack = movesForBlack[random.nextInt(movesForBlack.length)];
            
            System.out.println(moveChosenBlack);
            
            moveChosenBlack.execute();
            playerW.updatePlayer(state);
            playerB.updatePlayer(state);
        }
        
        printState(state);
        printStateGraphic(state);
      
    }
    
    public static void printState(Square[][] state){
        
        String[] files = { "A", "B", "C", "D", "E", "F", "G", "H" };
        for (int r=0; r<=7; r++){
            for (int f=0; f<=7; f++){
                System.out.println(files[f] + (r+1)  + "| " + state[r][f].toString());
            }
        }
    }
    public static void printStateGraphic(Square[][] state) {

        String[] files = {"A", "B", "C", "D", "E", "F", "G", "H"};
        System.out.print("|");
        for (int i=0; i<files.length; i++){
            System.out.print(" " + files[i]+ "  |");
        }
        System.out.print("\n");
        for (int r = 7; r >= 0; r--) {
            System.out.print("|");
            for (int f = 0; f <= 7; f++) {
                
                if (state[r][f].isEmpty()) {
                    System.out.print("    |");
                } else {
                    System.out.print(" " + state[r][f].toString().substring(0,1) + state[r][f].toString().substring(6, 7) + " |");
                }
            }
            System.out.println(" " + (r+1) +"\n");

        }
    }
}
