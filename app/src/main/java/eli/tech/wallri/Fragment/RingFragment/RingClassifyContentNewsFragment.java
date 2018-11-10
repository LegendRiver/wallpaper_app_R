package eli.tech.wallri.Fragment.RingFragment;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eli.tech.wallri.Activity.RingActivity.RingDetailsActivity;
import eli.tech.wallri.Activity.RingActivity.RingHomeActrivity;
import eli.tech.wallri.AdManager.Facebook.FacebookAdManager;
import eli.tech.wallri.Adapter.Rings.RingContentNewsRecyclerViewAdapter;
import eli.tech.wallri.DataBean.ProgressBean;
import eli.tech.wallri.DataBean.MessageEvent;
import eli.tech.wallri.DataBean.RingNewsbean;
import eli.tech.wallri.R;
import eli.tech.wallri.System.Constant;
import eli.tech.wallri.Utils.OKhttpManager;
import eli.tech.wallri.Utils.ToastUtils;
import eli.tech.wallri.Utils.Utils;

/**
 * Created by 小雷 on 2018/1/30.
 */

public class RingClassifyContentNewsFragment extends Fragment {

    private RecyclerView mNewsRecyclerViewRing;
    private TwinklingRefreshLayout mNewsRefreshRing;
    private String ratingUrl;
    private String category_name;
    private List<Integer> flagList;
    private List<ProgressBean> imageList;
    private RingContentNewsRecyclerViewAdapter adapter;
    private List<RingNewsbean.RingtonesBean> list;
    private int n = 20;
    private int s = 0;
    private MediaPlayer mediaPlayer;
    private List<Boolean> isplayingList = new ArrayList<>();
    private List<Integer> downLoadFlag;
    private List<Integer> addFlagList = new ArrayList<>();
    private Map<Integer, NativeAd> mRingrAdsList;
    private int frequency = 0;
    private NativeAdsManager nativeAd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ringnewsfragment, container, false);
        initView(view);


        OKhttpManager.getInstance().asyncJsonStringByURL(ratingUrl, new OKhttpManager.Func1() {
            @Override
            public String onResponse(String result) {
                mRingrAdsList = new HashMap<>();
                flagList = new ArrayList<>();
                imageList = new ArrayList<>();
                list = new ArrayList<>();
                downLoadFlag = new ArrayList<>();
                Gson gson = new Gson();
                RingNewsbean ringNewSben = gson.fromJson(result, RingNewsbean.class);
                list = ringNewSben.getRingtones();
                for (int i = 0; i < list.size(); i++) {
                    isplayingList.add(false);
                    flagList.add(0);
                    downLoadFlag.add(0);
                    ProgressBean bean = new ProgressBean();
                    bean.image = R.drawable.play;
                    bean.progress = 0;
                    bean.max = 10000000;
                    bean.BufferProgressBar = 0;
                    imageList.add(bean);
                }
                initAdsData();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mNewsRecyclerViewRing.setLayoutManager(linearLayoutManager);
                ((DefaultItemAnimator) mNewsRecyclerViewRing.getItemAnimator()).setSupportsChangeAnimations(false);
                mNewsRecyclerViewRing.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                adapter = new RingContentNewsRecyclerViewAdapter(mRingrAdsList, imageList, list, getActivity(), flagList, mediaPlayer, isplayingList, downLoadFlag);
                mNewsRecyclerViewRing.setAdapter(adapter);
                setRingFeedNativeAds();
                nativeAd.loadAds();
                //条目点击事件
                adapter.setOnItemClickListener(new RingContentNewsRecyclerViewAdapter.onItemClickListener() {
                    @Override
                    public void onItemclic(View view, int data) {
                        flagList.set(data, 0);
                        mediaPlayer.pause();
                        for (int i = 0; i < imageList.size(); i++) {
                            imageList.get(i).image = R.drawable.play;
                        }
                        adapter.notifyDataSetChanged();
                        Intent intent = new Intent(getActivity(), RingDetailsActivity.class);
                        intent.putExtra("RingUrl", (Serializable) list);
                        intent.putExtra("position", data);
                        intent.putExtra("category_name", category_name);
                        intent.putExtra("sort", "date");
                        startActivity(intent);
                    }
                });
                //上拉加载，下拉刷新
                mNewsRefreshRing.setOnRefreshListener(new RefreshListenerAdapter() {
                    @Override
                    public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                        super.onRefresh(refreshLayout);
                        Refresh();
                    }

                    @Override
                    public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                        super.onLoadMore(refreshLayout);
                        n = n + 20;
                        s = s + 20;
                        String mLoadMoreUrl = "http://rington.co/android/picyap-rw/ringtones/list.php?v=" + category_name + "&loc=zh&s=" + s + "&n=" + n + "&sort=date";
                        OKhttpManager.getInstance().asyncJsonStringByURL(mLoadMoreUrl, new OKhttpManager.Func1() {
                            @Override
                            public String onResponse(String result) {
                                int page = 0;
                                Gson gson = new Gson();
                                RingNewsbean ringNewsbean1 = gson.fromJson(result, RingNewsbean.class);
                                List<RingNewsbean.RingtonesBean> ringtones = ringNewsbean1.getRingtones();
                                if (!ringtones.isEmpty()) {
                                    addFlagList.clear();
                                    for (int i = 0; i < ringtones.size(); i++) {

                                        RingNewsbean.RingtonesBean bean = new RingNewsbean.RingtonesBean();
                                        ProgressBean imagebean = new ProgressBean();
                                        bean.setTitle(ringtones.get(i).getTitle());
                                        bean.setCategory_name(ringtones.get(i).getCategory_name());
                                        bean.setAvg_rating(ringtones.get(i).getAvg_rating());
                                        bean.setId(ringtones.get(i).getId());
                                        bean.setRingtone(ringtones.get(i).getRingtone());
                                        imagebean.image = R.drawable.play;
                                        imagebean.progress = 0;
                                        imagebean.BufferProgressBar = 0;
                                        imagebean.max = 10000000;
                                        flagList.add(0);
                                        imageList.add(imagebean);
                                        list.add(bean);
                                        downLoadFlag.add(0);
                                        isplayingList.add(false);
                                        page = page + 1;
                                    }
                                    for (int j = 0; j < page / 5; j++) {
                                        frequency = frequency + 6;
                                        isplayingList.add(frequency, false);
                                        flagList.add(frequency, 0);
                                        downLoadFlag.add(frequency, 0);
                                        ProgressBean bean = new ProgressBean();
                                        bean.image = R.drawable.play;
                                        bean.progress = 0;
                                        bean.max = 10000000;
                                        bean.BufferProgressBar = 0;
                                        imageList.add(frequency, bean);
                                        RingNewsbean.RingtonesBean ringtonesBean = new RingNewsbean.RingtonesBean();
                                        ringtonesBean.setType(1);
                                        list.add(frequency, ringtonesBean);
                                        addFlagList.add(frequency);

                                    }
                                    adapter.notifyDataSetChanged();
                                    mNewsRefreshRing.finishLoadmore();
                                    nativeAd.loadAds();
                                } else {

                                    ToastUtils.showToast(getActivity(), "End");
                                    mNewsRefreshRing.finishLoadmore();


                                }


                                return null;
                            }
                        });


                    }
                });

                //设置滑动的事件
                mNewsRecyclerViewRing.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    int mScrollThreshold;

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                        if (isSignificantDelta) {
                            if (dy > 0) {
                                //上滑动
                                EventBus.getDefault().post(new MessageEvent(401));
                            } else if (dy < 0) {
                                if (firstItemPosition == 0) {
                                    EventBus.getDefault().post(new MessageEvent(401));
                                } else {
                                    //下滑动
                                    EventBus.getDefault().post(new MessageEvent(402));
                                }


                            }
                        }
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                });
                return null;
            }
        });


        return view;
    }

    private void initAdsData() {

        for (int j = 0; j < list.size() / 5; j++) {
            if (j == 0) {
                frequency = frequency + 5;
                isplayingList.add(frequency, false);
                flagList.add(frequency, 0);
                downLoadFlag.add(frequency, 0);
                ProgressBean bean = new ProgressBean();
                bean.image = R.drawable.play;
                bean.progress = 0;
                bean.max = 10000000;
                bean.BufferProgressBar = 0;
                imageList.add(frequency, bean);
                RingNewsbean.RingtonesBean ringtonesBean = new RingNewsbean.RingtonesBean();
                ringtonesBean.setType(1);
                list.add(frequency, ringtonesBean);
                addFlagList.add(frequency);
            } else {
                frequency = frequency + 6;
                isplayingList.add(frequency, false);
                flagList.add(frequency, 0);
                downLoadFlag.add(frequency, 0);
                ProgressBean bean = new ProgressBean();
                bean.image = R.drawable.play;
                bean.progress = 0;
                bean.max = 10000000;
                bean.BufferProgressBar = 0;
                imageList.add(frequency, bean);
                RingNewsbean.RingtonesBean ringtonesBean = new RingNewsbean.RingtonesBean();
                ringtonesBean.setType(1);
                list.add(frequency, ringtonesBean);
                addFlagList.add(frequency);
            }
        }

    }

    //用eventBus获取改变语言和recyclerView滑动的位置
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {

        try {


            if (messageEvent.getMessage() == 409) {
                EventBus.getDefault().post(new MessageEvent(401));
                mNewsRecyclerViewRing.scrollToPosition(0);
                Toast.makeText(getActivity(), R.string.toast, Toast.LENGTH_LONG).show();
            } else if (messageEvent.getMessage() == 1110) {


                for (int i = 0; i < list.size(); i++) {
                    imageList.get(i).image = R.drawable.play;
                    imageList.get(i).max = 10000000;
                    imageList.get(i).progress = 0;
                    downLoadFlag.set(i, 0);
                    flagList.set(i, 0);
                    isplayingList.set(i, false);
                }
                adapter.notifyDataSetChanged();
            } else if (messageEvent.getMessage() == 2220) {
                for (int i = 0; i < list.size(); i++) {
                    imageList.get(i).image = R.drawable.play;
                    imageList.get(i).max = 10000000;
                    imageList.get(i).progress = 0;
                    downLoadFlag.set(i, 0);
                    flagList.set(i, 0);
                    isplayingList.set(i, false);
                }
                adapter.notifyDataSetChanged();

            }

        } catch (Exception e) {
            System.out.println("not create");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        category_name = ((RingHomeActrivity) context).getCategory_name();

    }

    private void initView(View itemView) {
        EventBus.getDefault().register(this);
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.Ring_ToolbarContainer);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mNewsRefreshRing.setEnableRefresh(true);
                    mNewsRefreshRing.setEnableOverScroll(false);
                } else {
                    mNewsRefreshRing.setEnableRefresh(false);
                    mNewsRefreshRing.setEnableOverScroll(false);
                }

            }
        });
        RingHomeActrivity activity = (RingHomeActrivity) (getActivity());
        mediaPlayer = activity.getMediaPlayer();
        ratingUrl = "http://rington.co/android/picyap-rw/ringtones/list.php?v=" + category_name + "&loc=zh&s=0&n=20&sort=date";
        mNewsRecyclerViewRing = (RecyclerView) itemView.findViewById(R.id.Ring_News_RecyclerView);
        mNewsRefreshRing = (TwinklingRefreshLayout) itemView.findViewById(R.id.Ring_News_Refresh);
    }

    /**
     * 刷新数据
     */
    private void Refresh() {
        //判断当前网络
        boolean networkAvailable = Utils.isNetworkAvailable(getActivity());
        if (networkAvailable) {
            OKhttpManager.getInstance().asyncJsonStringByURL(ratingUrl, new OKhttpManager.Func1() {
                @Override
                public String onResponse(String result) {
                    mediaPlayer.reset();
                    Gson gson = new Gson();
                    RingNewsbean ringNewSben = gson.fromJson(result, RingNewsbean.class);
                    List<RingNewsbean.RingtonesBean> ringtones = ringNewSben.getRingtones();
                    list.clear();
                    imageList.clear();
                    flagList.clear();
                    isplayingList.clear();
                    addFlagList.clear();
                    frequency = 0;
                    mRingrAdsList.clear();
                    for (int i = 0; i < ringtones.size(); i++) {
                        RingNewsbean.RingtonesBean bean = new RingNewsbean.RingtonesBean();
                        ProgressBean imagebean = new ProgressBean();
                        bean.setTitle(ringtones.get(i).getTitle());
                        bean.setCategory_name(ringtones.get(i).getCategory_name());
                        bean.setAvg_rating(ringtones.get(i).getAvg_rating());
                        bean.setId(ringtones.get(i).getId());
                        bean.setRingtone(ringtones.get(i).getRingtone());
                        imagebean.image = R.drawable.play;
                        imagebean.progress = 0;
                        imagebean.BufferProgressBar = 0;
                        imagebean.max = 10000000;
                        flagList.add(0);
                        imageList.add(imagebean);
                        list.add(bean);
                        isplayingList.add(false);
                    }
                    initAdsData();

                    adapter.notifyDataSetChanged();
                    mNewsRefreshRing.finishRefreshing();
                    nativeAd.loadAds();
                    return result;
                }
            });

        } else {

            Toast.makeText(getActivity(), "The current network is not available, please check the network connection!", Toast.LENGTH_LONG).show();
            //  mNewestFragmentRecyclerView.setPullLoadMoreCompleted();
            mNewsRefreshRing.finishRefreshing();

        }


    }

    @Override
    public void onDestroy() {
        Constant.isOut = true;
        Constant.isPlaying = true;
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer = null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    private void setRingFeedNativeAds() {
        nativeAd = new NativeAdsManager(getActivity(), FacebookAdManager.RingCategoryNew_10, 5);
        nativeAd.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                for (int i = 0; i < addFlagList.size(); i++) {
                    mRingrAdsList.put(addFlagList.get(i), nativeAd.nextNativeAd());
                }
                ((SimpleItemAnimator) mNewsRecyclerViewRing.getItemAnimator()).setSupportsChangeAnimations(false);
                adapter.notifyItemRangeChanged(0, list.size());
            }

            @Override
            public void onAdError(AdError adError) {
                ToastUtils.showErrorToast(getActivity(), adError.getErrorMessage());
            }

        });


    }
}
