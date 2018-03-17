package com.nicklin.easyandroid_payutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nicklin.easyandroid_payutil.pay.PayResult;
import com.nicklin.easyandroid_payutil.pay.PayUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * alipay 支付宝支付
     * wx 微信支付
     *
     * onFinish都会回调
     * onSuccess仅支付成功回调
     */
    private void pay(){
        String orderInfo = "";
        String type = "alipay";

        PayUtil.pay(this, type, orderInfo, new PayUtil.PayCallback() {
            @Override
            public void onFinish(PayResult payResult) {

            }

            @Override
            public void onSuccess(PayResult payResult) {

            }
        });
    }
}
