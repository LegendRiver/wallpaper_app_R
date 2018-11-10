package eli.tech.wallri.Activity.WallPaperActivity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.view.Display;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Activity.BaseActivity;
import eli.tech.wallri.Adapter.MyFragmentPageAdapter;
import eli.tech.wallri.Adapter.RecyclerViewItemDecoration;
import eli.tech.wallri.DataBean.NewsBean;
import eli.tech.wallri.Fragment.WallPaperFragment.WallpaperFragment;
import eli.tech.wallri.R;
import eli.tech.wallri.Utils.SystemUtil;
import eli.tech.wallri.Utils.Utils;

/**
 * Created by mac on 2018/3/30.
 */

public class WallpaperCategoryActivity extends BaseActivity {
    private String categoryUrl = "";
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

    private LoadingDialog alertDialog;
    private String cateName;
    private int cateID;
    private String feedType = "image";
    private int offset = 0;
    private WallpaperFragment categoriesFragment;
    private List<NewsBean.DataBean> data=new ArrayList<>();

    @Override
    public int intiLayout() {
        return R.layout.activity_dynamic_home;
    }

    @Override
    public void initData() {
        getmImageDynamicToolbar().setImageResource(R.drawable.back);
        alertDialog = Utils.setLoadingDialog(this);
        cateName = getIntent().getStringExtra("cateName");
        cateID = getIntent().getIntExtra("cateId", 0);

        processURL();
        nitializeTheDataCreationObject();
    }

    // 创建对象
    private void nitializeTheDataCreationObject() {
        GridLayoutManager categoriesLayoutManager = new GridLayoutManager(WallpaperCategoryActivity.this, 2);
        RecyclerViewItemDecoration categoriesLayoutDecoration = new RecyclerViewItemDecoration(8);

        categoriesFragment = new WallpaperFragment();
        categoriesFragment.setData(WallpaperCategoryActivity.this
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
                , "get",
                categoriesLayoutDecoration);
        categoriesFragment.SettingPagingData(categoryUrl, "");


        categoriesFragment.setList(data);
        categoriesFragment.setOffset(offset);
        categoriesFragment.getMethod();

        Intent intent = new Intent(WallpaperCategoryActivity.this, WallpaperDetailActivity.class);
        categoriesFragment.SetJumpTransferValue(intent);
        alertDialog.close();


        //创建viewpager适配器
        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        adapter.addFragment(categoriesFragment, cateName);
        //设置viewpager适配器
        getmDynamicHomePageViewPager().setAdapter(adapter);
        getmDynamicHomePageViewPager().setOffscreenPageLimit(4);
        //关联viewpager和tabLayout
        getmDynamicHomePageTapLayout().setupWithViewPager(getmDynamicHomePageViewPager());

    }

    private void processURL() {
        String model = Build.MODEL;
        this.categoryUrl = "http://wallpaper.ksmobile.com/v2/list/get?pid=1&lan=en_US&app_lan=en-US&net=1&aid=181f767dffd70569&brand=" + SystemUtil.getDeviceBrand() +
                "&model=" + model + "&osv=" + SystemUtil.getSystemVersion() + "&api_level=" + Build.VERSION.SDK_INT + "&appv=20600&mcc=460&mnc=01&vga=" + getScreen() + "&pos=106&cid=" + cateID + "&count=30&offset=0";
    }

    //获得屏幕宽高
    private String getScreen() {
        Display mDisplay = getWindowManager().getDefaultDisplay();
        int width = mDisplay.getWidth();
        int height = mDisplay.getHeight();
        return width + "x" + height;
    }
}
