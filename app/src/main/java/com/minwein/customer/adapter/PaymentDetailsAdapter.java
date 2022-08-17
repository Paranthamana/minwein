package com.minwein.customer.adapter;

import android.content.Context;
import android.os.Build;
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
 * Created by Tech on 23-01-2018.
 */

public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsAdapter.MyViewHolder> {

    private List<OrderDetailsApiResponse.Payment_detail> orderDetailitem;
    public Context context;

    public PaymentDetailsAdapter(Context context, List<OrderDetailsApiResponse.Payment_detail> orderDetailItem) {
        this.context = (Context) context;
        this.orderDetailitem = orderDetailItem;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderDetailsSubTotalText, tvOrderDetailsSubTotal;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvOrderDetailsSubTotalText = (TextView) itemView.findViewById(R.id.tvOrderDetailsSubTotalText);
            tvOrderDetailsSubTotal = (TextView) itemView.findViewById(R.id.tvOrderDetailsSubTotal);
        }
    }


    @Override
    public PaymentDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_payment_details, parent, false);
        return new PaymentDetailsAdapter.MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PaymentDetailsAdapter.MyViewHolder holder, int position) {
        OrderDetailsApiResponse.Payment_detail orderDetailItem = orderDetailitem.get(position);

        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {
            holder.tvOrderDetailsSubTotal.setGravity(Gravity.CENTER | Gravity.START);
            holder.tvOrderDetailsSubTotalText.setGravity(Gravity.CENTER|Gravity.START);
        } else {
            holder.tvOrderDetailsSubTotal.setGravity(Gravity.CENTER | Gravity.END);
            holder.tvOrderDetailsSubTotalText.setGravity(Gravity.CENTER|Gravity.START);
        }

        holder.tvOrderDetailsSubTotalText.setText(orderDetailItem.getName());
        holder.tvOrderDetailsSubTotal.setText(SessionManager.getInstance().getCurrencySymbol() + " " + (CommonFunctions.getInstance().roundOffDecimalValue(Double.parseDouble(orderDetailItem.getValue().toString()))));
    }

    @Override
    public int getItemCount() {
        return orderDetailitem.size();
    }
}
