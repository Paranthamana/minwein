package com.minwein.customer.model;



/*** Created by AMSHEER on 19-01-2018.
 */

public class Ingredients {
    private String ingredientsId;
    private String ingredientsKey;
    private String ingredient_name;
    private String groupName;
    private String groupKey;
    private String ingredientsQuantity;
    private String price;
    private Integer item_ingredients_id;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Integer getItem_ingredients_id() {
        return item_ingredients_id;
    }

    public void setItem_ingredients_id(Integer item_ingredients_id) {
        this.item_ingredients_id = item_ingredients_id;
    }



    public String getIngredientsKey() {
        return ingredientsKey;
    }

    public void setIngredientsKey(String ingredientsKey) {
        this.ingredientsKey = ingredientsKey;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public String getIngredientsId() {
        return ingredientsId;
    }

    public void setIngredientsId(String ingredientsId) {
        this.ingredientsId = ingredientsId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIngredientsQuantity() {
        return ingredientsQuantity;
    }

    public void setIngredientsQuantity(String ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
    }
}