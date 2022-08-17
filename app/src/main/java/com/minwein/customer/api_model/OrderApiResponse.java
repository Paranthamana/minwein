package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderApiResponse {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("data")
@Expose
private List<Datum> data = null;
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

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
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

    public class Datum {

        @SerializedName("order_id")
        @Expose
        private String order_id;
        @SerializedName("order_key")
        @Expose
        private String order_key;
        @SerializedName("order_number")
        @Expose
        private String order_number;
        @SerializedName("order_date_time")
        @Expose
        private String order_date_time;
        @SerializedName("order_total")
        @Expose
        private String order_total;
        @SerializedName("order_status")
        @Expose
        private String order_status;
        @SerializedName("vendor_image")
        @Expose
        private String vendor_image;
        @SerializedName("vendor_name")
        @Expose
        private String vendor_name;
        @SerializedName("vendor_id")
        @Expose
        private String vendor_id;
        @SerializedName("user_id")
        @Expose
        private String user_id;
        @SerializedName("cuisine_name")
        @Expose
        private String cuisine_name;

        @SerializedName("rating_status")
        @Expose
        private String rating_status;

        public String getRating_status() {
            return rating_status;
        }

        public void setRating_status(String rating_status) {
            this.rating_status = rating_status;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_key() {
            return order_key;
        }

        public void setOrder_key(String order_key) {
            this.order_key = order_key;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getOrder_date_time() {
            return order_date_time;
        }

        public void setOrder_date_time(String order_date_time) {
            this.order_date_time = order_date_time;
        }

        public String getOrder_total() {
            return order_total;
        }

        public void setOrder_total(String order_total) {
            this.order_total = order_total;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getVendor_image() {
            return vendor_image;
        }

        public void setVendor_image(String vendor_image) {
            this.vendor_image = vendor_image;
        }

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public String getVendor_id() {
            return vendor_id;
        }

        public void setVendor_id(String vendor_id) {
            this.vendor_id = vendor_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCuisine_name() {
            return cuisine_name;
        }

        public void setCuisine_name(String cuisine_name) {
            this.cuisine_name = cuisine_name;
        }
    }

}
