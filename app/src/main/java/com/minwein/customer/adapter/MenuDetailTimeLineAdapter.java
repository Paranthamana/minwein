package com.minwein.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.api_model.ItemDetailsResponse;
import com.minwein.customer.utils.LanguageConstants;

import java.util.List;

/**
 * Created by Tech on 04-01-2018.
 */

public class MenuDetailTimeLineAdapter extends RecyclerView.Adapter<MenuDetailTimeLineAdapter.MyViewHolder> {
    Context mContext;
    private List<ItemDetailsResponse.Item_rating_info> itemTimeline;

    public MenuDetailTimeLineAdapter(Context context,List<ItemDetailsResponse.Item_rating_info> itemTimeline ) {
        this.mContext=context;
        this.itemTimeline=itemTimeline;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTasteTitle,tvTimingTitle,tvPriceTitle,tvRateText,tvTimeLineName;
        RatingBar rtbTimeLineRatingTaste,rtbTimeLineRatingTiming,rtbTimeLineRatingPrice;
        ImageView ivTimeLine;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvTasteTitle =(TextView)itemView.findViewById(R.id.tvTasteTitle);
            tvTimingTitle =(TextView)itemView.findViewById(R.id.tvTimingTitle);
            tvPriceTitle =(TextView)itemView.findViewById(R.id.tvPriceTitle);
            tvRateText =(TextView)itemView.findViewById(R.id.tvRateText);
            rtbTimeLineRatingTaste =(RatingBar)itemView.findViewById(R.id.rtbTimeLineRatingTaste);
            rtbTimeLineRatingTiming =(RatingBar)itemView.findViewById(R.id.rtbTimeLineRatingTiming);
            rtbTimeLineRatingPrice =(RatingBar)itemView.findViewById(R.id.rtbTimeLineRatingPrice);
            tvTimeLineName =(TextView) itemView.findViewById(R.id.tvTimeLineName);
            ivTimeLine = itemView.findViewById(R.id.ivTimeLine);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_menu_detail_time_line,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ItemDetailsResponse.Item_rating_info itemTimelineList = itemTimeline.get(position);
        int[] colors = new int[] { R.drawable.xml_shape_rounded_circlegreen, R.drawable.xml_shape_rounded_circlered,R.drawable.xml_shape_rounded_circleblue };

        int colorPos = position % colors.length;
        holder.ivTimeLine.setImageResource(colors[colorPos]);

//        for (int count = 0;count<itemTimeline.size();count++){
//            holder.ivTimeLine.setImageResource(R.drawable.xml_shape_rounded_circlegreen);
//            holder.ivTimeLine.setImageResource(R.drawable.xml_shape_rounded_circlered);
//            holder.ivTimeLine.setImageResource(R.drawable.xml_shape_rounded_circleblue);
//        }

        holder.tvPriceTitle.setText(LanguageConstants.price);
        holder.tvTasteTitle.setText(LanguageConstants.taste);
        holder.tvTimingTitle.setText(LanguageConstants.timing);
        holder.tvRateText.setText(itemTimelineList.getRating_review_text());
        holder.rtbTimeLineRatingPrice.setRating(Float.valueOf(itemTimelineList.getRating_Price()));
        holder.rtbTimeLineRatingTiming.setRating(Float.valueOf(itemTimelineList.getRating_Timing()));
        holder.rtbTimeLineRatingTaste.setRating(Float.valueOf(itemTimelineList.getRating_Taste()));
        holder.tvTimeLineName.setText(itemTimelineList.getFirst_name());
    }

    @Override
    public int getItemCount() {
        return itemTimeline.size();
    }


}
