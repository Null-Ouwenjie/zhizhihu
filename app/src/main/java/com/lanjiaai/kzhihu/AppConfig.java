package com.lanjiaai.kzhihu;

/**
 * 定义系统级配置
 * Created by Jack on 2015/11/27.
 */
public class AppConfig {

    public static final int MAX_CACHE_AGE = 3600 * 12;       // 60 * 60 = 1 小时
    public static boolean isDebug = true;  // todo Release 版本记得关掉 debug （设为 false）

    public static final String KZH_BASE_URL = "http://api.kanzhihu.com/";

    public static final long RESPONSE_CACHE_SIZE = 10240000; // 10MB
    public static final int CONNECTION_TIME_OUT = 30;
    public static final int READ_TIME_OUT = 30;
    public static final int WRITE_TIME_OUT = 30;

}
