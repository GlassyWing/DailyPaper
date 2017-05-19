package com.example.manlier.dailypaper.modules.comments.widget;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.adapter.CommentRecyclerViewAdapter;
import com.example.manlier.dailypaper.beans.CommentBean;
import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.modules.comments.presenter.CommentPresenter;
import com.example.manlier.dailypaper.modules.comments.presenter.CommentPresenterImpl;
import com.example.manlier.dailypaper.modules.comments.view.CommentView;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by manlier on 2017/5/15.
 */

public class CommentActivity extends AppCompatActivity
        implements CommentView, SwipeRefreshLayout.OnRefreshListener {

    // 滑动刷新布局
    private SwipeRefreshLayout swipeRefreshLayout;

    // 评论显示视图
    private RecyclerView recyclerView;
    private CommentRecyclerViewAdapter adapter;

    // 添加评论浮动按钮
    private FloatingActionButton fab;

    // 显示评论的视图所用到的布局管理器
    LinearLayoutManager linearLayoutManager;

    // 底部弹出框
    BottomSheetDialog bottomSheetDialog;

    Button btnOk;

    EditText etComment;

    private CommentPresenter presenter;

    private String docId;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);

    private int pageIndex;

    private List<CommentBean> data;

    private static final int PAGE_SIZE = 6;

    private boolean isLoading;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_recycler_view);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initView();
        data = new ArrayList<>();
        docId = fetchDocId();
        presenter = new CommentPresenterImpl(getBaseContext(), this);
        presenter.loadComments(docId, pageIndex, PAGE_SIZE);
    }

    private void initView() {

        adapter = new CommentRecyclerViewAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_comment_list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab_add_comment);
        fab.setOnClickListener(onFabAddClickListener);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_recycler_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.addOnScrollListener(onScrollListener);

        bottomSheetDialog = new BottomSheetDialog(CommentActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
        btnOk = (Button) dialogView.findViewById(R.id.btn_dialog_bottom_sheet_ok);
        Button btnCancel = (Button) dialogView.findViewById(R.id.btn_dialog_bottom_sheet_cancel);
        etComment = (EditText) dialogView.findViewById(R.id.btn_dialog_bottom_sheet_comment);

        bottomSheetDialog.setContentView(dialogView);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = etComment.getText().toString();
                String observer = getString(R.string.name);
                String ptime = dateFormat.format(new Date());

                if (!presenter.isValidComment(comment)) {
                    etComment.setError(getString(R.string.invalid_comment));
                } else {
                    CommentBean commentBean = new CommentBean(docId, observer, comment, ptime);
                    presenter.insertComment(commentBean);
                    bottomSheetDialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    private String fetchDocId() {
        return ((NewsBean) getIntent().getSerializableExtra("news")).getDocid();
    }

    @Override
    public void showRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void hideLoading() {
        adapter.setShowFooter(false);
    }

    @Override
    public void showLoading() {
        adapter.setShowFooter(true);
    }

    @Override
    public void addComments(List<CommentBean> commentBeanList) {

        hideLoading();

        data.addAll(commentBeanList);

        // 如果是首次加载
        if (pageIndex == 0) {
            adapter.setData(data);
        }

        // 如果没有更多评论，则隐藏脚部
        if (commentBeanList.size() == 0) {
            adapter.notifyDataSetChanged();
            return;
        }

        // 通知数据已改变
        adapter.notifyDataSetChanged();

        // 更新页面索引
        pageIndex += PAGE_SIZE;

    }

    @Override
    public void showLoadResult(String info) {
        if (pageIndex == 0) {hideLoading(); adapter.notifyDataSetChanged();}
        View view = this.findViewById(R.id.swipe_refresh_layout_recycler_view);
        Snackbar.make(view, info, Snackbar.LENGTH_SHORT).show();
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState > 0) {
                fab.hide();
            } else {
                fab.show();
            }

//            // 当滚动停止时，如果已经滚动到了最后一个元素
//            if (newState == RecyclerView.SCROLL_STATE_IDLE
//                    && lastVisibleItem + 1 == adapter.getItemCount()
//                    && adapter.isShowFooter()) {
//                //加载更多
//                Logger.w("loading more data");
//                presenter.loadComments(docId, pageIndex, PAGE_SIZE);
//            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {

                boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                if (isRefreshing) {
                    hideLoading();
                    return;
                }
                isLoading = true;
                showLoading();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.w("loading more data");
                        presenter.loadComments(docId, pageIndex, PAGE_SIZE);
                        isLoading = false;
                    }
                });

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

//                if(dy>0){
//                    fab.hide();
//                }else{
//                    fab.show();
//                }
        }
    };

    private View.OnClickListener onFabAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Logger.w("Clicked");
            bottomSheetDialog.show();
        }
    };

    @Override
    public void onRefresh() {
        Logger.w("loading more data");
        pageIndex = 0;
        data.clear();
        adapter.notifyDataSetChanged();
        presenter.loadComments(docId, pageIndex, PAGE_SIZE);
    }


}
