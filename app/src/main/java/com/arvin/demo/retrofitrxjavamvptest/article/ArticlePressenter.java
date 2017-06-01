package com.arvin.demo.retrofitrxjavamvptest.article;

import com.arvin.demo.retrofitrxjavamvptest.base.BasePressenter;

/**
 * Created by arvin on 2017/5/24.
 */

public class ArticlePressenter extends BasePressenter<IAricleView> {

    private IArticleModel articleModel;

    public ArticlePressenter() {

        articleModel = new ArticleModelImpl(new ArticleModelImpl.IArticleModelCallback() {
            @Override
            public void beforeLoad() {
                if(mView != null) {
                    mView.showLoading();
                }
            }

            @Override
            public void afterLoad() {
                if(mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void retureArticle(Article article) {
                if(mView != null) {
                    mView.getAriticle(article);
                }
            }
        });
    }

    public void getAriticle() {
        articleModel.getArticle();
    }
}
