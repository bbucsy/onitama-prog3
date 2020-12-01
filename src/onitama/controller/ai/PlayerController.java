package onitama.controller.ai;

import onitama.model.Game;
import onitama.model.Player;
import onitama.model.moves.Move;

/**
 * This class is the base class of every AI in the game.
 */
public abstract class PlayerController {

    /**
     * Them model of the game
     */
    protected Game model;
    /**
     * Them model of the controlled player
     */
    protected Player player;

    public PlayerController() {
    }

    /**
     * Chooses a move based on an algorithm, and executes it.
     * If it can't find possible moves, calls the loose function of the player
     */
    public void ExecuteMove() {
        Move nextMove = this.getNextMove();
        if (nextMove == null) player.loose();
        else player.executeMove(nextMove);
    }

    /**
     *
     * @return The chosen move
     */
    protected abstract Move getNextMove();

    /**
     * Because it is possible to run more than one game at a time
     * there needs to be a way to make a new empty controller from an object
     * @return  A new controller instance
     */
    public abstract PlayerController clone();


    /**
     *
     * @param model The model of the game the class this controls
     */
    public void setModel(Game model) {
        this.model = model;
    }

    /**
     *
     * @param player The player this object controls
     */
    public void setPlayer(Player player) {
        this.player = player;
    }



}
