package com.minwein.customer.utils;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by AMSHEER on 05-12-2017.
 */

public class SessionManager {

    private static final String USER_KEY = "userKey";
    private static final String USER_FIRSTNAME ="firstName" ;
    private static final String USER_LASTNAME ="lastName" ;
    private static final String USER_EMAIL ="emailKey" ;
    private static final String USER_MOBILENUMBER = "mobileNumberKey";
    private static final String USER_GENDER = "genderKey";
    private static final String USER_DOB = "dobKey";
    private static final String USER_NEWSLETTER = "newsLetterKey";
    private static final String LOGIN_EMAIL = "loginEmailKey";
    private static final String LOGIN_PASSWORD = "loginPasswordKey";
    private static final String LOYALTY_POINTS = "loyaltyPointsKey";
    private static final String FAV_KEY = "favType";
    private static final String order_key = "orderKeyType";
    private static final String CURRENCY_SYMBOL = "currencySymbol";
    private static final String DEVICE_TOKEN = "";
    private static final String DEVICE_TYPE = "";

    private static SessionManager ourInstance = new SessionManager();
    private final String APP_PREFERENCE_NAME = "Minwein_pref";

    private final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private final String APPLICATION_LANGUAGE = "APP_LANGUAGE";


    public static SessionManager getInstance() {
        return ourInstance;
    }

    public String getAccessToken() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(ACCESS_TOKEN, "");
    }
    public String getAppLanguage() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(APPLICATION_LANGUAGE, "en");
    }

    public void setAppLanguage(String language) {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        prefs.edit().putString(APPLICATION_LANGUAGE, language).apply();
    }

    public void userDetails(String userkey, String first_name, String last_name, String email,
                            long mobile_number, String gender, String dob, Integer newsletter,Integer loyaltyPoints)
    {
        SharedPreferences pref = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        pref.edit().putString(USER_KEY, userkey).apply();
        pref.edit().putString(USER_FIRSTNAME, first_name).apply();
        pref.edit().putString(USER_LASTNAME, last_name).apply();
        pref.edit().putString(USER_EMAIL, email).apply();
        pref.edit().putLong(USER_MOBILENUMBER, mobile_number).apply();
        pref.edit().putString(USER_GENDER, gender).apply();
        pref.edit().putString(USER_DOB, dob).apply();
        pref.edit().putInt(USER_NEWSLETTER, newsletter).apply();
        pref.edit().putInt(LOYALTY_POINTS, loyaltyPoints).apply();
    }

    public String getUserKey() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(USER_KEY, "");
    }
    public String getUserFirstname() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(USER_FIRSTNAME, "");
    }
    public String getUserLastname() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(USER_LASTNAME, "");
    }
    public long getUserMobilenumber() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getLong(USER_MOBILENUMBER, 0);
    }
    public String getUserEmail() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(USER_EMAIL, "");
    }
    public String getUserGender() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(USER_GENDER, "");
    }
    public String getUserDob() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(USER_DOB, "");
    }
    public int getUserNewsletter() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getInt(USER_NEWSLETTER,0);
    }

    public void setloyalitypoints(Integer loyaltyPoints) {
        SharedPreferences pref = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME,Activity.MODE_PRIVATE);
        pref.edit().putInt(LOYALTY_POINTS, loyaltyPoints).apply();
    }
    public Integer getLoyaltyPoints() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getInt(LOYALTY_POINTS, 0);
    }

    public void setfavkey(String favkey) {
        SharedPreferences pref = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        pref.edit().putString(USER_KEY, favkey).apply();
    }

    public String getCurrencySymbol() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(CURRENCY_SYMBOL, "SAR");
    }

    public String getDeviceToken() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(DEVICE_TOKEN, "");
    }

    public String getDeviceType() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return prefs.getString(DEVICE_TYPE, "1");
    }
    public void Logout() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

    }
}
