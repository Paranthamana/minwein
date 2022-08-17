package com.minwein.customer.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.activity.CartSummaryActivity;
import com.minwein.customer.adapter.RestaurantRatingAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.api_model.RestaurantRatingApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantRatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantRatingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @BindView(R.id.rtbRating)
    RatingBar rtbRating;
    @BindView(R.id.tvRestaurantRatingValue)
    TextView tvRestaurantRatingValue;
    @BindView(R.id.rvRestaurantRatingList)
    RecyclerView rvRestaurantRatingList;
    Unbinder unbinder;
    @BindView(R.id.tvRestaurantRatingName)
    TextView tvRestaurantRatingName;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;
    @BindView(R.id.llRestaurantRating)
    LinearLayout llRestaurantRating;
    private List<RestaurantRatingApiResponse.Data.Vendor_rating> restaurantRatingList;
    private RestaurantRatingAdapter mAdapter;

    Activity activity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Toolbar RestaurantRatingToolbar;
    ImageView ivCart, ivFilter;

    private OnFragmentInteractionListener mListener;
    private String restaurant;
    private String vendorKey;
    private String vendorName;

    public RestaurantRatingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantRatingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantRatingFragment newInstance(String param1, String param2) {
        RestaurantRatingFragment fragment = new RestaurantRatingFragment();
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
            restaurant = getArguments().getString("restaurant");

            Gson gson = new Gson();
            RestaurantListApiResponse.Data.Vendor_list restaurantObject = gson.fromJson(restaurant, RestaurantListApiResponse.Data.Vendor_list.class);
            vendorKey = restaurantObject.getVendor_key();
            vendorName = restaurantObject.getVendor_name();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_rating, container, false);
        unbinder = ButterKnife.bind(this, view);


        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(activity);
        rvRestaurantRatingList.setLayoutManager(mlayoutManager);
        rvRestaurantRatingList.setItemAnimator(new DefaultItemAnimator());




//        RestaurantRatingToolbar = (Toolbar) activity.findViewById(R.id.tbRestaurantDetails);
//        ivCart = (ImageView) RestaurantRatingToolbar.findViewById(R.id.ivRestaurantDetailsCart);
//        ivFilter = (ImageView) RestaurantRatingToolbar.findViewById(R.id.imRestaurantDetailsRestaurantFilter);
//        ivFilter.setVisibility(View.GONE);
//        ivCart.setVisibility(View.VISIBLE);
//        ivCart.setOnClickListener(this);
//        AppCompatActivity activity = (AppCompatActivity) activity;
//        activity.setSupportActionBar(RestaurantRatingToolbar);

//        String vendorkey = "";
//        Bundle bundle = this.getArguments();
//        String strtext =
// bundle.getString("vendorkey");
        CommonApiCalls.getInstance().restaurantRating(activity, vendorKey,
                new CommonCallback.Listener() {
                    @Override
                    public void onSuccess(Object object) {
                        RestaurantRatingApiResponse body = (RestaurantRatingApiResponse) object;
                        restaurantRatingList = body.getData().getVendor_rating();

                        if (tvRestaurantRatingName!=null&&body.getData()!=null&&body.getData().getVendor_name() != null){
                            tvRestaurantRatingName.setText(body.getData().getVendor_name());
                        }
                        if ((body.getData().getVendor_rating_average() != null)) {
                            tvRestaurantRatingValue.setText("(" + (CommonFunctions.getInstance().roundOffDecimalValue(Double.parseDouble(body.getData().getVendor_rating_average()))) + ")");
                            rtbRating.setRating(Float.valueOf(body.getData().getVendor_rating_average()));
                        }

                        if (restaurantRatingList.size() == 0) {
                            tvNoDataFound.setText(LanguageConstants.noRatingFoundVendor);
                            rvRestaurantRatingList.setVisibility(View.GONE);
                            llRestaurantRating.setVisibility(View.GONE);
                            tvNoDataFound.setVisibility(View.VISIBLE);
                        } else {
                            llRestaurantRating.setVisibility(View.VISIBLE);
                            rvRestaurantRatingList.setVisibility(View.VISIBLE);
                            tvNoDataFound.setVisibility(View.GONE);
                            mAdapter = new RestaurantRatingAdapter(activity, restaurantRatingList);
                            rvRestaurantRatingList.setAdapter(mAdapter);
                        }
                    }

                    @Override
                    public void onFailure(String reason) {
                        tvNoDataFound.setVisibility(View.VISIBLE);
                        tvNoDataFound.setText(LanguageConstants.noRatingFoundVendor);
                        rvRestaurantRatingList.setVisibility(View.GONE);
                        llRestaurantRating.setVisibility(View.GONE);
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
        Activity a;

        if (context instanceof Activity) {
            activity = (Activity) context;
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
        if (CustomProgressDialog.getInstance() != null && CustomProgressDialog.getInstance().isShowing())
            CustomProgressDialog.getInstance().dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v == ivCart) {
            CartSummaryActivity.deliveryOptions.clear();
            CommonFunctions.getInstance().newIntent(activity, CartSummaryActivity.class, new Bundle(), false);
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
