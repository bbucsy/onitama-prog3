package onitama.model;

import onitama.model.moves.MoveCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Stack<MoveCard> cards = new Stack<>();
        for (int i = 0; i < 5; i++) {
            cards.add(mock(MoveCard.class));
        }
        game = new Game(cards);
    }


    @Test
    void testInitValues() {
        assertNotNull(game.getExchangeCard().getCards().get(0));

        for (int i = 0; i < 2; i++) {
            assertNotNull(game.getPlayer(i));
            assertEquals(i, game.getPlayerNumber(game.getPlayer(i)));
            assertEquals(2, game.getPlayer(i).getHand().getCards().size());
        }
        assertTrue(game.getPlayer(0).isTurn());
        assertFalse(game.getPlayer(1).isTurn());
        assertEquals(0, game.getPlayerOnTurn());
        assertEquals(Game.GameState.RUNNING, game.getState());
    }

    @Test
    void testNextTurn() {
        game.nextTurn();
        assertFalse(game.getPlayer(0).isTurn());
        assertTrue(game.getPlayer(1).isTurn());
        assertEquals(1, game.getPlayerOnTurn());
    }

    @Test
    void testGetPlayerNumberWrongReference() {
        assertThrows(IllegalArgumentException.class, () -> {
            game.getPlayerNumber(mock(Player.class));
        });
    }

    @Test
    void testExchangeCards() {
        MoveCard cardToExchange = mock(MoveCard.class);
        when(cardToExchange.changeOrientation()).thenReturn(cardToExchange);

        MoveCard sideCard = game.getExchangeCard().getCards().get(0);
        MoveCard exchanged = game.exchangeCard(cardToExchange);

        assertEquals(sideCard, exchanged);
        assertEquals(cardToExchange, game.getExchangeCard().getCards().get(0));
        verify(cardToExchange).changeOrientation();

    }

    @Test
    void testPlayerLost() {
        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);

        game.playerLost(p2);
        assertEquals(Game.GameState.PLAYER_1_WON, game.getState());

        game.playerLost(p1);
        assertEquals(Game.GameState.PLAYER_2_WON, game.getState());

        assertThrows(IllegalArgumentException.class, () -> {
            game.playerLost(mock(Player.class));
        });

    }

}