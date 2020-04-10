package com.android.adtdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adtiming.mediationsdk.AdTimingAds;
import com.adtiming.mediationsdk.InitCallback;
import com.adtiming.mediationsdk.banner.BannerAd;
import com.adtiming.mediationsdk.banner.BannerAdListener;
import com.adtiming.mediationsdk.interactive.AdTimingInteractiveAd;
import com.adtiming.mediationsdk.interactive.InteractiveAdListener;
import com.adtiming.mediationsdk.interstitial.AdTimingInterstitialAd;
import com.adtiming.mediationsdk.interstitial.InterstitialAdListener;
import com.adtiming.mediationsdk.nativead.AdIconView;
import com.adtiming.mediationsdk.nativead.AdInfo;
import com.adtiming.mediationsdk.nativead.MediaView;
import com.adtiming.mediationsdk.nativead.NativeAd;
import com.adtiming.mediationsdk.nativead.NativeAdListener;
import com.adtiming.mediationsdk.nativead.NativeAdView;
import com.adtiming.mediationsdk.utils.error.AdTimingError;
import com.adtiming.mediationsdk.utils.model.Scene;
import com.adtiming.mediationsdk.video.AdTimingRewardedVideo;
import com.adtiming.mediationsdk.video.RewardedVideoListener;

/**
 * @author chenfeiyue
 * @since [历史 创建日期:2020/4/9]
 */
public class MainActivity extends BaseActvity implements View.OnClickListener {

    FrameLayout bannerLayout;

    BannerAd bannerAd;

    NativeAd nativeAd;

    private NativeAdView nativeAdView;
    private FrameLayout nativeAdParent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_init).setOnClickListener(this);
        findViewById(R.id.btn_video).setOnClickListener(this);
        findViewById(R.id.btn_interstitial).setOnClickListener(this);
        findViewById(R.id.btn_native).setOnClickListener(this);
        findViewById(R.id.btn_banner).setOnClickListener(this);
        findViewById(R.id.btn_interactive).setOnClickListener(this);
        bannerLayout = findViewById(R.id.banner_layout);
        nativeAdParent = findViewById(R.id.nativeAdParent);
        setVideoListener();
        nativeAdView = new NativeAdView(this);
