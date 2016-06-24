package com.lanjiaai.kzhihu.model.entity;

/**
 * Created by Jack on 2016/2/3.
 */
public class CheckNewModel extends BaseModel {

    boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CheckNewModel{" +
                "result=" + result +
                '}';
    }
}
