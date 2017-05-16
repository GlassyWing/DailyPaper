package com.example.manlier.dailypaper.modules.news.presenter;

import com.example.manlier.dailypaper.beans.NewsDetailBean;
import com.example.manlier.dailypaper.listeners.OnLoadNewsDetailListener;
import com.example.manlier.dailypaper.modules.news.view.NewsDetailView;
import com.example.manlier.dailypaper.modules.providers.RetrofitService;
import com.orhanobut.logger.Logger;

import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by manlier on 2017/5/15.
 */

public class NewsDetailPresenterImplWithObservable
        implements NewsDetailPresenter, OnLoadNewsDetailListener {

    private NewsDetailView newsDetailView;

    public NewsDetailPresenterImplWithObservable(NewsDetailView newsDetailView) {
        this.newsDetailView = newsDetailView;
    }

    @Override
    public void loadNewsDetail(String docId) {
        newsDetailView.showLoading();
        RetrofitService.getNewsService().loadNewsDetail(docId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Map<String, NewsDetailBean>, Observable<NewsDetailBean>>() {
                    @Override
                    public Observable<NewsDetailBean> call(Map<String, NewsDetailBean> stringNewsDetailBeanMap) {
                        return Observable.just(stringNewsDetailBeanMap.get(docId));
                    }
                })
                .subscribe(new Observer<NewsDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailure("load news detail fail", e);
                    }

                    @Override
                    public void onNext(NewsDetailBean newsDetailBean) {
                        onSuccess(newsDetailBean);
                    }
                });
    }

    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if (newsDetailBean != null) {
            newsDetailView.showNewsDetail(newsDetailBean.getTitle(), newsDetailBean.getBody());
        } else {
            Logger.log(Logger.ERROR, getClass().getCanonicalName(),"Load Error", null);
        }
        newsDetailView.hideLoading();
    }

    @Override
    public void onFailure(String msg, Throwable e) {
        newsDetailView.showLoadErrorMessage();
        newsDetailView.hideLoading();
    }
}
