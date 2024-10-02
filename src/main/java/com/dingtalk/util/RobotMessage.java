

package com.dingtalk.util;

import com.aliyun.dingtalkrobot_1_0.Client;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponse;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Collections;
import shade.com.alibaba.fastjson2.JSONObject;

public class RobotMessage {

    public static Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new Client(config);
    }

    public static void sendMessage(String[] message) throws Exception {
        Client client = createClient();
        BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
        batchSendOTOHeaders.xAcsDingtalkAccessToken = AccessTokenUtil.getAccessToken();
        JSONObject jsonObject = new JSONObject();
        StringBuilder messageText = new StringBuilder();
        StringBuilder messageUrl = new StringBuilder();
        if (message[0].contains("缺货")) {
            messageText.append(message[1]).append("通道").append(message[2]).append("的").append(message[0]).append(",通知时间为:").append(LocalDateTime.now());
        } else {
            messageText.append(message[1]).append("通道").append(message[2]).append("的").append(message[0]).append("缺货,通知时间为:").append(LocalDateTime.now());
        }

        messageUrl.append("http://ai.jxhlife.com:9527/item/").append(message[4]).append("?userid=").append(message[5]);
        System.out.println(messageUrl.toString());
        System.out.println(messageUrl);
        String encode = URLEncoder.encode(messageUrl.toString(), "UTF-8");
        System.out.println("dingtalk://dingtalkclient/page/link?url=" + encode + "&pc_slide=true");
        jsonObject.put("text", messageText.toString());
        jsonObject.put("title", message[1]);
        jsonObject.put("picUrl", message[3]);
        jsonObject.put("messageUrl", "dingtalk://dingtalkclient/page/link?url=" + encode + "&pc_slide=true");
        BatchSendOTORequest batchSendOTORequest = (new BatchSendOTORequest()).setMsgParam(jsonObject.toString()).setMsgKey("sampleLink").setRobotCode("dingl8blibdbipryih06").setUserIds(Collections.singletonList(message[5]));

        try {
            BatchSendOTOResponse batchSendOTOResponse = client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
            BatchSendOTOResponseBody body = batchSendOTOResponse.getBody();
            String processQueryKey = body.getProcessQueryKey();
            System.out.println(processQueryKey);
            System.out.println("success,Time:" + LocalDateTime.now());
        } catch (TeaException e) {
            if (!Common.empty(e.code) && !Common.empty(e.message)) {
                System.out.println("err.code:" + e.code);
                System.out.println("err.message" + e.message);
            }
        } catch (Exception e) {
            TeaException err = new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                System.out.println("err.code:" + err.code);
                System.out.println("err.message" + err.message);
            }
        }

    }

    public static void main(String[] args) throws Exception {
        String[] a = new String[]{"胡萝卜缺货", "金旅城店", "3", "http://ai.jxhlife.com:9530/picWithLabel/029/001/3/20240725090100.jpg", "1", "3018314737-902327211"};
        sendMessage(a);
    }
}
