package com.minwein.customer.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.enums.SortByEnum;
import com.minwein.customer.fragment.RestaurantsListingFragment;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SortByActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivback)
    ImageView ivBack;
    @BindView(R.id.tvHeader)
    TextView tvHeader;
    @BindView(R.id.ivSortby_save)
    ImageView ivSortBySave;
    @BindView(R.id.tv_popularity)
    TextView tvPopularity;
    @BindView(R.id.tvA_Z)
    TextView tvA_Z;
    @BindView(R.id.tv_Fast_Delivery)
    TextView tvFastDelivery;
    @BindView(R.id.tv_Recommended)
    TextView tvRecommended;
    @BindView(R.id.tv_Rating)
    TextView tvRating;
    @BindView(R.id.tv_New)
    TextView tvNew;
    @BindView(R.id.tv_Delivery_fee)
    TextView tvDeliveryFee;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.tv_Food_Truck)
    TextView tvFoodTruck;
    @BindView(R.id.tv_Restaurant)
    TextView tvRestaurant;
    String textColor ;
    List<RestaurantListApiResponse.Data.Vendor_list> RestaurantList;
    @BindView(R.id.cvSortBy)
    CardView cvSortBy;
    SortByEnum sortByType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sortby);
        ButterKnife.bind(this);
        tvHeader.setText(LanguageConstants.sortby);
        tvPopularity.setText(LanguageConstants.popularity);
        tvA_Z.setText(LanguageConstants.a_z);
        tvFastDelivery.setText(LanguageConstants.fastDelivery);
        tvRecommended.setText(LanguageConstants.recommended);
        tvRating.setText(LanguageConstants.rating);
        tvNew.setText(LanguageConstants.New);
        tvDeliveryFee.setText(LanguageConstants.delivery_fee);
        tvOpen.setText(LanguageConstants.open);
        tvFoodTruck.setText(LanguageConstants.foodtruck);
        tvRestaurant.setText(LanguageConstants.restaurant);


        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                cvSortBy.setForegroundGravity(Gravity.START);
                ivBack.setImageResource(R.drawable.ic_back_right);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }

        ivBack.setOnClickListener(this);
        ivSortBySave.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == ivBack) {
            finish();
        }
        if (v == ivSortBySave) {
        }
    }

    @OnClick({R.id.tv_popularity, R.id.tvA_Z, R.id.tv_Fast_Delivery, R.id.tv_Recommended, R.id.tv_Rating, R.id.tv_New, R.id.tv_Delivery_fee, R.id.tv_open, R.id.tv_Food_Truck, R.id.tv_Restaurant})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_popularity:
                textColor = "1";
                sortByType= SortByEnum.POPULARITY;
                break;
            case R.id.tvA_Z:
                textColor = "2";
                sortByType= SortByEnum.A_Z;
                break;
            case R.id.tv_Fast_Delivery:
                textColor = "3";
                sortByType= SortByEnum.FAST_DELIVERY;
                break;
            case R.id.tv_Recommended:
                textColor = "4";
                sortByType= SortByEnum.RECOMMENDED;
                break;
            case R.id.tv_Rating:
                textColor = "5";
                sortByType= SortByEnum.RATING;
                break;
            case R.id.tv_New:
                textColor = "6";
                sortByType= SortByEnum.NEW;
                break;
            case R.id.tv_Delivery_fee:
                textColor = "7";
                sortByType= SortByEnum.DELIVERY_FEE;
                break;
            case R.id.tv_open:
                textColor = "8";
                sortByType= SortByEnum.OPEN;
                break;
            case R.id.tv_Food_Truck:
                textColor = "9";
                sortByType= SortByEnum.FOOD_TRUCK;
                break;
            case R.id.tv_Restaurant:
                textColor = "10";
                sortByType= SortByEnum.RESTAURANT;
                break;
        }
        switch (textColor) {
            case "1":
                tvPopularity.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "2":
                tvA_Z.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "3":
                tvFastDelivery.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "4":
                tvRecommended.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "5":
                tvRating.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "6":
                tvNew.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "7":
                tvDeliveryFee.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "8":
                tvOpen.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "9":
                tvFoodTruck.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case "10":
                tvRestaurant.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
        Intent intent = new Intent();
        intent.putExtra("SortByType",sortByType);
        setResult(2, intent);
        finish();

    }


}
