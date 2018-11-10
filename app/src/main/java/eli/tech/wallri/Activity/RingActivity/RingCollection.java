package eli.tech.wallri.Activity.RingActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Adapter.Rings.RingNewsRecyclerViewAdapter;
import eli.tech.wallri.DataBean.ProgressBean;
import eli.tech.wallri.DataBean.RingNewsbean;
import eli.tech.wallri.Like.LikeButton;
import eli.tech.wallri.R;

/**
 * 铃声的收藏界面
 */
public class RingCollection extends AppCompatActivity {

    private ImageView mCollectionBackRing;
    private Toolbar mToobarRing;
    private RecyclerView mCollectionRecyclerViewRing;
    private List<RingNewsbean.RingtonesBean> setList;
    private List<Integer> flagList = new ArrayList<>();
    private List<ProgressBean> imageList = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private RingNewsRecyclerViewAdapter adapter;
    private List<Boolean> isplayingList = new ArrayList<>();
    private int scollPosition;
    private List<Integer> downLoadFlag = new ArrayList<>();
    private List<String> dataBaseTableNameList = new ArrayList<>();
    private LikeButton likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_collection);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        //沉浸式
        ImmersionBar.with(this).init();


        for (int i = 0; i < setList.size(); i++) {
            isplayingList.add(false);
            flagList.add(0);
            downLoadFlag.add(0);
            ProgressBean bean = new ProgressBean();
            bean.image = R.drawable.play;
            bean.progress = 0;
            bean.BufferProgressBar = 0;
            bean.max = 100;
            imageList.add(bean);
            isplayingList.add(false);

        }
        if (mediaPlayer != null) {
            adapter = new RingNewsRecyclerViewAdapter(null,setList, RingCollection.this, imageList, flagList, mediaPlayer, isplayingList, downLoadFlag);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RingCollection.this, LinearLayoutManager.VERTICAL, false);
            mCollectionRecyclerViewRing.setLayoutManager(linearLayoutManager);
            ((DefaultItemAnimator) mCollectionRecyclerViewRing.getItemAnimator()).setSupportsChangeAnimations(false);
            mCollectionRecyclerViewRing.addItemDecoration(new DividerItemDecoration(RingCollection.this, DividerItemDecoration.VERTICAL));
            mCollectionRecyclerViewRing.setAdapter(adapter);
        }

        mCollectionRecyclerViewRing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager l = (LinearLayoutManager) mCollectionRecyclerViewRing.getLayoutManager();
                int firstVisibleItemPosition = l.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = l.findLastVisibleItemPosition();
                scollPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2;
            }
        });

        adapter.setOnItemClickListener(new RingNewsRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemclic(View view, int data) {
                flagList.set(data, 0);
                mediaPlayer.pause();
                for (int i = 0; i < imageList.size(); i++) {
                    imageList.get(i).image = R.drawable.play;
                }
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(RingCollection.this, RingDetailsActivity.class);
                intent.putExtra("RingUrl", (Serializable) setList);
                intent.putExtra("position", data);
                intent.putExtra("isContent", true);
                startActivity(intent);


            }


        });

    }

    private void initView() {
        mediaPlayer = new MediaPlayer();
        mCollectionBackRing = (ImageView) findViewById(R.id.Ring_Collection_back);
        mToobarRing = (Toolbar) findViewById(R.id.Ring_toobar);
        mCollectionRecyclerViewRing = (RecyclerView) findViewById(R.id.Ring_Collection_RecyclerView);
        setList = new ArrayList<>();
        initDataBase();
        initData();



        //返回按钮
        mCollectionBackRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        Cursor queryData = likeButton.getQueryData();
        while (queryData.moveToNext()) {
            //把URL存入set集合 防止重复
            RingNewsbean.RingtonesBean bean = new RingNewsbean.RingtonesBean();
            bean.setId(queryData.getString(queryData.getColumnIndex("RingId")));
            bean.setRingtone(queryData.getString(queryData.getColumnIndex("Ringtone")));
            bean.setCategory_name(queryData.getString(queryData.getColumnIndex("RingCategory_name")));
            bean.setTitle(queryData.getString(queryData.getColumnIndex("RingTitle")));
            bean.setAvg_rating(queryData.getString(queryData.getColumnIndex("Avg_rating")));
            setList.add(bean);


        }

        //去除list集合里重复的对象
        for (int i = 0; i < setList.size() - 1; i++) {
            for (int j = setList.size() - 1; j > i; j--) {
                if (setList.get(j).getId().equals(setList.get(i).getId())) {
                    setList.remove(j);
                }
            }
        }
    }

    private void initDataBase() {
        dataBaseTableNameList.add("RingId");
        dataBaseTableNameList.add("Ringtone");
        dataBaseTableNameList.add("RingCategory_name");
        dataBaseTableNameList.add("RingTitle");
        dataBaseTableNameList.add("Avg_rating");
        likeButton = new LikeButton(dataBaseTableNameList, "RingCollection.db", this, null, "myRingCollection", "Ringtone");
    }

    @Override
    protected void onDestroy() {
        //释放mediaplayer资源

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
            flagList.set(scollPosition, 0);
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        super.onDestroy();
    }
}
