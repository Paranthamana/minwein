package com.minwein.customer.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.ForgetPasswordApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imForgotPasswordBack)
    ImageView imForgotPasswordBack;
    @BindView(R.id.tbForgotPassword)
    Toolbar tbForgotPassword;
    @BindView(R.id.tvLogin2)
    TextView tvLogin2;
    @BindView(R.id.etForgotPasswordEmail)
    EditText etForgotPasswordEmail;
    @BindView(R.id.btForgotPasswordSubmit)
    Button btForgotPasswordSubmit;
    @BindView(R.id.tilForgotPasswordEmail)
    TextInputLayout tilForgotPasswordEmail;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        imForgotPasswordBack.setOnClickListener(this);
        btForgotPasswordSubmit.setOnClickListener(this);
        tilForgotPasswordEmail.setHint(LanguageConstants.enter_your_emailId);
        btForgotPasswordSubmit.setText(LanguageConstants.submit);
        tvLogin2.setText(LanguageConstants.forget_password);
        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                imForgotPasswordBack.setImageResource(R.drawable.ic_back_right);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        if (view == imForgotPasswordBack) {
            finish();
        }
        if (view == btForgotPasswordSubmit) {
            if (etForgotPasswordEmail.getText().toString().trim().isEmpty()) {
                etForgotPasswordEmail.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.Email + LanguageConstants.CannotbeEmpty);
            } else if (!CommonFunctions.getInstance().isValidEmaillId(etForgotPasswordEmail.getText().toString().trim())) {
                CommonFunctions.getInstance().ValidField(this, LanguageConstants.Email);
            } else {
                String email = etForgotPasswordEmail.getText().toString();
                CommonApiCalls.getInstance().forgetPassword(ForgotPasswordActivity.this, email, new CommonCallback.Listener() {
                    @Override
                    public void onSuccess(Object body) {
                        ForgetPasswordApiResponse forgetPasswordApiResponse = (ForgetPasswordApiResponse) body;
                        MyApplication.result(forgetPasswordApiResponse.getMessage());
                        finish();

                    }

                    @Override
                    public void onFailure(String reason) {

                    }
                });
            }
        }
    }

}
