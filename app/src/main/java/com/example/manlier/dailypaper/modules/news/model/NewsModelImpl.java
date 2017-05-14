package com.example.manlier.dailypaper.modules.news.model;

import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.beans.NewsDetailBean;
import com.example.manlier.dailypaper.commons.API;
import com.example.manlier.dailypaper.commons.NewsType;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadNewsDetailListener;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadNewsListener;
import com.example.manlier.dailypaper.modules.news.presenter.NewsPresenterImpl;
import com.example.manlier.dailypaper.modules.news.processor.NewsJsonProcessor;
import com.example.manlier.dailypaper.modules.news.widget.NewsFragment;
import com.example.manlier.dailypaper.utils.OkHttpUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by manlier on 2017/5/10.
 */

/**
 * 新闻信息数据存储处理类
 */
public class NewsModelImpl implements NewsModel {

    private static int ATTEMPT_TIME = 3;
    public static final String TAG = NewsPresenterImpl.class.getCanonicalName();

    @Override
    public void loadNews(String url, NewsType type, OnLoadNewsListener listener) {

        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<NewsBean> beanList = NewsJsonProcessor.parseToNewsBeans(response, type.ID);
                listener.onSuccess(beanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("Load news fail", e);
            }
        });

    }

    @Override
    public void loadNewsDetail(String docId, OnLoadNewsDetailListener listener) {
        String url = newsDetailAPI(docId);
        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean detailBean = NewsJsonProcessor.parseToNewsDetailBean(response, docId);
                listener.onSuccess(detailBean);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("Load news detail fail", e);
            }
        });
    }

    /**
     * 取得新闻详情的URL地址
     * 完整形式如下
     * http://c.m.163.com/nc/article/{docId}/full.html
     *
     * @param docId 新闻详情Id
     * @return http://c.m.163.com/nc/article/{docId}/full.html
     */
    private String newsDetailAPI(String docId) {
        return API.NEW_DETAIL + docId + API.END_DETAIL_URL;
    }
}
