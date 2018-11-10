package eli.tech.wallri.AdManager.Facebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.R;
import eli.tech.wallri.Utils.ToastUtils;

/**
 * Created by 小雷 on 2018/3/1.
 */

public class FacebookAdManager {

    public static String WallpaperPopularFeed_01 = "192825138120325_209630043106501";
    public static String WallpaperNewFeed_02 = "192825138120325_209629423106563";
    public static String WallpaperCategoryFeed_03 = "192825138120325_209637036439135";
    public static String WallpaperLoading_04 = "192825138120325_207010090035163";
    public static String WallpaperSuccess_05 = "192825138120325_209266623142843";
    public static String RingsPopularFeed_06 = "192825138120325_209626703106835";
    public static String RingsNewFeed_07 = "192825138120325_207516769984495";
    public static String RingCategoryRating_08 = "192825138120325_209726346430204";
    public static String RingCategoryPopular_09 = "192825138120325_209726596430179";
    public static String RingCategoryNew_10 = "192825138120325_209730993096406";
    public static String RingLoading_11 = "192825138120325_207084420027730";
    public static String RingSuccess_12 = "192825138120325_207525406650298";
    public static String WallpaperDetailPage_13 = "192825138120325_210859732983532";
    public static String RingDetailPage_14 = "192825138120325_210859842983521";


    public static NativeAdsManager setWallPaperFeedAds(Context context, String id, int adsCount) {

        final NativeAdsManager nativeAd = new NativeAdsManager(context, id, adsCount);

        nativeAd.loadAds();

        return nativeAd;

    }

    public static void showLoadingNativeAd(final Context context, String id, final ViewGroup viewGroup, final int width) {
        final NativeAd LoadingNativeAd = new NativeAd(context, id);
        LoadingNativeAd.setAdListener(new AdListener() {

            private LinearLayout mWallPaper_loading_feed_banner_native_linearLayout;

            @Override
            public void onError(Ad ad, AdError error) {
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
                if (LoadingNativeAd != null) {
                    LoadingNativeAd.unregisterView();
                }

                // Ad clicked callback
                LayoutInflater inflater = LayoutInflater.from(context);
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.loading_native_ad, viewGroup, false);
                viewGroup.addView(adView);


                mWallPaper_loading_feed_banner_native_linearLayout = adView.findViewById(R.id.WallPaper_Loading_Feed_Banner_Native_LinearLayout);
                mWallPaper_loading_feed_banner_native_linearLayout.setVisibility(View.VISIBLE);
                MediaView mWallpaper_Feed_Banner_Native_Image = (MediaView) adView.findViewById(R.id.WallPaper_Loading_Feed_Banner_Native_Image);
                mWallpaper_Feed_Banner_Native_Image.getLayoutParams().height = width / 2;
                TextView mWallpaper_Feed_Banner_Native_Title = (TextView) adView.findViewById(R.id.WallPaper_Loading_Feed_Banner_Native_Title);
                TextView mWallpaper_Feed_Banner_Native_Text = (TextView) adView.findViewById(R.id.WallPaper_Loading_Feed_Banner_Native_Text);
                TextView mWallpaper_Feed_Banner_Native_Button = (TextView) adView.findViewById(R.id
                        .WallPaper_Loading_Feed_Banner_Native_Button);
                LinearLayout mWallpaper_Feed_Banner_Nativ_Container = adView.findViewById(R.id.WallPaper_Loading_Feed_Banner_Nativ_Container);
                RelativeLayout mWallPaper_Feed_Banner_Native_Rela = adView.findViewById(R.id.WallPaper_Loading_Feed_Banner_Native_Rela);
                SimpleDraweeView mWallPaper_Feed_Banner_Native_Icon = adView.findViewById(R.id.WallPaper_Loading_Feed_Banner_Native_Icon);

                try {
                    mWallpaper_Feed_Banner_Native_Image.setNativeAd(LoadingNativeAd);
                    mWallpaper_Feed_Banner_Native_Title.setText(LoadingNativeAd.getAdTitle());
                    mWallpaper_Feed_Banner_Native_Text.setText(LoadingNativeAd.getAdBody());
                    if (LoadingNativeAd.getAdCallToAction() != null) {
                        mWallpaper_Feed_Banner_Native_Button.setText(LoadingNativeAd.getAdCallToAction());
                        mWallpaper_Feed_Banner_Native_Button.setVisibility(View.VISIBLE);
                    }

                    mWallPaper_Feed_Banner_Native_Icon.setImageURI(LoadingNativeAd.getAdIcon().getUrl());
                    AdChoicesView adChoicesView = new AdChoicesView(context, LoadingNativeAd, true);
                    mWallpaper_Feed_Banner_Nativ_Container.addView(adChoicesView);

                    List<View> clickableViews = new ArrayList<>();
                    clickableViews.add(mWallpaper_Feed_Banner_Native_Title);
                    clickableViews.add(mWallpaper_Feed_Banner_Native_Text);
                    clickableViews.add(mWallPaper_Feed_Banner_Native_Icon);
                    clickableViews.add(mWallpaper_Feed_Banner_Native_Button);
                    LoadingNativeAd.registerViewForInteraction(viewGroup, clickableViews);
                } catch (Exception e) {

                    mWallPaper_loading_feed_banner_native_linearLayout.setVisibility(View.GONE);
                }


            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });

        // Request an ad
        LoadingNativeAd.loadAd(NativeAd.MediaCacheFlag.ALL);

    }

