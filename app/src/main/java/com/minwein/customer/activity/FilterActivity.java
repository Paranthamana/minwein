package com.minwein.customer.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minwein.customer.R;
import com.minwein.customer.adapter.FilterCuisines;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.enums.SortByEnum;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;
import com.nex3z.flowlayout.FlowLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.tvHeader)
    TextView tvHeader;
    @BindView(R.id.ivFilterSave)
    ImageView ivFilterSave;
    @BindView(R.id.tbFilter)
    Toolbar tbFilter;
    @BindView(R.id.cvFilter)
    CardView cvFilter;
    @BindView(R.id.tvCuisinesTitle)
    TextView tvCuisinesTitle;
    @BindView(R.id.rvCuisines)
    RecyclerView rvCuisines;
    @BindView(R.id.tvMore)
    TextView btnMore;
    @BindView(R.id.tvFoodType)
    TextView tvFoodType;
    @BindView(R.id.tvNearest)
    TextView tvNearest;
    @BindView(R.id.tvMinOrder)
    TextView tvMinOrder;
    @BindView(R.id.cbVeg)
    CheckBox cbVeg;
    @BindView(R.id.cbNonVeg)
    CheckBox cbNonVeg;
    @BindView(R.id.cbBoth)
    CheckBox cbBoth;
    @BindView(R.id.rbMinZero)
    RadioButton rbMinZero;
    @BindView(R.id.rbMinOne)
    RadioButton rbMinOne;
    @BindView(R.id.rbMinTwo)
    RadioButton rbMinTwo;
    @BindView(R.id.rbMinThree)
    RadioButton rbMinThree;
    @BindView(R.id.rgNearest)
    RadioGroup rgNearest;
    @BindView(R.id.tvDeliveryType)
    TextView tvDeliveryType;
    @BindView(R.id.rbCompany)
    RadioButton rbCompany;
    @BindView(R.id.rbVendor)
    RadioButton rbVendor;
    @BindView(R.id.rgDeliveryType)
    RadioGroup rgDeliveryType;
    @BindView(R.id.rbAll)
    RadioButton rbAll;
    @BindView(R.id.rbUnderOne)
    RadioButton rbUnderOne;
    @BindView(R.id.rbUnderTwo)
    RadioButton rbUnderTwo;
    @BindView(R.id.rbUnderThree)
    RadioButton rbUnderThree;
    @BindView(R.id.rgMinOrder)
    RadioGroup rgMinOrder;
    @BindView(R.id.tv_popularity)
    TextView tvPopularity;
    @BindView(R.id.tvA_Z)
    TextView tvAZ;
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
    @BindView(R.id.flow2)
    FlowLayout flow2;
    @BindView(R.id.tvSortDetails)
    TextView tvSortDetails;
    @BindView(R.id.tvFilterDetails)
    TextView tvFilterDetails;
    @BindView(R.id.btnApply)
    Button btnApply;
    @BindView(R.id.tvClear)
    TextView tvClear;

    private List<RestaurantListApiResponse.Data.Cusine> cuisine;
    private int more = 0;

    ArrayList<String> selectedCuisines = new ArrayList<>();
    List<String> food_type = new ArrayList<>();
    SortByEnum sortByType;
    String textColor ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        tvHeader.setText(LanguageConstants.filter);
        setSupportActionBar(tbFilter);
        ivback.setOnClickListener(this);
        ivFilterSave.setOnClickListener(this);
        fillAutoSpacingLayout();
        tvCuisinesTitle.setText(LanguageConstants.Cuisines);
        btnMore.setText(LanguageConstants.more);
        tvNearest.setText(LanguageConstants.Nearest);
        tvDeliveryType.setText(LanguageConstants.Delivery_Type);
        tvMinOrder.setText(LanguageConstants.Min_Order_Value);
        tvFoodType.setText(LanguageConstants.Food_Type);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                cvFilter.setForegroundGravity(Gravity.START);
                ivback.setImageResource(R.drawable.ic_back_right);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }

        String cuisines = getIntent().getExtras().getString("cuisines");
        Gson gson = new Gson();
        Type listOfObjects2 = new TypeToken<List<RestaurantListApiResponse.Data.Cusine>>() {
        }.getType();
        cuisine = gson.fromJson(cuisines, listOfObjects2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvCuisines.setLayoutManager(linearLayoutManager);

        List<String> cuisinesList = new ArrayList<String>();
        for (int count = 0; count < 5 && count < cuisine.size(); count++) {
            cuisinesList.add(cuisine.get(count).getCuisine_name());
        }


        FilterCuisines filterCuisines = new FilterCuisines(FilterActivity.this, cuisinesList, selectedCuisines);
        rvCuisines.setAdapter(filterCuisines);
        filterCuisines.getItemCount();

        btnMore.setOnClickListener(this);


        cbVeg.setText(LanguageConstants.veg);
        cbNonVeg.setText(LanguageConstants.Non_veg);
        cbBoth.setText(LanguageConstants.boths);

        cbBoth.setOnClickListener(this);
        cbVeg.setOnClickListener(this);
        cbNonVeg.setOnClickListener(this);
        btnApply.setOnClickListener(this);


        rbMinZero.setText("0-15min");
        rbMinOne.setText("15min-30min");
        rbMinTwo.setText("30min-45min");
        rbMinThree.setText("45min-60min");


        rbAll.setText(LanguageConstants.All);
        rbUnderOne.setText(LanguageConstants.Under + " 100");
        rbUnderTwo.setText(LanguageConstants.Under + " 200");
        rbUnderThree.setText(LanguageConstants.Under + " 300");


        rbCompany.setText(LanguageConstants.Company);
        rbVendor.setText(LanguageConstants.vendor);

    }

    private void fillAutoSpacingLayout() {
        tvClear.setText(LanguageConstants.clearall);
        tvFilterDetails.setText(LanguageConstants.filterby);
        btnApply.setText(LanguageConstants.apply);
        tvSortDetails.setText(LanguageConstants.Sortby);
        tvPopularity.setText(LanguageConstants.popularity);
        tvAZ.setText(LanguageConstants.a_z);
        tvFastDelivery.setText(LanguageConstants.fastDelivery);
        tvRecommended.setText(LanguageConstants.recommended);
        tvRating.setText(LanguageConstants.rating);
        tvNew.setText(LanguageConstants.New);
        tvDeliveryFee.setText(LanguageConstants.delivery_fee);
        tvOpen.setText(LanguageConstants.open);
        tvFoodTruck.setText(LanguageConstants.foodtruck);
        tvRestaurant.setText(LanguageConstants.restaurant);

    }


    @Override
    public void onClick(View view) {
        if (view == ivback) {
            finish();
        } else if (view == btnApply) {

            int nearestButton = rgNearest.getCheckedRadioButtonId();
            View nearestView = rgNearest.findViewById(nearestButton);
            int nearest = rgNearest.indexOfChild(nearestView) + 1;


            int deliveryButton = rgDeliveryType.getCheckedRadioButtonId();
            View deliveryView = rgDeliveryType.findViewById(deliveryButton);
            int delivery = rgDeliveryType.indexOfChild(deliveryView) + 1;


            int minOrderButton = rgMinOrder.getCheckedRadioButtonId();
            View minOrderView = rgMinOrder.findViewById(minOrderButton);
            int minOrder = rgMinOrder.indexOfChild(minOrderView) + 1;


            Gson gson = new Gson();
            String cuisine = gson.toJson(selectedCuisines);
            String foodType = gson.toJson(food_type);
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putString("nearest", nearest != 0 ? String.valueOf(nearest) : "");
            bundle.putString("delivery", delivery != 0 ? String.valueOf(delivery) : "");
            bundle.putString("minOrder", minOrder != 0 ? String.valueOf(minOrder) : "");
            bundle.putString("cuisine", cuisine);
            bundle.putString("foodType", foodType);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        } else if (view == btnMore) {
            if (more == 0) {
                more = 1;
                btnMore.setText(LanguageConstants.less);
                List<String> cuisinesList = new ArrayList<String>();
                for (int count = 0; count < cuisine.size(); count++) {
                    cuisinesList.add(cuisine.get(count).getCuisine_name());
                }

                FilterCuisines filterCuisines = new FilterCuisines(FilterActivity.this, cuisinesList, selectedCuisines);
                rvCuisines.setAdapter(filterCuisines);
            } else {

                more = 0;
                btnMore.setText(LanguageConstants.more);

                List<String> cuisinesList = new ArrayList<String>();
                for (int count = 0; count < 5 && count < cuisine.size(); count++) {
                    cuisinesList.add(cuisine.get(count).getCuisine_name());
                }

                FilterCuisines filterCuisines = new FilterCuisines(FilterActivity.this, cuisinesList, selectedCuisines);
                rvCuisines.setAdapter(filterCuisines);
            }
        } else if (view == cbNonVeg) {
            if (food_type.contains("2")) {
                food_type.remove("2");
                cbNonVeg.setChecked(false);
            } else {
                food_type.add("2");
                cbNonVeg.setChecked(true);
            }
        } else if (view == cbVeg) {
            if (food_type.contains("1")) {
                food_type.remove("1");
                cbVeg.setChecked(false);
            } else {
                food_type.add("1");
                cbVeg.setChecked(true);
            }
        } else if (view == cbBoth) {
            if (food_type.contains("3")) {
                food_type.remove("3");
                cbBoth.setChecked(false);
            } else {
                food_type.add("3");
                cbBoth.setChecked(true);
            }
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
                tvAZ.setTextColor(getResources().getColor(R.color.colorPrimary));
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
