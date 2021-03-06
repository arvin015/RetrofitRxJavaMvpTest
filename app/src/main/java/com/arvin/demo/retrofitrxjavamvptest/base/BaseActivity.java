package com.arvin.demo.retrofitrxjavamvptest.base;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by arvin on 2017/5/24.
 */

public abstract class BaseActivity<V, P extends BasePressenter<V>> extends Activity {

    public P pressenter;
    private int layoutId = -1;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutId = getLayoutId();
        setContentView(layoutId);
        unbinder = ButterKnife.bind(this);
        pressenter = createPressenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pressenter != null) {
            pressenter.attach((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        if (pressenter != null) {
            pressenter.dettach();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public abstract int getLayoutId();

    public abstract P createPressenter();
}
