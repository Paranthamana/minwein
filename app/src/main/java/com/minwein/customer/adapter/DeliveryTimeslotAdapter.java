package com.minwein.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.api_model.RestaurantInformationApiResponse;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;

import java.util.List;

/**
 * Created by Tech on 19-Jan-2018.
 */

public class DeliveryTimeslotAdapter extends RecyclerView.Adapter<DeliveryTimeslotAdapter.MyViewHolder> {
    private Context mcontext;
    private List<RestaurantInformationApiResponse.DeliveryTimeslot> deliveryTimslotList;

    public DeliveryTimeslotAdapter(Context context, List<RestaurantInformationApiResponse.DeliveryTimeslot> deliveryTimslotList) {
        this.deliveryTimslotList = deliveryTimslotList;
        this.mcontext = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay, tvStartTime, tvEndTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            tvStartTime = (TextView) itemView.findViewById(R.id.tvStartTime);
            tvEndTime = (TextView) itemView.findViewById(R.id.tvEndTime);

        }
    }

    @Override
    public DeliveryTimeslotAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_delivery_houres, parent, false);
        return new DeliveryTimeslotAdapter.MyViewHolder(itemview);
    }


    @Override
    public void onBindViewHolder(DeliveryTimeslotAdapter.MyViewHolder holder, int position) {
        final RestaurantInformationApiResponse.DeliveryTimeslot TimslotList = deliveryTimslotList.get(position);

        String day = "";

        switch (TimslotList.getDayId()) {
            case "1":
                day = LanguageConstants.monday;
                break;
            case "2":
                day = LanguageConstants.tuesday;
                break;
            case "3":
                day = LanguageConstants.wednesday;
                break;
            case "4":
                day = LanguageConstants.thursday;
                break;
            case "5":
                day = LanguageConstants.friday;
                break;
            case "6":
                day = LanguageConstants.saturday;
                break;
            default:
                day = LanguageConstants.sunday;
                break;
        }

        holder.tvDay.setText(day);

//       String time= LocalTime.parse(TimslotList.getTimeslotStartTime(), DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a"));
        if (TimslotList.getTimeslotStartTime()==null && (TimslotList.getTimeslotEndTime()==null)) {
            holder.tvEndTime.setVisibility(View.GONE);
            holder.tvStartTime.setText(LanguageConstants.closed);
            holder.tvStartTime.setTextColor(MyApplication.context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tvStartTime.setText(CommonFunctions.getInstance().formatDate(TimslotList.getTimeslotStartTime().toString(),"HH:mm:ss","HH:mm a"));
            holder.tvEndTime.setText(CommonFunctions.getInstance().formatDate(TimslotList.getTimeslotEndTime().toString(),"HH:mm:ss","HH:mm a"));

        }
    }

    @Override
    public int getItemCount() {
        return deliveryTimslotList.size();
    }


}

