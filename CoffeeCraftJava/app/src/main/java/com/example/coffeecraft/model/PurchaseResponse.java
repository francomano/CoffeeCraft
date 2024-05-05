package com.example.coffeecraft.model;
import com.google.gson.annotations.SerializedName;

public class PurchaseResponse {
    @SerializedName("mood")
    private String mood;

    @SerializedName("sugar")
    private int sugar;

    @SerializedName("coffee_type")
    private String coffeeType;

    @SerializedName("id")
    private int id;

    @SerializedName("owner_id")
    private int ownerId;

    // Getter methods for accessing the fields

    public String getMood() {
        return mood;
    }

    public int getSugar() {
        return sugar;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
