package com.minwein.customer.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.adapter.IngredientsListAdapter;
import com.minwein.customer.adapter.MenuDetailRelatedItemAdapter;
import com.minwein.customer.adapter.MenuDetailTimeLineAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.ItemDetailsResponse;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.api_model.VendorDetailsApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.model.Ingredients;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuDetailActivity extends AppCompatActivity implements View.OnClickListener {


    public static ArrayList<Ingredients> ingredientsModels = new ArrayList<>();
    @BindView(R.id.imMenuDetail_back)
    ImageView imMenuDetailBack;
    @BindView(R.id.tvMenuDetail_Title)
    TextView tvMenuDetailTitle;
    @BindView(R.id.imMenuDetail_cart)
    ImageView imMenuDetailCart;
    @BindView(R.id.tbMenuDetail)
    Toolbar tbMenuDetail;
    @BindView(R.id.tvItemNameName)
    TextView tvItemNameName;
    @BindView(R.id.tvCategoryName)
    TextView tvCategoryName;
    @BindView(R.id.tvItemDescription)
    TextView tvItemDescription;
    @BindView(R.id.tvItemPrice)
    TextView tvItemPrice;
    @BindView(R.id.tvPreparationTime)
    TextView tvPreparationTime;
    @BindView(R.id.tvPreparationTimeValue)
    TextView tvPreparationTimeValue;
    @BindView(R.id.tvQuantity)
    TextView tvQuantity;
    @BindView(R.id.btnAddToCart)
    Button btnAddToCart;
    @BindView(R.id.tvRelatedItems)
    TextView tvRelatedItems;
    @BindView(R.id.imArrowLeft)
    ImageView imArrowLeft;
    @BindView(R.id.rvRelatedItemsList)
    RecyclerView rvRelatedItemsList;
    @BindView(R.id.imArrowRight)
    ImageView imArrowRight;
    @BindView(R.id.tvTimeline)
    TextView tvTimeline;
    @BindView(R.id.rvTimeLine)
    RecyclerView rvTimeLine;
    @BindView(R.id.fragment_menu)
    LinearLayout fragmentMenu;
    @BindView(R.id.ivDecreaseQuantity)
    ImageView ivDecreaseQuantity;
    @BindView(R.id.ivIncreaseQuantity)
    ImageView ivIncreaseQuantity;
    @BindView(R.id.sdvItemImage)
    SimpleDraweeView sdvItemImage;
    @BindView(R.id.tvCartValue)
    TextView tvCartValue;
    @BindView(R.id.ivBlank)
    ImageView ivBlank;
    @BindView(R.id.llRelatedItemTitle)
    LinearLayout llRelatedItemTitle;
    @BindView(R.id.llRelatedItem)
    LinearLayout llRelatedItem;
    @BindView(R.id.svMenuDetail)
    ScrollView svMenuDetail;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;
    private String itemKey;
    private ItemDetailsResponse.Data itemData;
    MenuDetailRelatedItemAdapter menuDetailRelatedItemAdapter;
    MenuDetailTimeLineAdapter menuDetailTimeLineAdapter;
    List<ItemDetailsResponse.Item_rating_info> itemTimeline = new ArrayList<>();
    LinearLayoutManager horizontalLayoutManager;


    List<Ingredients> selectedIngredients = new ArrayList<>();
    List<VendorDetailsApiResponse.Item> itemCategories = new ArrayList<>();
    private String restaurant;
    private String vendorName;
    private String vendorKey;
    private String categoryString;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        ButterKnife.bind(this);

//        tvDeliveryTime.setText(LanguageConstants.deliverttimeText);
        tvPreparationTime.setText(LanguageConstants.preparationText);
        btnAddToCart.setText(LanguageConstants.add);
        tvRelatedItems.setText(LanguageConstants.relatedItems);
        tvTimeline.setText(LanguageConstants.timeline);

        setSupportActionBar(tbMenuDetail);
        tbMenuDetail.setTitle("");
        focusOnView();


        itemKey = getIntent().getExtras().getString("item_key");
        categoryString = getIntent().getExtras().getString("category");
        restaurant = getIntent().getExtras().getString("restaurant");

        Gson gson = new Gson();
        VendorDetailsApiResponse.Item_category category = gson.fromJson(categoryString, VendorDetailsApiResponse.Item_category.class);
        List<VendorDetailsApiResponse.Item> items = new ArrayList<>();
        for (int count = 0; count < category.getItems().size(); count++) {
            if (!itemKey.equals(category.getItems().get(count).getItem_key())) {
                items.add(category.getItems().get(count));
            }
        }

        if (items.size() == 0) {
            llRelatedItemTitle.setVisibility(View.GONE);
            llRelatedItem.setVisibility(View.GONE);
        } else {
            llRelatedItemTitle.setVisibility(View.VISIBLE);
            llRelatedItem.setVisibility(View.VISIBLE);
            menuDetailRelatedItemAdapter = new MenuDetailRelatedItemAdapter(MenuDetailActivity.this, items, categoryString, restaurant);
            rvRelatedItemsList.setAdapter(menuDetailRelatedItemAdapter);

        }

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));


         horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvRelatedItemsList.setLayoutManager(horizontalLayoutManager);

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        rvTimeLine.setLayoutManager(mlayoutManager);

        rvTimeLine.setLayoutManager(mlayoutManager);
        rvTimeLine.setAdapter(menuDetailTimeLineAdapter);
        imMenuDetailBack.setOnClickListener(this);
        imMenuDetailCart.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        imArrowLeft.setOnClickListener(this);
        imArrowRight.setOnClickListener(this);
        ivDecreaseQuantity.setOnClickListener(this);
        ivIncreaseQuantity.setOnClickListener(this);

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                imMenuDetailBack.setImageResource(R.drawable.ic_back_right);
                imArrowLeft.setImageResource(R.drawable.ic_next_arrow_right);
                imArrowRight.setImageResource(R.drawable.ic_next_arrow_left);
                ivDecreaseQuantity.setImageResource(R.drawable.ic_cart_add);
                ivIncreaseQuantity.setImageResource(R.drawable.ic_cart_sub);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }


        if (RealmLibrary.getInstance().getCartSize() == 0) {
            tvCartValue.setText("0");
        } else {
            tvCartValue.setText(String.valueOf(RealmLibrary.getInstance().getCartSize()));
        }

        RestaurantListApiResponse.Data.Vendor_list restaurantObject = gson.fromJson(restaurant, RestaurantListApiResponse.Data.Vendor_list.class);
        vendorKey = restaurantObject.getVendor_key();
        vendorName = restaurantObject.getVendor_name();


        CommonApiCalls.getInstance().getItemDetails(MenuDetailActivity.this, itemKey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object object) {
                ItemDetailsResponse body = (ItemDetailsResponse) object;
                itemData = body.getData();
                tvItemNameName.setText(itemData.getItem_details().getItem_name());

                if (itemData.getItem_details().getItem_image().size() > 0) {

                    String itemImage = Urls.BASE_URL_STAGING + itemData.getItem_details().getItem_image().get(0).getItem_image_path();
                    CommonFunctions.getInstance().loadImageByFresco(sdvItemImage, itemImage);
                } else {
                    sdvItemImage.setImageResource(R.drawable.no_image);
                }

                tvMenuDetailTitle.setText(itemData.getItem_details().getItem_name());
                tvItemPrice.setText(SessionManager.getInstance().getCurrencySymbol().concat(CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(itemData.getItem_details().getItem_price().toString()))));
                tvPreparationTimeValue.setText(String.valueOf(itemData.getItem_details().getPreparation_time()).concat(LanguageConstants.mins));
