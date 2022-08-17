package com.minwein.customer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.api.PlaceAPI;

import java.util.ArrayList;

public  class PlacesAutoCompleteAdapter extends BaseAdapter implements Filterable {
 
    public  ArrayList<String> resultList;
 
    Context mContext;

    public PlaceAPI mPlaceAPI = new PlaceAPI();
//    private ArrayList<PlacesModel> myResults;

    public PlacesAutoCompleteAdapter(Context context) {
//        super(context);
 
        mContext = context;

    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }
 
    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    resultList = mPlaceAPI.autocomplete(constraint.toString());
//                    myResults = mPlaceAPI.autocomplete1(constraint.toString());
 
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
 
                return filterResults;
            }
 
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
 
        return filter;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = null;

        //if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if (position != (resultList.size() - 1))
        if (convertView  == null) {
            convertView = inflater.inflate(R.layout.autocomplete_list_item, null);
        }
//        else
//            view = inflater.inflate(R.layout.autocomplete_google_logo, null);
        //}
        //else {
        //    view = convertView;
        //}

        if (position != (resultList.size() - 1)) {
            TextView autocompleteTextView = (TextView) convertView.findViewById(R.id.autocompleteText);
            autocompleteTextView.setText(resultList.get(position));
        }
        else {
//            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            // not sure what to do <img draggable="false" class="emoji" alt="" src="https://s.w.org/images/core/emoji/72x72/1f600.png">
        }

        return convertView;
    }
}