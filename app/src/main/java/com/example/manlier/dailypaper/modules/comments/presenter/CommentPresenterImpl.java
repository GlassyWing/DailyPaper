package com.example.manlier.dailypaper.modules.comments.presenter;

import android.content.Context;

import com.example.manlier.dailypaper.beans.CommentBean;
import com.example.manlier.dailypaper.listeners.OnLoadCommentListener;
import com.example.manlier.dailypaper.modules.comments.model.CommentModel;
import com.example.manlier.dailypaper.modules.comments.model.CommentModelImpl;
import com.example.manlier.dailypaper.modules.comments.view.CommentView;
import com.orhanobut.logger.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by manlier on 2017/5/16.
 */

public class CommentPresenterImpl
        implements CommentPresenter, OnLoadCommentListener {

    private static final int charNumMinLimit = 0;

    private static final int charNumMaxLimit = 150;

    private CommentView commentView;
    private CommentModel commentModel;

    public CommentPresenterImpl(Context context, CommentView commentView) {
        this.commentView = commentView;
        commentModel = new CommentModelImpl(context);
    }

    @Override
    public void loadComments(String docId, int index, int size) {
        List<CommentBean> comments = commentModel.loadComments(docId, index, size);
        Logger.w("load results: %n%s", comments);
        if (comments.size() == 0 && index == 0) {
            onNoData("当前没有任何评论，快来抒发你的意见吧！");
        } else if (comments.size() == 0 && index != 0) {
            onNoData("没有更多评论，来抒发你的意见吧！");
        }
        onLoadSuccess(comments);

    }

    @Override
    public void insertComment(CommentBean commentBean) {
        boolean success = commentModel.insertComment(commentBean);
        if (!success) {
            onNoData("添加评论失败，请稍候重试！");
        } else {
            onInsertSuccess(commentBean);
        }
    }

    @Override
    public boolean isValidComment(String comment) {
        return charNumMinLimit < comment.length() && comment.length() < charNumMaxLimit;
    }

    @Override
    public void onLoadSuccess(List<CommentBean> commentBeanList) {
        commentView.hideRefreshing();
        commentView.addComments(commentBeanList);
    }

    @Override
    public void onInsertSuccess(CommentBean commentBean) {
        commentView.addComments(Collections.singletonList(commentBean));
    }


    @Override
    public void onNoData(String msg) {
        commentView.hideRefreshing();
        commentView.showLoadResult(msg);
    }

}
