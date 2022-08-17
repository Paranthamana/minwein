package com.minwein.customer.activity;

import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.LoginApiResponse;
import com.minwein.customer.api_model.SignupApiResponse;
import com.minwein.customer.api_model.TermsAndConditionApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.fragment.LoginFragment;
import com.minwein.customer.fragment.RestaurantsListingFragment;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imSignUpBack)
    ImageView imSignUpBack;
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etSignUpEmail)
    EditText etSignUpEmail;
    @BindView(R.id.etSignUPMobileNumber)
    EditText etSignUPMobileNumber;
    @BindView(R.id.etSignUpPassword)
    EditText etSignUpPassword;
    @BindView(R.id.etSignUpConfirmPassword)
    EditText etSignUpConfirmPassword;
    @BindView(R.id.cbSignUp)
    CheckBox cbSignUp;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.imSignUpFacebook)
    ImageView imSignUpFacebook;
    @BindView(R.id.imSignUpGoogle)
    ImageView imSignUpGoogle;
    @BindView(R.id.tvSignUpOr)
    TextView tvSignUpOr;
    @BindView(R.id.SignUp)
    FrameLayout SignUp;
    @BindView(R.id.tvSignup)
    TextView tvSignup;
    @BindView(R.id.tvTermsAndCondition)
    TextView tvTermsAndCondition;
    @BindView(R.id.tilFirstName)
    TextInputLayout tilFirstName;
    @BindView(R.id.tilLastName)
    TextInputLayout tilLastName;
    @BindView(R.id.tilSignUpEmail)
    TextInputLayout tilSignUpEmail;
    @BindView(R.id.tilSignUPMobileNumber)
    TextInputLayout tilSignUPMobileNumber;
    @BindView(R.id.tilSignUpPassword)
    TextInputLayout tilSignUpPassword;
    @BindView(R.id.tilSignUpConfirmPassword)
    TextInputLayout tilSignUpConfirmPassword;
    @BindView(R.id.tvAccept)
    TextView tvAccept;

    FragmentManager fragmentManager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        imSignUpBack.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        tvSignup.setText(LanguageConstants.signUp);
        btnSignUp.setText(LanguageConstants.signUp);
