package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantRatingApiResponse {

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

    @SerializedName("vendor_rating")
    @Expose
    private List<Vendor_rating> vendor_rating = null;
    @SerializedName("vendor_rating_average")
    @Expose
    private String vendor_rating_average;
    @SerializedName("vendor_name")
    @Expose
    private String vendor_name;

    public List<Vendor_rating> getVendor_rating() {
        return vendor_rating;
    }

    public void setVendor_rating(List<Vendor_rating> vendor_rating) {
        this.vendor_rating = vendor_rating;
    }

    public String getVendor_rating_average() {
        return vendor_rating_average;
    }

    public void setVendor_rating_average(String vendor_rating_average) {
        this.vendor_rating_average = vendor_rating_average;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public class Vendor_rating {

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
        @SerializedName("rating_Delivery_time")
        @Expose
        private String rating_Delivery_time;
        @SerializedName("rating_Food_temperature")
        @Expose
        private String rating_Food_temperature;
        @SerializedName("rating_Customer_service")
        @Expose
        private String rating_Customer_service;
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
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;

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

        public String getRating_Delivery_time() {
            return rating_Delivery_time;
        }

        public void setRating_Delivery_time(String rating_Delivery_time) {
            this.rating_Delivery_time = rating_Delivery_time;
        }

        public String getRating_Food_temperature() {
            return rating_Food_temperature;
        }

        public void setRating_Food_temperature(String rating_Food_temperature) {
            this.rating_Food_temperature = rating_Food_temperature;
        }

        public String getRating_Customer_service() {
            return rating_Customer_service;
        }

        public void setRating_Customer_service(String rating_Customer_service) {
            this.rating_Customer_service = rating_Customer_service;
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

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

    }
}

}