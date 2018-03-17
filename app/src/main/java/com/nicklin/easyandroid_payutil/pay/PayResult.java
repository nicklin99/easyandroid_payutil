package com.nicklin.easyandroid_payutil.pay;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by mac on 2018/3/1.
 */

public class PayResult {
    private Map data;

    private String result;
    private String message;
    private boolean state = false;

    private static final String CANCEL = "cancel";
    private static final String ERROR = "error";
    private static final String FAIL = "fail";
    private static final String SUCCESS = "success";

    public PayResult(Map result) {
        data = result;
    }

    public PayResult() {

    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",result);

        if(message==null){
            if(result.equals(CANCEL)){
                message = "已取消";
            }else if(result.equals(FAIL)){
                message = "支付失败";
            }
        }

        jsonObject.put("msg",message );
        jsonObject.put("state",state);

        return jsonObject.toJSONString();
    }

    public void setResult(String result) {
        this.result = result;

        if(result.equals(SUCCESS)){
            state = true;
        }
    }

    public void setCancel(){
        this.result = CANCEL;
    }

    public void setResultFail(){
        this.result = FAIL;
        this.state = false;
    }

    public void setResultSuccess(){
        this.result = SUCCESS;
        this.state = true;
        this.message = "订单支付成功";
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }
}
