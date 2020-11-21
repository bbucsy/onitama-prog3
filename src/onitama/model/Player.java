package onitama.model;

import onitama.model.figures.Figure;
import onitama.model.moves.Move;
import onitama.model.moves.MoveCard;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Figure> figures;
    private List<MoveCard> cards;
    private Game game;

    private MoveCard selectedCard;
    private Figure selectedFigure;

    public List<Move> getAvaibleMoves(){
        if(selectedCard != null && selectedFigure != null){
            return selectedCard.getAvaibleMoves(selectedFigure);
        }
        return null;
    }

    public void makeMove(int i){
        if(selectedCard != null && selectedFigure != null){
            selectedCard.getAvaibleMoves(selectedFigure).get(i).makeMove();
            cards.remove(selectedCard);
            cards.add(game.exchangeCards(selectedCard));
            selectedCard = null;
            selectedFigure = null;
        }
    }


    public MoveCard getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(MoveCard selectedCard) {
        this.selectedCard = selectedCard;
    }

    public Figure getSelectedFigure() {
        return selectedFigure;
    }

    public void setSelectedFigure(Figure selectedFigure) {
        this.selectedFigure = selectedFigure;
    }

    public Player(Game g) {
        game = g;
        cards = new ArrayList<>();
        figures = new ArrayList<>();
    }

    public void addFigure(Figure f) {
        figures.add(f);
    }

    public void removeFigure(Figure f) {
        figures.remove(f);
    }


    public void loose() {
        System.out.println("Player lsot");
    }

    public void addCard(MoveCard card) {
        cards.add(card);
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public List<MoveCard> getCards() {
        return cards;
    }


    @Override
    public String toString() {
        return "{" +
                "cards=" + cards +
                '}';
    }
}
