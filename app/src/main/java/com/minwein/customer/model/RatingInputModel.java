package com.minwein.customer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RatingInputModel {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("vendor_type")
    @Expose
    private String vendorType;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("item")
    @Expose
    private List<RatingModel> item = null;
    @SerializedName("Delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("Food_temperature")
    @Expose
    private String foodTemperature;
    @SerializedName("Customer_service")
    @Expose
    private String customerService;
    @SerializedName("vendor_rating_review_text")
    @Expose
    private String vendorRatingReviewText;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<RatingModel> getItem() {
        return item;
    }

    public void setItem(List<RatingModel> item) {
        this.item = item;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getFoodTemperature() {
        return foodTemperature;
    }

    public void setFoodTemperature(String foodTemperature) {
        this.foodTemperature = foodTemperature;
    }

    public String getCustomerService() {
        return customerService;
    }

    public void setCustomerService(String customerService) {
        this.customerService = customerService;
    }

    public String getVendorRatingReviewText() {
        return vendorRatingReviewText;
    }

    public void setVendorRatingReviewText(String vendorRatingReviewText) {
        this.vendorRatingReviewText = vendorRatingReviewText;
    }



}