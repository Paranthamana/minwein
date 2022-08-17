package com.minwein.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minwein.customer.R;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.RatingAndReviewApiResponse;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;

import java.util.List;

/**
 * Created by Tech on 18-01-2018.
 */

public class RatingAndReviewRestaurantAdapter extends RecyclerView.Adapter<RatingAndReviewRestaurantAdapter.MyViewHolder> {
    private final List<RatingAndReviewApiResponse.Restaurant> ratinglist ;
    private Context mcontext ;
    public RatingAndReviewRestaurantAdapter(Context context, List<RatingAndReviewApiResponse.Restaurant> ratinglist) {
        this.mcontext= context;
        this.ratinglist = ratinglist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvHeaderName,tvRestaurantRatingBarValue,tvFirstText,tvFirstTextRatingBarValue,tvSecondText,
                tvSecondTextRatingBarValue,tvThirdText,tvThirdTextRatingBarValue,tvDescribtion;
        SimpleDraweeView sdvRateAndReviews;
        RatingBar rtbProductRating,rtbDeliverytimeRating,rtbFoodTemperatureRating,rtbCustomerService;
        public MyViewHolder(View itemView) {
            super(itemView);
            sdvRateAndReviews = (SimpleDraweeView) itemView.findViewById(R.id.sdvRateAndReviews);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvHeaderName =(TextView)itemView.findViewById(R.id.tvHeaderName);
            tvRestaurantRatingBarValue = (TextView) itemView.findViewById(R.id.tvRestaurantRatingBarValue);
            tvFirstText=(TextView)itemView.findViewById(R.id.tvFirstText);
            tvFirstTextRatingBarValue = (TextView) itemView.findViewById(R.id.tvFirstTextRatingBarValue);
            tvSecondText =(TextView)itemView.findViewById(R.id.tvSecondText);
            tvSecondTextRatingBarValue = (TextView) itemView.findViewById(R.id.tvSecondTextRatingBarValue);
            tvThirdText=(TextView)itemView.findViewById(R.id.tvThirdText);
            tvThirdTextRatingBarValue = (TextView) itemView.findViewById(R.id.tvThirdTextRatingBarValue);
            tvDescribtion =(TextView)itemView.findViewById(R.id.tvDescribtion);
            rtbProductRating=(RatingBar)itemView.findViewById(R.id.rtbProductRating);
            rtbDeliverytimeRating=(RatingBar)itemView.findViewById(R.id.rtbDeliverytimeRating);
            rtbFoodTemperatureRating=(RatingBar)itemView.findViewById(R.id.rtbFoodTemperatureRating);
            rtbCustomerService=(RatingBar)itemView.findViewById(R.id.rtbpriceRating);
            mcontext = itemView.getContext();
        }
    }


    @Override
    public RatingAndReviewRestaurantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_rating_and_reviews,parent,false);
        return new RatingAndReviewRestaurantAdapter.MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RatingAndReviewRestaurantAdapter.MyViewHolder holder, int position) {
        RatingAndReviewApiResponse.Restaurant rating_type = ratinglist.get(position);
        String itemImage = Urls.BASE_URL_STAGING + rating_type.getVendor_image();
        CommonFunctions.getInstance().loadImageByFresco(holder.sdvRateAndReviews, itemImage);
        holder.tvDate.setText(CommonFunctions.getInstance().formatDate(rating_type.getRating_review_datetime().toString(),"yyyy-MM-dd HH:mm:ss","MM dd,yyyy"));;
        holder.tvHeaderName.setText(rating_type.getVendor_name());
        holder.tvRestaurantRatingBarValue.setText("(".concat(rating_type.getRating_average()).concat(")"));
        holder.tvFirstText.setText(LanguageConstants.delivery_time);
        holder.tvFirstTextRatingBarValue.setText("(".concat(rating_type.getRating_Delivery_time()).concat(")"));
        holder.tvSecondText.setText(LanguageConstants.food_temperature);
        holder.tvSecondTextRatingBarValue.setText("(".concat(rating_type.getRating_Food_temperature()).concat(")"));
        holder.tvThirdText.setText(LanguageConstants.customer_service);
        holder.tvThirdTextRatingBarValue.setText("(".concat(rating_type.getRating_Customer_service()).concat(")"));
        holder.tvDescribtion.setText(rating_type.getVendor_description());
        holder.rtbProductRating.setRating(Float.valueOf(rating_type.getRating_average()));
        holder.rtbDeliverytimeRating.setRating(Float.valueOf(rating_type.getRating_Delivery_time()));
        holder.rtbFoodTemperatureRating.setRating(Float.valueOf(rating_type.getRating_Food_temperature()));
        holder.rtbCustomerService.setRating(Float.valueOf(rating_type.getRating_Customer_service()));



        //   RATING_TYPE=ratinglist.getRating_Type();
//        if(RATING_TYPE.equals("Food")){
//            RatingAndReviewApiResponse.Data foodratings = rating_type.getData();
//            holder.etFirsttext.setText("Taste");
//            holder.etSecondtext.setText("Timing");
//            holder.etThirdtext.setText("Taste");
//        }
//        else if (RATING_TYPE.equals("Restaurant")){
//            holder.etFirsttext.setText("Delivery Time");
//            holder.etSecondtext.setText("Food Temperature");
//            holder.etThirdtext.setText("Customer Service");
//        }
//        else {
//            holder.etFirsttext.setText("Taste");
//            holder.etSecondtext.setText("Price");
//            holder.etThirdtext.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return ratinglist.size();
    }


}
