package com.minwein.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.minwein.customer.R;

import java.util.List;

/**
 * Created by AMSHEER on 15-02-2018.
 */

public class FilterCuisines extends RecyclerView.Adapter<FilterCuisines.MyViewHolder> {
    private final Context context;
    private final List<String> cuisinesList;
    private final List<String> selectedCuisines;

    public FilterCuisines(Context context, List<String> cuisinesList, List<String> selectedCuisines) {
        this.context = context;
        this.cuisinesList = cuisinesList;
        this.selectedCuisines = selectedCuisines;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_filter_cuisines, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String cuisine = cuisinesList.get(position);
        holder.cbCuisines.setText(cuisine);

        if (selectedCuisines.contains(cuisine)){
            holder.cbCuisines.setChecked(true);
        }else{
            holder.cbCuisines.setChecked(false);
        }
        holder.cbCuisines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCuisines.contains(cuisine)){
                    selectedCuisines.remove(cuisine);
                    holder.cbCuisines.setChecked(false);
                }else{
                    selectedCuisines.add(cuisine);
                    holder.cbCuisines.setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cuisinesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox cbCuisines;

        public MyViewHolder(View itemView) {
            super(itemView);
            cbCuisines = (CheckBox) itemView.findViewById(R.id.cbCuisines);
        }
    }
}
