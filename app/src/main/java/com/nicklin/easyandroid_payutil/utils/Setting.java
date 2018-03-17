package com.nicklin.easyandroid_payutil.utils;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.nicklin.easyandroid_payutil.MainApplication;

import java.util.HashMap;
import java.util.Map;


public class Setting {

    public static String package_name;

    public static Map<String,String> metas = new HashMap<>();

    //运行时读取meta，若metas存在则直接读取内存
    public static String getMeta(String key) {
        if(metas.get(key)!=null){
            return metas.get(key);
        }

        try {
            Bundle metaData = MainApplication.getContext().getPackageManager()
                    .getApplicationInfo(package_name, PackageManager.GET_META_DATA).metaData;

            metas.put(key,metaData.getString(key));
            return metas.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
