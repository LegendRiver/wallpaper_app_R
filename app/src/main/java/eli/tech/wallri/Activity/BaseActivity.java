package eli.tech.wallri.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import eli.tech.wallri.R;
import eli.tech.wallri.view.CustomCoordinatorLayout;
import eli.tech.wallri.view.CustomViewpager;

/**
 * Created by 小雷 on 2018/3/20.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ImageView mImageDynamicToolbar;
    private SimpleDraweeView mToWallpaperImage;
    private Toolbar mDynamicToolbar;
    private TabLayout mDynamicHomePageTapLayout;
    private AppBarLayout mDynamicToolbarContainer;
    private CustomViewpager mDynamicHomePageViewPager;
    private SimpleDraweeView mDynamicFabButton;
    private RelativeLayout mDynamicFabButtonBackground;
    private CustomCoordinatorLayout mDynamicCoordinatorLayout;
    private DrawerLayout mLayoutDrawer;
    private View statusBarView;

    private RelativeLayout mHomeFragmentfragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initBar();

        //设置布局
        setContentView(intiLayout());
        //初始化控件
        initView();
        //设置数据
        initData();
        //侧拉菜单
        SettingTheSidePullMenu();
        leftButtonClickEvent();
        rightButtonClickEvent();
    }


    protected void initBar() {
        //延时加载数据,设置和toobar一样颜色的渐变栏
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (isStatusBar()) {
                    initStatusBar();
                    getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            initStatusBar();
                        }
                    });
                }


                return false;
            }
        });
    }

    //右侧按钮的点击事件
    protected void rightButtonClickEvent() {
    }

    //左侧按钮的点击事件
    protected void leftButtonClickEvent() {

    }

    //设置侧拉菜单
    protected void SettingTheSidePullMenu() {

    }

    protected void initView() {

        mImageDynamicToolbar = (ImageView) findViewById(R.id.Dynamictoobar_image);
        mToWallpaperImage = (SimpleDraweeView) findViewById(R.id.toWallpaperImage);
        mDynamicToolbar = (Toolbar) findViewById(R.id.Dynamictoobar);
        mDynamicHomePageTapLayout = (TabLayout) findViewById(R.id.DynamicHomePageTapLayout);
        mDynamicToolbarContainer = (AppBarLayout) findViewById(R.id.DynamicToolbarContainer);
        mDynamicHomePageViewPager = (CustomViewpager) findViewById(R.id.DynamicHomePageViewPager);
        mDynamicFabButton = (SimpleDraweeView) findViewById(R.id.DynamicfabButton);
        mDynamicFabButtonBackground = (RelativeLayout) findViewById(R.id.DynamicfabButtonBackground);
        mDynamicCoordinatorLayout = (CustomCoordinatorLayout) findViewById(R.id.DynamicCoordinatorLayout);
        mLayoutDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mHomeFragmentfragment = findViewById(R.id.homeFragmentfragment);
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    public ImageView getmImageDynamicToolbar() {
        return mImageDynamicToolbar;
    }

    public void setmImageDynamicToolbar(ImageView mImageDynamicToolbar) {
        this.mImageDynamicToolbar = mImageDynamicToolbar;
    }

    public SimpleDraweeView getmToWallpaperImage() {
        return mToWallpaperImage;
    }

    public void setmToWallpaperImage(SimpleDraweeView mToWallpaperImage) {
        this.mToWallpaperImage = mToWallpaperImage;
    }

    public Toolbar getmDynamicToolbar() {
        return mDynamicToolbar;
    }

    public void setmDynamicToolbar(Toolbar mDynamicToolbar) {
        this.mDynamicToolbar = mDynamicToolbar;
    }

    public TabLayout getmDynamicHomePageTapLayout() {
        return mDynamicHomePageTapLayout;
    }

    public void setmDynamicHomePageTapLayout(TabLayout mDynamicHomePageTapLayout) {
        this.mDynamicHomePageTapLayout = mDynamicHomePageTapLayout;
    }

    public AppBarLayout getmDynamicToolbarContainer() {
        return mDynamicToolbarContainer;
    }

    public void setmDynamicToolbarContainer(AppBarLayout mDynamicToolbarContainer) {
        this.mDynamicToolbarContainer = mDynamicToolbarContainer;
    }

    public CustomViewpager getmDynamicHomePageViewPager() {
        return mDynamicHomePageViewPager;
    }

    public void setmDynamicHomePageViewPager(CustomViewpager mDynamicHomePageViewPager) {
        this.mDynamicHomePageViewPager = mDynamicHomePageViewPager;
    }

    public SimpleDraweeView getmDynamicFabButton() {
        return mDynamicFabButton;
    }

    public void setmDynamicFabButton(SimpleDraweeView mDynamicFabButton) {
        this.mDynamicFabButton = mDynamicFabButton;
    }

    public RelativeLayout getmDynamicFabButtonBackground() {
        return mDynamicFabButtonBackground;
    }

    public void setmDynamicFabButtonBackground(RelativeLayout mDynamicFabButtonBackground) {
        this.mDynamicFabButtonBackground = mDynamicFabButtonBackground;
    }

    public CustomCoordinatorLayout getmDynamicCoordinatorLayout() {
        return mDynamicCoordinatorLayout;
    }

    public void setmDynamicCoordinatorLayout(CustomCoordinatorLayout mDynamicCoordinatorLayout) {
        this.mDynamicCoordinatorLayout = mDynamicCoordinatorLayout;
    }

    public DrawerLayout getmLayoutDrawer() {
        return mLayoutDrawer;
    }

    public void setmLayoutDrawer(DrawerLayout mLayoutDrawer) {
        this.mLayoutDrawer = mLayoutDrawer;
    }


    public RelativeLayout getmHomeFragmentfragment() {
        return mHomeFragmentfragment;
    }

    public void setmHomeFragmentfragment(RelativeLayout mHomeFragmentfragment) {
        this.mHomeFragmentfragment = mHomeFragmentfragment;
    }

    /**
     * 设置数据
     */
    public abstract void initData();

    /**
     * 初始化状态栏
     */
    private void initStatusBar() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.shape_title);
        }
    }


    protected boolean isStatusBar() {
        return true;
    }

    //获得屏幕分辨率
    protected String getSreeen() {
        Display mDisplay = this.getWindowManager().getDefaultDisplay();
        int width = mDisplay.getWidth();
        int height = mDisplay.getHeight();
        return width + "x" + height;
    }


}
