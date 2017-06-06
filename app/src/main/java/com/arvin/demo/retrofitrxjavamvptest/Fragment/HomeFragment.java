package com.arvin.demo.retrofitrxjavamvptest.Fragment;

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
import com.arvin.demo.retrofitrxjavamvptest.bean.PictureInfo;
import com.arvin.demo.retrofitrxjavamvptest.pressenter.HomePressenter;
import com.arvin.demo.retrofitrxjavamvptest.view.IHomeView;

import java.util.ArrayList;

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

    private ArrayList<String> dataList;
    private HomeAdapter adapter;

    private ArrayList<PictureInfo> picList;

    private boolean isLoadingMore = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {

        dataList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            dataList.add("test" + (i + 1));
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeRecycleView.setLayoutManager(layoutManager);

        picList = new ArrayList<>();
        picList.add(new PictureInfo(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496685071389&di=d48f375afe59a0f07b8d01b38a13afa2&imgtype=0&src=http%3A%2F%2Fwww.renwen.com%2Fphoto%2F11%2F343%2F1134333_1397898795990833.jpg",
                "多肉植物1"));
        picList.add(new PictureInfo(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496685071388&di=2c92b4e248ba3940d090d17b3eb9208e&imgtype=0&src=http%3A%2F%2Fstaticqn.qizuang.com%2Fimg%2F20170420%2F58f86280562cd-s3.jpg",
                "多肉植物2"));
        picList.add(new PictureInfo(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496685071387&di=171876b2abda10b9a873bd00a9bab2ea&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20160223%2Fmp60107766_1456203432430_4.png",
                "多肉植物3"));

        adapter = new HomeAdapter(getContext(), dataList, picList);
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
        if (pressenter != null) {
            pressenter.getMainInfos(1);
        }
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
    public void returnMainInfos(ArrayList<String> infoList) {
        dataList.addAll(infoList);
        setCompleteRefresh();
        adapter.changeFootViewState(HomeAdapter.LOAD_MORE);
        isLoadingMore = false;
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
