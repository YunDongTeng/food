package com.cloud.food.weixin;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class PayConfig implements WXPayConfig {

    private String appId = "";
    private String mchId = "";
    private String key = "";
    private String certPath = "";
    private Integer httpConnectionTimeoutMs = 8000;  //单位：ms
    private Integer httpReadTimeMs = 10000;  //单位：ms
    private byte[] certBytes;


    private String pay_notify_url = "http://haomai.com/pay/notify";
    private String refund_notify_url = "http://haomai.com/pay/refundNotify";


    public PayConfig(){

        File file = new File(certPath);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            this.certBytes = new byte[(int)file.length()];
            fileInputStream.read(this.certBytes);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(certBytes);
        return byteArrayInputStream;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return httpConnectionTimeoutMs;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return httpReadTimeMs;
    }

    public String getPayNotifyUrl(){
        return this.pay_notify_url;
    }
    public String getRefundNotifyUrl(){
        return this.refund_notify_url;
    }
}
