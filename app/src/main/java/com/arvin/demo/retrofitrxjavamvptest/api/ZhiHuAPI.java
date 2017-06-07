package com.arvin.demo.retrofitrxjavamvptest.api;

import com.arvin.demo.retrofitrxjavamvptest.entity.LatestNews;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by arvin on 2017/6/7.
 */

public interface ZhiHuAPI {

    @GET("api/4/news/latest")
    Observable<LatestNews> getLasteNews();
}