    public static void showSuccessNativeAd(final Context context, String id, final ViewGroup viewGroup, final int width) {
        final NativeAd successNativeAd = new NativeAd(context, id);
        successNativeAd.setAdListener(new AdListener() {

            private LinearLayout mWallPaper_success_banner_native_linearLayout;

            @Override
            public void onError(Ad ad, AdError adError) {
                ToastUtils.showToast(context, adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {

                if (successNativeAd != null) {
                    successNativeAd.unregisterView();
                }
                List<View> clickableViews = new ArrayList<>();
                // Ad clicked callback
                LayoutInflater inflater = LayoutInflater.from(context);
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.success_native_ad, viewGroup, false);
                viewGroup.addView(adView);


                mWallPaper_success_banner_native_linearLayout = adView.findViewById(R.id.WallPaper_Success_Banner_Native_LinearLayout);
                mWallPaper_success_banner_native_linearLayout.setVisibility(View.VISIBLE);


                MediaView mWallpaper_Success_Native_Image = (MediaView) adView.findViewById(R.id.WallPaper_Success_Banner_Native_Image);


                mWallpaper_Success_Native_Image.getLayoutParams().height = width / 2;
                TextView mWallpaper_Success_Native_Title = (TextView) adView.findViewById(R.id.WallPaper_Success_Banner_Native_Title);
                TextView mWallpaper_Success_Native_Text = (TextView) adView.findViewById(R.id.WallPaper_Success_Banner_Native_Text);
                TextView mWallpaper_Success_Native_Button = (TextView) adView.findViewById(R.id
                        .WallPaper_Success_Banner_Native_Button);
                LinearLayout mWallpaper_Success_Nativ_Container = adView.findViewById(R.id.WallPaper_Success_Banner_Nativ_Container);
                RelativeLayout mWallPaper_Success_Native_Rela = adView.findViewById(R.id.WallPaper_Success_Banner_Native_Rela);
                SimpleDraweeView mWallPaper_Success_Native_Icon = adView.findViewById(R.id.WallPaper_Success_Banner_Native_Icon);


                try {


                    if (successNativeAd != null) {
                        mWallpaper_Success_Native_Image.setNativeAd(successNativeAd);
                        mWallpaper_Success_Native_Title.setText(successNativeAd.getAdTitle());
                        mWallpaper_Success_Native_Text.setText(successNativeAd.getAdBody());
                    }

                    if (successNativeAd.getAdCallToAction() != null) {
                        mWallpaper_Success_Native_Button.setText(successNativeAd.getAdCallToAction());
                        mWallpaper_Success_Native_Button.setVisibility(View.VISIBLE);
                        clickableViews.add(mWallpaper_Success_Native_Button);
                    }

                    mWallPaper_Success_Native_Icon.setImageURI(successNativeAd.getAdIcon().getUrl());
                    AdChoicesView adChoicesView = new AdChoicesView(context, successNativeAd, true);
                    mWallpaper_Success_Nativ_Container.addView(adChoicesView);


                    clickableViews.add(mWallpaper_Success_Native_Title);
                    clickableViews.add(mWallPaper_Success_Native_Icon);
                    clickableViews.add(mWallpaper_Success_Native_Text);
                    successNativeAd.registerViewForInteraction(viewGroup, clickableViews);
                } catch (Exception e) {
                    mWallPaper_success_banner_native_linearLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        successNativeAd.loadAd(NativeAd.MediaCacheFlag.ALL);
    }

}
