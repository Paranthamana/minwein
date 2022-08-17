package com.minwein.customer.api_model;

/**
 * Created by Tech on 11-Jan-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultAddressApiResponse {

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

        @SerializedName("default_address")
        @Expose
        private Default_address default_address;

        public Default_address getDefault_address() {
            return default_address;
        }

        public void setDefault_address(Default_address default_address) {
            this.default_address = default_address;
        }

    }
    public class Default_address {

        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("user_address_key")
        @Expose
        private String user_address_key;
        @SerializedName("addtype")
        @Expose
        private String addtype;
        @SerializedName("address_line1")
        @Expose
        private String address_line1;
        @SerializedName("address_line2")
        @Expose
        private String address_line2;
        @SerializedName("land_mark")
        @Expose
        private String land_mark;
        @SerializedName("company_name")
        @Expose
        private String company_name;
        @SerializedName("default_add")
        @Expose
        private Integer default_add;

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getUser_address_key() {
            return user_address_key;
        }

        public void setUser_address_key(String user_address_key) {
            this.user_address_key = user_address_key;
        }

        public String getAddtype() {
            return addtype;
        }

        public void setAddtype(String addtype) {
            this.addtype = addtype;
        }

        public String getAddress_line1() {
            return address_line1;
        }

        public void setAddress_line1(String address_line1) {
            this.address_line1 = address_line1;
        }

        public String getAddress_line2() {
            return address_line2;
        }

        public void setAddress_line2(String address_line2) {
            this.address_line2 = address_line2;
        }

        public String getLand_mark() {
            return land_mark;
        }

        public void setLand_mark(String land_mark) {
            this.land_mark = land_mark;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public Integer getDefault_add() {
            return default_add;
        }

        public void setDefault_add(Integer default_add) {
            this.default_add = default_add;
        }

    }
    }
