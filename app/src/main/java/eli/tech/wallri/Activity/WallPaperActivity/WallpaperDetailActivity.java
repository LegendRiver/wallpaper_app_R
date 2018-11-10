package eli.tech.wallri.Activity.WallPaperActivity;

import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Activity.BaseActivity;
import eli.tech.wallri.AdManager.IncentiveVideoAd;
import eli.tech.wallri.Adapter.MyFragmentPageAdapter;
import eli.tech.wallri.Adapter.RecyclerViewItemDecoration;
import eli.tech.wallri.DataBean.NewsBean;
import eli.tech.wallri.Fragment.WallPaperFragment.WallpaperFragment;
import eli.tech.wallri.Like.LikeButton;
import eli.tech.wallri.R;
import eli.tech.wallri.Utils.Utils;
import eli.tech.wallri.view.CustomViewpager;
import eli.tech.wallri.view.PopWindow;

/**
 * Created by mac on 2018/3/30.
 */

public class WallpaperDetailActivity extends BaseActivity implements View.OnClickListener {
    //这个是通用的布局
    private Integer dynamic_popular_fragment = R.layout.dynamic_details_fragment;
    private Integer dynamicRefresh = R.id.activity_Dynamic_Details_cRefresh;
    //这个是通用布局的控件id
    private Integer dynamic_popular_fragment_RecyclerView = R.id.activity_Dynamic_Details_RecyclerView;
    //这个是通用布局的item布局
    private Integer wallpaper_leave_image = R.layout.wallpaper_leave_image;
    //这个是通用布局的item控件id
    private Integer Wallpaper_Level_Image = R.id.Wallpaper_Level_Image;
    private CustomViewpager myViewpager;
    private String url;
    private String json;
    private String feedType = "orientationImage";
    private int position;
    private TextView mDeatilsActivityInsertVideo;
    private RelativeLayout mViedeoBottonRela;
    private RelativeLayout mDetailsActivityBackvideo;
    private ImageView mDetailsGoodVideo;
    private RelativeLayout mDetailsGoodRelaVideo;
    private RelativeLayout mVideotopLine;
    private WallpaperFragment wallpaperDetailFragment;
    private ProgressBar mDownloadProgress;
    private LoadingDialog loadingDialog;
    private IncentiveVideoAd ads;
    private LikeButton likeButton;

    @Override
    protected void initBar() {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化状态栏的第三方
        ImmersionBar.with(this).init();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_dynamic_details;
    }

    @Override
    protected void initView() {
        super.initView();
        //创建loadingdialog对象
        loadingDialog = Utils.setLoadingDialog(this);
        ads = new IncentiveVideoAd(this);
        ads.loadRewardedVideoAd();
        //找控件
        myViewpager = findViewById(R.id.activity_dynamic_details_viewpager);
        mDeatilsActivityInsertVideo = (TextView) findViewById(R.id.DeatilsActivityInsertVideo);
        mViedeoBottonRela = (RelativeLayout) findViewById(R.id.ViedeoBottonRela);
        mDetailsActivityBackvideo = (RelativeLayout) findViewById(R.id.DetailsActivityBackvideo);
        mDetailsGoodVideo = (ImageView) findViewById(R.id.Video_Details_Good);
        mDetailsGoodRelaVideo = (RelativeLayout) findViewById(R.id.Video_Details_Good_Rela);
        mVideotopLine = (RelativeLayout) findViewById(R.id.videotopLine);
        mDownloadProgress = findViewById(R.id.downloadProgress);
        mViedeoBottonRela.getBackground().setAlpha(50);
        mDetailsActivityBackvideo.getBackground().setAlpha(50);
        mDetailsGoodRelaVideo.getBackground().setAlpha(50);
        mDetailsActivityBackvideo.setOnClickListener(this);
        mDetailsGoodRelaVideo.setOnClickListener(this);
        mViedeoBottonRela.setOnClickListener(this);
    }

