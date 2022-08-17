package com.minwein.customer.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.adapter.RestaurantMenuListPagerAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.api_model.VendorDetailsApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantDetailMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantDetailMenuFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.llCategoryTitles)
    LinearLayout llCategoryTitles;
    @BindView(R.id.hsvCategory)
    HorizontalScrollView hsvCategory;
    @BindView(R.id.vpMenu)
    ViewPager vpMenu;
    Activity activity;

    Toolbar RestaurantMenuToolbar;
    ImageView ivCart, ivFilter;

    public static List<TextView> titles = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int selectedId = 0;
    private String restaurant;
    private String vendorKey;
    private String vendorName;

    public RestaurantDetailMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantDetailMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantDetailMenuFragment newInstance(String param1, String param2) {
        RestaurantDetailMenuFragment fragment = new RestaurantDetailMenuFragment();
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
            vendorKey = getArguments().getString("vendorKey");

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_detail_menu, container, false);
        unbinder = ButterKnife.bind(this, view);



//        RestaurantMenuToolbar = (Toolbar) activity.findViewById(R.id.tbRestaurantDetails);
//        ivFilter = (ImageView) RestaurantMenuToolbar.findViewById(R.id.imRestaurantDetailsRestaurantFilter);
//        ivCart = (ImageView) RestaurantMenuToolbar.findViewById(R.id.ivRestaurantDetailsCart);
//        ivFilter.setVisibility(View.VISIBLE);
//        ivCart.setVisibility(View.GONE);
//        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
//        appCompatActivity.setSupportActionBar(RestaurantMenuToolbar);

        Gson gson = new Gson();
        RestaurantListApiResponse.Data.Vendor_list data = gson.fromJson(restaurant, RestaurantListApiResponse.Data.Vendor_list.class);
        vendorKey = data.getVendor_key();
        vendorName = data.getVendor_name();

        CommonApiCalls.getInstance().vendorDetails(activity, vendorKey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object object) {
                VendorDetailsApiResponse data = (VendorDetailsApiResponse) object;
                List<VendorDetailsApiResponse.Item_category> category = data.getData().getItem_category();

//                Bundle bundle = new Bundle();
//                Gson gson = new Gson();
//                String categories = gson.toJson(category);
//                bundle.putString("category", categories);
//                gson.toJson(categories, VendorDetailsApiResponse.Item_category.class);

                if (category.size() > 0) {
                    for (int count = 0; count < category.size(); count++) {
                        View child = getLayoutInflater().inflate(R.layout.adapter_categories_title, null);
                        LinearLayout llTitle = (LinearLayout) child.findViewById(R.id.llTitle);
                        final TextView textView = (TextView) child.findViewById(R.id.tvCategoriesTitle);
                        textView.setText(category.get(count).getCategory_name());
                        textView.setId(count);
                        titles.add(textView);
                        final int finalCount = count;
                        llTitle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectedId = finalCount;
                                changeTitleColor();
                                vpMenu.setCurrentItem(finalCount);
                            }
                        });
                        llCategoryTitles.addView(child);


                    }
                    RestaurantMenuListPagerAdapter adapter = new RestaurantMenuListPagerAdapter(activity, category, restaurant);
//                    if (SessionManager.getInstance().getAppLanguage().equals("ar")) {
//                        vpMenu.setRotationY(180);
//                    } else {
//
//                    }
                    vpMenu.setAdapter(adapter);

                    vpMenu.setCurrentItem(0);
                    selectedId = 0;
                    changeTitleColor();
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });


        vpMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedId = position;
                changeTitleColor();
                try {
                    int x, y;
                    LinearLayout views = (LinearLayout) hsvCategory.getChildAt(0);
                    x = views.getChildAt(selectedId).getLeft();
                    y = views.getChildAt(selectedId).getTop();
                    hsvCategory.scrollTo(x, y);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpMenu.setCurrentItem(0);
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
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        RestaurantMenuToolbar = (Toolbar) activity.findViewById(R.id.tbRestaurantDetails);
//        ivFilter = (ImageView) RestaurantMenuToolbar.findViewById(R.id.imRestaurantDetailsRestaurantFilter);
//        ivCart = (ImageView) RestaurantMenuToolbar.findViewById(R.id.ivRestaurantDetailsCart);
//        ivFilter.setVisibility(View.VISIBLE);
//        ivCart.setVisibility(View.GONE);
//        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
//        appCompatActivity.setSupportActionBar(RestaurantMenuToolbar);

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
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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

    private void changeTitleColor() {
        for (int count = 0; count < titles.size(); count++) {
            if (titles.get(count).getId() == selectedId) {
                titles.get(count).setTextColor(getResources().getColor(R.color.colorPrimary));

            } else {
                titles.get(count).setTextColor(getResources().getColor(R.color.black));

            }
        }


    }


}
