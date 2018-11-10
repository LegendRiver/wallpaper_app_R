package eli.tech.wallri.Adapter.WallPaper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.List;

import eli.tech.wallri.DataBean.NewsBean;
import eli.tech.wallri.R;

/**
 * Created by mac on 2018/3/30.
 */

public class WallpaperRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private Integer layout;
    private Integer imageLayout;
    private List<NewsBean.DataBean> list;
    private String type;
    private Integer feedLayoutViedoID;
    private Integer mDownload_Number;
    private boolean isCategory;
    private Integer Like_Heart;
    private Integer Category_BackGround = R.id.Wallpaper_Category_BackGround;

    private Integer Category_Title = R.id.Wallpaper_Category_Title;


    public WallpaperRecyclerViewAdapter(Context context,
                                        List<NewsBean.DataBean> list,
                                        Integer layout,
                                        Integer imageLayout,
                                        String type,
                                        Integer feedLayoutViedoID,
                                        Integer Download_Number,
                                        boolean isCategory,
                                        Integer mLike_Heart,
                                        Integer mCategory_BackGround,
                                        Integer mCategory_Title
    ) {
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.imageLayout = imageLayout;
        this.type = type;
        this.feedLayoutViedoID = feedLayoutViedoID;
        this.mDownload_Number = Download_Number;
        this.isCategory = isCategory;
        this.Like_Heart = mLike_Heart;
        this.Category_BackGround = mCategory_BackGround;
        this.Category_Title = mCategory_Title;
    }

    @Override
    public int getItemViewType(int position) {
        switch (type) {
            case "image":
                return 0;

            case "orientationImage":
                return 1;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case 0:
                View imageView = LayoutInflater.from(context).inflate(layout, parent, false);
                ImageViewHolder imageViewHolder = new ImageViewHolder(imageView);
                return imageViewHolder;

            case 1:
                View videoView = LayoutInflater.from(context).inflate(layout, parent, false);
                orientationImageViewHolder videoViewHolder = new orientationImageViewHolder(videoView);
                return videoViewHolder;

        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case 0:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.imageView.setImageURI(list.get(position).getThumbnail_url());

                if (isCategory == true) {
                    imageViewHolder.Download_Number.setVisibility(View.GONE);
                    imageViewHolder.mLike_Heart.setVisibility(View.GONE);
                    imageViewHolder.mCategory_BackGround.setBackgroundColor(Color.parseColor("#30000000"));
                    imageViewHolder.mCategory_Title.setText(list.get(position).getTitle());
                } else {
                    if (list.get(position).getDownload_count() == 0) {
                        imageViewHolder.Download_Number.setText(0 + "");
                    } else {
                        imageViewHolder.Download_Number.setText(list.get(position).getDownload_count() + "");
                    }
                    imageViewHolder.mCategory_BackGround.setVisibility(View.GONE);
                }


                break;

            case 1:
                orientationImageViewHolder orientationImageViewHolder = (orientationImageViewHolder) holder;
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setLowResImageRequest(ImageRequest.fromUri(list.get(position).getThumbnail_url()))
                        .setImageRequest(ImageRequest.fromUri(list.get(position).getPreview()))
                        .setOldController(orientationImageViewHolder.Wallpaper_Leave_Image.getController())
                        .build();
                orientationImageViewHolder.Wallpaper_Leave_Image.setController(controller);
                break;
        }
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageView;
        private TextView Download_Number;
        private ImageView mLike_Heart;
        private RelativeLayout mCategory_BackGround;
        private TextView mCategory_Title;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(imageLayout);
            Download_Number = itemView.findViewById(mDownload_Number);
            mLike_Heart = itemView.findViewById(Like_Heart);
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

    class orientationImageViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView Wallpaper_Leave_Image;

        public orientationImageViewHolder(View itemView) {
            super(itemView);
            Wallpaper_Leave_Image = itemView.findViewById(feedLayoutViedoID);
        }
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
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
}
