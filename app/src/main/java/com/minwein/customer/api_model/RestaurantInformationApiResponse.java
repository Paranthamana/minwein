package com.minwein.customer.api_model;

/**
 * Created by Tech on 19-Jan-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantInformationApiResponse {

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
        @SerializedName("vendor_details")
        @Expose
        private VendorDetails vendorDetails;
        @SerializedName("pickup_timeslot")
        @Expose
        private List<PickupTimeslot> pickupTimeslot = null;
        @SerializedName("delivery_timeslot")
        @Expose
        private List<DeliveryTimeslot> deliveryTimeslot = null;

        public List<PickupTimeslot> getPickupTimeslot() {
            return pickupTimeslot;
        }

        public void setPickupTimeslot(List<PickupTimeslot> pickupTimeslot) {
            this.pickupTimeslot = pickupTimeslot;
        }

        public List<DeliveryTimeslot> getDeliveryTimeslot() {
            return deliveryTimeslot;
        }

        public void setDeliveryTimeslot(List<DeliveryTimeslot> deliveryTimeslot) {
            this.deliveryTimeslot = deliveryTimeslot;
        }

        public VendorDetails getVendorDetails() {
            return vendorDetails;
        }

        public void setVendorDetails(VendorDetails vendorDetails) {
            this.vendorDetails = vendorDetails;
        }

        public class VendorDetails {

            @SerializedName("vendor_id")
            @Expose
            private String vendorId;
            @SerializedName("vendor_key")
            @Expose
            private String vendorKey;
            @SerializedName("vendor_type")
            @Expose
            private String vendorType;
            @SerializedName("rating_average")
            @Expose
            private String rating_average;
            @SerializedName("vendor_image")
            @Expose
            private String vendorImage;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;
            @SerializedName("contact_email")
            @Expose
            private String contactEmail;
            @SerializedName("contact_number1")
            @Expose
            private String contactNumber1;
            @SerializedName("payment_option")
            @Expose
            private String paymentOption;
            @SerializedName("pickup_time")
            @Expose
            private String pickupTime;
            @SerializedName("delivery_time")
            @Expose
            private String deliveryTime;
            @SerializedName("delivery_fee")
            @Expose
            private String deliveryFee;
            @SerializedName("timeslot_availability")
            @Expose
            private String timeslotAvailabilityStatus;
            @SerializedName("service_tax_percentage")
            @Expose
            private String serviceTaxPercentage;
            @SerializedName("vat_percentage")
            @Expose
            private String vatPercentage;
            @SerializedName("vendor_address_line1")
            @Expose
            private String vendorAddressLine1;
            @SerializedName("vendor_description")
            @Expose
            private String vendorDescription;
            @SerializedName("vendor_name")
            @Expose
            private String vendorName;
            @SerializedName("area")
            @Expose
            private String area;
            @SerializedName("country_name")
            @Expose
            private String countryName;
            @SerializedName("city_name")
            @Expose
            private String  cityName;
            @SerializedName("cuisine_name")
            @Expose
            private String cuisineName;

            public String getVendorId() {
                return vendorId;
            }

            public void setVendorId(String vendorId) {
                this.vendorId = vendorId;
            }

            public String getVendorKey() {
                return vendorKey;
            }

            public void setVendorKey(String vendorKey) {
                this.vendorKey = vendorKey;
            }

            public String getVendorType() {
                return vendorType;
            }

            public void setVendorType(String vendorType) {
                this.vendorType = vendorType;
            }

            public String getRating_average() {
                return rating_average;
            }

            public void setRating_average(String rating_average) {
                this.rating_average = rating_average;
            }

            public String getVendorImage() {
                return vendorImage;
            }

            public void setVendorImage(String vendorImage) {
                this.vendorImage = vendorImage;
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

            public String getContactEmail() {
                return contactEmail;
            }

            public void setContactEmail(String contactEmail) {
                this.contactEmail = contactEmail;
            }

            public String getContactNumber1() {
                return contactNumber1;
            }

            public void setContactNumber1(String contactNumber1) {
                this.contactNumber1 = contactNumber1;
            }

            public String getPaymentOption() {
                return paymentOption;
            }

            public void setPaymentOption(String paymentOption) {
                this.paymentOption = paymentOption;
            }

            public String getPickupTime() {
                return pickupTime;
            }

            public void setPickupTime(String pickupTime) {
                this.pickupTime = pickupTime;
            }

            public String getDeliveryTime() {
                return deliveryTime;
            }

            public void setDeliveryTime(String deliveryTime) {
                this.deliveryTime = deliveryTime;
            }

            public String getDeliveryFee() {
                return deliveryFee;
            }

            public void setDeliveryFee(String deliveryFee) {
                this.deliveryFee = deliveryFee;
            }





            public String getServiceTaxPercentage() {
                return serviceTaxPercentage;
            }

            public void setServiceTaxPercentage(String serviceTaxPercentage) {
                this.serviceTaxPercentage = serviceTaxPercentage;
            }

            public String getVatPercentage() {
                return vatPercentage;
            }

            public void setVatPercentage(String vatPercentage) {
                this.vatPercentage = vatPercentage;
            }

            public String getVendorAddressLine1() {
                return vendorAddressLine1;
            }

            public void setVendorAddressLine1(String vendorAddressLine1) {
                this.vendorAddressLine1 = vendorAddressLine1;
            }

            public String getVendorDescription() {
                return vendorDescription;
            }

            public void setVendorDescription(String vendorDescription) {
                this.vendorDescription = vendorDescription;
            }

            public String getVendorName() {
                return vendorName;
            }

            public void setVendorName(String vendorName) {
                this.vendorName = vendorName;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public Object getCountryName() {
                return countryName;
            }

            public void setCountryName(String countryName) {
                this.countryName = countryName;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String  getCuisineName() {
                return cuisineName;
            }

            public void setCuisineName(String  cuisineName) {
                this.cuisineName = cuisineName;
            }

            public String getTimeslotAvailabilityStatus() {
                return timeslotAvailabilityStatus;
            }

            public void setTimeslotAvailabilityStatus(String timeslotAvailabilityStatus) {
                this.timeslotAvailabilityStatus = timeslotAvailabilityStatus;
            }
        }
    }
    public class DeliveryTimeslot {

        @SerializedName("vendor_timeslot_id")
        @Expose
        private String vendorTimeslotId;
        @SerializedName("vendor_timeslot_key")
        @Expose
        private String vendorTimeslotKey;
        @SerializedName("timeslot_type")
        @Expose
        private String timeslotType;
        @SerializedName("day_id")
        @Expose
        private String dayId;
        @SerializedName("timeslot_start_time")
        @Expose
        private String timeslotStartTime;
        @SerializedName("timeslot_end_time")
        @Expose
        private String timeslotEndTime;

        public String getVendorTimeslotId() {
            return vendorTimeslotId;
        }

        public void setVendorTimeslotId(String vendorTimeslotId) {
            this.vendorTimeslotId = vendorTimeslotId;
        }

        public String getVendorTimeslotKey() {
            return vendorTimeslotKey;
        }

        public void setVendorTimeslotKey(String vendorTimeslotKey) {
            this.vendorTimeslotKey = vendorTimeslotKey;
        }

        public String getTimeslotType() {
            return timeslotType;
        }

        public void setTimeslotType(String timeslotType) {
            this.timeslotType = timeslotType;
        }

        public String getDayId() {
            return dayId;
        }

        public void setDayId(String dayId) {
            this.dayId = dayId;
        }

        public String getTimeslotStartTime() {
            return timeslotStartTime;
        }

        public void setTimeslotStartTime(String timeslotStartTime) {
            this.timeslotStartTime = timeslotStartTime;
        }

        public String getTimeslotEndTime() {
            return timeslotEndTime;
        }

        public void setTimeslotEndTime(String timeslotEndTime) {
            this.timeslotEndTime = timeslotEndTime;
        }

    }
    public class PickupTimeslot {

        @SerializedName("vendor_timeslot_id")
        @Expose
        private String vendorTimeslotId;
        @SerializedName("vendor_timeslot_key")
        @Expose
        private String vendorTimeslotKey;
        @SerializedName("timeslot_type")
        @Expose
        private String timeslotType;
        @SerializedName("day_id")
        @Expose
        private String dayId;
        @SerializedName("timeslot_start_time")
        @Expose
        private String timeslotStartTime;
        @SerializedName("timeslot_end_time")
        @Expose
        private String timeslotEndTime;

        public String getVendorTimeslotId() {
            return vendorTimeslotId;
        }

        public void setVendorTimeslotId(String vendorTimeslotId) {
            this.vendorTimeslotId = vendorTimeslotId;
        }

        public String getVendorTimeslotKey() {
            return vendorTimeslotKey;
        }

        public void setVendorTimeslotKey(String vendorTimeslotKey) {
            this.vendorTimeslotKey = vendorTimeslotKey;
        }

        public String getTimeslotType() {
            return timeslotType;
        }

        public void setTimeslotType(String timeslotType) {
            this.timeslotType = timeslotType;
        }

        public String getDayId() {
            return dayId;
        }

        public void setDayId(String dayId) {
            this.dayId = dayId;
        }

        public String getTimeslotStartTime() {
            return timeslotStartTime;
        }

        public void setTimeslotStartTime(String timeslotStartTime) {
            this.timeslotStartTime = timeslotStartTime;
        }

        public String getTimeslotEndTime() {
            return timeslotEndTime;
        }

        public void setTimeslotEndTime(String timeslotEndTime) {
            this.timeslotEndTime = timeslotEndTime;
        }

    }

}
