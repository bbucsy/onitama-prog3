package onitama.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import onitama.model.moves.MoveCard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * A singleton responsible loading the MoveCards using gson library.
 * This class has methods for randomly selecting an amount of cards from the deck.
 */
public class MoveCardStorage {

    /**
     * The instance of the class
     */
    private static final MoveCardStorage instance = new MoveCardStorage();
    /**
     * A list of all the loaded cards.
     */
    private final List<MoveCard> cards;

    /**
     * Private constructor
     */
    private MoveCardStorage() {
        cards = new ArrayList<>();
    }

    /**
     *
     * @return The instance of this singleton
     */
    public static MoveCardStorage getInstance() {
        return instance;
    }

    /**
     * Tries to load cards from a given JSON
     * @param filepath the path to the json
     * @throws FileNotFoundException If the file is not found
     */
    public void loadCards(String filepath) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileReader fr = new FileReader(filepath);
        Type cardListType = new TypeToken<List<MoveCard>>() {
        }.getType();

        List<MoveCard> loaded = gson.fromJson(fr, cardListType);
        cards.addAll(loaded);

    }

    /**
     * Gets x pieces of cards from the deck, and gives a stack of them
     * @param amount    The amount of cards to be taken from the deck
     * @return  A stack of random cards
     * @throws IllegalArgumentException If the amount is more than the cards in the deck
     */
    public Stack<MoveCard> getRandomCards(int amount) throws IOException, ClassNotFoundException {
        if (amount > cards.size()) throw new IllegalArgumentException();


        List<MoveCard> clone = new ArrayList<>(cards);
        Stack<MoveCard> returnCards = new Stack<>();
        Random rand = new Random();

        for (int i = 0; i < amount; i++) {
            MoveCard rc = clone.get(rand.nextInt(clone.size()));
            returnCards.push(rc.deepClone());
            clone.remove(rc);
        }
        return returnCards;
    }


}
