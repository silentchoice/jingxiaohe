package com.dingtalk.test;

import com.dingtalk.open.app.api.callback.OpenDingTalkCallbackListener;
import shade.com.alibaba.fastjson2.JSON;
import shade.com.alibaba.fastjson2.JSONObject;


public class RobotMsgCallbackConsumer implements OpenDingTalkCallbackListener<JSONObject, JSONObject> {
    @Override
    public JSONObject execute(JSONObject request) {
        System.out.println("request" + JSON.toJSONString(request));
        try {
//            System.out.println("request");
            JSONObject text = request.getJSONObject("text");
            if (text != null) {
                //机器人接收消息内容
                String msg = text.getString("content").trim();
                String openConversationId = request.getString("conversationId");
                System.out.println("msg:" + msg);
                System.out.println("openConversationId:" + openConversationId);
            }
        } catch (Exception e) {
//            log.error("receive group message by robot error:" +e.getMessage(), e);
            System.out.println(e.getMessage());
        }
        return new JSONObject();
    }
}
