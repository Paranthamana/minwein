package com.minwein.customer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.utils.LanguageConstants;

/**
 * Created by AMSHEER on 19-12-2017.
 */

public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.MyViewHolder> {

    public OffersListAdapter() {
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView btnBuy;
        public MyViewHolder(View itemView) {
            super(itemView);
            btnBuy = itemView.findViewById(R.id.btnBuy);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_offers_list,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    holder.btnBuy.setText(LanguageConstants.buy);

    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
