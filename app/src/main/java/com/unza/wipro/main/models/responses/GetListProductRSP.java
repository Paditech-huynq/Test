package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.Product;

import java.util.List;

public class GetListProductRSP {
    @SerializedName("result")
    @Expose
    private long result;
    @SerializedName("current_time")
    @Expose
    private long currentTime;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Product> data = null;

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}