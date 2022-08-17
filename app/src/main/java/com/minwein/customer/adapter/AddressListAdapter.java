package com.minwein.customer.adapter;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.activity.AddNewAddress;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api_model.AddressListApiResponse;
import com.minwein.customer.api_model.DefaultAddressApiResponse;
import com.minwein.customer.api_model.DeleteAddressApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.utils.AddressLatLng;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by AMSHEER on 19-12-2017.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {
    private List<AddressListApiResponse.Datum> addresslist;
    private Context mcontext;

    public AddressListAdapter(Context mcontext, List<AddressListApiResponse.Datum> addresslist) {
        this.mcontext = mcontext;
        this.addresslist = addresslist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddress, tv_addresstitle;
        ImageView imAddressEdit, imAddressDelete;
        RadioButton rbMarkAsDefault;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tv_addresstitle = (TextView) itemView.findViewById(R.id.tv_addresstitle);
            imAddressEdit = (ImageView) itemView.findViewById(R.id.imAddressEdit);
            imAddressDelete = (ImageView) itemView.findViewById(R.id.imAddressDelete);
            rbMarkAsDefault = (RadioButton) itemView.findViewById(R.id.rbMarkAsDefault);
            mcontext = itemView.getContext();

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_adderss_list, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AddressListApiResponse.Datum address = addresslist.get(position);
        String city = "", state = "", zip = "", country = "";

        Geocoder geocoder = new Geocoder(mcontext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(address.getLatitude(), address.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            zip = addresses.get(0).getPostalCode();
            country = addresses.get(0).getCountryName();
        }

        String addressString = "";
        if (address.getAddress_line1() != null && !address.getAddress_line1().equals("")) {
            addressString = addressString + address.getAddress_line1().trim() + "\n";
        }
        if (address.getAddress_line2() != null && !address.getAddress_line2().equals("")) {
            addressString = addressString + address.getAddress_line2().trim() + "," + "\n";
        }
        if (address.getLand_mark() != null && !address.getLand_mark().equals("")) {
            addressString = addressString + address.getLand_mark().trim() + "," + "\n";
        }
        if (address.getCompany_name() != null && !address.getCompany_name().equals("")) {
            addressString = addressString + address.getCompany_name().trim() + ".";
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
            holder.tv_addresstitle.setText(LanguageConstants.Work);
        } else {
            holder.tv_addresstitle.setText(LanguageConstants.office);
        }

        holder.imAddressDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mcontext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_alert);
                TextView tvQue = dialog.findViewById(R.id.tvQue);
                TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnYes = dialog.findViewById(R.id.btnYes);
                btnCancel.setText(LanguageConstants.cancel);
                btnYes.setText(LanguageConstants.yes);
                tvTitle.setText(LanguageConstants.alert);
                tvQue.setText(LanguageConstants.areyousurewanttodeletethisaddress);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String addressKey = address.getUser_address_key();
                        String userKey = SessionManager.getInstance().getUserKey();
                        CommonApiCalls.getInstance().deleteAddress(mcontext, userKey, addressKey, new CommonCallback.Listener() {
                            @Override
                            public void onSuccess(Object body) {
                                DeleteAddressApiResponse deleteAddressApiResponse = (DeleteAddressApiResponse) body;
                                MyApplication.result(deleteAddressApiResponse.getMessage());
                                addresslist.remove(address);
                                notifyChange(addresslist);
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(String reason) {

                            }
                        });
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
        holder.rbMarkAsDefault.setText(LanguageConstants.markDefault);
        holder.rbMarkAsDefault.setTag(position);
        if (CommonFunctions.AddressIsDefault.fromInteger(address.getDefault_add()) == CommonFunctions.AddressIsDefault.Yes) {
            holder.rbMarkAsDefault.setChecked(true);
        } else {
            holder.rbMarkAsDefault.setChecked(false);
        }
        holder.rbMarkAsDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int mPosition = (int) v.getTag();
                String addresskey = address.getUser_address_key();
                String userkey = SessionManager.getInstance().getUserKey();
                CommonApiCalls.getInstance().defaultAddress(mcontext, addresskey, userkey, new CommonCallback.Listener() {
                    @Override
                    public void onSuccess(Object body) {
                        for (int Count = 0; Count < addresslist.size(); Count++) {
                            addresslist.get(Count).setDefault_add(CommonFunctions.AddressIsDefault.No.getValue());
                        }
                        addresslist.get(position).setDefault_add(CommonFunctions.AddressIsDefault.Yes.getValue());
                        notifyDataSetChanged();
                        DefaultAddressApiResponse defaultAddressApiResponse = (DefaultAddressApiResponse) body;
                        MyApplication.result(defaultAddressApiResponse.getMessage());
                    }

                    @Override
                    public void onFailure(String reason) {

                    }
                });

            }
        });

        holder.imAddressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                AddressLatLng.lng = 0.0;
                AddressLatLng.lat = 0.0;
                AddressListApiResponse.Datum address_ = addresslist.get(position);
                notifyDataSetChanged();
                notifyChange(addresslist);
                Gson gson = new Gson();
                String selectedAddress = gson.toJson(address_);
                bundle.putString("currentAddress", selectedAddress);
                CommonFunctions.getInstance().newIntent(mcontext, AddNewAddress.class, bundle, false);
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
