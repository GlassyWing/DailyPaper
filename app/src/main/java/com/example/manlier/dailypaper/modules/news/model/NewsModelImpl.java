package com.example.manlier.dailypaper.modules.news.model;

import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.beans.NewsDetailBean;
import com.example.manlier.dailypaper.commons.API;
import com.example.manlier.dailypaper.commons.NewsType;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadNewsDetailListener;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadNewsListener;
import com.example.manlier.dailypaper.modules.news.processor.NewsJsonProcessor;
import com.example.manlier.dailypaper.modules.news.widget.NewsFragment;
import com.example.manlier.dailypaper.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by manlier on 2017/5/10.
 */

/**
 * 新闻信息数据存储处理类
 */
public class NewsModelImpl implements NewsModel {

    @Override
    public void loadNews(String url, NewsType type, OnLoadNewsListener listener) {
        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {

            @Override
            public void onSuccess(String response) {
                List<NewsBean> newsBeanList = NewsJsonProcessor.parseToNewsBeans(response, type.ID);
                listener.onSuccess(newsBeanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("载入新闻信息失败！", e);
            }
        });
    }

    @Override
    public void loadNewsDetail(String docId, OnLoadNewsDetailListener listener) {
        OkHttpUtils.get(newsDetailAPI(docId), new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean = NewsJsonProcessor.parseToNewsDetailBean(response, docId);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("载入新闻详情失败！", e);
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
