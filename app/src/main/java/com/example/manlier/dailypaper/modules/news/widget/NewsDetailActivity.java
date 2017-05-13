package com.example.manlier.dailypaper.modules.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.modules.news.presenter.NewsDetailPresenter;
import com.example.manlier.dailypaper.modules.news.presenter.NewsDetailPresenterImpl;
import com.example.manlier.dailypaper.modules.news.view.NewsDetailView;
import com.example.manlier.dailypaper.utils.ImageLoaderUtils;
import com.example.manlier.dailypaper.wigets.MySwipeBackActivity;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by manlier on 2017/5/12.
 */

/**
 * 新闻详情界面
 * <p>
 * 该类继承自SwipeBackActivity, 以获得滑动返回的功能
 * 有关SwipeBackActivity的使用，参考：
 * https://github.com/ikew0ng/SwipeBackLayout
 */
public class NewsDetailActivity extends MySwipeBackActivity
        implements NewsDetailView {

    // 从NewsFragment传递过来的组件
    private NewsBean newsBean;

    // 工具条
    private Toolbar toolbar;

    // 显示图像的视图
    private ImageView imageView;

    // 该部件支持Html的显示
    // 详情查看 https://github.com/SufficientlySecure/html-textview
    private HtmlTextView textView;

    private TextView tvTitle;

    // 进度条
    private ProgressBar progressBar;

    // SwipeBack 布局
    private SwipeBackLayout swipeBackLayout;

    private NewsDetailPresenter newsDetailPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.ivImage);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        textView = (HtmlTextView) findViewById(R.id.htNewsContent);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        // 设置左上角是否有返回图标
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        newsBean = (NewsBean) getIntent().getSerializableExtra("news");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(newsBean.getTitle());

        ImageLoaderUtils.display(getApplicationContext(), imageView,
                newsBean.getImgsrc());

        newsDetailPresenter = new NewsDetailPresenterImpl(getApplicationContext(), this);
        newsDetailPresenter.loadNewsDetail(newsBean.getDocid());


    }


    @Override
    public void showNewsDetail(String title, String detail) {
        tvTitle.setText(title);
        textView.setHtml(detail, new HtmlHttpImageGetter(textView));
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
