package com.minwein.customer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AMSHEER on 12-05-2018.
 */

public class RatingModel {






    Integer randomKey;


    @SerializedName("item_id")
    @Expose
    private String item_id;
    @SerializedName("Taste")
    @Expose
    private String taste;
    @SerializedName("Timing")
    @Expose
    private String timing;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("item_rating_review_text")
    @Expose
    private String item_rating_review_text;



    public Integer getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(Integer randomKey) {
        this.randomKey = randomKey;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem_rating_review_text() {
        return item_rating_review_text;
    }

    public void setItem_rating_review_text(String item_rating_review_text) {
        this.item_rating_review_text = item_rating_review_text;
    }
}
