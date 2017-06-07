package com.arvin.demo.retrofitrxjavamvptest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arvin.demo.retrofitrxjavamvptest.R;
import com.arvin.demo.retrofitrxjavamvptest.adapter.DividerItemDecoration;
import com.arvin.demo.retrofitrxjavamvptest.adapter.HomeAdapter;
import com.arvin.demo.retrofitrxjavamvptest.base.BaseFragment;
import com.arvin.demo.retrofitrxjavamvptest.entity.LatestNews;
import com.arvin.demo.retrofitrxjavamvptest.pressenter.HomePressenter;
import com.arvin.demo.retrofitrxjavamvptest.view.IHomeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by arvin on 2017/5/27.
 */

public class HomeFragment extends BaseFragment<IHomeView, HomePressenter> implements IHomeView {

    private String TAG = "HomeFragment";

    @BindView(R.id.homeRecycleView)
    RecyclerView homeRecycleView;
    Unbinder unbinder;

    private HomeAdapter adapter;

    private boolean isLoadingMore = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        loadData();
        setRefreshState(true);
    }

    private void initView() {

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeRecycleView.setLayoutManager(layoutManager);

        adapter = new HomeAdapter(getContext(), null);
        homeRecycleView.setAdapter(adapter);

        homeRecycleView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));

        homeRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItemPosition = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        (lastVisibleItemPosition + 1 == adapter.getItemCount())) {
                    if (!isLoadingMore) {
                        adapter.changeFootViewState(HomeAdapter.LOADING_MORE);
                        setRefreshing();
                        isLoadingMore = true;
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void loadData() {
        pressenter.getLatestNews();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint---isVisibleToUser = " + isVisibleToUser);
        if (adapter != null) {
            adapter.setAutoRollEnabled(isVisibleToUser);
        }
    }

    @Override
    public void setRefreshing() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.home;
    }

    @Override
    public HomePressenter createPressenter() {
        return new HomePressenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void returnLatestNews(LatestNews latestNews) {
        if (latestNews != null) {
            adapter.setLatestNews(latestNews);
            adapter.notifyDataSetChanged();
            setRefreshState(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
