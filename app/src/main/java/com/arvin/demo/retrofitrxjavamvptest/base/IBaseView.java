package com.arvin.demo.retrofitrxjavamvptest.base;

/**
 * Created by arvin on 2017/5/24.
 */

public interface IBaseView<M> {

    void showLoading();

    void hideLoading();

    void setData(M data);
}
