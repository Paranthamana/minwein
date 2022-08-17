package com.minwein.customer.api;


import com.minwein.customer.api_model.AddFavoriteApiResponse;
import com.minwein.customer.api_model.AddNewAddressApiResponse;
import com.minwein.customer.api_model.AddRestaurantRatingApiResponse;
import com.minwein.customer.api_model.AddressListApiResponse;
import com.minwein.customer.api_model.CartVerifyApiResponse;
import com.minwein.customer.api_model.ContactUsApiResponse;
import com.minwein.customer.api_model.DefaultAddressApiResponse;
import com.minwein.customer.api_model.DeleteAddressApiResponse;
import com.minwein.customer.api_model.DeleteFavoritesApiResponse;
import com.minwein.customer.api_model.EditProfileApiResponse;
import com.minwein.customer.api_model.FaqApiResponse;
import com.minwein.customer.api_model.FavoritesApiResponse;
import com.minwein.customer.api_model.ForgetPasswordApiResponse;
import com.minwein.customer.api_model.ItemDetailsResponse;
import com.minwein.customer.api_model.LoginApiResponse;
import com.minwein.customer.api_model.LoyaltyPointShareApiResponse;
import com.minwein.customer.api_model.LoyaltyPointsApiResponse;
import com.minwein.customer.api_model.OrderApiResponse;
import com.minwein.customer.api_model.OrderConfirmedApiResponse;
import com.minwein.customer.api_model.OrderDetailsApiResponse;
import com.minwein.customer.api_model.RatingAndReviewApiResponse;
import com.minwein.customer.api_model.RestaurantInformationApiResponse;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.api_model.RestaurantRatingApiResponse;
import com.minwein.customer.api_model.SignupApiResponse;
import com.minwein.customer.api_model.TermsAndConditionApiResponse;
import com.minwein.customer.api_model.UpdateAddressApiResponse;
import com.minwein.customer.api_model.VendorDetailsApiResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by AMSHEER on 06-12-2017.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST(Urls.LOGIN)
    Call<LoginApiResponse> login(@Field("email") String email,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST(Urls.EDIT_PROFILE)
    Call<EditProfileApiResponse> editProfile(@Query("language") String language, @Field("user_key") String userKey,
                                             @Field("first_name") String firstName,
                                             @Field("last_name") String lastName,
                                             @Field("email") String email,
                                             @Field("mobile_number") String mobileNumber,
                                             @Field("username") String userName,
                                             @Field("gender") String gender,
                                             @Field("dob") String dob,
                                             @Field("newsletters") int newsLetter);

    @FormUrlEncoded
    @POST(Urls.SIGN_UP)
    Call<SignupApiResponse> signup(@Field("registration_type") String registration_type,
                                   @Field("first_name") String first_name,
                                   @Field("last_name") String last_name,
                                   @Field("email") String email,
                                   @Field("password") String password,
                                   @Field("confirm_password") String confirm_password,
                                   @Field("mobile_number") String mobile_number,
                                   @Field("extra_variable1") String extra_variable1,
                                   @Field("extra_variable2") String extra_variable2,
                                   @Field("device_token") String device_token,
                                   @Field("device_type_id") String device_type_id);

    @FormUrlEncoded
    @POST(Urls.USER_CONTACT)
    Call<ContactUsApiResponse> contactUs(
            @Field("contact_first_name") String contactFirstName,
            @Field("contact_last_name") String contactLastName,
            @Field("contact_email") String contactEmail,
            @Field("contact_mobile") String contactMobile,
            @Field("contact_message") String contactMessage);

    @FormUrlEncoded
    @PUT(Urls.USER_FORGOT_PASSWORD)
    Call<ForgetPasswordApiResponse> forgetPassword(@Field("email_address") String emailAddress);

    @FormUrlEncoded
    @POST(Urls.USER_ADDRESS_CREATE)
    Call<AddNewAddressApiResponse> addNewAddress(@Field("user_key") String userKey,
                                                 @Field("address_line1") String addressLine1,
                                                 @Field("address_line2") String addressLine2,
                                                 @Field("latitude") String latitude,
                                                 @Field("longitude") String longitude,
                                                 @Field("addtype") String addType,
                                                 @Field("company_name") String companyName,
                                                 @Field("land_mark") String landMark);


    @GET(Urls.ORDERS)
    Call<OrderApiResponse> orders(@Query("user_key") String userKey);

    @GET(Urls.ADDRESS_LIST)
    Call<AddressListApiResponse> addressList(@Query("user_key") String userKey);

    @GET(Urls.LOYALTY_POINTS)
    Call<LoyaltyPointsApiResponse> loyaltyPoints(@Query("user_key") String userKey);

    @FormUrlEncoded
    @POST(Urls.FAVORITES)
    Call<FavoritesApiResponse> favourites(
            @Field("user_key") String userKey);

    @GET(Urls.FAQ)
    Call<FaqApiResponse> faq();


    @FormUrlEncoded
    @POST(Urls.USER_RATING)
    Call<RatingAndReviewApiResponse> rateAndReview(@Field("user_key") String userKey);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "user-address/delete", hasBody = true)
    Call<DeleteAddressApiResponse> deleteAddress(@Field("user_key") String userKey,
                                                 @Field("user_address_key") String userAddressKey);

    @FormUrlEncoded
    @POST(Urls.USER_ADDRESS_DEFAULT_ADDRESS)
    Call<DefaultAddressApiResponse> defaultAddress(@Field("user_address_key") String userAddressKey,
                                                   @Field("user_key") String userKey);


    @GET(Urls.ORDER_DETAILS)
    Call<OrderDetailsApiResponse> orderDetails(
            @Query("order_key") String orderKey);

    @GET(Urls.RESTAURANT_DETAIL)
    Call<RestaurantListApiResponse> restaurantList(@Query("latitude") String latitude,
                                                   @Query("longitude") String longitude,
                                                   @Query("cuisine_food") String cuisineFood,
                                                   @Query("user_key") String userKey);

    @GET(Urls.OFFER)
    Call<JSONObject> offerlist(
            @Query("offer_type") String offerType);

    @GET(Urls.USER_LOYALTY_POINTS_SHARE)
    Call<LoyaltyPointShareApiResponse> loyaltyPointShare(@Query("user_key") String userKey,
                                                         @Query("share_mobile_number") String shareMobileNumber,
                                                         @Query("share_loyalty_points") String shareLoyaltyPoints);

    @FormUrlEncoded
    @POST(Urls.FAVORITES_DELETE)
    Call<DeleteFavoritesApiResponse> favouriteDelete(@Field("user_key") String user_key,
                                                     @Field("vendor_key") String vendor_key);

    @GET(Urls.BRANCH_VENDOR_DETAILS)
    Call<VendorDetailsApiResponse> vendorDetails(@Query("vendor_key") String vendorKey);


    @FormUrlEncoded
    @PUT(Urls.USER_ADDRESS_UPDATE)
    Call<UpdateAddressApiResponse> updateAddress(
            @Field("user_key") String userKey,
            @Field("user_address_key") String userAddressKey,
            @Field("address_line1") String addressLine1,
            @Field("address_line2") String addressLine2,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("addtype") String addType,
            @Field("company_name") String companyName,
            @Field("land_mark") String landmark);

