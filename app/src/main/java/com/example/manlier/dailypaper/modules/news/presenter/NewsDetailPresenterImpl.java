package com.example.manlier.dailypaper.modules.news.presenter;

import android.content.Context;

import com.example.manlier.dailypaper.beans.NewsDetailBean;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadNewsDetailListener;
import com.example.manlier.dailypaper.modules.news.model.NewsModel;
import com.example.manlier.dailypaper.modules.news.model.NewsModelImpl;
import com.example.manlier.dailypaper.modules.news.view.NewsDetailView;

/**
 * Created by manlier on 2017/5/12.
 */

public class NewsDetailPresenterImpl
        implements NewsDetailPresenter, OnLoadNewsDetailListener {

    private Context context;
    private NewsDetailView newsDetailView;
    private NewsModel newsModel;

    public NewsDetailPresenterImpl(Context context, NewsDetailView newsDetailView) {
        this.context = context;
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
        }
        newsDetailView.hideLoading();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        newsDetailView.hideLoading();
    }
}
