package com.jana.overwatch.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew Jeong on 2016-09-25.
 */

public class OAuthClient {

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("state")
    @Expose
    private String checkState;

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
