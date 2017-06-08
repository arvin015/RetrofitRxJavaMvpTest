package com.arvin.demo.retrofitrxjavamvptest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.arvin.demo.retrofitrxjavamvptest.R;
import com.arvin.demo.retrofitrxjavamvptest.base.BaseFragment;
import com.arvin.demo.retrofitrxjavamvptest.pressenter.BlogPressenter;
import com.arvin.demo.retrofitrxjavamvptest.view.IBlogView;

/**
 * Created by arvin on 2017/5/27.
 */

public class BlogFragment extends BaseFragment<IBlogView, BlogPressenter> implements IBlogView {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.blog;
    }

    @Override
    public BlogPressenter createPressenter() {
        return new BlogPressenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setData(Object data) {

    }
}
