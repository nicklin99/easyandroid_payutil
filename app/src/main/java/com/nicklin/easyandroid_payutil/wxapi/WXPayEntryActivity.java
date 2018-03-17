package com.nicklin.easyandroid_payutil.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.nicklin.easyandroid_payutil.pay.PayResult;
import com.nicklin.easyandroid_payutil.utils.LogUtil;
import com.nicklin.easyandroid_payutil.utils.Setting;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.HashMap;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	api = WXAPIFactory.createWXAPI(this, Setting.getMeta("wx_appid"));
        api.handleIntent(getIntent(), this);

		LogUtil.d("wxpay:onCreate");
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		LogUtil.d("wxpay:onResp");

		PayResult payResult = new PayResult(new HashMap());

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			int code = resp.errCode;

			Intent intent = new Intent(Setting.package_name +"." + TAG);

			LogUtil.d("wxpay:" +"errStr:" +resp.errStr);
			LogUtil.d("wxpay:" +"errCode:" +resp.errCode);

			switch (code){
				case -2:
					payResult.setCancel();
					intent.putExtra("result","cancel");
					LogUtil.d("wxpay:" +"cancel");
					break;
				case -1:
					payResult.setResultFail();
					intent.putExtra("result","fail");
					LogUtil.d("wxpay:" +"fail");
					break;
				case 0:
					payResult.setResultSuccess();
					intent.putExtra("result","success");
					LogUtil.d("wxpay:" +"success");

					break;
			}

			LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
			finish();
		}


	}
}