package com.nicklin.easyandroid_payutil.pay.wxpay;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;


import com.alibaba.fastjson.JSONObject;
import com.nicklin.easyandroid_payutil.pay.Pay;
import com.nicklin.easyandroid_payutil.pay.PayResult;
import com.nicklin.easyandroid_payutil.pay.PayUtil;
import com.nicklin.easyandroid_payutil.utils.LogUtil;
import com.nicklin.easyandroid_payutil.utils.Setting;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * SDK ----------
 * 引入微信SDK
 * 注册微信SDK
 *
 * todo 支付 ----------
 * 后台调用微信支付API统一下单生成支付订单，返回给app orderInfo
 * app获取到后调起微信支付
 *
 * todo 支付结果返回处理 ----------
 * 动态注册广播，通知支付结果
 *
 */

public class Wxpay implements Pay {

    private static Wxpay instance;

    private IWXAPI api;

    private PayUtil.PayCallback payCallback;

    public static Wxpay getInstance(){
        if(instance==null){
            instance = new Wxpay();
        }
        return instance;
    }

    private void onCreate(Activity activity){
        api = WXAPIFactory.createWXAPI(activity, null);
        String appid = Setting.getMeta("wx_appid");
        api.registerApp(appid);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
            String result = intent.getStringExtra("result");
            PayResult payResult = new PayResult();
            payResult.setResult(result);

            if(payCallback!=null){
                if(payResult.isState()){
                    payCallback.onSuccess(payResult);
                }

                payCallback.onFinish(payResult);
            }
        }
    };

    public Wxpay with(PayUtil.PayCallback callback){
        payCallback = callback;
        return this;
    }

    @Override
    public synchronized PayResult pay(Activity activity, String orderInfo) {
        if(api==null){
            onCreate(activity);
        }

        PayReq req = new PayReq();

        LogUtil.d("wxpay:" + orderInfo);

        JSONObject jsonObject = JSONObject.parseObject(orderInfo);

        req.appId			= jsonObject.getString("appid");
        req.partnerId		= jsonObject.getString("partnerid");
        req.prepayId		= jsonObject.getString("prepayid");
        req.nonceStr		= jsonObject.getString("noncestr");
        req.timeStamp		= jsonObject.getString("timestamp");
        req.packageValue	= jsonObject.getString("package");
        req.sign			= jsonObject.getString("sign");

        boolean open = api.sendReq(req);

        LogUtil.d("wxpay:" +open);
        if(!open){
            PayResult payResult = new PayResult();
            payResult.setResultFail();
            payCallback.onFinish(payResult);
            return null;
        }

        if(payCallback!=null && open){
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Setting.package_name + ".WXPayEntryActivity");
            LocalBroadcastManager.getInstance(activity).registerReceiver(broadcastReceiver,intentFilter);
        }

        return null;
    }

}