//        AdRequest.Builder.addTestDevice("98203978D7423F312839081A0B4EA435")
    }

    private void initSdk() {
//        MobileAds.initialize(this, "ca-app-pub-1893299139380965~7586304539");

        AdLog.LogE("---------------");
        String appKey = "1Xc9hP0H7QgMDkHfHBtlx0bFGszWFVy9";
        AdTimingAds.init(this, appKey, new InitCallback() {

            // Invoked when the initialization is successful.
            @Override
            public void onSuccess() {
                AdLog.LogE("-----------");
            }

            // Invoked when the initialization is failed.
            @Override
            public void onError(AdTimingError error) {
                AdLog.LogE("-----------" + error.toString());
            }
        }, AdTimingAds.AD_TYPE.INTERSTITIAL, AdTimingAds.AD_TYPE.REWARDED_VIDEO);
        setVideoListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_init:
                initSdk();
                break;
            case R.id.btn_video:
                video();
                break;
            case R.id.btn_interstitial:
                interstitial();
                break;
            case R.id.btn_native:
                nativeAd();
                break;
            case R.id.btn_banner:
                bannerAd();
                break;
            case R.id.btn_interactive:
                interactiveAd();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bannerAd != null) {
            bannerAd.destroy();
        }
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }

    private void nativeAd() {
        nativeAd = new NativeAd(this, "6759", new NativeAdListener() {

            /**
             * Invoked when Native Ad are available.
             * You can then show the video by calling nativeAd.showAd().
             */
            @Override
            public void onAdReady(AdInfo info) {
                //native ad load success
                AdLog.LogE("--------------CTA: " + info.getCallToActionText() + ", desc:" + info.getDesc()
                        + ",title: " + info.getTitle() + ", rate:" + info.getStarRating());


                showNative(info);

            }

            /**
             * Invoked when the end user clicked on the Native Ad
             */
            @Override
            public void onAdClicked() {
                //native ad click
                AdLog.LogE("--------------");
            }

            /**
             * Invoked when the call to load a Native Ad has failed
             * String error contains the reason for the failure.
             */
            @Override
            public void onAdFailed(String error) {
                //native ad load failed
                AdLog.LogE("--------------" + error);
            }
        });
        nativeAd.loadAd();
    }

    private void setVideoListener() {
        AdTimingRewardedVideo.setAdListener(new RewardedVideoListener() {

            /**
             * Invoked when the ad availability status is changed.
             *
             * @param - available is a boolean.
             *		True: means the rewarded videos is available and
             *                you can show the video by calling AdTimingRewardedVideo.showAd().
             *		False: means no videos are available
             */
            @Override
            public void onRewardedVideoAvailabilityChanged(boolean b) {
                // Change the rewarded video state according to availability in app.
                // You could show ad right after it's was loaded here
                AdLog.LogE("--------------" + b);
            }

            /**
             * Invoked when the RewardedVideo ad view has opened.
             * Your activity will lose focus.
             */
            @Override
            public void onRewardedVideoAdShowed(Scene scene) {
                // Do not perform heavy tasks till the video ad is going to be closed.
                AdLog.LogE("--------------" + scene);
            }

            /**
             * Invoked when the call to show a rewarded video has failed
             * @param - error contains the reason for the failure:
             */
            @Override
            public void onRewardedVideoAdShowFailed(Scene scene, AdTimingError adTimingError) {
                // Video Ad show failed
                AdLog.LogE("--------------" + scene + "  error:" + adTimingError.toString());
            }

            /**
             * Invoked when the user clicked on the RewardedVideo ad.
             */
            @Override
            public void onRewardedVideoAdClicked(Scene scene) {
                // Video Ad is clicked
                AdLog.LogE("--------------" + scene);
            }

            /**
             * Invoked when the RewardedVideo ad is to be closed.
             * Your activity will regain focus.
             */
            @Override
            public void onRewardedVideoAdClosed(Scene scene) {
                // Video Ad Closed
                AdLog.LogE("--------------" + scene);
            }

            /**
             * Invoked when the RewardedVideo ad start to play.
             * Your activity will regain focus.
             * NOTE:You may not receive this callback on some AdNetworks.
             */
            @Override
            public void onRewardedVideoAdStarted(Scene scene) {
                // Video Ad Started
                AdLog.LogE("--------------" + scene);
            }

            /**
             * Invoked when the RewardedVideo ad play end.
             * Your activity will regain focus.
             * NOTE:You may not receive this callback on some AdNetworks.
             */
            @Override
            public void onRewardedVideoAdEnded(Scene scene) {
                // Video Ad play end
                AdLog.LogE("--------------" + scene);
            }

            /**
             * Invoked when the video is completed and the user should be rewarded.
             * If using server-to-server callbacks you may ignore this events and wait
             * for the callback from the AdTiming server.
             */
            @Override
            public void onRewardedVideoAdRewarded(Scene scene) {
                // Here you can reward the user according to your in-app settings.
                AdLog.LogE("--------------" + scene);
            }
        });
        AdTimingInterstitialAd.setAdListener(new InterstitialAdListener() {

            /**
             * Invoked when the interstitial ad availability status is changed.
             *
             * @param - available is a boolean.
             *			True: means the interstitial ad is available and you can
             *				  show the video by calling AdTimingInterstitialAd.showAd().
             *			False: means no ad are available
             */
            @Override
            public void onInterstitialAdAvailabilityChanged(boolean available) {
                // Change the interstitial ad state in app according to param available.
                AdLog.LogE("--------------" + available);
            }

            /**
             * Invoked when the Interstitial ad view has opened.
             * Your activity will lose focus.
             */
            @Override
            public void onInterstitialAdShowed(Scene scene) {
                // Do not perform heavy tasks till the ad is going to be closed.
                AdLog.LogE("--------------" + scene);
            }

            @Override
            public void onInterstitialAdShowFailed(Scene scene, AdTimingError adTimingError) {
                AdLog.LogE("--------------" + scene);
            }

            /**
             * Invoked when the Interstitial ad is to be closed.
             * Your activity will regain focus.
             */
            @Override
            public void onInterstitialAdClosed(Scene scene) {
                AdLog.LogE("--------------" + scene);
            }

            /**
             * Invoked when the user clicked on the Interstitial ad.
             */
            @Override
            public void onInterstitialAdClicked(Scene scene) {
                AdLog.LogE("--------------" + scene);
            }

        });


        AdTimingInteractiveAd.setAdListener(new InteractiveAdListener() {

            /**
             * Invoked when the interactive ad availability status is changed.
             *
             * @param - available is a boolean.
             *			True: means the interactive ad is available and you can
             *				  show the ad by calling AdTimingInteractiveAd.showAd().
             *			False: means no ad are available
             */
            @Override
            public void onInteractiveAdAvailabilityChanged(boolean available) {
                // Change the interactive ad state in app according to param available.
                AdLog.LogE("--------------" + available);
            }

            /**
             * Invoked when the interactive ad view has opened.
             * Your activity will lose focus.
             */
            @Override
            public void onInteractiveAdShowed(Scene scene) {
                // Do not perform heavy tasks till the ad is going to be closed.
                AdLog.LogE("--------------" + scene);
            }

            @Override
            public void onInteractiveAdShowFailed(Scene scene, AdTimingError adTimingError) {
                AdLog.LogE("--------------" + scene + "  error：" + adTimingError.toString());
            }

            /**
             * Invoked when the interactive ad is to be closed.
             * Your activity will regain focus.
             */
            @Override
            public void onInteractiveAdClosed(Scene scene) {
                AdLog.LogE("--------------" + scene);
            }

        });
    }


    private void video() {
        AdLog.LogE("---------" + AdTimingRewardedVideo.isSceneCapped("Default_Scene"));
        Scene default_scene = AdTimingRewardedVideo.getSceneInfo("test");

        String scene = default_scene == null ? "" : default_scene.toString();

        AdLog.LogE(scene);

        if (AdTimingRewardedVideo.isReady()) {
            AdTimingRewardedVideo.showAd("Default_Scene");
        } else {
            AdTimingRewardedVideo.loadAd();
        }

    }

    private void interstitial() {
        if (AdTimingInterstitialAd.isReady()) {
            AdTimingInterstitialAd.showAd();
        } else {
            AdTimingInterstitialAd.loadAd();
        }
    }

    private void bannerAd() {
        bannerAd = new BannerAd(MainActivity.this, "placementId", new BannerAdListener() {

            /**
             * Invoked when Banner Ad are available.
             * You can then show the video by calling bannerAd.showAd().
             */
            @Override
            public void onAdReady(View view) {
                // bannerAd is load success
                AdLog.LogE("--------------");

                if (null != view.getParent()) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                bannerLayout.removeAllViews();
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                bannerLayout.addView(view, layoutParams);
            }

            /**
             * Invoked when the end user clicked on the Banner Ad
             */
            @Override
            public void onAdClicked() {
                // bannerAd click
                AdLog.LogE("--------------");
            }

            /**
             * Invoked when the call to load a Banner Ad has failed
             * String error contains the reason for the failure.
             */
            @Override
            public void onAdFailed(String error) {
                // bannerAd fail
                AdLog.LogE("--------------");
            }
        });
    }

    private void interactiveAd() {
        if (AdTimingInteractiveAd.isReady()) {
            AdTimingInteractiveAd.showAd();
        } else {
            AdTimingInteractiveAd.loadAd();
        }
    }

    private void showNative(AdInfo adInfo) {
        if ((nativeAdView != null) && (adInfo != null)) {
            nativeAdView.removeAllViews();
            View adView = View.inflate(this, R.layout.native_ad_layout2, null);
            TextView title = adView.findViewById(R.id.ad_title);
            title.setText(adInfo.getTitle());
            TextView text = adView.findViewById(R.id.ad_text);
            text.setText(adInfo.getDesc());
            Button btn = adView.findViewById(R.id.ad_btn);
            btn.setText(adInfo.getCallToActionText());

            MediaView mediaView = adView.findViewById(R.id.ad_media);

            AdIconView iconMediaView = adView.findViewById(R.id.ad_icon_media);

            nativeAdParent.removeAllViews();
            nativeAdView.addView(adView);

            nativeAdView.setTitleView(title);
            nativeAdView.setDescView(text);
            nativeAdView.setAdIconView(iconMediaView);
            nativeAdView.setMediaView(mediaView);
            nativeAdView.setCallToActionView(btn);

            nativeAd.registerNativeAdView(nativeAdView);

            adView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            adView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(Gravity.CENTER);
            nativeAdParent.addView(nativeAdView, layoutParams);
        } else {
            Toast.makeText(this, "-native ad 加载中--", Toast.LENGTH_SHORT).show();
        }
    }
}