    //初始化数据
    @Override
    public void initData() {
        final List<NewsBean.DataBean> data = (List<NewsBean.DataBean>) getIntent().getSerializableExtra("data");
        url = getIntent().getStringExtra("url");
        json = getIntent().getStringExtra("json");
        position = getIntent().getIntExtra("position", 0);
        if (getIntent().getStringExtra("isLike") != null) {
            mDetailsGoodRelaVideo.setVisibility(View.GONE);
        }
        //创建fragment传值
        LinearLayoutManager layoutManager = new LinearLayoutManager(WallpaperDetailActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerViewItemDecoration newsLayoutDecoration = new RecyclerViewItemDecoration(0);
        wallpaperDetailFragment = new WallpaperFragment();
        wallpaperDetailFragment.setData(this
                , false
                , dynamic_popular_fragment
                , dynamic_popular_fragment_RecyclerView
                , dynamicRefresh
                , wallpaper_leave_image
                , 0
                , 0
                , 0
                , Wallpaper_Level_Image
                , 0
                , 0
                , feedType
                , layoutManager
                , "get",
                newsLayoutDecoration);
        wallpaperDetailFragment.SettingPagingData(url, json);
        wallpaperDetailFragment.setList(data);
        wallpaperDetailFragment.setPosition(position);

        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        adapter.addFragment(wallpaperDetailFragment, null);
        myViewpager.setAdapter(adapter);
        initLikeButton();
        loadingDialog.close();
    }

    private void initLikeButton() {
        List<String> dataBaseTableNameList = new ArrayList<>();
        if (wallpaperDetailFragment.getList().size() > 0) {
            dataBaseTableNameList.add("Imagepath");
            dataBaseTableNameList.add("Preview");
            dataBaseTableNameList.add("Thumbnailurl");
            dataBaseTableNameList.add("Downloadcount");
            likeButton = new LikeButton(dataBaseTableNameList, "collection.db", this, mDetailsGoodVideo, "myCollection", "Preview");
            wallpaperDetailFragment.setButton(likeButton);
        }
        likeButton.getLike(wallpaperDetailFragment.getList().get(position).getPreview());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DetailsActivityBackvideo:
                finish();
                break;
            case R.id.Video_Details_Good_Rela:
                setLike();
                break;
            case R.id.ViedeoBottonRela:
                setPoPWindow(v);
                break;
        }

    }

    //设置like
    private void setLike() {
        List<NewsBean.DataBean> list = wallpaperDetailFragment.getList();
        int position = wallpaperDetailFragment.getPosition();
        if (list != null && list.size() > 0) {
            List<String> dataBaseTableDataList = new ArrayList<>();
            dataBaseTableDataList.add(list.get(position).getImage_path());
            dataBaseTableDataList.add(list.get(position).getPreview());
            dataBaseTableDataList.add(list.get(position).getThumbnail_url());
            dataBaseTableDataList.add(String.valueOf(list.get(position).getDownload_count()));
            likeButton.setLike(dataBaseTableDataList);
        }
    }

    //设置set窗口
    private void setPoPWindow(View v) {
        if (wallpaperDetailFragment.getList().size() > 0) {
             double score = wallpaperDetailFragment.getList().get(wallpaperDetailFragment.getPosition()).getAdRandomNum();
            String sdCardPath = Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri/path.txt";
            String imagePath = wallpaperDetailFragment.getList().get(wallpaperDetailFragment.getPosition()).getImage_path();
            String fileName = wallpaperDetailFragment.getList().get(wallpaperDetailFragment.getPosition()).getTitle()+".jpg";
            String saveDir = Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/image";
            //初始化popuwindou
            PopWindow popuWindow = new PopWindow(this, v, getWindow(), 0, sdCardPath, fileName, imagePath, saveDir, mDownloadProgress, ads);
            popuWindow.setAdRandomNum(score);
            popuWindow.initImageView();
            popuWindow.setSetOnInitAdRandomNum(new PopWindow.setOnInitAdRandomNumListener() {
                @Override
                public void OnInitAdRandomNum() {
                    wallpaperDetailFragment.getList().get(wallpaperDetailFragment.getPosition()).setAdRandomNum(0.65);
                }
            });
        }
    }
}
