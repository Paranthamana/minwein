package com.minwein.customer.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.minwein.customer.R;
import com.minwein.customer.activity.HomeActivity;
import com.minwein.customer.activity.MapActivity;
import com.minwein.customer.adapter.PlacesAutoCompleteAdapter;
import com.minwein.customer.utils.AppSharedValues;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.GPSTracker;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeCityAreaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeCityAreaFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.etHomeSearchForFoodOrCuisine)
    EditText etHomeSearchForFoodOrCuisine;
    @BindView(R.id.btnHomeSearch)
    Button btnHomeSearch;
    Unbinder unbinder;
    @BindView(R.id.autoHomeLocation)
    AutoCompleteTextView autoHomeLocation;
    @BindView(R.id.tbHome)
    Toolbar tbHome;
    DrawerLayout drawerLayout;
    @BindView(R.id.ivMap)
    ImageView ivMap;
    @BindView(R.id.tilLocation)
    TextInputLayout tilLocation;
    @BindView(R.id.tilSearchForFoodOrCuisine)
    TextInputLayout tilSearchForFoodOrCuisine;
    Activity activity;
    @BindView(R.id.ivLocateMe)
    ImageView ivLocateMe;
    GPSTracker mGpsTracker;
    @BindView(R.id.tvCurrentLocation)
    TextView tvCurrentLocation;
    @BindView(R.id.llLocateMe)
    LinearLayout llLocateMe;

    GoogleApiClient googleApiClient;
    private static final int REQUEST_LOCATION = 100;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Place place;
    private OnFragmentInteractionListener mListener;
    private GoogleApiClient mGoogleApiClient;
    private String placeId;
    private double lat;
    private double lng;
    Context mcontext;
    private LocationManager locationManager;

    public HomeCityAreaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeCityAreaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeCityAreaFragment newInstance(String param1, String param2) {
        HomeCityAreaFragment fragment = new HomeCityAreaFragment();
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

    //test
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home_city_area, container, false);
        unbinder = ButterKnife.bind(this, view);
        btnHomeSearch.setText(LanguageConstants.search);
        tilSearchForFoodOrCuisine.setHint(LanguageConstants.Search_for_food_cuisine);
        tilLocation.setHint(LanguageConstants.enter_your_location);
        tvCurrentLocation.setText(LanguageConstants.usemycurrentlocation);
        btnHomeSearch.setOnClickListener(this);
        llLocateMe.setOnClickListener(this);

        tbHome.setTitle("");
        autoHomeLocation.setFocusable(false);
        autoHomeLocation.setFocusableInTouchMode(true);
        //for crate home button
        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbHome);
        tbHome.setNavigationIcon(R.drawable.ic_menu);
        mGpsTracker = new GPSTracker(activity);

      /*  if (SessionManager.getInstance().getAppLanguage().equals("en")) {
            etHomeSearchForFoodOrCuisine.setGravity(START|CENTER);
        } else if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            etHomeSearchForFoodOrCuisine.setGravity(END|CENTER);

        }
*/

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, tbHome, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

