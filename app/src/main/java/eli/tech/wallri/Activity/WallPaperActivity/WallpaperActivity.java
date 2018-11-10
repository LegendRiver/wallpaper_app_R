package eli.tech.wallri.Activity.WallPaperActivity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import eli.tech.wallri.Activity.BaseActivity;
import eli.tech.wallri.Activity.DynamicWallpaperActivity.DynamicHomeActivity;
import eli.tech.wallri.Activity.RingActivity.RingActivity;
import eli.tech.wallri.Adapter.MyFragmentPageAdapter;
import eli.tech.wallri.Adapter.RecyclerViewItemDecoration;
import eli.tech.wallri.DataBean.NewsBean;
import eli.tech.wallri.Fragment.SlideFragment;
import eli.tech.wallri.Fragment.WallPaperFragment.WallpaperFragment;
import eli.tech.wallri.R;
import eli.tech.wallri.Utils.CleanMessageUtil;
import eli.tech.wallri.Utils.SystemUtil;
import eli.tech.wallri.Utils.ToastUtils;
import eli.tech.wallri.Utils.Utils;
import site.gemus.openingstartanimation.OpeningStartAnimation;
import site.gemus.openingstartanimation.RedYellowBlueDrawStrategy;

/**
 * Created by mac on 2018/3/30.
 */

public class WallpaperActivity extends BaseActivity implements View.OnClickListener {

    private String popularRequestURL = "";
    private String newestRequestURL = "";
    private String categoryRequestURL = "";
    private int offset = 30;
    //这个是通用的布局
    private Integer dynamic_popular_fragment = R.layout.dynamic_popular_fragment;
    //这个是通用布局的刷新 分页控件
    private Integer dynamicRefresh = R.id.dynamicRefresh;
    //这个是通用布局的控件id
    private Integer dynamic_popular_fragment_RecyclerView = R.id.dynamic_popular_fragment_RecyclerView;
    //这个是通用布局的item布局
    private Integer dynamic_feed = R.layout.dynamic_feed;
    //这个是通用布局的item控件id
    private Integer dynamic_image = R.id.dynamic_image;

    //这个是通用的显示下载量的控件
    private Integer mDownload_Number = R.id.Download_Number;
    //这个是通用的显示like数量的控件
    private Integer mLike_Heart = R.id.Like_Heart;

    private Integer Category_BackGround = R.id.Wallpaper_Category_BackGround;

    private Integer Category_Title = R.id.Wallpaper_Category_Title;


    private String feedType = "vertical";


    private List<NewsBean.DataBean> popularList = new ArrayList<>();
    private List<NewsBean.DataBean> newestList = new ArrayList<>();
    private List<NewsBean.DataBean> categoryList = new ArrayList<>();

    int hasMore = 1;
    //SD卡读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private UUID uuid;

    @Override
    public int intiLayout() {
        return R.layout.activity_dynamic_home;
    }

    @Override
    public void initData() {
        ScreenAnimation();
        verifyStoragePermissions(this, this);
        uuid = UUID.randomUUID();
        appid();
        this.processURL();
        this.initFragments();
        getmImageDynamicToolbar().setOnClickListener(this);
    }


    //开屏动画
    private void ScreenAnimation() {
        //图片太大了 给压缩一下
        Drawable drawable = getResources().getDrawable(R.drawable.applogo);
        Drawable drawable1 = Utils.zoomDrawable(drawable, 200, 200);
        OpeningStartAnimation openingStartAnimation = new OpeningStartAnimation.Builder(this)
                .setDrawStategy(new RedYellowBlueDrawStrategy())
                .setAppName("").setAppIcon(drawable1)
                .setAppStatement("Colorful your screen")
                .setAnimationInterval(3000)
                .setAnimationFinishTime(1000)
                .setColorOfAppStatement(R.drawable.shape_title)
                .create();

        openingStartAnimation.show(WallpaperActivity.this);
    }

