package com.unza.wipro.main.models.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.Customer;

public class GetListCustomerRSP {

@SerializedName("result")
@Expose
private String result;
@SerializedName("current_time")
@Expose
private String currentTime;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<Customer> data = null;

public String getResult() {
return result;
}

public void setResult(String result) {
this.result = result;
}

public String getCurrentTime() {
return currentTime;
}

public void setCurrentTime(String currentTime) {
this.currentTime = currentTime;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Customer> getData() {
return data;
}

public void setData(List<Customer> data) {
this.data = data;
}

}