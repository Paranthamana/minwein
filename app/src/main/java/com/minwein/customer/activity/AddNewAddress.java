package com.minwein.customer.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.adapter.PlacesAutoCompleteAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.AddNewAddressApiResponse;
import com.minwein.customer.api_model.AddressListApiResponse;
import com.minwein.customer.api_model.UpdateAddressApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.fragment.HomeCityAreaFragment;
import com.minwein.customer.utils.AddressLatLng;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.DataParser;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewAddress extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    Toolbar toolbar;
    @BindView(R.id.imAddAddressBack)
    ImageView imAddAddressBack;
    @BindView(R.id.tvAddAddressHeader)
    TextView tvAddAddressHeader;
    @BindView(R.id.ivAddressSave)
    ImageView ivAddAddressSave;
    @BindView(R.id.tbAddAddress)
    android.support.v7.widget.Toolbar tbAddAddress;
    @BindView(R.id.atEnterTheLocation)
    AutoCompleteTextView atEnterTheLocation;
    //    @BindView(R.id.etAddress_Line1)
//    EditText etAddressLine1;
    @BindView(R.id.etAddress_Line2)
    EditText etAddressLine2;
    @BindView(R.id.etLandmark)
    EditText etLandmark;
    @BindView(R.id.etCompany)
    EditText etCompany;

    Boolean isUpdate = false;
    String userAddressKey;
    @BindView(R.id.tvAddressType)
    TextView tvAddressType;
    @BindView(R.id.LtAddresstype)
    LinearLayout LtAddresstype;
    String ADDRESSTYPE = "";
    @BindView(R.id.tilAddressType)
    TextInputLayout tilAddressType;
    @BindView(R.id.tilEnterTheLocation)
    TextInputLayout tilEnterTheLocation;
    //    @BindView(R.id.tilAddress_Line1)
//    TextInputLayout tilAddressLine1;
    @BindView(R.id.tilAddress_Line2)
    TextInputLayout tilAddressLine2;
    @BindView(R.id.tilLandmark)
    TextInputLayout tilLandmark;
    @BindView(R.id.tilCompany)
    TextInputLayout tilCompany;
    @BindView(R.id.ivLocation)
    ImageView ivLocation;
    private double lat;
    private double lng;
    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private boolean isFirst = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new_address);
        ButterKnife.bind(this);
        ivAddAddressSave.setOnClickListener(this);
        tvAddressType.setOnClickListener(this);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        imAddAddressBack.setOnClickListener(this);

        //Set Language Constant
        tvAddressType.setHint(LanguageConstants.select_address_type);
        tilEnterTheLocation.setHint(LanguageConstants.address_line1);
