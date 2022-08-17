package com.minwein.customer.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.adapter.CartDetailsItemAdapter;
import com.minwein.customer.app_interfaces.ChangeInCart;
import com.minwein.customer.realm_db.CartRealmModel;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class CartDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imCartDetailsBack)
    ImageView imCartDetailsBack;
    @BindView(R.id.tvCartDetailsTitle)
    TextView tvCartDetailsTitle;
    @BindView(R.id.tbCartDetails)
    Toolbar tbCartDetails;
    @BindView(R.id.rvCartDetailsList)
    RecyclerView rvCartDetailsList;
    @BindView(R.id.tvCartDetailTotal)
    TextView tvCartDetailTotal;
    @BindView(R.id.btCartDetailContune)
    Button btCartDetailContune;
    @BindView(R.id.tvTotalTitle)
    TextView tvTotalTitle;
    private String vendor_key;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        ButterKnife.bind(this);

        setSupportActionBar(tbCartDetails);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        tvTotalTitle.setText(LanguageConstants.total);
        btCartDetailContune.setText(LanguageConstants.Continue);
        vendor_key = getIntent().getExtras().getString("vendor_key");

        RealmResults<CartRealmModel> cartList = RealmLibrary.getInstance().getCartList(vendor_key);

        CartDetailsItemAdapter cartDetailsItemAdapter = new CartDetailsItemAdapter(CartDetailsActivity.this,cartList, new ChangeInCart() {
            @Override
            public void onClick() {
                tvCartDetailTotal.setText(" " + SessionManager.getInstance().getCurrencySymbol() + " " + total());
            }
        });

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCartDetailsList.setLayoutManager(mlayoutManager);
        rvCartDetailsList.setAdapter(cartDetailsItemAdapter);
        for (int count = 0;count<cartList.size(); count ++) {
            tvCartDetailsTitle.setText(cartList.get(count).getVendorName());
        }
        btCartDetailContune.setOnClickListener(this);
        imCartDetailsBack.setOnClickListener(this);

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                imCartDetailsBack.setImageResource(R.drawable.ic_back_right);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }

        tvCartDetailTotal.setText(" " + SessionManager.getInstance().getCurrencySymbol() + " " + total());

    }

    @Override
    public void onClick(View v) {
        if (v == imCartDetailsBack) {
            finish();
        }
        if (v == btCartDetailContune) {
            Bundle bundle = new Bundle();
            bundle.putString("vendor_key", vendor_key);
            CommonFunctions.getInstance().newIntent(CartDetailsActivity.this, CartCheckoutActivity.class, bundle, true);
        }
    }

    private String total(){
        RealmResults<CartRealmModel> itemList = RealmLibrary.getInstance().getCartList(vendor_key);

        Double totalPrice = 0.0;

        for (int count  = 0; count < itemList.size(); count++){
            CartRealmModel item = itemList.get(count);
            Double itemPrice = Double.parseDouble(item.getPrice());
            Double ingPrice =  0.0;
            for (int count1 = 0; count1 < item.getIngredientsRealmModel().size(); count1++){
                ingPrice = ingPrice +  Double.parseDouble(item.getIngredientsRealmModel().get(count1).getPrice());
            }

            totalPrice = totalPrice + ((itemPrice + ingPrice) * Integer.parseInt(item.getQTY()));

        }
        return String.valueOf(totalPrice);
    }
}
