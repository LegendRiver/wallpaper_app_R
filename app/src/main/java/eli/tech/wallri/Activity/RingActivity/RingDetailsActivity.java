package eli.tech.wallri.Activity.RingActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.wx.goodview.GoodView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Activity.LoadingActivity;
import eli.tech.wallri.Adapter.Rings.RingDeatilsRecyclerViewAdapter;
import eli.tech.wallri.DataBean.ProgressBean;
import eli.tech.wallri.DataBean.RingNewsbean;
import eli.tech.wallri.Like.LikeButton;
import eli.tech.wallri.R;
import eli.tech.wallri.System.Constant;
import eli.tech.wallri.Utils.DownloadUtil;
import eli.tech.wallri.Utils.OKhttpManager;
import eli.tech.wallri.Utils.ToastUtils;
import eli.tech.wallri.Utils.Utils;

import static eli.tech.wallri.System.Constant.RingBaseUrl;

/**
 * 铃声的大图页
 */
public class RingDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerViewRingDetails;
    private List<RingNewsbean.RingtonesBean> ringDataList;
    private LinearLayoutManager manager;
    private List<Integer> flagList;
    private List<ProgressBean> imageList;
    private int position;
    private RelativeLayout mDetailsActivityBackImage;
    private ImageView mDetails_good;
    private RelativeLayout mDetails_good_rela;
    private Typeface tf;
    private int scollPosition;
    private MediaPlayer mediaPlayer;
    private List<Integer> mColorList = new ArrayList<>();
    private List<Integer> mBigColorList = new ArrayList<>();
    private List<Boolean> isplayingList = new ArrayList<>();
    private List<Integer> downLoadFlag = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private RelativeLayout mRing_set;
    private GoodView mGoodView;
    private boolean flag = false;
    private int n = 20;
    private int s = 0;
    private String category_name;
    private String sort;
    private RingDeatilsRecyclerViewAdapter adapter;
    private Boolean isContent;
    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;
    private List<String> dataBaseTableNameList = new ArrayList<>();
    private LikeButton likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_details);
        //设置不显示ActionBar
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ImmersionBar.with(this).init();
        initView();
        flagList = new ArrayList<>();
        imageList = new ArrayList<>();
        for (int i = 0; i < ringDataList.size(); i++) {
            isplayingList.add(false);
            ProgressBean bean = new ProgressBean();
            bean.image = R.drawable.play_white;
            bean.progress = 0;
            bean.max = 10000000;
            imageList.add(bean);
            downLoadFlag.add(0);
            flagList.add(0);
        }
        manager = new LinearLayoutManager(RingDetailsActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewRingDetails.setLayoutManager(manager);
        ((DefaultItemAnimator) mRecyclerViewRingDetails.getItemAnimator())
                .setSupportsChangeAnimations(false);
        mRecyclerViewRingDetails.setItemAnimator(null);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerViewRingDetails);
        adapter = new RingDeatilsRecyclerViewAdapter(ringDataList, RingDetailsActivity.this,
                imageList, flagList, mediaPlayer, mColorList, mBigColorList, isplayingList);
        mRecyclerViewRingDetails.setAdapter(adapter);
        mRecyclerViewRingDetails.scrollToPosition(position);
        initLikeButton();

        mRecyclerViewRingDetails.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager l = (LinearLayoutManager) mRecyclerViewRingDetails.
                        getLayoutManager();
                firstVisibleItemPosition = l.findFirstVisibleItemPosition();
                lastVisibleItemPosition = l.findLastVisibleItemPosition();
                scollPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2;

                if (dx > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }

            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) mRecyclerViewRingDetails
                        .getLayoutManager();
                String url = RingBaseUrl + "/ringtones/dl.php?loc=zh&action=download&id="
                        + ringDataList.get(scollPosition).getId() + "&file=" +
                        ringDataList.get(scollPosition).getRingtone();
                //获取最后一个完全显示的ItemPosition ,角标值
                int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = manager.getItemCount();
                if (newState == 0) {
                    Constant.isOut = false;
                    Constant.isPlaying = true;
                    if (nameList.size() != 0) {
                        for (int i = 0; i < nameList.size(); i++) {
                            nameList.remove(i);
                        }
                    }

                    mediaPlayer.reset();
                    adapter.setHandler().removeCallbacks(adapter.setRunnable());
                    for (int i = 0; i < imageList.size(); i++) {
                        imageList.get(i).image = R.drawable.play_white;
                        imageList.get(i).progress = 0;
                        imageList.get(i).max = 10000000;
                        flagList.set(i, 0);
                        downLoadFlag.set(i, 0);
                        isplayingList.set(i, false);
                    }

                    File file = new File(Environment.getExternalStorageDirectory().getPath()
                            + "/eli.tech.wallri" + "/ringtones/"
                            + ringDataList.get(scollPosition).getTitle() + ".mp3");
                    if (!file.exists()) {
                        nameList.add(ringDataList.get(scollPosition).getTitle() + ".mp3");
                        downLoadFlag.set(scollPosition, 1);
                        imageList.get(scollPosition).image = R.drawable.loading2;
                        adapter.notifyDataSetChanged();
                        DownloadUtil.get().download(url, ringDataList.get(scollPosition)
                                        .getTitle() + ".mp3", Environment.getExternalStorageDirectory()
                                        .getPath() + "/eli.tech.wallri" + "/ringtones",
                                new DownloadUtil.OnDownloadListener() {
                                    @Override
                                    public void onDownloadSuccess() {
                                        try {
                                            if (Constant.isOut == false) {
                                                if (downLoadFlag.get(scollPosition) == 1) {
                                                    if (nameList.size() != 0) {
                                                        mediaPlayer.setDataSource(Environment.
                                                                getExternalStorageDirectory().getPath()
                                                                + "/eli.tech.wallri" + "/ringtones/" +
                                                                nameList.get(nameList.size() - 1));
                                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                        mediaPlayer.prepareAsync();
                                                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                            @Override
                                                            public void onPrepared(MediaPlayer mp) {
                                                                mp.start();
                                                                Constant.isPlaying = false;
                                                                isplayingList.set(scollPosition, true);
                                                                flagList.set(scollPosition, 1);
                                                                imageList.get(scollPosition).max = mp.getDuration();
                                                                imageList.get(scollPosition).image = R.drawable.stop_white;
                                                                adapter.notifyDataSetChanged();

                                                            }
                                                        });

                                                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                            @Override
                                                            public void onCompletion(MediaPlayer mp) {
                                                                flagList.set(scollPosition, 0);
                                                                imageList.get(scollPosition).image = R.drawable.play_white;
                                                                adapter.notifyDataSetChanged();
                                                            }
                                                        });
                                                    }

                                                }

                                            }

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onDownloading(int progress) {

                                    }

                                    @Override
                                    public void onDownloadFailed() {

                                    }
                                });

                    } else {
                        try {
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()
                                    .getPath() + "/eli.tech.wallri" + "/ringtones/"
                                    + ringDataList.get(scollPosition).getTitle() + ".mp3");
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.prepareAsync();
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                    imageList.get(scollPosition).max = mp.getDuration();
                                    flagList.set(scollPosition, 1);
                                    isplayingList.set(scollPosition, true);
                                    imageList.get(scollPosition).image = R.drawable.stop_white;
                                    Constant.isPlaying = false;
                                    adapter.notifyDataSetChanged();
                                }
                            });

                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    flagList.set(scollPosition, 0);
                                    imageList.get(scollPosition).image = R.drawable.play_white;
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    likeButton.getLike(ringDataList.get(scollPosition).getRingtone());


                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        n = n + 20;
                        s = s + 20;
                        String mLoadMoreUrl = "http://rington.co/android/picyap-rw/ringtones/list.php?v="
                                + category_name + "&loc=zh&s=" + s + "&n=" + n + "&sort=" + sort;
                        OKhttpManager.getInstance().asyncJsonStringByURL(mLoadMoreUrl, new OKhttpManager.Func1() {
                            @Override
                            public String onResponse(String result) {
                                Gson gson = new Gson();
                                RingNewsbean ringNewsbean1 = gson.fromJson(result, RingNewsbean.class);
                                List<RingNewsbean.RingtonesBean> ringtones = ringNewsbean1.getRingtones();
                                if (!ringtones.isEmpty()) {
                                    for (int i = 0; i < ringtones.size(); i++) {
                                        RingNewsbean.RingtonesBean bean = new RingNewsbean.RingtonesBean();
                                        ProgressBean imagebean = new ProgressBean();
                                        bean.setTitle(ringtones.get(i).getTitle());
                                        bean.setCategory_name(ringtones.get(i).getCategory_name());
                                        bean.setAvg_rating(ringtones.get(i).getAvg_rating());
                                        bean.setId(ringtones.get(i).getId());
                                        bean.setRingtone(ringtones.get(i).getRingtone());
                                        imagebean.image = R.drawable.play_white;
                                        imagebean.progress = 0;
                                        imagebean.BufferProgressBar = 0;
                                        imagebean.max = 10000000;
                                        flagList.add(0);
                                        downLoadFlag.add(0);
                                        imageList.add(imagebean);
                                        ringDataList.add(bean);
                                        isplayingList.add(false);
                                    }
                                    adapter.notifyDataSetChanged();
                                } else {

                                    ToastUtils.showToast(RingDetailsActivity.this, "End");

                                }


                                return null;
                            }
                        });

                    }


                }


            }


        });


    }


    private void initView() {

        Constant.isPlaying = false;
        mGoodView = new GoodView(RingDetailsActivity.this);
        mColorList.add(R.drawable.red);
        mColorList.add(R.drawable.blue);
        mColorList.add(R.drawable.darkgreen);
        mColorList.add(R.drawable.green);
        mColorList.add(R.drawable.grey);
        mColorList.add(R.drawable.populer);
        mColorList.add(R.drawable.yellow);
        mBigColorList.add(R.drawable.bigred);
        mBigColorList.add(R.drawable.bigblue);
        mBigColorList.add(R.drawable.bigdarkgreen);
        mBigColorList.add(R.drawable.biggreen);
        mBigColorList.add(R.drawable.biggrey);
        mBigColorList.add(R.drawable.bigpopuler);
        mBigColorList.add(R.drawable.bigyellow);

        mediaPlayer = new MediaPlayer();
        ringDataList = (List<RingNewsbean.RingtonesBean>) getIntent().getSerializableExtra("RingUrl");

        for (int i = 0; i < ringDataList.size(); i++) {
            if (ringDataList.get(i).getType() == 1) {
                ringDataList.remove(i);
            }

        }
        position = getIntent().getIntExtra("position", 0);
        category_name = getIntent().getStringExtra("category_name");
        sort = getIntent().getStringExtra("sort");
        isContent = getIntent().getBooleanExtra("isContent", false);
        //得到AssetManager
        AssetManager mgr = getAssets();
        //根据路径得到Typeface
        tf = Typeface.createFromAsset(mgr, "fonts/roboto_regular.ttf");
        mRecyclerViewRingDetails = (RecyclerView) findViewById(R.id.RingDetails_RecyclerView);
        mDetailsActivityBackImage = findViewById(R.id.RingDetailsActivityBackImage);
        mDetailsActivityBackImage.getBackground().setAlpha(50);
        mDetailsActivityBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDetails_good = findViewById(R.id.RingDetails_Good);
        mDetails_good_rela = findViewById(R.id.RingDetails_Good_Rela);
        mDetails_good_rela.getBackground().setAlpha(50);
        if (isContent == true) {
            mDetails_good_rela.setVisibility(View.GONE);
        }
        mRing_set = findViewById(R.id.Ring_Set);
        mRing_set.getBackground().setAlpha(50);


        //like的点击事件
        mDetails_good_rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> dataBaseTableDataList = new ArrayList<>();
                if (ringDataList.size() > 0) {
                    dataBaseTableDataList.add(ringDataList.get(scollPosition).getId());
                    dataBaseTableDataList.add(ringDataList.get(scollPosition).getRingtone());
                    dataBaseTableDataList.add(ringDataList.get(scollPosition).getCategory_name());
                    dataBaseTableDataList.add(ringDataList.get(scollPosition).getTitle());
                    dataBaseTableDataList.add(ringDataList.get(scollPosition).getAvg_rating());
                }
                likeButton.setLike(dataBaseTableDataList);
            }
        });
        //设置的点击事件
        mRing_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPoPuWindow(v);

                Animation animation = AnimationUtils.loadAnimation(RingDetailsActivity.this
                        , R.anim.ring_out_bottom);
                mRing_set.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mRing_set.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

    }


    private void initLikeButton() {
        if (ringDataList.size() > 0) {
            dataBaseTableNameList.add("RingId");
            dataBaseTableNameList.add("Ringtone");
            dataBaseTableNameList.add("RingCategory_name");
            dataBaseTableNameList.add("RingTitle");
            dataBaseTableNameList.add("Avg_rating");
            likeButton = new LikeButton(dataBaseTableNameList, "RingCollection.db", this, mDetails_good, "myRingCollection", "Ringtone");
        }
        likeButton.getLike(ringDataList.get(position).getRingtone());
    }

    @Override
    public void onClick(View v) {
        String downloadUrl = RingBaseUrl + "/ringtones/dl.php?loc=zh&action=download&id="
                + ringDataList.get(scollPosition).getId() + "&file=" + ringDataList.get(scollPosition).getRingtone();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(RingDetailsActivity.this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + RingDetailsActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                RingDetailsActivity.this.startActivity(intent);
            } else {
                //有了权限，具体的动作
                switch (v.getId()) {
                    //设置铃声
                    case R.id.Bell:
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            imageList.get(scollPosition).image = R.drawable.play_white;
                            adapter.notifyItemChanged(scollPosition);
                        }


                        startActivity(new Intent(RingDetailsActivity.this
                                , LoadingActivity.class));
                        DownloadUtil.get().download(downloadUrl, ringDataList.get(scollPosition).getTitle() + ".mp3"
                                , Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones"
                                , new DownloadUtil.OnDownloadListener() {
                                    @Override
                                    public void onDownloadSuccess() {
                                        Utils.setRing(RingDetailsActivity.this, RingtoneManager.TYPE_RINGTONE,
                                                Environment.getExternalStorageDirectory().getPath()
                                                        + "/eli.tech.wallri" + "/ringtones/"
                                                        + ringDataList.get(scollPosition).getTitle()
                                                        + ".mp3", ringDataList.get(scollPosition).getTitle());
                                    }

                                    @Override
                                    public void onDownloading(int progress) {

                                    }

                                    @Override
                                    public void onDownloadFailed() {

                                    }
                                });


                        break;
                    //设置系统通知
                    case R.id.Notice:
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            imageList.get(scollPosition).image = R.drawable.play_white;
                            adapter.notifyItemChanged(scollPosition);
                        }
                        startActivity(new Intent(RingDetailsActivity.this, LoadingActivity.class));
                        DownloadUtil.get().download(downloadUrl, ringDataList.get(scollPosition).getTitle() + ".mp3", Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones", new DownloadUtil.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess() {

                                Utils.setRing(RingDetailsActivity.this, RingtoneManager.TYPE_NOTIFICATION, Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones/" + ringDataList.get(scollPosition).getTitle() + ".mp3", ringDataList.get(scollPosition).getTitle());
                            }

                            @Override
                            public void onDownloading(int progress) {

                            }

                            @Override
                            public void onDownloadFailed() {

                            }
                        });

                        break;
                    //设置闹铃
                    case R.id.Alarm:
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            imageList.get(scollPosition).image = R.drawable.play_white;
                            adapter.notifyItemChanged(scollPosition);
                        }
                        startActivity(new Intent(RingDetailsActivity.this, LoadingActivity.class));
                        DownloadUtil.get().download(downloadUrl, ringDataList.get(scollPosition).getTitle() + ".mp3", Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones", new DownloadUtil.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess() {

                                Utils.setRing(RingDetailsActivity.this, RingtoneManager.TYPE_ALARM, Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones/" + ringDataList.get(scollPosition).getTitle() + ".mp3", ringDataList.get(scollPosition).getTitle());
                            }

                            @Override
                            public void onDownloading(int progress) {

                            }

                            @Override
                            public void onDownloadFailed() {

                            }
                        });

                        break;


                }
            }
        }
    }

    //设置PopuWindow
    private void setPoPuWindow(View v) {

        LayoutInflater inflater = LayoutInflater.from(RingDetailsActivity.this);
        View inflate = inflater.inflate(R.layout.ringview, null);
        LinearLayout mBell = inflate.findViewById(R.id.Bell);
        mBell.getBackground().setAlpha(50);
        LinearLayout mNotice = inflate.findViewById(R.id.Notice);
        mNotice.getBackground().setAlpha(50);
        LinearLayout mAlarm = inflate.findViewById(R.id.Alarm);
        mAlarm.getBackground().setAlpha(50);
        SimpleDraweeView mBellImage = inflate.findViewById(R.id.BellImage);
        mBellImage.setImageURI("res://eli.tech.wallri/" + R.drawable.telephone);
        TextView mBellText = inflate.findViewById(R.id.BellText);
        SimpleDraweeView mNoticeImage = inflate.findViewById(R.id.NoticeImage);
        mNoticeImage.setImageURI("res://eli.tech.wallri/" + R.drawable.noti);
        TextView mNoticeText = inflate.findViewById(R.id.NoticeText);
        SimpleDraweeView mAlarmImage = inflate.findViewById(R.id.AlarmImage);
        mAlarmImage.setImageURI("res://eli.tech.wallri/" + R.drawable.alarm_clock);
        TextView mAlarmText = inflate.findViewById(R.id.AlarmText);
        mBell.setOnClickListener(this);
        mNotice.setOnClickListener(this);
        mAlarm.setOnClickListener(this);
        inflate.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int[] location = new int[2];
        v.getLocationOnScreen(location);

        PopupWindow mPopuWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopuWindow.setOutsideTouchable(true);
        mPopuWindow.setFocusable(true);
        mPopuWindow.setAnimationStyle(R.style.AnimationFade1);
        mPopuWindow.setBackgroundDrawable(new ColorDrawable());
        mRing_set.getLocationOnScreen(location);
        mPopuWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        mPopuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mRing_set.setVisibility(View.VISIBLE);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                Animation animation = AnimationUtils.loadAnimation(RingDetailsActivity.this, R.anim.ring_in_bottom);
                mRing_set.startAnimation(animation);

            }
        });

    }

    @Override
    protected void onDestroy() {
        //释放mediaplayer资源
        Constant.isOut = true;
        Constant.isPlaying = true;
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer = null;
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}