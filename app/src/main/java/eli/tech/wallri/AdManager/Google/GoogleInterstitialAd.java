package eli.tech.wallri.AdManager.Google;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;

/**
 * Created by 小雷 on 2018/3/30.
 */

public class GoogleInterstitialAd  {
    private Context context;


    public GoogleInterstitialAd(Context context) {
        this.context = context;
        initAds();
    }

    public com.google.android.gms.ads.InterstitialAd initAds() {
        com.google.android.gms.ads.InterstitialAd mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3947341999148372/2399970620");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        return mInterstitialAd;

    }

}
