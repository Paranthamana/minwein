package com.minwein.customer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.adapter.RatingAndReviewFoodAdapter;
import com.minwein.customer.adapter.RatingAndReviewRestaurantAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.RatingAndReviewApiResponse;
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
 * Use the {@link RatingAndReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingAndReviewsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rvRatingAndReview)
    RecyclerView rvRatingAndReview;
    Unbinder unbinder;
    @BindView(R.id.tv_rating_and_review_Title)
    TextView tvRatingAndReviewTitle;
    @BindView(R.id.im_rating_review_filter)
    ImageView imRatingReviewFilter;
    @BindView(R.id.tb_rating_and_review)
    Toolbar tbRatingAndReview;
    String rating = "1";
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;
    Activity activity;
    String textColor = "1";

    //    RatingAndReviewApiResponse ratingAndReviewApiResponse;
    private List<RatingAndReviewApiResponse> RatingAndReviewList;
    private List<RatingAndReviewApiResponse.Food> RatingAndReviewFoodList;
    private List<RatingAndReviewApiResponse.Restaurant> RatingAndReviewRestaurantList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RatingAndReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RatingAndReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RatingAndReviewsFragment newInstance(String param1, String param2) {
        RatingAndReviewsFragment fragment = new RatingAndReviewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_rating_and_reviews, container, false);
        unbinder = ButterKnife.bind(this, view);


        //set toolbar appearance
        tvRatingAndReviewTitle.setText(LanguageConstants.rate_and_reviews);
        imRatingReviewFilter.setImageResource(R.drawable.ic_filter);
        tbRatingAndReview.setTitle("");
        imRatingReviewFilter.setOnClickListener(this);
        //for crate home button
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbRatingAndReview);
        tbRatingAndReview.setNavigationIcon(R.drawable.ic_menu);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, tbRatingAndReview, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(activity);
        rvRatingAndReview.setLayoutManager(mlayoutManager);
        rvRatingAndReview.setItemAnimator(new DefaultItemAnimator());
//        RatingAndReviewFoodList= data.getFood();

        String userkey = SessionManager.getInstance().getUserKey();
        Apicall(userkey);
        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }
        }

        return view;
    }

    private void Apicall(String userkey) {
        CommonApiCalls.getInstance().RateAndReview(activity, userkey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                RatingAndReviewApiResponse ratingAndReviewApiResponse = (RatingAndReviewApiResponse) body;
                RatingAndReviewApiResponse.Data data = ratingAndReviewApiResponse.getData();
                if (rating.equals("1")) {
                    RatingAndReviewFoodList = data.getFood();
                    if (RatingAndReviewFoodList.size() == 0) {
                        tvNoDataFound.setText(LanguageConstants.noReviewFound);
                        rvRatingAndReview.setVisibility(View.GONE);
                        tvNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        rvRatingAndReview.setVisibility(View.VISIBLE);
                        tvNoDataFound.setVisibility(View.GONE);
                        RatingAndReviewFoodAdapter ratingAndReviewAdapter = new RatingAndReviewFoodAdapter(activity, RatingAndReviewFoodList);
                        rvRatingAndReview.setAdapter(ratingAndReviewAdapter);
                    }
                } else if (rating.equals("2")) {
                    RatingAndReviewRestaurantList = data.getRestaurant();
                    if (RatingAndReviewRestaurantList.size() == 0) {
                        tvNoDataFound.setText(LanguageConstants.noReviewFound);
                        rvRatingAndReview.setVisibility(View.GONE);
                        tvNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        rvRatingAndReview.setVisibility(View.VISIBLE);
                        tvNoDataFound.setVisibility(View.GONE);
                        RatingAndReviewRestaurantAdapter ratingAndReviewRestaurantAdapter = new RatingAndReviewRestaurantAdapter(activity, RatingAndReviewRestaurantList);
                        rvRatingAndReview.setAdapter(ratingAndReviewRestaurantAdapter);
                    }
                } else
                {
                    tvNoDataFound.setText(LanguageConstants.noReviewFound);
                    rvRatingAndReview.setVisibility(View.GONE);
                    tvNoDataFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
//        EventBus.getDefault().register(this);
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
        if (CustomProgressDialog.getInstance() != null && CustomProgressDialog.getInstance().isShowing()){
            CustomProgressDialog.getInstance().dismiss();}
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imRatingReviewFilter) {
            final Dialog menudialog = new Dialog(activity);
            menudialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            menudialog.setContentView(R.layout.map_menu_dialogue);
            final TextView Food_Menu_Option, FoodTruck_Menu_Option, Restaurant_Menu_Option;
            Food_Menu_Option = menudialog.findViewById(R.id.tvFoodOption);
            Restaurant_Menu_Option = menudialog.findViewById(R.id.tvRestaurantOption);
            FoodTruck_Menu_Option = menudialog.findViewById(R.id.tvFooftruckOption);
            Food_Menu_Option.setText(LanguageConstants.food);
            Restaurant_Menu_Option.setText(LanguageConstants.restaurant);
            FoodTruck_Menu_Option.setText(LanguageConstants.foodtruck);
            Food_Menu_Option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ratingAndReviewApiResponse.setRating_Type("Food");
                    rating = "1";
                    textColor = "1";
                    String userkey = SessionManager.getInstance().getUserKey();
                    Apicall(userkey);
                    menudialog.dismiss();
                }
            });
            Restaurant_Menu_Option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ratingAndReviewApiResponse.setRating_Type("Restaurant");
                    rating = "2";
                    textColor = "2";
                    String userkey = SessionManager.getInstance().getUserKey();
                    Apicall(userkey);
                    menudialog.dismiss();
                }
            });

            FoodTruck_Menu_Option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ratingAndReviewApiResponse.setRating_Type("FoodTruck");
                    rating = "3";
                    textColor = "3";
                    String userkey = SessionManager.getInstance().getUserKey();
                    Apicall(userkey);
                    menudialog.dismiss();
                }
            });
            switch (textColor) {
                case "1":
                    Food_Menu_Option.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                case "2":
                    Restaurant_Menu_Option.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                default:
                    FoodTruck_Menu_Option.setTextColor(getResources().getColor(R.color.colorPrimary));
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
            }else {
                lp.gravity = Gravity.START | Gravity.TOP;
            }
            menudialog.getWindow().setAttributes(lp);
            menudialog.show();
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
