package eli.tech.wallri.Activity.DynamicWallpaperActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Activity.BaseActivity;
import eli.tech.wallri.Adapter.MyFragmentPageAdapter;
import eli.tech.wallri.Adapter.RecyclerViewItemDecoration;
import eli.tech.wallri.DataBean.DynamicCategoriesBean;
import eli.tech.wallri.DataBean.DynamicDataBean;
import eli.tech.wallri.Fragment.SlideFragment;
import eli.tech.wallri.Fragment.DynamicWallpaperFragment.DynamicFragment;
import eli.tech.wallri.R;
import eli.tech.wallri.Utils.CleanMessageUtil;
import eli.tech.wallri.Utils.OKhttpManager;
import eli.tech.wallri.Utils.ToastUtils;
import eli.tech.wallri.Utils.Utils;

/**
 * Created by 小雷 on 2018/3/20.
 */

public class DynamicHomeActivity extends BaseActivity {
    private String firstURL = "http://s2.videowallpapers.io:9527/connect";
    private String newestSecondUrl = "http://s2.videowallpapers.io:9527/getnewestwallpapers";
    private String popularSecondUrl = "http://s2.videowallpapers.io:9527/gethotestwallpapers";
    private String firstJson = "{\"header\":{\"pkg\":\"io.videowallpapers.vw\",\"fb_app_id\":\"437955976577752\",\"channel\":\"google_play\",\"lang\":\"zh-CN\",\"fcm-token\":\"\",\"deviceid\":\"3e14dc788b33931f6a2205316c02faaaa\",\"version_code\":200261},\"data\":{\"userId\":\"3e14dc788b33931f6a2205316c02faaaa\",\"userType\":\"facebook\",\"sharecode\":\"\"}}\n";
    private String SecondJson = "{\"header\":{\"pkg\":\"io.videowallpapers.vw\",\"fb_app_id\":\"437955976577752\",\"channel\":\"google_play\",\"lang\":\"zh-CN\",\"fcm-token\":\"\",\"deviceid\":\"3e14dc788b33931f6a2205316c02faaaa\",\"version_code\":200261},\"data\":{\"lastKey\":\"";

    private String hottestJson = "";
    private String newestJson = "";
    private String categoryJson = "";

    private List<DynamicDataBean.DataBean> popularList = new ArrayList<>();
    private List<DynamicDataBean.DataBean> newestList = new ArrayList<>();
    private List<DynamicDataBean.DataBean> categoryList = new ArrayList<>();

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
    private Integer Download_Number = R.id.Download_Number;
    //这个是通用的显示like数量的控件
    private Integer Like_Image = R.id.Like_Heart;

    private Integer Category_BackGround = R.id.Wallpaper_Category_BackGround;

    private Integer Category_Title = R.id.Wallpaper_Category_Title;

    private String feedType = "image";
    private LoadingDialog loadingDialog;

    @Override
    public int intiLayout() {
        return R.layout.activity_dynamic_home;
    }

    //初始化数据
    @Override
    public void initData() {
        loadingDialog = Utils.setLoadingDialog(this);
        getmToWallpaperImage().setImageURI("res://eli.tech.wallri/" + R.drawable.menu);
        getmImageDynamicToolbar().setImageResource(R.drawable.back);
        //请求
        OKhttpManager.getInstance().sendJsonPost(firstURL, firstJson, new OKhttpManager.Func1() {
            @Override
            public String onResponse(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    hottestJson = "{\"data\":" + jsonObject.getJSONObject("data").getString("hotest") + ",\"error\": {\"errorCode\": 0,\"errorMsg\": \"\"},\"result\": 0,\"timestamp\": 0}";
                    newestJson = "{\"data\":" + jsonObject.getJSONObject("data").getString("newest") + ",\"error\": {\"errorCode\": 0,\"errorMsg\": \"\"},\"result\": 0,\"timestamp\": 0}";
                    categoryJson = "{\"data\":" + jsonObject.getJSONObject("data").getString("categories") + ",\"error\": {\"errorCode\": 0,\"errorMsg\": \"\"},\"result\": 0,\"timestamp\": 0}";

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dynamicDataProcess(popularList, hottestJson, 0);
                dynamicDataProcess(newestList, newestJson, 0);
                dynamicDataProcess(categoryList, categoryJson, 1);
                SettingUpTheTablayoutTitle();
                DynamicHomeActivity.this.loadingDialog.close();
                return result;
            }
        });
    }

