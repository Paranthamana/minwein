package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tech on 12-Jan-2018.
 */

public class RatingAndReviewApiResponse {
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

        @SerializedName("restaurant")
        @Expose
        private List<Restaurant> restaurant = null;
        @SerializedName("food_truck")
        @Expose
        private List<Object> food_truck = null;
        @SerializedName("food")
        @Expose
        private List<Food> food = null;

        public List<Restaurant> getRestaurant() {
            return restaurant;
        }

        public void setRestaurant(List<Restaurant> restaurant) {
            this.restaurant = restaurant;
        }

        public List<Object> getFood_truck() {
            return food_truck;
        }

        public void setFood_truck(List<Object> food_truck) {
            this.food_truck = food_truck;
        }

        public List<Food> getFood() {
            return food;
        }

        public void setFood(List<Food> food) {
            this.food = food;
        }

    }
    public class Food {

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
        @SerializedName("vendor_lang_id")
        @Expose
        private String vendor_lang_id;
        @SerializedName("language_code")
        @Expose
        private String language_code;
        @SerializedName("vendor_name")
        @Expose
        private String vendor_name;
        @SerializedName("vendor_description")
        @Expose
        private String vendor_description;
        @SerializedName("owner_name")
        @Expose
        private Object owner_name;
        @SerializedName("vendor_address_line1")
        @Expose
        private String vendor_address_line1;
        @SerializedName("vendor_address_line2")
        @Expose
        private String vendor_address_line2;
        @SerializedName("area")
        @Expose
        private String area;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("item_image_id")
        @Expose
        private String item_image_id;
        @SerializedName("item_id")
        @Expose
        private String item_id;
        @SerializedName("item_image_path")
        @Expose
        private String item_image_path;
        @SerializedName("sort_no")
        @Expose
        private Object sort_no;
        @SerializedName("item_lang_id")
        @Expose
        private String item_lang_id;
        @SerializedName("item_name")
        @Expose
        private String item_name;
        @SerializedName("item_description")
        @Expose
        private String item_description;
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
        @SerializedName("rating_Taste")
        @Expose
        private String rating_Taste;
        @SerializedName("rating_Timing")
        @Expose
        private String rating_Timing;
        @SerializedName("rating_Price")
        @Expose
        private String rating_Price;
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

        public String getVendor_lang_id() {
            return vendor_lang_id;
        }

        public void setVendor_lang_id(String vendor_lang_id) {
            this.vendor_lang_id = vendor_lang_id;
        }

        public String getLanguage_code() {
            return language_code;
        }

        public void setLanguage_code(String language_code) {
            this.language_code = language_code;
        }

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public String getVendor_description() {
            return vendor_description;
        }

        public void setVendor_description(String vendor_description) {
            this.vendor_description = vendor_description;
        }

        public Object getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(Object owner_name) {
            this.owner_name = owner_name;
        }

        public String getVendor_address_line1() {
            return vendor_address_line1;
        }

        public void setVendor_address_line1(String vendor_address_line1) {
            this.vendor_address_line1 = vendor_address_line1;
        }

        public String getVendor_address_line2() {
            return vendor_address_line2;
        }

        public void setVendor_address_line2(String vendor_address_line2) {
            this.vendor_address_line2 = vendor_address_line2;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getItem_image_id() {
            return item_image_id;
        }

        public void setItem_image_id(String item_image_id) {
            this.item_image_id = item_image_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_image_path() {
            return item_image_path;
        }

        public void setItem_image_path(String item_image_path) {
            this.item_image_path = item_image_path;
        }

        public Object getSort_no() {
            return sort_no;
        }

        public void setSort_no(Object sort_no) {
            this.sort_no = sort_no;
        }

        public String getItem_lang_id() {
            return item_lang_id;
        }

        public void setItem_lang_id(String item_lang_id) {
            this.item_lang_id = item_lang_id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_description() {
            return item_description;
        }

        public void setItem_description(String item_description) {
            this.item_description = item_description;
        }

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

        public String getRating_Taste() {
            return rating_Taste;
        }

        public void setRating_Taste(String rating_Taste) {
            this.rating_Taste = rating_Taste;
        }

        public String getRating_Timing() {
            return rating_Timing;
        }

        public void setRating_Timing(String rating_Timing) {
            this.rating_Timing = rating_Timing;
        }

        public String getRating_Price() {
            return rating_Price;
        }

        public void setRating_Price(String rating_Price) {
            this.rating_Price = rating_Price;
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

    }

    public class Restaurant {

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
        @SerializedName("contact_email")
        @Expose
        private String contact_email;
        @SerializedName("contact_number1")
        @Expose
        private String contact_number1;
        @SerializedName("contact_number2")
        @Expose
        private Object contact_number2;
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
        @SerializedName("vendor_status")
        @Expose
        private String vendor_status;
        @SerializedName("approve_status")
        @Expose
        private String approve_status;
        @SerializedName("vendor_availability_status")
        @Expose
        private String vendor_availability_status;
        @SerializedName("vendor_commission")
        @Expose
        private String vendor_commission;
        @SerializedName("vat_percentage")
        @Expose
        private String vat_percentage;
        @SerializedName("service_tax_percentage")
        @Expose
        private String service_tax_percentage;
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
        @SerializedName("vendor_lang_id")
        @Expose
        private String vendor_lang_id;
        @SerializedName("language_code")
        @Expose
        private String language_code;
        @SerializedName("vendor_name")
        @Expose
        private String vendor_name;
        @SerializedName("vendor_description")
        @Expose
        private String vendor_description;
        @SerializedName("owner_name")
        @Expose
        private Object owner_name;
        @SerializedName("vendor_address_line1")
        @Expose
        private String vendor_address_line1;
        @SerializedName("vendor_address_line2")
        @Expose
        private String vendor_address_line2;
        @SerializedName("area")
        @Expose
        private String area;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country")
        @Expose
        private String country;

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

        public String getVendor_lang_id() {
            return vendor_lang_id;
        }

        public void setVendor_lang_id(String vendor_lang_id) {
            this.vendor_lang_id = vendor_lang_id;
        }

        public String getLanguage_code() {
            return language_code;
        }

        public void setLanguage_code(String language_code) {
            this.language_code = language_code;
        }

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public String getVendor_description() {
            return vendor_description;
        }

        public void setVendor_description(String vendor_description) {
            this.vendor_description = vendor_description;
        }

        public Object getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(Object owner_name) {
            this.owner_name = owner_name;
        }

        public String getVendor_address_line1() {
            return vendor_address_line1;
        }

        public void setVendor_address_line1(String vendor_address_line1) {
            this.vendor_address_line1 = vendor_address_line1;
        }

        public String getVendor_address_line2() {
            return vendor_address_line2;
        }

        public void setVendor_address_line2(String vendor_address_line2) {
            this.vendor_address_line2 = vendor_address_line2;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }
}
