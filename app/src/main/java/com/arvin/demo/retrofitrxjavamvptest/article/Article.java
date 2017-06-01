package com.arvin.demo.retrofitrxjavamvptest.article;

/**
 * Created by arvin on 2017/5/24.
 */

public class Article {

    private ArticleData data;

    public ArticleData getData() {
        return data;
    }

    public void setData(ArticleData data) {
        this.data = data;
    }
}

class ArticleData {

    private ArticleDate date;

    private String author;
    private String title;
    private String digest;
    private String content;

    public ArticleDate getDate() {
        return date;
    }

    public void setDate(ArticleDate date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class ArticleDate {

    private String curr;
    private String prev;
    private String next;

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}