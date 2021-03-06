package com.example.manlier.dailypaper.modules.news.widget;

/**
 * Created by manlier on 2017/5/10.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.contracts.API;
import com.example.manlier.dailypaper.contracts.NewsType;
import com.example.manlier.dailypaper.adapter.NewsRecyclerViewAdapter;
import com.example.manlier.dailypaper.listeners.OnItemClickListener;
import com.example.manlier.dailypaper.modules.news.presenter.NewsPresenter;
import com.example.manlier.dailypaper.modules.news.presenter.NewsPresenterImplWithObservable;
import com.example.manlier.dailypaper.modules.news.view.NewsView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 新闻列表的Fragment
 * 它实现了刷新监听器
 */
public class NewsListFragment extends Fragment
        implements NewsView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = NewsListFragment.class
            .getCanonicalName();

    // 滑动刷新布局
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;

    private List<NewsBean> data;

    // 该Fragment所加载的新闻类型
    private NewsType type = NewsType.TOP;

    // 页面指针，指定从第几条新闻开始加载
    private int pageIndex = 0;

    // 指示当前是否正在刷新，防止多次刷新时重复加载
    private boolean refreshing = false;

    private NewsPresenter newsPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsPresenter = new NewsPresenterImplWithObservable(this);
        type = (NewsType) getArguments().getSerializable("type");
        data = new LinkedList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

        // 设置刷新部件（即刷新时出现的小球）的颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.primary,
                R.color.primary_light,
                R.color.primary_dark,
                R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        // 新闻条目按照线性垂直排列
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new NewsRecyclerViewAdapter(getActivity().getApplicationContext());
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(onScrollListener);
        onRefresh();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 为第一个页面时，初始化FAB按钮的点击事件
        if (type == NewsType.TOP)
            changeFABAction(getActivity());
    }

    public void changeFABAction(Activity activity) {
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        fab.setOnClickListener(v -> layoutManager.scrollToPositionWithOffset(0, 0));
    }

    @Override
    public void onRefresh() {
        if (refreshing) return; // 如果正在刷新中， 取消本次刷新
        pageIndex = 0;
        data.clear();
        adapter.notifyDataSetChanged();
        newsPresenter.loadNews(type, pageIndex);
    }

    @Override
    public void showRefreshing() {
        refreshing = true;
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
        refreshing = false;
    }

    @Override
    public void addNews(List<NewsBean> newsBeanList) {

        adapter.setShowFooter(true);

        data.addAll( newsBeanList);
        Collections.sort(data, new Comparator<NewsBean>() {
            @Override
            public int compare(NewsBean o1, NewsBean o2) {
                return o2.getPtime().compareTo(o1.getPtime());
            }
        });

        // 若是首次加载
        if (pageIndex == 0) {
            adapter.setData(data);   // 为adapter设置数据源
        } else {
            // 如果已无更多数据
            if (newsBeanList.size() == 0) {
                adapter.setShowFooter(false);  // 隐藏 加载中。。。
            }
        }

        // 每次数据改变，需要通知adapter
        adapter.notifyDataSetChanged();

        // 更新页面索引
        pageIndex += API.PAGE_SIZE;
    }

    @Override
    public void showLoadFailMsg() {

        // 如果发生错误时还没有加载任何数据
        if (pageIndex == 0) {
            adapter.setShowFooter(false);   // 隐藏脚部（加载中。。。部件）
            adapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? recyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 当新闻条目被点击时的监听器
     */
    private OnItemClickListener onItemClickListener = (v, position) -> {
        if (data.size() <= 0) {
            return;
        }
        NewsBean newsBean = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("news", newsBean);

        View transitionView = v.findViewById(R.id.ivNews);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        transitionView, getString(R.string.transition_news_img));   // 设置切换Activity时，新闻图片的动画
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());    // 切换到NewsDetailActivity
    };

    /**
     * 当新闻视图滚动时的监听器
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()
                    && adapter.isShowFooter()) {
                //加载更多
                Logger.d(TAG, "loading more data");
                newsPresenter.loadNews(type, pageIndex);
            }
        }
    };

    /**
     * 静态工厂方法
     *
     * @param type fragment类型，该类型与新闻类型相同
     * @return NewsListFragment
     */
    public static NewsListFragment newInstance(NewsType type) {
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        args.putSerializable("type", type);
        fragment.setArguments(args);
        return fragment;
    }
}
