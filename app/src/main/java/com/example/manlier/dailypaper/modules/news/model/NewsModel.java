package com.example.manlier.dailypaper.modules.news.model;

/**
 * Created by manlier on 2017/5/10.
 */

import com.example.manlier.dailypaper.commons.NewsType;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadNewsDetailListener;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadNewsListener;

/**
 * 处理News数据的模型
 */
public interface NewsModel {

    /**
     * 载入新闻
     *
     * @param url      新闻来源的地址
     * @param type     新闻类型
     * @param listener 载入新闻监听
     */
    void loadNews(String url, NewsType type, OnLoadNewsListener listener);

    /**
     * 载入新闻详情
     *
     * @param docId    新闻详情Id
     * @param listener 载入新闻详情监听
     */
    void loadNewsDetail(String docId, OnLoadNewsDetailListener listener);
}
