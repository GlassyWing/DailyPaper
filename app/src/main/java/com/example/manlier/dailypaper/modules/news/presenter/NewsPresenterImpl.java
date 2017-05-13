package com.example.manlier.dailypaper.modules.news.presenter;

import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.commons.API;
import com.example.manlier.dailypaper.commons.NewsType;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadNewsListener;
import com.example.manlier.dailypaper.modules.news.model.NewsModel;
import com.example.manlier.dailypaper.modules.news.model.NewsModelImpl;
import com.example.manlier.dailypaper.modules.news.view.NewsView;
import com.example.manlier.dailypaper.modules.news.widget.NewsFragment;
import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Created by manlier on 2017/5/11.
 */

public class NewsPresenterImpl implements
        NewsPresenter, OnLoadNewsListener {

    private static final String TAG =
            NewsPresenterImpl.class.getCanonicalName();

    private NewsView newsView;
    private NewsModel newsModel;

    public NewsPresenterImpl(NewsView newsView) {
        this.newsView = newsView;
        this.newsModel = new NewsModelImpl();
    }

    @Override
    public void loadNews(NewsType type, int page) {
        String url = type.generateNewsUrl(page);
        Logger.d(TAG, url);

        // 如果页面中没有任何数据，显示刷新部件
        if (page == 0)
            newsView.showRefreshing();
        newsModel.loadNews(url, type, this);
    }

    @Override
    public void onSuccess(List<NewsBean> list) {
        Logger.d(TAG, list);

        // 按日期从大到小排序
        Collections.sort(list, (o1, o2) -> o2.getPtime().compareTo(o1.getPtime()));

        // 一旦加载完成，隐藏刷新部件
        newsView.hideRefreshing();
        newsView.addNews(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        newsView.hideRefreshing();
        newsView.showLoadFailMsg();
    }
}
