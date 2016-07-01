package com.ouwenjie.zhizhihu.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 网络相关工具类
 *
 * @author Sam Sun
 * @version $Revision:1.0.0, $Date: 2012-2-15 下午3:54:17
 */
public class NetworkUtil {

    /**
     * 网络类型常量（支持到Android4.0）
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_GPRS = 1;
    public static final int NETWORK_TYPE_EDGE = 2;
    public static final int NETWORK_TYPE_UMTS = 3;
    public static final int NETWORK_TYPE_CDMA = 4;
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    public static final int NETWORK_TYPE_EVDO_A = 6;
    public static final int NETWORK_TYPE_1xRTT = 7;
    public static final int NETWORK_TYPE_HSDPA = 8;
    public static final int NETWORK_TYPE_HSUPA = 9;
    public static final int NETWORK_TYPE_HSPA = 10;
    public static final int NETWORK_TYPE_IDEN = 11;
    public static final int NETWORK_TYPE_EVDO_B = 12;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_EHRPD = 14;
    public static final int NETWORK_TYPE_HSPAP = 15;

    /**
     * 链接管理对象
     */
    private static ConnectivityManager connectivityManager;

    public static final String NETWORK_TYPE_WIFI = "wifi";
    public static final String NETWORK_TYPE_CMWAP = "cmwap";
    public static final String NETWORK_TYPE_CMNET = "cmnet";
    public static final String NETWORK_TYPE_UNINET = "uninet";
    public static final String NETWORK_TYPE_UNIWAP = "uniwap";
    public static final String NETWORK_TYPE_CTNET = "ctnet";
    public static final String NETWORK_TYPE_CTWAP = "ctwap";

    /**
     * 检查网络是否可用
     *
     * @param cxt
     * @return
     */
    public static boolean isNetworkAvailable(Context cxt) {

        if (getService(cxt) == null)
            return false;
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (null != netInfo && netInfo.isConnected()) {
            if (netInfo.getState() == NetworkInfo.State.CONNECTED) {    // 判断当前网络是否已经连接
                return true;
            }
        }
        return false;
    }

    /**
     * ping 网络ip
     *
     * @param str
     * @return
     */
    public static boolean pingHost(String str) {
        boolean isSuccess = false;
        int status;
        Process process;
        try {
            process = Runtime.getRuntime().exec("ping -c 1 -w 100 " + str);
            status = process.waitFor();
            if (status == 0) {
                isSuccess = true;
            }
        } catch (IOException | InterruptedException e) {
//            Logger.d(e.toString());
        }
//        Logger.d("pingHost==>" + "ping " + str + " [result]" + (isSuccess ? "success" : "failed"));
        return isSuccess;
    }

    /**
     * 是否为WIFI
     *
     * @param cxt
     * @return
     */
    public static boolean isWifi(Context cxt) {
        if (getService(cxt) == null)
            return false;
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)
            return true;
        return false;
    }

    /**
     * 读取APN字段
     *
     * @param cxt
     * @return
     */
    public static String getApn(Context cxt) {
        if (getService(cxt) == null)
            return null;
        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
        if (info == null)
            return null;
        for (int i = 0; i < info.length; i++) {
            if (info[i].getType() == ConnectivityManager.TYPE_MOBILE) {
                return info[i].getExtraInfo();
            }
        }
        return null;
    }

    /**
     * 调用系统服务获取ConnectivityManager对象
     *
     * @param cxt
     * @return
     */
    public static ConnectivityManager getService(Context cxt) {
        if (null != cxt && null == connectivityManager) {
            connectivityManager = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return connectivityManager;
    }

    /**
     * 获取网络类型名称
     *
     * @param cxt
     * @return
     */
    public static String getNetworkType(Context cxt) {
        ConnectivityManager mag = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mag.getActiveNetworkInfo();
        if (info != null) {
            String typeName = info.getTypeName();
            if (typeName.equalsIgnoreCase("mobile")) {
                return info.getExtraInfo();
            } else {
                return typeName;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取网络名称
     *
     * @param cxt
     * @return
     */
    public static String getOnline(Context cxt) {
        if (null == cxt)
            return null;
        String online;
        TelephonyManager tm;
        tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        if (NetworkUtil.isWifi(cxt)) {// 是WIFI
            online = "wifi";
        } else { // 非WIFI
            online = NetworkUtil.getOnline(tm.getNetworkType()); // 根据NetworkType获取名称
        }
        return online;
    }

    /**
     * 获取网络名称
     *
     * @param type
     * @return
     */
    public static String getOnline(int type) {
        switch (type) {
            case NETWORK_TYPE_GPRS:
                return "GPRS";
            case NETWORK_TYPE_EDGE:
                return "EDGE";
            case NETWORK_TYPE_UMTS:
                return "UMTS";
            case NETWORK_TYPE_CDMA:
                return "CDMA";
            case NETWORK_TYPE_EVDO_0:
                return "EVDO_0";
            case NETWORK_TYPE_EVDO_A:
                return "EVDO_A";
            case NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case NETWORK_TYPE_HSPA:
                return "HSPA";
            case NETWORK_TYPE_IDEN:
                return "IDEN";
            case NETWORK_TYPE_EVDO_B:
                return "EVDO_B";
            case NETWORK_TYPE_LTE:
                return "LTE";
            case NETWORK_TYPE_EHRPD:
                return "EHRPD";
            case NETWORK_TYPE_HSPAP:
                return "HSPAP";
            default:
                return "unknown";
        }
    }

    /**
     * 获取运营商名称
     *
     * @param cxt
     * @return
     */
    public static String getProvider(Context cxt) {
        if (null == cxt)
            return "";
        String provider;
        TelephonyManager tm;
        tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {// 有手机卡
            provider = tm.getSimOperatorName();
        } else { // 没有手机卡
            provider = "";
        }
        return provider;
    }

    /**
     * 用来获取手机拨号上网（包括CTWAP和CTNET）时由PDSN分配给手机终端的源IP地址。
     *
     * @return
     * @author SHANHY
     */
    public static String getPsdnIp() {

        NetworkInterface intf;
        InetAddress inetAddress;

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
//            Logger.d(e.toString());
        }
        return "";
    }
}
