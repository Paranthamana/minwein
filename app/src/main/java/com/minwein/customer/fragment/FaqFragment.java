package com.minwein.customer.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.adapter.FAQAdapter;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.FaqApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CustomProgressDialog;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FaqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaqFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.lvFAQ)
    ExpandableListView lvFAQ;
    Unbinder unbinder;
    @BindView(R.id.tvFaqTitle)
    TextView tvFaqTitle;
    @BindView(R.id.tb_Faq)
    Toolbar tbFaq;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;
    @BindView(R.id.tvFaqHeader)
    TextView tvFaqHeader;
    private List<FaqApiResponse.Datum> faqList;
    Activity activity;

    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    FAQAdapter mFaqAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FaqFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FaqFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FaqFragment newInstance(String param1, String param2) {
        FaqFragment fragment = new FaqFragment();
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
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvFaqHeader.setText(LanguageConstants.faq);
        tvFaqTitle.setText(LanguageConstants.faq);
        //set toolbar appearance
        tbFaq.setTitle("");


        //for crate home button
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(tbFaq);
        tbFaq.setNavigationIcon(R.drawable.ic_menu);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, tbFaq, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        CommonApiCalls.getInstance().faq(activity, new CommonCallback.Listener() {
            @Override
            public void onSuccess(Object body) {
                FaqApiResponse faqApiResponse = (FaqApiResponse) body;
                if (faqApiResponse.getStatus() == 200) {

                    int count = 0;
                    for (FaqApiResponse.Datum data : faqApiResponse.getData()) {
                        listDataHeader.add(data.getQuestion());

                        List<String> ListChild = new ArrayList<String>();
                        ListChild.add(data.getAnswer());
                        listDataChild.put(listDataHeader.get(count), ListChild);

                        count++;
                    }
                    if (listDataChild.size() == 0 || listDataHeader.size() == 0) {
                        lvFAQ.setVisibility(View.GONE);
                        tvNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        lvFAQ.setVisibility(View.VISIBLE);
                        tvNoDataFound.setVisibility(View.GONE);
                        mFaqAdapter = new FAQAdapter(activity, listDataHeader, listDataChild);
                        lvFAQ.setAdapter(mFaqAdapter);
                        lvFAQ.setGroupIndicator(null);
                        lvFAQ.setChildIndicator(null);
                        lvFAQ.setChildDivider(getResources().getDrawable(android.R.color.transparent));
                        lvFAQ.setDivider(getResources().getDrawable(android.R.color.transparent));
                        lvFAQ.setDividerHeight(0);
//                        MyApplication.result(faqApiResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(String reason) {

            }
        });

        mFaqAdapter = new FAQAdapter(activity, listDataHeader, listDataChild);
        lvFAQ.setAdapter(mFaqAdapter);
        lvFAQ.setGroupIndicator(null);
        lvFAQ.setChildIndicator(null);
        lvFAQ.setChildDivider(getResources().getDrawable(android.R.color.transparent));
        lvFAQ.setDivider(getResources().getDrawable(android.R.color.transparent));
        lvFAQ.setDividerHeight(0);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
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
        if (CustomProgressDialog.getInstance() != null && CustomProgressDialog.getInstance().isShowing()){
            CustomProgressDialog.getInstance().dismiss();}
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
