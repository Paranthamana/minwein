package com.minwein.customer.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minwein.customer.fragment.RestaurantDetailMenuFragment;
import com.minwein.customer.fragment.RestaurantInformation;
import com.minwein.customer.fragment.RestaurantRatingFragment;
import com.minwein.customer.utils.LanguageConstants;

/**
 * Created by AMSHEER on 20-12-2017.
 */

public class RestaurantDetailPagerAdapter extends FragmentPagerAdapter {

    private final String restaurant;
    private int tabCount;

    public RestaurantDetailPagerAdapter(FragmentManager fm, int tabCount, String data) {
        super(fm);
        this.tabCount = tabCount;
        this.restaurant = data;


    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("restaurant", restaurant);
//        bundle.putString("vendorKey",vendorKey);
        switch (position) {
            case 0:
                RestaurantDetailMenuFragment fragment = new RestaurantDetailMenuFragment();
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                RestaurantInformation informationFragment = new RestaurantInformation();
                informationFragment.setArguments(bundle);
                return informationFragment;
            case 2:
                RestaurantRatingFragment ratingFragment = new RestaurantRatingFragment();
                ratingFragment.setArguments(bundle);
                return ratingFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = LanguageConstants.menu;
                break;
            case 1:
                title = LanguageConstants.Information;
                break;
            case 2:
                title = LanguageConstants.Ratings;
                break;
        }
        return title;
    }
}