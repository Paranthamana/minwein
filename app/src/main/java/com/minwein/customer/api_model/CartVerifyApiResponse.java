package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartVerifyApiResponse {

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
    public class Cart {

        @SerializedName("delivery_options")
        @Expose
        private Delivery_options delivery_options;
        @SerializedName("items")
        @Expose
        private List<Item> items = null;
        @SerializedName("vendor_key")
        @Expose
        private String vendor_key;
        @SerializedName("vendor_name")
        @Expose
        private String vendor_name;
        @SerializedName("error_key")
        @Expose
        private Integer error_key;
        @SerializedName("error_message")
        @Expose
        private String error_message;
        @SerializedName("vendor_total_array")
        @Expose
        private Vendor_total_array vendor_total_array;

        public Delivery_options getDelivery_options() {
            return delivery_options;
        }

        public void setDelivery_options(Delivery_options delivery_options) {
            this.delivery_options = delivery_options;
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

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public Integer getError_key() {
            return error_key;
        }

        public void setError_key(Integer error_key) {
            this.error_key = error_key;
        }

        public String getError_message() {
            return error_message;
        }

        public void setError_message(String error_message) {
            this.error_message = error_message;
        }

        public Vendor_total_array getVendor_total_array() {
            return vendor_total_array;
        }

        public void setVendor_total_array(Vendor_total_array vendor_total_array) {
            this.vendor_total_array = vendor_total_array;
        }

    }
    public class Data {

        @SerializedName("cart")
        @Expose
        private List<Cart> cart = null;
        @SerializedName("grand_total_array")
        @Expose
        private Grand_total_array grand_total_array;

        public List<Cart> getCart() {
            return cart;
        }

        public void setCart(List<Cart> cart) {
            this.cart = cart;
        }

        public Grand_total_array getGrand_total_array() {
            return grand_total_array;
        }

        public void setGrand_total_array(Grand_total_array grand_total_array) {
            this.grand_total_array = grand_total_array;
        }

    }

    public class Delivery_options {

        @SerializedName("deliveryDate")
        @Expose
        private String deliveryDate;
        @SerializedName("deliveryType")
        @Expose
        private String deliveryType;
        @SerializedName("driverType")
        @Expose
        private String driverType;
        @SerializedName("orderMessage")
        @Expose
        private String orderMessage;
        @SerializedName("paymentType")
        @Expose
        private String paymentType;

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
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

        public String getOrderMessage() {
            return orderMessage;
        }

        public void setOrderMessage(String orderMessage) {
            this.orderMessage = orderMessage;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

    }

    public class Grand_total_array {

        @SerializedName("grandTotal")
        @Expose
        private Double grandTotal;
        @SerializedName("grandOnline")
        @Expose
        private Double grandOnline;
        @SerializedName("grandCash")
        @Expose
        private Double grandCash;

        public Double getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(Double grandTotal) {
            this.grandTotal = grandTotal;
        }

        public Double getGrandOnline() {
            return grandOnline;
        }

        public void setGrandOnline(Double grandOnline) {
            this.grandOnline = grandOnline;
        }

        public Double getGrandCash() {
            return grandCash;
        }

        public void setGrandCash(Double grandCash) {
            this.grandCash = grandCash;
        }

    }

    public class Item {

        @SerializedName("ingredients")
        @Expose
        private List<Object> ingredients = null;
        @SerializedName("item_key")
        @Expose
        private String item_key;
        @SerializedName("quantity")
        @Expose
        private String quantity;

        public List<Object> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Object> ingredients) {
            this.ingredients = ingredients;
        }

        public String getItem_key() {
            return item_key;
        }

        public void setItem_key(String item_key) {
            this.item_key = item_key;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

    }

    public class Vendor_total_array {

        @SerializedName("deliveryFee")
        @Expose
        private String deliveryFee;
        @SerializedName("service_charge")
        @Expose
        private String service_charge;
        @SerializedName("discount_Amount")
        @Expose
        private Integer discount_Amount;
        @SerializedName("VAT_charge")
        @Expose
        private String vAT_charge;
        @SerializedName("vendorTotal")
        @Expose
        private Double vendorTotal;

        public String getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(String deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public String getService_charge() {
            return service_charge;
        }

        public void setService_charge(String service_charge) {
            this.service_charge = service_charge;
        }

        public Integer getDiscount_Amount() {
            return discount_Amount;
        }

        public void setDiscount_Amount(Integer discount_Amount) {
            this.discount_Amount = discount_Amount;
        }

        public String getVAT_charge() {
            return vAT_charge;
        }

        public void setVAT_charge(String vAT_charge) {
            this.vAT_charge = vAT_charge;
        }

        public Double getVendorTotal() {
            return vendorTotal;
        }

        public void setVendorTotal(Double vendorTotal) {
            this.vendorTotal = vendorTotal;
        }

    }
}