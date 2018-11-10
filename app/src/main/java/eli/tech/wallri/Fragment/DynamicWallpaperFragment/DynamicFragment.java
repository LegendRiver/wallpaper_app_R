package eli.tech.wallri.Fragment.DynamicWallpaperFragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import eli.tech.wallri.Adapter.DynamicAdapter.DynamicRecyclerViewAdapter;
import eli.tech.wallri.DataBean.DynamicDataBean;
import eli.tech.wallri.Fragment.BaseFragment;
import eli.tech.wallri.Utils.OKhttpManager;
import eli.tech.wallri.Utils.ToastUtils;

/**
 * Created by mac on 2018/3/22.
 */

public class DynamicFragment extends BaseFragment {

    private List<DynamicDataBean.DataBean> list;
    private DynamicRecyclerViewAdapter adapter;
    private Intent intent;

    protected void postMethod() {
        Rerequest();
        TwinklingRefreshLayout.finishLoadmore();
        ToastUtils.showToast(getActivity(), "Next page!");
    }

    private void Rerequest() {
        if (PagingDataUrl != null && PagingDataJson != null) {
            String newJson = ProcessingJSON(PagingDataJson);
            OKhttpManager.getInstance().sendJsonPost(PagingDataUrl, newJson, new OKhttpManager.Func1() {

                @Override
                public String onResponse(String result) {
                    DynamicDataBean dynamicDataBean = OKhttpManager.parsr(result, DynamicDataBean.class);
                    for (int dataBeanSize = 0; dataBeanSize < dynamicDataBean.getData().size(); dataBeanSize++) {
                        if (!dynamicDataBean.getData().get(dataBeanSize).getCate().equals("ads")) {
                            list.add(dynamicDataBean.getData().get(dataBeanSize));
                        }
                    }
                    adapter.notifyDataSetChanged();

                    return null;
                }
            });
        }
    }

    @Override
    public void DataRefresh() {
        list.clear();
        Rerequest();
        TwinklingRefreshLayout.finishRefreshing();
        ToastUtils.showToast(getActivity(), "Update!");
    }

    @Override
    protected RecyclerView.Adapter initAdapter() {
        adapter = new DynamicRecyclerViewAdapter(context, list, feedLayout, feedLayoutId, feedLayoutDownloadNumberId, feedLayoutLike_ImageId, feedType, feedLayoutViedoID, isCategory, Category_BackGround, Category_Title);
        return adapter;
    }


    @Override
    protected void onClick() {
        adapter.setOnItemClickListener(new DynamicRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemclic(View view, int data) {
                if (intent != null) {
                    String cateName = list.get(data).getCate();
                    intent.putExtra("data", (Serializable) list);
                    intent.putExtra("url", PagingDataUrl);
                    intent.putExtra("json", PagingDataJson);
                    intent.putExtra("position", data);
                    intent.putExtra("cateName", cateName);
                    startActivity(intent);
                }
            }
        });
    }


    //查询数据库在滑动时判断数据库中是否有like的，如果有则把心变红

    public List<DynamicDataBean.DataBean> getList() {
        return list;
    }

    public void setList(List<DynamicDataBean.DataBean> list) {
        this.list = list;
    }

    //这个是处理第二次加lastkey字段的Json的方法
    private String ProcessingJSON(String PagingDataJson) {
        if (list.size() == 0) {
            return PagingDataJson + "\"}}";
        } else {
            PagingDataJson = PagingDataJson + list.get(list.size() - 1).getKey() + "\"}}";
            return PagingDataJson;
        }

    }

    public void SetJumpTransferValue(Intent intent) {
        this.intent = intent;
    }

    @Override
    protected void isLike() {
        likeButton.getLike(getList().get(getPosition()).getKey());
    }
}
