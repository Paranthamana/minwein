package com.minwein.customer.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
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
import com.minwein.customer.adapter.CheckoutAddressListAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.AddressListApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressCheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rvAddressList)
    RecyclerView rvAddressList;
    @BindView(R.id.tvAddressTitle)
    TextView tvAddressTitle;
    @BindView(R.id.ivAddressToolbarAddAddress)
    ImageView ivAddressToolbarAddAddress;
    @BindView(R.id.tbAddress)
    Toolbar tbAddress;
    @BindView(R.id.ablCheckOut)
    AppBarLayout ablCheckOut;
    @BindView(R.id.tvCheckoutAddressNoDataFound)
    TextView tvCheckoutAddressNoDataFound;
    @BindView(R.id.ivAddressToolbarAddAddressBack)
    ImageView ivAddressToolbarAddAddressBack;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_checkout);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(AddressCheckoutActivity.this);
        rvAddressList.setLayoutManager(mlayoutManager);
        rvAddressList.setItemAnimator(new DefaultItemAnimator());
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        tvAddressTitle.setText(LanguageConstants.selectaddress);
        ivAddressToolbarAddAddress.setImageResource(R.drawable.ic_add_address);
        ivAddressToolbarAddAddress.setOnClickListener(this);
        ivAddressToolbarAddAddressBack.setOnClickListener(this);
        String userKey = SessionManager.getInstance().getUserKey();
        CustomProgressDialog.getInstance().show(AddressCheckoutActivity.this);
        CommonApiCalls.getInstance().addressList(AddressCheckoutActivity.this, userKey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                AddressListApiResponse addressListApiResponse = (AddressListApiResponse) body;
                List<AddressListApiResponse.Datum> addresslist = addressListApiResponse.getData();

                if (addresslist.size() == 0) {
                    tvCheckoutAddressNoDataFound.setText(LanguageConstants.noAddressFound);
                    rvAddressList.setVisibility(View.GONE);
                    tvCheckoutAddressNoDataFound.setVisibility(View.VISIBLE);
                } else {
                    CheckoutAddressListAdapter adapter = new CheckoutAddressListAdapter(AddressCheckoutActivity.this, addresslist);
                    rvAddressList.setAdapter(adapter);
                    rvAddressList.setVisibility(View.VISIBLE);
                    tvCheckoutAddressNoDataFound.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == ivAddressToolbarAddAddressBack) {
            finish();
        }
        else if (view == ivAddressToolbarAddAddress){
            Bundle bundle = new Bundle();
            CommonFunctions.getInstance().newIntent(AddressCheckoutActivity.this, AddNewAddress.class, bundle, false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userKey = SessionManager.getInstance().getUserKey();
        CommonApiCalls.getInstance().addressList(AddressCheckoutActivity.this, userKey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                AddressListApiResponse addressListApiResponse = (AddressListApiResponse) body;
                List<AddressListApiResponse.Datum> addresslist = addressListApiResponse.getData();

                if (addresslist.size() == 0) {
                    tvCheckoutAddressNoDataFound.setText(LanguageConstants.noAddressFound);
                    rvAddressList.setVisibility(View.GONE);
                    tvCheckoutAddressNoDataFound.setVisibility(View.VISIBLE);
                } else {
                    CheckoutAddressListAdapter adapter = new CheckoutAddressListAdapter(AddressCheckoutActivity.this, addresslist);
                    rvAddressList.setAdapter(adapter);
                    rvAddressList.setVisibility(View.VISIBLE);
                    tvCheckoutAddressNoDataFound.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });
    }
}
