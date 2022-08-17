package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorDetailsApiResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("item_category")
        @Expose
        private List<Item_category> item_category = null;

        public List<Item_category> getItem_category() {
            return item_category;
        }

        public void setItem_category(List<Item_category> item_category) {
            this.item_category = item_category;
        }

    }

    public class Item {

        @SerializedName("item_id")
        @Expose
        private String item_id;
        @SerializedName("item_key")
        @Expose
        private String item_key;
        @SerializedName("category_id")
        @Expose
        private String category_id;
        @SerializedName("vendor_id")
        @Expose
        private String vendor_id;
        @SerializedName("item_price")
        @Expose
        private String item_price;
        @SerializedName("item_package_price")
        @Expose
        private String item_package_price;
        @SerializedName("preparation_time")
        @Expose
        private String preparation_time;
        @SerializedName("item_name")
        @Expose
        private String item_name;
        @SerializedName("item_description")
        @Expose
        private String item_description;
        @SerializedName("item_image_path")
        @Expose
        private String item_image_path;
        @SerializedName("item_ingredients_group")
        @Expose
        private List<Object> item_ingredients_group = null;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_key() {
            return item_key;
        }

        public void setItem_key(String item_key) {
            this.item_key = item_key;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getVendor_id() {
            return vendor_id;
        }

        public void setVendor_id(String vendor_id) {
            this.vendor_id = vendor_id;
        }

        public String getItem_price() {
            return item_price;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public String getItem_package_price() {
            return item_package_price;
        }

        public void setItem_package_price(String item_package_price) {
            this.item_package_price = item_package_price;
        }

        public String getPreparation_time() {
            return preparation_time;
        }

        public void setPreparation_time(String preparation_time) {
            this.preparation_time = preparation_time;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_description() {
            return item_description;
        }

        public void setItem_description(String item_description) {
            this.item_description = item_description;
        }

        public String getItem_image_path() {
            return item_image_path;
        }

        public void setItem_image_path(String item_image_path) {
            this.item_image_path = item_image_path;
        }

        public List<Object> getItem_ingredients_group() {
            return item_ingredients_group;
        }

        public void setItem_ingredients_group(List<Object> item_ingredients_group) {
            this.item_ingredients_group = item_ingredients_group;
        }

    }

    public class Item_category {

        @SerializedName("category_id")
        @Expose
        private String category_id;
        @SerializedName("category_key")
        @Expose
        private String category_key;
        @SerializedName("category_name")
        @Expose
        private String category_name;
        @SerializedName("category_image")
        @Expose
        private String category_image;
        @SerializedName("items")
        @Expose
        private List<Item> items = null;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_key() {
            return category_key;
        }

        public void setCategory_key(String category_key) {
            this.category_key = category_key;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCategory_image() {
            return category_image;
        }

        public void setCategory_image(String category_image) {
            this.category_image = category_image;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

    }

    public class Item_ingredient {

        @SerializedName("ingredient_id")
        @Expose
        private String ingredient_id;
        @SerializedName("ingredient_key")
        @Expose
        private String ingredient_key;
        @SerializedName("ingredient_name")
        @Expose
        private String ingredient_name;
        @SerializedName("ingredient_description")
        @Expose
        private String ingredient_description;
        @SerializedName("ingredient_image")
        @Expose
        private String ingredient_image;
        @SerializedName("price")
        @Expose
        private String price;

        public String getIngredient_id() {
            return ingredient_id;
        }

        public void setIngredient_id(String ingredient_id) {
            this.ingredient_id = ingredient_id;
        }

        public String getIngredient_key() {
            return ingredient_key;
        }

        public void setIngredient_key(String ingredient_key) {
            this.ingredient_key = ingredient_key;
        }

        public String getIngredient_name() {
            return ingredient_name;
        }

        public void setIngredient_name(String ingredient_name) {
            this.ingredient_name = ingredient_name;
        }

        public String getIngredient_description() {
            return ingredient_description;
        }

        public void setIngredient_description(String ingredient_description) {
            this.ingredient_description = ingredient_description;
        }

        public String getIngredient_image() {
            return ingredient_image;
        }

        public void setIngredient_image(String ingredient_image) {
            this.ingredient_image = ingredient_image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

    }

    public class Item_ingredients_group {

        @SerializedName("item_ingredient_group_id")
        @Expose
        private String item_ingredient_group_id;
        @SerializedName("item_ingredient_group_key")
        @Expose
        private String item_ingredient_group_key;
        @SerializedName("ingredient_type")
        @Expose
        private String ingredient_type;
        @SerializedName("minimum")
        @Expose
        private String minimum;
        @SerializedName("maximum")
        @Expose
        private String maximum;
        @SerializedName("group_name")
        @Expose
        private String group_name;
        @SerializedName("item_ingredients")
        @Expose
        private List<Item_ingredient> item_ingredients = null;

        public String getItem_ingredient_group_id() {
            return item_ingredient_group_id;
        }

        public void setItem_ingredient_group_id(String item_ingredient_group_id) {
            this.item_ingredient_group_id = item_ingredient_group_id;
        }

        public String getItem_ingredient_group_key() {
            return item_ingredient_group_key;
        }

        public void setItem_ingredient_group_key(String item_ingredient_group_key) {
            this.item_ingredient_group_key = item_ingredient_group_key;
        }

        public String getIngredient_type() {
            return ingredient_type;
        }

        public void setIngredient_type(String ingredient_type) {
            this.ingredient_type = ingredient_type;
        }

        public String getMinimum() {
            return minimum;
        }

        public void setMinimum(String minimum) {
            this.minimum = minimum;
        }

        public String getMaximum() {
            return maximum;
        }

        public void setMaximum(String maximum) {
            this.maximum = maximum;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public List<Item_ingredient> getItem_ingredients() {
            return item_ingredients;
        }

        public void setItem_ingredients(List<Item_ingredient> item_ingredients) {
            this.item_ingredients = item_ingredients;
        }

    }
}
