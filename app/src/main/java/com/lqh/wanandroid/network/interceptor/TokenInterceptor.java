package com.lqh.wanandroid.network.interceptor;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lqh.wanandroid.ConstantValues;
import com.lqh.wanandroid.network.NetworkCons;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

/**
 * token 失效，自动刷新token，然后再次发送请求，用户无感知
 * User: ljx
 * Date: 2019-12-04
 * Time: 11:56
 */
public class TokenInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);

        int code = originalResponse.code();
        LogUtils.d("请求返回的httpCode码: " + code);

        if (401 == (code)) {
            //token 失效  1、这里根据自己的业务需求写判断条件
            return handleTokenInvalid(chain, request);
        }
        return originalResponse;
    }


    //处理token失效问题
    private Response handleTokenInvalid(Chain chain, Request request) throws IOException {
        HashMap<String, String> mapParam = new HashMap<>();
        RequestBody body = request.body();
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            for (int i = 0; i < formBody.size(); i++) {
                //2、保存参数
                mapParam.put(formBody.name(i), formBody.value(i));
            }
        }
        //同步刷新token
        //3、发请求前需要add("request_time",System.currentTimeMillis())
        boolean success = refreshToken();
        Request newRequest;
        if (success) { //刷新成功，重新签名
            //4、拿到最新的token,重新发起请求
//            mapParam.put("token", User.get().getToken());
            newRequest =
                    RxHttp.postForm(request.url().toString())
                            .addHeader("token", SPUtils.getInstance().getString(ConstantValues.TOKEN))
                            .addAll(mapParam) //添加参数
                            .buildRequest();
        } else {
            newRequest = request;
        }
        return chain.proceed(newRequest);
    }

    //刷新token
    private boolean refreshToken() {
        //请求时间小于token刷新时间，说明token已经刷新，则无需再次刷新
        synchronized (this) {
            //再次判断是否已经刷新
            try {
                //获取到最新的token，这里需要同步请求token,千万不能异步  5、根据自己的业务修改
                com.lqh.wanandroid.network.entity.Response execute = RxHttp.get(NetworkCons.INSTANCE.getGetTokenUrl())
                        .execute(SimpleParser.get(com.lqh.wanandroid.network.entity.Response.class));
                String token = (String) execute.getData();
                LogUtils.d("获取到的token:" + token);
                SPUtils.getInstance().put(ConstantValues.TOKEN, token);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }
}
