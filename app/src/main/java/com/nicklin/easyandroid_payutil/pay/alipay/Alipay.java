package com.nicklin.easyandroid_payutil.pay.alipay;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.nicklin.easyandroid_payutil.pay.Pay;
import com.nicklin.easyandroid_payutil.pay.PayResult;
import com.nicklin.easyandroid_payutil.utils.LogUtil;

import java.util.Map;

/**
 * Created by mac on 2018/3/1.
 */

public final class Alipay implements Pay {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private static Alipay instance;

    public static Alipay getInstance(){
        if(instance==null){
            instance = new Alipay();
        }
        return instance;
    }

    @Override
    public PayResult pay(Activity activity, String orderInfo) {

        LogUtil.d("PayUtil:alipay" + orderInfo);

        PayTask alipay = new PayTask(activity);
        Map<String, String> result = alipay.payV2(orderInfo, true);

        Log.i("msp", result.toString());

        return handlerResult(result);
    }

    public PayResult handlerResult(Map<String, String> rawResult) {
        if (rawResult == null) {
            return null;
        }

        PayResult payResult = new PayResult(rawResult);

        for (String key : rawResult.keySet()) {
            if (TextUtils.equals(key, "resultStatus")) {
                String status = rawResult.get(key);

                if("9000".equals(status)){
                    payResult.setResultSuccess();
                }else{
                    payResult.setResult("fail");
                    payResult.setMessage(rawResult.get("memo") + " code:" + status );
                }

                //中途取消支付
                if(status=="6001"){
                    payResult.setMessage(rawResult.get("memo"));
                    payResult.setCancel();
                }
            }
        }

        return payResult;
    }
}