//        tilAddressLine1.setHint(LanguageConstants.address_line1);
        tilAddressLine2.setHint(LanguageConstants.address_line2);
        tilLandmark.setHint(LanguageConstants.landmark);
        tilCompany.setHint(LanguageConstants.company);
        tvAddAddressHeader.setText(LanguageConstants.add_address);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                imAddAddressBack.setImageResource(R.drawable.ic_back_right);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }


        MarkerPoints = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment2);
        mapFragment.getMapAsync(this);


        final PlacesAutoCompleteAdapter adapter = new PlacesAutoCompleteAdapter(this);
        atEnterTheLocation.setAdapter(adapter);
        atEnterTheLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView autocompleteText = view.findViewById(R.id.autocompleteText);
                String description = adapter.getItem(position);
                autocompleteText.setText(description);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                inputMethodManager.isWatchingCursor(view);

                try {
                    Geocoder geocoder = new Geocoder(AddNewAddress.this);
                    List<Address> addressList = geocoder.getFromLocationName(
                            description, 5);
                    if (addressList != null && addressList.size() > 0) {
                        AddressLatLng.lat = addressList.get(0).getLatitude();
                        AddressLatLng.lng = addressList.get(0).getLongitude();
                        Log.v("Latitude is", "" + lat);
                        Log.v("Longitude is", "" + lng);

                        LatLng latLng = new LatLng(AddressLatLng.lat, AddressLatLng.lng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.getString("currentAddress") != null) {
            isFirst = true;
            String address = bundle.getString("currentAddress");
            Gson gson = new Gson();
            AddressListApiResponse.Datum addressData = gson.fromJson(address, AddressListApiResponse.Datum.class);
            isUpdate = true;
            AddressLatLng.lat = addressData.getLatitude();
            AddressLatLng.lng = addressData.getLongitude();
            userAddressKey = addressData.getUser_address_key();
            String addressType = addressData.getAddtype();
            if (addressType.equals("1")) {
                tvAddressType.setText(LanguageConstants.home);
            } else if (addressType.equals("2")) {
                tvAddressType.setText(LanguageConstants.work);
            } else {
                tvAddressType.setText(LanguageConstants.office);
            }
            String addressline1 = "";
            if (addressData.getAddress_line1() != null && !addressData.getAddress_line1().equals("")) {
                addressline1 = addressline1 + addressData.getAddress_line1() + ",\n";
            }
            if (addressData.getAddress_line2() != null && !addressData.getAddress_line2().equals("")) {
                addressline1 = addressline1 + addressData.getAddress_line2() + ",\n";
            }
//            if (addressData.getAddress_city() != null && !addressData.getAddress_city().equals("")) {
//                addressline1 = addressline1 + addressData.getAddress_city() + ",\n";
//            }
//            if (addressData.getAddress_country() != null && !addressData.getAddress_country().equals("")) {
//                addressline1 = addressline1 + addressData.getAddress_country() + ",\n";
//            }
//            if (addressData.getAddress_zip_code() != null && !addressData.getAddress_zip_code().equals("")) {
//                addressline1 = addressline1 + addressData.getAddress_zip_code() + "\n";
//            }
            atEnterTheLocation.setText(addressline1);

        } else {
            isUpdate = false;
        }

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (CustomProgressDialog.getInstance() != null && CustomProgressDialog.getInstance().isShowing()) {
            CustomProgressDialog.getInstance().dismiss();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (!isFirst) {
                    //get latlng at the center by calling
                    LatLng midLatLng = mMap.getCameraPosition().target;
                    Geocoder geocoder;
                    AddressLatLng.lat = midLatLng.latitude;
                    AddressLatLng.lng = midLatLng.longitude;
                    List<Address> addressList = null;
                    String addressline1 = "";
                    geocoder = new Geocoder(AddNewAddress.this, Locale.getDefault());

                    try {
                        addressList = geocoder.getFromLocation(midLatLng.latitude, midLatLng.longitude, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList != null && addressList.size() > 0) {

                        if (addressList.get(0).getAddressLine(1) != null && !addressList.get(0).getAddressLine(0).equals("")) {
                            addressline1 = addressline1 + addressList.get(0).getAddressLine(0) + ",\n";
                        }
                        if (addressList.get(0).getLocality() != null && !addressList.get(0).getLocality().equals("")) {
                            addressline1 = addressline1 + addressList.get(0).getLocality() + ",\n";
                        }
                        if (addressList.get(0).getSubAdminArea() != null && !addressList.get(0).getSubAdminArea().equals("")) {
                            addressline1 = addressline1 + addressList.get(0).getSubAdminArea() + ",\n";
                        }
                        if (addressList.get(0).getAdminArea() != null && !addressList.get(0).getAdminArea().equals("")) {
                            addressline1 = addressline1 + addressList.get(0).getAdminArea() + ",\n";
                        }
                        if (addressList.get(0).getCountryName() != null && !addressList.get(0).getCountryName().equals("")) {
                            addressline1 = addressline1 + addressList.get(0).getCountryName() + ",\n";
                        }
                        if (addressList.get(0).getPostalCode() != null && !addressList.get(0).getPostalCode().equals("")) {
                            addressline1 = addressline1 + addressList.get(0).getPostalCode() + ".\n";
                        }
                        if (addressList.get(0).getPhone() != null && !addressList.get(0).getPhone().equals("")) {
                            addressline1 = addressline1 + addressList.get(0).getPhone() + ".\n";
                        }
                    }
                    atEnterTheLocation.setText(addressline1);
                }else{
                    isFirst = false;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == imAddAddressBack) {
            finish();
        }
        if (v == tvAddressType) {

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddNewAddress.this);
            builderSingle.setTitle(LanguageConstants.selectaddresstype);

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddNewAddress.this, android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add(LanguageConstants.home);
            arrayAdapter.add(LanguageConstants.work);
            arrayAdapter.add(LanguageConstants.office);

            builderSingle.setNegativeButton(LanguageConstants.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    if (arrayAdapter.getItem(which).equals(LanguageConstants.home)) {
                        tvAddressType.setText(LanguageConstants.home);
                        ADDRESSTYPE = "1";
                    } else if (arrayAdapter.getItem(which).equals(LanguageConstants.work)) {
                        tvAddressType.setText(LanguageConstants.work);
                        ADDRESSTYPE = "2";
                    } else {
                        tvAddressType.setText(LanguageConstants.office);
                        ADDRESSTYPE = "3";

                    }
                }
            });
            builderSingle.show();

        }
        if (v == ivAddAddressSave) {
            if (atEnterTheLocation.getText().toString().trim().isEmpty()) {
                atEnterTheLocation.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.enteraddress_line1);
            } else if (tvAddressType.getText().toString().trim().isEmpty()) {
                tvAddressType.requestFocus();
                CommonFunctions.getInstance().ShowSnackBar(this, LanguageConstants.selectaddresstype);

            } else if (!isUpdate) {
                String Location = atEnterTheLocation.getText().toString();
                String addressType = tvAddressType.getText().toString();
//                String AddressLine1 = etAddressLine1.getText().toString();
                String AddressLine2 = etAddressLine2.getText().toString();
                String Landmark = etLandmark.getText().toString();
                String Company = etCompany.getText().toString();
                String userkey = SessionManager.getInstance().getUserKey();
                CommonApiCalls.getInstance().AddNewAddress(this, userkey, Location, AddressLine2,
                        String.valueOf(AddressLatLng.lat),
                        String.valueOf(AddressLatLng.lng), ADDRESSTYPE, Company, Landmark, new CommonCallback.Listener() {
                            @Override
                            public void onSuccess(Object body) {
                                AddNewAddressApiResponse addNewAddressApiResponse = (AddNewAddressApiResponse) body;
                                MyApplication.result(addNewAddressApiResponse.getMessage());
                                finish();
                            }

                            @Override
                            public void onFailure(String reason) {

                            }
                        });
            } else if (isUpdate) {
                String Location = atEnterTheLocation.getText().toString();
                String addressType="";
                if (tvAddressType.getText().toString().equals("Home")) {
                    addressType = "1";
                } else if (tvAddressType.getText().toString().equals("Work")) {
                    addressType = "2";
                } else {
                    addressType = "3";

                }
                String AddressLine2 = etAddressLine2.getText().toString();
                String Landmark = etLandmark.getText().toString();
                String Company = etCompany.getText().toString();
                String userkey = SessionManager.getInstance().getUserKey();

                CommonApiCalls.getInstance().updateAddress(this, userkey, userAddressKey, Location, AddressLine2,
                        String.valueOf(AddressLatLng.lat), String.valueOf(AddressLatLng.lng), addressType, Company, Landmark, new CommonCallback.Listener() {
                            @Override
                            public void onSuccess(Object object) {
                                UpdateAddressApiResponse data = (UpdateAddressApiResponse) object;
                                MyApplication.result(data.getMessage());
                                finish();
                            }

                            @Override
                            public void onFailure(String reason) {

                            }
                        });

            } else {
                Log.e("1234", "Somthing wrong");
            }
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());
            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.GRAY);
                Log.d("onPostExecute", "onPostExecute lineoptions decoded");
            }
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        if (isUpdate) {
            LatLng latLng = new LatLng(AddressLatLng.lat, AddressLatLng.lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } else {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        if (mGoogleApiClient != null) {

            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


}
