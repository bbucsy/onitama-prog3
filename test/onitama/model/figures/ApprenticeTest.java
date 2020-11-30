package onitama.model.figures;

import onitama.model.Player;
import onitama.model.board.AbstractField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApprenticeTest {

    private Apprentice apprentice;
    @Mock private Player p1;
    @Mock private Player p2;
    @Mock private AbstractField mockField;
    @Mock private AbstractField mockCurrentField;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        apprentice = new Apprentice(p1);
        apprentice.setCurrentField(mockCurrentField);
    }

    @Test
    void testDie() {
        apprentice.die();
        assertNull(apprentice.currentField);
        verify(mockCurrentField).setFigure(null);
        verify(p1).removeFigure(apprentice);
    }

    @Test
    void testDieTwice(){
        assertThrows(IllegalStateException.class,()->{
            apprentice.die();
            apprentice.die();
        });
    }

    @Test
    void testMoveTo() {
        apprentice.moveTo(mockField);
        verify(mockField).accept(apprentice);
        verify(mockCurrentField).setFigure(null);
        assertEquals(mockField,apprentice.currentField);
    }

    @Test
    void hitByFriendlyFigure() {
        Figure friend = mock(Figure.class);
        when(friend.getPlayer()).thenReturn(p1);
        assertThrows(IllegalArgumentException.class, ()->{
            apprentice.hitByFigure(friend);
        });
    }

    @Test
    void hitByEnemyFigureAndDie() {
        Figure enemy = mock(Figure.class);
        when(enemy.getPlayer()).thenReturn(p2);
        apprentice.setCurrentField(mockField);

        apprentice.hitByFigure(enemy);
        assertNull(apprentice.currentField);
        verify(mockField).setFigure(null);
        verify(p1).removeFigure(apprentice);

    }

    @Test
    void testGetters() {
        assertEquals(apprentice.getPlayer(),p1);
        assertEquals(apprentice.getFigureType(),FigureType.APPRENTICE);
        assertEquals(apprentice.getCurrentField(),mockCurrentField);
    }


}