package onitama.model.board;

import onitama.model.Player;
import onitama.model.figures.Apprentice;
import onitama.model.figures.Figure;
import onitama.model.figures.Master;
import onitama.utils.SubjectObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SimpleFieldTest {


    private SimpleField field;
    private Point pos;
    @Mock private Board mockedBoard;
    @Mock private Player mockedPlayer;
    @Mock private SubjectObserver<Figure> mockObserver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pos = new Point(1,1);
        field = new SimpleField(mockedBoard,pos);
    }

    @Test
    void testAcceptApprentice() {
        Apprentice a1 = new Apprentice(mockedPlayer);
        field.accept(a1);
        assertEquals(a1,field.getFigure());
    }

    @Test
    void testAcceptMaster() {
        Master m1 = new Master(mockedPlayer);
        field.accept(m1);
        assertEquals(m1,field.getFigure());
    }

    @Test
    void testAcceptVerifyMasterCollisionCalled(){
        Master m1 = mock(Master.class);
        Master m2 = mock(Master.class);

        field.accept(m1);
        field.accept(m2);
        assertEquals(m2,field.getFigure());
        verify(m1).hitByFigure(m2);

    }
    @Test
    void testAcceptVerifyApprenticeCollisionCalled(){
        Apprentice a1 = mock(Apprentice.class);
        Apprentice a2 = mock(Apprentice.class);

        field.accept(a1);
        field.accept(a2);
        assertEquals(a2,field.getFigure());
        verify(a1).hitByFigure(a2);
    }

    @Test
    void testGetPosition(){
        assertEquals(pos,field.getPosition());
    }
    @Test
    void  testGetBoard(){
        assertEquals(mockedBoard,field.getBoard());
    }

    @Test
    void testUpdateFired() {
        Apprentice a1 = mock(Apprentice.class);
        Apprentice a2 = mock(Apprentice.class);

        field.attachObserver(mockObserver);
        field.accept(a1);
        field.accept(a2);

        verify(mockObserver).update(null);
        verify(mockObserver).update(a1);
        verify(mockObserver).update(a2);

    }
}