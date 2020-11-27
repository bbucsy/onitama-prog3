package onitama.controller.ai;

import onitama.model.Game;
import onitama.model.Player;
import onitama.model.moves.Move;

import java.util.List;
import java.util.Random;

public class RandomAi extends AbstractAi {

    Random rand;

    public RandomAi(Game model, Player player) {
        super(model, player);
        rand = new Random();
    }

    @Override
    protected Move getNextMove() {
        List<Move> possibleMoves = player.getAllPossibleMoves();
        if(possibleMoves.size() == 0)return null;
        return possibleMoves.get(rand.nextInt(possibleMoves.size()));
    }
}
