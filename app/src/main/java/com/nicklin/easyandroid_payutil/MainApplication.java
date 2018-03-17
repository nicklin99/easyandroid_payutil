package com.nicklin.easyandroid_payutil;

import android.app.Application;
import android.content.Context;

import com.nicklin.easyandroid_payutil.utils.Setting;

/**
 * Created by mac on 2018/3/18.
 */

public class MainApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Setting.package_name = getPackageName();
    }


}
