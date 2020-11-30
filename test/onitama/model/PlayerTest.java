package onitama.model;

import onitama.model.board.AbstractField;
import onitama.model.figures.Figure;
import onitama.model.moves.Move;
import onitama.model.moves.MoveCard;
import onitama.utils.SubjectObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    @Mock private Game mockGame;
    @Mock private MoveCard mockCard1;
    @Mock private MoveCard mockCard2;
    @Mock private Figure mockFigure;
    @Mock private MoveCard exchangeCard;
    @Mock SubjectObserver<Player> observer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        player = new Player(mockGame, Color.BLACK);
        player.getHand().addCard(mockCard1);
        player.getHand().addCard(mockCard2);
        player.addFigure(mockFigure);
    }


    @Test
    void testExecuteIllegalMoveWrongPlayer(){
        Move mockMove = mock(Move.class);
        //wrong player
        when(mockMove.getPlayer()).thenReturn(mock(Player.class));
        //card in hand
        when(mockMove.getParentCard()).thenReturn(mockCard1);
        //good figure
        when(mockMove.getFigure()).thenReturn(mockFigure);

        assertThrows(IllegalArgumentException.class,()->{
            player.executeMove(mockMove);
        });
    }

    @Test
    void testExecuteIllegalMoveWrongCard(){
        Move mockMove = mock(Move.class);
        //good player
        when(mockMove.getPlayer()).thenReturn(player);
        //card not in hand
        when(mockMove.getParentCard()).thenReturn(mock(MoveCard.class));
        //good figure
        when(mockMove.getFigure()).thenReturn(mockFigure);

        assertThrows(IllegalArgumentException.class,()->{
            player.executeMove(mockMove);
        });
    }

    @Test
    void testExecuteIllegalMoveWrongFigure(){
        Move mockMove = mock(Move.class);
        //good player
        when(mockMove.getPlayer()).thenReturn(player);
        //card in hand
        when(mockMove.getParentCard()).thenReturn(mockCard1);
        //wrong figure
        when(mockMove.getFigure()).thenReturn(mock(Figure.class));

        assertThrows(IllegalArgumentException.class,()->{
            player.executeMove(mockMove);
        });
    }

    @Test
    void testExecuteMove(){
        AbstractField destination = mock(AbstractField.class);
        when(mockFigure.getPlayer()).thenReturn(player);
        when(mockGame.exchangeCard(any())).thenReturn(exchangeCard);
        Move move = new Move(mockFigure,destination,mockCard1);

        player.executeMove(move);

        verify(mockFigure).moveTo(destination);
        verify(mockGame).nextTurn();
        assertEquals(exchangeCard,player.getHand().getCards().get(0));
        assertEquals(mockCard2,player.getHand().getCards().get(1));
        assertNull(player.getSelectedCard());
        assertNull(player.getSelectedFigure());
    }

    @Test
    void testFireUpdateCalled(){
        player.attachObserver(observer);
        player.setTurn(!player.isTurn());
        player.removeFigure(mockFigure);
        player.loose();

        verify(observer,times(4)).update(player);

    }

}