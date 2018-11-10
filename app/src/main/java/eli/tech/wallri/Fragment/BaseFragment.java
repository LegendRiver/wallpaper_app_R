package eli.tech.wallri.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import eli.tech.wallri.Adapter.RecyclerViewItemDecoration;
import eli.tech.wallri.Like.LikeButton;
import eli.tech.wallri.R;

/**
 * Created by mac on 2018/3/22.
 */

public class BaseFragment extends Fragment {

    private RecyclerViewItemDecoration decoration;

    protected Integer layout;
    protected Context context;
    protected RecyclerView recyclerView;
    protected com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout TwinklingRefreshLayout;
    //private List<DynamicDataBean.DataBean> list;
    protected Integer recyclerViewId;
    protected Integer dynamicRefreshId;
    protected Integer feedLayout;
    protected Integer feedLayoutId;
    protected Integer feedLayoutViedoID;
    protected Integer feedLayoutDownloadNumberId;
    protected Integer feedLayoutLike_ImageId;
    protected RecyclerView.LayoutManager layoutManager;
    protected String PagingDataUrl;
    protected String PagingDataJson;
    protected String type;
    protected String feedType;
    protected RecyclerView.Adapter adapter;
    protected boolean isCategory;
    protected int position = 0;
    protected LikeButton likeButton;
    boolean isSlidingToLast = false;
    private int viewPaperpPosition;
    protected Integer Category_BackGround;
    protected Integer Category_Title;
    private RelativeLayout mTheTopButton;
    private SimpleDraweeView mTheTopButtonImage;


    public void setData(Context context,
                        boolean isCategory,
                        Integer layout,
                        Integer recyclerViewId,
                        Integer dynamicRefreshId,
                        Integer feedLayout,
                        Integer feedLayoutId,
                        Integer feedLayoutDownloadNumberId,
                        Integer feedLayoutLike_ImageId,
                        Integer feedLayoutViedoID,
                        Integer Category_BackGround,
                        Integer Category_Title,
                        String feedType,
                        RecyclerView.LayoutManager layoutManager,
                        String type,
                        RecyclerViewItemDecoration decoration) {
        this.isCategory = isCategory;
        this.layout = layout;
        this.recyclerViewId = recyclerViewId;
        this.layoutManager = layoutManager;
        this.feedLayout = feedLayout;
        this.feedLayoutId = feedLayoutId;
        this.feedLayoutDownloadNumberId = feedLayoutDownloadNumberId;
        this.feedLayoutLike_ImageId = feedLayoutLike_ImageId;
        this.context = context;
        this.dynamicRefreshId = dynamicRefreshId;
        this.type = type;
        this.decoration = decoration;
        this.feedType = feedType;
        this.feedLayoutViedoID = feedLayoutViedoID;
        this.Category_BackGround = Category_BackGround;
        this.Category_Title = Category_Title;
    }

    public void SettingPagingData(String PagingDataUrl, String PagingDataJson) {

        this.PagingDataJson = PagingDataJson;
        this.PagingDataUrl = PagingDataUrl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(layout, container, false);
        initView(view);
        initData();
        return view;
    }

    protected void initView(View view) {


        recyclerView = view.findViewById(recyclerViewId);
        TwinklingRefreshLayout = view.findViewById(dynamicRefreshId);

        adapter = initAdapter();

        if (PagingDataUrl == null && PagingDataJson == null) {
            TwinklingRefreshLayout.setEnableLoadmore(false);
            TwinklingRefreshLayout.setEnableRefresh(false);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(adapter);


        //判断是水平分页还是垂直分页
        if (recyclerView.getLayoutManager().canScrollHorizontally() == true) {
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            TwinklingRefreshLayout.setEnableLoadmore(false);
            TwinklingRefreshLayout.setEnableRefresh(false);
            HorizontalPagingLoading();


        } else {
            if(isCategory==true){
                TwinklingRefreshLayout.setEnableLoadmore(false);
                TwinklingRefreshLayout.setEnableRefresh(false);
            }
            mTheTopButton = view.findViewById(R.id.TheTopButton);
            mTheTopButton.getBackground().setAlpha(50);
            mTheTopButtonImage = view.findViewById(R.id.TheTopButtonImage);
            mTheTopButtonImage.setImageURI("res://eli.tech.wallri/" + R.drawable.up_arrow);
            verticalPagingLoading();
            mTheTopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerView.scrollToPosition(0);
                }
            });
        }

        onClick();
    }

    public void setToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }


    protected void HorizontalPagingLoading() {
        //RecyclerView滚动监听
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager l = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = l.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = l.findLastVisibleItemPosition();
                viewPaperpPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2;
                if (viewPaperpPosition == adapter.getItemCount() - 1) {
                    DataLoadMore(type);
                }
                if (dx > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (PagingDataUrl != null && PagingDataJson != null) {

                    isLike();
                    // 当停止滑动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                        //获取最后一个完全显示的ItemPosition ,角标值
                        int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                        //所有条目,数量值
                        int totalItemCount = manager.getItemCount();

                        // 判断是否滚动到底部，并且是向右滚动
                        if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                            //加载更多功能的代码
                            //水平分页
                            DataLoadMore(type);
                        }
                    }
                }

            }
        });
    }

    protected void isLike() {


    }

    public void setButton(LikeButton likeButton) {
        this.likeButton = likeButton;
    }

    public int getPosition() {
        return viewPaperpPosition;
    }


    protected void initData() {
        setToPosition(position);

    }

    protected void onClick() {

    }


    protected RecyclerView.Adapter initAdapter() {
        return adapter;
    }

    //垂直分页
    private void verticalPagingLoading() {
        TwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                DataRefresh();
                super.onRefresh(refreshLayout);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                DataLoadMore(type);
                super.onLoadMore(refreshLayout);
            }
        });
    }


    //分页
    protected void DataLoadMore(String type) {

        switch (type) {
            case "post":
                postMethod();
                break;
            case "get":
                getMethod();
                break;
            case "others":
                break;
        }
    }

    protected void postMethod() {
    }

    protected void getMethod() {

    }

    public void setPosition(int position) {
        this.position = position;
    }


    //下拉刷新
    public void DataRefresh() {

    }


}
