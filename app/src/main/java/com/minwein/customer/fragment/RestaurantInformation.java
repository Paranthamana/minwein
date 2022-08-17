package com.minwein.customer.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.activity.CartSummaryActivity;
import com.minwein.customer.adapter.DeliveryTimeslotAdapter;
import com.minwein.customer.adapter.PickTimeslotAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.AddFavoriteApiResponse;
import com.minwein.customer.api_model.DeleteFavoritesApiResponse;
import com.minwein.customer.api_model.RestaurantInformationApiResponse;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantInformation extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.imInformationRestaurantImage)
    SimpleDraweeView imInformationRestaurantImage;
    @BindView(R.id.tvInformationRestaurantName)
    TextView tvInformationRestaurantName;

    @BindView(R.id.rtbInformationRestaurantRating)
    RatingBar rtbInformationRestaurantRating;
    @BindView(R.id.tvInformationRestaurantRatingValue)
    TextView tvInformationRestaurantRatingValue;
    @BindView(R.id.llDeliveryHours)
    LinearLayout llDeliveryHours;
    Unbinder unbinder;
    @BindView(R.id.tvdeliveryhours)
    TextView tvdeliveryhours;
    @BindView(R.id.tvpickupHours)
    TextView tvpickupHours;
    @BindView(R.id.tvpickupTime)
    TextView tvpickupTime;
    @BindView(R.id.tvDeliveryTime)
    TextView tvDeliveryTime;
    @BindView(R.id.tvDeliveryFee)
    TextView tvDeliveryFee;
    @BindView(R.id.tvPaymentOption)
    TextView tvPaymentOption;
    @BindView(R.id.tvAddressTitle)
    TextView tvAddressTitle;
    @BindView(R.id.rvDeliveryHours)
    RecyclerView rvDeliveryHours;
    @BindView(R.id.rvPickupHours)
    RecyclerView rvPickupHours;
    Toolbar RestaurantInformationToolbar;
    ImageView ivCart, ivFilter;
    @BindView(R.id.tvInformationRestaurantCuisines)
    TextView tvInformationRestaurantCuisines;
    @BindView(R.id.tvInformationRestaurantDescribtion)
    TextView tvInformationRestaurantDescribtion;
    @BindView(R.id.btnRestaurantStatus)
    Button btnRestaurantStatus;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvPickupendtimeValue)
    TextView tvPickupendtimeValue;
    @BindView(R.id.tvDeliverytimeValue)
    TextView tvDeliverytimeValue;
    @BindView(R.id.tvDeliveryFeeValue)
    TextView tvDeliveryFeeValue;
    //    @BindView(R.id.tvPaymentOptionType)
