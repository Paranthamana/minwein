package com.minwein.customer.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.api_model.RestaurantRatingApiResponse;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Tech on 04-01-2018.
 */

public class RestaurantRatingAdapter extends RecyclerView.Adapter<RestaurantRatingAdapter.MyViewHolder> {
    private Context mContext;
    private List<RestaurantRatingApiResponse.Data.Vendor_rating> restaurantRatingList;

    public RestaurantRatingAdapter(Context context, List<RestaurantRatingApiResponse.Data.Vendor_rating> restaurantRatingList) {
        this.mContext = context;
        this.restaurantRatingList = restaurantRatingList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RatingBar rtbRatingDeliveryTime,rtbRatingFoodTemperature,rtbRatingCustomerService;
        TextView tvRatingsName,tvRestaurantRatingFirstName,tvRestaurantRatingFirstNameValue,tvRestaurantRatingSecondName,tvRestaurantRatingSecondNameValue,
                tvRestaurantRatingThirdName,tvRestaurantRatingThirdNameValue,tvRestaurantRatingDateTime,tvRestaurantRatingDescription;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvRatingsName = itemView.findViewById(R.id.tvRatingsName);
            tvRestaurantRatingFirstName = itemView.findViewById(R.id.tvRestaurantRatingFirstName);
            tvRestaurantRatingFirstNameValue = itemView.findViewById(R.id.tvRestaurantRatingFirstNameValue);
            tvRestaurantRatingSecondName = itemView.findViewById(R.id.tvRestaurantRatingSecondName);
            tvRestaurantRatingSecondNameValue = itemView.findViewById(R.id.tvRestaurantRatingSecondNameValue);
            tvRestaurantRatingThirdName = itemView.findViewById(R.id.tvRestaurantRatingThirdName);
            tvRestaurantRatingThirdNameValue = itemView.findViewById(R.id.tvRestaurantRatingThirdNameValue);
            tvRestaurantRatingDateTime = itemView.findViewById(R.id.tvRestaurantRatingDateTime);
            tvRestaurantRatingDescription = itemView.findViewById(R.id.tvRestaurantRatingDescription);
            rtbRatingDeliveryTime=itemView.findViewById(R.id.rtbRatingDeliveryTime);
            rtbRatingFoodTemperature=itemView.findViewById(R.id.rtbRatingFoodTemperature);
            rtbRatingCustomerService=itemView.findViewById(R.id.rtbRatingCustomerService);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_restaurant_rating_list,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RestaurantRatingApiResponse.Data.Vendor_rating restaurantrating = restaurantRatingList.get(position);
        holder.tvRatingsName.setText(restaurantrating.getFirst_name() + (restaurantrating.getLast_name()));
        holder.tvRestaurantRatingFirstName.setText(LanguageConstants.delivery_time);
        holder.tvRestaurantRatingFirstNameValue.setText("(" + (restaurantrating.getRating_Delivery_time()) + ")");
        holder.tvRestaurantRatingSecondName.setText(LanguageConstants.food_temperature);
        holder.tvRestaurantRatingSecondNameValue.setText("(" + (restaurantrating.getRating_Food_temperature()) + ")");
        holder.tvRestaurantRatingThirdName.setText(LanguageConstants.customer_service);
        holder.tvRestaurantRatingThirdNameValue.setText("(" + (restaurantrating.getRating_Customer_service()) + ")");
        holder.tvRestaurantRatingDescription.setText(restaurantrating.getRating_review_text());
        holder.rtbRatingDeliveryTime.setRating(Float.valueOf(restaurantrating.getRating_Delivery_time()));
        holder.rtbRatingCustomerService.setRating(Float.valueOf(restaurantrating.getRating_Customer_service()));
        holder.rtbRatingFoodTemperature.setRating(Float.valueOf(restaurantrating.getRating_Food_temperature()));
        holder.tvRestaurantRatingDateTime.setText(CommonFunctions.getInstance().formatDate(restaurantrating.getRating_review_datetime().toString(),"yyyy-MM-dd HH:mm:ss","MMM dd,yyyy"));
    }

    @Override
    public int getItemCount() {
        return restaurantRatingList.size();
    }


}
