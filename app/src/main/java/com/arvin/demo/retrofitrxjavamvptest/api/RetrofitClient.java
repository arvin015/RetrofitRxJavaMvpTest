package com.arvin.demo.retrofitrxjavamvptest.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arvin on 2017/5/24.
 */

public class RetrofitClient {

    private static String baseUrl = "http://news-at.zhihu.com/";

    private static Retrofit retrofit;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> classService) {
        if(retrofit == null) {
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(classService);
    }
}