    private void processURL() {
        String model = Build.MODEL;
        this.popularRequestURL = "http://wallpaper.ksmobile.com/v3/list/get?pid=1&lan=en_US&app_lan=en_US&net=1&aid=181f767dffd70569&brand=" + SystemUtil.getDeviceBrand() +
                "&model=" + model + "&osv=" + SystemUtil.getSystemVersion() + "&api_level=" + Build.VERSION.SDK_INT + "&appv=20600&mcc=460&mnc=01&vga=" + getScreen() + "&pos=102&count=30&offset=";

        this.newestRequestURL = "http://wallpaper.ksmobile.com/v3/list/get?pid=1&lan=en_US&app_lan=en_US&net=1&aid=181f767dffd70569&brand=" + SystemUtil.getDeviceBrand() +
                "&model=" + model + "&osv=" + SystemUtil.getSystemVersion() + "&api_level=" + Build.VERSION.SDK_INT + "&appv=20600&mcc=460&mnc=01&vga=" + getScreen() + "&pos=101&count=30&offset=";

        this.categoryRequestURL = "http://wallpaper.ksmobile.com/v2/category/get?pid=1&lan=en_US&app_lan=en_US&net=1&aid=181f767dffd70569&brand=" + SystemUtil.getDeviceBrand() +
                "&model=" + model + "&osv=" + SystemUtil.getSystemVersion() + "&api_level=" + Build.VERSION.SDK_INT + "&appv=20600&mcc=460&mnc=01&vga=" + getScreen() + "&pos=105&count=30&offset=0";
    }


    private void initFragments() {
        GridLayoutManager popularLayoutManager = new GridLayoutManager(WallpaperActivity.this, 2);
        RecyclerViewItemDecoration popularDecoration = new RecyclerViewItemDecoration(8);

        //创建ftagment对象
        WallpaperFragment popularFragment = new WallpaperFragment();
        popularFragment.setData(this,
                false,
                dynamic_popular_fragment
                , dynamic_popular_fragment_RecyclerView
                , dynamicRefresh
                , dynamic_feed
                , dynamic_image
                , mDownload_Number
                , mLike_Heart
                , 0
                , Category_BackGround
                , Category_Title
                , feedType
                , popularLayoutManager
                , "get",
                popularDecoration);
        popularFragment.SettingPagingData(popularRequestURL, "");
        popularFragment.setList(popularList);
        popularFragment.setOffset(offset);
        popularFragment.getMethod();
        Intent popularIntent = new Intent(WallpaperActivity.this, WallpaperDetailActivity.class);
        popularFragment.SetJumpTransferValue(popularIntent);

        GridLayoutManager newestLayoutManager = new GridLayoutManager(WallpaperActivity.this, 2);
        RecyclerViewItemDecoration newestDecoration = new RecyclerViewItemDecoration(8);

        //创建fragment对象
        WallpaperFragment newestFragment = new WallpaperFragment();
        newestFragment.setData(this,
                false,
                dynamic_popular_fragment
                , dynamic_popular_fragment_RecyclerView
                , dynamicRefresh
                , dynamic_feed
                , dynamic_image
                , mDownload_Number
                , mLike_Heart
                , 0
                , Category_BackGround
                , Category_Title
                , feedType
                , newestLayoutManager
                , "get",
                newestDecoration);
        newestFragment.SettingPagingData(newestRequestURL, "");
        newestFragment.setList(newestList);
        newestFragment.setOffset(offset);
        newestFragment.getMethod();
        Intent newestIntent = new Intent(WallpaperActivity.this, WallpaperDetailActivity.class);
        newestFragment.SetJumpTransferValue(newestIntent);

        LinearLayoutManager categoriesLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewItemDecoration categoryDecoration = new RecyclerViewItemDecoration(8);

        //创建fragment对象
        WallpaperFragment categoryFragment = new WallpaperFragment();
        categoryFragment.setData(this,
                true,
                dynamic_popular_fragment
                , dynamic_popular_fragment_RecyclerView
                , dynamicRefresh
                , dynamic_feed
                , dynamic_image
                , mDownload_Number
                , mLike_Heart
                , 0
                , Category_BackGround
                , Category_Title
                , feedType
                , categoriesLinearLayoutManager
                , "get",
                categoryDecoration);
        categoryFragment.SettingPagingData(categoryRequestURL, "");
        categoryFragment.setList(categoryList);
        categoryFragment.setDataType("category");
        categoryFragment.getMethod();
        Intent categoryIntent = new Intent(WallpaperActivity.this, WallpaperCategoryActivity.class);
        categoryFragment.SetJumpTransferValue(categoryIntent);


        //创建viewpager适配器
        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        adapter.addFragment(categoryFragment, this.getResources().getString(R.string.classify));
        adapter.addFragment(popularFragment, this.getResources().getString(R.string.hot));
        adapter.addFragment(newestFragment, this.getResources().getString(R.string.news));
        //设置viewpager适配器
        getmDynamicHomePageViewPager().setAdapter(adapter);
        getmDynamicHomePageViewPager().setOffscreenPageLimit(4);
        //关联viewpager和tablayout
        getmDynamicHomePageTapLayout().setupWithViewPager(getmDynamicHomePageViewPager());
    }

