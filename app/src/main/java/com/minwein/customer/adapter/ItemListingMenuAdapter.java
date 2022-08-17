package com.minwein.customer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.activity.MenuDetailActivity;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.VendorDetailsApiResponse;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

/**
 * Created by AMSHEER on 20-12-2017.
 */

public class ItemListingMenuAdapter extends RecyclerView.Adapter<ItemListingMenuAdapter.MyViewHolder> {
    private final VendorDetailsApiResponse.Item_category items;
    private final String restaurant;
    Context mcontext;

    public ItemListingMenuAdapter(Context mContext, VendorDetailsApiResponse.Item_category item_category, String restaurant) {
        this.mcontext = mContext;
        this.items = item_category;
        this.restaurant = restaurant;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName, tvItemDescription, tvItemPrize;
        Button btMenuAdd;
        SimpleDraweeView sdvItems;
        ImageView ivAdd;
        public MyViewHolder(View itemView) {
            super(itemView);
            btMenuAdd = (Button) itemView.findViewById(R.id.btMenuAdd);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            tvItemPrize = itemView.findViewById(R.id.tvItemPrize);
            sdvItems = itemView.findViewById(R.id.sdvItems);
            ivAdd = itemView.findViewById(R.id.ivAdd);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_list, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VendorDetailsApiResponse.Item item = items.getItems().get(position);
        holder.tvItemDescription.setText(item.getItem_description());
        holder.tvItemName.setText(item.getItem_name());
        holder.tvItemPrize.setText(SessionManager.getInstance().getCurrencySymbol()+ " "+(CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(item.getItem_price()))));
        String itemImage = Urls.BASE_URL_STAGING + item.getItem_image_path();
        CommonFunctions.getInstance().loadImageByFresco(holder.sdvItems, itemImage);
//        holder.btMenuAdd.setText(LanguageConstants.add);
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("item_key", item.getItem_key());
                bundle.putString("restaurant", restaurant);

                Gson gson = new Gson();
                String categories = gson.toJson(items);
                bundle.putString("category", categories);
//                gson.toJson(categories, VendorDetailsApiResponse.Item_category.class);

                CommonFunctions.getInstance().newIntent(mcontext, MenuDetailActivity.class, bundle, false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.getItems().size();
    }


}
