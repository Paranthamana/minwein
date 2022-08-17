package com.minwein.customer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.minwein.customer.R;
import com.minwein.customer.activity.CartDetailsActivity;
import com.minwein.customer.activity.CartSummaryActivity;
import com.minwein.customer.activity.ForgotPasswordActivity;
import com.minwein.customer.activity.HomeActivity;
import com.minwein.customer.activity.SignUpActivity;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.LoginApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_SIGN_IN = 9001;
    @BindView(R.id.tbLogin)
    Toolbar tbLogin;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.etLoginEmail)
    EditText etLoginEmail;
    @BindView(R.id.etLoginPassword)
    EditText etLoginPassword;
    @BindView(R.id.tvLoginForgotPassword)
    TextView tvLoginForgotPassword;
    @BindView(R.id.tvLoginSignUp)
    TextView tvLoginSignUp;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.imLoginFacebook)
    LoginButton imLoginFacebook;
    @BindView(R.id.imLoginGoogle)
    SignInButton imLoginGoogle;
    @BindView(R.id.tvLoginOr)
    TextView tvLoginOr;
    @BindView(R.id.Login)
    FrameLayout Login;
    Unbinder unbinder;
    CallbackManager callbackManager;
    @BindView(R.id.tvnoAccount)
    TextView tvnoAccount;
    @BindView(R.id.imFacebook)
    ImageView imFacebook;
    @BindView(R.id.imGoogle)
    ImageView imGoogle;
    @BindView(R.id.tilLoginEmail)
    TextInputLayout tilLoginEmail;
    @BindView(R.id.tilLoginPassword)
    TextInputLayout tilLoginPassword;
    private FragmentManager fragmentManager;
    private GoogleSignInClient mGoogleSignInClient;
    Activity activity;
    DrawerLayout drawerLayout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        btnLogin.setOnClickListener(this);
        tbLogin.setTitle("");
        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbLogin);

        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }

        if (drawerLayout != null ){
            tbLogin.setNavigationIcon(R.drawable.ic_menu);
            {
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        activity, drawerLayout, tbLogin, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawerLayout.addDrawerListener(toggle);
                toggle.syncState();

            }
        }
        tvLoginSignUp.setOnClickListener(this);
        tvLoginSignUp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvLoginForgotPassword.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        imFacebook.setOnClickListener(this);
        imGoogle.setOnClickListener(this);
        tvLogin.setText(LanguageConstants.login);
        tilLoginEmail.setHint(LanguageConstants.Email+"/"+LanguageConstants.MobileNumber);
        tilLoginPassword.setHint(LanguageConstants.enterYourPassword);
        tvLoginForgotPassword.setText(LanguageConstants.forget_password);
        tvnoAccount.setText(LanguageConstants.dont_have_account_yet);
        tvLoginSignUp.setText(LanguageConstants.signUp);
        btnLogin.setText(LanguageConstants.login);
        tvLoginOr.setText(LanguageConstants.or);


        //google + login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
//        printHashKey(getContext());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        updateUI(account);
    }
    //    public static void printHashKey(Context pContext) {
//            try {
//                PackageInfo info = pContext.getPackageManager().getPackageInfo(
//                        "com.minwein.customer",
//                        PackageManager.GET_SIGNATURES);
//                for (Signature signature : info.signatures) {
//                    MessageDigest md = MessageDigest.getInstance("SHA");
//                    md.update(signature.toByteArray());
//                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                }
//            } catch (PackageManager.NameNotFoundException e) {
//
//            } catch (NoSuchAlgorithmException e) {
//            }
//    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


        if (context instanceof Activity){
            activity=(Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            if (etLoginEmail.getText().toString().trim().isEmpty()) {
                etLoginEmail.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.Email + LanguageConstants.CannotbeEmpty);
            }
            /*else if (!CommonFunctions.getInstance().isValidEmaillId(etLoginEmail.getText().toString().trim())) {
                CommonFunctions.getInstance().ValidField(activity, LanguageConstants.Email);
            }*/ else if (etLoginPassword.getText().toString().trim().isEmpty()) {
                etLoginPassword.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.Password + LanguageConstants.CannotbeEmpty);
            } else {
                String email = etLoginEmail.getText().toString();
                String password = etLoginPassword.getText().toString();
                CommonApiCalls.getInstance().login(activity, email, password, new CommonCallback.Listener() {
                    @Override
                    public void onSuccess(Object object) {
                        LoginApiResponse body = (LoginApiResponse) object;
                        MyApplication.result(body.getMessage());
                        LoginApiResponse.Data data = body.getData();
                        SessionManager.getInstance().userDetails(data.getUser_key(), data.getFirst_name(), data.getLast_name(),
                                data.getEmail(), data.getMobile_number(),
                                data.getGender(), data.getDob(), data.getNewsletters(),data.getLoyalty_points());
                        if (drawerLayout == null){
                            CommonFunctions.getInstance().newIntent(activity, CartSummaryActivity.class, Bundle.EMPTY, true);
                        }else {
                            CommonFunctions.getInstance().newIntent(activity, HomeActivity.class, Bundle.EMPTY, true);
                        }
                    }

                    @Override
                    public void onFailure(String reason) {

                    }
                });
            }

//            String email = etLoginEmail.getText().toString();
//            String password = etLoginPassword.getText().toString();
//
//            CommonApiCalls.getInstance().login(activity, email, password);
        }
        if (view == tvLoginSignUp) {
            Bundle bundle = new Bundle();
            CommonFunctions.getInstance().newIntent(getContext(), SignUpActivity.class, bundle, false);
//            getChildFragmentManager().beginTransaction() .replace(R.id.flContainer, new SignUpFragment(), "SignUp")
//                    .addToBackStack("login")
//                    .commit();
        }
        if (view == tvLoginForgotPassword) {
            Bundle bundle = new Bundle();
            CommonFunctions.getInstance().newIntent(getContext(), ForgotPasswordActivity.class, bundle, false);
        }
        if (view == imFacebook) {
            imLoginFacebook.performClick();
            imLoginFacebook.setReadPermissions("email");
            imLoginFacebook.setFragment(this);
            imLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    System.out.println("bvhgfhg");
                    GraphRequest request = GraphRequest.newMeRequest(
                            AccessToken.getCurrentAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(
                                        JSONObject object,
                                        GraphResponse response) {
                                    Log.e("name", response.toString());
                                    // Application code

                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,link,birthday,gender");
                    request.setParameters(parameters);
                    request.executeAsync();

                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });
        }
        if (view == imGoogle) {
            imLoginGoogle.performClick();
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e("nkjans", account.getEmail());

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ncjjcjd", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(Object o) {
        return;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
