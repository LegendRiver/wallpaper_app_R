package eli.tech.wallri.Activity.DynamicWallpaperActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Activity.BaseActivity;
import eli.tech.wallri.Adapter.MyFragmentPageAdapter;
import eli.tech.wallri.Adapter.RecyclerViewItemDecoration;
import eli.tech.wallri.DataBean.DynamicDataBean;
import eli.tech.wallri.Fragment.DynamicWallpaperFragment.DynamicFragment;
import eli.tech.wallri.Fragment.SlideFragment;
import eli.tech.wallri.R;
import eli.tech.wallri.Utils.CleanMessageUtil;
import eli.tech.wallri.Utils.OKhttpManager;
import eli.tech.wallri.Utils.ToastUtils;
import eli.tech.wallri.Utils.Utils;

public class DynamicCatagoriesActivity extends BaseActivity {

    private String categoryUrl = "http://s2.videowallpapers.io:9527/getwallpaperbycate";
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

    private Integer Category_BackGround = R.id.Wallpaper_Category_BackGround;

    private Integer Category_Title = R.id.Wallpaper_Category_Title;
    //这个是通用的显示下载量的控件
    private Integer Download_Number = R.id.Download_Number;

    private Integer Like_Image = R.id.Like_Heart;

    private LoadingDialog alertDialog;
    private String cateName;
    private List<DynamicDataBean.DataBean> newList = new ArrayList();
    private String categaryJson;
    private String feedType = "image";

    @Override
    public int intiLayout() {
        return R.layout.activity_dynamic_home;
    }

    @Override
    public void initData() {
        getmToWallpaperImage().setImageURI("res://eli.tech.wallri/" + R.drawable.menu);
        getmImageDynamicToolbar().setImageResource(R.drawable.back);
        alertDialog = Utils.setLoadingDialog(this);
        cateName = getIntent().getStringExtra("cateName");
        categaryJson = "{\"header\":{\"pkg\":\"io.videowallpapers.vw\",\"fb_app_id\":\"437955976577752\",\"channel\":\"google_play\",\"lang\":\"zh-CN\",\"fcm-token\":\"\",\"deviceid\":\"3e14dc788b33931f6a2205316c02fcbac\",\"version_code\":200261},\"data\":{\"cate\":\"" + cateName + "\",\"lastKey\":\"";
        OKhttpManager.getInstance().sendJsonPost(categoryUrl, categaryJson + "\"}}", new OKhttpManager.Func1() {
            @Override
            public String onResponse(String result) {
                DynamicDataBean parsr = OKhttpManager.parsr(result, DynamicDataBean.class);
                List<DynamicDataBean.DataBean> dataBeanList = parsr.getData();

                for (int dataBean = 0; dataBean < dataBeanList.size(); dataBean++) {

                    if (!dataBeanList.get(dataBean).getCate().equals("ads")) {
                        newList.add(dataBeanList.get(dataBean));
                    }
                }
                SettingUpTheTabLayoutTitle();
                alertDialog.close();
                return null;
            }
        });
    }

    private void SettingUpTheTabLayoutTitle() {
        GridLayoutManager categoriesLayoutManager = new GridLayoutManager(this, 2);
        RecyclerViewItemDecoration categoriesLayoutDecoration = new RecyclerViewItemDecoration(8);

        DynamicFragment categoriesFragment = new DynamicFragment();
        categoriesFragment.setData(this
                , false
                , dynamic_popular_fragment
                , dynamic_popular_fragment_RecyclerView
                , dynamicRefresh
                , dynamic_feed
                , dynamic_image
                , Download_Number
                , Like_Image
                , 0
                , Category_BackGround
                , Category_Title
                , feedType
                , categoriesLayoutManager
                , "post",
                categoriesLayoutDecoration);
        categoriesFragment.SettingPagingData(categoryUrl, categaryJson);
        categoriesFragment.setList(newList);
        Intent intent = new Intent(DynamicCatagoriesActivity.this, DynamicDetailsActivity.class);
        categoriesFragment.SetJumpTransferValue(intent);


        //创建viewpager适配器
        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        adapter.addFragment(categoriesFragment, cateName);
        //设置viewpager适配器
        getmDynamicHomePageViewPager().setAdapter(adapter);
        getmDynamicHomePageViewPager().setOffscreenPageLimit(4);
        //关联viewpager和tabLayout
        getmDynamicHomePageTapLayout().setupWithViewPager(getmDynamicHomePageViewPager());

    }

    //初始化侧拉菜单
    @Override
    protected void SettingTheSidePullMenu() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DynamicCatagoriesActivity.this, LinearLayoutManager.VERTICAL, false);
        SlideFragment dynamicSlideFragment = new SlideFragment();
        dynamicSlideFragment.setData(DynamicCatagoriesActivity.this
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
        SlideTitleList.add(R.string.Mark);//给应用打分Mark
        SlideTitleList.add(R.string.Collection);  //收藏
        SlideTitleList.add(R.string.ScavengingCaching);//缓存8
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.star);
        imageList.add(R.drawable.like_gray_save);
        imageList.add(R.drawable.clear_cache);
        dynamicSlideFragment.setSlideList(SlideTitleList, imageList);
        getSupportFragmentManager().beginTransaction().add(R.id.homeFragmentfragment, dynamicSlideFragment).commit();
        dynamicSlideFragment.setOnItemClickListener(new SlideFragment.onItemClickListener() {
            @Override
            public void onItemclic(View view, int data) {
                switch (data) {
                    //给应用打分Mark
                    case 0:
                        Utils.applicationScore(DynamicCatagoriesActivity.this);
                        break;
                    //收藏
                    case 1:
                        startActivity(new Intent(DynamicCatagoriesActivity.this, DynamicCollectionActivity.class));
                        break;
                    //缓存
                    case 2:
                        CleanMessageUtil.clearAllCache(DynamicCatagoriesActivity.this.getApplicationContext());
                        ToastUtils.showToast(DynamicCatagoriesActivity.this, "Success");
                        break;

                }
            }
        });

    }

    //toobar右边菜单按钮
    @Override
    protected void rightButtonClickEvent() {
        getmToWallpaperImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置旋转属性动画
                ObjectAnimator anim = ObjectAnimator.ofFloat(getmToWallpaperImage(), "rotation", 90f, 180f);
                anim.setDuration(500);
                anim.start();
                // 点击按钮打开侧滑菜单
                if (!getmLayoutDrawer().isDrawerOpen(GravityCompat.END)) {
                    getmLayoutDrawer().openDrawer(GravityCompat.START);

                }

            }
        });
    }

    //toobar左边返回按钮
    @Override
    protected void leftButtonClickEvent() {
        getmImageDynamicToolbar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
