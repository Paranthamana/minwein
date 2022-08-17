package com.minwein.customer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.minwein.customer.R;
import com.minwein.customer.utils.AppSharedValues;
import com.minwein.customer.utils.CommonFunctions;

public class SplashScreenActivity extends AppCompatActivity {

    private final long SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppSharedValues.latitude=0.0;
        AppSharedValues.longitude=0.0;
        AppSharedValues.foodOrCuisine="";
        if (getActionBar()!= null){
            getActionBar().hide();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                CommonFunctions.getInstance().newIntent(SplashScreenActivity.this, HomeActivity.class,  bundle, true);
            }
        }, SPLASH_TIME_OUT);
    }
}
