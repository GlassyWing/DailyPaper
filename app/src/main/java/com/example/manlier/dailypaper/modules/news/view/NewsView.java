package com.example.manlier.dailypaper.modules.news.view;

/**
 * Created by manlier on 2017/5/10.
 */

import com.example.manlier.dailypaper.beans.NewsBean;

import java.util.List;

/**
 * 新闻视图关心的操作
 */
public interface NewsView {

    void showLoading();

    void hideLoading();

    void addNews(List<NewsBean> newsBeanList);

    void showLoadFailMsg();
}
