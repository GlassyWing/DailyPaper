package com.example.manlier.dailypaper.commons;

/**
 * Created by manlier on 2017/5/13.
 */

/**
 * 表示新闻类型
 */
public enum NewsType {

    TOP(API.TOP_ID, "头条", true),
    TECH(API.TECH_ID, "科技"),
    CHOICE(API.CHOICE_ID, "精选"),
    NBA(API.NBA_ID, "NBA"),
    JOKE(API.JOKE_ID, "笑话"),
    MILITARY(API.MILITARY_ID, "军事");

    // 类型ID
    final public String ID;

    // 类型描述
    final public String DESC;

    // 标识是否为头条
    private boolean top;

    NewsType(String ID, String desc) {
        this(ID, desc, false);
    }

    NewsType(String ID, String desc, boolean isTop) {
        this.ID = ID;
        this.DESC = desc;
        this.top = isTop;
    }

    /**
     * 生成获取新闻的Rest形式的API
     * 如： http://c.m.163.com/nc/article/headline/{T1348647909107}/0-5.html  头条
     *      http://c.m.163.com/nc/article/list/{T1350383429665}/0-5.html      笑话
     *
     * @param pageIndex 页面指针
     * @return Url字符串
     */
    public String generateNewsUrl(int pageIndex) {
        StringBuilder builder = new StringBuilder();
        if (top) {
            builder.append(API.TOP_URL);
        } else {
            builder.append(API.COMMON_URL);
        }
        builder.append(ID).append("/").append(pageIndex).append(API.END_URL);
        return builder.toString();
    }
}
