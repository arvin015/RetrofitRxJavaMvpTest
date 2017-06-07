package com.arvin.demo.retrofitrxjavamvptest.entity;

/**
 * Created by arvin on 2017/6/5.
 */

public class PictureInfo {

    private int id;
    private String intro;
    private String path;

    public PictureInfo(String path, String intro) {
        this.path = path;
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
