package com.minwein.customer.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.adapter.CartSummaryItemAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.AddressListApiResponse;
import com.minwein.customer.api_model.CartVerifyApiResponse;
import com.minwein.customer.api_model.OrderConfirmedApiResponse;
import com.minwein.customer.app_interfaces.ChangeInCart;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.model.CartItems;
import com.minwein.customer.model.DeliveryOption;
import com.minwein.customer.realm_db.CartRealmModel;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.AppSharedValues;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class CartSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imCartSummaryBack)
    ImageView imCartSummaryBack;
    @BindView(R.id.tvCartSummaryTitle)
    TextView tvCartSummaryTitle;
    @BindView(R.id.tbCartSummary)
    Toolbar tbCartSummary;
    @BindView(R.id.rvCartSummaryList)
    RecyclerView rvCartSummaryList;
    @BindView(R.id.tvCartSummaryGrandTotal)
    TextView tvCartSummaryGrandTotal;
    @BindView(R.id.tvCartSummaryOnlinePayment)
    TextView tvCartSummaryOnlinePayment;
    @BindView(R.id.tvCartSummaryCash)
    TextView tvCartSummaryCash;
    @BindView(R.id.btnCheckOut)
    Button btnCheckOut;
    @BindView(R.id.tvGrandTotal)
    TextView tvGrandTotal;
    @BindView(R.id.tvOnlinePayment)
    TextView tvOnlinePayment;
    @BindView(R.id.tvCash)
    TextView tvCash;
    public static CartItems cartItems;
    CartVerifyApiResponse.Grand_total_array cartData;
    List<CartVerifyApiResponse.Cart> cartLists;
    RealmResults<CartRealmModel> cartList;
    @BindView(R.id.cbLoyaltyPoints)
    CheckBox cbLoyaltyPoints;
    @BindView(R.id.btnChangeAddress)
    Button btnChangeAddress;
    @BindView(R.id.tvAddress)
    TextView tvAddress;

    public static List<DeliveryOption> deliveryOptions = new ArrayList<>();
    @BindView(R.id.llOnline)
    LinearLayout llOnline;
    @BindView(R.id.llCash)
    LinearLayout llCash;
    public static AddressListApiResponse.Datum address;
    private FragmentManager fragmentManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_summary);
        ButterKnife.bind(this);
        tvCartSummaryTitle.setText(LanguageConstants.cartSummaryTitle);
        tvGrandTotal.setText(LanguageConstants.grandTotal);
        tvOnlinePayment.setText(LanguageConstants.online_payment);
        tvCash.setText(LanguageConstants.cash);
        cbLoyaltyPoints.setText(LanguageConstants.loyalty_points);
        btnChangeAddress.setText(LanguageConstants.choose);
        btnCheckOut.setText(LanguageConstants.Check_out);
        tvAddress.setHint(LanguageConstants.chooseAddress);
        setSupportActionBar(tbCartSummary);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        address = null;
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCartSummaryList.setLayoutManager(mlayoutManager);


        btnCheckOut.setOnClickListener(this);
        imCartSummaryBack.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        btnChangeAddress.setOnClickListener(this);

