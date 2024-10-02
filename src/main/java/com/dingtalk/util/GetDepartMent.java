package com.dingtalk.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentGetRequest;
import com.dingtalk.api.request.OapiV2DepartmentListsubidRequest;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubidResponse;
import com.taobao.api.ApiException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dingtalk.util.WriteSQLUtil.*;

public class GetDepartMent {
    public static void main(String[] args) throws SQLException {
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/get");
//            OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
//            req.setDeptId(603536015L);
//            OapiV2DepartmentGetResponse rsp = client.execute(req, "86fe4dca0bd63638a5043ec9c2a38ad6");
//            System.out.println(rsp.getBody());

//        部门id:603536015,部门名称:店长部
//        部门id:36120264,部门名称:运营部
        HashMap<Integer, List<Long>> integerListHashMap = new HashMap<>();
        int leave = 0;
        List<Long> initDepartMentID = new ArrayList<>();
        initDepartMentID.add(1L);
        boolean flag = true;
        while (flag) {
            leave++;
            List<Long> subDepartMent = new ArrayList<>();
            for (Long aLong : initDepartMentID) {
                List<Long> subDepartMent1 = getSubDepartMent(aLong);
                assert subDepartMent1 != null;
                subDepartMent.addAll(subDepartMent1);
            }
            if (subDepartMent.size() == 0) flag = false;
            integerListHashMap.put(leave, new ArrayList<>(subDepartMent));
            initDepartMentID.clear();
            initDepartMentID = subDepartMent;
        }
//        assert subDepartMent != null;
        List<String[]> resultList = new ArrayList<>();
        for (Integer integer : integerListHashMap.keySet()) {
            List<Long> longs = integerListHashMap.get(integer);
            for (Long aLong : longs) {
                String departMentInfo = getDepartMentInfo(aLong);
                String[] res = {String.valueOf(integer), String.valueOf(aLong), departMentInfo};
                resultList.add(res);
            }
        }
        Connection connection = getConnection();
        batchInsert(connection, resultList);

        closeResources(connection);
    }

    public static List<Long> getSubDepartMent(long departMentID) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsubid");
            OapiV2DepartmentListsubidRequest req = new OapiV2DepartmentListsubidRequest();
            req.setDeptId(departMentID);
            OapiV2DepartmentListsubidResponse rsp = client.execute(req, "86fe4dca0bd63638a5043ec9c2a38ad6");
            OapiV2DepartmentListsubidResponse.DeptListSubIdResponse result = rsp.getResult();
//            System.out.println(rsp.getBody());
            return result.getDeptIdList();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDepartMentInfo(long departMentID) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/get");
            OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
            req.setDeptId(departMentID);
            OapiV2DepartmentGetResponse rsp = client.execute(req, "86fe4dca0bd63638a5043ec9c2a38ad6");
//            System.out.println(rsp.getBody());
            OapiV2DepartmentGetResponse.DeptGetResponse result = rsp.getResult();
            String name = result.getName();
            return name;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
//    602966131,
//            603291120,
//            602705188,
//            603765009
}
