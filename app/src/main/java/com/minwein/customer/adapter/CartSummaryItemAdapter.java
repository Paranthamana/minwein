package com.minwein.customer.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.activity.CartDetailsActivity;
import com.minwein.customer.activity.CartSummaryActivity;
import com.minwein.customer.api_model.CartVerifyApiResponse;
import com.minwein.customer.app_interfaces.ChangeInCart;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.RatedItemImage;
import com.minwein.customer.utils.SessionManager;

import java.util.List;

/**
 * Created by Tech on 04-01-2018.
 */

public class CartSummaryItemAdapter extends RecyclerView.Adapter<CartSummaryItemAdapter.MyViewHolder> {

    private final ChangeInCart changeInCart;
    List<CartVerifyApiResponse.Cart> cartList;
    private final Context mcontext;

    public CartSummaryItemAdapter(Context mcontext, List<CartVerifyApiResponse.Cart> cart, ChangeInCart changeInCart) {
        this.cartList = cart;
        this.mcontext = mcontext;
        this.changeInCart = changeInCart;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTotal;
        private final TextView tvVendorName;
        private final TextView tvErrorMessage;
        private final ImageView ivWarning;
        RelativeLayout rlCartSummary;
        ImageView ivListDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvVendorName = itemView.findViewById(R.id.tvVendorName);
            rlCartSummary = itemView.findViewById(R.id.rlCartSummary);
            ivListDelete = itemView.findViewById(R.id.ivListDelete);
            tvErrorMessage = itemView.findViewById(R.id.tvErrorMessage);
            ivWarning = itemView.findViewById(R.id.ivWarning);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cart_summary_item_list, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CartVerifyApiResponse.Cart cart = cartList.get(position);
        if (cart.getVendor_total_array() != null && cart.getVendor_total_array().getVendorTotal() != null) {
            holder.tvTotal.setText(SessionManager.getInstance().getCurrencySymbol()
                    +" "+(CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(cart.getVendor_total_array().getVendorTotal()))));
        } else {
            holder.tvTotal.setText("");
        }
        if (cart.getError_key().equals(0)) {
            holder.tvErrorMessage.setVisibility(View.GONE);
        } else {
            holder.tvErrorMessage.setVisibility(View.VISIBLE);
            holder.tvErrorMessage.setText(cart.getError_message());
        }
        if (isvendorExist(cart.getVendor_key())){
            holder.ivWarning.setVisibility(View.VISIBLE);
            holder.ivWarning.setImageResource(R.drawable.ic_infochecked);
        }else {
            holder.ivWarning.setVisibility(View.VISIBLE);
            holder.ivWarning.setImageResource(R.drawable.ic_infocheck);
        }
        holder.tvVendorName.setText(cart.getVendor_name());
        holder.rlCartSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.getError_key().equals(0)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("vendor_key", cart.getVendor_key());
                    CommonFunctions.getInstance().newIntent(mcontext, CartDetailsActivity.class, bundle, false);
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(mcontext, cart.getError_message());
                }
            }
        });
        holder.ivListDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mcontext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_alert);
                TextView tvQue = dialog.findViewById(R.id.tvQue);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnYes = dialog.findViewById(R.id.btnYes);
                TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                tvTitle.setText(LanguageConstants.alert);
                tvQue.setText(LanguageConstants.areYouSureWantToDeleteThisRestaurant);
                btnCancel.setText(LanguageConstants.cancel);
                btnYes.setText(LanguageConstants.yes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        RealmLibrary.getInstance().deleteVendorItem(cartList.get(position).getVendor_key());
                        changeInCart.onClick();

//                        holder.rlCartSummary.removeAllViews();
                        /*if (cartList.size() == 0){
                            ((Activity) mcontext).finish();
                        }*/
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                window.setAttributes(wlp);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    private boolean isvendorExist(String vendor_key) {
        for (int count = 0;count<CartSummaryActivity.deliveryOptions.size();count++){
            if (vendor_key.equals(CartSummaryActivity.deliveryOptions.get(count).getVendor_key() ))
            {
                if(CartSummaryActivity.deliveryOptions.get(count).getDetailFilled()) {
                    return true;
                }
            }
        }
        return false;
    }
}
