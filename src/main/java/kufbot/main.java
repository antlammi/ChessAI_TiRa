/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot;

import java.io.IOException;
import java.util.Scanner;
import kufbot.connection.Connection;
import kufbot.model.*;

/**
 *
 * @author antlammi
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
       
       
        
        Scanner scanner = new Scanner(System.in);
        while(true){
            String input = scanner.nextLine();
            if (input.equals("xboard")){
                System.out.append("\n");
                System.out.flush();
                if (scanner.nextLine().startsWith("protover")){
                    System.out.append("feature sigint=0 sigterm=0 reuse=0 myname=\"kufbot\" done=1\n");
                    System.out.flush();
                }
                Connection xboard = new Connection();
                break;
            } else if (input.equals("game")) {
                Game game = new Game("Human", "MinmaxAB", 4, false, true); //enginetypes, depth, fastsim(prints for every move or only outcome), dynamicdepth (varies based on speed)
                game.run();
                break;
            }
        }
        
       
         
    }

}
