package com.minwein.customer.adapter;

import android.annotation.SuppressLint;
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
import com.minwein.customer.utils.SessionManager;

import java.util.List;

/**
 * Created by Tech on 19-Jan-2018.
 */

public class PickTimeslotAdapter extends RecyclerView.Adapter<PickTimeslotAdapter.MyViewHolder> {
    private Context mcontext;
    private List<RestaurantInformationApiResponse.PickupTimeslot> PickupTimslotList;

    public PickTimeslotAdapter(Context context, List<RestaurantInformationApiResponse.PickupTimeslot> PickupTimslotList) {
        this.PickupTimslotList = PickupTimslotList;
        this.mcontext = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay, tvStartTime, tvEndTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDay = (TextView) itemView.findViewById(R.id.tvpickDay);
            tvStartTime = (TextView) itemView.findViewById(R.id.tvPickupStartTime);
            tvEndTime = (TextView) itemView.findViewById(R.id.tvPickupendtime);

        }
    }

    @Override
    public PickTimeslotAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pickup_houres, parent, false);
        return new PickTimeslotAdapter.MyViewHolder(itemview);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(PickTimeslotAdapter.MyViewHolder holder, int position) {
        final RestaurantInformationApiResponse.PickupTimeslot TimslotList = PickupTimslotList.get(position);

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
        if (TimslotList.getTimeslotStartTime() == null && TimslotList.getTimeslotEndTime() == null) {
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
        return PickupTimslotList.size();
    }


}

