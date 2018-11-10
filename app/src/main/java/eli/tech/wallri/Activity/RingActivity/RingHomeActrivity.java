package eli.tech.wallri.Activity.RingActivity;

import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

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
import eli.tech.wallri.Fragment.RingFragment.RingClassifyContentHomePageFragment;
import eli.tech.wallri.Fragment.RingFragment.RingClassifyContentNewsFragment;
import eli.tech.wallri.Fragment.RingFragment.RingClassifyContentRatingFragment;
import eli.tech.wallri.R;
import eli.tech.wallri.System.Constant;
import eli.tech.wallri.Utils.Utils;
import eli.tech.wallri.view.CustomCoordinatorLayout;
import eli.tech.wallri.view.CustomViewpager;

/**
 * 这个类是铃声详情界面的类，内容有打分，最新，和热门三个Fragment
 */
public class RingHomeActrivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mToobarImageRing;
    private Toolbar mToobarRing;
    private TabLayout mHomePageTapLayoutRing;
    private AppBarLayout mToolbarContainerRing;
    private CustomViewpager mHomePageViewPagerRing;
    private SimpleDraweeView mFabButtonRing;
    private CustomCoordinatorLayout mCoordinatorLayoutRing;
    private DrawerLayout mRingDrawerLayout;
    private View statusBarView;
    //存入标题的集合
    List<String> mTabList = new ArrayList<>();
    //存入Fragment的集合
    List<Fragment> mFragmentList = new ArrayList<>();
    private String category_name;
    private MediaPlayer mediaPlayer;
    //private FloatingActionButton mFloatingActionButton;
    private TextView mRing_title;
    private ImageView mRing_home_back;
    private RelativeLayout mRing_home_fabButton;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeBlue);
        setContentView(R.layout.activity_ring_home);

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

    //用eventBus获取改变语言和recyclerView滑动的位置
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {

        if (messageEvent.getMessage() == 401) {
            mRing_home_fabButton.setVisibility(View.GONE);

        } else if (messageEvent.getMessage() == 402) {
            mRing_home_fabButton.setVisibility(View.VISIBLE);

        }

    }

    //使用方法往fragment里暴漏类名
    public String getCategory_name() {
        return category_name;
    }

    //使用方法往fragemnt里面传入meidaplayer对象
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    //初始化数据
    private void initView() {
        loadingDialog = Utils.setLoadingDialog(this);
        //注册eventbus
        EventBus.getDefault().register(this);
        //创建mediaplayer对象
        mediaPlayer = new MediaPlayer();
        //获得铃声主界面的传值
        category_name = getIntent().getStringExtra("Category_name");
        mToobarImageRing = (ImageView) findViewById(R.id.Ring_toobar_image);
        mToobarImageRing.setOnClickListener(this);
        mToobarRing = (Toolbar) findViewById(R.id.Ring_toobar);
        mHomePageTapLayoutRing = (TabLayout) findViewById(R.id.Ring_HomePageTapLayout);
        mToolbarContainerRing = (AppBarLayout) findViewById(R.id.Ring_ToolbarContainer);
        mHomePageViewPagerRing = (CustomViewpager) findViewById(R.id.Ring_HomePageViewPager);
        mRing_title = findViewById(R.id.Ring_title);
        mRing_title.setText(category_name);
        findViewById(R.id.RingLogo).setVisibility(View.GONE);
        mHomePageViewPagerRing.setOffscreenPageLimit(3);
        mFabButtonRing = (SimpleDraweeView) findViewById(R.id.Ring_fabButton);
        mFabButtonRing.setImageURI("res://eli.tech.wallri/" + R.drawable.up_arrow);
        mRing_home_fabButton = findViewById(R.id.Ring_Home_fabButton);
        mRing_home_fabButton.getBackground().setAlpha(50);
        mRing_home_fabButton.setOnClickListener(this);
        mCoordinatorLayoutRing = (CustomCoordinatorLayout) findViewById(R.id.Ring_CoordinatorLayout);
        mRingDrawerLayout = (DrawerLayout) findViewById(R.id.RingDrawerLayout);
        mRing_home_back = findViewById(R.id.Ring_Home_back);
        mRing_home_back.setOnClickListener(this);
        //侧拉点击事件
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        final Logger httpLogger = Logger.getLogger(HttpURLConnection.class.getName());
        httpLogger.setLevel(Level.OFF);
        //往集合中存入标题
        mTabList.add(this.getResources().getString(R.string.Rating));
        mTabList.add(this.getResources().getString(R.string.hot));
        mTabList.add(this.getResources().getString(R.string.news));
        //创建Fragmen对象
        RingClassifyContentRatingFragment ringClassifyContentRatingFragment = new RingClassifyContentRatingFragment();
        RingClassifyContentHomePageFragment ringClassifyContentHomePageFragment = new RingClassifyContentHomePageFragment();
        RingClassifyContentNewsFragment ringClassifyContentNewsFragment = new RingClassifyContentNewsFragment();
        //把fragment对象存入集合
        mFragmentList.add(ringClassifyContentRatingFragment);
        mFragmentList.add(ringClassifyContentHomePageFragment);
        mFragmentList.add(ringClassifyContentNewsFragment);
        //创建fragment适配器
        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        //往适配器里传入fragment的对象和标题
        adapter.addFragment(mFragmentList.get(0), mTabList.get(0));
        adapter.addFragment(mFragmentList.get(1), mTabList.get(1));
        adapter.addFragment(mFragmentList.get(2), mTabList.get(2));
        //设置适配器
        mHomePageViewPagerRing.setAdapter(adapter);
        //设置当前位置
        mHomePageViewPagerRing.setCurrentItem(1);
        //tablayout和viewpager关联
        mHomePageTapLayoutRing.setupWithViewPager(mHomePageViewPagerRing);
    }

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

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击置顶按钮回退发送Eventbus事件
            case R.id.Ring_Home_fabButton:
                int currentItem = mHomePageViewPagerRing.getCurrentItem();

                switch (currentItem) {
                    case 0:
                        EventBus.getDefault().post(new MessageEvent(407));
                        break;
                    case 1:
                        EventBus.getDefault().post(new MessageEvent(408));
                        break;
                    case 2:
                        EventBus.getDefault().post(new MessageEvent(409));
                        break;


                }

                break;

            //打开侧拉菜单
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

            case R.id.Ring_Home_back:
                finish();
                break;
            default:
                break;
        }
    }

    //在销毁方法里吧mediaplayer和eventbus对象置为空
    @Override
    protected void onDestroy() {
        Constant.isOut=true;
        //释放mediaplayer资源
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
