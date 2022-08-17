package com.minwein.customer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.activity.MenuDetailActivity;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.RestaurantListApiResponse;
import com.minwein.customer.api_model.VendorDetailsApiResponse;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.util.List;

/**
 * Created by AMSHEER on 15-02-2018.
 */

public class MapItemListingAdapter extends RecyclerView.Adapter<MapItemListingAdapter.MyViewHolder> {
    private final Context context;
    private final List<VendorDetailsApiResponse.Item> items;
    private final RestaurantListApiResponse.Data.Vendor_list restaurant;
    private final List<VendorDetailsApiResponse.Item_category> category;

    public MapItemListingAdapter(Context context, List<VendorDetailsApiResponse.Item> items,
                                 RestaurantListApiResponse.Data.Vendor_list restaurant, List<VendorDetailsApiResponse.Item_category> category) {
        this.context = context;
        this.items = items;
        this.restaurant = restaurant;
        this.category = category;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_map__food, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VendorDetailsApiResponse.Item item = items.get(position);
        holder.tvViewItem.setText(LanguageConstants.view);
        holder.tvItemName.setText(item.getItem_name());
//        holder.tvRestaurantName.setText(restaurant.getVendor_name());
        holder.tvFoodPrice.setText(SessionManager.getInstance().getCurrencySymbol()+" "+
                CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(item.getItem_price())));
        holder.tvFoodRatingText.setText("(" + restaurant.getRating_average()+ ")");
        holder.rtbFoodRating.setRating(Float.valueOf(restaurant.getRating_average()));
        String itemImage = Urls.BASE_URL_STAGING + item.getItem_image_path();
        CommonFunctions.getInstance().loadImageByFresco(holder.ivFood, itemImage);
        holder.tvViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String restaurant1 = gson.toJson(restaurant);
                Bundle bundle = new Bundle();
                bundle.putString("item_key", item.getItem_key());
                bundle.putString("restaurant", restaurant1);
                VendorDetailsApiResponse.Item_category categor = null;

                for (int count = 0; count < category.size(); count++){
                    categor = category.get(count);
                    for (int count1 = 0; count1 < category.get(count).getItems().size(); count1++){
                        if (item.getItem_key().equals(category.get(count).getItems().get(count1).getItem_key())){
                            categor = category.get(count);
                            break;
                        }
                    }
                }

                String categories = gson.toJson(categor);
                bundle.putString("category", categories);
                CommonFunctions.getInstance().newIntent(context, MenuDetailActivity.class, bundle, false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItemName;
        private final TextView tvRestaurantName;
        private final TextView tvFoodPrice;
        private final TextView tvViewItem;
        private final TextView tvFoodRatingText;
        private final RatingBar rtbFoodRating;
        private final SimpleDraweeView ivFood;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            tvViewItem = itemView.findViewById(R.id.tvViewItem);
            tvFoodRatingText = itemView.findViewById(R.id.tvFoodRatingText);
            rtbFoodRating = itemView.findViewById(R.id.rtbFoodRating);
            ivFood = itemView.findViewById(R.id.ivFood);
        }
    }
}
