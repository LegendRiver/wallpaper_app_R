package eli.tech.wallri.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import eli.tech.wallri.Adapter.RecyclerViewItemDecoration;
import eli.tech.wallri.DataBean.MessageEvent;
import eli.tech.wallri.R;

/**
 * Created by 小雷 on 2017/12/29.
 * 用的最多的工具类
 */

public class Utils {

    public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";


    /**
     * 这个是设置锁屏监听弹出广告的方法
     *
     * @param context 上下文
     * @param rele    父容器
     */
    public static ScreenListener setScreen(final Context context, final ViewGroup rele) {

        ScreenListener screenListener = new ScreenListener(context);


        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
            }

            @Override
            public void onScreenOff() {
            }

            @Override
            public void onUserPresent() {
                LayoutInflater inflater = LayoutInflater.from(context);
                View inflate = inflater.inflate(R.layout.lockscreen, null);


                SimpleDraweeView mMainActivity_PopuWindow = inflate.findViewById(R.id.MainActivity_PopuWindow);
                mMainActivity_PopuWindow.setImageURI("http://pic23.photophoto.cn/20120530/0020033092420808_b.jpg");


                final PopupWindow mPopuWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                mPopuWindow.setOutsideTouchable(true);

                //设置创建popuwindow里面的listview有焦点

                mPopuWindow.setFocusable(true);

                //如果想让创建popuwindow有这个动画效果,就必须有这段代码，没有这段代码动画执行不了

                mPopuWindow.setBackgroundDrawable(new ColorDrawable());

                mPopuWindow.showAtLocation(rele, Gravity.CENTER, 0, 0);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopuWindow.dismiss();
                    }
                }, 5000);


            }
        });


        return screenListener;
    }

    /**
     * 这个是设置2列的RecyclerView
     *
     * @param recyclerView recyclerView控件对象
     * @param adapter      适配器
     * @param context      上下文
     */
    public static void setRecyclerView(RecyclerView recyclerView, Object adapter, Context context) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        RecyclerViewItemDecoration decoration = new RecyclerViewItemDecoration(8);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter((RecyclerView.Adapter) adapter);


    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getSystem() {
        String SYS = null;

        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                SYS = SYS_MIUI;//小米

            } else if (prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    || prop.getProperty(KEY_EMUI_VERSION, null) != null
                    || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
                SYS = SYS_EMUI;//华为  
            } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                SYS = SYS_FLYME;//魅族  
            }


        } catch (IOException e) {
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    public static String getMeizuFlymeOSFlag() {

        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {

        }
        return defaultValue;
    }

    /**
     * 设置桌面壁纸
     */
    public static void setBackGroundForTable(String imageUrl, final String fileName, final String saveDir, final Context context) {

        DownloadUtil.get().download(imageUrl, fileName,
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
                            EventBus.getDefault().post(new MessageEvent(0));
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e + "ex");
                            EventBus.getDefault().post(new MessageEvent(1));
                        } catch (OutOfMemoryError error) {
                            EventBus.getDefault().post(new MessageEvent(1));
                            System.out.println(error + "oom");
                        }


                    }

                    @Override
                    public void onDownloading(int progress) {
                    }

                    @Override
                    public void onDownloadFailed() {
                        EventBus.getDefault().post(new MessageEvent(1));
                    }
                }
        );


    }

    /**
     * 设置锁屏壁纸
     */
    public static void setBackGroundForLock(String imageUrl, final String fileName, final String saveDir, final Context context) {

        DownloadUtil.get().download(imageUrl, fileName,
                saveDir, new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        try {
                            WallpaperManager mWallManager = WallpaperManager.getInstance(context);

                            // MM图片
                            File wall = new File(saveDir, fileName);
                            // MM图片路径
                            String path = wall.getAbsolutePath();
                            Bitmap bitmap = BitmapFactory.decodeFile(path);


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mWallManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_SYSTEM);
                                EventBus.getDefault().post(new MessageEvent(0));

                            } else {
                                EventBus.getDefault().post(new MessageEvent(1));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            EventBus.getDefault().post(new MessageEvent(1));
                        } catch (OutOfMemoryError error) {
                            EventBus.getDefault().post(new MessageEvent(1));
                        }


                    }

                    @Override
                    public void onDownloading(int progress) {

                    }

                    @Override
                    public void onDownloadFailed() {
                        EventBus.getDefault().post(new MessageEvent(1));
                    }
                });
    }


    /**
     * 过的toobar的高
     *
     * @param context
     * @return
     */
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }


    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabsHeight);
    }


    /**
     * 设置到顶回退
     *
     * @param recyclerView
     * @param btn
     * @param context
     */
    public static void setFloutBtu(final RecyclerView recyclerView, final FloatingActionButton btn, final Context context) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int mScrollThreshold;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;

                if (isSignificantDelta) {

                    if (dy > 0) {

                        onScrollUp(btn);
                    } else {

                        onScrollDown(btn);
                    }
                }

            }

            public void setScrollThreshold(int scrollThreshold) {
                mScrollThreshold = scrollThreshold;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
                Toast.makeText(context, R.string.toast, Toast.LENGTH_LONG).show();
            }
        });


    }


    //下滑时候要执行的代码
    private static void onScrollDown(final FloatingActionButton btn) {
        btn.setVisibility(View.VISIBLE);


    }

    //上滑是要执行的代码
    private static void onScrollUp(final FloatingActionButton btn) {
        btn.setVisibility(View.GONE);

    }


    /**
     *   * 获取时间 小时:分;秒 HH:mm:ss
     *   *
     *   * @return
     *   
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     *   * 获取现在时间
     *   *
     *   * @return 返回短时间字符串格式yyyy-MM-dd
     *   
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     *   * 返回美国时间格式 26 Apr 2006
     *   *
     *   * @param str
     *   * @return
     *   
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[1].toUpperCase();
    }

    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;

    public static String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "Sunday";
        } else if ("2".equals(mWay)) {
            mWay = "Monday";
        } else if ("3".equals(mWay)) {
            mWay = "Tuesday";
        } else if ("4".equals(mWay)) {
            mWay = "Wednesday";
        } else if ("5".equals(mWay)) {
            mWay = "Thursday";
        } else if ("6".equals(mWay)) {
            mWay = "Friday";
        } else if ("7".equals(mWay)) {
            mWay = "Saturday";
        }

        return mWay + "," + getEDate(getStringDateShort()) + " " + mDay;
    }

    /**
     * 检查当前网络是否可用
     *
     * @param
     * @return
     */

    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
