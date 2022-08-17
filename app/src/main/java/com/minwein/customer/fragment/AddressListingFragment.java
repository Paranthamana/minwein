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
import android.widget.ImageView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.activity.AddNewAddress;
import com.minwein.customer.adapter.AddressListAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.AddressListApiResponse;
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
 * Use the {@link AddressListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressListingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.tvAddressToolbarTitle)
    TextView tvAddressToolbarTitle;
    @BindView(R.id.imAddressToolbarAddAddress)
    ImageView imAddressToolbarAddAddress;
    @BindView(R.id.tbAddress)
    Toolbar tbAddress;
    @BindView(R.id.tvAddressNoDataFound)
    TextView tvAddressNoDataFound;
    private AddressListAdapter mAdapter;
    @BindView(R.id.rvAddressList)
    RecyclerView rvAddressList;
    DrawerLayout drawerLayout;
    Unbinder unbinder;
    Activity activity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<AddressListApiResponse.Datum> addresslist;

    public AddressListingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddressListingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressListingFragment newInstance(String param1, String param2) {
        AddressListingFragment fragment = new AddressListingFragment();
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
        View view = inflater.inflate(R.layout.fragment_address_listing, container, false);
        unbinder = ButterKnife.bind(this, view);


        tvAddressToolbarTitle.setText(LanguageConstants.address);
        tbAddress.setTitle("");

        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbAddress);
        tbAddress.setNavigationIcon(R.drawable.ic_menu);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, tbAddress, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        imAddressToolbarAddAddress.setImageResource(R.drawable.ic_add_address);
        imAddressToolbarAddAddress.setOnClickListener(this);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(activity);
        rvAddressList.setLayoutManager(mlayoutManager);
        rvAddressList.setItemAnimator(new DefaultItemAnimator());

        String userKey = SessionManager.getInstance().getUserKey();
        CustomProgressDialog.getInstance().show(activity);
        CommonApiCalls.getInstance().addressList(activity, userKey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                AddressListApiResponse addressListApiResponse = (AddressListApiResponse) body;
                addresslist = addressListApiResponse.getData();
                if (addresslist.size() == 0) {
                    tvAddressNoDataFound.setText(LanguageConstants.noAddressFound);
                    rvAddressList.setVisibility(View.GONE);
                    tvAddressNoDataFound.setVisibility(View.VISIBLE);
                } else {
                    mAdapter = new AddressListAdapter(activity, addresslist);
                    rvAddressList.setAdapter(mAdapter);
                    rvAddressList.setVisibility(View.VISIBLE);
                    tvAddressNoDataFound.setVisibility(View.GONE);
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
        if (CustomProgressDialog.getInstance() != null && CustomProgressDialog.getInstance().isShowing()){
            CustomProgressDialog.getInstance().dismiss();}
    }

    @Override
    public void onClick(View v) {
        if (v == imAddressToolbarAddAddress) {
            CustomProgressDialog.getInstance().show(activity);
            Bundle bundle = new Bundle();
            CommonFunctions.getInstance().newIntent(activity, AddNewAddress.class, bundle, false);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        String userKey = SessionManager.getInstance().getUserKey();
        CommonApiCalls.getInstance().addressList(activity, userKey, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                AddressListApiResponse addressListApiResponse = (AddressListApiResponse) body;
                addresslist = addressListApiResponse.getData();
                if (addresslist.size() == 0) {
                    tvAddressNoDataFound.setText(LanguageConstants.noAddressFound);
                    rvAddressList.setVisibility(View.GONE);
                    tvAddressNoDataFound.setVisibility(View.VISIBLE);
                } else {
                    mAdapter = new AddressListAdapter(activity, addresslist);
                    rvAddressList.setAdapter(mAdapter);
                    rvAddressList.setVisibility(View.VISIBLE);
                    tvAddressNoDataFound.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });
//        if (allowRefresh)
//        {
//            allowRefresh = false;
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//        }


//        String user_key=SessionManager.getInstance().getUserKey();
//        CommonApiCalls.getInstance().addressList(activity,user_key);

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
