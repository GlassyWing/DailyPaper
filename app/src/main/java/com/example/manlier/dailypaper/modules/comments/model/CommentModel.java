package com.example.manlier.dailypaper.modules.comments.model;

import com.example.manlier.dailypaper.beans.CommentBean;

import java.util.List;

/**
 * Created by manlier on 2017/5/16.
 */

public interface CommentModel {

    List<CommentBean> loadComments(String docId, int index, int size);

    boolean insertComment(CommentBean commentBean);
}
