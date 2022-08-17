package com.minwein.customer.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minwein.customer.R;
import com.minwein.customer.activity.HomeActivity;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.ContactUsApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactUsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.imContactCall)
    ImageView imContactCall;
    @BindView(R.id.ll_phonecall)
    LinearLayout llPhonecall;
    @BindView(R.id.imContactMail)
    ImageView imContactMail;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    Unbinder unbinder;
    @BindView(R.id.tvContactus_Title)
    TextView tvContactusTitle;
    @BindView(R.id.tb_contactus)
    Toolbar tbContactus;
    @BindView(R.id.etContactUsFirstname)
    EditText etContactUsFirstname;
    @BindView(R.id.etContactUsLastname)
    EditText etContactUsLastname;
    @BindView(R.id.etContactUsEmail)
    EditText etContactUsEmail;
    @BindView(R.id.etContactUsPhonenumber)
    EditText etContactUsPhonenumber;
    @BindView(R.id.etContactUsComments)
    EditText etContactUsComments;
    @BindView(R.id.btContactUsSend)
    Button btContactUsSend;
    @BindView(R.id.tvPrefixMobileNumber)
    TextView tvPrefixMobileNumber;
    @BindView(R.id.tvContactusHeader)
    TextView tvContactusHeader;
    @BindView(R.id.tilContactUsFirstname)
    TextInputLayout tilContactUsFirstname;
    @BindView(R.id.tilContactUsLastname)
    TextInputLayout tilContactUsLastname;
    @BindView(R.id.tilContactUsEmail)
    TextInputLayout tilContactUsEmail;
    @BindView(R.id.tilContactUsPhonenumber)
    TextInputLayout tilContactUsPhonenumber;
    @BindView(R.id.tilContactUsComments)
    TextInputLayout tilContactUsComments;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tvEmailId)
    TextView tvEmailId;
    Activity activity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactUsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactUsFragment newInstance(String param1, String param2) {
        ContactUsFragment fragment = new ContactUsFragment();
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
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        tilContactUsFirstname.setHint(LanguageConstants.firstName);
        tilContactUsLastname.setHint(LanguageConstants.lastname);
        tilContactUsEmail.setHint(LanguageConstants.Email);
        tilContactUsPhonenumber.setHint(LanguageConstants.MobileNumber);
        tilContactUsComments.setHint(LanguageConstants.comments_and_quries);
        btContactUsSend.setText(LanguageConstants.sent);
        llPhonecall.setOnClickListener(this);
        llEmail.setOnClickListener(this);
        btContactUsSend.setOnClickListener(this);

        //set toolbar appearance
        tvContactusTitle.setText(LanguageConstants.contact_us);
        tbContactus.setTitle("");
        tvContactusHeader.setText(LanguageConstants.contact_us);
//        etContactUsFirstname.setHint(LanguageConstants.firstName);
//        etContactUsLastname.setHint(LanguageConstants.lastname);
//        etContactUsEmail.setHint(LanguageConstants.enter_your_emailId);
//        etContactUsPhonenumber.setHint(LanguageConstants.enter_your_phoneNumber);
//        etContactUsComments.setHint(LanguageConstants.comments_and_quries);
//        btContactUsSend.setText(LanguageConstants.sent);


        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbContactus);
        tbContactus.setNavigationIcon(R.drawable.ic_menu);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
               activity, drawerLayout, tbContactus, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


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
    public void onClick(View v) {
        if (v == llPhonecall) {
            String phone = "+9669874589568";
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        }
        if (v == llEmail) {
            String[] TO = {"nagaraj.r@technoduce.com"};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail...."));
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), "There is no email cilent installed.", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == btContactUsSend) {
            if (etContactUsFirstname.getText().toString().trim().isEmpty()) {
                etContactUsFirstname.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), LanguageConstants.firstName + LanguageConstants.CannotbeEmpty);
            } else if (etContactUsLastname.getText().toString().trim().isEmpty()) {
                etContactUsLastname.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), LanguageConstants.lastname + LanguageConstants.CannotbeEmpty);
            } else if (etContactUsEmail.getText().toString().trim().isEmpty()) {
                etContactUsEmail.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), LanguageConstants.Email + LanguageConstants.CannotbeEmpty);
            } else if (!CommonFunctions.getInstance().isValidEmaillId(etContactUsEmail.getText().toString().trim())) {
                CommonFunctions.getInstance().ValidField(getActivity(), LanguageConstants.Email);
            } else if (etContactUsPhonenumber.getText().toString().trim().isEmpty()) {
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), LanguageConstants.MobileNumber + LanguageConstants.CannotbeEmpty);
            }  else if (!CommonFunctions.getInstance().isValidMobile(etContactUsPhonenumber.getText().toString())) {
                etContactUsPhonenumber.requestFocus();
                CommonFunctions.getInstance().ValidFieldMobile(getActivity(), LanguageConstants.validMobileNumber);
            } else if (etContactUsComments.getText().toString().trim().isEmpty()) {
                etContactUsComments.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), LanguageConstants.comments_and_quries + LanguageConstants.CannotbeEmpty);
            } else {
                String contact_firstname = etContactUsFirstname.getText().toString();
                String contact_lastname = etContactUsLastname.getText().toString();
                String contact_email = etContactUsEmail.getText().toString();
                String contact_phone = etContactUsPhonenumber.getText().toString();
                String comment = etContactUsComments.getText().toString();

                CommonApiCalls.getInstance().contactUs(getActivity(), contact_firstname,
                        contact_lastname, contact_email, contact_phone, comment, new CommonCallback.Listener() {
                            @Override
                            public void onSuccess(Object body) {
                                ContactUsApiResponse data = (ContactUsApiResponse) body;
                                MyApplication.result(data.getMessage());
                                etContactUsFirstname.setText("");
                                etContactUsLastname.setText("");
                                etContactUsEmail.setText("");
                                etContactUsPhonenumber.setText("");
                                etContactUsComments.setText("");
                                CommonFunctions.getInstance().newIntent(activity, HomeActivity.class,Bundle.EMPTY,true);
                            }

                            @Override
                            public void onFailure(String reason) {

                            }
                        });
            }

        }

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