    /**
     * 第一次请求初始化数据
     *
     * @param list
     */
    private void dynamicDataProcess(List list, String jsonStr, int type) {


        switch (type) {
            case 0:
                List<DynamicDataBean.DataBean> dynamicDataList = OKhttpManager.parsr(jsonStr, DynamicDataBean.class).getData();
                for (int index = 0; index < dynamicDataList.size(); index++) {
                    if (!dynamicDataList.get(index).getCate().equals("ads")) {
                        list.add(dynamicDataList.get(index));
                    }

                }
                break;
            case 1:

                List<DynamicCategoriesBean.DataBean> categories = OKhttpManager.parsr(jsonStr, DynamicCategoriesBean.class).getData();
                for (int index = 0; index < categories.size(); index++) {
                    DynamicDataBean.DataBean dataBean = new DynamicDataBean.DataBean();
                    dataBean.setCate(categories.get(index).getName());
                    dataBean.setThumb(categories.get(index).getImage());
                    dataBean.setId(categories.get(index).getId());
                    list.add(dataBean);
                }
                break;
        }

    }

    //床创建fragemnt对象传值
    //设置tablayout并和viewpager，fragment关联
    private void SettingUpTheTablayoutTitle() {
        //存入Fragment的集合
        //populai
        //创建RecyclerView布局管理器
        GridLayoutManager popularLayoutManager = new GridLayoutManager(DynamicHomeActivity.this, 2);
        RecyclerViewItemDecoration popularDecoration = new RecyclerViewItemDecoration(8);

        //创建ftagment对象
        DynamicFragment popularFragment = new DynamicFragment();
        popularFragment.setData(this
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
                , popularLayoutManager
                , "post",
                popularDecoration);
        popularFragment.SettingPagingData(popularSecondUrl, SecondJson);
        popularFragment.setList(popularList);
        Intent popularIntent = new Intent(DynamicHomeActivity.this, DynamicDetailsActivity.class);
        popularFragment.SetJumpTransferValue(popularIntent);

        //new
        //创建ftagment对象
        GridLayoutManager newsLayoutManager = new GridLayoutManager(DynamicHomeActivity.this, 2);
        RecyclerViewItemDecoration newsLayoutDecoration = new RecyclerViewItemDecoration(8);

        DynamicFragment newsFragment = new DynamicFragment();
        newsFragment.setData(this
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
                , newsLayoutManager
                , "post",
                newsLayoutDecoration);
        newsFragment.SettingPagingData(newestSecondUrl, SecondJson);
        newsFragment.setList(newestList);
        Intent newsIntent = new Intent(DynamicHomeActivity.this, DynamicDetailsActivity.class);
        newsFragment.SetJumpTransferValue(newsIntent);

        //category
        //创建fragment对象
        LinearLayoutManager categoriesLinearLayoutManager = new LinearLayoutManager(DynamicHomeActivity.this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewItemDecoration categoriesLayoutDecoration = new RecyclerViewItemDecoration(8);
        DynamicFragment categoriesFragment = new DynamicFragment();
        categoriesFragment.setData(this
                , true
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
                , categoriesLinearLayoutManager
                , "post"
                , categoriesLayoutDecoration);
        categoriesFragment.setList(categoryList);
        Intent intent = new Intent(DynamicHomeActivity.this, DynamicCatagoriesActivity.class);
        categoriesFragment.SetJumpTransferValue(intent);

        //创建viewpager适配器
        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        adapter.addFragment(categoriesFragment, this.getResources().getString(R.string.classify));
        adapter.addFragment(popularFragment, this.getResources().getString(R.string.hot));
        adapter.addFragment(newsFragment, this.getResources().getString(R.string.news));
        //设置viewpager适配器
        getmDynamicHomePageViewPager().setAdapter(adapter);
        getmDynamicHomePageViewPager().setOffscreenPageLimit(4);
        //关联viewpager和tablayout
        getmDynamicHomePageTapLayout().setupWithViewPager(getmDynamicHomePageViewPager());

    }

    //初始化侧拉菜单
    @Override
    protected void SettingTheSidePullMenu() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DynamicHomeActivity.this, LinearLayoutManager.VERTICAL, false);
        SlideFragment dynamicSlideFragment = new SlideFragment();
        dynamicSlideFragment.setData(DynamicHomeActivity.this
                , false
                , R.layout.navifragment
                , 0
                , R.layout.navifragmentitem
                , 0
                , 0
                , 0
                , 0
                , null
                ,0
                ,0
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
                        Utils.applicationScore(DynamicHomeActivity.this);
                        break;
                    //收藏
                    case 1:
                        startActivity(new Intent(DynamicHomeActivity.this, DynamicCollectionActivity.class));
                        break;
                    //缓存
                    case 2:
                        CleanMessageUtil.clearAllCache(DynamicHomeActivity.this.getApplicationContext());
                        ToastUtils.showToast(DynamicHomeActivity.this, "Success");
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
