package com.minwein.customer.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.adapter.RestaurantDetailPagerAdapter;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.fragment.RestaurantInformation;
import com.minwein.customer.fragment.RestaurantRatingFragment;
import com.minwein.customer.fragment.RestaurantsListingFragment;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, RestaurantInformation.OnFragmentInteractionListener, RestaurantRatingFragment.OnFragmentInteractionListener
        , View.OnClickListener {


    //    public static String vendorName;
    @BindView(R.id.imRestaurantDetailsRestaurantBack)
    ImageView imRestaurantDetailsRestaurantBack;
    @BindView(R.id.tbRestaurantDetails)
    Toolbar tbRestaurantDetails;
    @BindView(R.id.tlRestaurantDetails)
    TabLayout tlRestaurantDetails;
    @BindView(R.id.vpRestaurantDetails)
    ViewPager vpRestaurantDetails;
    //    public static String vendorKey;
    @BindView(R.id.tvRestaurantitle)
    TextView tvRestaurantitle;
    @BindView(R.id.ivRestaurantDetailsCart)
    ImageView ivRestaurantDetailsCart;
    @BindView(R.id.tvDetailCartValue)
    TextView tvDetailCartValue;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    String vendorKey;
    String restaurant;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);

        flCart.setOnClickListener(this);
        // setSupportActionBar(tbRestaurantDetails);
        imRestaurantDetailsRestaurantBack.setOnClickListener(this);

        tlRestaurantDetails.addTab(tlRestaurantDetails.newTab().setText(LanguageConstants.menu));
        tlRestaurantDetails.addTab(tlRestaurantDetails.newTab().setText(LanguageConstants.Information));
        tlRestaurantDetails.addTab(tlRestaurantDetails.newTab().setText(LanguageConstants.Ratings));
        tlRestaurantDetails.setTabGravity(TabLayout.GRAVITY_FILL);

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                imRestaurantDetailsRestaurantBack.setImageResource(R.drawable.ic_back_right);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        Bundle bundle = getIntent().getExtras();
        String restaurant = bundle.getString("restaurant");
        Gson gson = new Gson();
        RestaurantListApiResponse.Data.Vendor_list data = gson.fromJson(restaurant, RestaurantListApiResponse.Data.Vendor_list.class);
        String Restaurantname = data.getVendor_name();
        tvRestaurantitle.setText(Restaurantname);

        if (RealmLibrary.getInstance().getCartSize() == 0) {
            tvDetailCartValue.setText("0");
        } else {
            tvDetailCartValue.setText(String.valueOf(RealmLibrary.getInstance().getCartSize()));
        }

        RestaurantDetailPagerAdapter adapter = new RestaurantDetailPagerAdapter(getSupportFragmentManager(),
                tlRestaurantDetails.getTabCount(), restaurant);
        vpRestaurantDetails.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlRestaurantDetails));
        tlRestaurantDetails.setTabsFromPagerAdapter(adapter);
        vpRestaurantDetails.setAdapter(adapter);
        tlRestaurantDetails.setOnTabSelectedListener(this);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vpRestaurantDetails.setCurrentItem(tab.getPosition());
        if (tab.getPosition() == 0) {
            flCart.setVisibility(View.VISIBLE);
            flCart.setOnClickListener(this);
        } else if (tab.getPosition() == 1) {
            flCart.setVisibility(View.VISIBLE);
            flCart.setOnClickListener(this);
        } else {
            flCart.setVisibility(View.VISIBLE);
            flCart.setOnClickListener(this);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        if (v == imRestaurantDetailsRestaurantBack) {
            finish();
        } else if (v == flCart) {
            Bundle bundle = new Bundle();
            if (RealmLibrary.getInstance().getCartSize() == 0) {
                CommonFunctions.getInstance().ShowSnackBar(RestaurantDetailsActivity.this, LanguageConstants.cartEmpty);
            } else {
                CartSummaryActivity.deliveryOptions.clear();
                CommonFunctions.getInstance().newIntent(RestaurantDetailsActivity.this, CartSummaryActivity.class, bundle, false);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (RealmLibrary.getInstance().getCartSize() == 0) {
            tvDetailCartValue.setText("0");
        } else {
            tvDetailCartValue.setText(String.valueOf(RealmLibrary.getInstance().getCartSize()));
        }
    }
}
