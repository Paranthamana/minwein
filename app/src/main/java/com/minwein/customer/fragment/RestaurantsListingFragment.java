package com.minwein.customer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minwein.customer.R;
import com.minwein.customer.activity.CartSummaryActivity;
import com.minwein.customer.activity.FilterActivity;
import com.minwein.customer.activity.MapActivity;
import com.minwein.customer.activity.SortByActivity;
import com.minwein.customer.adapter.RestaurantListingAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.enums.SortByEnum;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.AppSharedValues;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantsListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantsListingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CALLBACK_REQUEST_SORTBY = 0;
    private static final int CALLBACK_REQUEST_FILTER = 199;


    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.rvRestaurantListing)
    RecyclerView rvRestaurantListing;
    Unbinder unbinder;
    @BindView(R.id.tvRestaurantListing_Title)
    TextView tvRestaurantListingTitle;
    @BindView(R.id.imRestaurantListing_map)
    ImageView imRestaurantListingMap;
    @BindView(R.id.imRestaurantListing_filter)
    ImageView imRestaurantListingFilter;
    @BindView(R.id.imRestaurantListing_sortby)
    ImageView imRestaurantListingSortby;
    @BindView(R.id.ivRestaurantListCart)
    ImageView ivRestaurantListCart;
    @BindView(R.id.tbRestaurantListing)
    Toolbar tbRestaurantListing;
    @BindView(R.id.tvNoDataFounds)
    TextView tvNoDataFounds;
    @BindView(R.id.tvRestaurantListCartValue)
    TextView tvRestaurantListCartValue;
    Activity activity;

    RestaurantListingAdapter adapter;
    FragmentManager fragmentManager;
    @BindView(R.id.ivRefresh)
    ImageView ivRefresh;
    @BindView(R.id.ivFilter)
    ImageView ivFilter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<RestaurantListApiResponse.Data.Vendor_list> RestaurantList;
    private String vendorKey;
    private String vendorName;
    private List<RestaurantListApiResponse.Data.Cusine> cuisines;
    private List<RestaurantListApiResponse.Data.Vendor_list> restaurantList;

    public RestaurantsListingFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantsListingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantsListingFragment newInstance(String param1, String param2) {
        RestaurantsListingFragment fragment = new RestaurantsListingFragment();
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
            String restaurant = getArguments().getString("restaurant");

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
        View view = inflater.inflate(R.layout.fragment_restaurants_listing, container, false);
        unbinder = ButterKnife.bind(this, view);


//        tvRestaurantListingTitle.setText(LanguageConstants.Restaurant);
        //set toolbar appearance
        tbRestaurantListing.setTitle("");
        tvRestaurantListingTitle.setText(LanguageConstants.restaurant);
        etSearch.setHint(LanguageConstants.search);
        imRestaurantListingMap.setImageResource(R.drawable.ic_map);
        imRestaurantListingFilter.setImageResource(R.drawable.ic_filter);
        imRestaurantListingSortby.setImageResource(R.drawable.ic_shortby);
        ivRestaurantListCart.setImageResource(R.drawable.ic_cart);
        ivRefresh.setOnClickListener(this);
        imRestaurantListingFilter.setVisibility(View.GONE);
        //for crate home button
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbRestaurantListing);
        tbRestaurantListing.setNavigationIcon(R.drawable.ic_menu);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        addSearchListener();

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, tbRestaurantListing, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

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

        ivFilter.setOnClickListener(this);
        imRestaurantListingMap.setOnClickListener(this);
        imRestaurantListingFilter.setOnClickListener(this);
        imRestaurantListingSortby.setOnClickListener(this);
        ivRestaurantListCart.setOnClickListener(this);
        if (RealmLibrary.getInstance().getCartSize() == 0) {
            tvRestaurantListCartValue.setText("0");
        } else {
            tvRestaurantListCartValue.setText(String.valueOf(RealmLibrary.getInstance().getCartSize()));
        }


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvRestaurantListing.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        rvRestaurantListing.setLayoutManager(mLayoutManager);
        String userKey = SessionManager.getInstance().getUserKey();
        CommonApiCalls.getInstance().RestaurantList(activity, String.valueOf(AppSharedValues.latitude), String.valueOf(AppSharedValues.longitude), AppSharedValues.foodOrCuisine, userKey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                RestaurantListApiResponse restaurantListApiResponse = (RestaurantListApiResponse) body;
                RestaurantList = restaurantListApiResponse.getData().getVendor_list();
                cuisines = restaurantListApiResponse.getData().getCusines();

                if (RestaurantList == null || RestaurantList.size() == 0) {
                    tvNoDataFounds.setVisibility(View.VISIBLE);
                    tvNoDataFounds.setText(LanguageConstants.noDataFound);
                    rvRestaurantListing.setVisibility(View.GONE);
                } else {
                    tvNoDataFounds.setVisibility(View.GONE);
                    rvRestaurantListing.setVisibility(View.VISIBLE);
                    RestaurantListingAdapter adapter = new RestaurantListingAdapter(activity, RestaurantList);
                    rvRestaurantListing.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String reason) {
                tvNoDataFounds.setVisibility(View.VISIBLE);
                tvNoDataFounds.setText(LanguageConstants.noDataFound);
                rvRestaurantListing.setVisibility(View.GONE);
                CommonFunctions.getInstance().ShowSnackBar(activity, reason);
            }
        });

        return view;
    }

    private void addSearchListener() {
        etSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<RestaurantListApiResponse.Data.Vendor_list> filteredList = new ArrayList<>();

                for (int i = 0; RestaurantList != null && i < RestaurantList.size(); i++) {

                    final String text = RestaurantList.get(i).getVendor_name().toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(RestaurantList.get(i));
                    }
                    /*else {
                        tvNoDataFounds.setText(LanguageConstants.noDataFound);
                        tvNoDataFounds.setVisibility(View.VISIBLE);
                    }*/
                }
                rvRestaurantListing.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
                rvRestaurantListing.setLayoutManager(mLayoutManager);
                if (filteredList.size() == 0) {
                    tvNoDataFounds.setVisibility(View.VISIBLE);
                    rvRestaurantListing.setVisibility(View.GONE);
                    tvNoDataFounds.setText(LanguageConstants.noDataFound);
                } else {
                    tvNoDataFounds.setVisibility(View.GONE);
                    rvRestaurantListing.setVisibility(View.VISIBLE);
                    adapter = new RestaurantListingAdapter(activity, filteredList);
                    rvRestaurantListing.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
//                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                mAdapter = new SimpleAdapter(filteredList, MainActivity.this);
//                mRecyclerView.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
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
                CommonApiCalls.getInstance().RestaurantList(activity, String.valueOf(AppSharedValues.latitude), String.valueOf(AppSharedValues.longitude), AppSharedValues.foodOrCuisine, userKey, new CommonCallback.Listener() {
                    @Override
                    public void onSuccess(Object body) {
                        RestaurantListApiResponse restaurantListApiResponse = (RestaurantListApiResponse) body;
                        restaurantList = restaurantListApiResponse.getData().getVendor_list();
                        cuisines = restaurantListApiResponse.getData().getCusines();
                        loadFoodFromFilter(nearest, delivery, minOrder, cuisineList, foodTypes);


                    }

                    @Override
                    public void onFailure(String reason) {

                    }
                });
            }
        } else if (requestCode == CALLBACK_REQUEST_SORTBY) {
            if (resultCode == 2) {
                SortByEnum sortByType = (SortByEnum) data.getExtras().get("SortByType");

                if (sortByType == SortByEnum.POPULARITY) {

                } else if (sortByType == SortByEnum.A_Z) {
                    Collections.sort(RestaurantList, new Comparator<RestaurantListApiResponse.Data.Vendor_list>() {
                        @Override
                        public int compare(RestaurantListApiResponse.Data.Vendor_list lhs, RestaurantListApiResponse.Data.Vendor_list rhs) {
                            return lhs.getVendor_name().compareTo(rhs.getVendor_name());
                        }
                    });
                } else if (sortByType == SortByEnum.FAST_DELIVERY) {
                    Collections.sort(RestaurantList, new Comparator<RestaurantListApiResponse.Data.Vendor_list>() {
                        @Override
                        public int compare(RestaurantListApiResponse.Data.Vendor_list lhs, RestaurantListApiResponse.Data.Vendor_list rhs) {
                            return Integer.valueOf(lhs.getDelivery_time()).compareTo(Integer.valueOf(rhs.getDelivery_time()));
                        }
                    });
                } else if (sortByType == SortByEnum.RECOMMENDED) {

                } else if (sortByType == SortByEnum.RATING) {
                    Collections.sort(RestaurantList, new Comparator<RestaurantListApiResponse.Data.Vendor_list>() {
                        @Override
                        public int compare(RestaurantListApiResponse.Data.Vendor_list lhs, RestaurantListApiResponse.Data.Vendor_list rhs) {
                            return Double.valueOf(rhs.getRating_average()).compareTo(Double.valueOf(lhs.getRating_average()));
                        }
                    });
                } else if (sortByType == SortByEnum.NEW) {

                } else if (sortByType == SortByEnum.DELIVERY_FEE) {
                    Collections.sort(RestaurantList, new Comparator<RestaurantListApiResponse.Data.Vendor_list>() {
                        @Override
                        public int compare(RestaurantListApiResponse.Data.Vendor_list lhs, RestaurantListApiResponse.Data.Vendor_list rhs) {
                            return Integer.valueOf(lhs.getDelivery_fee()).compareTo(Integer.valueOf(rhs.getDelivery_fee()));
                        }
                    });
                } else if (sortByType == SortByEnum.OPEN) {
                    Collections.sort(RestaurantList, new Comparator<RestaurantListApiResponse.Data.Vendor_list>() {
                        @Override
                        public int compare(RestaurantListApiResponse.Data.Vendor_list lhs, RestaurantListApiResponse.Data.Vendor_list rhs) {
                            return (lhs.getTimeslot_availability()).compareTo((rhs.getTimeslot_availability()));
                        }
                    });

                } else if (sortByType == SortByEnum.FOOD_TRUCK) {
                    Collections.sort(RestaurantList, new Comparator<RestaurantListApiResponse.Data.Vendor_list>() {
                        @Override
                        public int compare(RestaurantListApiResponse.Data.Vendor_list lhs, RestaurantListApiResponse.Data.Vendor_list rhs) {
                            return Integer.valueOf(rhs.getVendor_type()).compareTo(Integer.valueOf(lhs.getVendor_type()));
                        }
                    });
                } else if (sortByType == SortByEnum.RESTAURANT) {
                    Collections.sort(RestaurantList, new Comparator<RestaurantListApiResponse.Data.Vendor_list>() {
                        @Override
                        public int compare(RestaurantListApiResponse.Data.Vendor_list lhs, RestaurantListApiResponse.Data.Vendor_list rhs) {
                            return Integer.valueOf(lhs.getVendor_type()).compareTo(Integer.valueOf(rhs.getVendor_type()));
                        }
                    });
                }
                CustomProgressDialog.getInstance().dismiss();
                adapter = new RestaurantListingAdapter(activity, RestaurantList);
                rvRestaurantListing.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }


    private void loadFoodFromFilter(String nearest, String delivery, String minOrder, ArrayList<String> cuisine, ArrayList<String> foodType) {
//        for (int count = 0; count < markers.size(); count++) {
//            markers.get(count).remove();
//        }

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
                        if (!list.contains(restaurantList.get(count))) {
                            list.add(restaurantList.get(count));
                        }
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
                    if (!list.contains(restaurantList.get(count))) {
                        list1.add(list.get(count));
                    }
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
                            if (!list.contains(restaurantList.get(count))) {
                                list2.add(list1.get(count));
                            }
                        }
                        break;
                    case "2":
                        if (minimum_neares >= 15 && minimum_neares <= 30) {
                            if (!list.contains(restaurantList.get(count))) {
                                list2.add(list1.get(count));
                            }
                        }
                        break;
                    case "3":
                        if (minimum_neares >= 30 && minimum_neares <= 45) {
                            if (!list.contains(restaurantList.get(count))) {
                                list2.add(list1.get(count));
                            }
                        }
                        break;
                    case "4":
                        if (minimum_neares >= 45 && minimum_neares <= 60) {
                            if (!list.contains(restaurantList.get(count))) {
                                list2.add(list1.get(count));
                            }
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
                        if (!list.contains(restaurantList.get(count))) {
                            list3.add(list2.get(count));
                        }
                    }
                } else if (delivery.equals("2")) {
                    if (minimum_neares > 15 && minimum_neares < 30) {
                        if (!list.contains(restaurantList.get(count))) {
                            list3.add(list2.get(count));
                        }
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
                        if (!list.contains(restaurantList.get(count))) {
                            list5.add(list4.get(count));
                            break;
                        }
                    case "2":
                        if (min <= 100) {
                            if (!list.contains(restaurantList.get(count))) {
                                list5.add(list4.get(count));
                            }
                        }
                        break;
                    case "3":
                        if (min <= 200) {
                            if (!list.contains(restaurantList.get(count))) {
                                list5.add(list4.get(count));
                            }
                        }
                        break;
                    case "4":
                        if (min <= 300) {
                            if (!list.contains(restaurantList.get(count))) {
                                list5.add(list4.get(count));
                            }
                        }
                        break;
                }
            }
        } else {
            list5.addAll(list4);

            if (list5.size() == 0) {
                tvNoDataFounds.setVisibility(View.VISIBLE);
                rvRestaurantListing.setVisibility(View.GONE);
                tvNoDataFounds.setText(LanguageConstants.noDataFound);
            } else {
                tvNoDataFounds.setVisibility(View.GONE);
                rvRestaurantListing.setVisibility(View.VISIBLE);
                RestaurantListingAdapter adapter = new RestaurantListingAdapter(activity, list5);
                rvRestaurantListing.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (RealmLibrary.getInstance().getCartSize() == 0) {
            tvRestaurantListCartValue.setText("0");
        } else {
            tvRestaurantListCartValue.setText(String.valueOf(RealmLibrary.getInstance().getCartSize()));
        }

    }

    @Override
    public void onClick(View view) {
        if (view == imRestaurantListingMap) {
            CommonFunctions.getInstance().newIntent(activity, MapActivity.class, Bundle.EMPTY, false);

        } else if (view == ivRestaurantListCart) {
            if (RealmLibrary.getInstance().getCartSize() == 0) {
                CommonFunctions.getInstance().ShowSnackBar(activity, LanguageConstants.cartEmpty);
            } else {
                CartSummaryActivity.deliveryOptions.clear();
                CommonFunctions.getInstance().newIntent(activity, CartSummaryActivity.class, Bundle.EMPTY, false);
            }
        } else if (view == imRestaurantListingSortby) {
//            bundle.putSerializable("RestaurantList",(Serializable)RestaurantList);
//            CommonFunctions.getInstance().newIntent(activity, SortByActivity.class,bundle, false);
            Intent intent = new Intent(activity, SortByActivity.class);
            startActivityForResult(intent, CALLBACK_REQUEST_SORTBY);
        } else if (view == ivFilter) {

            Gson gson = new Gson();
            Type listOfObjects2 = new TypeToken<List<RestaurantListApiResponse.Data.Cusine>>() {
            }.getType();
            String json2 = gson.toJson(cuisines, listOfObjects2);

            Bundle bundle = new Bundle();
            bundle.putString("cuisines", json2);


            Intent intent = new Intent(activity, FilterActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, CALLBACK_REQUEST_FILTER);
        } else if (view == ivRefresh) {
            String userKey = SessionManager.getInstance().getUserKey();
            CommonApiCalls.getInstance().RestaurantList(activity, String.valueOf(AppSharedValues.latitude), String.valueOf(AppSharedValues.longitude), AppSharedValues.foodOrCuisine, userKey, new CommonCallback.Listener() {
                @Override
                public void onSuccess(Object body) {
                    RestaurantListApiResponse restaurantListApiResponse = (RestaurantListApiResponse) body;
                    RestaurantList = restaurantListApiResponse.getData().getVendor_list();
                    cuisines = restaurantListApiResponse.getData().getCusines();

                    if (RestaurantList == null || RestaurantList.size() == 0) {
                        tvNoDataFounds.setVisibility(View.VISIBLE);
                        tvNoDataFounds.setText(LanguageConstants.noDataFound);
                        rvRestaurantListing.setVisibility(View.GONE);
                    } else {
                        tvNoDataFounds.setVisibility(View.GONE);
                        rvRestaurantListing.setVisibility(View.VISIBLE);
                        RestaurantListingAdapter adapter = new RestaurantListingAdapter(activity, RestaurantList);
                        rvRestaurantListing.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(String reason) {
                    tvNoDataFounds.setVisibility(View.VISIBLE);
                    tvNoDataFounds.setText(LanguageConstants.noDataFound);
                    rvRestaurantListing.setVisibility(View.GONE);
                    CommonFunctions.getInstance().ShowSnackBar(activity, reason);
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
}
