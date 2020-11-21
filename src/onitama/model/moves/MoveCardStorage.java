package onitama.model.moves;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoveCardStorage {


    private List<MoveCard> cards;

    public MoveCardStorage(String filepath) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileReader fr = new FileReader(filepath);
        Type cardListType = new TypeToken<List<MoveCard>>() {}.getType();
        cards = gson.fromJson(fr, cardListType);

        System.out.println(cards.size());

    }


    public void saveCardList() throws IOException {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter("cards.json");
        writer.write(gson.toJson(cards));
        writer.close();
    }


    public List<MoveCard> getRandomCards(int amount){
        if (amount > cards.size()) throw new IllegalArgumentException();

        List<MoveCard> returnCards = new ArrayList<>();
        Random rand = new Random();

        for(int i = 0; i < amount; i++){
            MoveCard rc = cards.get(rand.nextInt(cards.size()));
            returnCards.add(rc);
            cards.remove(rc);
        }

        return returnCards;
    }


}
