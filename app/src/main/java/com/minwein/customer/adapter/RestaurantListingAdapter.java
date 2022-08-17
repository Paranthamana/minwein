package com.minwein.customer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.activity.RestaurantDetailsActivity;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.AddFavoriteApiResponse;
import com.minwein.customer.api_model.DeleteFavoritesApiResponse;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import java.util.List;

/**
 * Created by AMSHEER on 19-12-2017.
 */

public class RestaurantListingAdapter extends RecyclerView.Adapter<RestaurantListingAdapter.MyViewHolder> {

    private final Context mcontext;
    private final List<RestaurantListApiResponse.Data.Vendor_list> restaurantList;

    public RestaurantListingAdapter(Context context, List<RestaurantListApiResponse.Data.Vendor_list> Restaurantlist) {
        this.mcontext = context;
        this.restaurantList = Restaurantlist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvRestaurantname, tvcuisine, tvRating, tvDeliveryFeeSAR, tvMinimumOrderSAR,
                tvFavoritesCounts, tvDeliveryFee, tvMinimumOrder, tvDelFeeSymbol, tvRatingValues,
                tvAverageDelivery,tvDeliveryAvg;
        LinearLayout llRestaurant,llStatus,llVendorStatus;
        SimpleDraweeView ivRestaurant;
        ToggleButton favorite_add;
        RatingBar rbRestaurantRating;
        ImageView ivVeg, ivNonVeg, ivVendorStatus, ivPaymentOp1, ivPaymentOp2;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvRestaurantname = (TextView) itemView.findViewById(R.id.tvRestaurantName);
            tvcuisine = (TextView) itemView.findViewById(R.id.tvCuisine);
            tvRating = (TextView) itemView.findViewById(R.id.tvRestaurantRate);
            tvDeliveryFeeSAR = (TextView) itemView.findViewById(R.id.tvDeliveryFeeSAR);
            tvMinimumOrderSAR = (TextView) itemView.findViewById(R.id.tvMinimumOrderSAR);
            llRestaurant = (LinearLayout) itemView.findViewById(R.id.llRestaurant);
            tvFavoritesCounts = (TextView) itemView.findViewById(R.id.tvfavoritesCount);
            ivRestaurant = (SimpleDraweeView) itemView.findViewById(R.id.ivRestaurant);
            favorite_add = (ToggleButton) itemView.findViewById(R.id.rbFavoriteSelector);
            rbRestaurantRating = (RatingBar) itemView.findViewById(R.id.rtbRestaurantRating);
            tvDeliveryFee = (TextView) itemView.findViewById(R.id.tvDeliveryFee);
            tvMinimumOrder = (TextView) itemView.findViewById(R.id.tvMinimumOrder);
            tvDelFeeSymbol = (TextView) itemView.findViewById(R.id.tvDelFeeSymbol);
            tvRatingValues = (TextView) itemView.findViewById(R.id.tvRatingValues);
            tvAverageDelivery = (TextView) itemView.findViewById(R.id.tvAverageDelivery);
            tvDeliveryAvg = (TextView) itemView.findViewById(R.id.tvDeliveryAvg);
            ivVeg = (ImageView) itemView.findViewById(R.id.ivVeg);
            ivNonVeg = (ImageView) itemView.findViewById(R.id.ivNonVeg);
            llStatus = (LinearLayout) itemView.findViewById(R.id.llStatus);
            llVendorStatus = (LinearLayout) itemView.findViewById(R.id.llVendorStatus);
            ivVendorStatus = (ImageView) itemView.findViewById(R.id.ivVendorStatus);
            ivPaymentOp1 = (ImageView) itemView.findViewById(R.id.ivPaymentOp1);
            ivPaymentOp2 = (ImageView) itemView.findViewById(R.id.ivPaymentOp2);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_restaurant_listing, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RestaurantListApiResponse.Data.Vendor_list restaurantDetails = restaurantList.get(position);
        holder.tvDeliveryFee.setText(LanguageConstants.delivery);
        holder.tvMinimumOrder.setText(LanguageConstants.min);
        holder.tvAverageDelivery.setText(LanguageConstants.Avg);
        holder.tvRestaurantname.setText(restaurantDetails.getVendor_name());
        holder.tvcuisine.setText((CharSequence) restaurantDetails.getCuisine_name());

        holder.tvDeliveryAvg.setText(restaurantDetails.getMinimum_delivery_time()+LanguageConstants.min);

        if (restaurantDetails.getVendor_availability_status().equals("0")){
            holder.llStatus.setBackgroundResource(R.drawable.xml_imagebg_rounded_unselect);
            holder.llVendorStatus.setBackgroundResource(R.drawable.xml_shape_rounded_red);
            holder.ivVendorStatus.setImageResource(R.drawable.ic_closed);
        }else if (restaurantDetails.getVendor_availability_status().equals("1")){
            holder.llStatus.setBackgroundResource(R.drawable.xml_imagebg_rounded_rectangle);
            holder.llVendorStatus.setBackgroundResource(R.drawable.xml_shape_rounded_circlegreen);
            holder.ivVendorStatus.setImageResource(R.drawable.ic_open);
        }

        if (restaurantDetails.getPayment_option().equals("1")){
            holder.ivPaymentOp1.setVisibility(View.VISIBLE);
            holder.ivPaymentOp2.setVisibility(View.GONE);
        }else if (restaurantDetails.getPayment_option().equals("2")){
            holder.ivPaymentOp1.setVisibility(View.GONE);
            holder.ivPaymentOp2.setVisibility(View.VISIBLE);
        }else {
            holder.ivPaymentOp1.setVisibility(View.VISIBLE);
            holder.ivPaymentOp2.setVisibility(View.VISIBLE);
        }

        if (restaurantDetails.getRating_average() != null) {
            holder.rbRestaurantRating.setRating((Float.valueOf(restaurantDetails.getRating_average())));
            holder.tvRating.setText("("+(restaurantDetails.getRating_average())+")");
            holder.tvRatingValues.setText(restaurantDetails.getRating_average());
        } else {
            holder.rbRestaurantRating.setRating(0);
            holder.tvRating.setText("("+("0")+")");
            holder.tvRatingValues.setText("0.0");
        }
        if(SessionManager.getInstance().getUserKey().isEmpty()){
            holder.favorite_add.setChecked(false);
        }else {
            if (restaurantDetails.getUser_fav_check().equals("0")) {
                holder.favorite_add.setChecked(false);
            } else {
                holder.favorite_add.setChecked(true);
            }
        }
        holder.tvMinimumOrderSAR.setText(SessionManager.getInstance().getCurrencySymbol()+" "+(CommonFunctions.getInstance().roundOffDecimalValue(Double.parseDouble(restaurantDetails.getMinimum_order()))));
        holder.tvFavoritesCounts.setText("(" +( restaurantDetails.getFavorites_count().toString())+ ")");
        holder.tvDeliveryFeeSAR.setText(SessionManager.getInstance().getCurrencySymbol()+" "+(CommonFunctions.getInstance().roundOffDecimalValue(Double.parseDouble(restaurantDetails.getDelivery_fee()))));
        String itemImage = Urls.BASE_URL_STAGING + restaurantDetails.getVendor_image();
        CommonFunctions.getInstance().loadImageByFresco(holder.ivRestaurant, itemImage);

        if (restaurantDetails.getFood_type().equals("1")) {
            holder.ivNonVeg.setVisibility(View.GONE);
            holder.ivVeg.setVisibility(View.GONE);
        } else if (restaurantDetails.getFood_type().equals("2")) {
            holder.ivNonVeg.setVisibility(View.GONE);
            holder.ivVeg.setVisibility(View.GONE);
        } else {
            holder.ivNonVeg.setVisibility(View.GONE);
            holder.ivVeg.setVisibility(View.GONE);
        }

        holder.llRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String restaurant = gson.toJson(restaurantDetails);
                bundle.putString("restaurant", restaurant);
                CommonFunctions.getInstance().newIntent(mcontext, RestaurantDetailsActivity.class, bundle, false);
            }
        });
        holder.favorite_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SessionManager.getInstance().getUserKey().isEmpty()) {
                    if (restaurantDetails.getUser_fav_check().equals("0")) {
                        String user_key = SessionManager.getInstance().getUserKey();
                        String vendor_type = restaurantDetails.getVendor_type();
                        String vendor_key = restaurantDetails.getVendor_key();
                        CommonApiCalls.getInstance().favouriteAdd(mcontext, user_key, vendor_key, vendor_type, new CommonCallback.Listener() {
                            @Override
                            public void onSuccess(Object object) {
                                AddFavoriteApiResponse data = (AddFavoriteApiResponse) object;
                                holder.favorite_add.setChecked(true);
                                restaurantDetails.setUser_fav_check("1");
                                restaurantDetails.setFavorites_count(data.getData().getVendor_fav_count());
                                holder.tvFavoritesCounts.setText("("+(data.getData().getVendor_fav_count())+")");
                                MyApplication.result(data.getMessage());
                                notifyDataSetChanged();

                            }

                            @Override
                            public void onFailure(String reason) {

                            }
                        });
                    } else {
                        String userKey = SessionManager.getInstance().getUserKey();
                        String vendorKey = restaurantDetails.getVendor_key();
                        CommonApiCalls.getInstance().favouriteDelete(mcontext, userKey, vendorKey, new CommonCallback.Listener() {
                            @Override
                            public void onSuccess(Object object) {
                                DeleteFavoritesApiResponse data = (DeleteFavoritesApiResponse) object;
                                MyApplication.result(data.getMessage());
                                holder.favorite_add.setChecked(false);
                                restaurantDetails.setFavorites_count(restaurantDetails.getFavorites_count()-1);
                                restaurantDetails.setUser_fav_check("0");
                                holder.tvFavoritesCounts.setText("("+String.valueOf(restaurantDetails.getFavorites_count()-1)+")");
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(String reason) {

                            }
                        });
                    }
                }else{
                    holder.favorite_add.setChecked(false);
                    CommonFunctions.getInstance().ShowSnackBar(mcontext, "You must login to continue");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


}


