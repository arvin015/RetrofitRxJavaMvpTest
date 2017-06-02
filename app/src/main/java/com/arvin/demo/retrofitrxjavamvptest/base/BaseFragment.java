package com.arvin.demo.retrofitrxjavamvptest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arvin.demo.retrofitrxjavamvptest.R;

import butterknife.ButterKnife;

/**
 * Created by arvin on 2017/5/27.
 */

public abstract class BaseFragment<V, P extends BasePressenter<V>> extends Fragment {

    public P pressenter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pressenter = createPressenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);

        if (isSetRefresh()) {
            initSwipeRefresh(view);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pressenter != null) {
            pressenter.attach((V) this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pressenter != null) {
            pressenter.dettach();
        }
    }

    private void initSwipeRefresh(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        if (refreshLayout != null) {
            refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                    android.R.color.holo_red_light, android.R.color.holo_orange_light,
                    android.R.color.holo_green_light);
            refreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                            .getDisplayMetrics()));
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    setRefreshing();
                }
            });
        }
    }

    public abstract int getLayoutId();

    public abstract P createPressenter();

    public boolean isSetRefresh() {
        return true;
    }

    public void setRefreshing() {
    }

    public void setCompleteRefresh() {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
    }
}
