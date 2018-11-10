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

import eli.tech.wallri.R;

/**
 * date 2017/12/25
 * author:张学雷(Administrator)
 * functinn:
 */

public class RingNaviFragmentRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Integer> list;
    private List<Integer> listImage;
    private Context context;


    public RingNaviFragmentRecyclerViewAdapter(List<Integer> listImage, List<Integer> list, Context context) {
        this.list = list;
        this.context = context;
        this.listImage = listImage;
    }

    //加载布局。
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(context).inflate(R.layout.naviringfragmentitem, parent, false);
        viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    //赋值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //        name, occupation, age, introduction;
        final myViewHolder myViewHolder = (myViewHolder) holder;
        myViewHolder.mNvifragment_Text.setText(list.get(position));

        //设置字体
        //得到AssetManager
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/roboto_bold.ttf");
        myViewHolder.mNvifragment_Text.setTypeface(tf);
        myViewHolder.mNavifragment_Image.setImageURI("res:///" + listImage.get(position));


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView mNvifragment_Text;
        SimpleDraweeView mNavifragment_Image;

        public myViewHolder(View itemView) {
            super(itemView);
            mNvifragment_Text = itemView.findViewById(R.id.Ring_navifragment_Text);
            mNavifragment_Image = itemView.findViewById(R.id.Ring_navifragment_Image);
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
    public   onItemClickListener monItemClickListener;

    //设置recycleview的某个监听
    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        monItemClickListener = onItemClickListener;

    }


}
