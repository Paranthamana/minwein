package com.minwein.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.app_interfaces.NavigationClick;
import com.minwein.customer.enums.NavigationMenuEnum;
import com.minwein.customer.model.NavigationMenuModel;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** * Created by AMSHEER on 06-12-2017.
 */

public class NavigationMenuAdapter extends BaseAdapter {
    private final List<NavigationMenuModel> navigationMenuModels;
    private final Context context;
    private final NavigationClick navigationClick;

    public NavigationMenuAdapter(Context context, List<NavigationMenuModel> navigationMenuModels, NavigationClick navigationClick) {
        this.context = context;
        this.navigationMenuModels = navigationMenuModels;
        this.navigationClick = navigationClick;
    }

    @Override
    public int getCount() {
        return navigationMenuModels.size();
    }

    @Override
    public NavigationMenuModel getItem(int position) {
        return navigationMenuModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_navigation_menu, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final NavigationMenuModel navigationItem = getItem(position);
        holder.ivNavigationIcon.setImageResource(navigationItem.getNavigationIcon());
        if (navigationItem.getNavigationItemId() != NavigationMenuEnum.LOGOUT) {
            holder.tvNavigationName.setText(navigationItem.getNavigationItemName());
        } else {
            if (!SessionManager.getInstance().getUserKey().isEmpty()) {
                holder.tvNavigationName.setText(navigationItem.getNavigationItemName());
            }else{
                holder.tvNavigationName.setText(LanguageConstants.logIn);
            }
        }

        if (navigationItem.getNavigationItemId() == NavigationMenuEnum.LOYALTY_POINTS) {
            if(!SessionManager.getInstance().getUserKey().isEmpty())
            {
                holder.tvLoyaltyPoints.setText("("+SessionManager.getInstance().getLoyaltyPoints().toString()+")");
                holder.tvLoyaltyPoints.setVisibility(View.VISIBLE);
        }
        else {
                holder.tvLoyaltyPoints.setVisibility(View.GONE);
            }
        }

        if (navigationItem.getNavigationItemId() == NavigationMenuEnum.CART){
            if (SessionManager.getInstance().getUserKey().isEmpty()){
                holder.tvLoyaltyPoints.setVisibility(View.GONE);
            }
           else if (RealmLibrary.getInstance().getCartSize() == 0) {
                holder.tvLoyaltyPoints.setVisibility(View.VISIBLE);
                holder.tvLoyaltyPoints.setText("("+"0"+")");
            } else {
                holder.tvLoyaltyPoints.setText("("+String.valueOf(RealmLibrary.getInstance().getCartSize()+")"));
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationClick.onClick(navigationItem);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ivNavigationIcon)
        ImageView ivNavigationIcon;
        @BindView(R.id.tvNavigationName)
        TextView tvNavigationName;
        @BindView(R.id.tvLoyaltyPoints)
        TextView tvLoyaltyPoints;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
