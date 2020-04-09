package com.android.adtdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author chenfeiyue
 * @since [历史 创建日期:2020/4/9]
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
