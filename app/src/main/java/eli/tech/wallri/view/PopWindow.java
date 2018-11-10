package eli.tech.wallri.view;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.math.BigDecimal;

import eli.tech.wallri.Activity.FinishFailActivity;
import eli.tech.wallri.Activity.FinishSuccessActivity;
import eli.tech.wallri.AdManager.IncentiveVideoAd;
import eli.tech.wallri.R;
import eli.tech.wallri.System.VideoWallpaper;
import eli.tech.wallri.Utils.DownloadUtil;
import eli.tech.wallri.Utils.Utils;

/**
 * Created by 小雷 on 2018/3/29.
 */

public class PopWindow {


    private Context context;
    private TextView mUpDesktopWallpaperSetting;
    private CheckBox mUpDesktopCheckBoxSet;
    private LinearLayout mUpDesktopWallpaperLineSetting;
    private TextView mUpDesktopAndLockWallpaperSet;
    private CheckBox mUpDesktopAndLockCheckBoxSet;
    private LinearLayout mUpDesktopAndLockWallpaperLineSet;
    private RelativeLayout mRadioGroupLowerPopuwindow;
    private TextView mBtuSet;
    private TextView mTextView1Ad;
    private RelativeLayout mRelaPopuWindow;
    private View showAtLocationView;
    private Window window;
    private PopupWindow mPopuWindow;
    private int score;
    private IncentiveVideoAd ads;
    private String sdCardPath;
    private String fileName;
    private String imagePath;
    private String saveDir;
    private ProgressBar downloadProgress;
    private View itemView;
    private double AdRandomNum;
    private double Range = 0.70;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    downloadProgress.setVisibility(View.GONE);
                    context.startActivity(new Intent(context, FinishSuccessActivity.class));
                    break;
            }

        }
    };


    //构造方法
    public PopWindow(Context context
            , View showAtLocationView
            , Window window
            , int score
            , String sdCardPath
            , String fileName
            , String imagePath
            , String saveDir
            , ProgressBar downloadProgress
            , IncentiveVideoAd ads
    ) {
        this.context = context;
        this.showAtLocationView = showAtLocationView;
        this.window = window;
        this.score = score;
        this.sdCardPath = sdCardPath;
        this.fileName = fileName;
        this.imagePath = imagePath;
        this.saveDir = saveDir;
        this.downloadProgress = downloadProgress;
        this.ads = ads;
        initView();
    }

    public void setAdRandomNum(double AdRandomNum) {

        this.AdRandomNum = AdRandomNum;
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        itemView = inflater.inflate(R.layout.lowerpopupmenu, null);
        mUpDesktopWallpaperSetting = (TextView) itemView.findViewById(R.id.Setting_up_desktop_wallpaper);
        mUpDesktopCheckBoxSet = (CheckBox) itemView.findViewById(R.id.Set_up_desktop_CheckBox);
        mUpDesktopWallpaperLineSetting = (LinearLayout) itemView.findViewById(R.id.Setting_up_desktop_wallpaper_Line);
        mUpDesktopAndLockWallpaperSet = (TextView) itemView.findViewById(R.id.Set_up_desktop_and_lock_wallpaper);
        mUpDesktopAndLockCheckBoxSet = (CheckBox) itemView.findViewById(R.id.Set_up_desktop_and_lock_CheckBox);
        mUpDesktopAndLockWallpaperLineSet = (LinearLayout) itemView.findViewById(R.id.Set_up_desktop_and_lock_wallpaper_Line);
        mRadioGroupLowerPopuwindow = (RelativeLayout) itemView.findViewById(R.id.LowerPopuwindow_RadioGroup);
        mBtuSet = (TextView) itemView.findViewById(R.id.set_btu);
        mTextView1Ad = (TextView) itemView.findViewById(R.id.ad_textView_1);
        mRelaPopuWindow = (RelativeLayout) itemView.findViewById(R.id.PopuWindow_Rela);
    }


    public void initImageView() {
        ads.loadRewardedVideoAd();
        AfterSeeingTheImageAdvertisement();
        BigDecimal bigDecimal1 = new BigDecimal(AdRandomNum);
        BigDecimal bigDecimal2 = new BigDecimal(Range);
        if (bigDecimal1.compareTo(bigDecimal2) >= 0) {
            mBtuSet.setText("LOCK");
        }

        setImagePopwindow();
        initPopuWindow(itemView);
    }


    public void initVideoView() {
        //初始化广告预加载
        ads.loadRewardedVideoAd();
//监听广告的状态看是否是看完广告
        AfterSeeingTheVideoAdvertisement();
        if (score >= 100) {
            mBtuSet.setText("LOCK");
        }
        setVideoPopwindow();
        initPopuWindow(itemView);
    }


    private void AfterSeeingTheVideoAdvertisement() {
        ads.setWatchingAdsListener(new IncentiveVideoAd.watchingAdsListener() {
            @Override
            public void getWatchingAdsListener() {
                initAdRandomNum();
                SetUpVideoPopUpHints();
            }
        });

    }

    private void AfterSeeingTheImageAdvertisement() {
        ads.setWatchingAdsListener(new IncentiveVideoAd.watchingAdsListener() {
            @Override
            public void getWatchingAdsListener() {
                initAdRandomNum();
                setUpImagePopUpHints();
            }
        });

    }

    private void initAdRandomNum() {
        if (setOnInitAdRandomNumListener != null) {
            setOnInitAdRandomNumListener.OnInitAdRandomNum();
        }

    }


    private void setImagePopwindow() {
        mRelaPopuWindow.getBackground().setAlpha(150);
        mUpDesktopAndLockCheckBoxSet.setVisibility(View.GONE);
        mUpDesktopAndLockWallpaperLineSet.setVisibility(View.GONE);
        mBtuSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal bigDecimal1 = new BigDecimal(AdRandomNum);
                BigDecimal bigDecimal2 = new BigDecimal(Range);
                if (bigDecimal1.compareTo(bigDecimal2) >= 0) {
                    LockUpPopUpPrompt();
                } else {
                    setUpImagePopUpHints();

                }


            }
        });
    }

    private void setVideoPopwindow() {
        mRelaPopuWindow.getBackground().setAlpha(150);
        mUpDesktopAndLockCheckBoxSet.setVisibility(View.GONE);
        mUpDesktopAndLockWallpaperLineSet.setVisibility(View.GONE);

        //判断是付费还是免费的
        mBtuSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score >= 100) {
                    LockUpPopUpPrompt();
                } else {
                    SetUpVideoPopUpHints();
                }
            }
        });

    }

    /**
     * 设置广告的dialog
     */
    private void LockUpPopUpPrompt() {
        AlertDialog.Builder prompt = Utils.shownormalDialog("Prompt", "This is a paid wallpaper. Please see the unlocked ad！", context);
        prompt.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ads.showRewardedVideo();
            }
        });
        prompt.show();
    }

    //初始化popuwindow
    private void initPopuWindow(View itemView) {
        mPopuWindow = new PopupWindow(itemView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopuWindow.setOutsideTouchable(true);
        mPopuWindow.setFocusable(true);
        mPopuWindow.setAnimationStyle(R.style.AnimationFade1);
        mPopuWindow.setBackgroundDrawable(new ColorDrawable());
        mPopuWindow.showAtLocation(showAtLocationView, Gravity.BOTTOM, 0, 0);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
        mPopuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
    }


    /**
     * 设置没有锁上的设置壁纸弹窗
     */
    private void setUpImagePopUpHints() {
        ads.loadRewardedVideoAd();
        mPopuWindow.dismiss();
        downloadProgress.setVisibility(View.VISIBLE);
        DownloadUtil.get().download(imagePath, fileName,
                saveDir, new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        try {
                            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                            // MM图片
                            File wall = new File(saveDir, fileName);
                            // MM图片路径
                            String path = wall.getAbsolutePath();
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            wallpaperManager.setBitmap(bitmap);
                            handler.sendEmptyMessage(0);

                        } catch (Exception e) {
                            e.printStackTrace();
                            context.startActivity(new Intent(context, FinishFailActivity.class));
                        } catch (OutOfMemoryError error) {
                            context.startActivity(new Intent(context, FinishFailActivity.class));
                        }


                    }

                    @Override
                    public void onDownloading(int progress) {
                    }

                    @Override
                    public void onDownloadFailed() {
                        context.startActivity(new Intent(context, FinishFailActivity.class));
                    }
                }
        );
    }


    /**
     * 设置没有锁上的设置视频壁纸弹窗
     */
    private void SetUpVideoPopUpHints() {
        ads.loadRewardedVideoAd();


        Utils.deleteDataFromSdCard(sdCardPath);

        if (mUpDesktopCheckBoxSet.isChecked() == true) {
            mPopuWindow.dismiss();
            downloadProgress.setVisibility(View.VISIBLE);
            Utils.writeDataToSdCard(sdCardPath, saveDir + "/" + fileName);
            DownloadUtil.get().download(imagePath, fileName,
                    saveDir, new DownloadUtil.OnDownloadListener() {
                        @Override
                        public void onDownloadSuccess() {
                            //调用设置壁纸的系统service
                            VideoWallpaper videoWallpaper = new VideoWallpaper();
                            videoWallpaper.setToWallPaper(context);
                            downloadProgress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onDownloading(int progress) {
                        }

                        @Override
                        public void onDownloadFailed() {
                        }
                    }
            );

        }
    }

    private setOnInitAdRandomNumListener setOnInitAdRandomNumListener;

    public void setSetOnInitAdRandomNum(setOnInitAdRandomNumListener InitAdRandomNum) {
        setOnInitAdRandomNumListener = InitAdRandomNum;
    }

    public interface setOnInitAdRandomNumListener {
        void OnInitAdRandomNum();
    }


}
