package com.example.manlier.dailypaper.modules.news.processor;

import com.example.manlier.dailypaper.beans.NewsBean;
import com.example.manlier.dailypaper.beans.NewsDetailBean;
import com.example.manlier.dailypaper.utils.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

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
        List<NewsBean> beanList = new ArrayList<>();

        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(json).getAsJsonObject();
            JsonElement jsonElement = jsonObject.get(newsType);
            if (jsonElement == null)
                return null;
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            jsonArray.forEach(element -> {
                JsonObject jo = element.getAsJsonObject();
                if (isInterest(jo)) {
                    beanList.add(JsonUtils.deserialize(jo, NewsBean.class));
                }
            });
        } catch (Exception e) {
            Logger.log(Logger.ERROR, TAG, "error occurs on parsing news json", e);
        }

        return beanList;
    }

    /**
     * 将新闻详情JSON 转移为Java实体
     * @param json 新闻详情json字符串
     * @param docId 详情id
     * @return 新闻详情
     */
    public static NewsDetailBean parseToNewsDetailBean(String json, String docId) {
        NewsDetailBean newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(json).getAsJsonObject();
            JsonElement jsonElement = jo.get(docId);
            if (jsonElement == null)
                return null;
            newsDetailBean = JsonUtils.deserialize(jsonElement.getAsJsonObject(), NewsDetailBean.class);
        } catch (Exception e) {
            Logger.log(Logger.ERROR, TAG, "error occurs on parsing news detail json", e);
        }
        return newsDetailBean;
    }

    /**
     * 检测JsonObject对象是否是我们感兴趣的对象
     *
     * @param jo sonObject对象
     * @return bool 表示是否感兴趣
     */
    private static boolean isInterest(JsonObject jo) {
        // 跳过特殊类型
//        if (jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) {
//            return false;
//        }

        // 跳过多标签（多类型）的新闻
//        if (jo.has("TAGS") && !jo.has("TAG")) {
//            return false;
//        }
//
        // 跳过拥有图集的新闻
//        if (!jo.has("imgextra")) {
//            NewsBean news = JsonUtils.deserialize(jo, NewsBean.class);
//            return false;
//        }

        return !(jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) && !(jo.has("TAGS") /*&& !jo.has("TAG")*/) && !jo.has("imgextra");

    }
}
