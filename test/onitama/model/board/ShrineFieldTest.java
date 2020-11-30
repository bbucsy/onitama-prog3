package onitama.model.board;

import onitama.model.Player;
import onitama.model.figures.Master;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class ShrineFieldTest {


    private ShrineField field;
    @Mock private Player player;
    @Mock private Player enemy;
    @Mock private Board board;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        field = new ShrineField(board,new Point(1,1),player);
    }

    @Test
    void testAcceptFriendlyMaster() {
        Master friendlyMaster = new Master(player);
        field.accept(friendlyMaster);
        verify(player, never()).loose();
    }

    @Test
    void testAcceptEnemyMaster() {
        Master friendlyMaster = new Master(enemy);
        field.accept(friendlyMaster);
        verify(player).loose();
    }
}