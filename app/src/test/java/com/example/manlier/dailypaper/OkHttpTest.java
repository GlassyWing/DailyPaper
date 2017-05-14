package com.example.manlier.dailypaper;

import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;

/**
 * Created by manlier on 2017/5/14.
 */

public class OkHttpTest {


    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void getNewsBeans() {
        Request request = new Request.Builder()
                .url("http://c.m.163.com/nc/article/headline/T1348647909107/0-5.html")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected coe " + response);

                Headers headers = response.headers();
                headers.names().forEach(name -> {
                    System.out.println(name + ":" + headers.get(name));
                });

                System.out.println(response.body().string());
                Logger.json(response.body().string());
            }
        });
    }

    public static void main(String[] args) {
        new OkHttpTest().getNewsBeans();
    }
}
