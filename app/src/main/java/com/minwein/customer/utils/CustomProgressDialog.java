package com.minwein.customer.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import com.minwein.customer.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class CustomProgressDialog {
    private static CustomProgressDialog ourInstance = new CustomProgressDialog();
    ACProgressFlower dialog;
    public static CustomProgressDialog getInstance() {
        return ourInstance;
    }

    private CustomProgressDialog() {

    }

    public void show(Context context) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(MyApplication.context.getResources().getColor(R.color.white))
                .bgColor(MyApplication.context.getResources().getColor(R.color.colorPrimary))
                .fadeColor(MyApplication.context.getResources().getColor(R.color.colorPrimary)).build();
        dialog.show();

    }

    public void dismiss() {
        if (dialog != null)
            dialog.dismiss();
    }

    public boolean isShowing() {
        if (dialog != null && dialog.isShowing())
            return true;
        else return false;
    }
}
