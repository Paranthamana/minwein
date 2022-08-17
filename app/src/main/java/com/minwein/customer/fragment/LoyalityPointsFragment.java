package com.minwein.customer.fragment;

import android.app.Activity;
import android.content.Context;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.activity.HomeActivity;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.LoyaltyPointShareApiResponse;
import com.minwein.customer.api_model.LoyaltyPointsApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoyalityPointsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoyalityPointsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tvLoyaltyPointsTitle)
    TextView tvLoyaltyPointsTitle;
    @BindView(R.id.tb_loyalty_points)
    Toolbar tb_loyalty_points;
    @BindView(R.id.etEnterPoints)
    EditText etEnterPoints;
    @BindView(R.id.btShare)
    Button btLoyalityPointsShare;
    Unbinder unbinder;
    @BindView(R.id.llloyalitypoints)
    LinearLayout llloyalitypoints;
    @BindView(R.id.tvLoyaltyPoints)
    TextView tvLoyaltyPoints;
    @BindView(R.id.flLoyalImgTxt)
    FrameLayout flLoyalImgTxt;
    @BindView(R.id.btLoyalityPointsConvert)
    Button btLoyalityPointsConvert;
    @BindView(R.id.etmobilenumber)
    EditText etmobilenumber;
    @BindView(R.id.tvYourLoyaltyPoints)
    TextView tvYourLoyaltyPoints;
    @BindView(R.id.tvLoyaltyDescription)
    TextView tvLoyaltyDescription;
//    @BindView(R.id.tvloyaltyDetail)
//    TextView tvloyaltyDetail;
    @BindView(R.id.tvLoyaltyshare)
    TextView tvLoyaltyshare;
    @BindView(R.id.tvPrefixMobileNumber)
    TextView tvPrefixMobileNumber;
    @BindView(R.id.tilMobileNumber)
    TextInputLayout tilMobileNumber;
    @BindView(R.id.tilEnterPoints)
    TextInputLayout tilEnterPoints;
    Activity activity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoyalityPointsFragment() {
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
    public static LoyalityPointsFragment newInstance(String param1, String param2) {
        LoyalityPointsFragment fragment = new LoyalityPointsFragment();
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
        View view = inflater.inflate(R.layout.fragment_loyality_points, container, false);
        unbinder = ButterKnife.bind(this, view);
        //set toolbar appearance
        tvLoyaltyPointsTitle.setText(LanguageConstants.loyalty_points);
        tb_loyalty_points.setTitle("");
        btLoyalityPointsShare.setOnClickListener(this);
        tvYourLoyaltyPoints.setText(LanguageConstants.yourLoyaltyPoints);
        btLoyalityPointsConvert.setText(LanguageConstants.convert);
        tvLoyaltyshare.setText(LanguageConstants.sharefriends);
        tilMobileNumber.setHint(LanguageConstants.enter_your_phoneNumber);
        tilEnterPoints.setHint(LanguageConstants.enterPoints);
        btLoyalityPointsShare.setText(LanguageConstants.share);
        tvLoyaltyDescription.setText(LanguageConstants.desc);
//        tvloyaltyDetail.setText(LanguageConstants.loyaltyDetail);

        //for crate home button
        DrawerLayout drawerLayout = (DrawerLayout)activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity)activity;
        appCompatActivity.setSupportActionBar(tb_loyalty_points);
        tb_loyalty_points.setNavigationIcon(R.drawable.ic_menu);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
               activity, drawerLayout, tb_loyalty_points, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        String user_key = SessionManager.getInstance().getUserKey();
        CustomProgressDialog.getInstance().show(getActivity());
        CommonApiCalls.getInstance().loyaltyPoints(getActivity(), user_key, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                LoyaltyPointsApiResponse loyaltyPointsApiResponse = (LoyaltyPointsApiResponse) body;
                LoyaltyPointsApiResponse.Data data = loyaltyPointsApiResponse.getData();
                if (data.getLoyalty_points() == null) {
                    tvLoyaltyPoints.setText("0");
                    SessionManager.getInstance().setloyalitypoints(0);
                } else {
                    tvLoyaltyPoints.setText(data.getLoyalty_points().toString() + "");
                    SessionManager.getInstance().setloyalitypoints(data.getLoyalty_points());
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });
//        tvLoyaltyPoints.setText(SessionManager.getInstance().getLoyaltyPoints());
        btLoyalityPointsConvert.setOnClickListener(this);
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v == btLoyalityPointsShare) {
            String userkey = SessionManager.getInstance().getUserKey();
            if (etmobilenumber.getText().toString().trim().isEmpty()) {
                etmobilenumber.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), LanguageConstants.enter_your_phoneNumber);
            }else if (!CommonFunctions.getInstance().isValidMobile(etmobilenumber.getText().toString())) {
                    etmobilenumber.requestFocus();
                    CommonFunctions.getInstance().ValidFieldMobile(getActivity(), LanguageConstants.validMobileNumber);
            }else if (String.valueOf(SessionManager.getInstance().getUserMobilenumber()).equals(etmobilenumber.getText().toString())) {
                etmobilenumber.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), LanguageConstants.mobileNumbercannotbesame);
            } else if (etEnterPoints.getText().toString().trim().isEmpty()) {
                etEnterPoints.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), LanguageConstants.Enterloyaltypoints);
            }else if (etEnterPoints.getText().toString().equals("0")){
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), LanguageConstants.Enterloyaltypoints);
            }
            else {
                String mobileNumber = etmobilenumber.getText().toString();
                String points = etEnterPoints.getText().toString();
                CommonApiCalls.getInstance().loyaltyPointShare(getActivity(), userkey, mobileNumber, points, new CommonCallback.Listener() {
                    @Override
                    public void onSuccess(Object object) {
                        LoyaltyPointShareApiResponse data = (LoyaltyPointShareApiResponse) object;
                        MyApplication.result(data.getMessage());
                        SessionManager.getInstance().setloyalitypoints(data.getData().getLoyaltyPoints());
                        tvLoyaltyPoints.setText(SessionManager.getInstance().getLoyaltyPoints().toString());
                        etmobilenumber.setText("");
                        etEnterPoints.setText("");
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
