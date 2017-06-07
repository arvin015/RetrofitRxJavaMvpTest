package com.arvin.demo.retrofitrxjavamvptest.view;

import com.arvin.demo.retrofitrxjavamvptest.base.IBaseView;
import com.arvin.demo.retrofitrxjavamvptest.entity.LatestNews;

import java.util.ArrayList;

/**
 * Created by arvin on 2017/5/27.
 */

public interface IHomeView extends IBaseView {

    void returnLatestNews(LatestNews latestNews);
}
