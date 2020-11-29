package onitama.controller.ai;

import onitama.model.moves.Move;

import java.util.List;
import java.util.Random;

public class RandomAi extends AbstractAi {

    final Random rand;

    public RandomAi() {
        super();
        rand = new Random();
    }


    @Override
    protected Move getNextMove() {
        List<Move> possibleMoves = player.getAllPossibleMoves();
        if(possibleMoves.size() == 0)return null;
        return possibleMoves.get(rand.nextInt(possibleMoves.size()));
    }
}
