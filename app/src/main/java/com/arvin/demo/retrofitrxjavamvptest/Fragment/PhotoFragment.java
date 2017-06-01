package com.arvin.demo.retrofitrxjavamvptest.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.arvin.demo.retrofitrxjavamvptest.R;
import com.arvin.demo.retrofitrxjavamvptest.base.BaseFragment;
import com.arvin.demo.retrofitrxjavamvptest.base.BasePressenter;
import com.arvin.demo.retrofitrxjavamvptest.pressenter.PhotoPressenter;
import com.arvin.demo.retrofitrxjavamvptest.view.IPhotoView;

/**
 * Created by arvin on 2017/5/27.
 */

public class PhotoFragment extends BaseFragment implements IPhotoView {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.photo;
    }

    @Override
    public BasePressenter createPressenter() {
        return new PhotoPressenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
