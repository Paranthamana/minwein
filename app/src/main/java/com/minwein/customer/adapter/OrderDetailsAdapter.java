package com.minwein.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.api_model.OrderDetailsApiResponse;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;


import java.util.List;

/**
 * Created by Tech on 24-11-2017.
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {

    private List<OrderDetailsApiResponse.Item> orderDetailItems;
    public Context context;

    public OrderDetailsAdapter(Context context, List<OrderDetailsApiResponse.Item> orderDetailItems) {
        this.context = (Context) context;
        this.orderDetailItems = orderDetailItems;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderedItemName,tvQty,tvPrice;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvOrderedItemName = (TextView) itemView.findViewById(R.id.tvOrderedItemName);
            tvQty = (TextView) itemView.findViewById(R.id.tvQty);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_orderdetails,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderDetailsApiResponse.Item orderDetailItem = orderDetailItems.get(position);

//        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {
//            holder.tvOrderDetailsSubTotal.setGravity(Gravity.CENTER | Gravity.START);
//            holder.tvOrderDetailsSubTotalText.setGravity(Gravity.CENTER|Gravity.START);
//        } else {
//            holder.tvOrderDetailsSubTotal.setGravity(Gravity.CENTER | Gravity.END);
//            holder.tvOrderDetailsSubTotalText.setGravity(Gravity.CENTER|Gravity.START);
//        }


        holder.tvOrderedItemName.setText(orderDetailItem.getItem_name());
        holder.tvQty.setText(orderDetailItem.getQuantity().toString());
        holder.tvPrice.setText(SessionManager.getInstance().getCurrencySymbol()+" "+(CommonFunctions.getInstance().roundOffDecimalValue(Double.parseDouble(orderDetailItem.getItem_price().toString()))));
    }

    @Override
    public int getItemCount() {
        return orderDetailItems.size();
    }
}
