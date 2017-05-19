package com.example.manlier.dailypaper.modules.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.modules.comments.widget.CommentActivity;
import com.example.manlier.dailypaper.modules.news.presenter.NewsDetailPresenter;
import com.example.manlier.dailypaper.modules.news.presenter.NewsDetailPresenterImplWithObservable;
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

    public static final String TAG = NewsDetailActivity.class.getCanonicalName();

    // 从NewsFragment传递过来的组件
    private NewsBean newsBean;

    // 该部件支持Html的显示
    // 详情查看 https://github.com/SufficientlySecure/html-textview
    private HtmlTextView textView;

    private TextView tvTitle;

    // 底部浮动按钮
    private FloatingActionButton fabBottom;

    // 顶部评论浮动按钮
    private FloatingActionButton fabComment;

    // 进度条
    private ProgressBar progressBar;

    // SwipeBack 布局
    private SwipeBackLayout swipeBackLayout;

    private NewsDetailPresenter newsDetailPresenter;

    private boolean loadSuccess;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.ivImage);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        textView = (HtmlTextView) findViewById(R.id.htNewsContent);
        fabBottom = (FloatingActionButton) findViewById(R.id.fab);
        fabComment = (FloatingActionButton) findViewById(R.id.fab_comment);

        // AppBar使用toolbar
        setSupportActionBar(toolbar);

        // 设置显示应用条的可返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nsvDetail);
        fabBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });
        fabComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loadSuccess) {
                    showInfo("请等待加载完成");
                    return;
                }
                switchToComment();
            }
        });

        // 设置点击按钮时返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeBackLayout = getSwipeBackLayout();

        // 设置向右滑动返回
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        // 反序列化得到传入的NewsBean对象
        newsBean = fetchNewsBean(savedInstanceState);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(newsBean.getTitle());

        // 显示图片
        ImageLoaderUtils.display(getApplicationContext(), imageView,
                newsBean.getImgsrc());

        newsDetailPresenter = new NewsDetailPresenterImplWithObservable(this);
        newsDetailPresenter.loadNewsDetail(newsBean.getDocid());
    }

    /**
     * 当从评论Activity返回时恢复状态
     *
     * @param savedInstanceState 状态
     * @return
     */
    private NewsBean fetchNewsBean(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return (NewsBean) getIntent().getSerializableExtra("news");
        } else {
            return (NewsBean) savedInstanceState.getSerializable("news");
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("news", newsBean);
    }

    @Override
    public void showNewsDetail(String title, String detail) {
        loadSuccess = true;
        tvTitle.setText(title);
        textView.setHtml(detail, new HtmlHttpImageGetter(textView, null, true));
    }

    @Override
    public void showLoadErrorMessage() {
        View view = this.findViewById(R.id.nsvDetail);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    public void showInfo(String info) {
        View view = this.findViewById(R.id.nsvDetail);
        Snackbar.make(view, info, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void switchToComment() {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("news", newsBean);
        startActivity(intent);
    }
}
