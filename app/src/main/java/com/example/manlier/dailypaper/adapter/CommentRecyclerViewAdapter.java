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
import android.widget.TextView;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.beans.CommentBean;

import java.util.List;

/**
 * Created by manlier on 2017/5/16.
 */

public class CommentRecyclerViewAdapter extends WithFooterRecyclerViewAdapter<CommentBean> {

    public CommentRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 如果当前视图类型为评论类型
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(v);
        }

        // 否则为脚部类型
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footer, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentViewHolder) {
            CommentBean commentBean = data.get(position);
            if (commentBean == null) return;
            CommentViewHolder vh = (CommentViewHolder) holder;

            // 设置新闻条目的内容
            vh.tvObserver.setText(commentBean.getObserver());
            vh.tvComment.setText(commentBean.getComment());
            vh.tvDate.setText(commentBean.getPtime());
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
     * 评论视图Holder
     */
    public class CommentViewHolder extends RecyclerView.ViewHolder implements
    View.OnTouchListener {

        public TextView tvObserver;
        public TextView tvComment;
        public TextView tvDate;

        public CommentViewHolder(View itemView) {
            super(itemView);
            tvObserver = (TextView) itemView.findViewById(R.id.tvObserver);
            tvComment = (TextView) itemView.findViewById(R.id.tvComment);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            itemView.setOnTouchListener(this);
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
