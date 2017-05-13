package com.example.manlier.dailypaper.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description : OkHttp网络连接工具类
 *
 * @author manlier
 */
public class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";

    private static OkHttpUtils instance;
    private OkHttpClient client;
    private Handler delivery;

    private OkHttpUtils() {
        client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        //cookie enabled
        client.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        delivery = new Handler(Looper.getMainLooper());
    }

    /**
     * 获得OkHttpClient
     * 整个系统只有一个OkHttpClient的实例，即单例
     * 因此，为了防止多线程情况下，获得不同的实例
     * 需要进行同步。
     *
     * @return client
     */
    private synchronized static OkHttpUtils getInstance() {
        if (instance == null) {
            instance = new OkHttpUtils();
        }
        return instance;
    }

    /**
     * get 请求
     *
     * @param url      url地址
     * @param callback 回调
     */
    private void getRequest(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }

    /**
     * get 请求
     *
     * @param url      url地址
     * @param callback 回调
     * @param params   表单形式的参数
     */
    private void postRequest(String url, final ResultCallback callback, List<Param> params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 发送请求结果
     *
     * @param callback 回调
     * @param request  请求
     */
    private void deliveryResult(final ResultCallback callback, Request request) {

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String str = response.body().string();
                    if (callback.paramType == String.class) {
                        sendSuccessCallBack(callback, str);
                    } else {
                        Object object = JsonUtils.deserialize(str, callback.paramType);
                        sendSuccessCallBack(callback, object);
                    }
                } catch (final Exception e) {
                    LogUtils.e(TAG, "convert json failure", e);
                    sendFailCallback(callback, e);
                }

            }
        });
    }

    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        delivery.post(() -> {
            if (callback != null) {
                callback.onFailure(e);
            }
        });
    }

    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {
        delivery.post(() -> {
            if (callback != null) {
                callback.onSuccess(obj);
            }
        });
    }

    /**
     * 创建Post请求
     *
     * @param url    url地址
     * @param params post参数
     * @return 创建好的请求
     */
    private Request buildPostRequest(String url, List<Param> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }


    /**********************对外接口************************/

    /**
     * get请求
     *
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(String url, ResultCallback callback) {
        getInstance().getRequest(url, callback);
    }

    /**
     * post请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @param params   请求参数
     */
    public static void post(String url, final ResultCallback callback, List<Param> params) {
        getInstance().postRequest(url, callback, params);
    }

    /**
     * http请求回调类,回调方法在UI线程中执行
     *
     * @param <T>
     */
    public static abstract class ResultCallback<T> {

        // 超类的形参类型
        Type paramType;

        public ResultCallback() {
            paramType = getSuperclassTypeParameter(getClass());
        }

        // 获取实际传入的形参类型
        static Type getSuperclassTypeParameter(Class<?> subclass) {

            // 获得包含形参的超类
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }

            // 转换为形参类型
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        /**
         * 请求成功回调
         *
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 请求失败回调
         *
         * @param e
         */
        public abstract void onFailure(Exception e);
    }

    /**
     * post请求参数类
     */
    public static class Param {

        String key;    // 参数键
        String value;  // 参数值

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

    }


}
