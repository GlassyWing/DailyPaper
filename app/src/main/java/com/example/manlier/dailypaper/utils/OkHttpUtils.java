package com.example.manlier.dailypaper.utils;

import android.os.Handler;
import android.os.Looper;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


import okhttp3.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Description : OkHttp网络连接工具类
 *
 * @author manlier
 */
public class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";

    private static OkHttpUtils instance;

    private OkHttpClient client;

    private Handler handler;

    private OkHttpUtils() {
        client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获得OkHttpClient
     * 整个系统只有一个OkHttpClient的实例，即单例
     * 因此，为了防止多线程情况下，获得不同的实例
     * 需要进行同步。
     *
     * @return client
     */
    public synchronized static OkHttpUtils getInstance() {
        if (instance == null) {
            instance = new OkHttpUtils();
        }
        return instance;
    }

    public static void get(String url, ResultCallback callback) {
        getInstance().getRequest(url, callback);
    }

    public Request buildRequest(String url) {
        return new Request.Builder()
                .url(url)
                .header("User-Agent", "okHttp3")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml,application/json;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                .build();
    }

    public void getRequest(String url, ResultCallback callback) {
        Logger.i("request url: %s", url);
        client.newCall(buildRequest(url)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.log(Logger.ERROR, TAG, "request url fail", e);
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String body = response.body().string();
                Logger.i("response body: %s", body);
                sendSuccessCallback(callback, body);
            }
        });
    }

    private void sendFailCallback(ResultCallback callback, Exception e) {
        handler.post(() -> {
            if (callback != null)
                callback.onFailure(e);
        });
    }

    private void sendSuccessCallback(ResultCallback callback, Object obj) {
        handler.post(() -> {
            if (callback != null) {
                callback.onSuccess(obj);
            }
        });
    }

    public static abstract class ResultCallback<T> {
        /**
         * 请求成功回调
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 请求失败回调
         * @param e
         */
        public abstract void onFailure(Exception e);
    }
}
