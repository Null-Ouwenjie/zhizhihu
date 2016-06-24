package com.lanjiaai.kzhihu.model.entity;

/**
 * Created by Jack on 2016/2/3.
 */
public class BaseModel {

    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "error='" + error + '\'' +
                '}';
    }
}
