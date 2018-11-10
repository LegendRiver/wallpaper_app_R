package eli.tech.wallri.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.facebook.ads.NativeAd;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import eli.tech.wallri.AdManager.Facebook.FacebookAdManager;
import eli.tech.wallri.DataBean.MessageEvent;
import eli.tech.wallri.R;

/**
 * 这个类是壁纸 铃声的loading界面（可以替换成广告）
 */
public class LoadingActivity extends AppCompatActivity {

    private String setUpPictureUrl;
    private SimpleDraweeView mLoadingGif;
    private NativeAd nativeAd;
    private RelativeLayout nativeAdContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        //隐藏Actionbar
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();
        DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
//加载drawable里的一张gif图
                .setUri(Uri.parse("res://" + getPackageName() + "/" + R.drawable.loading))//设置uri
                .build();
//设置Controller
        mLoadingGif.setController(mDraweeController);



    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage() == 0) {
            startActivity(new Intent(LoadingActivity.this, FinishSuccessActivity.class), ActivityOptions.makeSceneTransitionAnimation(LoadingActivity.this).toBundle());
            finish();

        } else if (messageEvent.getMessage() == 1) {
            System.out.println("走这里了");
            startActivity(new Intent(LoadingActivity.this, FinishFailActivity.class), ActivityOptions.makeSceneTransitionAnimation(LoadingActivity.this).toBundle());
            finish();
        }


    }

    //初始化
    private void initView() {


        EventBus.getDefault().register(this);
        setUpPictureUrl = getIntent().getStringExtra("SetUpPicture");

        mLoadingGif = findViewById(R.id.LoadingGif);
        nativeAdContainer = findViewById(R.id.WallPerLoadingAdsRela);
        Display display = getWindowManager().getDefaultDisplay();
        int getWidth = display.getWidth();
        FacebookAdManager.showLoadingNativeAd(this,FacebookAdManager.WallpaperLoading_04,nativeAdContainer,getWidth);

    }

    //eventBus解除注册
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }




}
