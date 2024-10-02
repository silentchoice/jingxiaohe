package com.dingtalk.utils;

// This file is auto-generated, don't edit it. Thanks.

//import com.alibaba.fastjson2.JSONObject;
import com.aliyun.tea.*;
import com.dingtalk.util.AccessTokenUtil;
import shade.com.alibaba.fastjson2.JSONObject;

import java.time.LocalDateTime;

public class Sample {

    /**
     * <b>description</b> :
     * <p>使用 Token 初始化账号Client</p>
     * @return Client
     *
     * @throws Exception
     */
    public static com.aliyun.dingtalkrobot_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkrobot_1_0.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.dingtalkrobot_1_0.Client client = Sample.createClient();
        System.out.println(client);

        com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders batchSendOTOHeaders = new com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders();
        String accessToken = AccessTokenUtil.getAccessToken();
        batchSendOTOHeaders.xAcsDingtalkAccessToken = accessToken;
        JSONObject jsonObject = new JSONObject();
//        "胡萝卜缺货", "金旅城店", "3", "http://ai.jxhlife.com:9530/picWithLabel/029/001/3/20240725090100.jpg", "1", "3018314737-902327211"}
        jsonObject.put("text","金旅城店通道3的胡萝卜缺货,通知时间为:" + LocalDateTime.now());
        jsonObject.put("title","金旅城店");
        jsonObject.put("picUrl","http://ai.jxhlife.com:9530/picWithLabel/029/001/3/20240725090100.jpg");
        jsonObject.put("messageUrl","dingtalk://dingtalkclient/page/link?url=http://ai.jxhlife.com:9527/item/1?userid=034700515524091917&pc_slide=false");
        com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest batchSendOTORequest = new com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest()
                .setMsgParam(jsonObject.toString())
                .setMsgKey("sampleLink")
                .setRobotCode("dingl8blibdbipryih06")//dingquk2pwxydugcrudf，dingl8blibdbipryih06
                .setUserIds(java.util.Arrays.asList(
                        "034700515524091917"
                ));//3018314737-902327211；034700515524091917
        try {
            client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new com.aliyun.teautil.models.RuntimeOptions());
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println(err.code);
                System.out.println(err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println(err.code);
                System.out.println(err.message);
            }

        }
    }
}
