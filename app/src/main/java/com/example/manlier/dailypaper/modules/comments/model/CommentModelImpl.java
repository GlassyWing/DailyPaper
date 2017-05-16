package com.example.manlier.dailypaper.modules.comments.model;

import android.content.Context;
import android.database.Cursor;

import com.example.manlier.dailypaper.beans.CommentBean;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manlier on 2017/5/16.
 */

public class CommentModelImpl implements CommentModel {

    private CommentDbHelper helper;

    public CommentModelImpl(Context context) {
        helper = new CommentDbHelper(context);
    }

    @Override
    public List<CommentBean> loadComments(String docId, int index, int size) {
        Logger.w("load comments from %d to %d", index, index + size);
        List<CommentBean> result = new ArrayList<>();
        Cursor cursor = helper.getPieceComment(docId, index, size);
        while (cursor.moveToNext()) {

            String observer = cursor.getString(1);
            String comment = cursor.getString(2);
            String date = cursor.getString(3);
            CommentBean commentBean = new CommentBean(docId, observer, comment, date);
            result.add(commentBean);
        }
        cursor.close();
        return result;
    }

    public boolean insertComment(CommentBean commentBean) {
        return helper.insertData(commentBean.getObserver(), commentBean.getComment(), commentBean.getPtime(), commentBean.getDocId());
    }
}
