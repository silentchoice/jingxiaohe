package com.dingtalk.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentListsubidRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubidResponse;
import com.dingtalk.util.AccessTokenUtil;
import com.taobao.api.ApiException;

public class SubDepartMentList {
    public static void getSubDepartMentList(Long departId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsubid");
            String accessToken = AccessTokenUtil.getAccessToken();
            OapiV2DepartmentListsubidRequest req = new OapiV2DepartmentListsubidRequest();
            req.setDeptId(departId);
            OapiV2DepartmentListsubidResponse rsp = client.execute(req, accessToken);
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
