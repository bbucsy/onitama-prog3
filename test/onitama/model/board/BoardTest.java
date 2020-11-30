package onitama.model.board;

import onitama.model.Game;
import onitama.model.Player;
import onitama.model.figures.Apprentice;
import onitama.model.figures.Figure;
import onitama.model.figures.Master;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BoardTest {

    private Board board;
    @Mock
    private Game game;
    @Mock
    private Player p1;
    @Mock
    private Player p2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(game.getPlayer(0)).thenReturn(p1);
        when(game.getPlayer(1)).thenReturn(p2);
        board = new Board(game);
    }


    @Test
    void testGeneratedFieldsAreCorrect() {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i == 0 && j == 2) || (i == 4 && j == 2)) {
                    // check field and figures in the position of masters
                    assertTrue(board.getField(i, j) instanceof ShrineField);
                    Figure f = board.getField(i, j).getFigure();
                    assertTrue(f instanceof Master);

                } else {
                    assertTrue(board.getField(new Point(i, j)) instanceof SimpleField);
                    Figure f = board.getField(i, j).getFigure();
                    if (i == 0 || i == 4){
                        assertTrue(f instanceof Apprentice);
                        assertEquals(f.getPlayer(), (i == 0) ? p1 : p2);
                    }
                    else{
                        assertNull(f);
                    }
                }
            }
        }
    }

}