package ca.cmpt213.a5_backend.models;

import ca.cmpt213.a5_backend.controllers.TokimonController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Logic class that handles the logic of the application
 * It reads from the file and writes to the file
 * It also adds, updates, deletes, and sorts the cards
 * It also returns the size of the cards
 * It also returns the cards
 * It also empties the json file and the array
 * It also sorts the cards by criteria
 * It also gets the card by index
 *
 * It is responsible for all the logic of the application
 * It also used am arrayList as a backup database for the server. Incase the server goes down, all the cards can be retrieved from the arraylist
 *
 */
public class Logic{

    private ArrayList<Card> cards = new ArrayList<>();
    private static int numOfCards = 0;

    public Logic(){
        // Read from file
        try{
            JsonArray jsonArray = new Gson().fromJson(new FileReader("src/main/resources/static/data/tokimoncards.json"), JsonArray.class);
            if(jsonArray == null){
                return;
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                Card card = new Gson().fromJson(jsonArray.get(i), Card.class);
                cards.add(card);
                numOfCards++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addCard(Card card) {
        cards.add(card);
        numOfCards++;
        writeToFile();
        System.out.println(cards);
    }

    public Card getCard(int id) {
        for (Card card : cards) {
            if (card.getID() == id) {
                return card;
            }
        }
        return null;
    }


    public void updateCard(Card newCard, int id) {
        for (Card card : cards) {
            if (card.getID() == id) {
                card.setName(newCard.getName());
                card.setType(newCard.getType());
                card.setRarity(newCard.getRarity());
                card.setImageURL(newCard.getImageURL());
                card.setHealthPoints(newCard.getHealthPoints());
                card.setAttack(newCard.getAttack());

                // Save changes to file
                writeToFile();
                return;  // Exit after updating
            }
        }
    }

    public boolean deleteCard(int id) {
        Iterator<Card> iterator = cards.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getID() == id) {
                iterator.remove();

                // Save changes to file
                writeToFile();
                return true;
            }
        }
        return false;
    }

    public void writeToFile() {
        try (FileWriter fileWriter = new FileWriter("src/main/resources/static/data/tokimoncards.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(cards);
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void emptyJsonAndArray(){
        if(this.cards != null) {
            this.cards.clear();
            this.numOfCards = 0;
        }
        writeToFile();
    }
}