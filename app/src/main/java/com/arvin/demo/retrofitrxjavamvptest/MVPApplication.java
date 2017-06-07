package com.arvin.demo.retrofitrxjavamvptest;

import android.app.Application;
import android.content.Context;

/**
 * Created by arvin on 2017/6/7.
 */

public class MVPApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
}