//    TextView tvPaymentOptionType;
    Activity activity;
    @BindView(R.id.flRestaurantInfo)
    FrameLayout flRestaurantInfo;
    @BindView(R.id.svRestaurantInfo)
    ScrollView svRestaurantInfo;
    @BindView(R.id.llRestaurantInfo)
    LinearLayout llRestaurantInfo;
    @BindView(R.id.ivVendorStatus)
    ImageView ivVendorStatus;
    @BindView(R.id.tvVendorStatus)
    TextView tvVendorStatus;
    @BindView(R.id.llStatus)
    LinearLayout llStatus;
    @BindView(R.id.rbFavoriteSelector)
    ToggleButton rbFavoriteSelector;
    @BindView(R.id.tvfavoritesCount)
    TextView tvfavoritesCount;
    @BindView(R.id.tvMinOrder)
    TextView tvMinOrder;
    @BindView(R.id.tvMinOrderValue)
    TextView tvMinOrderValue;
    @BindView(R.id.llSimage)
    LinearLayout llSimage;
    @BindView(R.id.ivPaymentOption)
    ImageView ivPaymentOption;
    @BindView(R.id.ivPaymentOption2)
    ImageView ivPaymentOption2;
    @BindView(R.id.tvAvaRating)
    TextView tvAvaRating;
    @BindView(R.id.tvVendorId)
    TextView tvVendorId;

    private DeliveryTimeslotAdapter deliveryTimeslotAdapter;
    private PickTimeslotAdapter pickTimeslotAdapter;
    String restaurant;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<RestaurantInformationApiResponse.DeliveryTimeslot> deliveryTimslotList;
    private List<RestaurantInformationApiResponse.PickupTimeslot> pickupTimslotList;

    public RestaurantInformation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantInformation.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantInformation newInstance(String param1, String param2) {
        RestaurantInformation fragment = new RestaurantInformation();
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
        View view = inflater.inflate(R.layout.fragment_restaurant_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        btnRestaurantStatus.setText(LanguageConstants.open);
        tvdeliveryhours.setText(LanguageConstants.Delivery_hours);
        tvpickupHours.setText(LanguageConstants.pickup_hours);
        tvpickupTime.setText(LanguageConstants.pickup_time);
        tvDeliveryTime.setText(LanguageConstants.delivery_time);
        tvDeliveryFee.setText(LanguageConstants.delivery_fee);
        tvPaymentOption.setText(LanguageConstants.paymetMethod);
        tvAddressTitle.setText(LanguageConstants.address);
        svRestaurantInfo.scrollTo(0, 0);
        focusOnView();

        restaurant = getArguments().getString("restaurant");
        Gson gson = new Gson();
        RestaurantListApiResponse.Data.Vendor_list restaurantObject = gson.fromJson(restaurant, RestaurantListApiResponse.Data.Vendor_list.class);
        String vendorKey = restaurantObject.getVendor_key();

        /*if(SessionManager.getInstance().getUserKey().isEmpty()){
            rbFavoriteSelector.setChecked(false);
        }else {
            if (restaurantDetails.getUser_fav_check().equals("0")) {
                rbFavoriteSelector.setChecked(false);
            } else {
                rbFavoriteSelector.setChecked(true);
            }
        }*/

        rbFavoriteSelector.setOnClickListener(this);
//        RestaurantInformationToolbar=(Toolbar)activity.findViewById(R.id.tbRestaurantDetails);
//        ivCart=(ImageView)RestaurantInformationToolbar.findViewById(R.id.ivRestaurantDetailsCart);
//        ivFilter=(ImageView)RestaurantInformationToolbar.findViewById(R.id.imRestaurantDetailsRestaurantFilter);
//        ivFilter.setVisibility(View.GONE);
//        ivCart.setVisibility(View.VISIBLE);
//        ivCart.setOnClickListener(this);
//        AppCompatActivity activity = (AppCompatActivity) activity;
//        activity.setSupportActionBar(RestaurantInformationToolbar);

        RecyclerView.LayoutManager mlayoutManagerDelivery = new LinearLayoutManager(activity);
        rvDeliveryHours.setLayoutManager(mlayoutManagerDelivery);
        rvDeliveryHours.setItemAnimator(new DefaultItemAnimator());
        rvDeliveryHours.setAdapter(deliveryTimeslotAdapter);

        RecyclerView.LayoutManager mlayoutManagerPickup = new LinearLayoutManager(activity);
        rvPickupHours.setLayoutManager(mlayoutManagerPickup);
        rvPickupHours.setItemAnimator(new DefaultItemAnimator());
        rvPickupHours.setAdapter(pickTimeslotAdapter);
        CommonApiCalls.getInstance().RestaurantInformation(activity, vendorKey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object object) {
                RestaurantInformationApiResponse data = (RestaurantInformationApiResponse) object;
                deliveryTimslotList = data.getData().getDeliveryTimeslot();
                pickupTimslotList = data.getData().getPickupTimeslot();
                tvInformationRestaurantName.setText(data.getData().getVendorDetails().getVendorName());
                tvInformationRestaurantCuisines.setText(data.getData().getVendorDetails().getCuisineName());
                tvInformationRestaurantDescribtion.setText(data.getData().getVendorDetails().getVendorDescription());
                if (data.getData().getVendorDetails().getRating_average() == null){
                    tvAvaRating.setText(("0.0"));
                }else {
                    tvAvaRating.setText(data.getData().getVendorDetails().getRating_average());
                }
                tvVendorId.setText(data.getData().getVendorDetails().getVendorId());
                if (data.getData().getVendorDetails().getRating_average() == null) {
                    rtbInformationRestaurantRating.setRating(0);
                    tvInformationRestaurantRatingValue.setText("(" + ("0") + ")");
                } else {
                    rtbInformationRestaurantRating.setRating(Float.valueOf(data.getData().getVendorDetails().getRating_average()));
                    tvInformationRestaurantRatingValue.setText("(" + (data.getData().getVendorDetails().getRating_average()) + ")");
                }
                tvPickupendtimeValue.setText(data.getData().getVendorDetails().getPickupTime() + (LanguageConstants.mins));
                tvDeliverytimeValue.setText(data.getData().getVendorDetails().getDeliveryTime() + (LanguageConstants.mins));
                tvDeliveryFeeValue.setText((SessionManager.getInstance().getCurrencySymbol()) + " " + data.getData().getVendorDetails().getDeliveryFee());
//                tvPaymentOptionType.setText(data.getData().getVendorDetails().getPaymentOption());
                String vendorImage = Urls.BASE_URL_STAGING + data.getData().getVendorDetails().getVendorImage();
                CommonFunctions.getInstance().loadImageByFresco(imInformationRestaurantImage, vendorImage);
                tvMinOrder.setText(LanguageConstants.MinOrder);
                tvMinOrderValue.setText("SAR 0.00");

                String addressString = "";
                if (data.getData().getVendorDetails().getVendorAddressLine1() != null && !data.getData().getVendorDetails().getVendorAddressLine1().equals("")) {
                    addressString = addressString + data.getData().getVendorDetails().getVendorAddressLine1() + ",";
                }
                if (data.getData().getVendorDetails().getArea() != null && !data.getData().getVendorDetails().getArea().equals("")) {
                    addressString = addressString + data.getData().getVendorDetails().getArea() + ",";
                }
                if (data.getData().getVendorDetails().getCityName() != null && !data.getData().getVendorDetails().getCityName().equals("")) {
                    addressString = addressString + data.getData().getVendorDetails().getCityName() + ",";
                }
                if (data.getData().getVendorDetails().getCountryName() != null && !data.getData().getVendorDetails().getCountryName().equals("")) {
                    addressString = addressString + data.getData().getVendorDetails().getCountryName() + ",";
                }
                if (data.getData().getVendorDetails().getContactNumber1() != null && !data.getData().getVendorDetails().getContactNumber1().equals("")) {
                    addressString = addressString + data.getData().getVendorDetails().getContactNumber1() + ".";
                }

                tvAddress.setText(addressString);

                pickTimeslotAdapter = new PickTimeslotAdapter(activity, pickupTimslotList);
                rvPickupHours.setAdapter(pickTimeslotAdapter);
                Collections.sort(pickupTimslotList, new Comparator<RestaurantInformationApiResponse.PickupTimeslot>() {
                    @Override
                    public int compare(RestaurantInformationApiResponse.PickupTimeslot lhs, RestaurantInformationApiResponse.PickupTimeslot rhs) {
                        return lhs.getDayId().compareTo(rhs.getDayId());

                    }
                });


                Collections.sort(deliveryTimslotList, new Comparator<RestaurantInformationApiResponse.DeliveryTimeslot>() {
                    @Override
                    public int compare(RestaurantInformationApiResponse.DeliveryTimeslot lhs, RestaurantInformationApiResponse.DeliveryTimeslot rhs) {
                        return lhs.getDayId().compareTo(rhs.getDayId());

                    }
                });
                deliveryTimeslotAdapter = new DeliveryTimeslotAdapter(activity, deliveryTimslotList);
                rvDeliveryHours.setAdapter(deliveryTimeslotAdapter);

                String restaurentStatus;
                if (data.getData().getVendorDetails().getTimeslotAvailabilityStatus().equals("0")) {
                    llSimage.setBackgroundResource(R.drawable.xml_imagebg_rounded_unselect);
                    llStatus.setBackgroundResource(R.drawable.xml_rounded_button_red);
                    ivVendorStatus.setImageResource(R.drawable.ic_closed);
                    tvVendorStatus.setText(LanguageConstants.closed);
                    restaurentStatus = LanguageConstants.open;
                    btnRestaurantStatus.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else if (data.getData().getVendorDetails().getTimeslotAvailabilityStatus().equals("1")) {
                    llSimage.setBackgroundResource(R.drawable.xml_imagebg_rounded_rectangle);
                    llStatus.setBackgroundResource(R.drawable.xml_rounded_button_green);
                    ivVendorStatus.setImageResource(R.drawable.ic_open);
                    tvVendorStatus.setText(LanguageConstants.open);
                    restaurentStatus = LanguageConstants.closed;
                    btnRestaurantStatus.setBackgroundColor(getResources().getColor(R.color.light_red));
                } else {
                    restaurentStatus = LanguageConstants.busy;
                }
                btnRestaurantStatus.setText(restaurentStatus);

                String paymentOption;
                switch (data.getData().getVendorDetails().getPaymentOption()) {
                    case "1":
                        ivPaymentOption.setVisibility(View.VISIBLE);
                        ivPaymentOption2.setVisibility(View.GONE);
                        paymentOption = LanguageConstants.online;
                        break;
                    case "2":
                        ivPaymentOption.setVisibility(View.GONE);
                        ivPaymentOption2.setVisibility(View.VISIBLE);
                        paymentOption = LanguageConstants.cash;
                        break;
                    default:
                        ivPaymentOption.setVisibility(View.VISIBLE);
                        ivPaymentOption2.setVisibility(View.VISIBLE);
                        paymentOption = LanguageConstants.both;
                        break;
                }

//                tvPaymentOptionType.setText(paymentOption);
            }

            @Override
            public void onFailure(String reason) {

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

    private void focusOnView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                svRestaurantInfo.scrollTo(0, llRestaurantInfo.getTop());
            }
        });
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (CustomProgressDialog.getInstance() != null && CustomProgressDialog.getInstance().isShowing())
            CustomProgressDialog.getInstance().dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
//        RestaurantInformationToolbar=(Toolbar)activity.findViewById(R.id.tbRestaurantDetails);
//        ivCart=(ImageView)RestaurantInformationToolbar.findViewById(R.id.ivRestaurantDetailsCart);
//        ivFilter=(ImageView)RestaurantInformationToolbar.findViewById(R.id.imRestaurantDetailsRestaurantFilter);
//        ivFilter.setVisibility(View.GONE);
//        ivCart.setVisibility(View.VISIBLE);
//        ivCart.setOnClickListener(this);
//        AppCompatActivity activity = (AppCompatActivity) activity;
//        activity.setSupportActionBar(RestaurantInformationToolbar);
    }

    @Override
    public void onClick(View v) {
        if (v == ivCart) {
            CartSummaryActivity.deliveryOptions.clear();
            CommonFunctions.getInstance().newIntent(activity, CartSummaryActivity.class, Bundle.EMPTY, false);
        }else if (v == rbFavoriteSelector){
           /* if(!SessionManager.getInstance().getUserKey().isEmpty()) {
                if (restaurantDetails.getUser_fav_check().equals("0")) {
                    String user_key = SessionManager.getInstance().getUserKey();
                    String vendor_type = restaurantDetails.getVendor_type();
                    String vendor_key = restaurantDetails.getVendor_key();
                    CommonApiCalls.getInstance().favouriteAdd(mcontext, user_key, vendor_key, vendor_type, new CommonCallback.Listener() {
                        @Override
                        public void onSuccess(Object object) {
                            AddFavoriteApiResponse data = (AddFavoriteApiResponse) object;
                            holder.favorite_add.setChecked(true);
                            restaurantDetails.setUser_fav_check("1");
                            restaurantDetails.setFavorites_count(data.getData().getVendor_fav_count());
                            holder.tvFavoritesCounts.setText("("+(data.getData().getVendor_fav_count())+")");
                            MyApplication.result(data.getMessage());
                            notifyDataSetChanged();

                        }

                        @Override
                        public void onFailure(String reason) {

                        }
                    });
                } else {
                    String userKey = SessionManager.getInstance().getUserKey();
                    String vendorKey = restaurantDetails.getVendor_key();
                    CommonApiCalls.getInstance().favouriteDelete(mcontext, userKey, vendorKey, new CommonCallback.Listener() {
                        @Override
                        public void onSuccess(Object object) {
                            DeleteFavoritesApiResponse data = (DeleteFavoritesApiResponse) object;
                            MyApplication.result(data.getMessage());
                            holder.favorite_add.setChecked(false);
                            restaurantDetails.setFavorites_count(restaurantDetails.getFavorites_count()-1);
                            restaurantDetails.setUser_fav_check("0");
                            holder.tvFavoritesCounts.setText("("+String.valueOf(restaurantDetails.getFavorites_count()-1)+")");
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(String reason) {

                        }
                    });
                }
            }else{
                holder.favorite_add.setChecked(false);
                CommonFunctions.getInstance().ShowSnackBar(mcontext, "You must login to continue");
            }*/
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
