package ca.cmpt213.a5_backend.models;

public class Card {

    /**
     * Card class to store the card details
     * It has the following attributes:
     * 1. ID
     * 2. Name
     * 3. Type
     * 4. Rarity
     * 5. ImageURL
     * 6. HealthPoints
     * 7. Attack
     *
     *
     * It also has a contructor that initializes the card with the given values
     *  It also has the necessary getters and setters
     */

    public int ID;
    private String name;
    private String type;
    private int rarity;
    private String imageURL;
    private int healthPoints;
    private int attack;

    public Card(String imageURL, String name, String type, int rarity, int healthPoints, int attack, int id) {
        setID(id);
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.imageURL = imageURL;
        this.healthPoints = healthPoints;
        this.attack = attack;
    }

    public void setID(int id) {
        this.ID = id;
    }

    // Getters and setters...
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getRarity() {
        return rarity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getAttack() {
        return attack;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public String toString() {
        return "Card{" +
                "ID=" + ID +
                ", imageURL='" + imageURL + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", rarity=" + rarity +
                ", healthPoints=" + healthPoints +
                ", attack=" + attack +
                '}';
    }
}

