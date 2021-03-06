package com.example.manlier.dailypaper.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.listeners.OnItemClickListener;
import com.example.manlier.dailypaper.utils.ImageLoaderUtils;


import java.util.List;

/**
 * Created by manlier on 2017/5/10.
 */

/**
 * 新闻视图的适配器
 * <p>
 * 该适配器主要负责两类视图
 * 1是 新闻条目部件 的视图
 * 2是 脚部 即：（加载中。。。）部件的视图
 */
public class NewsRecyclerViewAdapter extends WithFooterRecyclerViewAdapter<NewsBean> {

    // 条目单击监听器
    private OnItemClickListener onItemClickListener;

    public NewsRecyclerViewAdapter(Context context) {
        super(context);
    }

    public void setData(List<NewsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NewsBean getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 如果当前视图类型为新闻类型
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            return new NewsItemViewHolder(v);
        }

        // 否则为脚部类型
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footer, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // 我们只关心新闻条目的外观
        if (holder instanceof NewsItemViewHolder) {

            NewsBean newsBean = data.get(position);
            if (newsBean == null) {
                return;
            }
            NewsItemViewHolder vh = (NewsItemViewHolder) holder;

            // 设置新闻条目的内容
            vh.title.setText(newsBean.getTitle());
            vh.summary.setText(newsBean.getDigest());
            vh.time.setText(newsBean.getPtime());
            ImageLoaderUtils.display(context, vh.image, newsBean.getImgsrc());
        }
    }

    /**
     * 脚部视图Holder
     */
    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * 新闻条目视图Holder
     */
    public class NewsItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnTouchListener {

        // 新闻条目视图所持有的组件
        public TextView title;     // 标题
        public TextView summary;   // 摘要
        public ImageView image;    // 新闻图片
        public TextView time;      // 新闻日期

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            summary = (TextView) itemView.findViewById(R.id.tvDesc);
            image = (ImageView) itemView.findViewById(R.id.ivNews);
            time = (TextView) itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(this);
            itemView.setOnTouchListener(this);
        }

        @Override
        public void onClick(View v) {

            // 单击条目时，如果有监听器，则设置回调
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(itemView, this.getAdapterPosition());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ObjectAnimator upAnim = ObjectAnimator.ofFloat(v, "translationZ", 16);
                    upAnim.setDuration(150);
                    upAnim.setInterpolator(new DecelerateInterpolator());
                    upAnim.start();
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    ObjectAnimator downAnim = ObjectAnimator.ofFloat(v, "translationZ", 0);
                    downAnim.setDuration(150);
                    downAnim.setInterpolator(new AccelerateInterpolator());
                    downAnim.start();
                    break;
            }
            return false;
        }
    }
}
