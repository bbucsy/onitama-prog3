package onitama.controller.ai;

import onitama.model.moves.Move;

import java.util.List;
import java.util.Random;

/**
 * This is a player controller, which chooses a random to execute on it's turn.
 */
public class RandomAi extends PlayerController {

    /**
     * A random instance to use in the algorithm
     */
    private final Random rand;

    public RandomAi() {
        super();
        rand = new Random();
    }


    /**
     * Because it is possible to run more than one game at a time
     * there needs to be a way to make a new empty controller from an object
     * @return  A new controller instance
     */
    @Override
    public PlayerController clone() {
        return new RandomAi();
    }

    /**
     *
     * @return A random move
     */
    @Override
    protected Move getNextMove() {
        List<Move> possibleMoves = player.getAllPossibleMoves();
        if (possibleMoves.size() == 0) return null;
        return possibleMoves.get(rand.nextInt(possibleMoves.size()));
    }
}
