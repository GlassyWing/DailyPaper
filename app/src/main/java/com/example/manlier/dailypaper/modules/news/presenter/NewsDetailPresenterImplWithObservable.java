package com.example.manlier.dailypaper.modules.news.presenter;

import com.example.manlier.dailypaper.beans.ImgEntity;
import com.example.manlier.dailypaper.beans.NewsDetailBean;
import com.example.manlier.dailypaper.listeners.OnLoadNewsDetailListener;
import com.example.manlier.dailypaper.modules.news.view.NewsDetailView;
import com.example.manlier.dailypaper.modules.providers.RetrofitService;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                .filter(new Func1<NewsDetailBean, Boolean>() {
                    @Override
                    public Boolean call(NewsDetailBean newsDetailBean) {
                        replaceImage(newsDetailBean);
                        return true;
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
            Logger.log(Logger.ERROR, getClass().getCanonicalName(), "Load Error", null);
        }
        newsDetailView.hideLoading();
    }

    @Override
    public void onFailure(String msg, Throwable e) {
        newsDetailView.showLoadErrorMessage();
        newsDetailView.hideLoading();
    }

    /**
     * 把响应回的html文本中的<!--IMG0-->标记替换为<img>元素
     *
     * @param detailBean 新闻详情实体
     */
    private void replaceImage(NewsDetailBean detailBean) {
        List<ImgEntity> ref = detailBean.getImg();
        final String[] temp = {detailBean.getBody()};
        ref.forEach(entity -> {
            temp[0] = temp[0].replace(entity.getRef(), createImgTag(entity));
        });
        detailBean.setBody(temp[0]);
    }

    /**
     * 创建<img>元素
     *
     * @param img image 实体
     * @return 创建好的元素
     */
    private String createImgTag(ImgEntity img) {
        return "<center><img " +
                " alt='" + img.getAlt() + "'" +
                " src='" + img.getSrc() + "'" +
                "/></center>";
    }

}
