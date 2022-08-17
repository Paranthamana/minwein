package com.minwein.customer.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.CartVerifyApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.model.CartItems;
import com.minwein.customer.model.DeliveryOption;
import com.minwein.customer.realm_db.CartRealmModel;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.AppSharedValues;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class CartCheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imCartContinueBack)
    ImageView imCartContinueBack;
    @BindView(R.id.tvCartContinueTitle)
    TextView tvCartContinueTitle;
    @BindView(R.id.tbCartContinue)
    Toolbar tbCartContinue;
    @BindView(R.id.rbCartContinueDelivery)
    RadioButton rbCartContinueDelivery;
    @BindView(R.id.rbCartContinuePickup)
    RadioButton rbCartContinuePickup;
    @BindView(R.id.rgCartContinueDeliveryType)
    RadioGroup rgCartContinueDeliveryType;
    @BindView(R.id.etDatePicker)
    EditText etDatePicker;
    @BindView(R.id.etTimePicker)
    EditText etTimePicker;
    @BindView(R.id.rbCartContinueCompanyDriver)
    RadioButton rbCartContinueCompanyDriver;
    @BindView(R.id.rbCartContinueVendorDriver)
    RadioButton rbCartContinueVendorDriver;
    @BindView(R.id.rgCartContinueDriverType)
    RadioGroup rgCartContinueDriverType;
    @BindView(R.id.rbCartContinueOnline)
    RadioButton rbCartContinueOnline;
    @BindView(R.id.rbCartContinueCashOnDelivery)
    RadioButton rbCartContinueCashOnDelivery;
    @BindView(R.id.rgCartContinuePaymentType)
    RadioGroup rgCartContinuePaymentType;
    @BindView(R.id.etCartContinueVoucher)
    EditText etCartContinueVoucher;
    @BindView(R.id.btCartContinueApply)
    Button btCartContinueApply;
    @BindView(R.id.etSpecialRequest)
    EditText etSpecialRequest;
    @BindView(R.id.tvCartContinueSubTotal)
    TextView tvCartContinueSubTotal;
    @BindView(R.id.tvCartContinueServiceCharges)
    TextView tvCartContinueServiceCharges;
    @BindView(R.id.tvCartContinueVAT)
    TextView tvCartContinueVAT;
    @BindView(R.id.tvCartContinueDeliveryFee)
    TextView tvCartContinueDeliveryFee;
    @BindView(R.id.tvCartContinueTotal)
    TextView tvCartContinueTotal;
    @BindView(R.id.tvCartContinueFinalTotal)
    TextView tvCartContinueFinalTotal;
    @BindView(R.id.btCartContinueComplete)
    Button btCartContinueComplete;
    @BindView(R.id.tvDeliveryType)
    TextView tvDeliveryType;
    @BindView(R.id.tvDateTime)
    TextView tvDateTime;
    @BindView(R.id.tvDriverType)
    TextView tvDriverType;
    @BindView(R.id.tvPaymentType)
    TextView tvPaymentType;
    @BindView(R.id.tvDoYouHaveAVoucher)
    TextView tvDoYouHaveAVoucher;
    @BindView(R.id.tvSubTotal)
    TextView tvSubTotal;
    @BindView(R.id.tvServiceCharge)
    TextView tvServiceCharge;
    @BindView(R.id.tvVAT)
    TextView tvVAT;
    @BindView(R.id.tvDeliveryFee)
    TextView tvDeliveryFee;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvTotalTitle)
    TextView tvTotalTitle;
    private Calendar myCalendar = Calendar.getInstance();
    private CartItems cartItems;
    private String vendor_key;

    String deliveryOption = "1";
    String driverOption = "1";
    String paymentOption = "1";
    String selectedDate = "";
    String selectedTime = "";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);
        ButterKnife.bind(this);
        final Calendar myCalendar = Calendar.getInstance();
        setSupportActionBar(tbCartContinue);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        tvDoYouHaveAVoucher.setText(LanguageConstants.vouchers);
        btCartContinueApply.setText(LanguageConstants.apply);
        etSpecialRequest.setHint(LanguageConstants.addMessage);
        tvTotal.setText(LanguageConstants.total);
        tvTotalTitle.setText(LanguageConstants.total);
        btCartContinueComplete.setText(LanguageConstants.complete);
        btCartContinueApply.setOnClickListener(this);

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                imCartContinueBack.setImageResource(R.drawable.ic_back_right);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }


        imCartContinueBack.setOnClickListener(this);
        btCartContinueComplete.setOnClickListener(this);
        etDatePicker.setOnClickListener(this);
        etTimePicker.setOnClickListener(this);
        tvDeliveryType.setText(LanguageConstants.delivery_type);
        rbCartContinueDelivery.setText(LanguageConstants.delivery);
        tvDateTime.setText(LanguageConstants.dateAndTime);
        rbCartContinuePickup.setText(LanguageConstants.pickup);
        etDatePicker.setHint(LanguageConstants.selected_date);
        etTimePicker.setHint(LanguageConstants.selected_time);
        tvDriverType.setText(LanguageConstants.driver_type);
        rbCartContinueCompanyDriver.setText(LanguageConstants.company_driver);
        rbCartContinueVendorDriver.setText(LanguageConstants.vendor_driver);
        tvPaymentType.setText(LanguageConstants.payment_type);
        rbCartContinueOnline.setText(LanguageConstants.online);
        rbCartContinueCashOnDelivery.setText(LanguageConstants.cash_on_delivery);
        tvDoYouHaveAVoucher.setText(LanguageConstants.Do_you_have_a_voucher);
        btCartContinueApply.setText(LanguageConstants.apply);
        etSpecialRequest.setHint(LanguageConstants.add_message_or_special_instruction);
        btCartContinueComplete.setText(LanguageConstants.complete);
        tvSubTotal.setText(LanguageConstants.subTotal);


        vendor_key = getIntent().getExtras().getString("vendor_key");

        for (int count = 0; count < CartSummaryActivity.deliveryOptions.size(); count++) {
            if (vendor_key.equals(CartSummaryActivity.deliveryOptions.get(count).getVendor_key())) {
                DeliveryOption option = CartSummaryActivity.deliveryOptions.get(count);
                driverOption = option.getDriverType();
                deliveryOption = option.getDeliveryType();
                paymentOption = option.getPaymentType();
                selectedDate = option.getDeliveryDate();
                selectedTime = option.getDeliveryDate();
            }
        }

        cartItems = new CartItems();
        RealmResults<CartRealmModel> cartList = RealmLibrary.getInstance().getCartList(vendor_key);
        for (int count = 0; count< cartList.size();count ++) {
            tvCartContinueTitle.setText(cartList.get(count).getVendorName());
        }
        makeCartJson();
    }

    private void makeCartJson() {
        final RealmResults<CartRealmModel> cartList = RealmLibrary.getInstance().getCartList();
        CartItems.User_detail userDetails = cartItems.getUser_details();
        userDetails.setAddress_key("");
        userDetails.setUser_key(SessionManager.getInstance().getUserKey());
        userDetails.setLatitude(String.valueOf(AppSharedValues.latitude));
        userDetails.setLongitude(String.valueOf(AppSharedValues.longitude));


        ArrayList<String> uniqueVendors = new ArrayList<>();
        for (int count = 0; count < cartList.size(); count++) {
            if (cartList.get(count) != null
                    && !uniqueVendors.contains(cartList.get(count).getVendor_key()))
                uniqueVendors.add(cartList.get(count).getVendor_key());
        }


        List<CartItems.Cart> carts = cartItems.getCart();
        for (int count = 0; count < uniqueVendors.size(); count++) {
            RealmResults<CartRealmModel> cartList1 = RealmLibrary.getInstance().getCartList(uniqueVendors.get(count));
            List<CartItems.Item> items = new ArrayList<>();
            CartItems.Cart cart = new CartItems.Cart();
            cart.setVendor_key(uniqueVendors.get(count));


            for (int count1 = 0; count1 < cartList1.size(); count1++) {
                CartItems.Item item = new CartItems.Item();
                List<CartItems.Ingredient> ingredientsKey = new ArrayList<>();
                for (int count2 = 0; count2 < cartList1.get(count1).getIngredientsRealmModel().size(); count2++) {
                    CartItems.Ingredient ingredient = new CartItems.Ingredient();
                    ingredient.setIngredient_key(cartList1.get(count1).getIngredientsRealmModel().get(count2).getIngredientsKey());
                    ingredientsKey.add(ingredient);
                }

                item.setIngredients(ingredientsKey);
                item.setItem_key(cartList1.get(count1).getItem_key());
                item.setQuantity(cartList1.get(count1).getQTY());
                items.add(item);
            }
            cart.setItems(items);

            CartItems.Delivery_option delivery_options = new CartItems.Delivery_option();


            cart.setDelivery_options(delivery_options);

            carts.add(cart);
        }

        Gson gson = new Gson();
        String input = gson.toJson(cartItems);
        System.out.println("Input ==> " + input);
        CommonApiCalls.getInstance().orderVerifyCart(CartCheckoutActivity.this, input, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                CartVerifyApiResponse cartVerifyApiResponse = (CartVerifyApiResponse) body;
                List<CartVerifyApiResponse.Cart> cartLists = cartVerifyApiResponse.getData().getCart();
                for (int count = 0; count < cartLists.size(); count++) {
                    if (cartLists.get(count).getVendor_key().equals(vendor_key)) {
                        tvCartContinueSubTotal.setText(SessionManager.getInstance().getCurrencySymbol()
                                + " "+CommonFunctions.getInstance().roundOffDecimalValue(cartLists.get(count).getVendor_total_array().getVendorTotal()));
                        tvCartContinueTotal.setText(SessionManager.getInstance().getCurrencySymbol()
                                + " "+CommonFunctions.getInstance().roundOffDecimalValue(cartLists.get(count).getVendor_total_array().getVendorTotal()));
                        tvCartContinueServiceCharges.setText(SessionManager.getInstance().getCurrencySymbol() + " "+cartLists.get(count).getVendor_total_array().getService_charge());
                        tvCartContinueVAT.setText(SessionManager.getInstance().getCurrencySymbol() +" "+
                                cartLists.get(count).getVendor_total_array().getVAT_charge());
                        tvCartContinueDeliveryFee.setText(SessionManager.getInstance().getCurrencySymbol() +" "+
                                cartLists.get(count).getVendor_total_array().getDeliveryFee());
                        tvCartContinueFinalTotal.setText(SessionManager.getInstance().getCurrencySymbol() +" "+
                                cartLists.get(count).getVendor_total_array().getVendorTotal().toString());
                    }
                }

            }

            @Override
            public void onFailure(String reason) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == imCartContinueBack) {
            finish();
        }
        else if (v == btCartContinueComplete) {
            if (etDatePicker.getText().toString().isEmpty()){
                CommonFunctions.getInstance().ShowSnackBar(CartCheckoutActivity.this,LanguageConstants.pleaseselectdate);
            }else if (etTimePicker.getText().toString().isEmpty()){
                CommonFunctions.getInstance().ShowSnackBar(CartCheckoutActivity.this,LanguageConstants.pleaseselecttime);
            }else {
                int deliveryButton = rgCartContinueDeliveryType.getCheckedRadioButtonId();
                View deliveryView = rgCartContinueDeliveryType.findViewById(deliveryButton);
                int delivery = rgCartContinueDeliveryType.indexOfChild(deliveryView);

                System.out.println("delivery == >" + delivery);

                int driverTypeButton = rgCartContinueDriverType.getCheckedRadioButtonId();
                View driverView = rgCartContinueDriverType.findViewById(driverTypeButton);
                int driver = rgCartContinueDriverType.indexOfChild(driverView);

                System.out.println("driver == >" + driver);

                int paymentTypeButton = rgCartContinuePaymentType.getCheckedRadioButtonId();
                View paymentView = rgCartContinuePaymentType.findViewById(paymentTypeButton);
                int payment = rgCartContinuePaymentType.indexOfChild(paymentView);

                System.out.println("payment == >" + payment);


                for (int count = 0; count < CartSummaryActivity.deliveryOptions.size(); count++) {
                    if (vendor_key.equals(CartSummaryActivity.deliveryOptions.get(count).getVendor_key())) {
                        DeliveryOption option = CartSummaryActivity.deliveryOptions.get(count);

                        option.setPaymentType(String.valueOf(payment + 1));
                        option.setOrderMessage(etSpecialRequest.getText().toString());
                        option.setDriverType(String.valueOf(driver + 1));
                        option.setDeliveryType(String.valueOf(delivery + 1));
                        option.setDeliveryDate(CommonFunctions.getInstance().formatDate(etDatePicker.getText().toString(), "dd/MMM/yyyy", "MMM dd,yyyy") +
                                " " + CommonFunctions.getInstance().formatDate(etTimePicker.getText().toString(), "HH:mm", "HH:mm a"));
                        option.setCouponCode(etCartContinueVoucher.getText().toString());
                        option.setDetailFilled(true);
                    }
                }
                finish();
            }
//            Bundle bundle = new Bundle();
//            CommonFunctions.getInstance().newIntent(CartCheckoutActivity.this, CartSummaryActivity.class, bundle, true);
        }
        else if (v == etDatePicker) {
            Calendar mcurrentDate = Calendar.getInstance();
            int year = mcurrentDate.get(Calendar.YEAR);
            int month = mcurrentDate.get(Calendar.MONTH);
            int dayofmonth = mcurrentDate.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(CartCheckoutActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                }
            },year,month,dayofmonth);
            datePickerDialog.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
            datePickerDialog.show();
            updateLabel();
        }
        else if (v == etTimePicker) {
            final Calendar mcurrentTime = Calendar.getInstance();
            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(CartCheckoutActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    if (selectedHour <= mcurrentTime.get(Calendar.HOUR)
                            && selectedMinute <= mcurrentTime.get(Calendar.MINUTE)) {
                        Toast.makeText(CartCheckoutActivity.this, "Set time at least 10 minutes from now", Toast.LENGTH_LONG).show();
                    } else {
                        etTimePicker.setText(selectedHour + ":" + selectedMinute);
                    }

                }
            }, hour, minute, false);
            mTimePicker.show();
        }

        else if (v == btCartContinueApply){
            if(etCartContinueVoucher.getText().toString().isEmpty()){
                CommonFunctions.getInstance().ShowSnackBar(CartCheckoutActivity.this,LanguageConstants.voucher+LanguageConstants.CannotbeEmpty);
            }
        }
    }
        private void updateLabel() {
            String myFormat = "dd/MMM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            etDatePicker.setText(sdf.format(myCalendar.getTime()));
        }
    };
