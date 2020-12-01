package onitama.model.figures;

import onitama.model.Player;
import onitama.model.board.AbstractField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MasterTest {

    @Mock
    Player player;
    @Mock
    AbstractField field;
    private Master master;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        master = new Master(player);
        master.setCurrentField(field);
    }

    @Test
    void moveTo() {
        AbstractField target = mock(AbstractField.class);
        master.moveTo(target);
        verify(target).accept(master);
    }

    @Test
    void die() {
        master.die();
        verify(player).loose();
        assertNull(master.currentField);
        verify(field).setFigure(null);
        verify(player).removeFigure(master);
    }

    @Test
    void getFigureType() {
        assertEquals(master.getFigureType(), FigureType.MASTER);
    }
}