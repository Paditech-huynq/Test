package com.unza.wipro.main.models.responses;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.ProductCategory;

public class GetProductCategoryRSP {

    @SerializedName("result")
    @Expose
    private int result;
    @SerializedName("current_time")
    @Expose
    private long currentTime;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ProductCategory> data = null;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
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

    public List<ProductCategory> getData() {
        return data;
    }

    public void setData(List<ProductCategory> data) {
        this.data = data;
    }
}