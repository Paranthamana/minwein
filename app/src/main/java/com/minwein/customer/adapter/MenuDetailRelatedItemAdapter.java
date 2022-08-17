package com.minwein.customer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.activity.MenuDetailActivity;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.VendorDetailsApiResponse;
import com.minwein.customer.utils.CommonFunctions;

import java.util.List;

/**
 * Created by Tech on 04-01-2018.
 */

public class MenuDetailRelatedItemAdapter extends RecyclerView.Adapter<MenuDetailRelatedItemAdapter.MyViewHolder> {

    private final List<VendorDetailsApiResponse.Item> items;
    private final String categoryString;
    private final String restaurant;
    private Context mcontext;

    public MenuDetailRelatedItemAdapter(Context mcontext, List<VendorDetailsApiResponse.Item> items, String categoryString, String restaurant) {
        this.mcontext = mcontext;
    this.items = items;
    this.categoryString = categoryString;
    this.restaurant = restaurant;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRelatedItemsName;
        SimpleDraweeView sdvRelatedItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvRelatedItemsName = (TextView) itemView.findViewById(R.id.tvRelatedItemsName);
            sdvRelatedItem = (SimpleDraweeView) itemView.findViewById(R.id.sdvRelatedItem);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_menu_detail_related_item, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvRelatedItemsName.setText(items.get(position).getItem_name());

        String itemImage = Urls.BASE_URL_STAGING + items.get(position).getItem_image_path();
        CommonFunctions.getInstance().loadImageByFresco(holder.sdvRelatedItem, itemImage);

        holder.sdvRelatedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("item_key", items.get(position).getItem_key());

                bundle.putString("category", categoryString);
                bundle.putString("restaurant", restaurant);
                CommonFunctions.getInstance().newIntent(mcontext, MenuDetailActivity.class,bundle,true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
