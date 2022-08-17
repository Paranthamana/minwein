package com.minwein.customer.adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.activity.CartSummaryActivity;
import com.minwein.customer.api_model.AddressListApiResponse;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by AMSHEER on 09-02-2018.
 */

public class CheckoutAddressListAdapter extends RecyclerView.Adapter<CheckoutAddressListAdapter.MyViewHolder>{
    private List<AddressListApiResponse.Datum> addresslist;
    private Context mcontext;

    public CheckoutAddressListAdapter(Context context, List<AddressListApiResponse.Datum> addresslist) {
        this.mcontext = context;
        this.addresslist = addresslist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddress, tv_addresstitle;
        RadioButton rbMarkAsDefault;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tv_addresstitle = (TextView) itemView.findViewById(R.id.tv_addresstitle);
            rbMarkAsDefault = (RadioButton) itemView.findViewById(R.id.rbMarkAsDefault);
            mcontext = itemView.getContext();

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_checkout_adderss_list,
                parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddressListApiResponse.Datum address = addresslist.get(position);
        String city = "",state="",zip="",country="";

        Geocoder geocoder = new Geocoder(mcontext, Locale.getDefault());
        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(address.getLatitude(),address.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            zip = addresses.get(0).getPostalCode();
            country = addresses.get(0).getCountryName();}

        String addressString = "";
        if (address.getAddress_line1()!= null && !address.getAddress_line1().equals("")){
            addressString = addressString + address.getAddress_line1() .trim()+ "\n";
        }
        if (address.getAddress_line2()!=null && !address.getAddress_line2().equals("")){
            addressString = addressString + address.getAddress_line2().trim() +","+ "\n";
        }
        if (address.getLand_mark()!=null && !address.getLand_mark().equals("")){
            addressString = addressString + address.getLand_mark() .trim()+","+"\n";
        }
        if (address.getCompany_name()!=null && !address.getCompany_name().equals("")){
            addressString = addressString + address.getCompany_name().trim()+".";
        }
//        if (address.getAddress_city()!=null && !address.getAddress_city().equals("")){
//            addressString = addressString + address.getAddress_city() + "\n";
//        }
//        if (address.getAddress_country()!=null && !address.getAddress_country().equals("")){
//            addressString = addressString + address.getAddress_country() + "\n";
//        }
//        if (address.getAddress_zip_code() != null && !address.getAddress_zip_code().equals("")) {
//            addressString = addressString + address.getAddress_zip_code() + "\n";
//        }
        holder.tvAddress.setText(addressString);

//        String addersstype = "";
//        if (address.getAddtype()!=null && !address.getAddtype().equals("")){
//            addersstype = LanguageConstants.Choose;
//        }else {
//            if (address.getAddtype().equals("")){
//            }
//        }
        if (address.getAddtype().equals("1")) {
            holder.tv_addresstitle.setText(LanguageConstants.home);
        } else if (address.getAddtype().equals("2")) {
            holder.tv_addresstitle.setText(LanguageConstants.Office);
        } else {
            holder.tv_addresstitle.setText(LanguageConstants.Work);
        }


        holder.rbMarkAsDefault.setText(LanguageConstants.chooseAddress);
        holder.rbMarkAsDefault.setTag(position);
        if (CommonFunctions.AddressIsDefault.fromInteger(address.getDefault_add()) == CommonFunctions.AddressIsDefault.Yes){
            holder.rbMarkAsDefault.setChecked(true);
        }else {
            holder.rbMarkAsDefault.setChecked(false);
        }
        holder.rbMarkAsDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int mPosition = (int) v.getTag();
                for(int Count = 0; Count < addresslist.size(); Count++) {
                    addresslist.get(Count).setDefault_add(CommonFunctions.AddressIsDefault.No.getValue());
                }
                addresslist.get(position).setDefault_add(CommonFunctions.AddressIsDefault.Yes.getValue());
                notifyDataSetChanged();
                CartSummaryActivity.address =  address;
                ((Activity)mcontext).finish();
            }
        });



    }
    private void notifyChange(List<AddressListApiResponse.Datum> addresslist) {
        this.addresslist = addresslist;
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return addresslist.size();
    }


}
