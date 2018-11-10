package eli.tech.wallri.Fragment.WallPaperFragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eli.tech.wallri.Adapter.WallPaper.WallpaperRecyclerViewAdapter;
import eli.tech.wallri.DataBean.CategoriesBean;
import eli.tech.wallri.DataBean.NewsBean;
import eli.tech.wallri.Fragment.BaseFragment;
import eli.tech.wallri.Utils.OKhttpManager;
import eli.tech.wallri.Utils.ToastUtils;

/**
 * Created by mac on 2018/3/30.
 */

public class WallpaperFragment extends BaseFragment {

    private int srcType = 2;
    private int offset = 60;
    private int count = 0;
    private String dataType = "";
    private List<NewsBean.DataBean> dataList = new ArrayList<>();
    private String message = "Next Page";
    private WallpaperRecyclerViewAdapter adapter;
    private Intent intent;


    private String nextPageURL(String url) {
        url = url + offset;
        return url;
    }


    //Type 2是图片数据
    public void getMethod() {
        this.message = "Next Page";
        request();
    }

    private void request() {
        if (PagingDataUrl != null && PagingDataJson != null) {
            OKhttpManager.getInstance().asyncJsonStringByURL(nextPageURL(PagingDataUrl), new OKhttpManager.Func1() {
                @Override
                public String onResponse(String result) {
                    if ("category" != dataType) {
                        NewsBean data = OKhttpManager.parsr(result, NewsBean.class);

                        for (int index = 0; index < data.getData().size(); index++) {
                            int type = data.getData().get(index).getType();
                            Random random = new Random();
                            data.getData().get(index).setAdRandomNum(random.nextDouble());
                            if (srcType == type || 0 == type) {
                                dataList.add(data.getData().get(index));
                            }
                        }

                        if (0 != count) {
                            finishLoadmore();
                        }
                        count++;
                        offset += 30;
                    } else {
                        Gson gson = new Gson();
                        CategoriesBean categoriesBean = gson.fromJson(result, CategoriesBean.class);
                        List<CategoriesBean.DataBean> category = categoriesBean.getData();
                        for (int i = 0; i < category.size(); i++) {
                            NewsBean.DataBean bean = new NewsBean.DataBean();
                            bean.setThumbnail_url(category.get(i).getThumbnail());
                            bean.setTitle(category.get(i).getCategory_name());
                            bean.setType(category.get(i).getCategory_id());
                            dataList.add(bean);
                        }
                    }

                    adapter.notifyDataSetChanged();
                    return null;
                }
            });
        }
    }

    private void finishLoadmore() {
        if (message.equals("Update")) {
            TwinklingRefreshLayout.finishRefreshing();
        } else {
            TwinklingRefreshLayout.finishLoadmore();
        }
        ToastUtils.showToast(getActivity(), message);
    }

    @Override
    protected RecyclerView.Adapter initAdapter() {
        adapter = new WallpaperRecyclerViewAdapter(context, dataList, feedLayout, feedLayoutId, feedType, feedLayoutViedoID, feedLayoutDownloadNumberId, isCategory, feedLayoutLike_ImageId, Category_BackGround, Category_Title);
        return adapter;
    }

    @Override
    protected void onClick() {
        adapter.setOnItemClickListener(new WallpaperRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemclic(View view, int position) {
                if (intent != null) {
                    String cateName = dataList.get(position).getTitle();
                    int cateId = dataList.get(position).getType();
                    intent.putExtra("data", (Serializable) dataList);
                    intent.putExtra("url", PagingDataUrl);
                    intent.putExtra("json", PagingDataJson);
                    intent.putExtra("position", position);
                    intent.putExtra("cateName", cateName);
                    intent.putExtra("cateId", cateId);
                    startActivity(intent);
                }
            }
        });
    }

    public List<NewsBean.DataBean> getList() {
        return dataList;
    }

    public void setList(List<NewsBean.DataBean> list) {
        this.dataList = list;
    }

    public void SetJumpTransferValue(Intent intent) {

        this.intent = intent;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setDataType(String str) {
        this.dataType = str;
    }

    @Override
    protected void isLike() {
        likeButton.getLike(getList().get(getPosition()).getPreview());
    }

    @Override
    public void DataRefresh() {
        dataList.clear();
        this.offset = 30;
        this.message = "Update";

        request();


    }
}
