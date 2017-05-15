package com.example.manlier.dailypaper.modules.news.view;

/**
 * Created by manlier on 2017/5/10.
 */

/**
 * 新闻详情视图所关心的操作
 */
public interface NewsDetailView {

    void showNewsDetail(String title, String detail);

    void showLoadErrorMessage();

    void showLoading();

    void hideLoading();
}
