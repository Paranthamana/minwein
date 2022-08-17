package com.minwein.customer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartItems {

    @SerializedName("cart")
    @Expose
    private List<Cart> cart = new ArrayList<>();
    @SerializedName("user_details")
    @Expose
    private User_detail user_details = new User_detail();

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public User_detail getUser_details() {
        return user_details;
    }

    public void setUser_details(User_detail user_details) {
        this.user_details = user_details;
    }


    public static class Cart {

        @SerializedName("items")
        @Expose
        private List<Item> items = null;
        @SerializedName("vendor_key")
        @Expose
        private String vendor_key;

        @SerializedName("vendor_name")
        @Expose
        private String vendor_name;
        @SerializedName("delivery_options")
        @Expose
        private Delivery_option delivery_options;


        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public String getVendor_key() {
            return vendor_key;
        }

        public void setVendor_key(String vendor_key) {
            this.vendor_key = vendor_key;
        }

        public Delivery_option getDelivery_options() {
            return delivery_options;
        }

        public void setDelivery_options(Delivery_option delivery_options) {
            this.delivery_options = delivery_options;
        }

    }

    public static class Delivery_option {

        @SerializedName("orderMessage")
        @Expose
        private String orderMessage= "";
        @SerializedName("deliveryType")
        @Expose
        private String deliveryType = "1";
        @SerializedName("driverType")
        @Expose
        private String driverType = "1";
        @SerializedName("deliveryDate")
        @Expose
        private String deliveryDate = "";
        @SerializedName("paymentType")
        @Expose
        private String paymentType = "1";

        @SerializedName("couponCode")
        @Expose
        private String couponCode = "";

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getOrderMessage() {
            return orderMessage;
        }

        public void setOrderMessage(String orderMessage) {
            this.orderMessage = orderMessage;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public String getDriverType() {
            return driverType;
        }

        public void setDriverType(String driverType) {
            this.driverType = driverType;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

    }

    public static class Ingredient {

        @SerializedName("ingredient_key")
        @Expose
        private String ingredient_key;

        @SerializedName("group_key")
        @Expose
        private String group_key;

        public String getGroup_key() {
            return group_key;
        }

        public void setGroup_key(String group_key) {
            this.group_key = group_key;
        }

        public String getIngredient_key() {
            return ingredient_key;
        }

        public void setIngredient_key(String ingredient_key) {
            this.ingredient_key = ingredient_key;
        }

    }

    public static class Item {

        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("item_key")
        @Expose
        private String item_key;
        @SerializedName("ingredients")
        @Expose
        private List<Ingredient> ingredients = null;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getItem_key() {
            return item_key;
        }

        public void setItem_key(String item_key) {
            this.item_key = item_key;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

    }

    public class User_detail {

        @SerializedName("address_key")
        @Expose
        private String address_key;

        @SerializedName("user_key")
        @Expose
        private String user_key;

        @SerializedName("latitude")
        @Expose
        private String latitude;

        @SerializedName("longitude")
        @Expose
        private String longitude;


        @SerializedName("loyalty")
        @Expose
        private String loyalty;


        public String getLoyalty() {
            return loyalty;
        }

        public void setLoyalty(String loyalty) {
            this.loyalty = loyalty;
        }

        public String getAddress_key() {
            return address_key;
        }

        public void setAddress_key(String address_key) {
            this.address_key = address_key;
        }

        public String getUser_key() {
            return user_key;
        }

        public void setUser_key(String user_key) {
            this.user_key = user_key;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

    }
}