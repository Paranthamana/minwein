package com.minwein.customer.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.app_interfaces.ChangeInCart;
import com.minwein.customer.model.Ingredients;
import com.minwein.customer.realm_db.CartRealmModel;
import com.minwein.customer.realm_db.IngredientsRealmModel;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Tech on 04-01-2018.
 */

public class CartDetailsItemAdapter extends RecyclerView.Adapter<CartDetailsItemAdapter.MyViewHolder> {

    private final RealmResults<CartRealmModel> cartList;
    private final ChangeInCart changeInCart;
    private Context context;

    public CartDetailsItemAdapter(Context context, RealmResults<CartRealmModel> cartLists, ChangeInCart changeInCart) {
        this.cartList = cartLists;
        this.context = context;
        this.changeInCart = changeInCart;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout llIngredients, llItem;
        private final ImageView ivDelete;
        ImageView ivItemsIncrease, ivItemsDecrease;
        TextView tvItemsQuantity, tvItemsName, tvItemsPrize;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivItemsIncrease = itemView.findViewById(R.id.ivItemsIncrease);
            ivItemsDecrease = itemView.findViewById(R.id.ivItemsDecrease);
            tvItemsQuantity = itemView.findViewById(R.id.tvItemsQuantity);
            tvItemsName = itemView.findViewById(R.id.tvItemsName);
            tvItemsPrize = itemView.findViewById(R.id.tvItemsPrize);
            llIngredients = itemView.findViewById(R.id.llIngredients);
            llItem = itemView.findViewById(R.id.llItem);
            ivDelete = itemView.findViewById(R.id.ivDelete);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cart_details_item, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvItemsQuantity.setText(String.valueOf(cartList.get(position).getQTY()));
        holder.tvItemsName.setText(cartList.get(position).getItem_name());


        holder.tvItemsPrize.setText(SessionManager.getInstance().getCurrencySymbol() + " " + (CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(cartList.get(position).getPrice()))));

        if (cartList.get(position).getIngredientsRealmModel().size() > 0) {
            for (int count = 0; count < cartList.get(position).getIngredientsRealmModel().size(); count++) {
                IngredientsRealmModel ingredients = cartList.get(position).getIngredientsRealmModel().get(count);
                View child = ((Activity) context).getLayoutInflater().inflate(R.layout.inflate_ingredients, null);
                TextView tvIngredientName = child.findViewById(R.id.tvIngredientName);
                tvIngredientName.setText(ingredients.getIngredient_name());
                TextView tvIngredientPrice = child.findViewById(R.id.tvIngredientPrice);
                tvIngredientPrice.setText(SessionManager.getInstance().getCurrencySymbol() + " " +
                        CommonFunctions.getInstance().roundOffDecimalValue(Double.valueOf(ingredients.getPrice())));

                holder.llIngredients.addView(child);

            }
        } else {
            holder.llIngredients.setVisibility(View.GONE);
        }

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_alert);
                TextView tvQue = dialog.findViewById(R.id.tvQue);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnYes = dialog.findViewById(R.id.btnYes);
                TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                tvTitle.setText(LanguageConstants.alert);
                tvQue.setText(LanguageConstants.areyousurewanttodeletethisitem);
                btnCancel.setText(LanguageConstants.cancel);
                btnYes.setText(LanguageConstants.yes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RealmLibrary.getInstance().deleteItem(cartList.get(position).getId());
                        dialog.dismiss();
                        holder.llItem.removeAllViews();
                        notifyDataSetChanged();
                        changeInCart.onClick();
                        if (cartList.size() == 0) {
                            ((Activity) context).finish();
                        }
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
        holder.ivItemsIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String quantity = holder.tvItemsQuantity.getText().toString();

                holder.tvItemsQuantity.setText(String.valueOf(Integer.parseInt(quantity) + 1));

                List<Ingredients> ingredientses = new ArrayList<>();
                for (int count = 0; count < cartList.get(position).getIngredientsRealmModel().size(); count++) {
                    Ingredients ingredients = new Ingredients();
                    IngredientsRealmModel ing = cartList.get(position).getIngredientsRealmModel().get(count);
                    ingredients.setIngredient_name(ing.getIngredient_name());
                    ingredients.setPrice(ing.getPrice());
                    ingredients.setIngredientsKey(ing.getIngredientsKey());
                    ingredients.setIngredientsId(ing.getIngredientsKey());
                    ingredients.setIngredientsQuantity(ing.getIngredientsQuantity());
                    ingredients.setGroupKey(ing.getGroupKey());
                    ingredientses.add(ingredients);
                }
                RealmLibrary.getInstance().insertItem(context, cartList.get(position).getItem_key(), cartList.get(position).getItem_key(), cartList.get(position).getItem_name(),
                        cartList.get(position).getItem_image(), String.valueOf(cartList.get(position).getItem_price_per_unit()),
                        String.valueOf(cartList.get(position).getCategory_id()), "", cartList.get(position).getVendor_key(), "1", String.valueOf(cartList.get(position).getItem_price_per_unit()),
                        String.valueOf(cartList.get(position).getItem_price_per_unit()), ingredientses, "1",
                        "", "1", cartList.get(position).getVendorName());
                changeInCart.onClick();

            }
        });
        holder.ivItemsDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String quantity = holder.tvItemsQuantity.getText().toString();
                if (Integer.parseInt(quantity) > 1) {


                    holder.tvItemsQuantity.setText(String.valueOf(Integer.parseInt(quantity) - 1));

                    List<Ingredients> ingredientses = new ArrayList<>();
                    for (int count = 0; count < cartList.get(position).getIngredientsRealmModel().size(); count++) {
                        Ingredients ingredients = new Ingredients();
                        IngredientsRealmModel ing = cartList.get(position).getIngredientsRealmModel().get(count);
                        ingredients.setIngredient_name(ing.getIngredient_name());
                        ingredients.setPrice(ing.getPrice());
                        ingredients.setIngredientsKey(ing.getIngredientsKey());
                        ingredients.setIngredientsId(ing.getIngredientsKey());
                        ingredients.setIngredientsQuantity(ing.getIngredientsQuantity());
                        ingredients.setGroupKey(ing.getGroupKey());
                        ingredientses.add(ingredients);
                    }
                    RealmLibrary.getInstance().insertItem(context, cartList.get(position).getItem_key(), cartList.get(position).getItem_key(), cartList.get(position).getItem_name(),
                            cartList.get(position).getItem_image(), String.valueOf(cartList.get(position).getItem_price_per_unit()),
                            String.valueOf(cartList.get(position).getCategory_id()), "", cartList.get(position).getVendor_key(), "2", String.valueOf(cartList.get(position).getItem_price_per_unit()),
                            String.valueOf(cartList.get(position).getItem_price_per_unit()), ingredientses, "1",
                            "", String.valueOf(Integer.parseInt(quantity) - 1).toString(), cartList.get(position).getVendorName());
                    changeInCart.onClick();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


}
