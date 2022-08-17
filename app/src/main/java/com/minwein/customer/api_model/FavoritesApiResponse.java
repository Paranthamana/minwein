package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoritesApiResponse {

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

        @SerializedName("favourite_list")
        @Expose
        private List<FavouriteList> favouriteList = null;

        public List<FavouriteList> getFavouriteList() {
            return favouriteList;
        }

        public void setFavouriteList(List<FavouriteList> favouriteList) {
            this.favouriteList = favouriteList;
        }

    }
    public class FavouriteList {

        @SerializedName("fav_id")
        @Expose
        private String favId;
        @SerializedName("vendor_key")
        @Expose
        private String vendorKey;
        @SerializedName("vendor_name")
        @Expose
        private String vendorName;
        @SerializedName("vendor_image")
        @Expose
        private String vendorImage;
        @SerializedName("cuisine_name")
        @Expose
        private String cuisineName;

        public String getFavId() {
            return favId;
        }

        public void setFavId(String favId) {
            this.favId = favId;
        }

        public String getVendorKey() {
            return vendorKey;
        }

        public void setVendorKey(String vendorKey) {
            this.vendorKey = vendorKey;
        }

        public String getVendorName() {
            return vendorName;
        }

        public void setVendorName(String vendorName) {
            this.vendorName = vendorName;
        }

        public String getVendorImage() {
            return vendorImage;
        }

        public void setVendorImage(String vendorImage) {
            this.vendorImage = vendorImage;
        }

        public String getCuisineName() {
            return cuisineName;
        }

        public void setCuisineName(String cuisineName) {
            this.cuisineName = cuisineName;
        }

    }

}