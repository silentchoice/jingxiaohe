package com.dingtalk.test;

//import com.alibaba.fastjson2.JSONObject;

import com.dingtalk.constant.AppConstant;
import com.dingtalk.open.app.api.OpenDingTalkClient;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.callback.OpenDingTalkCallbackListener;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import org.yaml.snakeyaml.TypeDescription;
import shade.com.alibaba.fastjson2.JSON;
import shade.com.alibaba.fastjson2.JSONObject;

//import static org.yaml.snakeyaml.introspector.PropertySubstitute.log;


public class RobotCode {
    public static void main(String[] args) throws Exception {
        String key = AppConstant.APP_KEY;
        String secret = AppConstant.APP_SECRET;
        String topic = "/v1.0/im/bot/messages/get";
        OpenDingTalkClient client = OpenDingTalkStreamClientBuilder
                .custom()
                .credential(new AuthClientCredential(key, secret))
                .registerCallbackListener(topic, new RobotMsgCallbackConsumer())
                .build();
        client.start();
    }


//    public static class RobotMsgCallbackConsumer implements OpenDingTalkCallbackListener<JSONObject, JSONObject> {
//
//        /*
//         * @param request
//         * @return
//         */
//        @Override
//        public JSONObject execute(JSONObject request) {
//            System.out.println(JSON.toJSONString(request));
//            try {
//                JSONObject text = request.getJSONObject("text");
//                if (text != null) {
//                    //机器人接收消息内容
//                    String msg = text.getString("content").trim();
//                    String openConversationId = request.getString("conversationId");
//                }
//                System.out.println("1");
//            } catch (Exception e) {
////                log.error("receive group message by robot error:" + e.getMessage(), e);
//                System.out.println(e.getMessage());
////                System.out.println();
//            }
//            return new JSONObject();
//        }
//    }
}
