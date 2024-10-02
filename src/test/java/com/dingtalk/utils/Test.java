package com.dingtalk.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Test {
    public static void sendMessage(String[] args) throws Exception {
//        dingquk2pwxydugcrudf
        Long timestamp = 1577262236757L;
        String appSecret = "hJgL2f9QYmTwuuiXazTNiol7qo5WlxC4udxihHBaFaPSe0XH3kqfRGrF9nvMzQV2";
        String stringToSign = timestamp + "\n" + appSecret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = new String(Base64.encodeBase64(signData));
        System.out.println(sign);
    }

    public static void main(String[] args) throws Exception {
        String[] a = {"胡萝卜缺货", "金旅城店", "3", "http://ai.jxhlife.com:9530/picWithLabel/029/001/3/20240725090100.jpg", "1", "01534201034626630351"};//3155189689010//3018314737-902327211
//        String[] result = {tockGoods, shopName, corridor, imageUrl, "", ""};
        sendMessage(a);

    }
}
