package onitama.model;

import onitama.model.board.Board;
import onitama.model.moves.MoveCard;
import onitama.model.moves.MoveCardStorage;
import onitama.model.moves.MoveCardWrapper;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

public class Game {

    private Player[] players;
    private Board board;
    private MoveCardWrapper exchangeCard;

    public Game() throws FileNotFoundException {
        exchangeCard = new MoveCardWrapper();
        players = new Player[2];
        players[0] = new Player(this, Color.BLUE);
        players[1] = new Player(this,Color.RED);
        board = new Board(this);

        MoveCardStorage cardStorage = new MoveCardStorage("cards.json");
        List<MoveCard> cards = cardStorage.getRandomCards(5);
        exchangeCard.setCard(cards.get(4));
        players[0].addCard(cards.get(0));
        players[0].addCard(cards.get(1));
        players[1].addCard(cards.get(2).changeOrientation());
        players[1].addCard(cards.get(3).changeOrientation());
        players[0].setTurn(true);
    }


    public MoveCard nextTurn(MoveCard c){
        MoveCard temp = exchangeCard.getCard();
        c.changeOrientation();
        exchangeCard.setCard(c);

        for (int i = 0; i < 2; i++) {
            players[i].setTurn(!players[i].isTurn());
        }


        return temp;
    }

    @Override
    public String toString() {
        return "Exchange card:" + exchangeCard+ "\n"
                + "Player 1:" + players[0] + "\n"
                + "Player 2:" + players[1] + "\n"
                + board.toString();
    }

    public Player getPlayer(int i){
        if(i < 0 || i > 1)
            return null;
        else
            return players[i];
    }

    public MoveCardWrapper getExchangeCard() {
        return exchangeCard;
    }

    public Board getBoard() {
        return board;
    }

}
