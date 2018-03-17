package com.nicklin.easyandroid_payutil.pay;

import android.app.Activity;

/**
 * Created by mac on 2018/3/1.
 */

public interface Pay {

    /**
     * 发起支付
     * @param activity 所属activity
     * @param orderInfo 订单信息
     */
    PayResult pay(Activity activity, String orderInfo);
}

