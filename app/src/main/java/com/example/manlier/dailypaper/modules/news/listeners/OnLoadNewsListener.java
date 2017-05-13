package com.example.manlier.dailypaper.modules.news.listeners;

/**
 * Created by manlier on 2017/5/10.
 */

import com.example.manlier.dailypaper.beans.NewsBean;

import java.util.List;

/**
 * 新闻数据加载回调
 */
public interface OnLoadNewsListener {

    /**
     * 成功载入新闻时的回调
     *
     * @param list 载入的新闻列表
     */
    void onSuccess(List<NewsBean> list);

    /**
     * 载入失败时的回调
     * @param msg 错误消息
     * @param e 异常
     */
    void onFailure(String msg, Exception e);
}
