package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemDetailsResponse {

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
    public class Category_detail {

        @SerializedName("category_lang_id")
        @Expose
        private Integer category_lang_id;
        @SerializedName("category_id")
        @Expose
        private Integer category_id;
        @SerializedName("language_code")
        @Expose
        private String language_code;
        @SerializedName("category_name")
        @Expose
        private String category_name;
        @SerializedName("category_image")
        @Expose
        private String category_image;

        public Integer getCategory_lang_id() {
            return category_lang_id;
        }

        public void setCategory_lang_id(Integer category_lang_id) {
            this.category_lang_id = category_lang_id;
        }

        public Integer getCategory_id() {
            return category_id;
        }

        public void setCategory_id(Integer category_id) {
            this.category_id = category_id;
        }

        public String getLanguage_code() {
            return language_code;
        }

        public void setLanguage_code(String language_code) {
            this.language_code = language_code;
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

    }

    public class Data {

        @SerializedName("item_details")
        @Expose
        private Item_details item_details;
        @SerializedName("item_rating_info")
        @Expose
        private List<Item_rating_info> item_rating_info = null;

        public Item_details getItem_details() {
            return item_details;
        }

        public void setItem_details(Item_details item_details) {
            this.item_details = item_details;
        }

        public List<Item_rating_info> getItem_rating_info() {
            return item_rating_info;
        }

        public void setItem_rating_info(List<Item_rating_info> item_rating_info) {
            this.item_rating_info = item_rating_info;
        }

    }

    public class Details {

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

    }

    public class Ingredient {

        @SerializedName("is_selected")
        @Expose
        private Boolean is_selected;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("details")
        @Expose
        private Details details;

        public Boolean getIs_selected() {
            return is_selected;
        }

        public void setIs_selected(Boolean is_selected) {
            this.is_selected = is_selected;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Details getDetails() {
            return details;
        }

        public void setDetails(Details details) {
            this.details = details;
        }

    }

    public class Item_details {

        @SerializedName("item_key")
        @Expose
        private String item_key;
        @SerializedName("item_id")
        @Expose
        private Integer item_id;
        @SerializedName("category_detail")
        @Expose
        private Category_detail category_detail;
        @SerializedName("preparation_time")
        @Expose
        private Integer preparation_time;
        @SerializedName("item_price")
        @Expose
        private Integer item_price;
        @SerializedName("loyalty_points")
        @Expose
        private Integer loyalty_points;
        @SerializedName("sort_no")
        @Expose
        private Object sort_no;
        @SerializedName("item_name")
        @Expose
        private String item_name;
        @SerializedName("item_description")
        @Expose
        private String item_description;
        @SerializedName("item_image")
        @Expose
        private List<Item_image> item_image = null;
        @SerializedName("item_ingredient")
        @Expose
        private List<Item_ingredient> item_ingredient = null;

        public String getItem_key() {
            return item_key;
        }

        public void setItem_key(String item_key) {
            this.item_key = item_key;
        }

        public Integer getItem_id() {
            return item_id;
        }

        public void setItem_id(Integer item_id) {
            this.item_id = item_id;
        }

        public Category_detail getCategory_detail() {
            return category_detail;
        }

        public void setCategory_detail(Category_detail category_detail) {
            this.category_detail = category_detail;
        }

        public Integer getPreparation_time() {
            return preparation_time;
        }

        public void setPreparation_time(Integer preparation_time) {
            this.preparation_time = preparation_time;
        }

        public Integer getItem_price() {
            return item_price;
        }

        public void setItem_price(Integer item_price) {
            this.item_price = item_price;
        }

        public Integer getLoyalty_points() {
            return loyalty_points;
        }

        public void setLoyalty_points(Integer loyalty_points) {
            this.loyalty_points = loyalty_points;
        }

        public Object getSort_no() {
            return sort_no;
        }

        public void setSort_no(Object sort_no) {
            this.sort_no = sort_no;
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

        public List<Item_image> getItem_image() {
            return item_image;
        }

        public void setItem_image(List<Item_image> item_image) {
            this.item_image = item_image;
        }

        public List<Item_ingredient> getItem_ingredient() {
            return item_ingredient;
        }

        public void setItem_ingredient(List<Item_ingredient> item_ingredient) {
            this.item_ingredient = item_ingredient;
        }

    }

    public class Item_image {

        @SerializedName("language_code")
        @Expose
        private String language_code;
        @SerializedName("item_image_path")
        @Expose
        private String item_image_path;

        public String getLanguage_code() {
            return language_code;
        }

        public void setLanguage_code(String language_code) {
            this.language_code = language_code;
        }

        public String getItem_image_path() {
            return item_image_path;
        }

        public void setItem_image_path(String item_image_path) {
            this.item_image_path = item_image_path;
        }

    }

    public class Item_ingredient {

        @SerializedName("item_ingredient_group_key")
        @Expose
        private String item_ingredient_group_key;
        @SerializedName("ingredient_type")
        @Expose
        private Integer ingredient_type;
        @SerializedName("minimum")
        @Expose
        private Integer minimum;
        @SerializedName("maximum")
        @Expose
        private Integer maximum;
        @SerializedName("group_name")
        @Expose
        private String group_name;
        @SerializedName("ingredients")
        @Expose
        private List<Ingredient> ingredients = null;

        public String getItem_ingredient_group_key() {
            return item_ingredient_group_key;
        }

        public void setItem_ingredient_group_key(String item_ingredient_group_key) {
            this.item_ingredient_group_key = item_ingredient_group_key;
        }

        public Integer getIngredient_type() {
            return ingredient_type;
        }

        public void setIngredient_type(Integer ingredient_type) {
            this.ingredient_type = ingredient_type;
        }

        public Integer getMinimum() {
            return minimum;
        }

        public void setMinimum(Integer minimum) {
            this.minimum = minimum;
        }

        public Integer getMaximum() {
            return maximum;
        }

        public void setMaximum(Integer maximum) {
            this.maximum = maximum;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

    }

    public class Item_rating_info {

        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("rating_review_id")
        @Expose
        private String rating_review_id;
        @SerializedName("rating_reference_id")
        @Expose
        private String rating_reference_id;
        @SerializedName("rating_review_user_id")
        @Expose
        private String rating_review_user_id;
        @SerializedName("rating_review_type")
        @Expose
        private String rating_review_type;
        @SerializedName("rating_Taste")
        @Expose
        private String rating_Taste;
        @SerializedName("rating_Timing")
        @Expose
        private String rating_Timing;
        @SerializedName("rating_Price")
        @Expose
        private String rating_Price;
        @SerializedName("rating_average")
        @Expose
        private String rating_average;
        @SerializedName("loyalty_points_earned")
        @Expose
        private String loyalty_points_earned;
        @SerializedName("rating_review_text")
        @Expose
        private String rating_review_text;
        @SerializedName("rating_review_status")
        @Expose
        private String rating_review_status;
        @SerializedName("rating_review_datetime")
        @Expose
        private String rating_review_datetime;

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getRating_review_id() {
            return rating_review_id;
        }

        public void setRating_review_id(String rating_review_id) {
            this.rating_review_id = rating_review_id;
        }

        public String getRating_reference_id() {
            return rating_reference_id;
        }

        public void setRating_reference_id(String rating_reference_id) {
            this.rating_reference_id = rating_reference_id;
        }

        public String getRating_review_user_id() {
            return rating_review_user_id;
        }

        public void setRating_review_user_id(String rating_review_user_id) {
            this.rating_review_user_id = rating_review_user_id;
        }

        public String getRating_review_type() {
            return rating_review_type;
        }

        public void setRating_review_type(String rating_review_type) {
            this.rating_review_type = rating_review_type;
        }

        public String getRating_Taste() {
            return rating_Taste;
        }

        public void setRating_Taste(String rating_Taste) {
            this.rating_Taste = rating_Taste;
        }

        public String getRating_Timing() {
            return rating_Timing;
        }

        public void setRating_Timing(String rating_Timing) {
            this.rating_Timing = rating_Timing;
        }

        public String getRating_Price() {
            return rating_Price;
        }

        public void setRating_Price(String rating_Price) {
            this.rating_Price = rating_Price;
        }

        public String getRating_average() {
            return rating_average;
        }

        public void setRating_average(String rating_average) {
            this.rating_average = rating_average;
        }

        public String getLoyalty_points_earned() {
            return loyalty_points_earned;
        }

        public void setLoyalty_points_earned(String loyalty_points_earned) {
            this.loyalty_points_earned = loyalty_points_earned;
        }

        public String getRating_review_text() {
            return rating_review_text;
        }

        public void setRating_review_text(String rating_review_text) {
            this.rating_review_text = rating_review_text;
        }

        public String getRating_review_status() {
            return rating_review_status;
        }

        public void setRating_review_status(String rating_review_status) {
            this.rating_review_status = rating_review_status;
        }

        public String getRating_review_datetime() {
            return rating_review_datetime;
        }

        public void setRating_review_datetime(String rating_review_datetime) {
            this.rating_review_datetime = rating_review_datetime;
        }

    }
}