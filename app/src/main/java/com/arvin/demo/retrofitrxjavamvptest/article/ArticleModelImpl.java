package com.arvin.demo.retrofitrxjavamvptest.article;

import android.util.Log;

import com.arvin.demo.retrofitrxjavamvptest.api.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by arvin on 2017/5/24.
 */

public class ArticleModelImpl implements IArticleModel {

    private String TAG = "ArticleModelImpl";

    private IArticleModelCallback callback;

    public ArticleModelImpl(IArticleModelCallback callback) {
        this.callback = callback;
    }

    @Override
    public void getArticle() {

        APIArticle apiArticle = RetrofitClient.createService(APIArticle.class);
        apiArticle.getArticle(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if(callback != null) {
                            callback.beforeLoad();
                        }
                    }

                    @Override
                    public void onNext(@NonNull Article article) {
                        Log.d(TAG, "onResponse = " + article.toString());

                        //转换时间格式yyyy-MM-dd
                        ArticleDate date = article.getData().getDate();
                        String curTime = date.getCurr();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
                        try {
                            Date date1 = sdf.parse(curTime);
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
                            article.getData().getDate().setCurr(sdf1.format(date1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(callback != null) {
                            callback.retureArticle(article);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "Error = " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if(callback != null) {
                            callback.afterLoad();
                        }
                    }
                });
//        Call<Article> call = apiArticle.getArticle(1);
//        call.enqueue(new Callback<Article>() {
//            @Override
//            public void onResponse(Call<Article> call, Response<Article> response) {
//
//                Log.d(TAG, "onResponse = " + response.body().toString());
//
//                Article article = response.body();
//
//                //转换时间格式yyyy-MM-dd
//                ArticleDate date = article.getData().getDate();
//                String curTime = date.getCurr();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
//                try {
//                    Date date1 = sdf.parse(curTime);
//                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
//                    article.getData().getDate().setCurr(sdf1.format(date1));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                if(callback != null) {
//                    callback.retureArticle(article);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Article> call, Throwable t) {
//                Log.d(TAG, "onFailure = " + t.toString());
//            }
//        });
    }

    interface IArticleModelCallback {
        void beforeLoad();
        void afterLoad();
        void retureArticle(Article article);
    }
}
