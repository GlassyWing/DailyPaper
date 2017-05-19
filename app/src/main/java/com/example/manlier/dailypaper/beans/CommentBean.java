package com.example.manlier.dailypaper.beans;

import java.io.Serializable;

/**
 * Created by manlier on 2017/5/16.
 */

public class CommentBean implements Serializable {

    // id
    private int _id;

    // 文章id
    private String docId;

    // 评论人
    private String observer;

    // 评论
    private String comment;

    // 时间
    private String ptime;

    // 支持
    private int voteUp;

    // 贬低
    private int voteDown;

    public CommentBean() {}

    public CommentBean(String docId, String observer, String comment, String ptime) {
        this.docId = docId;
        this.observer = observer;
        this.comment = comment;
        this.ptime = ptime;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getObserver() {
        return observer;
    }

    public void setObserver(String observer) {
        this.observer = observer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "_id=" + _id +
                ", docId='" + docId + '\'' +
                ", observer='" + observer + '\'' +
                ", comment='" + comment + '\'' +
                ", ptime='" + ptime + '\'' +
                ", voteUp=" + voteUp +
                ", voteDown=" + voteDown +
                '}';
    }

    public int getVoteUp() {
        return voteUp;
    }

    public void setVoteUp(int voteUp) {
        this.voteUp = voteUp;
    }

    public int getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(int voteDown) {
        this.voteDown = voteDown;
    }

}
