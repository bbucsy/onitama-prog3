package onitama.controller.ai;

import onitama.model.Game;
import onitama.model.Player;

public interface PlayerController {
    void ExecuteMove();

    void setModel(Game model);
    void setPlayer(Player player);
}
