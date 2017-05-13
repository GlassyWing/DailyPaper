package com.example.manlier.dailypaper.modules.news.presenter;

/**
 * Created by manlier on 2017/5/11.
 */

import com.example.manlier.dailypaper.commons.NewsType;

/**
 * 新闻模块控制器
 */
public interface NewsPresenter {

    /**
     * 加载新闻
     *
     * @param type 新闻类型
     * @param page 页面索引
     */
    void loadNews(NewsType type, int page);
}
