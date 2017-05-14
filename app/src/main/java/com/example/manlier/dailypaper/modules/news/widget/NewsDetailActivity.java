package com.example.manlier.dailypaper.modules.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.modules.news.presenter.NewsDetailPresenter;
import com.example.manlier.dailypaper.modules.news.presenter.NewsDetailPresenterImpl;
import com.example.manlier.dailypaper.modules.news.view.NewsDetailView;
import com.example.manlier.dailypaper.utils.ImageLoaderUtils;
import com.example.manlier.dailypaper.wigets.MySwipeBackActivity;
import com.orhanobut.logger.Logger;

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

    public static final String TAG = NewsDetailActivity.class.getCanonicalName();

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

    // 浮动按钮
    private FloatingActionButton fab;

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
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // AppBar使用toolbar
        setSupportActionBar(toolbar);

        // 设置显示应用条的可返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nsvDetail);
        fab.setOnClickListener(v -> scrollView.fullScroll(View.FOCUS_UP));

        // 设置点击按钮时返回
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);


        // 反序列化得到传入的NewsBean对象
        newsBean = (NewsBean) getIntent().getSerializableExtra("news");
        Logger.i("get newsBean: %s", newsBean.toString());

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
