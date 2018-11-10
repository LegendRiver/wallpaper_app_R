package eli.tech.wallri.Adapter.Rings;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import eli.tech.wallri.DataBean.RingCategoriesBean;
import eli.tech.wallri.R;

import static eli.tech.wallri.System.Constant.RingBaseUrl;

/**
 * Created by 小雷 on 2018/1/26.
 * 铃声分类界面reyclerView适配器
 */

public class RingClassifyRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<RingCategoriesBean.CategoriesBean> list;
    private Context context;


    public RingClassifyRecyclerViewAdapter(List<RingCategoriesBean.CategoriesBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //加载布局。
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(context).inflate(R.layout.ringclassflyrecyclerviewitem, parent, false);
        viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    //赋值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final myViewHolder myViewHolder = (myViewHolder) holder;
        //设置字体
        //得到AssetManager
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/roboto_bold.ttf");
        myViewHolder.mRing_Classfly_Image.setImageURI(RingBaseUrl+"/icons/"+list.get(position).getIcon());
        myViewHolder.mRing_Classfly_Title.setText(list.get(position).getCategory_name() + "");
        myViewHolder.mRing_Classfly_Title.setTypeface(tf);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

     class myViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mRing_Classfly_Image;
        TextView mRing_Classfly_Title;

        public myViewHolder(View itemView) {
            super(itemView);
            mRing_Classfly_Image = itemView.findViewById(R.id.Ring_Classfly_Image);
            mRing_Classfly_Title = itemView.findViewById(R.id.Ring_Classfly_Title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (monItemClickListener != null) {
                        monItemClickListener.onItemclic(v, getLayoutPosition());

                    }
                }
            });
        }
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
    private  onItemClickListener monItemClickListener;

    //设置recycleview的某个监听
    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        monItemClickListener = onItemClickListener;

    }

}
