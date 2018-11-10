package eli.tech.wallri.AdManager.Google;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by 小雷 on 2018/2/28.
 */

public class GoogleAdManager {

    //adView的控制 0为广告关闭 1为广告打开
    public static int MyAdvertisingControl = 0;


    //这个是壁纸大图页面的Banner广告
    public static void wallpaperPageBannerAds(Context context) {
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3947341999148372/7728797859");

    }


    public static InterstitialAd wallpaperLoadingPageAds(Context context) {
        InterstitialAd mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3947341999148372/2399970620");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        return mInterstitialAd;
    }




}
