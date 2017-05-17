package com.example.manlier.dailypaper.contracts;

import android.provider.BaseColumns;

/**
 * Created by manlier on 2017/5/16.
 */

public final class DailyPaperContract {

    private DailyPaperContract() {}

    /**
     * 评论表外观
     */
    public static class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "comment";
        public static final String COLUMN_NAME_OBSERVER = "observer";
        public static final String COLUMN_NAME_COMMENT = "comment";
        public static final String COLUMN_NAME_DATE = "ptime";
        public static final String COLUMN_NAME_DOC_ID = "docId";
    }
}
