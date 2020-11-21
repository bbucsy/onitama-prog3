package onitama.model;

import onitama.model.board.Board;
import onitama.model.moves.MoveCard;
import onitama.model.moves.MoveCardStorage;

import java.io.FileNotFoundException;
import java.util.List;

public class Game {

    private Player[] players;
    private Board board;
    private MoveCard exchangeCard;

    public Game() throws FileNotFoundException {
        players = new Player[2];
        players[0] = new Player(this);
        players[1] = new Player(this);
        board = new Board(this);
        MoveCardStorage cardStorage = new MoveCardStorage("cards.json");
        List<MoveCard> cards = cardStorage.getRandomCards(5);
        exchangeCard = cards.get(4);
        players[0].addCard(cards.get(0));
        players[0].addCard(cards.get(1));
        players[1].addCard(cards.get(2));
        players[1].addCard(cards.get(3));
    }

    public Player getPlayer(int i){
        if(i < 0 || i > 1)
            return null;
        else
            return players[i];
    }

    public Board getBoard() {
        return board;
    }

    public MoveCard exchangeCards(MoveCard c){
        MoveCard temp = exchangeCard;
        exchangeCard = c;
        return temp;
    }

    @Override
    public String toString() {
        return "Exchange card:" + exchangeCard+ "\n"
        + "Player 1:" + players[0] + "\n"
        + "Player 2:" + players[1] + "\n"
        + board.toString();
    }
}
