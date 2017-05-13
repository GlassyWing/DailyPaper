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

    /**
     * 由于不同类型的新闻页面对FAB按钮的响应不同，故
     * 切换到当前页面时需要
     * 变更FAB按钮点击事件
     */
    void changeFABAction();

    void addNews(List<NewsBean> newsBeanList);

    void showLoadFailMsg();
}
