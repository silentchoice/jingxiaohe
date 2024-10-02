package com.dingtalk.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.taobao.api.ApiException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dingtalk.util.WriteSQLUtil.*;

public class GetRoleList {
    public static List<String[]> getRoleList(Long departMentID, String departMentName) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
            OapiV2UserListRequest req = new OapiV2UserListRequest();
            req.setDeptId(departMentID);
            req.setCursor(0L);
            req.setSize(100L);
            OapiV2UserListResponse rsp = client.execute(req, "86fe4dca0bd63638a5043ec9c2a38ad6");
            OapiV2UserListResponse.PageResult result = rsp.getResult();
            List<OapiV2UserListResponse.ListUserResponse> list = result.getList();
            List<String[]> roleLists = new ArrayList<>();
//            Map<String, String[]> tmpMap = new HashMap<>();
            if (list.size() != 0) {
                for (OapiV2UserListResponse.ListUserResponse listUserResponse : list) {
                    String userid = listUserResponse.getUserid();
                    String name = listUserResponse.getName();
                    String title = listUserResponse.getTitle();
                    if (!Objects.equals(title, "开发部")) {
//                        String s1 = extractChinese(title);
                        String s = extractChinese(departMentName);
                        if ("张云芳".equals(name)) {
                            System.out.println("userid:" + userid + ",name:" + name + ",title:" + title + ",departMentName:" + s);
                        }
                        String[] roleList = {userid, name, title, s};
//                        if (tmpMap.get(userid) == null) {
//                            tmpMap.put(userid, roleList);
//                        } else if (tmpMap.get(userid) != null && title.contains("店")) {
//                            tmpMap.put(userid, roleList);
//                        }
                        roleLists.add(roleList);
                    }
                }
            }
//            for (String s : tmpMap.keySet()) {
//                roleLists.add(tmpMap.get(s));
//            }
            return roleLists;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String extractChinese(String str) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            result.append(matcher.group());
        }

        return result.toString();
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        List<String[]> departMentInfos = getDepartMentID(connection);
        ArrayList<String[]> roleLists = new ArrayList<>();
        ArrayList<String[]> resRoleLists = new ArrayList<>();
        assert departMentInfos != null;
        ArrayList<String> userIDs = new ArrayList<>();
        for (String[] departMentInfo : departMentInfos) {
            long departMentID = Long.parseLong(departMentInfo[0]);
            String departMentName = departMentInfo[1];
            List<String[]> roleList = getRoleList(departMentID, departMentName);
            assert roleList != null;
            roleLists.addAll(new ArrayList<>(roleList));
        }
        Map<String, String[]> tmpMap = new HashMap<>();
        for (String[] roleList : roleLists) {
            userIDs.add(roleList[0]);
            if (tmpMap.get(roleList[0]) == null) {
                tmpMap.put(roleList[0], roleList);
            } else if (tmpMap.get(roleList[0]) != null && roleList[3].contains("店")) {
                tmpMap.put(roleList[0], roleList);
            }
        }
        for (String s : tmpMap.keySet()) {
            String[] strings = tmpMap.get(s);
            if ("214935113424478416".equals(s)) {
                for (int i = 0; i < strings.length; i++) {
                    System.out.println("userid:" + strings[0] + ",name:" + strings[1] + ",title:" + strings[2] + ",departMentName:" + strings[3]);
                }
            }
            resRoleLists.add(tmpMap.get(s));
        }
        deleteUnusedIds(connection, userIDs);
        batchInsertRole(connection, resRoleLists);
        closeResources(connection);
    }
}
