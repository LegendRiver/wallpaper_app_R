package eli.tech.wallri.Activity.WallPaperActivity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Activity.BaseActivity;
import eli.tech.wallri.Adapter.MyFragmentPageAdapter;
import eli.tech.wallri.Adapter.RecyclerViewItemDecoration;
import eli.tech.wallri.DataBean.NewsBean;
import eli.tech.wallri.Fragment.WallPaperFragment.WallpaperFragment;
import eli.tech.wallri.Like.LikeButton;
import eli.tech.wallri.R;

/**
 * 这个类是收藏的页面
 */
public class WallPaperCollection extends BaseActivity {

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

    private List<NewsBean.DataBean> setList;
    private LikeButton likeButton;

    private List<String> dataBaseTableNameList = new ArrayList<>();


    @Override
    public int intiLayout() {
        return R.layout.activity_dynamic_home;
    }

    @Override
    public void initData() {
        getmImageDynamicToolbar().setImageResource(R.drawable.back);
        setList = new ArrayList<>();
        initDataBase();
        Cursor queryData = likeButton.getQueryData();
        while (queryData.moveToNext()) {
            //把URL存入set集合 防止重复
            NewsBean.DataBean bean = new NewsBean.DataBean();
            bean.setDownload_count(queryData.getInt(queryData.getColumnIndex("Downloadcount")));
            bean.setImage_path(queryData.getString(queryData.getColumnIndex("Imagepath")));
            bean.setPreview(queryData.getString(queryData.getColumnIndex("Preview")));
            bean.setThumbnail_url(queryData.getString(queryData.getColumnIndex("Thumbnailurl")));
            setList.add(bean);


        }
        for (int i = 0; i < setList.size() - 1; i++) {
            for (int j = setList.size() - 1; j > i; j--) {
                if (setList.get(j).getDownload_count() == setList.get(i).getDownload_count()) {
                    setList.remove(j);
                }
            }
        }

        initFragment();
    }

    private void initFragment() {
        //创建RecyclerView布局管理器
        GridLayoutManager popularLayoutManager = new GridLayoutManager(WallPaperCollection.this, 2);
        RecyclerViewItemDecoration popularDecoration = new RecyclerViewItemDecoration(8);

        //创建ftagment对象
        WallpaperFragment collectionFragment = new WallpaperFragment();
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
        collectionFragment.setList(setList);
        Intent popularIntent = new Intent(WallPaperCollection.this, WallpaperDetailActivity.class);
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
    private void initDataBase() {
        dataBaseTableNameList.add("Imagepath");
        dataBaseTableNameList.add("Preview");
        dataBaseTableNameList.add("Thumbnailurl");
        dataBaseTableNameList.add("Downloadcount");
        likeButton = new LikeButton(dataBaseTableNameList, "collection.db", this, null, "myCollection", "Preview");
    }


}
