/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.engine;

import kufbot.model.Move;
import kufbot.model.Player;

/**
 *
 * @author antlammi
 */
public interface Engine {
    
    /**
     *
     * @return Move made by the engine
     */
    public Move getMove();

    /**
     * Updates the status of Engine's maximizing player
     */
    public void update();

    /**
     * 
     * @return Maximizing Player
     */
    public Player getPlayer();
}
