package com.minwein.customer.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.activity.HomeActivity;
import com.minwein.customer.adapter.FavouriteAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.FavoritesApiResponse;
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
 * Use the {@link FavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouritesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rvFavoriteList)
    RecyclerView rvFavoriteList;
    Unbinder unbinder;
    @BindView(R.id.tv_favourites_title)
    TextView tvFavouritesTitle;
    @BindView(R.id.tb_favourites)
    Toolbar tbFavourites;
    @BindView(R.id.tvNoFavouritesFound)
    TextView tvNoDataFound;
    private List<FavoritesApiResponse.FavouriteList> favoritelist;
    private FavouriteAdapter mAdapter;
    Activity activity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouritesFragment newInstance(String param1, String param2) {
        FavouritesFragment fragment = new FavouritesFragment();
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
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        unbinder = ButterKnife.bind(this, view);


        //set toolbar appearance
        tvFavouritesTitle.setText(LanguageConstants.favorites);
        tbFavourites.setTitle("");
        //for crate home button
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbFavourites);
        tbFavourites.setNavigationIcon(R.drawable.ic_menu);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, tbFavourites, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(activity);
        rvFavoriteList.setLayoutManager(mlayoutManager);
        rvFavoriteList.setItemAnimator(new DefaultItemAnimator());


//        FavouriteAdapter favouriteAdapter = new FavouriteAdapter();
//        rvFavoriteList.setAdapter(favouriteAdapter);

        CustomProgressDialog.getInstance().show(activity);
        String userkey = SessionManager.getInstance().getUserKey();

        CommonApiCalls.getInstance().favourites(activity, userkey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                FavoritesApiResponse favoritesApiResponse = (FavoritesApiResponse) body;
                favoritelist = favoritesApiResponse.getData().getFavouriteList();
                String message=favoritesApiResponse.getMessage();
                Log.v("qwerty",message);
                if (favoritelist.size() == 0) {
                    rvFavoriteList.setVisibility(View.GONE);
                    tvNoDataFound.setText(LanguageConstants.noFavoritesFound);
                    tvNoDataFound.setVisibility(View.VISIBLE);
                } else {
                    rvFavoriteList.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    mAdapter = new FavouriteAdapter(activity, favoritelist);
                    rvFavoriteList.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });
//        String user_key = SessionManager.getInstance().getUserKey();
//        String fav_type = SessionManager.getInstance().;

//        CommonApiCalls.getInstance().favourites(activity, user_key);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

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
        Bundle bundle=new Bundle();
        CommonFunctions.getInstance().newIntent(getContext(), HomeActivity.class,bundle,true);
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
//    @Subscribe
//    public void onEvent(EBFavouritesDelete event) {
//        String userkey=SessionManager.getInstance().getUserKey();
//        CommonApiCalls.getInstance().favourites(activity,userkey);
//    }
}
