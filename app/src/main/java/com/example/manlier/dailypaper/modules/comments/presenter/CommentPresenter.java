package com.example.manlier.dailypaper.modules.comments.presenter;

import com.example.manlier.dailypaper.beans.CommentBean;

import java.util.List;

/**
 * Created by manlier on 2017/5/16.
 */

public interface CommentPresenter {

    void loadComments(String docId, int index, int size);

    void insertComment(CommentBean commentBean);

    boolean isValidComment(String comment);
}
