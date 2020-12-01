package onitama.model;

import onitama.model.figures.Figure;
import onitama.model.moves.Hand;
import onitama.model.moves.Move;
import onitama.model.moves.MoveCard;
import onitama.utils.ObservedSubject;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a player in the game.
 * This contains all its figures, and hand of two cards from which it can choose
 * one and make a move.
 *
 * Together with the Move objects, this is the main interface with which a human player or an AI can communicate
 * and influence the game model.
 * It is an observable class.
 */
public class Player extends ObservedSubject<Player> implements Serializable {

    /**
     * The model containing the player
     */
    private final Game game;
    /**
     * The color of the player
     */
    private final Color color;

    /**
     * A list of the players figures
     */
    private final List<Figure> figures;
    /**
     * The hand wrapping 2 MoveCards
     */
    private final Hand hand;
    /**
     * The MoveCard that is selected by the player
     */
    private MoveCard selectedCard;
    /**
     * The fiure that is selected by the player
     */
    private Figure selectedFigure;
    /**
     * Stores weather it's this players turn
     */
    private boolean isTurn = false;
    /**
     * The name of the palyer
     */
    private String name;

    /**
     * Constructor of Player
     * @param game The game containing this Player
     * @param color The color of the player
     */
    public Player(Game game, Color color) {
        this.game = game;
        this.color = color;
        this.hand = new Hand(2);
        this.figures = new ArrayList<>();
        name = "Anonymous";
    }

    /**
     * Lists all the available moves that are possible with the selected Figure and MoveCard pair.
     * @return The possible moves
     */
    public List<Move> getAvailableMoves() {
        if (selectedCard != null && selectedFigure != null) {
            return selectedCard.getAvailableMoves(selectedFigure);
        }
        return null;
    }

    /**
     * Calculates all the possible moves with all the combinations of figures and cards
     * that can be made in this turn
     * @return the list of all possible moves in this state of the game
     */
    public List<Move> getAllPossibleMoves() {
        ArrayList<Move> result = new ArrayList<>();
        for (MoveCard card : hand.getCards()) {
            for (Figure figure : figures) {
                result.addAll(card.getAvailableMoves(figure));
            }
        }
        return result;
    }

    /**
     * Executes a given move if possible, then calls the game model for the next turn.
     * @param move The move to be executed
     * @throws IllegalArgumentException If for some reason this player can't execute the given move
     */
    public void executeMove(Move move) {
        if (move.getPlayer() != this || !hand.getCards().contains(move.getParentCard()) || !figures.contains(move.getFigure()))
            throw new IllegalArgumentException("Player can't execute this move");
        move.getFigure().moveTo(move.getDestination());
        hand.setCard(game.exchangeCard(move.getParentCard()), move.getParentCard());
        this.selectedFigure = null;
        this.selectedCard = null;
        game.nextTurn();
    }

    /**
     * If the player looses notifies the gui and the game model.
      */
    public void loose() {
        game.playerLost(this);
        fireUpdated();
    }

    /**
     *
     * @return the currently selected MoveCard
     */
    public MoveCard getSelectedCard() {
        return selectedCard;
    }

    /**
     *
     * @param selectedCard the card to be selected
     */
    public void setSelectedCard(MoveCard selectedCard) {
        this.selectedCard = selectedCard;
    }

    /**
     *
     * @return The currently selected Figure
     */
    public Figure getSelectedFigure() {
        return selectedFigure;
    }

    /**
     *
     * @param selectedFigure The figure to be selected
     */
    public void setSelectedFigure(Figure selectedFigure) {
        this.selectedFigure = selectedFigure;
    }

    /**
     *
     * @param f The figure to be added.
     */
    public void addFigure(Figure f) {
        figures.add(f);
    }

    /**
     *
     * @param f The figure to be removed
     */
    public void removeFigure(Figure f) {
        figures.remove(f);
        fireUpdated();
    }

    /**
     *
     * @return True if its this player's turn
     */
    public boolean isTurn() {
        return isTurn;
    }

    /**
     *
     * @param turn Sets if its this player's turn
     */
    public void setTurn(boolean turn) {
        isTurn = turn;
        fireUpdated();
    }

    /**
     *
     * @return List of the player's figures
     */
    public List<Figure> getFigures() {
        return figures;
    }

    /**
     *
     * @return The hand containing the cards of this player
     */
    public Hand getHand() {
        return hand;
    }

    /**
     *
     * @return The color of the player
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return The name of this Player
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name The name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The message generated when fireUpdate is called
     */
    @Override
    protected Player getMessage() {
        return this;
    }
}
