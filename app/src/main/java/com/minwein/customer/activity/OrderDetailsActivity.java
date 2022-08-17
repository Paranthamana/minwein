package com.minwein.customer.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.adapter.OrderDetailsAdapter;
import com.minwein.customer.adapter.PaymentDetailsAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.OrderDetailsApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tech on 24-11-2017.
 */

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imOrderDetailsBack)
    ImageView imOrderDetailsBack;
    @BindView(R.id.tvOrderDetails)
    TextView tvOrderDetails;
    @BindView(R.id.tbOrderDetails)
    Toolbar tbOrderDetails;
    @BindView(R.id.tvOrderDetailsRestaurantName)
    TextView tvOrderDetailsRestaurantName;
    @BindView(R.id.rvOrderDetails)
    RecyclerView rvOrderDetails;
    @BindView(R.id.rvPaymentDetails)
    RecyclerView rvPaymentDetails;
    @BindView(R.id.tvitemNameTitle)
    TextView tvitemNameTitle;
    @BindView(R.id.tvQuantityTitle)
    TextView tvQuantityTitle;
    @BindView(R.id.tvPriceTitle)
    TextView tvPriceTitle;
    private OrderDetailsApiResponse.Venor_details venorDetails;
    private List<OrderDetailsApiResponse.Item> orderDetailItems;
    private List<OrderDetailsApiResponse.Payment_detail> orderDetailItem;
    private OrderDetailsAdapter mAdapter;
    private PaymentDetailsAdapter mAdapter1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        ButterKnife.bind(this);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                imOrderDetailsBack.setImageResource(R.drawable.ic_back_right);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                imOrderDetailsBack.setImageResource(R.drawable.ic_back);
            }
        }

        tvOrderDetails.setText(LanguageConstants.orderDetail);
        tvitemNameTitle.setText(LanguageConstants.itemName);
        tvQuantityTitle.setText(LanguageConstants.qty);
        tvPriceTitle.setText(LanguageConstants.price);

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        rvOrderDetails.setLayoutManager(mlayoutManager);
        rvOrderDetails.setItemAnimator(new DefaultItemAnimator());
        rvOrderDetails.setAdapter(mAdapter);

        RecyclerView.LayoutManager mlayoutManager1 = new LinearLayoutManager(getApplicationContext());
        rvPaymentDetails.setLayoutManager(mlayoutManager1);
        rvPaymentDetails.setItemAnimator(new DefaultItemAnimator());
        rvPaymentDetails.setAdapter(mAdapter1);

        Bundle bundle = getIntent().getExtras();
        String orderkey = bundle.getString("orderKey");

//        Gson gson = new Gson();
//        OrderDetailsApiResponse.Data data = gson.fromJson(orderkey, OrderDetailsApiResponse.Data.class);
//        String orderkeys = data.getOrder_details().getOrder_key();
//        Bundle bundle1 = new Bundle();
//        bundle1.putString("orderkey", orderkeys);

        CommonApiCalls.getInstance().orderDetails(this, orderkey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                OrderDetailsApiResponse orderDetailsApiResponse = (OrderDetailsApiResponse) body;
                orderDetailItems = orderDetailsApiResponse.getData().getOrder_details().getItems();
                orderDetailItem = orderDetailsApiResponse.getData().getPayment_details();
                venorDetails = orderDetailsApiResponse.getData().getVenor_details();
                tvOrderDetailsRestaurantName.setText(venorDetails.getVendor_name());
                mAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this, orderDetailItems);
                mAdapter1 = new PaymentDetailsAdapter(OrderDetailsActivity.this, orderDetailItem);
                rvOrderDetails.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                rvPaymentDetails.setAdapter(mAdapter1);
            }

            @Override
            public void onFailure(String reason) {

            }
        });
        imOrderDetailsBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == imOrderDetailsBack) {
            finish();
        }

    }
}

