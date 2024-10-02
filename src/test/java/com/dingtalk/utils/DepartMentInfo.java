package com.dingtalk.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentGetRequest;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;

public class DepartMentInfo {
    public static void getDepartMentInfo(Long departMentId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/get");
        OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
        req.setDeptId(100L);
        req.setLanguage("zh_CN");
//        OapiV2DepartmentGetResponse rsp = client.execute(req, access_token);
//        System.out.println(rsp.getBody());
    }
}
