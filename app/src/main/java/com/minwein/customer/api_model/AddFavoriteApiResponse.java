package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tech on 19-Jan-2018.
 */

public class AddFavoriteApiResponse {

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
        @SerializedName("vendor_fav_count")
        @Expose
        private Integer vendor_fav_count;

        public Integer getVendor_fav_count() {
            return vendor_fav_count;
        }

        public void setVendor_fav_count(Integer vendor_fav_count) {
            this.vendor_fav_count = vendor_fav_count;
        }

    }

}
