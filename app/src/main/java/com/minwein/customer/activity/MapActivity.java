package com.minwein.customer.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minwein.customer.R;
import com.minwein.customer.adapter.MapItemListingAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.api_model.VendorDetailsApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.AppSharedValues;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.CustomProgressDialog1;
import com.minwein.customer.utils.DataParser;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tech on 06-12-2017.
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    private static final int CALLBACK_REQUEST_FILTER = 199;
    private static final int REQUEST_LOCATION = 100;
    @BindView(R.id.ivMapMenu)
    ImageView ivMapmenu;
    @BindView(R.id.ivMapFilter)
    ImageView ivMapFilter;
    @BindView(R.id.ivRefresh)
    ImageView ivRefresh;
    TextView foodMenuOption;
    TextView restaurantMenuOption;
    TextView foodTruckMenuOption;
    @BindView(R.id.tvMapHeader)
    TextView tvMapHeader;
    @BindView(R.id.ivMapBack)
    ImageView ivMapBack;
    @BindView(R.id.ivList)
    ImageView ivList;
    @BindView(R.id.ivAddMap)
    ImageView ivAddMap;
    @BindView(R.id.ivSubMap)
    ImageView ivSubMap;
    @BindView(R.id.ivCurrentLocation)
    ImageView ivCurrentLocation;
    @BindView(R.id.ivRestaurantDetailsCart)
    ImageView ivRestaurantDetailsCart;
    @BindView(R.id.tvDetailCartValue)
    TextView tvDetailCartValue;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    LocationManager locationManager;
    private FragmentManager fragmentManager;
    Context mContext;
    protected double mLat;
    protected double mLong;
    GoogleApiClient googleApiClient;


    //1 = Food
    //2 = Restaurants
    //3 = Food Truck
    int mapType = 1;

    List<Marker> markers = new ArrayList<>();


    private List<RestaurantListApiResponse.Data.Vendor_list> restaurantList;
    private List<RestaurantListApiResponse.Data.Cusine> cuisines;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ButterKnife.bind(this);
        tvMapHeader.setText(LanguageConstants.map);
        ivCurrentLocation.setOnClickListener(this);
        ivAddMap.setOnClickListener(this);
        ivSubMap.setOnClickListener(this);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        CustomProgressDialog1.getInstance().show(MapActivity.this);

        isLocationEnabled();
        if (!isLocationEnabled()) {

            final Dialog dialog = new Dialog(MapActivity.this);
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
                    Bundle bundle = new Bundle();
                    CommonFunctions.getInstance().newIntent(MapActivity.this, HomeActivity.class, bundle, true);
                }
            });
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams wlp = dialogWindow.getAttributes();
            wlp.gravity = Gravity.CENTER;
            dialogWindow.setAttributes(wlp);
            dialog.show();

        }
            if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                this.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                ivMapBack.setImageResource(R.drawable.ic_back_right);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                this.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        if (RealmLibrary.getInstance().getCartSize() == 0) {
            tvDetailCartValue.setText("0");
        } else {
            tvDetailCartValue.setText(String.valueOf(RealmLibrary.getInstance().getCartSize()));
        }


        ivMapmenu.setOnClickListener(this);
        ivMapFilter.setOnClickListener(this);
        ivRefresh.setOnClickListener(this);
        ivList.setOnClickListener(this);
        ivMapBack.setOnClickListener(this);
        flCart.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        MarkerPoints = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fMapView);
        mapFragment.getMapAsync(this);
        String userKey = SessionManager.getInstance().getUserKey();
        CommonApiCalls.getInstance().RestaurantList(MapActivity.this, String.valueOf(AppSharedValues.latitude), String.valueOf(AppSharedValues.longitude), AppSharedValues.foodOrCuisine, userKey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                RestaurantListApiResponse restaurantListApiResponse = (RestaurantListApiResponse) body;
                restaurantList = restaurantListApiResponse.getData().getVendor_list();
                cuisines = restaurantListApiResponse.getData().getCusines();
                loadFood();


            }

            @Override
            public void onFailure(final String reason) {
//                CommonFunctions.getInstance().ShowSnackBar(MapActivity.this, reason);
                Toast.makeText(MapActivity.this, reason, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void loadFood() {
        CustomProgressDialog.getInstance().show(MapActivity.this);
        for (int count = 0; count < markers.size(); count++) {
            markers.get(count).remove();
        }
        for (int count = 0; restaurantList != null && count < restaurantList.size(); count++) {
            final RestaurantListApiResponse.Data.Vendor_list restaurant = restaurantList.get(count);

            if (restaurant.getItem_count() > 0) {
                final String vendorName = restaurant.getVendor_name();
                final String address = "";//restaurant.getBranch_address_line1();
                String latittude = restaurant.getLatitude();
                String longitude = restaurant.getLongitude();
                String vendorKey = restaurant.getVendor_key();
                String image = "";
                String image1 = "";
//                String image2 = "";
                if (restaurant.getItem_image_path().size() == 2) {
                    image = Urls.BASE_URL_PROD + restaurant.getItem_image_path().get(0).getItem_image_path();
                    image1 = Urls.BASE_URL_PROD + restaurant.getItem_image_path().get(1).getItem_image_path();
                } else if (restaurant.getItem_image_path().size() == 1) {
                    image = Urls.BASE_URL_PROD + restaurant.getItem_image_path().get(0).getItem_image_path();
                } else if (restaurant.getItem_image_path().size() > 2) {
//                    image2 = Urls.BASE_URL_PROD + restaurant.getItem_image_path().get(0).getItem_image_path();
                }


                final String restaurantKey = restaurant.getVendor_key();
                final String ratingValue = restaurant.getRating_average();

                final LatLng latlng = new LatLng(Double.parseDouble(latittude), Double.parseDouble(longitude));
                final String finalImage = image;
                final String finalImage1 = image1;
//                final String finalImage2 = image2;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL url;
                        Bitmap bmp = null;
                        Bitmap bmp1 = null;

                        try {
                            System.out.println("finalImage1 ====> " + finalImage1);
                            System.out.println("finalImage ====> < ====== >" + finalImage);

                            if (!finalImage.equals("")) {
                                url = new URL(finalImage);
                                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            }

                            if (!finalImage1.equals("")) {
                                URL url1 = new URL(finalImage1);
                                bmp1 = BitmapFactory.decodeStream(url1.openConnection().getInputStream());

                            }

                            /*if (!finalImage2.equals("")) {
                                URL url1 = new URL(finalImage1);
                                bmp2 = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                            }*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        if (bmp != null) {
                        final Bitmap bitmap = getMarkerBitmapFromViewFood(bmp, bmp1, restaurant.getItem_count(), ratingValue);
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Marker marker = mMap.addMarker(new MarkerOptions()
                                            .position(latlng)
                                            .title(vendorName)
                                            .snippet(address)
                                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                    marker.setTag(restaurantKey);
                                    markers.add(marker);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                    }
                });
                thread.start();
            }
        }
    }


    private boolean isLocationEnabled() {
        String le = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(le);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private Bitmap getMarkerBitmapFromView(Bitmap bmp, String ratingValue) {
        CustomProgressDialog.getInstance().dismiss();
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = customMarkerView.findViewById(R.id.ivVendorImage);
        ImageView ivVendorImage2 = customMarkerView.findViewById(R.id.ivVendorImage2);
        TextView tvRatingValue = customMarkerView.findViewById(R.id.tvRatingValue);
        TextView tvMultipleItems = customMarkerView.findViewById(R.id.tvMultipleItems);

        ivVendorImage2.setVisibility(View.GONE);
        tvMultipleItems.setVisibility(View.GONE);
        markerImageView.setImageBitmap(bmp);
        tvRatingValue.setText(ratingValue != null ? "(".concat(ratingValue).concat(")") : "");
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);

        return returnedBitmap;


    }

    private Bitmap getMarkerBitmapFromViewFood(Bitmap bmp, Bitmap bmp1, Integer itemCount, String ratingValue) {
        CustomProgressDialog.getInstance().dismiss();
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = customMarkerView.findViewById(R.id.ivVendorImage);
        ImageView ivVendorImage2 = customMarkerView.findViewById(R.id.ivVendorImage2);
        TextView tvRatingValue = customMarkerView.findViewById(R.id.tvRatingValue);
        TextView tvMultipleItems = customMarkerView.findViewById(R.id.tvMultipleItems);
        if (itemCount > 2) {
            ivVendorImage2.setVisibility(View.GONE);
            markerImageView.setVisibility(View.GONE);
            tvMultipleItems.setText(itemCount + "");
            tvMultipleItems.setVisibility(View.VISIBLE);
        } else {
            tvMultipleItems.setVisibility(View.GONE);
            if (bmp1 != null) {
                ivVendorImage2.setVisibility(View.VISIBLE);
                ivVendorImage2.setImageBitmap(bmp1);
            } else {
                ivVendorImage2.setVisibility(View.GONE);
            }
        }

        markerImageView.setImageBitmap(bmp);
        tvRatingValue.setText(ratingValue != null ? "(".concat(ratingValue).concat(")") : "");
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);

        return returnedBitmap;


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
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(false);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMarkerClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == ivMapmenu) {
            final Dialog menudialog = new Dialog(MapActivity.this);
            menudialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            menudialog.setContentView(R.layout.map_menu_dialogue);
            foodMenuOption = menudialog.findViewById(R.id.tvFoodOption);
            foodMenuOption.setText(LanguageConstants.food);
            foodMenuOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mapType = 1;
                    menudialog.dismiss();
                    loadFood();
                }
            });
            restaurantMenuOption = menudialog.findViewById(R.id.tvRestaurantOption);
            restaurantMenuOption.setText(LanguageConstants.restaurant);
            restaurantMenuOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mapType = 2;
                    menudialog.dismiss();
                    loadRestaurant();
                }
            });
            foodTruckMenuOption = menudialog.findViewById(R.id.tvFooftruckOption);
            foodTruckMenuOption.setText(LanguageConstants.foodtruck);
            foodTruckMenuOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mapType = 3;
                    menudialog.dismiss();
                    loadFoodTruck();
                }
            });
            switch (mapType) {
                case 1:
                    foodMenuOption.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                case 2:
                    restaurantMenuOption.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                default:
                    foodTruckMenuOption.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
            }
            menudialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(menudialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.y = 50;
            if (SessionManager.getInstance().getAppLanguage().equals("en")) {
                lp.gravity = Gravity.END | Gravity.TOP;
            } else {
                lp.gravity = Gravity.START | Gravity.TOP;
            }
            menudialog.getWindow().setAttributes(lp);
            menudialog.show();

        } else if (view == ivMapFilter) {
            Gson gson = new Gson();
            Type listOfObjects2 = new TypeToken<List<RestaurantListApiResponse.Data.Cusine>>() {
            }.getType();
            String json2 = gson.toJson(cuisines, listOfObjects2);

            Bundle bundle = new Bundle();
            bundle.putString("cuisines", json2);

            Intent intent = new Intent(MapActivity.this, FilterActivity.class);
            intent.putExtras(bundle);

            startActivityForResult(intent, CALLBACK_REQUEST_FILTER);

//            CommonFunctions.getInstance().newIntent(MapActivity.this, FilterActivity.class, bundle, false);

        } else if (view == ivList) {
            Bundle bundle = new Bundle();
            CommonFunctions.getInstance().newIntent(this, HomeActivity.class, bundle, true);
        } else if (view == ivMapBack) {
            Bundle bundle = new Bundle();
            CommonFunctions.getInstance().newIntent(this, HomeActivity.class, bundle, false);
        } else if (view == ivCurrentLocation) {
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }
            AppSharedValues.longitude = mLastLocation.getLongitude();
            AppSharedValues.latitude = mLastLocation.getLatitude();

            LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markeroptions = new MarkerOptions();
            markeroptions.position(latLng);
            markeroptions.title("Current Position");
            markeroptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_location_map));
            mCurrLocationMarker = mMap.addMarker(markeroptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            if (mGoogleApiClient != null) {

                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }

        } else if (view == ivAddMap) {
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        } else if (view == ivSubMap) {
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        } else if (view == flCart) {
            Bundle bundle = new Bundle();
            if (RealmLibrary.getInstance().getCartSize() == 0) {
                CommonFunctions.getInstance().ShowSnackBar(MapActivity.this, LanguageConstants.cartEmpty);
            } else {
                CartSummaryActivity.deliveryOptions.clear();
                CommonFunctions.getInstance().newIntent(MapActivity.this, CartSummaryActivity.class, bundle, false);
            }
        } else if (view == ivRefresh) {
            String userKey = SessionManager.getInstance().getUserKey();
            CommonApiCalls.getInstance().RestaurantList(MapActivity.this, String.valueOf(AppSharedValues.latitude), String.valueOf(AppSharedValues.longitude), AppSharedValues.foodOrCuisine, userKey, new CommonCallback.Listener() {
                @Override
                public void onSuccess(Object body) {
                    RestaurantListApiResponse restaurantListApiResponse = (RestaurantListApiResponse) body;
                    restaurantList = restaurantListApiResponse.getData().getVendor_list();
                    cuisines = restaurantListApiResponse.getData().getCusines();
                    if (mapType == 1) {
                        loadFood();
                    } else if (mapType == 2) {
                        loadRestaurant();
                    } else {
                        loadFoodTruck();
                    }

                }

                @Override
                public void onFailure(final String reason) {
//                CommonFunctions.getInstance().ShowSnackBar(MapActivity.this, reason);
                    Toast.makeText(MapActivity.this, reason, Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }


    }

    private void loadRestaurant() {
        CustomProgressDialog.getInstance().show(MapActivity.this);
        for (int count = 0; count < markers.size(); count++) {
            markers.get(count).remove();
        }
        for (int count = 0; count < restaurantList.size(); count++) {
            RestaurantListApiResponse.Data.Vendor_list restaurant = restaurantList.get(count);
            if (restaurant.getVendor_type().equals(String.valueOf(mapType - 1))) {

                System.out.println("Restaurant ==> " + restaurant.getVendor_type());

                final String vendorName = restaurant.getVendor_name();
                final String address = "";//restaurant.getBranch_address_line1();
                String latittude = restaurant.getLatitude();
                String longitude = restaurant.getLongitude();
                String vendorKey = restaurant.getVendor_key();
                final String image = Urls.BASE_URL_PROD + restaurant.getVendor_image();//ApiConfiguration.BASE_URL_PROD + restaurant.getLogo_path();
                final String restaurantKey = restaurant.getVendor_key();
                final String ratingValue = restaurant.getRating_average();

                final LatLng latlng = new LatLng(Double.parseDouble(latittude), Double.parseDouble(longitude));
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL url;
                        Bitmap bmp = null;

                        try {
                            url = new URL(image);
                            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (bmp != null) {
                            final Bitmap bitmap = getMarkerBitmapFromView(bmp, ratingValue);
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Marker marker = mMap.addMarker(new MarkerOptions()
                                                .position(latlng)
                                                .title(vendorName)
                                                .snippet(address)
                                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                        marker.setTag(restaurantKey);
                                        markers.add(marker);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                thread.start();
            }
        }
    }

    private void loadFoodTruck() {
        CustomProgressDialog.getInstance().show(MapActivity.this);
        for (int count = 0; count < markers.size(); count++) {
            markers.get(count).remove();
        }
        for (int count = 0; count < restaurantList.size(); count++) {
            RestaurantListApiResponse.Data.Vendor_list restaurant = restaurantList.get(count);
            if (restaurant.getVendor_type().equals(String.valueOf(mapType - 1))) {
                System.out.println("Food Truck ==> " + restaurant.getVendor_type());
                final String vendorName = restaurant.getVendor_name();
                final String address = "";//restaurant.getBranch_address_line1();
                String latittude = restaurant.getLatitude();
                String longitude = restaurant.getLongitude();
                String vendorKey = restaurant.getVendor_key();
                final String image = Urls.BASE_URL_PROD + restaurant.getVendor_image();//ApiConfiguration.BASE_URL_PROD + restaurant.getLogo_path();
                final String restaurantKey = restaurant.getVendor_key();
                final String ratingValue = restaurant.getRating_average();

                final LatLng latlng = new LatLng(Double.parseDouble(latittude), Double.parseDouble(longitude));
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL url;
                        Bitmap bmp = null;

                        try {
                            url = new URL(image);
                            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (bmp != null) {
                            final Bitmap bitmap = getMarkerBitmapFromView(bmp, ratingValue);
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Marker marker = mMap.addMarker(new MarkerOptions()
                                                .position(latlng)
                                                .title(vendorName)
                                                .snippet(address)
                                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                        marker.setTag(restaurantKey);
                                        markers.add(marker);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                thread.start();
            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (mapType == 1) {
            if (marker.getTag() != null) {
                CommonApiCalls.getInstance().vendorDetails(MapActivity.this, marker.getTag() != null ? marker.getTag().toString() : "", new CommonCallback.Listener() {
                    @Override
                    public void onSuccess(Object object) {
                        VendorDetailsApiResponse data = (VendorDetailsApiResponse) object;
                        List<VendorDetailsApiResponse.Item_category> category = data.getData().getItem_category();

                        showFoodDialog(marker.getTag() != null ? marker.getTag().toString() : "", category);


                    }

                    @Override
                    public void onFailure(String reason) {

                    }
                });
            } else {
//                MyApplication.displayUnKnownError();
            }

        } else if (mapType == 2) {
            showRestaurantDialog(marker.getTag() != null ? marker.getTag().toString() : "");
        } else if (mapType == 3) {
            showFoodTruckDialog(marker.getTag() != null ? marker.getTag().toString() : "");
        }
        return false;
    }

    private void showFoodDialog(String restaurantKey, List<VendorDetailsApiResponse.Item_category> category) {

        RestaurantListApiResponse.Data.Vendor_list restaurant = null;
        for (int count = 0; restaurantList != null && count < restaurantList.size(); count++) {
            if (restaurantList.get(count).getVendor_key().equals(restaurantKey)) {
                restaurant = restaurantList.get(count);
            }
        }

        List<VendorDetailsApiResponse.Item> items = new ArrayList<>();

        for (int count = 0; count < category.size(); count++) {

            items.addAll(category.get(count).getItems());
        }

        final Dialog vendorDialog = new Dialog(MapActivity.this);
        vendorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vendorDialog.setContentView(R.layout.dialog_map__food_listview);




        ImageView ivClose = vendorDialog.findViewById(R.id.ivClose);
        TextView tvRestaurantName = vendorDialog.findViewById(R.id.tvRestaurantName);
        TextView tvFoodCuisines = vendorDialog.findViewById(R.id.tvFoodCuisines);
        TextView tvFoodAllMenu = vendorDialog.findViewById(R.id.tvFoodAllMenu);
        RecyclerView rvItems = vendorDialog.findViewById(R.id.rvItems);

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        rvItems.setLayoutManager(mlayoutManager);

        MapItemListingAdapter mapItemListingAdapter = new MapItemListingAdapter(MapActivity.this, items, restaurant, category);
        rvItems.setAdapter(mapItemListingAdapter);

        final RestaurantListApiResponse.Data.Vendor_list finalRestaurant = restaurant;
        tvRestaurantName.setText(finalRestaurant.getVendor_name());
        tvFoodCuisines.setText(finalRestaurant.getCuisine_name());
        tvFoodAllMenu.setText(LanguageConstants.allmenu);
        tvFoodAllMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String restaurant1 = gson.toJson(finalRestaurant);
                bundle.putString("restaurant", restaurant1);
                vendorDialog.dismiss();
                CommonFunctions.getInstance().newIntent(MapActivity.this, RestaurantDetailsActivity.class, bundle, false);
            }
        });

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                vendorDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                vendorDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorDialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(vendorDialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        vendorDialog.getWindow().setAttributes(lp);
        vendorDialog.show();
    }

    private void showFoodTruckDialog(String restaurantKey) {
        RestaurantListApiResponse.Data.Vendor_list restaurant = null;
        for (int count = 0; restaurantList != null && count < restaurantList.size(); count++) {
            if (restaurantList.get(count).getVendor_key().equals(restaurantKey)) {
                restaurant = restaurantList.get(count);
            }
        }
        final Dialog vendorDialog = new Dialog(MapActivity.this);
        vendorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vendorDialog.setContentView(R.layout.dialog_map_foodtruck);


        ImageView ivClose = vendorDialog.findViewById(R.id.ivClose);
        TextView tvViewRestaurant = vendorDialog.findViewById(R.id.tvViewRestaurant);
        TextView tvfoodtruckName = vendorDialog.findViewById(R.id.tvfoodtruckName);
        TextView tvRestaurantPlaces = vendorDialog.findViewById(R.id.tvRestaurantPlaces);
        TextView tvFoodTruckAddress = vendorDialog.findViewById(R.id.tvFoodTruckAddress);
        TextView tvTruckRatingText = vendorDialog.findViewById(R.id.tvTruckRatingText);
        SimpleDraweeView ivTrucks = vendorDialog.findViewById(R.id.ivTrucks);
        RatingBar rtbTruckRating = vendorDialog.findViewById(R.id.rtbTruckRating);

        tvfoodtruckName.setText(restaurant.getVendor_name());
        tvViewRestaurant.setText(LanguageConstants.view);
        tvRestaurantPlaces.setText((CharSequence) restaurant.getCuisine_name());
        rtbTruckRating.setRating(Float.valueOf(restaurant.getRating_average()));
        String itemImage = Urls.BASE_URL_STAGING + restaurant.getVendor_image();
        CommonFunctions.getInstance().loadImageByFresco(ivTrucks, itemImage);
        tvTruckRatingText.setText("("+restaurant.getRating_average()+")");
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorDialog.dismiss();
            }
        });
        final RestaurantListApiResponse.Data.Vendor_list finalRestaurant = restaurant;
        tvViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String restaurant1 = gson.toJson(finalRestaurant);
                bundle.putString("restaurant", restaurant1);
                vendorDialog.dismiss();
                CommonFunctions.getInstance().newIntent(MapActivity.this, RestaurantDetailsActivity.class, bundle, false);
            }
        });

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                vendorDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                vendorDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(vendorDialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        vendorDialog.getWindow().setAttributes(lp);
        vendorDialog.show();
    }

    private void showRestaurantDialog(String restaurantKey) {
        RestaurantListApiResponse.Data.Vendor_list restaurant = null;
        for (int count = 0; restaurantList != null && count < restaurantList.size(); count++) {
            if (restaurantList.get(count).getVendor_key().equals(restaurantKey)) {
                restaurant = restaurantList.get(count);
            }
        }
        final Dialog vendorDialog = new Dialog(MapActivity.this);
        vendorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vendorDialog.setContentView(R.layout.dialog_map_restaurent);
        vendorDialog.setCancelable(false);
        TextView tvVendorNames = vendorDialog.findViewById(R.id.tvVendorNames);
        TextView tvVendorPlaces = vendorDialog.findViewById(R.id.tvVendorPlaces);
        TextView tvVendorRatingValue = vendorDialog.findViewById(R.id.tvVendorRatingValue);
        TextView tvMapVendorPopupView = vendorDialog.findViewById(R.id.tvMapVendorPopupView);
        SimpleDraweeView ivRestaurantLogo = vendorDialog.findViewById(R.id.ivRestaurantLogo);
        ImageView ivClose = vendorDialog.findViewById(R.id.ivClose);
        RatingBar rtbVendorRating = vendorDialog.findViewById(R.id.rtbVendorRating);

        tvVendorNames.setText(restaurant.getVendor_name());
        tvMapVendorPopupView.setText(LanguageConstants.view);
        rtbVendorRating.setRating(Float.valueOf(restaurant.getRating_average()));
        tvVendorRatingValue.setText("("+restaurant.getRating_average()+")");
        tvVendorPlaces.setText((CharSequence) restaurant.getCuisine_name());
        String itemImage = Urls.BASE_URL_STAGING + restaurant.getVendor_image();
        CommonFunctions.getInstance().loadImageByFresco(ivRestaurantLogo, itemImage);

        final RestaurantListApiResponse.Data.Vendor_list finalRestaurant = restaurant;
        tvMapVendorPopupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String restaurant1 = gson.toJson(finalRestaurant);
                bundle.putString("restaurant", restaurant1);
                vendorDialog.dismiss();
                CommonFunctions.getInstance().newIntent(MapActivity.this, RestaurantDetailsActivity.class, bundle, false);
            }
        });

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                vendorDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                vendorDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorDialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(vendorDialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        vendorDialog.getWindow().setAttributes(lp);
        vendorDialog.show();
    }

//    private class FetchUrl extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... url) {
//            String data = "";
//            try {
//                data = downloadUrl(url[0]);
//                Log.d("Background Task data", data.toString());
//            } catch (Exception e) {
//                Log.d("Background Task", e.toString());
//            }
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            ParserTask parserTask = new ParserTask();
//            parserTask.execute(result);
//        }
//    }

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
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        CommonFunctions.getInstance().newIntent(MapActivity.this, HomeActivity.class, bundle, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (RealmLibrary.getInstance().getCartSize() == 0) {
            tvDetailCartValue.setText("0");
        } else {
            tvDetailCartValue.setText(String.valueOf(RealmLibrary.getInstance().getCartSize()));
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        LatLng latLng = new LatLng(AppSharedValues.latitude, AppSharedValues.longitude);
        MarkerOptions markeroptions = new MarkerOptions();
        markeroptions.position(latLng);
        markeroptions.title("Current Position");
        markeroptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_location_map));
        mCurrLocationMarker = mMap.addMarker(markeroptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        CustomProgressDialog1.getInstance().dismiss();
        if (mGoogleApiClient != null) {

            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CALLBACK_REQUEST_FILTER) {
            if (resultCode == RESULT_OK) {
                final String nearest = data.getExtras().getString("nearest");
                final String delivery = data.getExtras().getString("delivery");
                final String minOrder = data.getExtras().getString("minOrder");
                final String cuisine = data.getExtras().getString("cuisine");
                final String foodType = data.getExtras().getString("foodType");


                Gson gson = new Gson();
                final ArrayList<String> cuisineList = gson.fromJson(cuisine, ArrayList.class);
                final ArrayList<String> foodTypes = gson.fromJson(foodType, ArrayList.class);
                String userKey = SessionManager.getInstance().getUserKey();
                CommonApiCalls.getInstance().RestaurantList(MapActivity.this, String.valueOf(AppSharedValues.latitude), String.valueOf(AppSharedValues.longitude), AppSharedValues.foodOrCuisine, userKey, new CommonCallback.Listener() {
                    @Override
                    public void onSuccess(Object body) {
                        RestaurantListApiResponse restaurantListApiResponse = (RestaurantListApiResponse) body;
                        restaurantList = restaurantListApiResponse.getData().getVendor_list();
                        cuisines = restaurantListApiResponse.getData().getCusines();
                        if (mapType == 1) {
                            loadFoodFromFilter(nearest, delivery, minOrder, cuisineList, foodTypes);
                        } else if (mapType == 2) {
                            loadRestaurantFromFilter(nearest, delivery, minOrder, cuisineList, foodTypes);
                        } else {
                            loadFoodTruckFromFilter(nearest, delivery, minOrder, cuisineList, foodTypes);
                        }

                    }

                    @Override
                    public void onFailure(String reason) {

                    }
                });

            }
        }
    }

    private void enableLoc() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(MapActivity.this)
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
                                status.startResolutionForResult(MapActivity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                            }
                            break;
                    }
                }
            });
        }
    }

    private void loadFoodFromFilter(String nearest, String delivery, String minOrder, ArrayList<String> cuisine, ArrayList<String> foodType) {
        List<RestaurantListApiResponse.Data.Vendor_list> list5 = getFilteredList(nearest, delivery, minOrder, cuisine, foodType);
        if (list5.size() == 0) {
            CommonFunctions.getInstance().ShowSnackBar(MapActivity.this, LanguageConstants.noRestaurantFound);
        } else {

            for (int count = 0; count < list5.size(); count++) {
                final RestaurantListApiResponse.Data.Vendor_list restaurant = list5.get(count);

                if (restaurant.getItem_count() > 0) {
                    final String vendorName = restaurant.getVendor_name();
                    final String address = "";//restaurant.getBranch_address_line1();
                    String latittude = restaurant.getLatitude();
                    String longitude = restaurant.getLongitude();
                    String vendorKey = restaurant.getVendor_key();
                    String image = "";
                    String image1 = "";
//                String image2 = "";
                    if (restaurant.getItem_image_path().size() == 2) {
                        image = Urls.BASE_URL_PROD + restaurant.getItem_image_path().get(0).getItem_image_path();
                        image1 = Urls.BASE_URL_PROD + restaurant.getItem_image_path().get(1).getItem_image_path();
                    } else if (restaurant.getItem_image_path().size() == 1) {
                        image = Urls.BASE_URL_PROD + restaurant.getItem_image_path().get(0).getItem_image_path();
                    } else if (restaurant.getItem_image_path().size() > 2) {
//                    image2 = Urls.BASE_URL_PROD + restaurant.getItem_image_path().get(0).getItem_image_path();
                    }


                    final String restaurantKey = restaurant.getVendor_key();
                    final String ratingValue = restaurant.getRating_average();

                    final LatLng latlng = new LatLng(Double.parseDouble(latittude), Double.parseDouble(longitude));
                    final String finalImage = image;
                    final String finalImage1 = image1;
//                final String finalImage2 = image2;
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL url;
                            Bitmap bmp = null;
                            Bitmap bmp1 = null;

                            try {
                                System.out.println("finalImage1 ====> " + finalImage1);
                                System.out.println("finalImage ====> < ====== >" + finalImage);

                                if (!finalImage.equals("")) {
                                    url = new URL(finalImage);
                                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                }

                                if (!finalImage1.equals("")) {
                                    URL url1 = new URL(finalImage1);
                                    bmp1 = BitmapFactory.decodeStream(url1.openConnection().getInputStream());

                                }

                            /*if (!finalImage2.equals("")) {
                                URL url1 = new URL(finalImage1);
                                bmp2 = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                            }*/

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                        if (bmp != null) {
                            final Bitmap bitmap = getMarkerBitmapFromViewFood(bmp, bmp1, restaurant.getItem_count(), ratingValue);
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Marker marker = mMap.addMarker(new MarkerOptions()
                                                .position(latlng)
                                                .title(vendorName)
                                                .snippet(address)
                                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                        marker.setTag(restaurantKey);
                                        markers.add(marker);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
//                    }
                    });
                    thread.start();
                }
            }
        }
    }

    private List<RestaurantListApiResponse.Data.Vendor_list> getFilteredList(String nearest, String delivery, String minOrder, ArrayList<String> cuisine, ArrayList<String> foodType) {
        for (int count = 0; count < markers.size(); count++) {
            markers.get(count).remove();
        }

        List<RestaurantListApiResponse.Data.Vendor_list> list = new ArrayList<>();
        List<RestaurantListApiResponse.Data.Vendor_list> list1 = new ArrayList<>();
        List<RestaurantListApiResponse.Data.Vendor_list> list2 = new ArrayList<>();
        List<RestaurantListApiResponse.Data.Vendor_list> list3 = new ArrayList<>();
        List<RestaurantListApiResponse.Data.Vendor_list> list4 = new ArrayList<>();
        List<RestaurantListApiResponse.Data.Vendor_list> list5 = new ArrayList<>();

        if (cuisine.size() > 0) {
            for (int count = 0; count < restaurantList.size(); count++) {
//            String d_time = restaurantList.get(count).getMinimum_delivery_time();
//            String min = restaurantList.get(count).getMinimum_order();

                String cusine_Name = restaurantList.get(count).getCuisine_name();
                for (int count1 = 0; count1 < cuisine.size(); count1++) {
                    String myCuisine = cuisine.get(count1);
                    if (cusine_Name != null && cusine_Name.contains(myCuisine)) {
                        list.add(restaurantList.get(count));
                    }

                }

            }
        } else {
            list.addAll(restaurantList);
        }

        if (foodType.size() > 0) {
            for (int count = 0; count < list.size(); count++) {
                String food = list.get(count).getFood_type();
                if (foodType.contains(food)) {
                    list1.add(list.get(count));
                }
            }
        } else {
            list1.addAll(list);
        }

        if (!nearest.equals("")) {
            for (int count = 0; count < list1.size(); count++) {
                Integer minimum_neares = Integer.parseInt(list1.get(count).getMinimum_delivery_time());
                switch (nearest) {
                    case "1":
                        if (minimum_neares <= 15) {
                            list2.add(list1.get(count));
                        }
                        break;
                    case "2":
                        if (minimum_neares >= 15 && minimum_neares <= 30) {
                            list2.add(list1.get(count));
                        }
                        break;
                    case "3":
                        if (minimum_neares >= 30 && minimum_neares <= 45) {
                            list2.add(list1.get(count));
                        }
                        break;
                    case "4":
                        if (minimum_neares >= 45 && minimum_neares <= 60) {
                            list2.add(list1.get(count));
                        }
                        break;
                }
            }
        } else {
            //Nearest
            list2.addAll(list1);
        }


        if (!delivery.equals("")) {
            for (int count = 0; count < list2.size(); count++) {
                Integer minimum_neares = Integer.parseInt(list2.get(count).getMinimum_delivery_time());
                if (delivery.equals("1")) {
                    if (minimum_neares < 15) {
                        list3.add(list2.get(count));
                    }
                } else if (delivery.equals("2")) {
                    if (minimum_neares > 15 && minimum_neares < 30) {
                        list3.add(list2.get(count));
                    }
                }
            }
        } else {
            //Nearest
            list3.addAll(list2);
        }

        if (!delivery.equals("")) {
            list4.addAll(list3);
        } else {
            list4.addAll(list3);
        }


        if (!minOrder.equals("")) {
            for (int count = 0; count < list4.size(); count++) {
                double min = Double.parseDouble(list4.get(count).getMinimum_order());
                switch (minOrder) {
                    case "1":
                        list5.add(list4.get(count));
                        break;
                    case "2":
                        if (min <= 100) {
                            list5.add(list4.get(count));
                        }
                        break;
                    case "3":
                        if (min <= 200) {
                            list5.add(list4.get(count));
                        }
                        break;
                    case "4":
                        if (min <= 300) {
                            list5.add(list4.get(count));
                        }
                        break;
                }
            }
        } else {
            list5.addAll(list4);

        }

        return list5;
    }

    private void loadRestaurantFromFilter(String nearest, String delivery, String minOrder, ArrayList<String> cuisine, ArrayList<String> foodType) {
        List<RestaurantListApiResponse.Data.Vendor_list> list5 = getFilteredList(nearest, delivery, minOrder, cuisine, foodType);
        if (list5.size() == 0) {
            CommonFunctions.getInstance().ShowSnackBar(MapActivity.this, LanguageConstants.noRestaurantFound);
        } else {

            for (int count = 0; count < markers.size(); count++) {
                markers.get(count).remove();
            }
            for (int count = 0; count < restaurantList.size(); count++) {
                RestaurantListApiResponse.Data.Vendor_list restaurant = restaurantList.get(count);
                if (restaurant.getVendor_type().equals(String.valueOf(mapType - 1))) {

                    System.out.println("Restaurant ==> " + restaurant.getVendor_type());

                    final String vendorName = restaurant.getVendor_name();
                    final String address = "";//restaurant.getBranch_address_line1();
                    String latittude = restaurant.getLatitude();
                    String longitude = restaurant.getLongitude();
                    String vendorKey = restaurant.getVendor_key();
                    final String image = Urls.BASE_URL_PROD + restaurant.getVendor_image();//ApiConfiguration.BASE_URL_PROD + restaurant.getLogo_path();
                    final String restaurantKey = restaurant.getVendor_key();
                    final String ratingValue = restaurant.getRating_average();

                    final LatLng latlng = new LatLng(Double.parseDouble(latittude), Double.parseDouble(longitude));
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL url;
                            Bitmap bmp = null;

                            try {
                                url = new URL(image);
                                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (bmp != null) {
                                final Bitmap bitmap = getMarkerBitmapFromView(bmp, ratingValue);
                                try {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Marker marker = mMap.addMarker(new MarkerOptions()
                                                    .position(latlng)
                                                    .title(vendorName)
                                                    .snippet(address)
                                                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                            marker.setTag(restaurantKey);
                                            markers.add(marker);
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    thread.start();
                }
            }
        }
    }

    private void loadFoodTruckFromFilter(String nearest, String delivery, String minOrder, ArrayList<String> cuisine, ArrayList<String> foodType) {
        List<RestaurantListApiResponse.Data.Vendor_list> list5 = getFilteredList(nearest, delivery, minOrder, cuisine, foodType);
        if (list5.size() == 0) {
            CommonFunctions.getInstance().ShowSnackBar(MapActivity.this, LanguageConstants.noRestaurantFound);
        } else {

            for (int count = 0; count < markers.size(); count++) {
                markers.get(count).remove();
            }
            for (int count = 0; count < restaurantList.size(); count++) {
                RestaurantListApiResponse.Data.Vendor_list restaurant = restaurantList.get(count);
                if (restaurant.getVendor_type().equals(String.valueOf(mapType - 1))) {
                    System.out.println("Food Truck ==> " + restaurant.getVendor_type());
                    final String vendorName = restaurant.getVendor_name();
                    final String address = "";//restaurant.getBranch_address_line1();
                    String latittude = restaurant.getLatitude();
                    String longitude = restaurant.getLongitude();
                    String vendorKey = restaurant.getVendor_key();
                    final String image = Urls.BASE_URL_PROD + restaurant.getVendor_image();//ApiConfiguration.BASE_URL_PROD + restaurant.getLogo_path();
                    final String restaurantKey = restaurant.getVendor_key();
                    final String ratingValue = restaurant.getRating_average();

                    final LatLng latlng = new LatLng(Double.parseDouble(latittude), Double.parseDouble(longitude));
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL url;
                            Bitmap bmp = null;

                            try {
                                url = new URL(image);
                                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (bmp != null) {
                                final Bitmap bitmap = getMarkerBitmapFromView(bmp, ratingValue);
                                try {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Marker marker = mMap.addMarker(new MarkerOptions()
                                                    .position(latlng)
                                                    .title(vendorName)
                                                    .snippet(address)
                                                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                            marker.setTag(restaurantKey);
                                            markers.add(marker);
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    thread.start();
                }
            }
        }
    }
}
