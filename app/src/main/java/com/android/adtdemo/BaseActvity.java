package com.android.adtdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.adtiming.mediationsdk.AdTimingAds;

/**
 * @author chenfeiyue
 * @since [历史 创建日期:2020/4/9]
 */
public class BaseActvity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdTimingAds.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AdTimingAds.onPause(this);
    }
}
