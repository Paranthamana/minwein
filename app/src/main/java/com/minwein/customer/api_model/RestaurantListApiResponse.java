package com.minwein.customer.api_model;

/**
 * Created by Tech on 17-Jan-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantListApiResponse {

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

        @SerializedName("vendor_list")
        @Expose
        private List<Vendor_list> vendor_list = null;

        @SerializedName("cusines")
        @Expose
        private List<Cusine> cusines = null;

        public List<Vendor_list> getVendor_list() {
            return vendor_list;
        }

        public void setVendor_list(List<Vendor_list> vendor_list) {
            this.vendor_list = vendor_list;
        }

        public List<Cusine> getCusines() {
            return cusines;
        }

        public void setCusines(List<Cusine> cusines) {
            this.cusines = cusines;
        }

        public class Vendor_list {
            @SerializedName("vendor_name")
            @Expose
            private String vendor_name;
            @SerializedName("user_fav_check")
            @Expose
            private String user_fav_check;

            @SerializedName("fav_id")
            @Expose
            private String fav_id;
            @SerializedName("vendor_id")
            @Expose
            private String vendor_id;
            @SerializedName("vendor_key")
            @Expose
            private String vendor_key;
            @SerializedName("vendor_type")
            @Expose
            private String vendor_type;
            @SerializedName("rating")
            @Expose
            private String rating;
            @SerializedName("vendor_image")
            @Expose
            private String vendor_image;
            @SerializedName("food_type")
            @Expose
            private String food_type;
            @SerializedName("minimum_order")
            @Expose
            private String minimum_order;
            @SerializedName("minimum_delivery_time")
            @Expose
            private String minimum_delivery_time;
            @SerializedName("minimum_pickup_time")
            @Expose
            private String minimum_pickup_time;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;
            @SerializedName("vendor_country_id")
            @Expose
            private Object vendor_country_id;
            @SerializedName("vendor_city_id")
            @Expose
            private Object vendor_city_id;
            @SerializedName("contact_email")
            @Expose
            private String contact_email;
            @SerializedName("contact_number1")
            @Expose
            private String contact_number1;
            @SerializedName("contact_number2")
            @Expose
            private Object contact_number2;
            @SerializedName("username")
            @Expose
            private String username;
            @SerializedName("password_hash")
            @Expose
            private String password_hash;
            @SerializedName("password_reset_token")
            @Expose
            private String password_reset_token;
            @SerializedName("secret_password_hash")
            @Expose
            private String secret_password_hash;
            @SerializedName("payment_option")
            @Expose
            private String payment_option;
            @SerializedName("pickup_time")
            @Expose
            private String pickup_time;
            @SerializedName("delivery_time")
            @Expose
            private String delivery_time;
            @SerializedName("delivery_fee")
            @Expose
            private String delivery_fee;
            @SerializedName("delivery_charge_base_km")
            @Expose
            private String delivery_charge_base_km;
            @SerializedName("delivery_charge_base")
            @Expose
            private String delivery_charge_base;
            @SerializedName("delivery_charge_additional_per_km")
            @Expose
            private String delivery_charge_additional_per_km;
            @SerializedName("delivery_time_base_km")
            @Expose
            private String delivery_time_base_km;
            @SerializedName("delivery_time_base")
            @Expose
            private String delivery_time_base;
            @SerializedName("delivery_time_additional_per_km")
            @Expose
            private String delivery_time_additional_per_km;
            @SerializedName("vendor_status")
            @Expose
            private String vendor_status;
            @SerializedName("approve_status")
            @Expose
            private String approve_status;
            @SerializedName("vendor_availability_status")
            @Expose
            private String vendor_availability_status;
            @SerializedName("device_type")
            @Expose
            private String device_type;
            @SerializedName("device_token")
            @Expose
            private String device_token;
            @SerializedName("vendor_commission")
            @Expose
            private String vendor_commission;
            @SerializedName("vat_percentage")
            @Expose
            private String vat_percentage;
            @SerializedName("service_tax_percentage")
            @Expose
            private String service_tax_percentage;
            @SerializedName("created_user_id")
            @Expose
            private String created_user_id;
            @SerializedName("created_at")
            @Expose
            private String created_at;
            @SerializedName("modified_user_id")
            @Expose
            private String modified_user_id;
            @SerializedName("modified_at")
            @Expose
            private String modified_at;
            @SerializedName("vendor_delivery_area_id")
            @Expose
            private String vendor_delivery_area_id;
            @SerializedName("vendor_delivery_area_key")
            @Expose
            private String vendor_delivery_area_key;
            @SerializedName("zone_type")
            @Expose
            private String zone_type;
            @SerializedName("radius")
            @Expose
            private String radius;
            @SerializedName("delivery_area_lat_lon")
            @Expose
            private String delivery_area_lat_lon;
            @SerializedName("cuisine_name")
            @Expose
            private String cuisine_name;
            @SerializedName("rating_average")
            @Expose
            private String rating_average;
            @SerializedName("favorites_count")
            @Expose
            private Integer favorites_count;
            @SerializedName("item_count")
            @Expose
            private Integer item_count;

            public Integer getTimeslot_availability() {
                return timeslot_availability;
            }

            public void setTimeslot_availability(Integer timeslot_availability) {
                this.timeslot_availability = timeslot_availability;
            }

            @SerializedName("timeslot_availability")
            @Expose
            private Integer timeslot_availability;
            @SerializedName("item_image_path")
            @Expose
            private List<Item_image_path> item_image_path = null;

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

            public String getVendor_key() {
                return vendor_key;
            }

            public void setVendor_key(String vendor_key) {
                this.vendor_key = vendor_key;
            }

            public String getVendor_type() {
                return vendor_type;
            }

            public void setVendor_type(String vendor_type) {
                this.vendor_type = vendor_type;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getVendor_image() {
                return vendor_image;
            }

            public void setVendor_image(String vendor_image) {
                this.vendor_image = vendor_image;
            }

            public String getFood_type() {
                return food_type;
            }

            public void setFood_type(String food_type) {
                this.food_type = food_type;
            }

            public String getMinimum_order() {
                return minimum_order;
            }

            public void setMinimum_order(String minimum_order) {
                this.minimum_order = minimum_order;
            }

            public String getMinimum_delivery_time() {
                return minimum_delivery_time;
            }

            public void setMinimum_delivery_time(String minimum_delivery_time) {
                this.minimum_delivery_time = minimum_delivery_time;
            }

            public String getMinimum_pickup_time() {
                return minimum_pickup_time;
            }

            public void setMinimum_pickup_time(String minimum_pickup_time) {
                this.minimum_pickup_time = minimum_pickup_time;
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

            public Object getVendor_country_id() {
                return vendor_country_id;
            }

            public void setVendor_country_id(Object vendor_country_id) {
                this.vendor_country_id = vendor_country_id;
            }

            public Object getVendor_city_id() {
                return vendor_city_id;
            }

            public void setVendor_city_id(Object vendor_city_id) {
                this.vendor_city_id = vendor_city_id;
            }

            public String getContact_email() {
                return contact_email;
            }

            public void setContact_email(String contact_email) {
                this.contact_email = contact_email;
            }

            public String getContact_number1() {
                return contact_number1;
            }

            public void setContact_number1(String contact_number1) {
                this.contact_number1 = contact_number1;
            }

            public Object getContact_number2() {
                return contact_number2;
            }

            public void setContact_number2(Object contact_number2) {
                this.contact_number2 = contact_number2;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword_hash() {
                return password_hash;
            }

            public void setPassword_hash(String password_hash) {
                this.password_hash = password_hash;
            }

            public String getPassword_reset_token() {
                return password_reset_token;
            }

            public void setPassword_reset_token(String password_reset_token) {
                this.password_reset_token = password_reset_token;
            }

            public String getSecret_password_hash() {
                return secret_password_hash;
            }

            public void setSecret_password_hash(String secret_password_hash) {
                this.secret_password_hash = secret_password_hash;
            }

            public String getPayment_option() {
                return payment_option;
            }

            public void setPayment_option(String payment_option) {
                this.payment_option = payment_option;
            }

            public String getPickup_time() {
                return pickup_time;
            }

            public void setPickup_time(String pickup_time) {
                this.pickup_time = pickup_time;
            }

            public String getDelivery_time() {
                return delivery_time;
            }

            public void setDelivery_time(String delivery_time) {
                this.delivery_time = delivery_time;
            }

            public String getDelivery_fee() {
                return delivery_fee;
            }

            public void setDelivery_fee(String delivery_fee) {
                this.delivery_fee = delivery_fee;
            }

            public String getDelivery_charge_base_km() {
                return delivery_charge_base_km;
            }

            public void setDelivery_charge_base_km(String delivery_charge_base_km) {
                this.delivery_charge_base_km = delivery_charge_base_km;
            }

            public String getDelivery_charge_base() {
                return delivery_charge_base;
            }

            public void setDelivery_charge_base(String delivery_charge_base) {
                this.delivery_charge_base = delivery_charge_base;
            }

            public String getDelivery_charge_additional_per_km() {
                return delivery_charge_additional_per_km;
            }

            public void setDelivery_charge_additional_per_km(String delivery_charge_additional_per_km) {
                this.delivery_charge_additional_per_km = delivery_charge_additional_per_km;
            }

            public String getDelivery_time_base_km() {
                return delivery_time_base_km;
            }

            public void setDelivery_time_base_km(String delivery_time_base_km) {
                this.delivery_time_base_km = delivery_time_base_km;
            }

            public String getDelivery_time_base() {
                return delivery_time_base;
            }

            public void setDelivery_time_base(String delivery_time_base) {
                this.delivery_time_base = delivery_time_base;
            }

            public String getDelivery_time_additional_per_km() {
                return delivery_time_additional_per_km;
            }

            public void setDelivery_time_additional_per_km(String delivery_time_additional_per_km) {
                this.delivery_time_additional_per_km = delivery_time_additional_per_km;
            }

            public String getVendor_status() {
                return vendor_status;
            }

            public void setVendor_status(String vendor_status) {
                this.vendor_status = vendor_status;
            }

            public String getApprove_status() {
                return approve_status;
            }

            public void setApprove_status(String approve_status) {
                this.approve_status = approve_status;
            }

            public String getVendor_availability_status() {
                return vendor_availability_status;
            }

            public void setVendor_availability_status(String vendor_availability_status) {
                this.vendor_availability_status = vendor_availability_status;
            }

            public String getDevice_type() {
                return device_type;
            }

            public void setDevice_type(String device_type) {
                this.device_type = device_type;
            }

            public String getDevice_token() {
                return device_token;
            }

            public void setDevice_token(String device_token) {
                this.device_token = device_token;
            }

            public String getVendor_commission() {
                return vendor_commission;
            }

            public void setVendor_commission(String vendor_commission) {
                this.vendor_commission = vendor_commission;
            }

            public String getVat_percentage() {
                return vat_percentage;
            }

            public void setVat_percentage(String vat_percentage) {
                this.vat_percentage = vat_percentage;
            }

            public String getService_tax_percentage() {
                return service_tax_percentage;
            }

            public void setService_tax_percentage(String service_tax_percentage) {
                this.service_tax_percentage = service_tax_percentage;
            }

            public String getCreated_user_id() {
                return created_user_id;
            }

            public void setCreated_user_id(String created_user_id) {
                this.created_user_id = created_user_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getModified_user_id() {
                return modified_user_id;
            }

            public void setModified_user_id(String modified_user_id) {
                this.modified_user_id = modified_user_id;
            }

            public String getModified_at() {
                return modified_at;
            }

            public void setModified_at(String modified_at) {
                this.modified_at = modified_at;
            }

            public String getVendor_delivery_area_id() {
                return vendor_delivery_area_id;
            }

            public void setVendor_delivery_area_id(String vendor_delivery_area_id) {
                this.vendor_delivery_area_id = vendor_delivery_area_id;
            }

            public String getVendor_delivery_area_key() {
                return vendor_delivery_area_key;
            }

            public void setVendor_delivery_area_key(String vendor_delivery_area_key) {
                this.vendor_delivery_area_key = vendor_delivery_area_key;
            }

            public String getZone_type() {
                return zone_type;
            }

            public void setZone_type(String zone_type) {
                this.zone_type = zone_type;
            }

            public String getRadius() {
                return radius;
            }

            public void setRadius(String radius) {
                this.radius = radius;
            }

            public String getDelivery_area_lat_lon() {
                return delivery_area_lat_lon;
            }

            public void setDelivery_area_lat_lon(String delivery_area_lat_lon) {
                this.delivery_area_lat_lon = delivery_area_lat_lon;
            }

            public String getCuisine_name() {
                return cuisine_name;
            }

            public void setCuisine_name(String cuisine_name) {
                this.cuisine_name = cuisine_name;
            }

            public String getRating_average() {
                return rating_average;
            }

            public void setRating_average(String rating_average) {
                this.rating_average = rating_average;
            }

            public Integer getFavorites_count() {
                return favorites_count;
            }

            public void setFavorites_count(Integer favorites_count) {
                this.favorites_count = favorites_count;
            }

            public Integer getItem_count() {
                return item_count;
            }

            public void setItem_count(Integer item_count) {
                this.item_count = item_count;
            }

            public List<Item_image_path> getItem_image_path() {
                return item_image_path;
            }

            public void setItem_image_path(List<Item_image_path> item_image_path) {
                this.item_image_path = item_image_path;
            }

            public String getFav_id() {
                return fav_id;
            }

            public void setFav_id(String fav_id) {
                this.fav_id = fav_id;
            }

            public String getUser_fav_check() {
                return user_fav_check;
            }

            public void setUser_fav_check(String user_fav_check) {
                this.user_fav_check = user_fav_check;
            }

        }
        public class Item_image_path {

            @SerializedName("item_key")
            @Expose
            private String item_key;
            @SerializedName("item_image_path")
            @Expose
            private String item_image_path;

            public String getItem_key() {
                return item_key;
            }

            public void setItem_key(String item_key) {
                this.item_key = item_key;
            }

            public String getItem_image_path() {
                return item_image_path;
            }

            public void setItem_image_path(String item_image_path) {
                this.item_image_path = item_image_path;
            }

        }

        public class Cusine {

            @SerializedName("cuisine_lang_id")
            @Expose
            private String cuisine_lang_id;
            @SerializedName("cuisine_id")
            @Expose
            private String cuisine_id;
            @SerializedName("language_code")
            @Expose
            private String language_code;
            @SerializedName("cuisine_name")
            @Expose
            private String cuisine_name;
            @SerializedName("cuisine_key")
            @Expose
            private String cuisine_key;
            @SerializedName("cuisine_status")
            @Expose
            private String cuisine_status;
            @SerializedName("created_user_id")
            @Expose
            private String created_user_id;
            @SerializedName("created_at")
            @Expose
            private String created_at;
            @SerializedName("modified_user_id")
            @Expose
            private Object modified_user_id;
            @SerializedName("modified_at")
            @Expose
            private Object modified_at;

            public String getCuisine_lang_id() {
                return cuisine_lang_id;
            }

            public void setCuisine_lang_id(String cuisine_lang_id) {
                this.cuisine_lang_id = cuisine_lang_id;
            }

            public String getCuisine_id() {
                return cuisine_id;
            }

            public void setCuisine_id(String cuisine_id) {
                this.cuisine_id = cuisine_id;
            }

            public String getLanguage_code() {
                return language_code;
            }

            public void setLanguage_code(String language_code) {
                this.language_code = language_code;
            }

            public String getCuisine_name() {
                return cuisine_name;
            }

            public void setCuisine_name(String cuisine_name) {
                this.cuisine_name = cuisine_name;
            }

            public String getCuisine_key() {
                return cuisine_key;
            }

            public void setCuisine_key(String cuisine_key) {
                this.cuisine_key = cuisine_key;
            }

            public String getCuisine_status() {
                return cuisine_status;
            }

            public void setCuisine_status(String cuisine_status) {
                this.cuisine_status = cuisine_status;
            }

            public String getCreated_user_id() {
                return created_user_id;
            }

            public void setCreated_user_id(String created_user_id) {
                this.created_user_id = created_user_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public Object getModified_user_id() {
                return modified_user_id;
            }

            public void setModified_user_id(Object modified_user_id) {
                this.modified_user_id = modified_user_id;
            }

            public Object getModified_at() {
                return modified_at;
            }

            public void setModified_at(Object modified_at) {
                this.modified_at = modified_at;
            }

        }

    }
}