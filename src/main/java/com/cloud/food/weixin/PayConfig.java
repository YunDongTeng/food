package com.cloud.food.weixin;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.*;

public class PayConfig implements WXPayConfig {

    private String appId = "APPID";
    private String mchId = "";
    private String key = "";
    private String certPath = "/root/";
    private Integer httpConnectionTimeoutMs = 8000;  //单位：ms
    private Integer httpReadTimeMs = 10000;  //单位：ms

    private byte[] certBytes;

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
}
