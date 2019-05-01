/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kufbot.engine;

import kufbot.model.Move;
import kufbot.model.Player;
import kufbot.model.Square;

/**
 * Chooses a random move from the Array of legal moves.
 * @author antlammi
 */
public class Random implements Engine {
    private java.util.Random random;
    private Player player;
    private Square[][] state;

    /**
     *
     * @param player
     * @param state
     */
    public Random(Player player, Square[][] state){
        this.random = new java.util.Random();
        this.player = player;
        this.state = state;
    }

    /**
     *
     * @return
     */
    @Override
    public Move getMove() {
        Move[] moves = player.getLegalMoves();
        return moves[random.nextInt(moves.length)];
        
    }
    
    /**
     *
     */
    public void update(){
        player.updatePlayer();
    }
    
    /**
     *
     * @return
     */
    public Player getPlayer(){
        return this.player;
    }
}
