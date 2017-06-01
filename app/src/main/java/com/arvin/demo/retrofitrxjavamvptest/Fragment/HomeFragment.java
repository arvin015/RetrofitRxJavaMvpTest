package com.arvin.demo.retrofitrxjavamvptest.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arvin.demo.retrofitrxjavamvptest.R;
import com.arvin.demo.retrofitrxjavamvptest.adapter.DividerItemDecoration;
import com.arvin.demo.retrofitrxjavamvptest.adapter.HomeAdapter;
import com.arvin.demo.retrofitrxjavamvptest.base.BaseFragment;
import com.arvin.demo.retrofitrxjavamvptest.base.BasePressenter;
import com.arvin.demo.retrofitrxjavamvptest.pressenter.HomePressenter;
import com.arvin.demo.retrofitrxjavamvptest.view.IHomeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by arvin on 2017/5/27.
 */

public class HomeFragment extends BaseFragment implements IHomeView {

    @BindView(R.id.homeRecycleView)
    RecyclerView homeRecycleView;
    Unbinder unbinder;

    private ArrayList<String> dataList;
    private HomeAdapter adapter;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeRecycleView.setLayoutManager(layoutManager);

        adapter = new HomeAdapter(getContext(), dataList);
        homeRecycleView.setAdapter(adapter);

        homeRecycleView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void setRefreshing() {

        Observable.create(new ObservableOnSubscribe<ArrayList<String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ArrayList<String>> e) throws Exception {
                Thread.sleep(2000);
                ArrayList<String> addList = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    addList.add("add" + (i + 1));
                }
                e.onNext(addList);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<String>>() {
                    @Override
                    public void accept(@NonNull ArrayList<String> list) throws Exception {
                        dataList.addAll(list);
                        adapter.notifyDataSetChanged();
                        setCompleteRefresh();
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.home;
    }

    @Override
    public BasePressenter createPressenter() {
        return new HomePressenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