//        etFirstName.setHint(LanguageConstants.firstName);
        tilFirstName.setHint(LanguageConstants.firstName);
        tilLastName.setHint(LanguageConstants.lastname);
        tilSignUpEmail.setHint(LanguageConstants.Email);
        tilSignUPMobileNumber.setHint(LanguageConstants.mobileNumber);
        tilSignUpPassword.setHint(LanguageConstants.Password);
        tilSignUpConfirmPassword.setHint(LanguageConstants.ConfirmPassword);
        tvTermsAndCondition.setText(LanguageConstants.termsAndCondition);
        tvTermsAndCondition.setOnClickListener(this);
        tvAccept.setText(LanguageConstants.accept);
        tvSignUpOr.setText(LanguageConstants.or);

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                imSignUpBack.setImageResource(R.drawable.ic_back_right);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == imSignUpBack) {
            finish();
        }
       else if (v == btnSignUp) {
            if (etFirstName.getText().toString().trim().isEmpty()) {
                etFirstName.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.firstName + LanguageConstants.CannotbeEmpty);
            } else if (etLastName.getText().toString().trim().isEmpty()) {
                etLastName.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.lastname + LanguageConstants.CannotbeEmpty);
            } else if (etSignUpEmail.getText().toString().trim().isEmpty()) {
                etSignUpEmail.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.Email + LanguageConstants.CannotbeEmpty);
            } else if (!CommonFunctions.getInstance().isValidEmaillId(etSignUpEmail.getText().toString().trim())) {
                CommonFunctions.getInstance().ValidField(this, LanguageConstants.Email);
            } else if (etSignUPMobileNumber.getText().toString().trim().isEmpty()) {
                etSignUPMobileNumber.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.MobileNumber + LanguageConstants.CannotbeEmpty);
            } else if (!CommonFunctions.getInstance().isValidMobile(etSignUPMobileNumber.getText().toString())) {
                etSignUPMobileNumber.requestFocus();
                CommonFunctions.getInstance().ValidFieldMobile(this, LanguageConstants.validMobileNumber);
            }else if (etSignUpPassword.getText().toString().trim().isEmpty()) {
                etSignUpPassword.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.Password + LanguageConstants.CannotbeEmpty);
            } else if (etSignUpConfirmPassword.getText().toString().trim().isEmpty()) {
                etSignUpConfirmPassword.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.ConfirmPassword + LanguageConstants.CannotbeEmpty);
            } else if (!etSignUpPassword.getText().toString().equals(etSignUpConfirmPassword.getText().toString())) {
                CommonFunctions.getInstance().PasswordMatch(this, LanguageConstants.ConfirmPassword, LanguageConstants.Didnotmatch);
            } else if (!cbSignUp.isChecked()) {
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.IAcceptTheTermsAndConditions + LanguageConstants.CannotbeEmpty);
            } else {
                String first_name = etFirstName.getText().toString();
                String last_name = etLastName.getText().toString();
                String email = etSignUpEmail.getText().toString();
                String password = etSignUpPassword.getText().toString();
                String confirm_password = etSignUpConfirmPassword.getText().toString();
                String mobilenumber = etSignUPMobileNumber.getText().toString();

                CommonApiCalls.getInstance().signUp(this, "1", first_name,
                        last_name, email,
                        password, confirm_password,
                        mobilenumber, "", "", SessionManager.getInstance().getDeviceToken(),
                        SessionManager.getInstance().getDeviceType(), new CommonCallback.Listener() {
                            @Override
                            public void onSuccess(Object body) {
                                SignupApiResponse signupApiResponse = (SignupApiResponse) body;
                                MyApplication.result(signupApiResponse.getMessage());
                                String email = etSignUpEmail.getText().toString();
                                String password = etSignUpConfirmPassword.getText().toString();
                                CommonApiCalls.getInstance().login(SignUpActivity.this, email, password, new CommonCallback.Listener() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        LoginApiResponse body = (LoginApiResponse) object;
                                        MyApplication.result(body.getMessage());
                                        LoginApiResponse.Data data = body.getData();
                                        SessionManager.getInstance().userDetails(data.getUser_key(), data.getFirst_name(), data.getLast_name(),
                                                data.getEmail(), data.getMobile_number(),
                                                data.getGender(), data.getDob(), data.getNewsletters(),data.getLoyalty_points());
                                        Bundle bundle = new Bundle();
                                        CommonFunctions.getInstance().newIntent(SignUpActivity.this, HomeActivity.class, bundle, true);
                                    }

                                    @Override
                                    public void onFailure(String reason) {

                                    }
                                });

                            }

                            @Override
                            public void onFailure(String reason) {

                            }
                        });
            }


        }
        else if(v==tvTermsAndCondition){
            Bundle bundle=new Bundle();
//            CommonFunctions.getInstance().newIntent(SignUpActivity.this,WebView.class,bundle,false);
            CommonApiCalls.getInstance().getTermsAndCondition(SignUpActivity.this, SessionManager.getInstance().getAppLanguage(),
                    new CommonCallback.Listener() {
                        @Override
                        public void onSuccess(Object body) {
                            TermsAndConditionApiResponse termsAndConditionApiResponse=(TermsAndConditionApiResponse)body;
                            final Dialog dialog = new Dialog(SignUpActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_terms_condition);
                            final TextView tvTermsAndConditionTitle,tvTermsAndConditionContent;
                            Button btClose;
                            tvTermsAndConditionTitle = (TextView) dialog.findViewById(R.id.tvTermsAndConditionTitle);
                            tvTermsAndConditionContent = (TextView) dialog.findViewById(R.id.tvTermsAndConditionContent);
                            btClose=(Button)dialog.findViewById(R.id.btClose);
                            tvTermsAndConditionTitle.setText(LanguageConstants.termsAndCondition);
                            btClose.setText(LanguageConstants.close);
                            tvTermsAndConditionContent.setText(Html.fromHtml(termsAndConditionApiResponse.getData().getContent().trim()).toString());
                            btClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.gravity = Gravity.CENTER;
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                            dialog.getWindow().setAttributes(lp);
                            dialog.show();
                        }

                        @Override
                        public void onFailure(String reason) {

                        }
                    });
        }
    }

}
