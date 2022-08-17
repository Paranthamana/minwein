package com.minwein.customer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minwein.customer.R;
import com.minwein.customer.api_model.VendorDetailsApiResponse;

import java.util.List;

/**
 * Created by AMSHEER on 20-12-2017.
 */

public class RestaurantMenuListPagerAdapter extends PagerAdapter {

    private final Context context;
    private final List<VendorDetailsApiResponse.Item_category> categories;
    private final String restaurant;


    public RestaurantMenuListPagerAdapter(Context context, List<VendorDetailsApiResponse.Item_category> categories, String restaurant) {

        this.context = context;
        this.categories = categories;
        this.restaurant = restaurant;
    }

    @Override
    public int getCount() {
        return categories.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.adapter_item_list_pager, collection, false);

        RecyclerView lvItemListing = layout.findViewById(R.id.rvItemListing);

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(context);
        lvItemListing.setLayoutManager(mlayoutManager);
        lvItemListing.setItemAnimator(new DefaultItemAnimator());

        ItemListingMenuAdapter itemMenuAdapter = new ItemListingMenuAdapter(context, categories.get(position), restaurant);
        lvItemListing.setAdapter(itemMenuAdapter);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
}
