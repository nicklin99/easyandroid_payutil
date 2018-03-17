

自己对微信支付和支付宝支付SDK,调用做了包装


### 调用支付

```java
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
```

### 是否支付成功

`payResult.isState()`

