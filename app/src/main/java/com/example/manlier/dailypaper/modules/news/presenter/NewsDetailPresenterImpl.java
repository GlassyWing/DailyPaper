package com.example.manlier.dailypaper.modules.news.presenter;

import android.content.Context;

import com.example.manlier.dailypaper.beans.NewsDetailBean;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadNewsDetailListener;
import com.example.manlier.dailypaper.modules.news.model.NewsModel;
import com.example.manlier.dailypaper.modules.news.model.NewsModelImpl;
import com.example.manlier.dailypaper.modules.news.view.NewsDetailView;
import com.orhanobut.logger.Logger;

/**
 * Created by manlier on 2017/5/12.
 */

public class NewsDetailPresenterImpl
        implements NewsDetailPresenter, OnLoadNewsDetailListener {

    private NewsDetailView newsDetailView;
    private NewsModel newsModel;

    public NewsDetailPresenterImpl( NewsDetailView newsDetailView) {
        this.newsDetailView = newsDetailView;
        newsModel = new NewsModelImpl();
    }

    @Override
    public void loadNewsDetail(String docId) {
        newsDetailView.showLoading();
        newsModel.loadNewsDetail(docId, this);
    }

    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if (newsDetailBean != null) {
            newsDetailView.showNewsDetail(newsDetailBean.getTitle(), newsDetailBean.getBody());
        } else {
            Logger.log(Logger.ERROR, getClass().getCanonicalName(),"Load Error", null);
        }
        newsDetailView.hideLoading();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        newsDetailView.hideLoading();
    }
}
