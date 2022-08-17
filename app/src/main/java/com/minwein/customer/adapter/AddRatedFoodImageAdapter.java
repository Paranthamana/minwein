package com.minwein.customer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minwein.customer.R;
import com.minwein.customer.activity.MenuDetailActivity;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.VendorDetailsApiResponse;
import com.minwein.customer.utils.CommonFunctions;

import java.util.List;

/**
 * Created by Tech on 09-02-2018.
 */

public class AddRatedFoodImageAdapter extends RecyclerView.Adapter<AddRatedFoodImageAdapter.MyViewHolder> {

    private Context mcontext;

    public AddRatedFoodImageAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRatedImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivRatedImage = itemView.findViewById(R.id.ivRatedImage);

        }
    }

    @Override
    public AddRatedFoodImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_add_rating_image, parent, false);
        return new AddRatedFoodImageAdapter.MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(AddRatedFoodImageAdapter.MyViewHolder holder, final int position) {

//        String itemImage = Urls.BASE_URL_STAGING + items.get(position).getItem_image_path();
//        CommonFunctions.getInstance().loadImageByFresco(holder.ivRatedImage, itemImage);

    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
