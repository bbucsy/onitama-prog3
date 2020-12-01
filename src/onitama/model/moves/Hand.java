package onitama.model.moves;

import onitama.utils.ObservedSubject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps movement cards, and when one is exchanged/removed/added
 * notifies every observers, that there might be a need to update the UI.
 */
public class Hand extends ObservedSubject<List<MoveCard>> implements Serializable {

    /**
     * A list of cards in the hand.
     */
    private final List<MoveCard> cards;
    /**
     * The maximum number of cards that can be in the hand.
     */
    private final int max;

    /**
     * Constructor of Hand
     * @param max The maximum number of cards that can be in the hand.
     */
    public Hand(int max) {
        this.max = max;
        cards = new ArrayList<>();
    }

    /**
     * Ads a card to the hand if there is enough space.
     * @param card  The card to be added to the hand.
     */
    public void addCard(MoveCard card) {
        if (cards.size() < max) {
            cards.add(card);
            fireUpdated();
        }
    }

    /**
     * Ads the card to the i-th place. Removes the card in that position if there are any.
     * @param card The card to add to the hand
     * @param i The position of the new card
     */
    public void setCard(MoveCard card, int i) {
        if (i >= max) return;
        if (i < cards.size())
            cards.remove(i);
        cards.add(i, card);
        fireUpdated();
    }

    /**
     * Exchanges the crd given to it to an other card in the hand
     * @param card  The new card to be added
     * @param exchanged The card which will be removed from the hand
     * @throws IllegalArgumentException If the card that is given to exchange isn't in the hand.
     */
    public void setCard(MoveCard card, MoveCard exchanged) {
        int pos = cards.indexOf(exchanged);
        if (pos == -1) throw new IllegalArgumentException("Can't exchange card that is not in hand.");

        cards.remove(exchanged);
        cards.add(pos, card);
        fireUpdated();
    }

    /**
     *
     * @return The list of cards in the hand
     */
    public List<MoveCard> getCards() {
        return cards;
    }

    /**
     *
     * @return The message generated to notify the observers.
     */
    @Override
    protected List<MoveCard> getMessage() {
        return cards;
    }
}
