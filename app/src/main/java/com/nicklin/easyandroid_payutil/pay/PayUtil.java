package com.nicklin.easyandroid_payutil.pay;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.nicklin.easyandroid_payutil.pay.alipay.Alipay;
import com.nicklin.easyandroid_payutil.pay.wxpay.Wxpay;
import com.nicklin.easyandroid_payutil.utils.LogUtil;


/**
 * Created by mac on 2018/3/1.
 */

public class PayUtil {

    private static Pay getInstance(String type){
        Pay instance = null;
        switch (type){
            case "alipay":
                instance = Alipay.getInstance();
                break;
            case "wx":
                instance = Wxpay.getInstance();
                break;
            default:
                break;


        }

        return instance;
    }

    /**
     * 调起支付控件，返回支付结果
     *
     * @param payCode
     * @param activity
     */
    public static void pay( final Activity activity,final String type,final String payCode,final PayCallback payCallback){
        if(type.equals("wx")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Wxpay instance =(Wxpay) getInstance(type);
                    instance.with(payCallback).pay(activity,payCode);
                }
            }).start();

        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final PayResult payResult = getInstance(type).pay(activity,payCode);

                    //onSuccess 一般会有 UI 操作，改为 UI 线程
                    if(payResult.isState()){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                payCallback.onSuccess(payResult);
                            }
                        });
                    }

                    payCallback.onFinish(payResult);
                }
            }).start();

        }
    }

    public static PayResult paySync(Activity activity,String type,String payCode){
        PayResult payResult = getInstance(type).pay(activity,payCode);
        return payResult;
    }

    //low level
    public interface PayCallback{
        void onFinish(PayResult payResult);
        void onSuccess(PayResult payResult);
    }

    public static void toastSuccess(Context context){
        LogUtil.d("pay:支付成功");
        Toast.makeText(context,"支付成功",Toast.LENGTH_LONG).show();
    }

}
