package onitama.controller.ai;

import onitama.model.Game;
import onitama.model.Player;
import onitama.model.moves.Move;

public abstract class AbstractAi {

    protected Game model;
    protected Player player;

    public AbstractAi(Game model, Player player) {
        this.model = model;
        this.player = player;
    }

    public void ExecuteMove(){

        Move nextMove = this.getNextMove();
        if (nextMove == null) player.loose();
        player.executeMove(nextMove);

    }

    protected abstract Move getNextMove();

}
