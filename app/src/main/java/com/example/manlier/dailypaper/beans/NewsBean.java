package com.example.manlier.dailypaper.beans;

/**
 * Created by manlier on 2017/5/9.
 */

import java.io.Serializable;

/**
 * 新闻类实体
 */
public class NewsBean implements Serializable {

    /**
     * docid
     */
    private String docid;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String digest;
    /**
     * 图片地址
     */
    private String imgsrc;
    /**
     * 来源
     */
    private String source;
    /**
     * 时间
     */
    private String ptime;
    /**
     * TAG
     */
    private String tag;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "docid='" + docid + '\'' +
                ", title='" + title + '\'' +
                ", digest='" + digest + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", source='" + source + '\'' +
                ", ptime='" + ptime + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