//                    System.out.println(i + "===状态===" + networkInfo[i].getState());
//                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 图片二次采样
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成 bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565; // 取 drawable 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config); // 建立对应 bitmap
        Canvas canvas = new Canvas(bitmap); // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把 drawable 内容画到画布中
        return bitmap;
    }

    /**
     * 调整图片宽高
     *
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
        Matrix matrix = new Matrix(); // 创建操作图片用的 Matrix 对象
        float scaleWidth = ((float) w / width); // 计算缩放比例
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight); // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true); // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        return new BitmapDrawable(newbmp);// 把 bitmap 转换成 drawable 并返回
    }

    static String jsonUrl = null;

    public static <T> T getDynamicWallpaper(String url, String json, final Class<T> tClass) {

        OKhttpManager.getInstance().sendJsonPost(url, json, new OKhttpManager.Func1() {
            @Override
            public String onResponse(String result) {
                jsonUrl = result;

                return null;
            }
        });
        return OKhttpManager.parsr(jsonUrl, tClass);

    }


    /**
     * 设置铃声
     *
     * @param type  RingtoneManager.TYPE_RINGTONE 来电铃声
     *              RingtoneManager.TYPE_NOTIFICATION 通知铃声
     *              RingtoneManager.TYPE_ALARM 闹钟铃声
     * @param path  下载下来的mp3全路径
     * @param title 铃声的名字
     */
    public static void setRing(Context context, int type, String path, String title) {

        Uri oldRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE); //系统当前  通知铃声
        Uri oldNotification = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION); //系统当前  通知铃声
        Uri oldAlarm = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM); //系统当前  闹钟铃声

        File sdfile = new File(path);
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, title);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_MUSIC, true);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile.getAbsolutePath());
        Uri newUri = null;
        String deleteId = "";
        try {
            Cursor cursor = context.getContentResolver().query(uri, null, MediaStore.MediaColumns.DATA + "=?", new String[]{path}, null);
            if (cursor.moveToFirst()) {
                deleteId = cursor.getString(cursor.getColumnIndex("_id"));
            }

            context.getContentResolver().delete(uri,
                    MediaStore.MediaColumns.DATA + "=\"" + sdfile.getAbsolutePath() + "\"", null);
            newUri = context.getContentResolver().insert(uri, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (newUri != null) {

            String ringStoneId = "";
            String notificationId = "";
            String alarmId = "";
            if (null != oldRingtoneUri) {
                ringStoneId = oldRingtoneUri.getLastPathSegment();
            }

            if (null != oldNotification) {
                notificationId = oldNotification.getLastPathSegment();
            }

            if (null != oldAlarm) {
                alarmId = oldAlarm.getLastPathSegment();
            }

            Uri setRingStoneUri;
            Uri setNotificationUri;
            Uri setAlarmUri;

            if (type == RingtoneManager.TYPE_RINGTONE || ringStoneId.equals(deleteId)) {
                setRingStoneUri = newUri;

            } else {
                setRingStoneUri = oldRingtoneUri;
            }

            if (type == RingtoneManager.TYPE_NOTIFICATION || notificationId.equals(deleteId)) {
                setNotificationUri = newUri;

            } else {
                setNotificationUri = oldNotification;
            }

            if (type == RingtoneManager.TYPE_ALARM || alarmId.equals(deleteId)) {
                setAlarmUri = newUri;

            } else {
                setAlarmUri = oldAlarm;
            }

            RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, setRingStoneUri);
            RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION, setNotificationUri);
            RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM, setAlarmUri);

            switch (type) {
                case RingtoneManager.TYPE_RINGTONE:

                    EventBus.getDefault().post(new MessageEvent(0));
                    break;
                case RingtoneManager.TYPE_NOTIFICATION:
                    EventBus.getDefault().post(new MessageEvent(0));
                    break;
                case RingtoneManager.TYPE_ALARM:
                    EventBus.getDefault().post(new MessageEvent(0));
            }
        }
    }

    /**
     * 数据没有加载起来时loading、的dialog
     *
     * @param context
     * @return
     */
    public static LoadingDialog setLoadingDialog(Context context) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.setLoadingText("Loading...");
        loadingDialog.show();
        return loadingDialog;
    }


    /**
     * 删除SD卡文件
     */
    public static void deleteDataFromSdCard(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

    }

    /**
     * 往SD卡中写入数据
     */
    public static void writeDataToSdCard(String path, String body) {

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(body.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 往SD卡中读取数据
     */
    public static String readDataFromSdCard(String path) {
        String res = null;
        try {
            FileInputStream is = new FileInputStream(path);
            byte[] b = new byte[is.available()];
            is.read(b);

            res = new String(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;


    }


    public static AlertDialog.Builder shownormalDialog(String title, String Prompt, Context context) {
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setTitle(title);
        normalDialog.setMessage(Prompt);
        normalDialog.setNegativeButton("NO", null);

        return normalDialog;
    }

    public static void applicationScore(Context context) {

        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    /**
     * 设置字体
     */
    public Typeface setFont(Context context) {
        //设置字体
        //得到AssetManager
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/roboto_regular.ttf");
        return tf;
    }







}
