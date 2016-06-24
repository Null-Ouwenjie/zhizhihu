package com.ouwenjie.zhizhihu;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by 文杰 on 2015/10/28.
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(getApplicationContext()).build());
        if (AppConfig.isDebug) {
            Stetho.initializeWithDefaults(this);  // 开启 Stetho 调试模式
            Logger.init("=LingKu=").logLevel(LogLevel.FULL);
        } else {
            Logger.init().logLevel(LogLevel.NONE);
        }

    }

    public static Context getContext() {
        return context;
    }

}