    //获得屏幕宽高
    private String getScreen() {
        Display mDisplay = getWindowManager().getDefaultDisplay();
        int width = mDisplay.getWidth();
        int height = mDisplay.getHeight();
        return width + "x" + height;
    }


    //第一次进入时存入appid（uuid）
    private void appid() {
        Utils.writeDataToSdCard(Environment.getExternalStorageDirectory().getPath() + "/.wallri", uuid.toString());
    }


    //重写回退方法设置退出提示
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (getmLayoutDrawer().isDrawerOpen(GravityCompat.START)) {
            getmLayoutDrawer().closeDrawer(GravityCompat.START);
            return true;
        } else {
            final AlertDialog alert = new AlertDialog.Builder(WallpaperActivity.this).setTitle(R.string.Prompt)
                    .setMessage(R.string.back)
                    .setPositiveButton(R.string.backtrue, new DialogInterface.OnClickListener() {//设置确定按钮

                        @Override//处理确定按钮点击事件

                        public void onClick(DialogInterface dialog, int which) {
                            //db.delete("flag", null, null);
                            finish();
                        }

                    })
                    .setNegativeButton(R.string.backfalse, null)
                    .create();
            alert.show();
            return super.onKeyDown(keyCode, event);
        }


    }


    //动态权限申请结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    //动态权限结果监听
    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission Granted
                } else {
                    ToastUtils.showToast(WallpaperActivity.this, "Please open the permissions！");

                    finish();
                }
            } else {


            }

        }
    }

    //申请读写SDCARD的权限
    public static void verifyStoragePermissions(Context context, Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Dynamictoobar_image:
                //打开侧拉菜单
                openTheSidePullMenu();
                //设置旋转属性动画
                break;

        }
    }

    //打开侧拉菜单
    private void openTheSidePullMenu() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(getmImageDynamicToolbar(), "rotation", 90f, 180f);
        anim.setDuration(500);
        anim.start();
        // 点击按钮打开侧滑菜单
        if (!getmLayoutDrawer().isDrawerOpen(GravityCompat.END)) {
            getmLayoutDrawer().openDrawer(GravityCompat.START);
        }
    }


    //初始化侧拉菜单
    @Override
    protected void SettingTheSidePullMenu() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WallpaperActivity.this, LinearLayoutManager.VERTICAL, false);
        SlideFragment dynamicSlideFragment = new SlideFragment();
        dynamicSlideFragment.setData(WallpaperActivity.this
                , false
                , R.layout.navifragment
                , 0
                , R.layout.navifragmentitem
                , 0
                , 0
                , 0
                , 0
                , null
                , 0
                , 0
                , null
                , linearLayoutManager
                , null
                , null);
        List<Integer> SlideTitleList = new ArrayList<Integer>();
        SlideTitleList.add(R.string.DynamicWallpaper);
        SlideTitleList.add(R.string.ring);
        SlideTitleList.add(R.string.Mark);//给应用打分Mark
        SlideTitleList.add(R.string.Collection);  //收藏
        SlideTitleList.add(R.string.ScavengingCaching);//缓存8
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.videw);
        imageList.add(R.drawable.ringleft);
        imageList.add(R.drawable.star);
        imageList.add(R.drawable.like_gray_save);
        imageList.add(R.drawable.clear_cache);
        dynamicSlideFragment.setSlideList(SlideTitleList, imageList);
        getSupportFragmentManager().beginTransaction().add(R.id.homeFragmentfragment, dynamicSlideFragment).commit();
        dynamicSlideFragment.setOnItemClickListener(new SlideFragment.onItemClickListener() {
            @Override
            public void onItemclic(View view, int data) {
                switch (data) {
                    case 0:
                        startActivity(new Intent(WallpaperActivity.this, DynamicHomeActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(WallpaperActivity.this, RingActivity.class));
                        break;
                    //给应用打分Mark
                    case 2:
                        final String appPackageName = WallpaperActivity.this.getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        break;
                    case 3:
                        startActivity(new Intent(WallpaperActivity.this, WallPaperCollection.class));
                        break;
                    //缓存
                    case 4:
                        CleanMessageUtil.clearAllCache(WallpaperActivity.this.getApplicationContext());
                        ToastUtils.showToast(WallpaperActivity.this, "Success");
                        break;

                }
            }
        });

    }
}
