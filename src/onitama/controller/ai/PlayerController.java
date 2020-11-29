package onitama.controller.ai;

import onitama.model.Game;
import onitama.model.Player;

public interface PlayerController {
    void ExecuteMove();

    Game getModel();
    void setModel(Game model);
    Player getPlayer();
    void setPlayer(Player player);
}
