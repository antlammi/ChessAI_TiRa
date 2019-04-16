/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot;


import kufbot.engine.Engine;
import kufbot.engine.Minmax;
import kufbot.model.*;

/**
 *
 * @author antlammi
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        
        Game game = new Game("Minmax", "HighestScore", 4, false);
        game.run();
    }
}
