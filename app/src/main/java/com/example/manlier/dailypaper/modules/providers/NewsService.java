package com.example.manlier.dailypaper.modules.providers;


import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.beans.NewsDetailBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by manlier on 2017/5/15.
 */

import static com.example.manlier.dailypaper.modules.providers.RetrofitService.*;

public interface NewsService {

    @Headers({CACHE_CONTROL_NETWORK, AVOID_HTTP403_FORBIDDEN})
    @GET("http://c.m.163.com/nc/article/{docId}/full.html")
    Observable<Map<String, NewsDetailBean>> loadNewsDetail(@Path("docId") String docId);

    /**
     * 获取新闻列表
     * eg: http://c.m.163.com/nc/article/headline/T1348647909107/60-20.html
     *      http://c.m.163.com/nc/article/list/T1348647909107/60-20.html
     *
     * @param type 新闻类型
     * @param id 新闻ID
     * @param startPage 起始页码
     * @return
     */
    @Headers({CACHE_CONTROL_NETWORK, AVOID_HTTP403_FORBIDDEN})
    @GET("nc/article/{type}/{id}/{index}-20.html")
    Observable<Map<String, List<NewsBean>>> getNewsList(@Path("type") String type, @Path("id") String id,
                                                        @Path("index") int startPage);

}
