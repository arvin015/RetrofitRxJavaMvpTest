package com.arvin.demo.retrofitrxjavamvptest.pressenter;

import com.arvin.demo.retrofitrxjavamvptest.api.RetrofitClient;
import com.arvin.demo.retrofitrxjavamvptest.api.ZhiHuAPI;
import com.arvin.demo.retrofitrxjavamvptest.base.BasePressenter;
import com.arvin.demo.retrofitrxjavamvptest.entity.LatestNews;
import com.arvin.demo.retrofitrxjavamvptest.view.IHomeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by arvin on 2017/5/27.
 */

public class HomePressenter extends BasePressenter<IHomeView> {

    private ZhiHuAPI zhiHuAPIService;

    public HomePressenter() {
        zhiHuAPIService = RetrofitClient.createService(ZhiHuAPI.class);
    }

    /**
     * 获取最新消息
     */
    public void getLatestNews() {

        if (zhiHuAPIService != null) {
            zhiHuAPIService.getLasteNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LatestNews>() {
                        @Override
                        public void accept(@NonNull LatestNews latestNews) throws Exception {
                            if (mView != null) {
                                mView.setData(latestNews);
                            }
                        }
                    });
        }
    }
}
