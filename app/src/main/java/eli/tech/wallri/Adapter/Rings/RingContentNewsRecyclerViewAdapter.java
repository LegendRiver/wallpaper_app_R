package eli.tech.wallri.Adapter.Rings;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xlhratingbar_lib.XLHRatingBar;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.NativeAd;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eli.tech.wallri.DataBean.ProgressBean;
import eli.tech.wallri.DataBean.MessageEvent;
import eli.tech.wallri.DataBean.RingNewsbean;
import eli.tech.wallri.R;
import eli.tech.wallri.System.Constant;
import eli.tech.wallri.Utils.DownloadUtil;
import eli.tech.wallri.Utils.DpOrPxUtils;

import static eli.tech.wallri.System.Constant.RingBaseUrl;


/**
 * Created by 小雷 on 2018/1/26.
 * 铃声分类界面reyclerView适配器
 */

public class RingContentNewsRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<ProgressBean> imageList;
    private List<RingNewsbean.RingtonesBean> list;
    private Context context;
    private List<Integer> flagList;
    private MediaPlayer mediaPlayer;
    private List<Boolean> isplayingList;
    private List<Integer> downLoadFlag;
    private List<String> nameList = new ArrayList<>();
    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {


            switch (msg.what) {
                case 0:
                    int position = (int) msg.obj;
                    if (imageList.get(position).max != 0) {
                        imageList.get(position).progress = (((mediaPlayer.getCurrentPosition() * 100) / imageList.get(position).max));
                        notifyItemChanged(position);
                    }
                    break;

            }

        }
    };
    private Thread thread;
    private AdChoicesView adChoicesView;
    private  Map<Integer, NativeAd> mRingrAdsList;

    public RingContentNewsRecyclerViewAdapter( Map<Integer, NativeAd> mRingrAdsList, List<ProgressBean> imageList, List<RingNewsbean.RingtonesBean> list, Context context, List<Integer> flagList, MediaPlayer mediaPlayer, List<Boolean> isplayingList, List<Integer> downLoadFlag) {
        this.imageList = imageList;
        this.list = list;
        this.context = context;
        this.flagList = flagList;
        this.mediaPlayer = mediaPlayer;
        this.isplayingList = isplayingList;
        this.downLoadFlag = downLoadFlag;
        this.mRingrAdsList = mRingrAdsList;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 1) {

            return 1;
        } else {

            return 0;
        }
    }

    //加载布局。
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.loading_native_ad_layout, parent, false);
            RecyclerView.ViewHolder viewHolder2 = new NativeAdViewHolder(view);
            return viewHolder2;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.ringhomepageitem, parent, false);
            RecyclerView.ViewHolder viewHolder = new myViewHolder(view);
            return viewHolder;
        }
    }


    //赋值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemType = getItemViewType(position);

        if (itemType == 0) {
            myViewHolder myViewHolder = (myViewHolder) holder;
            myViewHolder.mRing_Homepage_Title.setText(list.get(position).getTitle());
            myViewHolder.mRing_Homepage_Title_category_name.setText(list.get(position).getCategory_name());
            myViewHolder.mRing_Homepage_RatingBar.setCountSelected((int) Float.parseFloat(list.get(position).getAvg_rating()));
            myViewHolder.mRing_Homepage_Image.setImageResource(imageList.get(position).image);
            myViewHolder.mRing_Progress_Bar.setSecondaryProgress(imageList.get(position).BufferProgressBar);

            if (isplayingList.get(position) == true) {

                if (mediaPlayer.isPlaying()) {
                    isplayingList.set(position, false);
                    if (thread != null) {
                        thread = null;
                    }
                    imageList.get(position).max = (mediaPlayer.getDuration());

                }

                thread = new Thread() {
                    @Override
                    public void run() {
                        while (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            try {
                                sleep(100);
                                Message msg = handler.obtainMessage();
                                msg.what = 0;
                                msg.obj = position;
                                handler.sendMessage(msg);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                };
                thread.start();

            } else {
                myViewHolder.mRing_Progress_Bar.setProgress(imageList.get(position).progress);

            }
            myViewHolder.mRing_Homepage_Image.setOnClickListener(new View.OnClickListener() {
                final String url = RingBaseUrl + "/ringtones/dl.php?loc=zh&action=download&id=" + list.get(position).getId() + "&file=" + list.get(position).getRingtone();

                @Override
                public void onClick(final View v) {
                    EventBus.getDefault().post(new MessageEvent(3330));

                    if (nameList.size() != 0) {
                        for (int i = 0; i < nameList.size(); i++) {
                            nameList.remove(i);
                        }
                    }

                    Constant.isOut = false;
                    if (flagList.get(position) == 0) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        for (int i = 0; i < imageList.size(); i++) {
                            imageList.get(i).image = R.drawable.play;
                            imageList.get(i).progress = 0;
                            imageList.get(i).BufferProgressBar = 0;
                            imageList.get(i).max = 10000000;
                            flagList.set(i, 0);
                            downLoadFlag.set(i, 0);
                            isplayingList.set(i, false);

                        }

                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones/" + list.get(position).getTitle() + ".mp3");
                        if (!file.exists()) {
                            nameList.add(list.get(position).getTitle() + ".mp3");
                            imageList.get(position).image = R.drawable.loading2;
                            downLoadFlag.set(position, 1);
                            notifyDataSetChanged();
                            DownloadUtil.get().download(url, list.get(position).getTitle() + ".mp3", Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones", new DownloadUtil.OnDownloadListener() {
                                @Override
                                public void onDownloadSuccess() {
                                    try {
                                        if (Constant.isOut == false) {
                                            if (downLoadFlag.get(position) == 1) {
                                                if (nameList.size() != 0) {
                                                    mediaPlayer.setDataSource(Environment.
                                                            getExternalStorageDirectory().getPath()
                                                            + "/eli.tech.wallri" + "/ringtones/" +
                                                            nameList.get(nameList.size() - 1));
                                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                    mediaPlayer.prepareAsync();
                                                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                        @Override
                                                        public void onPrepared(MediaPlayer mp) {
                                                            mp.start();

                                                            Constant.isPlaying = false;
                                                            isplayingList.set(position, true);
                                                            flagList.set(position, 1);
                                                            imageList.get(position).image = R.drawable.stop;
                                                            imageList.get(position).max = mp.getDuration();
                                                            notifyItemChanged(position);
                                                        }
                                                    });

                                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                        @Override
                                                        public void onCompletion(MediaPlayer mp) {
                                                            flagList.set(position, 0);
                                                            imageList.get(position).image = R.drawable.play;
                                                            notifyDataSetChanged();
                                                        }
                                                    });

                                                }

                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onDownloading(int progress) {

                                }

                                @Override
                                public void onDownloadFailed() {

                                }
                            });


                        } else {
                            try {
                                mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones/" + list.get(position).getTitle() + ".mp3");
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mediaPlayer.prepareAsync();
                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {

                                        mp.start();
                                        flagList.set(position, 1);
                                        isplayingList.set(position, true);
                                        imageList.get(position).image = R.drawable.stop;
                                        notifyDataSetChanged();
                                        imageList.get(position).max = mp.getDuration();

                                    }
                                });

                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        flagList.set(position, 0);
                                        imageList.get(position).image = R.drawable.play;
                                        notifyDataSetChanged();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    } else {


                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            imageList.get(position).image = R.drawable.play;
                            notifyDataSetChanged();
                            isplayingList.set(position, false);
                        } else {
                            mediaPlayer.start();
                            imageList.get(position).image = R.drawable.stop;
                            notifyDataSetChanged();
                            isplayingList.set(position, true);
                        }


                    }


                }


            });
        } else if (itemType == 1) {
            NativeAdViewHolder nativeAdViewHolder = (NativeAdViewHolder) holder;
            NativeAd nativeAd = mRingrAdsList.get(position);
            try {
                nativeAdViewHolder.param.height = DpOrPxUtils.dip2px(context, 90);
                nativeAdViewHolder.container.setVisibility(View.VISIBLE);
                SimpleDraweeView adImage = nativeAdViewHolder.mRing_Feed_Banner_Native_Image;
                TextView tvAdTitle = nativeAdViewHolder.mRing_Feed_Banner_Native_Title;
                TextView tvAdBody = nativeAdViewHolder.mRing_Feed_Banner_Native_Text;
                TextView btnCTA = nativeAdViewHolder.mRing_Feed_Banner_Native_Button;
                LinearLayout adChoicesContainer = nativeAdViewHolder.mRing_Feed_Banner_Nativ_Container;
                tvAdTitle.setText(nativeAd.getAdTitle());
                tvAdBody.setText(nativeAd.getAdBody());
                adImage.setImageURI(nativeAd.getAdIcon().getUrl());
                btnCTA.setText(nativeAd.getAdCallToAction());
                if (adChoicesView != null) {
                    adChoicesView = null;
                    adChoicesContainer.removeAllViews();
                    adChoicesView = new AdChoicesView(context, nativeAd, true);
                    adChoicesContainer.addView(adChoicesView);

                } else {
                    adChoicesView = new AdChoicesView(context, nativeAd, true);
                    adChoicesContainer.addView(adChoicesView);
                }
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(adImage);
                clickableViews.add(btnCTA);
                clickableViews.add(tvAdTitle);
                nativeAd.registerViewForInteraction(nativeAdViewHolder.container, clickableViews);
            } catch (NullPointerException e) {
                nativeAdViewHolder.container.setVisibility(View.GONE);
                nativeAdViewHolder.param.height = 0;
            }
        }


    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView mRing_Homepage_Image;
        TextView mRing_Homepage_Title;
        TextView mRing_Homepage_Title_category_name;
        XLHRatingBar mRing_Homepage_RatingBar;
        ProgressBar mRing_Progress_Bar;

        public myViewHolder(View itemView) {
            super(itemView);
            mRing_Progress_Bar = itemView.findViewById(R.id.Ring_Progress_Bar);
            mRing_Homepage_Image = itemView.findViewById(R.id.Ring_Homepage_Image);
            mRing_Homepage_Title = itemView.findViewById(R.id.Ring_Homepage_Title);
            mRing_Homepage_Title_category_name = itemView.findViewById(R.id.Ring_Homepage_Title_category_name);
            mRing_Homepage_RatingBar = itemView.findViewById(R.id.Ring_Homepage_RatingBar);

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

    class NativeAdViewHolder extends RecyclerView.ViewHolder {
        View container;
        SimpleDraweeView mRing_Feed_Banner_Native_Image;
        TextView mRing_Feed_Banner_Native_Title;
        TextView mRing_Feed_Banner_Native_Text;
        TextView mRing_Feed_Banner_Native_Button;
        LinearLayout mRing_Feed_Banner_Nativ_Container;
        RecyclerView.LayoutParams param;

        public NativeAdViewHolder(View itemView) {
            super(itemView);
            this.container = itemView;
            param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            mRing_Feed_Banner_Native_Image = (SimpleDraweeView) itemView.findViewById(R.id.Ring_Feed_Banner_Native_Image);
            mRing_Feed_Banner_Native_Title = (TextView) itemView.findViewById(R.id.Ring_Feed_Banner_Native_Title);
            mRing_Feed_Banner_Native_Text = (TextView) itemView.findViewById(R.id.Ring_Feed_Banner_Native_Text);
            mRing_Feed_Banner_Native_Button = (TextView) itemView.findViewById(R.id
                    .Ring_Feed_Banner_Native_Button);
            mRing_Feed_Banner_Nativ_Container = itemView.findViewById(R.id.Ring_Feed_Banner_Nativ_Container);
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
    private onItemClickListener monItemClickListener;

    //设置recycleview的某个监听
    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        monItemClickListener = onItemClickListener;

    }


}
