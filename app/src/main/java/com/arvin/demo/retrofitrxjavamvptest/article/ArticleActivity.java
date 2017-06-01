package com.arvin.demo.retrofitrxjavamvptest.article;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arvin.demo.retrofitrxjavamvptest.R;
import com.arvin.demo.retrofitrxjavamvptest.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by arvin on 2017/5/24.
 */

public class ArticleActivity extends BaseActivity<IAricleView, ArticlePressenter> implements IAricleView {

    private String TAG = "ArticleActivity";

    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.authorText)
    TextView authorText;
    @BindView(R.id.timeText)
    TextView timeText;
    @BindView(R.id.digestText)
    TextView digestText;
    @BindView(R.id.contentText)
    TextView contentText;
    @BindView(R.id.loadingBar)
    ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取文章
        pressenter.getAriticle();
    }

    @Override
    public int getLayoutId() {
        return R.layout.article_main;
    }

    @Override
    public void getAriticle(Article article) {
        titleText.setText(article.getData().getTitle());
        authorText.setText("作者：" + article.getData().getAuthor());
        timeText.setText("时间：" + article.getData().getDate().getCurr());
        digestText.setText("摘要：" + article.getData().getDigest());
        contentText.setText(Html.fromHtml(article.getData().getContent()));
    }

    @Override
    public void showLoading() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public ArticlePressenter createPressenter() {
        return new ArticlePressenter();
    }
}
