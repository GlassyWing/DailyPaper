package com.example.manlier.dailypaper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by manlier on 2017/5/16.
 */

/**
 * 一个抽象的有脚部的RecyclerViewAdapter
 *
 * @param <T> 持有的数据类型
 */
public abstract class WithFooterRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    // 本适配器持有的视图有两类
    protected static final int TYPE_ITEM = 0;   // 常规的条目
    protected static final int TYPE_FOOTER = 1; // 脚部加载部件

    protected Context context;   // 上下文

    protected List<T> data;      // 持有数据

    protected boolean showFooter = true;  // 是否显示脚部

    public WithFooterRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public boolean isShowFooter() {
        return showFooter;
    }

    public void setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public abstract ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(ViewHolder holder, int position);

    /**
     * 获得条目数量
     *
     * @return 条目数量，如果有脚部则+1
     */
    @Override
    public int getItemCount() {
        int base = showFooter ? 1 : 0;
        return data == null ? base : data.size() + base;
    }

    /**
     * 返回条目类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        // 若没有显示脚部
        if (!showFooter) {
            // 直接返回新闻类型
            return TYPE_ITEM;
        }

        // 若正显示脚部且是最后一个元素
        if (position + 1 == getItemCount()) {

            // 返回脚部类型
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
}
