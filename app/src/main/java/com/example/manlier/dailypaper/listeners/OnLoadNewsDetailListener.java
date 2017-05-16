package com.example.manlier.dailypaper.listeners;

/**
 * Created by manlier on 2017/5/10.
 */

import com.example.manlier.dailypaper.beans.NewsDetailBean;

/**
 * 新闻详情数据加载回调
 */
public interface OnLoadNewsDetailListener {

    /**
     * 成功载入新闻详情时的回调
     *
     * @param newsDetailBean 新闻详情实体
     */
    void onSuccess(NewsDetailBean newsDetailBean);

    /**
     * 载入失败时的回调
     *
     * @param msg 错误消息
     * @param e 异常
     */
    void onFailure(String msg, Throwable e);
}
