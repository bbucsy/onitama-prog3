package onitama.model;

import onitama.model.board.Board;
import onitama.model.moves.Hand;
import onitama.model.moves.MoveCard;

import java.awt.*;
import java.io.*;
import java.util.Stack;

/**
 * This class contains all the state and other models of a match.
 * It is responsible for setting up a game, handling the players the exchange cards
 * and the actual turns.
 */
public class Game implements Serializable {

    /**
     * Static colors that are given to the players
     */
    private static final Color[] playerColors = new Color[]{Color.BLUE, Color.RED};
    /**
     * Array of the player models
     */
    private final Player[] players;
    /**
     * The model of the board
     */
    private final Board board;
    /**
     * A hand with the capacity of 1. It wraps the exchange card.
     */
    private final Hand exchangeCard;
    /**
     * The index of the player on turn
     */
    private int playerOnTurn = 0;
    /**
     * The state of the game
     */
    private GameState state = GameState.RUNNING;

    /**
     * Constructor of a game.
     * It creates all the necessary models inside and sets the starting player.
     * @param cards The stack of cards which will be in game. Must be at least 5
     * @throws IllegalArgumentException If there aren't enough cards given to the constructor.
     */
    public Game(Stack<MoveCard> cards) {
        if (cards.size() < 5) throw new IllegalArgumentException("Not enough cards");

        exchangeCard = new Hand(1);
        players = new Player[2];
        exchangeCard.setCard(cards.pop(), 0);
        initPlayers(cards);

        board = new Board(this);
    }

    /**
     * Initializes the player models
     * @param cards the cards given to the players
     */
    private void initPlayers(Stack<MoveCard> cards) {

        for (int i = 0; i < 2; i++) {
            players[i] = new Player(this, playerColors[i]);
            Hand h = players[i].getHand();
            for (int j = 0; j < 2; j++)
                h.addCard((i == 0) ? cards.pop() : cards.pop().changeOrientation());
        }

        players[0].setTurn(true);
        playerOnTurn = 0;

    }

    /**
     * Exchanges the card in the side, to the card recently used
     * @param c The recently used card
     * @return  The card that was in the exchange hand
     */
    public MoveCard exchangeCard(MoveCard c) {
        MoveCard temp = exchangeCard.getCards().get(0);
        exchangeCard.setCard(c.changeOrientation(), temp);
        return temp;
    }

    /**
     * Sets the parameters so that the next player is on turn
     */
    public void nextTurn() {
        for (int i = 0; i < 2; i++) {
            players[i].setTurn(!players[i].isTurn());
        }
        playerOnTurn = (playerOnTurn + 1) % 2;
    }

    /**
     * Uses in memory writing and serialization to make a deep copy of itseld
     * @return  A deep copy without the attached observers
     * @throws IOException If the in memory writing/reading fails
     * @throws ClassNotFoundException If the deserialization fails
     */
    public Game deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(temp);
        out.writeObject(this);

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(temp.toByteArray()));
        return (Game) in.readObject();
    }

    /**
     * Sets the status to the appropriate state if a player looses.
     * @param looser The loosing player
     * @throws IllegalArgumentException If the player is not in this game
     */
    public void playerLost(Player looser) {

        if (players[0] == looser) {
            state = GameState.PLAYER_2_WON;
        } else if (players[1] == looser) {
            state = GameState.PLAYER_1_WON;
        } else {
            throw new IllegalArgumentException("Player reference not in game");
        }
    }

    /**
     *
     * @return The state of the game.
     */
    public GameState getState() {
        return state;
    }

    /**
     *
     * @param i The index of the player wanted
     * @return The i-th player
     */
    public Player getPlayer(int i) {
        return players[i];
    }

    /**
     * Reverse indexes players.
     * @param p The player we want to know the index of
     * @return  The index of the player
     * @throws IllegalArgumentException If the player is not in the game
     */
    public int getPlayerNumber(Player p) {
        for (int i = 0; i < 2; i++) {
            if (players[i] == p) return i;
        }
        throw new IllegalArgumentException("Player reference not in game");
    }

    /**
     *
     * @return The hand wrapper of the exchange card
     */
    public Hand getExchangeCard() {
        return exchangeCard;
    }

    /**
     *
     * @return The index of the player whose turn is currently on
     */
    public int getPlayerOnTurn() {
        return playerOnTurn;
    }

    /**
     *
     * @return The model of the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * This enum represents the possible states of the game.
     */
    public enum GameState {
        RUNNING,
        PLAYER_1_WON,
        PLAYER_2_WON
    }

}
