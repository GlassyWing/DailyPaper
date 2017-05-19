package com.example.manlier.dailypaper.modules.comments.view;

import com.example.manlier.dailypaper.beans.CommentBean;
import com.example.manlier.dailypaper.beans.NewsBean;

import java.util.List;

/**
 * Created by manlier on 2017/5/16.
 */

public interface CommentView {

    void showRefreshing();

    void hideRefreshing();

    void hideLoading();

    void showLoading();

    void addComments(List<CommentBean> commentBeanList);

    void showLoadResult(String info);
}