//    @FormUrlEncoded
    @POST(Urls.USER_ADD_ITEM_RATING)
    Call<AddRestaurantRatingApiResponse> addRestaurantRating(@Body RequestBody body);

    @GET(Urls.ITEM_DETAILS)
    Call<ItemDetailsResponse> getItemDetails(@Query("item_key") String itemKey);

    @GET(Urls.BRANCH_VENDOR_RATINGS)
    Call<RestaurantRatingApiResponse> restaurantRating(@Query("vendor_key") String vendorKey);

    @FormUrlEncoded
    @POST(Urls.FAVORITES_ADD)
    Call<AddFavoriteApiResponse> favouriteAdd(@Field("user_key") String userKey,
                                              @Field("vendor_key") String vendorKey,
                                              @Field("vendor_type") String vendorType);

    @GET(Urls.Restaurant_INFORMATION)
    Call<RestaurantInformationApiResponse> restaurantInformation(@Query("vendor_key") String vendorKey);

    @GET(Urls.TERMS_AND_CONDITION)
    Call<TermsAndConditionApiResponse> getTermsAndCondition(@Query("language") String language);


    @POST(Urls.ORDER_VERIFY)
    Call<CartVerifyApiResponse> orderVerifyCart(@Body RequestBody body);

    @POST(Urls.CONFIRM_ORDER)
    Call<OrderConfirmedApiResponse> checkout(@Body RequestBody body);

}