//        RealmResults<CartRealmModel> cart = RealmLibrary.getInstance().getCartList();
        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                imCartSummaryBack.setImageResource(R.drawable.ic_back_right);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }

        loadDeliveryArray();
        cartItems = new CartItems();

        fragmentManager = getSupportFragmentManager();

    }

    private void loadDeliveryArray() {
        final RealmResults<CartRealmModel> cartList = RealmLibrary.getInstance().getCartList();
        ArrayList<String> uniqueVendors = new ArrayList<>();
        for (int count = 0; count < cartList.size(); count++) {
            if (cartList.get(count) != null
                    && !uniqueVendors.contains(cartList.get(count).getVendor_key())) {
                uniqueVendors.add(cartList.get(count).getVendor_key());
            }
        }
        for (int count = 0; count < uniqueVendors.size(); count++) {
            DeliveryOption deliveryOption = new DeliveryOption();
            deliveryOption.setCouponCode("");
            deliveryOption.setDeliveryDate("");
            deliveryOption.setDeliveryType("1");
            deliveryOption.setDriverType("1");
            deliveryOption.setOrderMessage("");
            deliveryOption.setPaymentType("1");
            deliveryOption.setVendor_key(uniqueVendors.get(count));
            deliveryOptions.add(deliveryOption);
        }

    }

    private void makeCartJson() {
        final RealmResults<CartRealmModel> cartList = RealmLibrary.getInstance().getCartList();
        CartItems.User_detail userDetails = cartItems.getUser_details();
        userDetails.setAddress_key(address != null ? address.getUser_address_key() : "");
        userDetails.setUser_key(SessionManager.getInstance().getUserKey());


        if (address == null || address.getUser_address_key().isEmpty()) {
            userDetails.setLatitude(String.valueOf(AppSharedValues.latitude));
            userDetails.setLongitude(String.valueOf(AppSharedValues.longitude));
        } else {

            userDetails.setLatitude("");
            userDetails.setLongitude("");
        }
        userDetails.setLoyalty("0");

        ArrayList<String> uniqueVendors = new ArrayList<>();
        for (int count = 0; count < cartList.size(); count++) {
            if (cartList.get(count) != null
                    && !uniqueVendors.contains(cartList.get(count).getVendor_key())) {
                uniqueVendors.add(cartList.get(count).getVendor_key());
            }
        }

        cartItems.getCart().clear();
        List<CartItems.Cart> carts = cartItems.getCart();
        for (int count = 0; count < uniqueVendors.size(); count++) {
            RealmResults<CartRealmModel> cartList1 = RealmLibrary.getInstance().getCartList(uniqueVendors.get(count));
            List<CartItems.Item> items = new ArrayList<>();
            CartItems.Cart cart = new CartItems.Cart();
            cart.setVendor_key(uniqueVendors.get(count));


            for (int count1 = 0; count1 < cartList1.size(); count1++) {
                CartItems.Item item = new CartItems.Item();
                cart.setVendor_name(cartList1.get(count1).getVendorName());

                List<CartItems.Ingredient> ingredientsKey = new ArrayList<>();
                for (int count2 = 0; count2 < cartList1.get(count1).getIngredientsRealmModel().size(); count2++) {
                    CartItems.Ingredient ingredient = new CartItems.Ingredient();
                    ingredient.setIngredient_key(cartList1.get(count1).getIngredientsRealmModel().get(count2).getIngredientsKey());
                    ingredient.setGroup_key(cartList1.get(count1).getIngredientsRealmModel().get(count2).getGroupKey());
                    ingredientsKey.add(ingredient);
                }

                item.setIngredients(ingredientsKey);
                item.setItem_key(cartList1.get(count1).getItem_key());
                item.setQuantity(cartList1.get(count1).getQTY());
                items.add(item);
            }
            cart.setItems(items);

            CartItems.Delivery_option delivery_options = new CartItems.Delivery_option();

            for (int distance = 0; distance < deliveryOptions.size(); distance++) {
                if (deliveryOptions.get(distance).getVendor_key().equals(uniqueVendors.get(count))) {
                    DeliveryOption option = deliveryOptions.get(distance);

                    delivery_options.setCouponCode(option.getCouponCode());
                    delivery_options.setDeliveryDate(option.getDeliveryDate());
                    delivery_options.setDeliveryType(option.getDeliveryType());
                    delivery_options.setDriverType(option.getDriverType());
                    delivery_options.setOrderMessage(option.getOrderMessage());
                    delivery_options.setPaymentType(option.getPaymentType());

                }
            }
            cart.setDelivery_options(delivery_options);

            carts.add(cart);
        }

        verifyOrder();
    }

    private void verifyOrder() {
        Gson gson = new Gson();
        String input = gson.toJson(cartItems);
        System.out.println("Input ==> " + input);
        CommonApiCalls.getInstance().orderVerifyCart(CartSummaryActivity.this, input, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                CartVerifyApiResponse cartVerifyApiResponse = (CartVerifyApiResponse) body;
                cartData = cartVerifyApiResponse.getData().getGrand_total_array();

                if (cartVerifyApiResponse.getData().getCart().size() > 0) {
                    CartSummaryItemAdapter cartSummaryItemAdapter = new CartSummaryItemAdapter(
                            CartSummaryActivity.this, cartVerifyApiResponse.getData().getCart(), new ChangeInCart() {
                        @Override
                        public void onClick() {
                            makeCartJson();
                        }
                    });
                    rvCartSummaryList.setAdapter(cartSummaryItemAdapter);

                } else {
                    finish();
                }
                tvCartSummaryGrandTotal.setText(SessionManager.getInstance().getCurrencySymbol() + " "
                        + (CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(cartData.getGrandTotal()))));
                if (cartData.getGrandOnline() == 0) {
                    llOnline.setVisibility(View.GONE);
                } else {
                    llOnline.setVisibility(View.VISIBLE);
                    tvCartSummaryOnlinePayment.setText(SessionManager.getInstance().getCurrencySymbol() + " " + (CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(cartData.getGrandOnline()))));
                }
                if (cartData.getGrandCash() == 0) {
                    llCash.setVisibility(View.GONE);
                } else {
                    llCash.setVisibility(View.VISIBLE);
                    tvCartSummaryCash.setText(SessionManager.getInstance().getCurrencySymbol() + " " + (CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(cartData.getGrandCash()))));
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == imCartSummaryBack) {
            finish();
        } else if (view == btnCheckOut) {
            if (SessionManager.getInstance().getUserKey().isEmpty()) {
                CommonFunctions.getInstance().ShowSnackBar(CartSummaryActivity.this, LanguageConstants.pleaselogintocontinue);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        CommonFunctions.getInstance().newIntent(CartSummaryActivity.this, LoginActivity.class, Bundle.EMPTY,
                                false);
                    }
                }, 1000);
            } else if (tvAddress.getText().toString().isEmpty()) {
                CommonFunctions.getInstance().ShowSnackBar(CartSummaryActivity.this, LanguageConstants.selectaddress);
            } else {
                Boolean isSelected = true;
                for (int count = 0; count < deliveryOptions.size(); count++) {
                    if (!deliveryOptions.get(count).getDetailFilled()) {
                        isSelected = false;
                        break;
                    } else {

                    }
                }

                if (isSelected) {
                    checkoutOrder();
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(CartSummaryActivity.this, "Complete Delivery details for all items");
                }

            }
