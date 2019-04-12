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
 *
 * @author antlammi
 */
public class Random implements Engine {
    private java.util.Random random;
    private Player player;
    public Random(Player player){
        this.random = new java.util.Random();
        this.player = player;
    }

    @Override
    public Move getMove() {
        Move[] moves = player.getLegalMoves();
        return moves[random.nextInt(moves.length)];
        
    }
    
    public void update(Square[][] state){
        player.updatePlayer(state);
    }
    
    public Player getPlayer(){
        return this.player;
    }
}
