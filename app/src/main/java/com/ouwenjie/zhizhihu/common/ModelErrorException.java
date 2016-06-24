package com.ouwenjie.zhizhihu.common;

/**
 * 当 model 不等于 1 时，触发这一个 Exception
 * Created by Jack on 2016/1/18.
 */
public class ModelErrorException extends RuntimeException {
    public ModelErrorException(String msg) {
        super(msg);
    }
}
