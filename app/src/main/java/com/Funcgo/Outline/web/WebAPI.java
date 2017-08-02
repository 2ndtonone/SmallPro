package com.Funcgo.Outline.web;

import com.Funcgo.Outline.utils.ConstantUtils;
import com.Funcgo.Outline.utils.Utility;
import com.loopj.android.http.IResponseHandler;
import com.loopj.android.http.RequestHandle;

/**
 * Created by ydh on 7/24/15.
 */
public class WebAPI {

    public static RequestHandle get(String operator, WebParam params, IResponseHandler httpResponseHandler) {
        String url = ConstantUtils.getPhpUrl(operator);
        url = Utility.addParamToUrl(url, params);
        return AhAsyncHttpClient.getInstance().get(url, httpResponseHandler);
    }

    public static RequestHandle post(String operator, WebParam params, IResponseHandler httpResponseHandler) {
        return AhAsyncHttpClient.getInstance().post(operator, params, httpResponseHandler);
    }

    public static void getCheckUpdatePost(IResponseHandler handler) {
        String operator = "";
        WebParam params = new WebParam();
        get(operator, params, handler);
    }

    public static void getAreaData(IResponseHandler handler) {

    }

    public static void login(String account, String pwd, IResponseHandler handler) {
        String operator = "pwdlogin";
        WebParam params = new WebParam();
        params.put("account_number", account);
        params.put("password", pwd);
        post(operator, params, handler);
    }

    public static void sendVerifyCode(String account, IResponseHandler handler) {
        String operator = "getverify";
        WebParam params = new WebParam();
        params.put("verify_code", account);
        post(operator, params, handler);

    }

    public static void regist(String nickname, String account, String code, String pwd, IResponseHandler handler) {
        String operator = "register";
        WebParam params = new WebParam();
        params.put("is_law", "1");
        params.put("account_number", account);
        params.put("password", pwd);
        params.put("verify_code", code);
        params.put("name", nickname);
        post(operator, params, handler);

    }

    public static void getUserInfo(String token, IResponseHandler handler) {
        String operator = "userconf";
        WebParam params = new WebParam();
        params.put("api_token", token);
        get(operator, params, handler);

    }

    public static void getServiceCountryList(String token, IResponseHandler handler) {
        String operator = "countrylist";
        WebParam params = new WebParam();
        params.put("api_token", token);
        get(operator, params, handler);

    }

    public static void getServiceList(String token, IResponseHandler handler) {
        String operator = "orderlist";
        WebParam params = new WebParam();
        params.put("api_token", token);
        get(operator, params, handler);

    }

    public static void getServiceDetail(String token, String country_service, String country_name, IResponseHandler handler) {
        String operator = "checkservice";
        WebParam params = new WebParam();
        params.put("api_token", token);
        params.put("service", country_service);
        params.put("country_service", country_name);
        post(operator, params, handler);

    }

    public static void pay(String token, int id, IResponseHandler handler) {
        String operator = "alipayorder";
        WebParam params = new WebParam();
        params.put("api_token", token);
        params.put("order_id", id);
        post(operator, params, handler);
    }

    public static void getProtocalData(IResponseHandler handler) {
        String operator = "useragreement";
        WebParam params = new WebParam();
        get(operator, params, handler);
    }

    public static void resetPwd(String account, String code, String pwd, IResponseHandler handler) {
        String operator = "forgotpassword";
        WebParam params = new WebParam();
        params.put("account_number", account);
        params.put("password", pwd);
        params.put("verify_code", code);
        post(operator, params, handler);
    }

    public static void sendAlipaySuccess(String token, String order_id, String trade_no, IResponseHandler handler) {
        String operator = "returnalipay";
        WebParam params = new WebParam();
        params.put("api_token", token);
        params.put("order_id", order_id);
        params.put("trade_no", trade_no);
        post(operator, params, handler);


    }
}