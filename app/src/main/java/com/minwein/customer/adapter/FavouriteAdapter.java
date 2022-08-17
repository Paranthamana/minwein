package com.minwein.customer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.activity.MapActivity;
import com.minwein.customer.activity.RestaurantDetailsActivity;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.DeleteFavoritesApiResponse;
import com.minwein.customer.api_model.FavoritesApiResponse;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.AppSharedValues;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import java.util.List;

/**
 * Created by AMSHEER on 19-12-2017.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    private Context mcontext;
    private List<FavoritesApiResponse.FavouriteList> favoriteList;

    public FavouriteAdapter(Context context, List<FavoritesApiResponse.FavouriteList> favoritelist) {
        this.favoriteList = favoritelist;
        this.mcontext = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvFavoriteRestaurantName, tvFavoriteRestaurantPlace;
        SimpleDraweeView sdvFavouriteRestaurant;
        ToggleButton tbFavoriteSelector;
        LinearLayout llFavourite;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvFavoriteRestaurantName = (TextView) itemView.findViewById(R.id.tvFavoriteRestaurantName);
            tvFavoriteRestaurantPlace = (TextView) itemView.findViewById(R.id.tvFavoriteRestaurantPlace);
            sdvFavouriteRestaurant = (SimpleDraweeView) itemView.findViewById(R.id.sdvFavouriteRestaurant);
            tbFavoriteSelector = (ToggleButton) itemView.findViewById(R.id.tbFavoriteSelector);
            llFavourite = (LinearLayout) itemView.findViewById(R.id.llFavourite);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_favourite_list, parent, false);
        return new MyViewHolder(itemview);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FavoritesApiResponse.FavouriteList favoritelist = favoriteList.get(position);

        holder.tvFavoriteRestaurantName.setText(favoritelist.getVendorName());
        String itemImage = Urls.BASE_URL_STAGING + favoritelist.getVendorImage();
        CommonFunctions.getInstance().loadImageByFresco(holder.sdvFavouriteRestaurant, itemImage);
        holder.tvFavoriteRestaurantPlace.setText(favoritelist.getCuisineName());
        RestaurantListApiResponse.Data.Vendor_list restaurant = null;
        final RestaurantListApiResponse.Data.Vendor_list finalRestaurant = restaurant;
//        holder.llFavourite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (AppSharedValues.longitude == 0.0 && AppSharedValues.latitude == 0.0) {
//                    CommonFunctions.getInstance().ShowSnackBar(mcontext, LanguageConstants.pleaseselectlocation);
//                } else {
//                    String userKey = SessionManager.getInstance().getUserKey();
//                    CommonApiCalls.getInstance().RestaurantList(mcontext,
//                            String.valueOf(AppSharedValues.latitude),
//                            String.valueOf(AppSharedValues.longitude),
//                            AppSharedValues.foodOrCuisine, userKey, new CommonCallback.Listener() {
//                                @Override
//                                public void onSuccess(Object body) {
//                                    RestaurantListApiResponse restaurantListApiResponse = (RestaurantListApiResponse) body;
//                                    List<RestaurantListApiResponse.Data.Vendor_list> restaurantList = restaurantListApiResponse.getData().getVendor_list();
//                                    RestaurantListApiResponse.Data.Vendor_list currentVendor = null;
//
//                                    for (int count = 0; count < restaurantList.size(); count++) {
//                                        if (restaurantList.get(count).getVendor_key().equals(favoritelist.getVendorKey())) {
//                                            currentVendor = restaurantList.get(count);
//                                            break;
//                                        }
//                                    }
//
//                                    if (currentVendor != null) {
//                                        Bundle bundle = new Bundle();
//                                        Gson gson = new Gson();
//                                        String restaurant1 = gson.toJson(currentVendor);
//                                        bundle.putString("restaurant", restaurant1);
//                                        CommonFunctions.getInstance().newIntent(mcontext, RestaurantDetailsActivity.class, bundle,
//                                                false);
//                                    }
//
//                                }
//
//                                @Override
//                                public void onFailure(String reason) {
//
//                                }
//                            });
//                }
//            }
//        });
        holder.tbFavoriteSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    String userKey= SessionManager.getInstance().getUserKey();
                    String vendorKey=favoritelist.getVendorKey();
                    CommonApiCalls.getInstance().favouriteDelete(mcontext,userKey,vendorKey, new CommonCallback.Listener() {
                        @Override
                        public void onSuccess(Object object) {
                            DeleteFavoritesApiResponse data=(DeleteFavoritesApiResponse)object;
                            MyApplication.result(data.getMessage());
                            favoriteList.remove(favoritelist);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(String reason) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }


}
