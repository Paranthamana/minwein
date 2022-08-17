package com.minwein.customer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class DeliveryOption {

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

        @SerializedName("vendor_key")
        @Expose
        private String vendor_key = "";

    @SerializedName("isDetailFilled")
    @Expose
    private Boolean isDetailFilled = false;

    public String getVendor_key() {
        return vendor_key;
    }

    public void setVendor_key(String vendor_key) {
        this.vendor_key = vendor_key;
    }

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
    public Boolean getDetailFilled() {
        return isDetailFilled;
    }

    public void setDetailFilled(Boolean detailFilled) {
        isDetailFilled = detailFilled;
    }

    }