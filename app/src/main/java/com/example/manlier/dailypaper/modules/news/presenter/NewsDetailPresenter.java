package com.example.manlier.dailypaper.modules.news.presenter;

/**
 * Created by manlier on 2017/5/12.
 */

/**
 * 新闻详情控制器
 */
public interface NewsDetailPresenter {

    /**
     * 载入新闻详情
     *
     * @param docId 新闻详情id
     */
    void loadNewsDetail(String docId);
}
