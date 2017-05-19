package com.example.manlier.dailypaper.beans;

import java.util.List;

/**
 * Created by manlier on 2017/5/9.
 */

public class NewsDetailBean {

    /**
     * docid
     */
    private String docid;
    /**
     * title
     */
    private String title;
    /**
     * source
     */
    private String source;
    /**
     * body
     */
    private String body;
    /**
     * ptime
     */
    private String ptime;
    /**
     * cover
     */
    private String cover;
    /**
     * 图片列表
     */
    private List<ImgEntity> img;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<ImgEntity> getImg() {
        return img;
    }

    public void setImg(List<ImgEntity> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "NewsDetailBean{" +
                "docid='" + docid + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", body='" + body + '\'' +
                ", ptime='" + ptime + '\'' +
                ", cover='" + cover + '\'' +
                ", img=" + img +
                '}';
    }
}
