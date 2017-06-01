package com.arvin.demo.retrofitrxjavamvptest.base;

/**
 * Created by arvin on 2017/5/24.
 */

public class BasePressenter<V> {

    public V mView;

    public void attach(V view) {
        this.mView = view;
    }

    public void dettach() {
        if(mView != null) {
            mView = null;
        }
    }
}
