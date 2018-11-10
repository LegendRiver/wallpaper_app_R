package eli.tech.wallri.AdManager;

import android.content.Context;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import eli.tech.wallri.AdManager.Facebook.FacebookAdManager;
import eli.tech.wallri.Utils.ToastUtils;

import static eli.tech.wallri.System.Constant.AD_UNIT_ID;

/**
 * 激励视屏的广告对象类
 * Created by 小雷 on 2018/3/29.
 */

public class IncentiveVideoAd {

    private Context context;
    private RewardedVideoAd rewardedVideoAdInstance;
    private boolean isLookAds;
    private InterstitialAd interstitialAd;

    public IncentiveVideoAd(Context context) {
        this.context = context;
        initAds();
    }

    private void initAds() {
        rewardedVideoAdInstance = MobileAds.getRewardedVideoAdInstance(context);
        interstitialAd = new InterstitialAd(context, FacebookAdManager.WallpaperSuccess_05);
        setFaceBookAdaListener();
        setGoogleAdsLinstener();
    }

    private void setGoogleAdsLinstener() {
        rewardedVideoAdInstance.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                if (isLoadingAdsListener != null) {
                    isLoadingAdsListener.getIsLoadAdsLintnener();
                }

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                if (isLookAds == true) {
                    isLookAds = false;
                    if (watchingAdsListener != null) {
                        watchingAdsListener.getWatchingAdsListener();
                    }
                } else {
                    loadRewardedVideoAd();
                }
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                isLookAds = true;
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {


            }
        });
    }

    private void setFaceBookAdaListener() {
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial displayed callback


            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                if (watchingAdsListener != null) {
                    watchingAdsListener.getWatchingAdsListener();
                }     // Interstitial dismissed callback
                interstitialAd.destroy();
            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Show the ad when it's done loading.

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });
    }

    //加载广告
    public void loadRewardedVideoAd() {
        if (!rewardedVideoAdInstance.isLoaded()) {
            rewardedVideoAdInstance.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        } else if (!interstitialAd.isAdLoaded()) {
            interstitialAd.loadAd();
        };
    }

    public void showRewardedVideo() {

        if (rewardedVideoAdInstance.isLoaded()) {
            rewardedVideoAdInstance.show();
        } else if (interstitialAd.isAdLoaded())  {
            interstitialAd.show();
        }else{
            ToastUtils.showToast(context,"No advertising!");
        }

    }


    public watchingAdsListener watchingAdsListener;
    public isLoadingAdsListener isLoadingAdsListener;

    public void setWatchingAdsListener(watchingAdsListener watchingAdListener) {
        watchingAdsListener = watchingAdListener;
    }

    public void isLoadingAdsListener(isLoadingAdsListener LoadingAdsListener) {
        isLoadingAdsListener = LoadingAdsListener;
    }

    public interface isLoadingAdsListener {
        void getIsLoadAdsLintnener();
    }

    public interface watchingAdsListener {
        void getWatchingAdsListener();

    }

}
