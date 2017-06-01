package com.arvin.demo.retrofitrxjavamvptest.article;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by arvin on 2017/5/24.
 */

public interface APIArticle {

//    @GET("article/today")
//    Call<Article> getArticle(@Query("dev") int dev);

    @GET("article/today")
    Observable<Article> getArticle(@Query("dev") int dev);
}
