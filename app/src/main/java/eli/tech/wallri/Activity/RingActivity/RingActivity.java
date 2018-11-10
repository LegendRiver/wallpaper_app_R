package eli.tech.wallri.Activity.RingActivity;

import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eli.tech.wallri.Adapter.MyFragmentPageAdapter;
import eli.tech.wallri.DataBean.MessageEvent;
import eli.tech.wallri.Fragment.RingFragment.RingClassifyFragment;
import eli.tech.wallri.Fragment.RingFragment.RingHomePageFragment;
import eli.tech.wallri.Fragment.RingFragment.RingNewsFragment;
import eli.tech.wallri.R;
import eli.tech.wallri.System.Constant;
import eli.tech.wallri.view.CustomCoordinatorLayout;
import eli.tech.wallri.view.CustomViewpager;

/**
 * 这个类是Rings的主界面，其中包含了分类，热门，最新三个Fragment
 */
public class RingActivity extends AppCompatActivity implements View.OnClickListener {
    private View statusBarView;
    private ImageView mToobarImageRing;
    private Toolbar mToobarRing;
    private TabLayout mHomePageTapLayoutRing;
    private AppBarLayout mToolbarContainerRing;
    private CustomViewpager mHomePageViewPagerRing;
    private SimpleDraweeView mFabButtonRing;
    private CustomCoordinatorLayout mCoordinatorLayoutRing;

    //存入标题的集合
    List<String> mTabList = new ArrayList<>();
    //存入Fragment的集合
    List<Fragment> mFragmentList = new ArrayList<>();
    private DrawerLayout mRingDrawerLayout;
    private ImageView mRing_toobar_left;
    //  private FloatingActionButton mRing_to_wallpaper_but;
    private MediaPlayer mediaPlayer;
    private ImageView mRing_back;
    private RelativeLayout mRing_fabButton_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeBlue);
        setContentView(R.layout.activity_ring);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        initView();


    }

    private void initView() {
        //注册EventBus
        EventBus.getDefault().register(this);
        //创建mediaplayer对象
        mediaPlayer = new MediaPlayer();
        mToobarImageRing = (ImageView) findViewById(R.id.Ring_toobar_image);
        mToobarImageRing.setOnClickListener(this);
        mToobarRing = (Toolbar) findViewById(R.id.Ring_toobar);
        mHomePageTapLayoutRing = (TabLayout) findViewById(R.id.Ring_HomePageTapLayout);
        mToolbarContainerRing = (AppBarLayout) findViewById(R.id.Ring_ToolbarContainer);
        mHomePageViewPagerRing = (CustomViewpager) findViewById(R.id.Ring_HomePageViewPager);
        mHomePageViewPagerRing.setOffscreenPageLimit(3);
        mFabButtonRing = (SimpleDraweeView) findViewById(R.id.Ring_fabButton);
        mFabButtonRing.setImageURI("res://eli.tech.wallri/" + R.drawable.up_arrow);
        mRing_fabButton_background = findViewById(R.id.Ring_fabButton_Background);
        mRing_fabButton_background.getBackground().setAlpha(50);
        mRing_fabButton_background.setOnClickListener(this);
        mCoordinatorLayoutRing = (CustomCoordinatorLayout) findViewById(R.id.Ring_CoordinatorLayout);
        mRingDrawerLayout = findViewById(R.id.RingDrawerLayout);
        findViewById(R.id.Ring_title).setVisibility(View.GONE);
        mHomePageTapLayoutRing.setTabTextColors(0xFFAFAFAF, Color.WHITE);

        mRing_back = findViewById(R.id.Ring_back);
        mRing_back.setOnClickListener(this);
        //初始化数据
        initData();

    }

    //用方法给Fragment返回mediaplayer对象
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    //用方法给Fragment返回mediaplayer对象
    public CustomViewpager getViewpager() {
        return mHomePageViewPagerRing;
    }



    //用eventBus获取是哪个Fragment点击了置顶按钮
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {

        if (messageEvent.getMessage() == 301) {
            mRing_fabButton_background.setVisibility(View.GONE);

        } else if (messageEvent.getMessage() == 302) {
            mRing_fabButton_background.setVisibility(View.VISIBLE);

        }

    }

    /**
     * 初始化数据
     */
    private void initData() {
        //关闭Logger日志打印
        final Logger httpLogger = Logger.getLogger(HttpURLConnection.class.getName());
        httpLogger.setLevel(Level.OFF);
        //往集合中存入标题
        mTabList.add(this.getResources().getString(R.string.classify));
        mTabList.add(this.getResources().getString(R.string.hot));
        mTabList.add(this.getResources().getString(R.string.news));
        //创建Fragment对象
        RingClassifyFragment ringClassifyFragment = new RingClassifyFragment();
        RingHomePageFragment ringHomePageFragment = new RingHomePageFragment();
        RingNewsFragment ringNewsFragment = new RingNewsFragment();
        //把Fragment存入集合
        mFragmentList.add(ringClassifyFragment);
        mFragmentList.add(ringHomePageFragment);
        mFragmentList.add(ringNewsFragment);
        //创建Fragment适配器
        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        //给Fragment适配器添加内容
        adapter.addFragment(mFragmentList.get(0), mTabList.get(0));
        adapter.addFragment(mFragmentList.get(1), mTabList.get(1));
        adapter.addFragment(mFragmentList.get(2), mTabList.get(2));
        //设置Fragment适配器
        mHomePageViewPagerRing.setAdapter(adapter);
        //设置ViewPager初始化位置
        mHomePageViewPagerRing.setCurrentItem(1);
        //把Tablayout和Viewpager关联
        mHomePageTapLayoutRing.setupWithViewPager(mHomePageViewPagerRing);


    }


    //初始化状态栏
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

    //右下角返回最上面的浮动按钮
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击置顶按钮向fragment发送时间
            case R.id.Ring_fabButton_Background:
                int currentItem = mHomePageViewPagerRing.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        EventBus.getDefault().post(new MessageEvent(307));
                        break;
                    case 1:
                        EventBus.getDefault().post(new MessageEvent(308));
                        break;
                    case 2:
                        EventBus.getDefault().post(new MessageEvent(309));
                        break;
                }
                break;

            //回退
            case R.id.Ring_back:
                finish();

                break;


            //打开侧滑按钮
            case R.id.Ring_toobar_image:
                //设置旋转属性动画
                ObjectAnimator anim = ObjectAnimator.ofFloat(mToobarImageRing, "rotation", 90f, 180f);
                anim.setDuration(500);
                anim.start();
                // 点击按钮打开侧滑菜单
                if (!mRingDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mRingDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;

            default:
                break;
        }
    }

    //回退监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mRingDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mRingDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁方法把mediaplayer和eventbus释放掉
    @Override
    protected void onDestroy() {
        Constant.isOut = true;
        //释放mediaplayer资源
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer = null;
        } else {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
