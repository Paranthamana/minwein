package com.minwein.customer.api_model;

/**
 * Created by Tech on 18-Jan-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddRestaurantRatingApiResponse {

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

        @SerializedName("order_id")
        @Expose
        private String order_id;
        @SerializedName("rating_review_user_id")
        @Expose
        private String rating_review_user_id;
        @SerializedName("rating_reference_id")
        @Expose
        private String rating_reference_id;
        @SerializedName("rating_review_type")
        @Expose
        private Integer rating_review_type;
        @SerializedName("rating_1")
        @Expose
        private String rating_1;
        @SerializedName("rating_2")
        @Expose
        private String rating_2;
        @SerializedName("rating_3")
        @Expose
        private String rating_3;
        @SerializedName("rating_average")
        @Expose
        private String rating_average;
        @SerializedName("rating_review_text")
        @Expose
        private String rating_review_text;
        @SerializedName("rating_review_status")
        @Expose
        private Integer rating_review_status;
        @SerializedName("loyalty_points_earned")
        @Expose
        private String loyalty_points_earned;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getRating_review_user_id() {
            return rating_review_user_id;
        }

        public void setRating_review_user_id(String rating_review_user_id) {
            this.rating_review_user_id = rating_review_user_id;
        }

        public String getRating_reference_id() {
            return rating_reference_id;
        }

        public void setRating_reference_id(String rating_reference_id) {
            this.rating_reference_id = rating_reference_id;
        }

        public Integer getRating_review_type() {
            return rating_review_type;
        }

        public void setRating_review_type(Integer rating_review_type) {
            this.rating_review_type = rating_review_type;
        }

        public String getRating_1() {
            return rating_1;
        }

        public void setRating_1(String rating_1) {
            this.rating_1 = rating_1;
        }

        public String getRating_2() {
            return rating_2;
        }

        public void setRating_2(String rating_2) {
            this.rating_2 = rating_2;
        }

        public String getRating_3() {
            return rating_3;
        }

        public void setRating_3(String rating_3) {
            this.rating_3 = rating_3;
        }

        public String getRating_average() {
            return rating_average;
        }

        public void setRating_average(String rating_average) {
            this.rating_average = rating_average;
        }

        public String getRating_review_text() {
            return rating_review_text;
        }

        public void setRating_review_text(String rating_review_text) {
            this.rating_review_text = rating_review_text;
        }

        public Integer getRating_review_status() {
            return rating_review_status;
        }

        public void setRating_review_status(Integer rating_review_status) {
            this.rating_review_status = rating_review_status;
        }

        public String getLoyalty_points_earned() {
            return loyalty_points_earned;
        }

        public void setLoyalty_points_earned(String loyalty_points_earned) {
            this.loyalty_points_earned = loyalty_points_earned;
        }

    }

}