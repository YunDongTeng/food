package com.cloud.food.util;

import java.security.MessageDigest;
import java.util.Arrays;

public class CheckUtil {

    private static final String token = "cloud";

    public static final boolean checkSignature(String signature, String timestamp, String nonce) {

        String[] strArray = new String[]{token, timestamp, nonce};

        Arrays.sort(strArray);

        StringBuffer content = new StringBuffer();
        for(int i=0;i<strArray.length;i++){
            content.append(strArray[i]);
        }

        String temp = getSha1(content.toString());

        return signature.equals(temp);


    }

    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
