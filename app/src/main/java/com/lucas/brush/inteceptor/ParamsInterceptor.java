package com.lucas.brush.inteceptor;


import android.text.TextUtils;

import com.lucas.brush.App;
import com.lucas.brush.UserInfo;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


//添加统一参数
public class ParamsInterceptor implements Interceptor {


    public ParamsInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();

        // 添加公共参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());
        String userId = getUserId();
        String sign = getSign();
        if (oldRequest.method().equals("GET")) {
            String needUserId = oldRequest.header("needToken");
            if (!TextUtils.isEmpty(needUserId) && needUserId.equals("false")) {

            } else {
                if (!TextUtils.isEmpty(userId)) {
                    authorizedUrlBuilder.addQueryParameter("user_id", userId);
                }
//                if (!TextUtils.isEmpty(sign)) {
//                    authorizedUrlBuilder.addQueryParameter("token", sign);
//                }
            }

        } else if (oldRequest.method().equals("POST")) {
            if (oldRequest.body() == null || oldRequest.body() instanceof FormBody) {
                String needUserId = oldRequest.header("needToken");
                if (!TextUtils.isEmpty(needUserId) && needUserId.equals("false")) {

                } else {
                    FormBody.Builder bodyBuilder = new FormBody.Builder();
                    if (!TextUtils.isEmpty(userId)) {
                        bodyBuilder.add("user_id", userId);
                    }
//                    if (!TextUtils.isEmpty(sign)) {
//                        bodyBuilder.add("token", sign);
//                    }

                    FormBody body;
                    if (oldRequest.body() != null) {
                        body = (FormBody) oldRequest.body();
                        for (int i = 0; i < body.size(); i++) {
                            bodyBuilder.add(body.name(i), body.value(i));
                        }
                    }
                    body = bodyBuilder.build();

                    oldRequest = oldRequest.newBuilder().post(body).build();
                }
            }
        }


        // 新的请求
        Request.Builder builder = oldRequest.newBuilder();
        if ("true".equals(oldRequest.header("needToken"))){
            builder.addHeader("token",sign);
        }
        Request newRequest = builder
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .removeHeader("needToken")
                .build();

        return chain.proceed(newRequest);
    }



    private String getUserId() {
        UserInfo instance = App.Companion.getInstance().userInfo;
        if (instance == null) return "";
        return instance.getUserBean().id+"";
    }

    private String getSign() {
        UserInfo instance = App.Companion.getInstance().userInfo;
        if (instance == null) return "";
        return instance.getUserBean().token;
    }

}
