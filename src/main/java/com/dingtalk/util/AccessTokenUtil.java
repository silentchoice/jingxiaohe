package com.dingtalk.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.constant.AppConstant;
import com.dingtalk.constant.UrlConstant;
import com.taobao.api.ApiException;

/**
 * 获取access_token工具类
 */
public class AccessTokenUtil {

    public static String getAccessToken() throws ApiException {
//        DefaultDingTalkClient client = new DefaultDingTalkClient(UrlConstant.GET_ACCESS_TOKEN_URL);
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();

        request.setAppkey(AppConstant.APP_KEY);
//        request.setAppkey("dingquk2pwxydugcrudf");
        request.setAppsecret(AppConstant.APP_SECRET);
//        request.setAppsecret("hJgL2f9QYmTwuuiXazTNiol7qo5WlxC4udxihHBaFaPSe0XH3kqfRGrF9nvMzQV2");
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);

        return response.getAccessToken();
    }

    public static void main(String[] args) throws ApiException {
        String accessToken = getAccessToken();
        System.out.println(accessToken);
    }
//    79892187,
//            603286125,
//            887223814

//    603286125,
//            603595035,
//            603536015,



//    "1210354163849773",
//            "17107484492703764",
//            "214935113424478416",
//            "1715862077715538",
//            "17210332121515146",
//            "1769250348674567",
//            "213751204226584390",
//            "212449351424312461",
//            "19105966201218830"
}
