package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoyaltyPointsApiResponse {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("data")
@Expose
private Data data;
@SerializedName("time")
@Expose
private long time;
@SerializedName("message")
@Expose
private String message;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

public long getTime() {
return time;
}

public void setTime(long time) {
this.time = time;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}



    public class Data {

        @SerializedName("loyalty_points")
        @Expose
        private Integer loyalty_points;

        public Integer getLoyalty_points() {
            return loyalty_points;
        }

        public void setLoyalty_points(Integer loyalty_points) {
            this.loyalty_points = loyalty_points;
        }

    }
}