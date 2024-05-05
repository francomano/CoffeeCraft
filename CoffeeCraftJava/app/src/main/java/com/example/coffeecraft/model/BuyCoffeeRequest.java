package com.example.coffeecraft.model;
import com.google.gson.annotations.SerializedName;

public class BuyCoffeeRequest {

    @SerializedName("mood")
    private String mood;

    @SerializedName("sugar")
    private int sugar;

    @SerializedName("coffee_type")
    private String coffeeType;

    public BuyCoffeeRequest(String mood, int sugar, String coffeeType) {
        this.mood = mood;
        this.sugar = sugar;
        this.coffeeType = coffeeType;
    }
}
