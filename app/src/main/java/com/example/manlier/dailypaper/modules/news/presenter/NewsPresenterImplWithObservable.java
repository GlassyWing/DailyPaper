package com.example.manlier.dailypaper.modules.news.presenter;

import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.contracts.NewsType;
import com.example.manlier.dailypaper.listeners.OnLoadNewsListener;
import com.example.manlier.dailypaper.modules.news.view.NewsView;
import com.example.manlier.dailypaper.modules.providers.RetrofitService;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by manlier on 2017/5/15.
 */

public class NewsPresenterImplWithObservable
        implements NewsPresenter, OnLoadNewsListener {

    private static final String TAG =
            NewsPresenterImplWithObservable.class.getCanonicalName();

    private NewsView newsView;

    public NewsPresenterImplWithObservable(NewsView newsView) {
        this.newsView = newsView;
    }

    @Override
    public void loadNews(NewsType type, int page) {
        newsView.showRefreshing();
        RetrofitService.getNewsService().getNewsList(type.TYPE, type.ID, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Map<String, List<NewsBean>>, Observable<List<NewsBean>>>() {
                    @Override
                    public Observable<List<NewsBean>> call(Map<String, List<NewsBean>> stringListMap) {
//                        List<NewsBean> result = new ArrayList<NewsBean>();
//                        stringListMap.get(type.ID).stream()
//                                .filter(NewsPresenterImplWithObservable::isInterest)
//                                .map(bean -> {
//                                    bean.setDocid(bean.getDocid().replace("_special", ""));
//                                    return bean;
//                                })
//                                .sorted((o1, o2) -> o2.getPtime().compareTo(o1.getPtime()))
//                                .forEach(result::add);
//                        ;
                        return Observable.just(process(stringListMap.get(type.ID)));
                    }
                })
                .subscribe(new Observer<List<NewsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailure("load news fail", e);
                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeanList) {
                        onSuccess(newsBeanList);
                    }
                });

    }

    @Override
    public void onSuccess(List<NewsBean> list) {
        Logger.d(TAG, list);

        // 一旦加载完成，隐藏刷新部件
        newsView.hideRefreshing();
        Collections.sort(list, new Comparator<NewsBean>() {
            @Override
            public int compare(NewsBean o1, NewsBean o2) {
                return o2.getPtime().compareTo(o1.getPtime());
            }
        });
        newsView.addNews(list);
    }

    @Override
    public void onFailure(String msg, Throwable e) {
        newsView.hideRefreshing();
        newsView.showLoadFailMsg();
    }

    private static boolean isInterest(NewsBean bean) {
        return !bean.getDigest().equals("");
    }

    private List<NewsBean> process(List<NewsBean> source) {
        List<NewsBean> result = new ArrayList<>();
        for (NewsBean bean : source) {
            if (isInterest(bean)) {
                bean.setDocid(bean.getDocid().replace("_special", ""));
                result.add(bean);
            }
        }
        Collections.sort(result,new Comparator<NewsBean>() {
            @Override
            public int compare(NewsBean o1, NewsBean o2) {
                return o2.getPtime().compareTo(o1.getPtime());
            }
        });
        return result;
    }
}
