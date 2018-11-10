package eli.tech.wallri.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import eli.tech.wallri.Adapter.SlideFragmentRecyclerViewAdapter;
import eli.tech.wallri.R;

/**
 * Created by 小雷 on 2018/3/29.
 */

public class SlideFragment extends BaseFragment {

    private RecyclerView mNaviFragmentRecyclerView;
    private List<Integer> list;
    private List<Integer> imageList;

    public void setSlideList(List<Integer> list, List<Integer> imageList) {
        this.imageList = imageList;
        this.list = list;
    }

    @Override
    protected void initView(View view) {
        mNaviFragmentRecyclerView = view.findViewById(R.id.NaviFragmentRecyclerView);
    }

    @Override
    protected void initData() {
        SlideFragmentRecyclerViewAdapter wallPaperNaviFragmentRecyclerViewAdapter = new SlideFragmentRecyclerViewAdapter(imageList, list, getActivity());
        mNaviFragmentRecyclerView.setLayoutManager(layoutManager);
        mNaviFragmentRecyclerView.setAdapter(wallPaperNaviFragmentRecyclerViewAdapter);
        wallPaperNaviFragmentRecyclerViewAdapter.setOnItemClickListener(new SlideFragmentRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemclic(View view, int data) {
                if (monItemClickListener != null) {
                    monItemClickListener.onItemclic(view, data);
                }

            }
        });
    }

    //点击事件recycleview点击事件的接口
    public interface onItemClickListener {
        /**
         * 抽象方法，当recycleview某个被点击的时候回调
         *
         * @param view 点击的item对象
         * @param data 点击时的数据
         */
        void onItemclic(View view, int data);

    }

    //创建接口
    private onItemClickListener monItemClickListener;

    //设置recycleview的某个监听
    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        monItemClickListener = onItemClickListener;

    }


}