//            Bundle bundle = new Bundle();
//            CommonFunctions.getInstance().newIntent(CartSummaryActivity.this, CartCheckoutActivity.class, bundle, false);
        } else if (view == btnChangeAddress) {
            if (SessionManager.getInstance().getUserKey().isEmpty()) {
                CommonFunctions.getInstance().ShowSnackBar(CartSummaryActivity.this, LanguageConstants.pleaselogintocontinue);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        CommonFunctions.getInstance().newIntent(CartSummaryActivity.this, LoginActivity.class, Bundle.EMPTY,
                                false);
                    }
                }, 1000);
            }else {
            CommonFunctions.getInstance().newIntent(CartSummaryActivity.this, AddressCheckoutActivity.class, new Bundle(), false);
        }}
    }

    private void checkoutOrder() {
        final RealmResults<CartRealmModel> cartList = RealmLibrary.getInstance().getCartList();
        CartItems.User_detail userDetails = cartItems.getUser_details();
        userDetails.setAddress_key(address != null ? address.getUser_address_key() : "");
        userDetails.setUser_key(SessionManager.getInstance().getUserKey());
        if (address == null || address.getUser_address_key().isEmpty()) {
            userDetails.setLatitude(String.valueOf(AppSharedValues.latitude));
            userDetails.setLongitude(String.valueOf(AppSharedValues.longitude));
        } else {
            userDetails.setLatitude("");
            userDetails.setLongitude("");
        }
        userDetails.setLoyalty("0");


        ArrayList<String> uniqueVendors = new ArrayList<>();
        for (int count = 0; count < cartList.size(); count++) {
            if (cartList.get(count) != null
                    && !uniqueVendors.contains(cartList.get(count).getVendor_key())) {
                uniqueVendors.add(cartList.get(count).getVendor_key());
            }
        }

        cartItems.getCart().clear();
        List<CartItems.Cart> carts = cartItems.getCart();
        for (int count = 0; count < uniqueVendors.size(); count++) {
            RealmResults<CartRealmModel> cartList1 = RealmLibrary.getInstance().getCartList(uniqueVendors.get(count));
            List<CartItems.Item> items = new ArrayList<>();
            CartItems.Cart cart = new CartItems.Cart();
            cart.setVendor_key(uniqueVendors.get(count));


            for (int count1 = 0; count1 < cartList1.size(); count1++) {
                CartItems.Item item = new CartItems.Item();
                cart.setVendor_name(cartList1.get(count1).getVendorName());

                List<CartItems.Ingredient> ingredientsKey = new ArrayList<>();
                for (int count2 = 0; count2 < cartList1.get(count1).getIngredientsRealmModel().size(); count2++) {
                    CartItems.Ingredient ingredient = new CartItems.Ingredient();
                    ingredient.setIngredient_key(cartList1.get(count1).getIngredientsRealmModel().get(count2).getIngredientsKey());
                    ingredient.setGroup_key(cartList1.get(count1).getIngredientsRealmModel().get(count2).getGroupKey());
                    ingredientsKey.add(ingredient);
                }

                item.setIngredients(ingredientsKey);
                item.setItem_key(cartList1.get(count1).getItem_key());
                item.setQuantity(cartList1.get(count1).getQTY());
                items.add(item);
            }
            cart.setItems(items);

            CartItems.Delivery_option delivery_options = new CartItems.Delivery_option();

            for (int distance = 0; distance < deliveryOptions.size(); distance++) {
                if (deliveryOptions.get(distance).getVendor_key().equals(uniqueVendors.get(count))) {
                    DeliveryOption option = deliveryOptions.get(distance);

                    delivery_options.setCouponCode(option.getCouponCode());
                    delivery_options.setDeliveryDate(option.getDeliveryDate());
                    delivery_options.setDeliveryType(option.getDeliveryType());
                    delivery_options.setDriverType(option.getDriverType());
                    delivery_options.setOrderMessage(option.getOrderMessage());
                    delivery_options.setPaymentType(option.getPaymentType());

                }
            }
            cart.setDelivery_options(delivery_options);

            carts.add(cart);
        }

        Gson gson = new Gson();
        String input = gson.toJson(cartItems);
        System.out.println("Input ==> " + input);

        CommonApiCalls.getInstance().checkout(CartSummaryActivity.this, input, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                OrderConfirmedApiResponse orderConfirmedApiResponse = (OrderConfirmedApiResponse) body;
                if (orderConfirmedApiResponse.getHttpcode() == 405) {
                    CommonFunctions.getInstance().ShowSnackBar(CartSummaryActivity.this, orderConfirmedApiResponse.getError().size() > 0 ? orderConfirmedApiResponse.getError().get(0) : "Something went wrong");
                } else {
                    MyApplication.result(orderConfirmedApiResponse.getMsg());
                    String orderId = orderConfirmedApiResponse.getData().getOrder_number();
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId", orderId);
                    CommonFunctions.getInstance().newIntent(CartSummaryActivity.this, OrderConfirmationActivity.class, bundle, true);
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        tvAddress.setText(address != null ? address.getAddress_line1().trim() : "");

        makeCartJson();
    }

}
