package com.ouwenjie.zhizhihu.common;

import android.util.Log;

import com.ouwenjie.zhizhihu.AppConfig;


/**
 * 调试工具类 (调试模式下：日志将被打印)
 *
 * @author ouwenjie
 * @version 2016年3月16日 13:50:09
 */
public class LLog {

    public static final String TAG = "Lightstao";

    // verbose
    public static final int LOG_LEVEL_V = 1;
    // info
    public static final int LOG_LEVEL_I = 2;
    // sDebug
    public static final int LOG_LEVEL_D = 3;
    // warn
    public static final int LOG_LEVEL_W = 4;
    // error
    public static final int LOG_LEVEL_E = 5;

    /**
     * 是否为调试模式，默认为true，发布前需改成false
     */
//	private static boolean sDebug = false;
//	private static boolean sDebug = true;

    private static boolean sDebug = AppConfig.isDebug;

    public static boolean isDebug() {
        return sDebug;
    }

    /**
     * 打印verbose级别的日志
     *
     * @param msg 日志内容
     */
    public static void v(Object msg) {
        log(TAG, msg, null, LOG_LEVEL_V);
    }

    /**
     * 打印verbose级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     */
    public static void v(String tag, Object msg) {
        log(tag, msg, null, LOG_LEVEL_V);
    }

    /**
     * 打印verbose级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     * @param e   异常对象
     */
    public static void v(String tag, Object msg, Throwable e) {
        log(tag, msg, e, LOG_LEVEL_V);
    }

    /**
     * 打印debug级别的日志
     *
     * @param msg 日志内容
     */
    public static void d(Object msg) {
        log(TAG, msg, null, LOG_LEVEL_D);
    }

    /**
     * 打印debug级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     */
    public static void d(String tag, Object msg) {
        log(tag, msg, null, LOG_LEVEL_D);
    }

    /**
     * 打印debug级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     * @param e   异常对象
     */
    public static void d(String tag, Object msg, Throwable e) {
        log(tag, msg, e, LOG_LEVEL_D);
    }

    /**
     * 打印info级别的日志
     *
     * @param msg 日志内容
     */
    public static void i(Object msg) {
        log(TAG, msg, null, LOG_LEVEL_I);
    }

    /**
     * 打印info级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     */
    public static void i(String tag, Object msg) {
        log(tag, msg, null, LOG_LEVEL_I);
    }

    /**
     * 打印info级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     * @param e   异常对象
     */
    public static void i(String tag, Object msg, Throwable e) {
        log(tag, msg, e, LOG_LEVEL_I);
    }

    /**
     * 打印warn级别的日志
     *
     * @param msg 日志内容
     */
    public static void w(Object msg) {
        log(TAG, msg, null, LOG_LEVEL_W);
    }

    /**
     * 打印warn级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     */
    public static void w(String tag, Object msg) {
        log(tag, msg, null, LOG_LEVEL_W);
    }

    /**
     * 打印warn级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     * @param e   异常对象
     */
    public static void w(String tag, Object msg, Throwable e) {
        log(tag, msg, e, LOG_LEVEL_W);
    }

    /**
     * 打印error级别的日志
     *
     * @param msg 日志内容
     */
    public static void e(Object msg) {
        log(TAG, msg, null, LOG_LEVEL_E);
    }

    /**
     * 打印error级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     */
    public static void e(String tag, Object msg) {

        log(tag, msg, null, LOG_LEVEL_E);
    }

    /**
     * 打印error级别的日志
     *
     * @param tag 显示标签
     * @param msg 日志内容
     * @param e   异常对象
     */
    public static void e(String tag, Object msg, Throwable e) {

        log(tag, msg, e, LOG_LEVEL_E);
    }

    /**
     * 对应日志
     *
     * @param tag      显示标签 标签
     * @param msgObj   日志内容Obj 消息对象
     * @param logLevel 日志级别
     */
    public static void log(String tag, Object msgObj, Throwable e, int logLevel) {
        if (!sDebug) // 不是调试模式
            return;

        if (null == tag && null == msgObj) // 标签和消息都为空
            return;

        String msg = "";

        tag = (null == tag) ? TAG : tag;

        if (null != msgObj) {

            Class<? extends Object> _class = msgObj.getClass();
            msg = _class.cast(msgObj).toString();
        }

        if (null != e) {
            msg += e.getMessage();
        }

        switch (logLevel) {

            case LOG_LEVEL_V:
                Log.v(tag, msg);
                break;
            case LOG_LEVEL_I:
                Log.i(tag, msg);
                break;
            case LOG_LEVEL_D:
                Log.d(tag, msg);
                break;
            case LOG_LEVEL_W:
                Log.w(tag, msg);
                break;
            case LOG_LEVEL_E:
                Log.e(tag, msg);
                break;
        }
    }
}
