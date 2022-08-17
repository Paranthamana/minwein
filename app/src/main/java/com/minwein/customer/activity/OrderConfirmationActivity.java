package com.minwein.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.api_model.OrderConfirmedApiResponse;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderConfirmationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivConfirm)
    ImageView ivConfirm;
    @BindView(R.id.tvOrderConfirmation)
    TextView tvOrderConfirmation;
    @BindView(R.id.tvOrderIdText)
    TextView tvOrderIdText;
    @BindView(R.id.tvOrderId)
    TextView tvOrderId;
    @BindView(R.id.btHome)
    Button btHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        ButterKnife.bind(this);

        tvOrderConfirmation.setText(LanguageConstants.orderConfirmation);
        tvOrderIdText.setText(LanguageConstants.orderId+":");
        btHome.setText(LanguageConstants.home);
        btHome.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String orderId = bundle.getString("orderId");
        tvOrderId.setText(orderId);
    }

    @Override
    public void onClick(View v) {
        if(v==btHome){
            RealmLibrary.getInstance().clearCart();
            CommonFunctions.getInstance().newIntent(OrderConfirmationActivity.this,HomeActivity.class,Bundle.EMPTY,true);
        }
    }
}
