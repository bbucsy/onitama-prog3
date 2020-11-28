package onitama.model;

import onitama.model.board.Board;
import onitama.model.moves.Hand;
import onitama.model.moves.MoveCard;
import onitama.utils.MoveCardStorage;

import java.awt.*;
import java.io.*;
import java.util.Stack;

public class Game implements Serializable {


    public enum GameState{
        RUNNING,
        PLAYER_1_WON,
        PLAYER_2_WON
    }


    private static final Color[] playerColors = new Color[]{Color.BLUE, Color.ORANGE};

    private final Player[] players;
    private final Board board;
    private final Hand exchangeCard;
    private int playerOnTurn = 0;
    private GameState state = GameState.RUNNING;

    public Game() {
        exchangeCard = new Hand(1);
        players = new Player[2];

        Stack<MoveCard> cards = MoveCardStorage.getInstance().getRandomCards(5);
        exchangeCard.setCard(cards.pop(), 0);
        initPlayers(cards);

        board = new Board(this);
    }

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

    public MoveCard exchangeCard(MoveCard c) {
        MoveCard temp = exchangeCard.getCards().get(0);
        c.changeOrientation();
        exchangeCard.setCard(c, temp);
        return temp;
    }

    public void nextTurn(){
        for (int i = 0; i < 2; i++) {
            players[i].setTurn(!players[i].isTurn());
        }
        playerOnTurn = (playerOnTurn+1)% 2;
    }

    public Game deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(temp);
        out.writeObject(this);

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(temp.toByteArray()));
        return (Game) in.readObject();
    }



    public void playerLost(Player looser){

        if(players[0] == looser){
            state = GameState.PLAYER_2_WON;
        } else  if (players[1] == looser){
            state = GameState.PLAYER_1_WON;
        }
        else {
            state = GameState.RUNNING;
        }
    }

    public GameState getState() {
        return state;
    }

    public Player getPlayer(int i) {
            return players[i];
    }

    public Hand getExchangeCard() {
        return exchangeCard;
    }

    public int getPlayerOnTurn() {
        return playerOnTurn;
    }

    public Board getBoard() {
        return board;
    }

}