//
//  tvDeliveryTimeValue.setText(itemData.get);
//                ivItemImage.setImageResource(itemData.getItem_name());

                tvCategoryName.setText(itemData.getItem_details().getCategory_detail().getCategory_name());
                tvItemDescription.setText(itemData.getItem_details().getItem_description());
                itemTimeline = itemData.getItem_rating_info();
                if (itemTimeline.size() == 0) {
                    tvNoDataFound.setText(LanguageConstants.noReviewFound);
                    rvTimeLine.setVisibility(View.GONE);
                    tvNoDataFound.setVisibility(View.VISIBLE);
                } else {
                    rvTimeLine.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    menuDetailTimeLineAdapter = new MenuDetailTimeLineAdapter(MenuDetailActivity.this, itemTimeline);
                    rvTimeLine.setAdapter(menuDetailTimeLineAdapter);
                }


                tvItemPrice.setText(SessionManager.getInstance().getCurrencySymbol()+" "+(CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(itemData.getItem_details().getItem_price().toString()))));
            }

            @Override
            public void onFailure(String reason) {

            }
        });
    }

    private void focusOnView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                svMenuDetail.scrollTo(0, fragmentMenu.getTop());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view == imMenuDetailBack) {
            finish();
        } else if (view == imMenuDetailCart) {
            Bundle bundle = new Bundle();
            if (RealmLibrary.getInstance().getCartSize() == 0) {
                CommonFunctions.getInstance().ShowSnackBar(MenuDetailActivity.this, LanguageConstants.cartEmpty);
            } else {
                CartSummaryActivity.deliveryOptions.clear();
                CommonFunctions.getInstance().newIntent(MenuDetailActivity.this, CartSummaryActivity.class, bundle, false);
            }

        } else if (view == btnAddToCart) {
            if (itemData.getItem_details().getItem_ingredient().size() > 0) {
                final Dialog dialog = new Dialog(MenuDetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_menu_ingredient_popup);
                if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    }
                }
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.copyFrom(dialog.getWindow().getAttributes());
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                wlp.gravity = Gravity.CENTER;
                window.setAttributes(wlp);
                LinearLayout llIngredients = dialog.findViewById(R.id.llIngredients);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                ImageView ivClose = dialog.findViewById(R.id.ivClose);
                TextView tvMenuIngredientHeader = dialog.findViewById(R.id.tvMenuIngredientHeader);
                tvMenuIngredientHeader.setText(itemData.getItem_details().getItem_name());
                btnCancel.setText(LanguageConstants.cancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                Button btnDialogAddToCart = (Button) dialog.findViewById(R.id.btnDialogAddToCart);
                btnDialogAddToCart.setText(LanguageConstants.addtoCart);
                btnDialogAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RealmLibrary.getInstance().insertItem(MenuDetailActivity.this, itemKey, itemKey, itemData.getItem_details().getItem_name(),
                                itemData.getItem_details().getItem_image().toString(), String.valueOf(itemData.getItem_details().getItem_price()),
                                String.valueOf(itemData.getItem_details().getCategory_detail().getCategory_id()), "", vendorKey, "1", String.valueOf(itemData.getItem_details().getItem_price()),
                                String.valueOf(itemData.getItem_details().getItem_price()), ingredientsModels, "1",
                                "", tvQuantity.getText().toString(), vendorName);
                        CommonFunctions.getInstance().ShowSnackBar(MenuDetailActivity.this, LanguageConstants.itemAddedTocart);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                finish();
                            }
                        }, 1000);

                    }
                });
                for (int count = 0; count < itemData.getItem_details().getItem_ingredient().size(); count++) {
                    View view1 = getLayoutInflater().inflate(R.layout.inflate_ingrdients_group, null);
                    RecyclerView rvIngredientList = view1.findViewById(R.id.rvIngredientList);
                    RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(MenuDetailActivity.this);
                    rvIngredientList.setLayoutManager(mlayoutManager);
                    rvIngredientList.setItemAnimator(new DefaultItemAnimator());
                    /*if (itemData.getItem_details().getItem_ingredient().get(count).getIngredients().get(count).getDetails() == null){
                    rvIngredientList.setVisibility(View.GONE);
                    }else {*/
                        rvIngredientList.setVisibility(View.VISIBLE);
                        TextView tvIngredientGroup = view1.findViewById(R.id.tvIngredientGroup);
                        tvIngredientGroup.setText(itemData.getItem_details().getItem_ingredient().get(count).getGroup_name());
                        IngredientsListAdapter adapter = new IngredientsListAdapter(MenuDetailActivity.this,
                                itemData.getItem_details().getItem_ingredient().get(count).getIngredients(), selectedIngredients,
                                itemData.getItem_details().getItem_ingredient().get(count).getItem_ingredient_group_key(),
                                itemData.getItem_details().getItem_ingredient().get(count).getGroup_name(),
                                itemData.getItem_details().getItem_ingredient().get(count).getMinimum(),
                                itemData.getItem_details().getItem_ingredient().get(count).getMaximum(), itemData.getItem_details().getItem_id());
                        rvIngredientList.setAdapter(adapter);

                        llIngredients.addView(view1);
//                    }
                }
                dialog.show();
            } else {
                RealmLibrary.getInstance().insertItem(MenuDetailActivity.this, itemKey, itemKey, itemData.getItem_details().getItem_name(),
                        itemData.getItem_details().getItem_image().toString(), String.valueOf(itemData.getItem_details().getItem_price()),
                        String.valueOf(itemData.getItem_details().getCategory_detail().getCategory_id()), "", vendorKey, "1", String.valueOf(itemData.getItem_details().getItem_price()),
                        String.valueOf(itemData.getItem_details().getItem_price()), new ArrayList<Ingredients>(), "1",
                        "", tvQuantity.getText().toString(), vendorName);
                CommonFunctions.getInstance().ShowSnackBar(MenuDetailActivity.this, LanguageConstants.itemAddedTocart);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        } else if (view == imArrowLeft) {
            rvRelatedItemsList.getLayoutManager().scrollToPosition(horizontalLayoutManager.findFirstVisibleItemPosition() - 1);
        } else if (view == imArrowRight) {
            rvRelatedItemsList.getLayoutManager().scrollToPosition(horizontalLayoutManager.findLastVisibleItemPosition() + 1);
        } else if (view == ivIncreaseQuantity) {
            if (SessionManager.getInstance().getAppLanguage().equals("ar")) {
                String quantity = tvQuantity.getText().toString();
                if (Integer.parseInt(quantity) > 1) {
                    tvQuantity.setText(String.valueOf(Integer.parseInt(quantity) - 1));
                }
            } else {
                String quantity = tvQuantity.getText().toString();
                tvQuantity.setText(String.valueOf(Integer.parseInt(quantity) + 1));
            }
        } else if (view == ivDecreaseQuantity) {
            if (SessionManager.getInstance().getAppLanguage().equals("ar")) {
                String quantity = tvQuantity.getText().toString();
                tvQuantity.setText(String.valueOf(Integer.parseInt(quantity) + 1));
            } else {
                String quantity = tvQuantity.getText().toString();
                if (Integer.parseInt(quantity) > 1) {
                    tvQuantity.setText(String.valueOf(Integer.parseInt(quantity) - 1));
                }
            }
        }
    }

}
