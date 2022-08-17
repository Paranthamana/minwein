package com.minwein.customer.utils;

import android.content.Context;

import com.minwein.customer.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class CustomProgressDialog1 {
    private static CustomProgressDialog1 ourInstance = new CustomProgressDialog1();
    ACProgressFlower dialog;
    public static CustomProgressDialog1 getInstance() {
        return ourInstance;
    }

    private CustomProgressDialog1() {

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
