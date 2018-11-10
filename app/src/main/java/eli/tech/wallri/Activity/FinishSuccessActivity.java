package eli.tech.wallri.Activity;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.drawee.view.SimpleDraweeView;

import eli.tech.wallri.AdManager.Facebook.FacebookAdManager;
import eli.tech.wallri.R;

/**
 * 这个类是成功的页面
 */
public class FinishSuccessActivity extends AppCompatActivity {

    private TextView mFinishActivityTextView;
    private RelativeLayout mFinishActivityBackImage;
    private Typeface tf;
    private SimpleDraweeView mFinishImage;
    private Button mFinish_button;
    private TextView mSigntext;
    private RelativeLayout mWallPaper_success_ad_rela;
    private RelativeLayout finishRela;
    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        //隐藏ActionBar
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        //设置回退
        mFinishActivityBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置回退
        mFinish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }


    //初始化
    private void initView() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Explode().setDuration(500));
            getWindow().setExitTransition(new Explode().setDuration(500));
        }

        //设置字体
        //得到AssetManager
        AssetManager mgr = getAssets();
        //根据路径得到Typeface
        tf = Typeface.createFromAsset(mgr, "fonts/roboto_regular.ttf");

        mFinishActivityTextView = findViewById(R.id.FinishActivityTextView);
        mFinishActivityTextView.setTypeface(tf);
        mFinishActivityBackImage = findViewById(R.id.FinishActivityBackImage);
        mFinishActivityBackImage.getBackground().setAlpha(50);
        mFinishImage = findViewById(R.id.FinishImage);
        mFinish_button = findViewById(R.id.Finish_button);
        mFinish_button.setTypeface(tf);
        mSigntext = findViewById(R.id.signtext);
        mWallPaper_success_ad_rela = findViewById(R.id.WallPaper_Success_Ad_Rela);
        finishRela = findViewById(R.id.finishRela);
        mSigntext.setTypeface(tf);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.finish_image);
        mFinishImage.startAnimation(animation);
        mFinishImage.setImageURI("res://com.example.addemo/" + R.drawable.finish_yes);




        interstitialAd = new InterstitialAd(this, FacebookAdManager.WallpaperSuccess_05);

        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial displayed callback


            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                finish();
                interstitialAd.destroy();
            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Show the ad when it's done loading.
                interstitialAd.show();
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

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
