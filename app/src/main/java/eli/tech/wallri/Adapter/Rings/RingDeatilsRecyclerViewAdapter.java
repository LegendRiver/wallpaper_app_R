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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import eli.tech.wallri.DataBean.ProgressBean;
import eli.tech.wallri.DataBean.RingNewsbean;
import eli.tech.wallri.R;
import eli.tech.wallri.System.Constant;
import eli.tech.wallri.Utils.DownloadUtil;

import static eli.tech.wallri.System.Constant.RingBaseUrl;


/**
 * Created by 小雷 on 2018/1/26.
 * 铃声大图界面reyclerView适配器
 */

public class RingDeatilsRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<ProgressBean> imageList;
    private List<RingNewsbean.RingtonesBean> list;
    private Context context;
    private List<Integer> flagList;
    private List<Integer> mColorList;
    private List<Integer> mBigColorList;
    private MediaPlayer mediaPlayer;
    private List<Boolean> isplayingList;
    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {


            switch (msg.what) {
                case 0:
                    int position = (int) msg.obj;
                    // 将SeekBar位置设置 到当前播放位置
                    isplayingList.set(position, false);
                    imageList.get(position).progress = (((mediaPlayer.getCurrentPosition() * 100) / imageList.get(position).max));
                    notifyItemChanged(position);


                    break;

            }

        }
    };
    private Runnable runnable;


    public RingDeatilsRecyclerViewAdapter(List<RingNewsbean.RingtonesBean> list, Context context, List<ProgressBean> imageList, List<Integer> flagList,
                                          MediaPlayer mediaPlayer, List<Integer> mColorList, List<Integer> mBigColorList, List<Boolean> isplayingList) {
        this.list = list;
        this.context = context;
        this.imageList = imageList;
        this.flagList = flagList;
        this.mediaPlayer = mediaPlayer;
        this.mColorList = mColorList;
        this.mBigColorList = mBigColorList;
        this.isplayingList = isplayingList;
    }

    //加载布局。
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(context).inflate(R.layout.ringdetailsitem, parent, false);
        viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    public Handler setHandler() {
        return handler;
    }

    public Runnable setRunnable() {
        return runnable;
    }

    //赋值
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final String url = RingBaseUrl + "/ringtones/dl.php?loc=zh&action=download&id=" + list.get(position).getId() + "&file=" + list.get(position).getRingtone();
        final myViewHolder myViewHolder = (RingDeatilsRecyclerViewAdapter.myViewHolder) holder;
        myViewHolder.bigImage.setImageResource(mBigColorList.get(Math.abs(position % mColorList.size())));
        myViewHolder.mSmallRela.setImageResource(mColorList.get(Math.abs(position % mColorList.size())));
        myViewHolder.mCategory_name.setText(list.get(position).getCategory_name());
        myViewHolder.mRing_Homepage_Title.setText(list.get(position).getTitle());
        myViewHolder.mRing_Homepage_Image.setImageURI("res://eli.tech.wallri/" + imageList.get(position).image);
        myViewHolder.mRing_Progress_Bar.setProgress(imageList.get(position).progress);

        if (isplayingList.get(position) == true) {
            isplayingList.set(position, false);

            // 每100毫秒更新一次位置
            runnable = new Runnable() {

                @Override
                public void run() {
                    while (Constant.isPlaying == false) {
                        try {
                            // 每100毫秒更新一次位置
                            Thread.sleep(100);
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

            new Thread(runnable).start();


        } else {
            //进度条
            myViewHolder.mRing_Progress_Bar.setProgress(imageList.get(position).progress);
        }


        myViewHolder.mRing_Homepage_Image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (flagList.get(position) == 0) {
                    Constant.isPlaying = true;
                    Constant.isOut = false;
                    mediaPlayer.stop();
                    mediaPlayer.reset();

                    for (int i = 0; i < imageList.size(); i++) {
                        imageList.get(i).image = R.drawable.play_white;
                        imageList.get(i).progress = 0;
                        imageList.get(i).BufferProgressBar = 0;
                        imageList.get(i).max = 10000000;
                        flagList.set(i, 0);
                        isplayingList.set(i, false);

                    }

                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones/" + list.get(position).getTitle() + ".mp3");
                    if (!file.exists()) {
                        imageList.get(position).image = R.drawable.loading2;
                        notifyDataSetChanged();
                        DownloadUtil.get().download(url, list.get(position).getTitle() + ".mp3", Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones", new DownloadUtil.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess() {
                                try {
                                    if (Constant.isOut == false) {
                                        mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/eli.tech.wallri" + "/ringtones/" + list.get(position).getTitle() + ".mp3");
                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                        mediaPlayer.prepareAsync();
                                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                mp.start();
                                                Constant.isPlaying = false;
                                                flagList.set(position, 1);
                                                isplayingList.set(position, true);
                                                imageList.get(position).image = R.drawable.stop_white;
                                                imageList.get(position).max = mp.getDuration();
                                                notifyDataSetChanged();
                                            }
                                        });


                                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            @Override
                                            public void onCompletion(MediaPlayer mp) {
                                                flagList.set(position, 0);
                                                imageList.get(position).image = R.drawable.play_white;
                                                notifyDataSetChanged();
                                            }
                                        });
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
                                    Constant.isPlaying = false;
                                    imageList.get(position).image = R.drawable.stop_white;
                                    notifyDataSetChanged();
                                    imageList.get(position).max = mp.getDuration();

                                }
                            });

                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    flagList.set(position, 0);
                                    imageList.get(position).image = R.drawable.play_white;
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
                        imageList.get(position).image = R.drawable.play_white;
                        isplayingList.set(position, false);
                        notifyDataSetChanged();

                    } else {
                        mediaPlayer.start();
                        imageList.get(position).image = R.drawable.stop_white;
                        isplayingList.set(position, true);
                        notifyDataSetChanged();

                    }

                }


            }


        });


    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mRing_Homepage_Image;
        TextView mRing_Homepage_Title;
        ProgressBar mRing_Progress_Bar;
        ImageView mSmallRela;
        RelativeLayout mLargeRela;
        ImageView bigImage;
        TextView mCategory_name;

        public myViewHolder(View itemView) {
            super(itemView);
            mRing_Progress_Bar = itemView.findViewById(R.id.Ring_Details_Item_ProgressBar);
            mRing_Homepage_Image = itemView.findViewById(R.id.Ring_Details_Item_Play);
            mRing_Homepage_Title = itemView.findViewById(R.id.Ring_Details_Item_title);
            mSmallRela = itemView.findViewById(R.id.SmallRela);
            mLargeRela = itemView.findViewById(R.id.LargeRela);
            bigImage = itemView.findViewById(R.id.bigImage);
            mCategory_name = itemView.findViewById(R.id.category_name);


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
