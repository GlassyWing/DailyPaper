package com.example.manlier.dailypaper.modules.news.processor;

import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.beans.NewsDetailBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by manlier on 2017/5/10.
 */

/**
 * 处理新闻JSON字符串的处理器
 */
public class NewsJsonProcessor {

    private static String TAG = "NewsJsonProcessor";

    /**
     * 将包含新闻信息的json对象转译为Java实体
     *
     * @param json     新闻信息的json字符串
     * @param newsType 新闻类型
     * @return 指定新闻类型的新闻
     */
    public static List<NewsBean> parseToNewsBeans(String json, String newsType) {
        List<NewsBean> result = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Map<String, List<NewsBean>> tmp = gson.fromJson(json, new TypeToken<Map<String, List<NewsBean>>>(){}.getType());
            tmp.get(newsType).stream()
                    .filter(NewsJsonProcessor::isInterest)
                    .map(bean -> {
                        bean.setDocid(bean.getDocid().replace("_special", ""));
                        return bean;
                    })
                    .forEach(result::add);
            return result;
        } catch (Exception e) {
            Logger.log(Logger.ERROR, TAG, "error occurs on parsing news json", e);
        }
        return result;
    }

    public static List<NewsBean> parseToNewsBeans(String json, String newsType, int attemptTime) {
        for (int i = 0; i < attemptTime; i++) {
            List<NewsBean> result = parseToNewsBeans(json, newsType);
            if (result != null) return result;
            Logger.e("json parse to NewsBean error, retry times: %2d", (i + 1));
        }
        return null;
    }

    /**
     * 将新闻详情JSON 转移为Java实体
     * @param json 新闻详情json字符串
     * @param docId 详情id
     * @return 新闻详情
     */
    public static NewsDetailBean parseToNewsDetailBean(String json, String docId) {
        try {
            Gson gson = new Gson();
            Map<String, NewsDetailBean> result = gson.fromJson(json, new TypeToken<Map<String, NewsDetailBean>>(){}.getType());
            return result.get(docId);
        } catch (Exception e) {
            Logger.log(Logger.ERROR, TAG, "error occurs on parsing news detail json", e);
        }
        return null;
    }

    public static NewsDetailBean parseToNewsDetailBean(String json, String docId, int attemptTime) {
        for (int i = 0; i < attemptTime; i++) {
            NewsDetailBean result = parseToNewsDetailBean(json, docId);
            if (result != null) return result;
            Logger.e("json parse to NewsDetailBean error, retry times: %2d" + (i + 1));
        }
        return null;
    }

    private static boolean isInterest(NewsBean bean) {
        return !bean.getDigest().equals("");
    }
}
