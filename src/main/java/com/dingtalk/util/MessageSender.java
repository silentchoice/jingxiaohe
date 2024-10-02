package com.dingtalk.util;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponseBody;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiServiceGetCorpTokenRequest;
import com.dingtalk.api.request.OapiSsoGettokenRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiServiceGetCorpTokenResponse;
import com.dingtalk.api.response.OapiSsoGettokenResponse;
import com.dingtalk.constant.AppConstant;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.mapper.CardListMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MessageSender {
    public static String tock_goods;
    public static String corridor;
//    public static String image_url;

    public MessageSender(String tock_goods, String corridor) {
        MessageSender.tock_goods = tock_goods;
        MessageSender.corridor = corridor;
//        MessageSender.image_url = image_url;
    }

    public MessageSender() {
    }

    public static OapiSsoGettokenResponse noPasswdLogin() throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient(UrlConstant.GET_ACCESS_TOKEN_URL);
        OapiSsoGettokenRequest request = new OapiSsoGettokenRequest();
        request.setCorpid(AppConstant.APP_KEY);
        request.setCorpsecret(AppConstant.APP_SECRET);
        request.setTopHttpMethod("GET");
        OapiSsoGettokenResponse rsp = client.execute(request);
//        System.out.println(rsp.getBody());
        return rsp;
    }

    public static void main(String[] args) throws Exception {
        String[] a = {"胡萝卜缺货", "金旅城店", "3", "http://ai.jxhlife.com:9530/picWithLabel/029/001/3/20240725090100.jpg", "1", "3018314737-902327211"};//3155189689010//3018314737-902327211
//        String[] result = {tockGoods, shopName, corridor, imageUrl, "", ""};
        sendMessage(a);

    }

    public static void sendMessage(String[] args) throws Exception {
        //构建工作通知推送Client
        String accessToken = AccessTokenUtil.getAccessToken();
        DingTalkClient client2 = new DefaultDingTalkClient(UrlConstant.MESSAGE_SENDER);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        System.out.println(args[5]);
        request.setUseridList(args[5]);
        request.setToAllUser(false);
        //构建消息模板
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        String dingUrl = "dingtalk://dingtalkclient/page/link?url=http://ai.jxhlife.com:9527/item/" + args[4] + "?userid=" + args[5] + "&pc_slide=false";
        msg.setMsgtype("link");
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle(args[1]);
        msg.getLink().setText(args[1] + "通道" + args[2] + "的" + args[0] + "缺货,通知时间为:" + LocalDateTime.now());
        msg.getLink().setMessageUrl(dingUrl);
        msg.getLink().setPicUrl(args[3]);
        System.out.println("success,Time:" + LocalDateTime.now());
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response rsp = client2.execute(request, accessToken);
        System.out.println(rsp.getBody());
    }
}
