package com.example.manlier.dailypaper.modules.news.listeners;

import android.view.View;

/**
 * Created by manlier on 2017/5/10.
 */

/**
 * 条目单击监听器
 */
public interface OnItemClickListener {

    /**
     * 当条目被单击时的回调
     *
     * @param view 视图
     * @param position 布局中的位置
     */
    void onItemClick(View view, int position);
}
