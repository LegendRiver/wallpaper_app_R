package eli.tech.wallri.Fragment.RingFragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Activity.RingActivity.RingActivity;
import eli.tech.wallri.Activity.RingActivity.RingHomeActrivity;
import eli.tech.wallri.Adapter.Rings.RingClassifyRecyclerViewAdapter;
import eli.tech.wallri.DataBean.MessageEvent;
import eli.tech.wallri.DataBean.RingCategoriesBean;
import eli.tech.wallri.R;
import eli.tech.wallri.System.Constant;
import eli.tech.wallri.Utils.OKhttpManager;
import eli.tech.wallri.Utils.Utils;

/**
 * Created by 小雷 on 2018/1/25.
 * 铃声分类fragment
 */

public class RingClassifyFragment extends Fragment {

    //recyclerView
    private RecyclerView mClassifyRecyclerViewRing;
    //Recyclerview刷新的控件
    private TwinklingRefreshLayout mClassflyRefreshRing;
    private String url;
    private List<RingCategoriesBean.CategoriesBean> list;
    private RingClassifyRecyclerViewAdapter adapter;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ringclassifyfragment, container, false);
        initView(view);

        OKhttpManager.getInstance().asyncJsonStringByURL(url, new OKhttpManager.Func1() {
            @Override
            public String onResponse(String result) {

                list = new ArrayList<>();
                Gson gson = new Gson();
                RingCategoriesBean ringCategoriesBean = gson.fromJson(result, RingCategoriesBean.class);
                list = ringCategoriesBean.getCategories();
                adapter = new RingClassifyRecyclerViewAdapter(list, getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mClassifyRecyclerViewRing.setLayoutManager(linearLayoutManager);
                mClassifyRecyclerViewRing.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                mClassifyRecyclerViewRing.setAdapter(adapter);
                mClassflyRefreshRing.setOnRefreshListener(new RefreshListenerAdapter() {
                    @Override
                    public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                        super.onRefresh(refreshLayout);
                        Refresh();
                    }
                });


                adapter.setOnItemClickListener(new RingClassifyRecyclerViewAdapter.onItemClickListener() {
                    @Override
                    public void onItemclic(View view, int data) {
                        Constant.isOut=true;
                        mediaPlayer.reset();
                        mediaPlayer.stop();
                        EventBus.getDefault().post(new MessageEvent(361));
                        Intent intent = new Intent(getActivity(), RingHomeActrivity.class);
                        intent.putExtra("Category_name", list.get(data).getCategory_name());
                        startActivity(intent);



                    }
                });


                return null;
            }
        });


        return view;
    }


    //初始化
    private void initView(View view) {
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.Ring_ToolbarContainer);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mClassflyRefreshRing.setEnableRefresh(true);
                    mClassflyRefreshRing.setEnableOverScroll(false);
                } else {
                    mClassflyRefreshRing.setEnableRefresh(false);
                    mClassflyRefreshRing.setEnableOverScroll(false);
                }

            }
        });
        RingActivity activity = (RingActivity) (getActivity());
        mediaPlayer = activity.getMediaPlayer();
        url = "http://rington.co/android/picyap-rw/ringtones/cat_list.php?loc=en";
        mClassifyRecyclerViewRing = (RecyclerView) view.findViewById(R.id.Ring_ClassifyRecyclerView);
        mClassflyRefreshRing = (TwinklingRefreshLayout) view.findViewById(R.id.Ring_Classfly_Refresh);
        mClassflyRefreshRing.setEnableLoadmore(false);
    }

    /**
     * 刷新数据
     */
    private void Refresh() {
        //判断当前网络
        boolean networkAvailable = Utils.isNetworkAvailable(getActivity());
        if (networkAvailable) {
            OKhttpManager.getInstance().asyncJsonStringByURL(url, new OKhttpManager.Func1() {
                @Override
                public String onResponse(String result) {
                    Gson gson = new Gson();
                    RingCategoriesBean ringCategoriesBean = gson.fromJson(result, RingCategoriesBean.class);
                    List<RingCategoriesBean.CategoriesBean> categories = ringCategoriesBean.getCategories();
                    list.clear();
                    for (int i = 0; i < categories.size(); i++) {
                        RingCategoriesBean.CategoriesBean bean = new RingCategoriesBean.CategoriesBean();
                        bean.setIcon(categories.get(i).getIcon());
                        bean.setCategory_name(categories.get(i).getCategory_name());
                        list.add(bean);

                    }

                    adapter.notifyDataSetChanged();
                    mClassflyRefreshRing.finishRefreshing();
                    return result;
                }
            });

        } else {

            Toast.makeText(getActivity(), "The current network is not available, please check the network connection!", Toast.LENGTH_LONG).show();
            //  mNewestFragmentRecyclerView.setPullLoadMoreCompleted();
            mClassflyRefreshRing.finishRefreshing();

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
