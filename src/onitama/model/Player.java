package onitama.model;

import onitama.model.figures.Figure;
import onitama.model.moves.Move;
import onitama.model.moves.MoveCard;
import onitama.utils.ObservedSubject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends ObservedSubject<List<MoveCard>>{

    private List<Figure> figures;
    private List<MoveCard> cards;
    private Game game;

    private MoveCard selectedCard;
    private Figure selectedFigure;
    private Color color;
    private boolean isTurn = false;

    public Player(Game game, Color color) {
        this.game = game;
        this.color = color;
        this.cards = new ArrayList<>();
        this.figures = new ArrayList<>();
    }

    public List<Move> getAvaibleMoves(){
        if(selectedCard != null && selectedFigure != null){
            return selectedCard.getAvaibleMoves(selectedFigure);
        }
        return null;
    }

    public void nexTurn(){
        cards.remove(selectedCard);
        cards.add(game.nextTurn(selectedCard));
        selectedCard = null;
        selectedFigure = null;
        this.fireUpdated();
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
        this.fireUpdated();
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public List<MoveCard> getCards() {
        return cards;
    }

    public Color getColor() {
        return color;
    }

    @Override
    protected List<MoveCard> getMessage() {
        return this.cards;
    }


    @Override
    public String toString() {
        return "{" +
                "cards=" + cards +
                '}';
    }
}
