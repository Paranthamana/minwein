package com.minwein.customer.app_interfaces;

/**
 * Created by AMSHEER on 20-01-2018.
 */

public class CommonCallback {
    public interface Listener {
        public void onSuccess(Object body);
        public void onFailure(String reason);
    }

    private Listener listener;
}
