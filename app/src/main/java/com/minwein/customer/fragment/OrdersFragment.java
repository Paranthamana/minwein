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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.adapter.OrderListAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.OrderApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
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
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tvOrderToolbarTitle)
    TextView tvOrderToolbarTitle;
    @BindView(R.id.tb_orders)
    Toolbar tbOrders;
    @BindView(R.id.tvNoOrderFound)
    TextView tvNoDataFound;
    private List<OrderApiResponse.Datum> orderList;
//    public static ArrayList<RatingModel> model = new ArrayList<>();
    private OrderListAdapter mAdapter;
    @BindView(R.id.rvOrderList)
    RecyclerView rvOrderList;
    Unbinder unbinder;
    Context mcontext;
    Activity activity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvOrderToolbarTitle.setText(LanguageConstants.orders);
        //set toolbar appearance
        tbOrders.setTitle("");

        //for crate home button
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbOrders);
        tbOrders.setNavigationIcon(R.drawable.ic_menu);
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, tbOrders, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(activity);
        rvOrderList.setLayoutManager(mlayoutManager);
        rvOrderList.setItemAnimator(new DefaultItemAnimator());
        String user_key = SessionManager.getInstance().getUserKey();
        CustomProgressDialog.getInstance().show(activity);
        CommonApiCalls.getInstance().orders(activity, user_key, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                OrderApiResponse orderApiResponse = (OrderApiResponse) body;
                orderList = orderApiResponse.getData();
                if (orderList.size() == 0) {
                    rvOrderList.setVisibility(View.GONE);
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    tvNoDataFound.setText(LanguageConstants.noOrdersFound);
                } else {
                    rvOrderList.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    mAdapter = new OrderListAdapter(activity, orderList);
                    rvOrderList.setAdapter(mAdapter);
                }
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
