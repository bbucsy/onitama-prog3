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

    public AbstractAi(){ }

    public void ExecuteMove(){
        Move nextMove = this.getNextMove();
        if (nextMove == null) player.loose();
        player.executeMove(nextMove);

    }

    protected abstract Move getNextMove();

    public Game getModel() {
        return model;
    }

    public void setModel(Game model) {
        this.model = model;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
