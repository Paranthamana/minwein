package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsApiResponse {

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

        @SerializedName("payment_details")
        @Expose
        private List<Payment_detail> payment_details = null;
        @SerializedName("order_details")
        @Expose
        private Order_details order_details;
        @SerializedName("venor_details")
        @Expose
        private Venor_details venor_details;

        public List<Payment_detail> getPayment_details() {
            return payment_details;
        }

        public void setPayment_details(List<Payment_detail> payment_details) {
            this.payment_details = payment_details;
        }

        public Order_details getOrder_details() {
            return order_details;
        }

        public void setOrder_details(Order_details order_details) {
            this.order_details = order_details;
        }

        public Venor_details getVenor_details() {
            return venor_details;
        }

        public void setVenor_details(Venor_details venor_details) {
            this.venor_details = venor_details;
        }

    }

    public class Payment_detail {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private Double value;
        @SerializedName("color")
        @Expose
        private Boolean color;
        @SerializedName("service_tax_percentage")
        @Expose
        private String service_tax_percentage;
        @SerializedName("vat_percentage")
        @Expose
        private String vat_percentage;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public Boolean getColor() {
            return color;
        }

        public void setColor(Boolean color) {
            this.color = color;
        }

        public String getService_tax_percentage() {
            return service_tax_percentage;
        }

        public void setService_tax_percentage(String service_tax_percentage) {
            this.service_tax_percentage = service_tax_percentage;
        }

        public String getVat_percentage() {
            return vat_percentage;
        }

        public void setVat_percentage(String vat_percentage) {
            this.vat_percentage = vat_percentage;
        }

    }

    public class Order_details {

        @SerializedName("order_key")
        @Expose
        private String order_key;
        @SerializedName("order_number")
        @Expose
        private String order_number;
        @SerializedName("vendor_id")
        @Expose
        private Integer vendor_id;
        @SerializedName("user_id")
        @Expose
        private Integer user_id;
        @SerializedName("mobile_number")
        @Expose
        private long mobile_number;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("order_date_time")
        @Expose
        private String order_date_time;
        @SerializedName("order_type")
        @Expose
        private Integer order_type;
        @SerializedName("pickup_delivery_date_time")
        @Expose
        private String pickup_delivery_date_time;
        @SerializedName("item_total")
        @Expose
        private Double item_total;
        @SerializedName("delivery_fee")
        @Expose
        private Double delivery_fee;
        @SerializedName("package_price")
        @Expose
        private Integer package_price;
        @SerializedName("delivery_option")
        @Expose
        private Integer delivery_option;
        @SerializedName("tax")
        @Expose
        private Double tax;
        @SerializedName("vat")
        @Expose
        private Double vat;
        @SerializedName("coupon_offer_value")
        @Expose
        private Integer coupon_offer_value;
        @SerializedName("conversion_loyalty_points_count")
        @Expose
        private Integer conversion_loyalty_points_count;
        @SerializedName("conversion_loyalty_points_currency")
        @Expose
        private Integer conversion_loyalty_points_currency;
        @SerializedName("loyalty_points_in_user_account")
        @Expose
        private Integer loyalty_points_in_user_account;
        @SerializedName("loyalty_points_used")
        @Expose
        private Integer loyalty_points_used;
        @SerializedName("loyalty_points_value")
        @Expose
        private Integer loyalty_points_value;
        @SerializedName("loyalty_points_earned_for_order")
        @Expose
        private Integer loyalty_points_earned_for_order;
        @SerializedName("order_total")
        @Expose
        private Double order_total;
        @SerializedName("payment_option")
        @Expose
        private Integer payment_option;
        @SerializedName("payment_status")
        @Expose
        private Integer payment_status;
        @SerializedName("order_status")
        @Expose
        private Integer order_status;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("user_first_name")
        @Expose
        private String user_first_name;
        @SerializedName("user_last_name")
        @Expose
        private String user_last_name;
        @SerializedName("user_address")
        @Expose
        private User_address user_address;
        @SerializedName("items")
        @Expose
        private List<Item> items = null;
        @SerializedName("offers")
        @Expose
        private List<Object> offers = null;
        @SerializedName("coupons")
        @Expose
        private List<Object> coupons = null;

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

        public Integer getVendor_id() {
            return vendor_id;
        }

        public void setVendor_id(Integer vendor_id) {
            this.vendor_id = vendor_id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public long getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(long mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getOrder_date_time() {
            return order_date_time;
        }

        public void setOrder_date_time(String order_date_time) {
            this.order_date_time = order_date_time;
        }

        public Integer getOrder_type() {
            return order_type;
        }

        public void setOrder_type(Integer order_type) {
            this.order_type = order_type;
        }

        public String getPickup_delivery_date_time() {
            return pickup_delivery_date_time;
        }

        public void setPickup_delivery_date_time(String pickup_delivery_date_time) {
            this.pickup_delivery_date_time = pickup_delivery_date_time;
        }

        public Double getItem_total() {
            return item_total;
        }

        public void setItem_total(Double item_total) {
            this.item_total = item_total;
        }

        public Double getDelivery_fee() {
            return delivery_fee;
        }

        public void setDelivery_fee(Double delivery_fee) {
            this.delivery_fee = delivery_fee;
        }

        public Integer getPackage_price() {
            return package_price;
        }

        public void setPackage_price(Integer package_price) {
            this.package_price = package_price;
        }

        public Integer getDelivery_option() {
            return delivery_option;
        }

        public void setDelivery_option(Integer delivery_option) {
            this.delivery_option = delivery_option;
        }

        public Double getTax() {
            return tax;
        }

        public void setTax(Double tax) {
            this.tax = tax;
        }

        public Double getVat() {
            return vat;
        }

        public void setVat(Double vat) {
            this.vat = vat;
        }

        public Integer getCoupon_offer_value() {
            return coupon_offer_value;
        }

        public void setCoupon_offer_value(Integer coupon_offer_value) {
            this.coupon_offer_value = coupon_offer_value;
        }

        public Integer getConversion_loyalty_points_count() {
            return conversion_loyalty_points_count;
        }

        public void setConversion_loyalty_points_count(Integer conversion_loyalty_points_count) {
            this.conversion_loyalty_points_count = conversion_loyalty_points_count;
        }

        public Integer getConversion_loyalty_points_currency() {
            return conversion_loyalty_points_currency;
        }

        public void setConversion_loyalty_points_currency(Integer conversion_loyalty_points_currency) {
            this.conversion_loyalty_points_currency = conversion_loyalty_points_currency;
        }

        public Integer getLoyalty_points_in_user_account() {
            return loyalty_points_in_user_account;
        }

        public void setLoyalty_points_in_user_account(Integer loyalty_points_in_user_account) {
            this.loyalty_points_in_user_account = loyalty_points_in_user_account;
        }

        public Integer getLoyalty_points_used() {
            return loyalty_points_used;
        }

        public void setLoyalty_points_used(Integer loyalty_points_used) {
            this.loyalty_points_used = loyalty_points_used;
        }

        public Integer getLoyalty_points_value() {
            return loyalty_points_value;
        }

        public void setLoyalty_points_value(Integer loyalty_points_value) {
            this.loyalty_points_value = loyalty_points_value;
        }

        public Integer getLoyalty_points_earned_for_order() {
            return loyalty_points_earned_for_order;
        }

        public void setLoyalty_points_earned_for_order(Integer loyalty_points_earned_for_order) {
            this.loyalty_points_earned_for_order = loyalty_points_earned_for_order;
        }

        public Double getOrder_total() {
            return order_total;
        }

        public void setOrder_total(Double order_total) {
            this.order_total = order_total;
        }

        public Integer getPayment_option() {
            return payment_option;
        }

        public void setPayment_option(Integer payment_option) {
            this.payment_option = payment_option;
        }

        public Integer getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(Integer payment_status) {
            this.payment_status = payment_status;
        }

        public Integer getOrder_status() {
            return order_status;
        }

        public void setOrder_status(Integer order_status) {
            this.order_status = order_status;
        }

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

        public String getUser_first_name() {
            return user_first_name;
        }

        public void setUser_first_name(String user_first_name) {
            this.user_first_name = user_first_name;
        }

        public String getUser_last_name() {
            return user_last_name;
        }

        public void setUser_last_name(String user_last_name) {
            this.user_last_name = user_last_name;
        }

        public User_address getUser_address() {
            return user_address;
        }

        public void setUser_address(User_address user_address) {
            this.user_address = user_address;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public List<Object> getOffers() {
            return offers;
        }

        public void setOffers(List<Object> offers) {
            this.offers = offers;
        }

        public List<Object> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<Object> coupons) {
            this.coupons = coupons;
        }

    }

    public class Item {

        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("item_id")
        @Expose
        private Integer item_id;
        @SerializedName("item_price")
        @Expose
        private Integer item_price;
        @SerializedName("total_price")
        @Expose
        private Integer total_price;
        @SerializedName("item_key")
        @Expose
        private String item_key;
        @SerializedName("item_name")
        @Expose
        private String item_name;
        @SerializedName("item_description")
        @Expose
        private String item_description;
        @SerializedName("ingredients_group")
        @Expose
        private List<Ingredients_group> ingredients_group = null;
        @SerializedName("item_images")
        @Expose
        private List<Item_image> item_images = null;
        @SerializedName("ingredients_name")
        @Expose
        private String ingredients_name;
        @SerializedName("name_modifiers")
        @Expose
        private Object name_modifiers;
        @SerializedName("name_ingredients")
        @Expose
        private String name_ingredients;

        transient Integer random_key;

        public Integer getRandom_key() {
            return random_key;
        }

        public void setRandom_key(Integer random_key) {
            this.random_key = random_key;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getItem_id() {
            return item_id;
        }

        public void setItem_id(Integer item_id) {
            this.item_id = item_id;
        }

        public Integer getItem_price() {
            return item_price;
        }

        public void setItem_price(Integer item_price) {
            this.item_price = item_price;
        }

        public Integer getTotal_price() {
            return total_price;
        }

        public void setTotal_price(Integer total_price) {
            this.total_price = total_price;
        }

        public String getItem_key() {
            return item_key;
        }

        public void setItem_key(String item_key) {
            this.item_key = item_key;
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

        public List<Ingredients_group> getIngredients_group() {
            return ingredients_group;
        }

        public void setIngredients_group(List<Ingredients_group> ingredients_group) {
            this.ingredients_group = ingredients_group;
        }

        public List<Item_image> getItem_images() {
            return item_images;
        }

        public void setItem_images(List<Item_image> item_images) {
            this.item_images = item_images;
        }

        public String getIngredients_name() {
            return ingredients_name;
        }

        public void setIngredients_name(String ingredients_name) {
            this.ingredients_name = ingredients_name;
        }

        public Object getName_modifiers() {
            return name_modifiers;
        }

        public void setName_modifiers(Object name_modifiers) {
            this.name_modifiers = name_modifiers;
        }

        public String getName_ingredients() {
            return name_ingredients;
        }

        public void setName_ingredients(String name_ingredients) {
            this.name_ingredients = name_ingredients;
        }

    }

    public class Ingredients_group {

        @SerializedName("ingredient_type")
        @Expose
        private Integer ingredient_type;
        @SerializedName("order_item_ingredient_group_id")
        @Expose
        private Integer order_item_ingredient_group_id;
        @SerializedName("item_ingredient_group_key")
        @Expose
        private String item_ingredient_group_key;
        @SerializedName("ingredients")
        @Expose
        private List<Ingredient> ingredients = null;

        public Integer getIngredient_type() {
            return ingredient_type;
        }

        public void setIngredient_type(Integer ingredient_type) {
            this.ingredient_type = ingredient_type;
        }

        public Integer getOrder_item_ingredient_group_id() {
            return order_item_ingredient_group_id;
        }

        public void setOrder_item_ingredient_group_id(Integer order_item_ingredient_group_id) {
            this.order_item_ingredient_group_id = order_item_ingredient_group_id;
        }

        public String getItem_ingredient_group_key() {
            return item_ingredient_group_key;
        }

        public void setItem_ingredient_group_key(String item_ingredient_group_key) {
            this.item_ingredient_group_key = item_ingredient_group_key;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

    }

    public class Item_image {

        @SerializedName("order_item_image_id")
        @Expose
        private Integer order_item_image_id;
        @SerializedName("order_item_id")
        @Expose
        private Integer order_item_id;
        @SerializedName("language_code")
        @Expose
        private String language_code;
        @SerializedName("item_image_path")
        @Expose
        private String item_image_path;

        public Integer getOrder_item_image_id() {
            return order_item_image_id;
        }

        public void setOrder_item_image_id(Integer order_item_image_id) {
            this.order_item_image_id = order_item_image_id;
        }

        public Integer getOrder_item_id() {
            return order_item_id;
        }

        public void setOrder_item_id(Integer order_item_id) {
            this.order_item_id = order_item_id;
        }

        public String getLanguage_code() {
            return language_code;
        }

        public void setLanguage_code(String language_code) {
            this.language_code = language_code;
        }

        public String getItem_image_path() {
            return item_image_path;
        }

        public void setItem_image_path(String item_image_path) {
            this.item_image_path = item_image_path;
        }

    }

    public class Ingredient {

        @SerializedName("order_item_ingredient_group_id")
        @Expose
        private Integer order_item_ingredient_group_id;
        @SerializedName("ingredient_id")
        @Expose
        private Integer ingredient_id;
        @SerializedName("ingredient_key")
        @Expose
        private String ingredient_key;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("ingredients")
        @Expose
        private List<Ingredient_> ingredients = null;

        public Integer getOrder_item_ingredient_group_id() {
            return order_item_ingredient_group_id;
        }

        public void setOrder_item_ingredient_group_id(Integer order_item_ingredient_group_id) {
            this.order_item_ingredient_group_id = order_item_ingredient_group_id;
        }

        public Integer getIngredient_id() {
            return ingredient_id;
        }

        public void setIngredient_id(Integer ingredient_id) {
            this.ingredient_id = ingredient_id;
        }

        public String getIngredient_key() {
            return ingredient_key;
        }

        public void setIngredient_key(String ingredient_key) {
            this.ingredient_key = ingredient_key;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public List<Ingredient_> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient_> ingredients) {
            this.ingredients = ingredients;
        }

    }

    public class Ingredient_ {

        @SerializedName("order_item_ingredient_mapping_lang_id")
        @Expose
        private Integer order_item_ingredient_mapping_lang_id;
        @SerializedName("order_item_ingredient_mapping_id")
        @Expose
        private Integer order_item_ingredient_mapping_id;
        @SerializedName("language_code")
        @Expose
        private String language_code;
        @SerializedName("ingredient_name")
        @Expose
        private String ingredient_name;
        @SerializedName("ingredient_description")
        @Expose
        private String ingredient_description;
        @SerializedName("ingredient_image")
        @Expose
        private String ingredient_image;

        public Integer getOrder_item_ingredient_mapping_lang_id() {
            return order_item_ingredient_mapping_lang_id;
        }

        public void setOrder_item_ingredient_mapping_lang_id(Integer order_item_ingredient_mapping_lang_id) {
            this.order_item_ingredient_mapping_lang_id = order_item_ingredient_mapping_lang_id;
        }

        public Integer getOrder_item_ingredient_mapping_id() {
            return order_item_ingredient_mapping_id;
        }

        public void setOrder_item_ingredient_mapping_id(Integer order_item_ingredient_mapping_id) {
            this.order_item_ingredient_mapping_id = order_item_ingredient_mapping_id;
        }

        public String getLanguage_code() {
            return language_code;
        }

        public void setLanguage_code(String language_code) {
            this.language_code = language_code;
        }

        public String getIngredient_name() {
            return ingredient_name;
        }

        public void setIngredient_name(String ingredient_name) {
            this.ingredient_name = ingredient_name;
        }

        public String getIngredient_description() {
            return ingredient_description;
        }

        public void setIngredient_description(String ingredient_description) {
            this.ingredient_description = ingredient_description;
        }

        public String getIngredient_image() {
            return ingredient_image;
        }

        public void setIngredient_image(String ingredient_image) {
            this.ingredient_image = ingredient_image;
        }

    }

    public class Venor_details {

        @SerializedName("vendor_key")
        @Expose
        private String vendor_key;
        @SerializedName("vat_percentage")
        @Expose
        private String vat_percentage;
        @SerializedName("service_tax_percentage")
        @Expose
        private String service_tax_percentage;
        @SerializedName("vendor_name")
        @Expose
        private String vendor_name;

        public String getVendor_key() {
            return vendor_key;
        }

        public void setVendor_key(String vendor_key) {
            this.vendor_key = vendor_key;
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

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

    }

    public class User_address {

        @SerializedName("user_address_id")
        @Expose
        private Integer user_address_id;
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

        public Integer getUser_address_id() {
            return user_address_id;
        }

        public void setUser_address_id(Integer user_address_id) {
            this.user_address_id = user_address_id;
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

    }
}