package eli.tech.wallri.Adapter.DynamicAdapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import eli.tech.wallri.DataBean.DynamicDataBean;
import eli.tech.wallri.R;
import eli.tech.wallri.view.FrescoBlurDraweeView;


/**
 * Created by 小雷 on 2018/3/20.
 */

public class DynamicRecyclerViewAdapter extends RecyclerView.Adapter {


    private Context context;
    private Integer layout;
    private Integer imageLayout;
    private List<DynamicDataBean.DataBean> list;
    private String type;
    private Integer feedLayoutViedoID;
    private Integer feedLayoutDownloadNumberId;
    private Integer feedLayoutLike_ImageId;
    private boolean isCategory;
    private Integer Category_BackGround = R.id.Wallpaper_Category_BackGround;
    private Integer Category_Title = R.id.Wallpaper_Category_Title;


    public DynamicRecyclerViewAdapter(Context context,
                                      List<DynamicDataBean.DataBean> list,
                                      Integer layout, Integer imageLayout,
                                      Integer feedLayoutDownloadNumberId,
                                      Integer feedLayoutLike_ImageId,
                                      String type,
                                      Integer feedLayoutViedoID,
                                      boolean isCategory,
                                      Integer mCategory_BackGround,
                                      Integer mCategory_Title
    ) {
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.imageLayout = imageLayout;
        this.type = type;
        this.feedLayoutViedoID = feedLayoutViedoID;
        this.feedLayoutDownloadNumberId = feedLayoutDownloadNumberId;
        this.feedLayoutLike_ImageId = feedLayoutLike_ImageId;
        this.Category_BackGround = mCategory_BackGround;
        this.Category_Title = mCategory_Title;
        this.isCategory=isCategory;
    }

    @Override
    public int getItemViewType(int position) {
        switch (type) {
            case "image":
                return 0;

            case "video":
                return 1;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case 0:
                View imageView = LayoutInflater.from(context).inflate(layout, parent, false);
                imageViewHolder imageViewHolder = new imageViewHolder(imageView);
                return imageViewHolder;

            case 1:
                View videoView = LayoutInflater.from(context).inflate(layout, parent, false);
                videoViewHolder videoViewHolder = new videoViewHolder(videoView);
                return videoViewHolder;

        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case 0:
                imageViewHolder imageViewHolder = (imageViewHolder) holder;
                if(isCategory==true){
                    imageViewHolder.mCategory_BackGround.setBackgroundColor(Color.parseColor("#30000000"));
                    imageViewHolder.mCategory_Title.setText(list.get(position).getCate());
                }
                imageViewHolder.feedLayoutLike_Image.setVisibility(View.GONE);
                imageViewHolder.feedLayoutDownloadNumber.setVisibility(View.GONE);
                imageViewHolder.imageView.setImageURI(list.get(position).getThumb());

                break;

            case 1:
                videoViewHolder videoViewHolder = (videoViewHolder) holder;
                final VideoView mVideoView = videoViewHolder.mVideoView;
                final FrescoBlurDraweeView imageView = videoViewHolder.imageView;
                imageView.loadImageUrlWithBlur(list.get(position).getThumb());
                imageView.setAlpha(255);
                mVideoView.setVideoURI(Uri.parse(list.get(position).getPreview()));
                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setVolume(0, 0);
                        mp.setLooping(true);
                        mp.start();
                        mVideoView.requestFocus();
                        startPropertyAnim(imageView, 2000, 1f, 1f, 0f);

                    }
                });
                break;
        }


    }


    class imageViewHolder extends RecyclerView.ViewHolder {


        private SimpleDraweeView imageView;
        private TextView feedLayoutDownloadNumber;
        private ImageView feedLayoutLike_Image;
        private RelativeLayout mCategory_BackGround;
        private TextView mCategory_Title;

        public imageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(imageLayout);
            feedLayoutDownloadNumber = itemView.findViewById(feedLayoutDownloadNumberId);
            feedLayoutLike_Image = itemView.findViewById(feedLayoutLike_ImageId);
            mCategory_BackGround = itemView.findViewById(Category_BackGround);
            mCategory_Title = itemView.findViewById(Category_Title);
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

    class videoViewHolder extends RecyclerView.ViewHolder {
        public FrescoBlurDraweeView imageView;
        public VideoView mVideoView;

        public FrameLayout frameLayout;


        public videoViewHolder(View itemView) {
            super(itemView);
            mVideoView = itemView.findViewById(feedLayoutViedoID);
            imageView = itemView.findViewById(imageLayout);
            frameLayout = itemView.findViewById(R.id.FrameLayout);

        }


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {

        int itemType = getItemViewType(holder.getLayoutPosition());
        switch (itemType) {
            case 1:
                videoViewHolder videoViewHolder = (videoViewHolder) holder;
                videoViewHolder.imageView.loadImageUrlWithBlur(list.get(holder.getLayoutPosition()).getThumb());
                startPropertyAnim(videoViewHolder.imageView, 1, 1f, 1f, 1f);
                break;

        }

        super.onViewAttachedToWindow(holder);
    }


    @Override
    public int getItemCount() {
        return list.size();
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


    // 核心关键点，此处将实现属性动画的动画实际执行
    private void startPropertyAnim(View view, int duration, float value1, float value2, float value3) {
        // 将直接把TextView这个view对象的透明度渐变。
        // 注意第二个参数："alpha"，指明了是透明度渐变属性动画
        // 透明度变化从1—>0.1—>1—>0.5—>1，TextView对象经历4次透明度渐变
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", value1, value2, value3);

        anim.setDuration(duration);// 动画持续时间

        // 这里是一个回调监听，获取属性动画在执行期间的具体值
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

        anim.start();
    }


}
