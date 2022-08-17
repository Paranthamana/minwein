package com.minwein.customer.api;

import android.content.Context;

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
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.MyApplication;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AMSHEER on 08-01-2018.
 */

public class CommonApiCalls {
    private static CommonApiCalls ourInstance;

    public static CommonApiCalls getInstance() {
        ourInstance = new CommonApiCalls();
        return ourInstance;
    }


    public void login(Context context, final String email, final String password, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<LoginApiResponse> call = apiInterface.login(email, password);
        call.enqueue(new Callback<LoginApiResponse>() {
            @Override
            public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void editProfile(Context context, String language, String userKey,
                            String firstName, String lastName, String email,
                            String mobileNumber, String username, String gender,
                            String dob, int newsLetter, final CommonCallback.Listener listener) {

        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<EditProfileApiResponse> call = apiInterface.editProfile(language, userKey,
                firstName, lastName, email, mobileNumber,
                username, gender, dob, newsLetter);

        call.enqueue(new Callback<EditProfileApiResponse>() {
            @Override
            public void onResponse(Call<EditProfileApiResponse> call, Response<EditProfileApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    MyApplication.displayKnownError(response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<EditProfileApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void signUp(Context context, String registrationType,
                       String firstName, String lastName, String email, String password,
                       String confirmPassword, String mobileNumber, String extraVariable1, String extraVariable2,
                       String deviceToken, String deviceTypeId, final CommonCallback.Listener listener) {

        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<SignupApiResponse> call = apiInterface.signup(registrationType,
                firstName, lastName, email, password, confirmPassword, mobileNumber,
                extraVariable1, extraVariable2, deviceToken, deviceTypeId);

        call.enqueue(new Callback<SignupApiResponse>() {
            @Override
            public void onResponse(Call<SignupApiResponse> call, Response<SignupApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    MyApplication.displayKnownError(response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<SignupApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void contactUs(Context context, String contactFirstName, String contactLastName,
                          String contactEmail, String contactMobileNumber, String contactMessage, final CommonCallback.Listener listener) {

        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<ContactUsApiResponse> call = apiInterface.contactUs(contactFirstName,
                contactLastName, contactEmail, contactMobileNumber, contactMessage);

        call.enqueue(new Callback<ContactUsApiResponse>() {
            @Override
            public void onResponse(Call<ContactUsApiResponse> call, Response<ContactUsApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    MyApplication.displayKnownError(response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ContactUsApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void forgetPassword(Context context, final String email, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<ForgetPasswordApiResponse> call = apiInterface.forgetPassword(email);
        call.enqueue(new Callback<ForgetPasswordApiResponse>() {
            @Override
            public void onResponse(Call<ForgetPasswordApiResponse> call, Response<ForgetPasswordApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    MyApplication.displayKnownError(response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }


    public void AddNewAddress(Context context, String userkey, String addressLine1,
                              String addressLine2,
                              String latitude, String longitude, String addType, String companyName,
                              String landMark, final CommonCallback.Listener listener) {

        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<AddNewAddressApiResponse> call = apiInterface.addNewAddress(userkey, addressLine1,
                addressLine2, latitude, longitude, addType, companyName, landMark);
        call.enqueue(new Callback<AddNewAddressApiResponse>() {
            @Override
            public void onResponse(Call<AddNewAddressApiResponse> call, Response<AddNewAddressApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    MyApplication.displayKnownError(response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddNewAddressApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void RateAndReview(Context context, final String userkey, final CommonCallback.Listener listner) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<RatingAndReviewApiResponse> call = apiInterface.rateAndReview(userkey);
        call.enqueue(new Callback<RatingAndReviewApiResponse>() {
            @Override
            public void onResponse(Call<RatingAndReviewApiResponse> call, Response<RatingAndReviewApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
//                    EventBus.getDefault().post(new EBRateAndReview(response.body()));
                    listner.onSuccess(response.body());
                } else {
                    MyApplication.displayKnownError(response.message());
                    listner.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<RatingAndReviewApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void orders(Context context, String userKey, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<OrderApiResponse> call = apiInterface.orders(userKey);
        call.enqueue(new Callback<OrderApiResponse>() {
            @Override
            public void onResponse(Call<OrderApiResponse> call, Response<OrderApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                    // EventBus.getDefault().post(new EBOrder(response.body()));
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void addressList(Context context, String userKey, final CommonCallback.Listener listner) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<AddressListApiResponse> call = apiInterface.addressList(userKey);
        call.enqueue(new Callback<AddressListApiResponse>() {
            @Override
            public void onResponse(Call<AddressListApiResponse> call, Response<AddressListApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listner.onSuccess(response.body());
//                    EventBus.getDefault().post(new EBAddressList(response.body()));
                } else {
                    MyApplication.displayKnownError(response.message());
                    listner.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddressListApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void loyaltyPoints(Context context, String userKey, final CommonCallback.Listener listner) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<LoyaltyPointsApiResponse> call = apiInterface.loyaltyPoints(userKey);
        call.enqueue(new Callback<LoyaltyPointsApiResponse>() {
            @Override
            public void onResponse(Call<LoyaltyPointsApiResponse> call, Response<LoyaltyPointsApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listner.onSuccess(response.body());
//                    EventBus.getDefault().post(new EBLoyaltyPoints(response.body()));
                } else {
                    MyApplication.displayKnownError(response.message());
                    listner.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<LoyaltyPointsApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void favourites(Context context, String userKey, final CommonCallback.Listener listner) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<FavoritesApiResponse> call = apiInterface.favourites(userKey);
        call.enqueue(new Callback<FavoritesApiResponse>() {
            @Override
            public void onResponse(Call<FavoritesApiResponse> call, Response<FavoritesApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listner.onSuccess(response.body());
//                    EventBus.getDefault().post(new EBFavourites(response.body().getData()));
                } else {
                    MyApplication.displayKnownError(response.message());
                    listner.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<FavoritesApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void faq(Context context, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<FaqApiResponse> call = apiInterface.faq();
        call.enqueue(new Callback<FaqApiResponse>() {
            @Override
            public void onResponse(Call<FaqApiResponse> call, Response<FaqApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
//                    EventBus.getDefault().post(new EBFaq(response.body()));
                } else {
                    MyApplication.displayKnownError(response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<FaqApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void orderDetails(Context context, String orderKey, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<OrderDetailsApiResponse> call = apiInterface.orderDetails(orderKey);
        call.enqueue(new Callback<OrderDetailsApiResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsApiResponse> call, Response<OrderDetailsApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
//                    EventBus.getDefault().post(new EBOrderDetails(response.body()));
                } else {
                    MyApplication.displayKnownError(response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void deleteAddress(Context context, final String userKey, final String userAddressKey, final CommonCallback.Listener listner) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<DeleteAddressApiResponse> call = apiInterface.deleteAddress(userKey, userAddressKey);
        call.enqueue(new Callback<DeleteAddressApiResponse>() {
            @Override
            public void onResponse(Call<DeleteAddressApiResponse> call, Response<DeleteAddressApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listner.onSuccess(response.body());
//                    EventBus.getDefault().post(new EBDeleteAddress(response.body()));

                } else {
                    MyApplication.displayKnownError(response.message());
                    listner.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<DeleteAddressApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void defaultAddress(Context context, final String userAddressKey, final String userKey, final CommonCallback.Listener listner) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<DefaultAddressApiResponse> call = apiInterface.defaultAddress(userAddressKey, userKey);
        call.enqueue(new Callback<DefaultAddressApiResponse>() {
            @Override
            public void onResponse(Call<DefaultAddressApiResponse> call, Response<DefaultAddressApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
//                    EventBus.getDefault().post(new EBDefaultAddress(response.body()));
                    listner.onSuccess(response.body());
                } else {
                    MyApplication.displayKnownError(response.message());
                    listner.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<DefaultAddressApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void RestaurantList(Context context, final String latitude, final String longitude, final String cuisine, String userKey, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<RestaurantListApiResponse> call = apiInterface.restaurantList(latitude, longitude, cuisine, userKey);
        call.enqueue(new Callback<RestaurantListApiResponse>() {
            @Override
            public void onResponse(Call<RestaurantListApiResponse> call, Response<RestaurantListApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
//                    MyApplication.displayKnownError(response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<RestaurantListApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void offerList(Context context, String offerType, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<JSONObject> call = apiInterface.offerlist(offerType);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                    //    EventBus.getDefault().post(new EBOfferList(response.body()));
                } else {
                    MyApplication.displayKnownError(response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void loyaltyPointShare(Context context, String userKey, String shareMobileNumber, String shareLoyaltyPoints, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<LoyaltyPointShareApiResponse> call = apiInterface.loyaltyPointShare(userKey, shareMobileNumber, shareLoyaltyPoints);
        call.enqueue(new Callback<LoyaltyPointShareApiResponse>() {
            @Override
            public void onResponse(Call<LoyaltyPointShareApiResponse> call, Response<LoyaltyPointShareApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<LoyaltyPointShareApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void updateAddress(Context context, String userkey, String userAddressKey, String addressLine1,
                              String addressLine2, String latitude, String longitude, String addType, String companyName,
                              String landMark, final CommonCallback.Listener listener) {

        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<UpdateAddressApiResponse> call = apiInterface.updateAddress(userkey, userAddressKey, addressLine1,
                addressLine2, latitude, longitude, addType, companyName, landMark);

        call.enqueue(new Callback<UpdateAddressApiResponse>() {
            @Override
            public void onResponse(Call<UpdateAddressApiResponse> call, Response<UpdateAddressApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {

                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());

                }
            }

            @Override
            public void onFailure(Call<UpdateAddressApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }



    public void addRestaurantRating(Context context, String body, final CommonCallback.Listener listener) {

        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (body));
        Call<AddRestaurantRatingApiResponse> call = apiInterface.addRestaurantRating(mRequestBody);

        call.enqueue(new Callback<AddRestaurantRatingApiResponse>() {
            @Override
            public void onResponse(Call<AddRestaurantRatingApiResponse> call, Response<AddRestaurantRatingApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());

                }
            }

            @Override
            public void onFailure(Call<AddRestaurantRatingApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void vendorDetails(Context context, String vendorKey, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<VendorDetailsApiResponse> call = apiInterface.vendorDetails(vendorKey);
        call.enqueue(new Callback<VendorDetailsApiResponse>() {
            @Override
            public void onResponse(Call<VendorDetailsApiResponse> call, Response<VendorDetailsApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<VendorDetailsApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();
            }
        });
    }

    public void favouriteDelete(Context context, String user_key, String vendorKey, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<DeleteFavoritesApiResponse> call = apiInterface.favouriteDelete(user_key, vendorKey);
        call.enqueue(new Callback<DeleteFavoritesApiResponse>() {
            @Override
            public void onResponse(Call<DeleteFavoritesApiResponse> call, Response<DeleteFavoritesApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {

                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<DeleteFavoritesApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });
    }

    public void favouriteAdd(Context context, String userKey, String vendorKey, String vendorType, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<AddFavoriteApiResponse> call = apiInterface.favouriteAdd(userKey, vendorKey, vendorType);
        call.enqueue(new Callback<AddFavoriteApiResponse>() {
            @Override
            public void onResponse(Call<AddFavoriteApiResponse> call, Response<AddFavoriteApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddFavoriteApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void RestaurantInformation(Context context, String vendorKey, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<RestaurantInformationApiResponse> call = apiInterface.restaurantInformation(vendorKey);
        call.enqueue(new Callback<RestaurantInformationApiResponse>() {
            @Override
            public void onResponse(Call<RestaurantInformationApiResponse> call, Response<RestaurantInformationApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<RestaurantInformationApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();

            }
        });

    }

    public void getItemDetails(Context context, String itemKey, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<ItemDetailsResponse> call = apiInterface.getItemDetails(itemKey);
        call.enqueue(new Callback<ItemDetailsResponse>() {
            @Override
            public void onResponse(Call<ItemDetailsResponse> call, Response<ItemDetailsResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();
            }
        });

    }

    public void restaurantRating(Context context, String vendorKey, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<RestaurantRatingApiResponse> call = apiInterface.restaurantRating(vendorKey);
        call.enqueue(new Callback<RestaurantRatingApiResponse>() {
            @Override
            public void onResponse(Call<RestaurantRatingApiResponse> call, Response<RestaurantRatingApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
//                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<RestaurantRatingApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();
            }
        });

    }

    public void getTermsAndCondition(Context context, String language, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }
        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);
        Call<TermsAndConditionApiResponse> call = apiInterface.getTermsAndCondition(language);
        call.enqueue(new Callback<TermsAndConditionApiResponse>() {
            @Override
            public void onResponse(Call<TermsAndConditionApiResponse> call, Response<TermsAndConditionApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TermsAndConditionApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();
            }
        });

    }

    public void orderVerifyCart(Context context, String body, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }

        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);

        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (body));
        Call<CartVerifyApiResponse> call = apiInterface.orderVerifyCart(mRequestBody);
        call.enqueue(new Callback<CartVerifyApiResponse>() {
            @Override
            public void onResponse(Call<CartVerifyApiResponse> call, Response<CartVerifyApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());

                }
            }

            @Override
            public void onFailure(Call<CartVerifyApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();
            }
        });

    }


    public void checkout(Context context, String body, final CommonCallback.Listener listener) {
        if (!CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().show(context);
        }

        ApiInterface apiInterface = ApiConfiguration.getInstance().getApiBuilder().create(ApiInterface.class);

        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (body));
        Call<OrderConfirmedApiResponse> call = apiInterface.checkout(mRequestBody);
        call.enqueue(new Callback<OrderConfirmedApiResponse>() {
            @Override
            public void onResponse(Call<OrderConfirmedApiResponse> call, Response<OrderConfirmedApiResponse> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                    MyApplication.displayKnownError(response.message());

                }
            }

            @Override
            public void onFailure(Call<OrderConfirmedApiResponse> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                t.printStackTrace();
                MyApplication.displayUnKnownError();
            }
        });

    }


}
