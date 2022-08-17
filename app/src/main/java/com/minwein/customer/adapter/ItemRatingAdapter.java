package com.minwein.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.api_model.OrderDetailsApiResponse;
import com.minwein.customer.model.RatingModel;
import com.minwein.customer.utils.LanguageConstants;

import java.util.List;

/**
 * Created by Tech on 22-02-2018.
 */

public class ItemRatingAdapter extends RecyclerView.Adapter<ItemRatingAdapter.MyViewHolder> {

    private List<OrderDetailsApiResponse.Item> orderDetailItems;
    private Context mcontext;

    String TasteRating;
    String TimingRating;
    String PriceRating;

    public ItemRatingAdapter(Context context, List<OrderDetailsApiResponse.Item> orderDetailItems) {
        this.mcontext = context;
        this.orderDetailItems = orderDetailItems;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final EditText etcomment;
        TextView tvTaste, tvTiming, tvPrice,tvTasteRate, tvTimingRate, tvPriceRate,tvItemName;
        RatingBar rtbTasteRating, rtbTimingRating, rtbPriceRating;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvTaste = itemView.findViewById(R.id.tvTaste);
            tvTiming = itemView.findViewById(R.id.tvTiming);
            tvPrice = itemView.findViewById(R.id.tvFoodPrice);
            tvTasteRate = itemView.findViewById(R.id.tvTasteratingValue);
            tvTimingRate = itemView.findViewById(R.id.tvTimingratingValue);
            tvPriceRate = itemView.findViewById(R.id.tvPriceratingValue);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            rtbTasteRating = itemView.findViewById(R.id.rtbTasteRating);
            rtbTimingRating = itemView.findViewById(R.id.rtbTimingRating);
            rtbPriceRating = itemView.findViewById(R.id.rtbPriceRating);
            etcomment = itemView.findViewById(R.id.etcomment);

        }
    }

    @Override
    public ItemRatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_addratingadapter, parent, false);
        return new ItemRatingAdapter.MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final ItemRatingAdapter.MyViewHolder holder, final int position) {
        OrderDetailsApiResponse.Item orderDetailItem = orderDetailItems.get(position);
        holder.tvTaste.setText(LanguageConstants.taste);
        holder.tvTiming.setText(LanguageConstants.timing);
        holder.tvPrice.setText(LanguageConstants.price);
        holder.etcomment.setHint(LanguageConstants.comments);
        holder.tvItemName.setText(orderDetailItem.getItem_name());

        holder.rtbTasteRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                holder.tvTasteRate.setText("(" + String.valueOf(rating) + ")");
            }
        });
        holder.rtbTimingRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                holder.tvTimingRate.setText("(" + String.valueOf(rating) + ")");
            }
        });
        holder.rtbPriceRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                holder.tvPriceRate.setText("(" + String.valueOf(rating) + ")");
            }
        });

       holder.rtbTasteRating.setOnRatingBarChangeListener(onRatingBarChangeListener(holder,position,orderDetailItems.get(position).getItem_name(), "TASTE"));
       holder.rtbTimingRating.setOnRatingBarChangeListener(onRatingBarChangeListener(
               holder,position,orderDetailItems.get(position).getItem_name(), "TIMING"));
       holder.rtbPriceRating.setOnRatingBarChangeListener(onRatingBarChangeListener(holder,position,orderDetailItems.get(position).getItem_name(),
               "PRICE"));

       holder.rtbTasteRating.setTag(position);
       holder.rtbTimingRating.setTag(position);
       holder.rtbPriceRating.setTag(position);

       holder.rtbTasteRating.setRating(getRatingValue(orderDetailItems.get(position).getRandom_key(), "TASTE"));
       holder.rtbTimingRating.setRating(getRatingValue(orderDetailItems.get(position).getRandom_key(), "TIMING"));
       holder.rtbPriceRating.setRating(getRatingValue(orderDetailItems.get(position).getRandom_key(), "PRICE"));

       holder.etcomment.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
               if (OrderListAdapter.model.size() != 0){
                   Boolean isExists = false;
                   for (int count = 0; count < OrderListAdapter.model.size(); count++) {
                       if (OrderListAdapter.model.get(count).getRandomKey().equals(orderDetailItems.get(position).getRandom_key())) {
                           RatingModel ratingModel = OrderListAdapter.model.get(count);
                           ratingModel.setItem_id(String.valueOf(orderDetailItems.get(position).getItem_id()));
                           ratingModel.setItem_rating_review_text(holder.etcomment.getText().toString());
                           isExists = true;
                           break;
                       }
                   }
                   if (!isExists) {
                       RatingModel ratingModel = new RatingModel();
                       ratingModel.setItem_rating_review_text(holder.etcomment.getText().toString());
                       ratingModel.setRandomKey(orderDetailItems.get(position).getRandom_key());
                       ratingModel.setItem_id(String.valueOf(orderDetailItems.get(position).getItem_id()));
                       ratingModel.setItem_rating_review_text("");
                       OrderListAdapter.model.add(ratingModel);
                   }
               }else{
                   RatingModel ratingModel = new RatingModel();

                   ratingModel.setItem_rating_review_text(holder.etcomment.getText().toString());
                   ratingModel.setRandomKey(orderDetailItems.get(position).getRandom_key());
                   ratingModel.setItem_id(String.valueOf(orderDetailItems.get(position).getItem_id()));
                   ratingModel.setItem_rating_review_text("");
                   OrderListAdapter.model.add(ratingModel);
               }
           }
       });

    }

    private float getRatingValue(Integer randomKey, String text) {
        for (int count = 0; count < OrderListAdapter.model.size(); count++) {
            if (OrderListAdapter.model.get(count).getRandomKey().equals(randomKey)) {
                RatingModel ratingModel = OrderListAdapter.model.get(count);

                switch (text) {
                    case "TASTE":
                        return Float.valueOf(ratingModel.getTaste());
                    case "TIMING":
                        return Float.valueOf(ratingModel.getTiming());
                    case "PRICE":
                        return Float.valueOf(ratingModel.getPrice());
                    default:
                        return 0.0f;
                }

            }
        }
        return 0.0f;
    }



    private RatingBar.OnRatingBarChangeListener onRatingBarChangeListener(final MyViewHolder myViewHolder, final int postion, final String name,

                                                                          final String text){
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (OrderListAdapter.model.size() != 0){
                    Boolean isExists = false;
                    for (int count = 0; count < OrderListAdapter.model.size(); count++) {
                        if (OrderListAdapter.model.get(count).getRandomKey().equals(orderDetailItems.get(postion).getRandom_key())) {
                            RatingModel ratingModel = OrderListAdapter.model.get(count);
                            switch (text) {
                                case "TASTE":
                                    ratingModel.setTaste(String.valueOf(rating));
                                    break;
                                case "TIMING":
                                    ratingModel.setTiming(String.valueOf(rating));
                                    break;
                                case "PRICE":
                                    ratingModel.setPrice(String.valueOf(rating));
                                    break;
                                default:

                                    break;
                            }


                            ratingModel.setItem_id(String.valueOf(orderDetailItems.get(postion).getItem_id()));
                            ratingModel.setRandomKey(orderDetailItems.get(postion).getRandom_key());
                            isExists = true;
                            break;
                        }
                    }
                    if (!isExists) {
                        RatingModel ratingModel = new RatingModel();
                        switch (text) {
                            case "TASTE":
                                ratingModel.setTaste(String.valueOf(rating));
                                break;
                            case "TIMING":
                                ratingModel.setTiming(String.valueOf(rating));
                                break;
                            case "PRICE":
                                ratingModel.setPrice(String.valueOf(rating));
                                break;
                            default:

                                break;
                        }
                        ratingModel.setItem_id(String.valueOf(orderDetailItems.get(postion).getItem_id()));
                        ratingModel.setRandomKey(orderDetailItems.get(postion).getRandom_key());
                        ratingModel.setItem_rating_review_text("");
                        OrderListAdapter.model.add(ratingModel);
                    }
                } else {




                    RatingModel ratingModel = new RatingModel();
                    switch (text) {
                        case "TASTE":
                            ratingModel.setTaste(String.valueOf(rating));
                            break;
                        case "TIMING":
                            ratingModel.setTiming(String.valueOf(rating));
                            break;
                        case "PRICE":
                            ratingModel.setPrice(String.valueOf(rating));
                            break;
                        default:

                            break;
                    }
                    ratingModel.setItem_id(String.valueOf(orderDetailItems.get(postion).getItem_id()));
                    ratingModel.setRandomKey(orderDetailItems.get(postion).getRandom_key());
                    ratingModel.setItem_rating_review_text("");
                    OrderListAdapter.model.add(ratingModel);

                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return orderDetailItems.size();
    }


}
