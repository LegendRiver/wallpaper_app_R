package eli.tech.wallri.Activity.DynamicWallpaperActivity;

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
import eli.tech.wallri.DataBean.DynamicDataBean;
import eli.tech.wallri.Fragment.DynamicWallpaperFragment.DynamicFragment;
import eli.tech.wallri.Like.LikeButton;
import eli.tech.wallri.R;
import eli.tech.wallri.Utils.Utils;
import eli.tech.wallri.view.PopWindow;

/**
 * Created by 小雷 on 2018/3/22.
 */

public class DynamicDetailsActivity extends BaseActivity implements View.OnClickListener {

    //这个是通用的布局
    private Integer dynamic_popular_fragment = R.layout.dynamic_details_fragment;
    private Integer dynamicRefresh = R.id.activity_Dynamic_Details_cRefresh;
    //这个是通用布局的控件id
    private Integer dynamic_popular_fragment_RecyclerView = R.id.activity_Dynamic_Details_RecyclerView;
    //这个是通用布局的item布局
    private Integer dynamic_feed = R.layout.dynamic_details_feed;
    //这个是通用布局的item控件id
    private Integer dynamic_image = R.id.videoplayer;

    private Integer dynamic_video_play_image = R.id.video_play_image;

    private Integer Category_BackGround = R.id.Wallpaper_Category_BackGround;

    private Integer Category_Title = R.id.Wallpaper_Category_Title;

    private eli.tech.wallri.view.CustomViewpager CustomViewpager;
    private String url;
    private String json;
    private String feedType = "video";
    private int position;
    private TextView mDeatilsActivityInsertVideo;
    private RelativeLayout mViedeoBottonRela;
    private RelativeLayout mDetailsActivityBackvideo;
    private ImageView mDetailsGoodVideo;
    private RelativeLayout mDetailsGoodRelaVideo;
    private RelativeLayout mVideotopLine;
    private DynamicFragment dynamicDetailFragment;
    private ProgressBar mDownloadProgress;
    private LoadingDialog loadingDialog;
    private IncentiveVideoAd ads;
    private List<String> dataBaseTableNameList = new ArrayList<>();
    private LikeButton likeButton;
    private List<DynamicDataBean.DataBean> data;


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
        CustomViewpager = findViewById(R.id.activity_dynamic_details_viewpager);
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
        data = (List<DynamicDataBean.DataBean>) getIntent().getSerializableExtra("data");
        url = getIntent().getStringExtra("url");
        json = getIntent().getStringExtra("json");
        position = getIntent().getIntExtra("position", 0);
        if (getIntent().getStringExtra("isLike") != null) {
            mDetailsGoodRelaVideo.setVisibility(View.GONE);
        }
        //创建fragment传值
        LinearLayoutManager layoutManager = new LinearLayoutManager(DynamicDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerViewItemDecoration newsLayoutDecoration = new RecyclerViewItemDecoration(0);
        dynamicDetailFragment = new DynamicFragment();
        dynamicDetailFragment.setData(this
                , false
                , dynamic_popular_fragment
                , dynamic_popular_fragment_RecyclerView
                , dynamicRefresh
                , dynamic_feed
                , dynamic_video_play_image
                , 0
                , 0
                , dynamic_image
                , 0
                , 0
                , feedType
                , layoutManager
                , "post",
                newsLayoutDecoration);
        dynamicDetailFragment.SettingPagingData(url, json);
        dynamicDetailFragment.setList(data);
        dynamicDetailFragment.setPosition(position);
        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        adapter.addFragment(dynamicDetailFragment, null);
        CustomViewpager.setAdapter(adapter);
        loadingDialog.close();
        initLikeButton();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DetailsActivityBackvideo:
                finish();
                break;
            case R.id.Video_Details_Good_Rela:
                addLike();
                break;
            case R.id.ViedeoBottonRela:
                setPoPWindow(v);
                break;
        }

    }

    private void initLikeButton() {

        if (dynamicDetailFragment.getList().size() > 0) {
            dataBaseTableNameList.add("thumb");
            dataBaseTableNameList.add("preview");
            dataBaseTableNameList.add("video");
            dataBaseTableNameList.add("videokey");
            likeButton = new LikeButton(dataBaseTableNameList, "videoCollection.db", this, mDetailsGoodVideo, "myVideoCollection", "videokey");
            dynamicDetailFragment.setButton(likeButton);
        }
        likeButton.getLike(data.get(position).getKey());
    }

    //点击收藏把数据存入本地数据库(点心)
    private void addLike() {
        int position = dynamicDetailFragment.getPosition();
        List<DynamicDataBean.DataBean> list = dynamicDetailFragment.getList();
        List<String> dataBaseTableDataList = new ArrayList<>();
        if (dynamicDetailFragment.getList().size() > 0) {
            dataBaseTableDataList.add(list.get(position).getThumb());
            dataBaseTableDataList.add(list.get(position).getPreview());
            dataBaseTableDataList.add(list.get(position).getVideo());
            dataBaseTableDataList.add(list.get(position).getKey());
        }
        likeButton.setLike(dataBaseTableDataList);
    }

    //设置set窗口
    private void setPoPWindow(View v) {
        if (dynamicDetailFragment.getList().size() > 0) {
            int score = dynamicDetailFragment.getList().get(dynamicDetailFragment.getPosition()).getScore();
            String sdCardPath = Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri/path.txt";
            String imagePath = dynamicDetailFragment.getList().get(dynamicDetailFragment.getPosition()).getVideo();
            String fileName = dynamicDetailFragment.getList().get(dynamicDetailFragment.getPosition()).getKey() + ".mp4";
            String saveDir = Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/video";
            //初始化popuwindou
            PopWindow popuWindow = new PopWindow(this, v, getWindow(), score, sdCardPath, fileName, imagePath, saveDir, mDownloadProgress, ads);
            popuWindow.initVideoView();
            popuWindow.setSetOnInitAdRandomNum(new PopWindow.setOnInitAdRandomNumListener() {
                @Override
                public void OnInitAdRandomNum() {
                    dynamicDetailFragment.getList().get(dynamicDetailFragment.getPosition()).setScore(50);
                }
            });
        }
    }


}
