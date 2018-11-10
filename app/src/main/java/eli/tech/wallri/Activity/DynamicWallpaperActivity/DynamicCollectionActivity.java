package eli.tech.wallri.Activity.DynamicWallpaperActivity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Activity.BaseActivity;
import eli.tech.wallri.Adapter.MyFragmentPageAdapter;
import eli.tech.wallri.Adapter.RecyclerViewItemDecoration;
import eli.tech.wallri.DataBean.DynamicDataBean;
import eli.tech.wallri.Fragment.DynamicWallpaperFragment.DynamicFragment;
import eli.tech.wallri.Like.LikeButton;
import eli.tech.wallri.R;

/**
 * Created by 小雷 on 2018/3/29.
 */

public class DynamicCollectionActivity extends BaseActivity {
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
    private List<DynamicDataBean.DataBean> videoCollectionData;
    private List<String> dataBaseTableNameList = new ArrayList<>();
    private LikeButton likeButton;


    @Override
    public int intiLayout() {
        return R.layout.activity_dynamic_home;
    }


    //初始化数据
    @Override
    public void initData() {
        getmImageDynamicToolbar().setImageResource(R.drawable.back);
        videoCollectionData = new ArrayList<>();
        initDataBase();
        Cursor queryData = likeButton.getQueryData();
        while (queryData.moveToNext()) {
            DynamicDataBean.DataBean bean = new DynamicDataBean.DataBean();
            bean.setThumb(queryData.getString(queryData.getColumnIndex("thumb")));
            bean.setPreview(queryData.getString(queryData.getColumnIndex("preview")));
            bean.setVideo(queryData.getString(queryData.getColumnIndex("video")));
            bean.setKey(queryData.getString(queryData.getColumnIndex("videokey")));
            videoCollectionData.add(bean);
        }
        //去除list集合里重复的对象
        for (int i = 0; i < videoCollectionData.size() - 1; i++) {
            for (int j = videoCollectionData.size() - 1; j > i; j--) {
                if (videoCollectionData.get(j).getKey().equals(videoCollectionData.get(i).getKey())) {

                    videoCollectionData.remove(j);
                }
            }
        }

        initFragment();

    }

    private void initDataBase() {
        dataBaseTableNameList.add("thumb");
        dataBaseTableNameList.add("preview");
        dataBaseTableNameList.add("video");
        dataBaseTableNameList.add("videokey");
        likeButton = new LikeButton(dataBaseTableNameList, "videoCollection.db", this, null, "myVideoCollection", "videokey");

    }

    //初始化fragment对象
    private void initFragment() {
        //创建RecyclerView布局管理器
        GridLayoutManager popularLayoutManager = new GridLayoutManager(DynamicCollectionActivity.this, 2);
        RecyclerViewItemDecoration popularDecoration = new RecyclerViewItemDecoration(8);

        //创建ftagment对象
        DynamicFragment collectionFragment = new DynamicFragment();
        collectionFragment.setData(this
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
        collectionFragment.setList(videoCollectionData);
        Intent popularIntent = new Intent(DynamicCollectionActivity.this, DynamicDetailsActivity.class);
        popularIntent.putExtra("isLike", "asd");
        collectionFragment.SetJumpTransferValue(popularIntent);

        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        adapter.addFragment(collectionFragment, this.getResources().getString(R.string.collection));
        getmDynamicHomePageViewPager().setAdapter(adapter);
        getmDynamicHomePageTapLayout().setupWithViewPager(getmDynamicHomePageViewPager());
    }

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
