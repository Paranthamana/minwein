package com.minwein.customer.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.minwein.customer.R;
import com.minwein.customer.activity.HomeActivity;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.EditProfileApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String APP_PREFERENCE_NAME = "Minwein_pref";
    @BindView(R.id.etGender)
    EditText etGender;
    @BindView(R.id.etDateofbirth)
    EditText etDateofbirth;
    Unbinder unbinder;
    @BindView(R.id.ll_DOB)
    LinearLayout DateofBirth;
    @BindView(R.id.tbGender)
    ToggleButton tbGender;
    @BindView(R.id.tvProfileTitle)
    TextView tvProfileTitle;
    @BindView(R.id.im_Profile_add)
    ImageView imProfileAdd;
    @BindView(R.id.tbSubscription)
    ToggleButton tbSubscription;
    @BindView(R.id.tb_profile)
    Toolbar tbProfile;
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etemail)
    EditText etemail;
    @BindView(R.id.etPhonenumber)
    EditText etPhonenumber;
    String gender_value = "";
    int subscribtion_value;
    @BindView(R.id.tvheader)
    TextView tvheader;
    @BindView(R.id.tvPrefixMobileNumber)
    TextView tvPrefixMobileNumber;
    @BindView(R.id.tvSubscription)
    TextView tvSubscription;
    @BindView(R.id.tvSubscriptionText)
    TextView tvSubscriptionText;
    @BindView(R.id.tilFirstName)
    TextInputLayout tilFirstName;
    @BindView(R.id.tilLastName)
    TextInputLayout tilLastName;
    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.tilPhoneNumber)
    TextInputLayout tilPhoneNumber;
    @BindView(R.id.tilGender)
    TextInputLayout tilGender;
    @BindView(R.id.tilDateOfBirth)
    TextInputLayout tilDateOfBirth;

    Activity activity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatePickerDialog datePickerDialog;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoyalityPointsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        //set toolbar appearance
        tvProfileTitle.setText(LanguageConstants.profile);
        tbProfile.setTitle("");
        imProfileAdd.setImageResource(R.drawable.ic_address_save);
        tvheader.setText(LanguageConstants.profileInformation);
        tilFirstName.setHint(LanguageConstants.firstName);
        tilLastName.setHint(LanguageConstants.lastname);
        tilEmail.setHint(LanguageConstants.Email);
        tilPhoneNumber.setHint(LanguageConstants.MobileNumber);
        tilGender.setHint(LanguageConstants.gender);
        tilDateOfBirth.setHint(LanguageConstants.dateofbirth);
        tvSubscription.setText(LanguageConstants.subscription);
        tvSubscriptionText.setText(LanguageConstants.subscriptionText);


        etFirstName.setText(SessionManager.getInstance().getUserFirstname());
        etLastName.setText(SessionManager.getInstance().getUserLastname());
        etemail.setText(SessionManager.getInstance().getUserEmail());
        etPhonenumber.setText(String.valueOf(SessionManager.getInstance().getUserMobilenumber()));
        if (SessionManager.getInstance().getUserGender().equals("0")) {
            tbGender.setChecked(true);
            etGender.setText(LanguageConstants.male);
        } else {
            tbGender.setChecked(false);
            etGender.setText(LanguageConstants.female);
        }
        if (SessionManager.getInstance().getUserNewsletter() == 1) {
            tbSubscription.setChecked(true);
        } else {
            tbSubscription.setChecked(false);
        }
        etDateofbirth.setText(SessionManager.getInstance().getUserDob());

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }


        //for crate home button
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbProfile);
        tbProfile.setNavigationIcon(R.drawable.ic_menu);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, tbProfile, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                if (activity != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }

            }

        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        DateofBirth.setOnClickListener(this);
        imProfileAdd.setOnClickListener(this);


        etDateofbirth.setOnClickListener(this);
        tbGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etGender.setText(LanguageConstants.male);
                    gender_value = "0";

                } else {
                    etGender.setText(LanguageConstants.female);
                    gender_value = "1";
                }

            }
        });
        tbSubscription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    subscribtion_value = 1;

                } else {
                    subscribtion_value = 0;
                }

            }
        });


        return view;
    }

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


        if (context instanceof Activity) {
            activity = (Activity) context;
        }
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        if (v == etDateofbirth) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH);// current day

            // date picker dialog
            datePickerDialog = new DatePickerDialog(activity,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            etDateofbirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }
        if (v == imProfileAdd) {
            if (etFirstName.getText().toString().trim().isEmpty()) {
                etFirstName.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.firstName + LanguageConstants.CannotbeEmpty);
            } else if (etLastName.getText().toString().trim().isEmpty()) {
                etLastName.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.lastname + LanguageConstants.CannotbeEmpty);
            } else if (etemail.getText().toString().trim().isEmpty()) {
                etemail.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.Email + LanguageConstants.CannotbeEmpty);
            } else if (!CommonFunctions.getInstance().isValidEmaillId(etemail.getText().toString().trim())) {
                CommonFunctions.getInstance().ValidField(activity, LanguageConstants.Email);
            } else if (etPhonenumber.getText().toString().trim().isEmpty()) {
                etPhonenumber.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.MobileNumber + LanguageConstants.CannotbeEmpty);
            } else if (!CommonFunctions.getInstance().isValidMobile(etPhonenumber.getText().toString())) {
                etPhonenumber.requestFocus();
                CommonFunctions.getInstance().ValidFieldMobile(activity, LanguageConstants.validMobileNumber);
            } else if (etGender.getText().toString().trim().isEmpty()) {
                etGender.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.gender + LanguageConstants.CannotbeEmpty);
            } else if (etDateofbirth.getText().toString().trim().isEmpty()) {
                etDateofbirth.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.dateofbirth + LanguageConstants.CannotbeEmpty);
            } else {
                String userKey = SessionManager.getInstance().getUserKey();
                String appLanguage = SessionManager.getInstance().getAppLanguage();
                String FirstName = etFirstName.getText().toString();
                String LastName = etLastName.getText().toString();
                String Email = etemail.getText().toString();
                String Phonenumber = etPhonenumber.getText().toString();
                String DateofBirth = etDateofbirth.getText().toString();
                String genderValue;
                Integer subscriptionValue;
                if (tbGender.isChecked()) {
                    genderValue = "0";
                } else {
                    genderValue = "1";
                }
                if (tbSubscription.isChecked()) {
                    subscriptionValue = 1;
                } else {
                    subscriptionValue = 0;
                }
                CommonApiCalls.getInstance().editProfile(activity, appLanguage, userKey, FirstName
                        , LastName, Email, Phonenumber, "", genderValue, DateofBirth,
                        subscriptionValue, new CommonCallback.Listener() {
                            @Override
                            public void onSuccess(Object body) {

                                EditProfileApiResponse editProfileApiResponse = (EditProfileApiResponse) body;
                                EditProfileApiResponse.Data data = editProfileApiResponse.getData();
                                SessionManager.getInstance().userDetails(data.getUserKey(), data.getFirstName(),
                                        data.getLastName(),
                                        data.getEmail(), data.getMobileNumber(), data.getGender(), data.getDob(),
                                        data.getNewsletters(), data.getLoyaltyPoints());
                                MyApplication.result(editProfileApiResponse.getMessage());
                                CommonFunctions.getInstance().newIntent(activity, HomeActivity.class, Bundle.EMPTY, false);
                            }

                            @Override
                            public void onFailure(String reason) {

                            }
                        });
            }


        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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