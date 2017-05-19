package com.example.manlier.dailypaper.listeners;

/**
 * Created by manlier on 2017/5/16.
 */

import com.example.manlier.dailypaper.beans.CommentBean;

import java.util.List;

/**
 * 加载评论监听
 */
public interface OnLoadCommentListener {

    /**
     * 成功载入评论时的回调
     *
     * @param commentBeanList 载入的评论列表
     */
    void onLoadSuccess(List<CommentBean> commentBeanList);

    void onInsertSuccess(CommentBean commentBean);

    /**
     * 当没有获得数据时的回调
     *
     * @param msg 消息
     */
    void onNoData(String msg);
}
