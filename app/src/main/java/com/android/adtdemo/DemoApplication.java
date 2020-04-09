package com.android.adtdemo;

import androidx.multidex.MultiDexApplication;

import com.adtiming.mediationsdk.utils.DeveloperLog;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author chenfeiyue
 * @since [历史 创建日期:2020/4/9]
 */
public class DemoApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        DeveloperLog.enableDebug(this, true);
    }

}
