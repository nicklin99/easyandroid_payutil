package com.nicklin.easyandroid_payutil.utils;

import android.util.Log;

/**
 * Created by mac on 2018/3/11.
 */

public class LogUtil {

    public static void d(String msg){
        if(AppConfig.DEBUG_LOG){
            Log.d("LogUtil", msg);
        }
    }
}