//                autoHomeLocation.setCursorVisible(false);
            }

        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final PlacesAutoCompleteAdapter adapter = new PlacesAutoCompleteAdapter(activity);
        autoHomeLocation.setAdapter(adapter);
        autoHomeLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView autocompleteText = view.findViewById(R.id.autocompleteText);
                String description = adapter.getItem(position);
                autocompleteText.setText(description);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputMethodManager.isWatchingCursor(view);

                try {
                    Geocoder geocoder = new Geocoder(activity);
                    List<Address> addressList = geocoder.getFromLocationName(
                            description, 5);
                    if (addressList != null && addressList.size() > 0) {
                        lat = addressList.get(0).getLatitude();
                        lng = addressList.get(0).getLongitude();
                        Log.v("Latitude is", "" + lat);
                        Log.v("Longitude is", "" + lng);


                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
        if (view == btnHomeSearch) {

            isLocationEnabled();
            if (!isLocationEnabled()) {

                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_alert);
                TextView tvQue = dialog.findViewById(R.id.tvQue);
                TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnYes = dialog.findViewById(R.id.btnYes);
                btnCancel.setText(LanguageConstants.cancel);
                btnYes.setText(LanguageConstants.yes);
                tvTitle.setText(LanguageConstants.alert);
                tvQue.setText(LanguageConstants.areYouWantEnableLocation);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enableLoc();
                        CustomProgressDialog.getInstance().show(activity);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                CustomProgressDialog.getInstance().dismiss();
                            }
                        }, 5000);
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams wlp = dialogWindow.getAttributes();
                wlp.gravity = Gravity.CENTER;
                dialogWindow.setAttributes(wlp);
                dialog.show();

            }else
            if (lat != 0.0 && lng != 0.0) {
                AppSharedValues.latitude = lat;
                AppSharedValues.longitude = lng;
                AppSharedValues.foodOrCuisine = etHomeSearchForFoodOrCuisine.getText().toString();
                CommonFunctions.getInstance().newIntent(activity, MapActivity.class, Bundle.EMPTY, false);
            } else {
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.addressCantBeBlank);
            }


        } else if (view == llLocateMe) {

            if (CheckGpsStatus()) {
                try {
                    CustomProgressDialog.getInstance().show(activity);
                    mGpsTracker = new GPSTracker(activity);
                    if (ActivityCompat.checkSelfPermission(activity, mPermission)
                            != PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(activity, new String[]{mPermission},
                                REQUEST_CODE_PERMISSION);
                        // check if GPS enabled
                        if (mGpsTracker.canGetLocation()) {

                            if (mGpsTracker.getLatitude() != 0.0 || mGpsTracker.getLongitude() != 0.0) {
                                lat = mGpsTracker.getLatitude();
                                lng = mGpsTracker.getLongitude();
                                AppSharedValues.latitude = lat;
                                AppSharedValues.longitude = lng;
                                AppSharedValues.foodOrCuisine = etHomeSearchForFoodOrCuisine.getText().toString();
                                autoHomeLocation.setText(mGpsTracker.getSubLocality(activity) + ","
                                        + mGpsTracker.getLocality(activity) + "," + mGpsTracker.getCountryName(activity)
                                        + "," + mGpsTracker.getPostalCode(activity));

                            } else {
//                                mGpsTracker.showSettingsAlert();
                            }
                        } else {
                            mGpsTracker.showSettingsAlert();
                            /*if (mGpsTracker.getLatitude() != 0.0 || mGpsTracker.getLongitude() != 0.0) {
                                lat = mGpsTracker.getLatitude();
                                lng = mGpsTracker.getLongitude();
                                autoHomeLocation.setText(mGpsTracker.getSubLocality(activity) + ","
                                        + mGpsTracker.getLocality(activity) + "," + mGpsTracker.getCountryName(activity)
                                        + "," + mGpsTracker.getPostalCode(activity));

                            }*/

                        }
                    } else {
                        if (mGpsTracker.getLatitude() != 0.0 || mGpsTracker.getLongitude() != 0.0) {
                            lat = mGpsTracker.getLatitude();
                            lng = mGpsTracker.getLongitude();
                            AppSharedValues.latitude = lat;
                            AppSharedValues.longitude = lng;
                            AppSharedValues.foodOrCuisine = etHomeSearchForFoodOrCuisine.getText().toString();
                            autoHomeLocation.setText(mGpsTracker.getSubLocality(activity) + ","
                                    + mGpsTracker.getLocality(activity) + "," + mGpsTracker.getCountryName(activity)
                                    + "," + mGpsTracker.getPostalCode(activity));
                            CustomProgressDialog.getInstance().dismiss();

                        } else {
                            mGpsTracker.showSettingsAlert();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_alert);
                TextView tvQue = dialog.findViewById(R.id.tvQue);
                TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnYes = dialog.findViewById(R.id.btnYes);
                btnCancel.setText(LanguageConstants.cancel);
                btnYes.setText(LanguageConstants.yes);
                tvTitle.setText(LanguageConstants.alert);
                tvQue.setText(LanguageConstants.areYouWantEnableLocation);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enableLoc();
//                        CustomProgressDialog.getInstance().show(MapActivity.this);
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams wlp = dialogWindow.getAttributes();
                wlp.gravity = Gravity.CENTER;
                dialogWindow.setAttributes(wlp);
                dialog.show();

            }


//            mGpsTracker = new GPSTracker(getActivity());
//            if (mGpsTracker.isGPSEnabled) {
//                if (mGpsTracker.getLatitude() != 0.0 || mGpsTracker.getLongitude() != 0.0) {
//                    lat = mGpsTracker.getLatitude();
//                    lng = mGpsTracker.getLongitude();
//                    autoHomeLocation.setText(mGpsTracker.getSubLocality(activity)+","
//                            +mGpsTracker.getLocality(activity)+","+mGpsTracker.getCountryName(activity)
//                            +","+mGpsTracker.getPostalCode(activity));
////                CommonFunctions.getInstance().newIntent(activity, MapActivity.class, Bundle.EMPTY, false);
//                }
//            }
        }
    }


    public Boolean CheckGpsStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isLocationEnabled() {
        String le = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) activity.getSystemService(le);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void enableLoc() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(activity, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                            }
                            break;
                    }
                }
            });
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

    @Override
    public void onResume() {
        super.onResume();
        isLocationEnabled();
        if (!isLocationEnabled()) {

            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_alert);
            TextView tvQue = dialog.findViewById(R.id.tvQue);
            TextView tvTitle = dialog.findViewById(R.id.tvTitle);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            Button btnYes = dialog.findViewById(R.id.btnYes);
            btnCancel.setText(LanguageConstants.cancel);
            btnYes.setText(LanguageConstants.yes);
            tvTitle.setText(LanguageConstants.alert);
            tvQue.setText(LanguageConstants.areYouWantEnableLocation);

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enableLoc();
                    CustomProgressDialog.getInstance().show(activity);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            CustomProgressDialog.getInstance().dismiss();
                        }
                    }, 3000);
                    dialog.dismiss();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams wlp = dialogWindow.getAttributes();
            wlp.gravity = Gravity.CENTER;
            dialogWindow.setAttributes(wlp);
            dialog.show();

        }
}}
