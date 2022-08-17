package com.minwein.customer.utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by AMSHEER on 04-12-2017.
 */

public class MyApplication extends Application {
    public static Context context;
    private final String REALM_NAME = "minwein.realm";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = getBaseContext();
        initializeRealm();

        Fresco.initialize(context);

        LanguageConstants.getInstance().LanguageConstants();

    }

    private void initializeRealm() {
        // Initialize Realm
        Realm.init(context);

        // The RealmConfiguration is created using the builder pattern.
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(42)
                .build();
// Use the config
        Realm realm = Realm.getInstance(config);

        MultiDex.install(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    public static void displayKnownError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void displayUnKnownError() {
        Toast.makeText(context, "Something wnt wrong!", Toast.LENGTH_LONG).show();
    }
    public static void result(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
