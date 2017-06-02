package com.arvin.demo.retrofitrxjavamvptest.pressenter;

import com.arvin.demo.retrofitrxjavamvptest.base.BasePressenter;
import com.arvin.demo.retrofitrxjavamvptest.view.IHomeView;

import java.util.ArrayList;

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

public class HomePressenter extends BasePressenter<IHomeView> {

    public void getMainInfos(int page) {

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
                        if (mView != null) {
                            mView.returnMainInfos(list);
                        }
                    }
                });
    }
}
