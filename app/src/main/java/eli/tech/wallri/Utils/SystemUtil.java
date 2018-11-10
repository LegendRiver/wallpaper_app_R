package eli.tech.wallri.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * Created by 小雷 on 2018/1/6.
 */

public class SystemUtil {

    /**
     * 获取手机厂商
     */

    public static String getDeviceBrand() {

        return Build.BRAND;
    }


    /**
     * 获取手机型号
     */

    public static String getSystemModel() {

        return Build.BRAND;
    }


    /**
     * 获取手机系统版本号
     */

    public static String getSystemVersion() {

        return Build.VERSION.RELEASE;
    }


    /**
     *  
     *      * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限) 
     *      * 
     *      * @return  手机IMEI 
     *      
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {

            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 返回当前应用的版本号
     */

    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String versionName = packageInfo.versionName;
        return versionName;

    }

    public static String getAppVersionName(Context context) {
        String versionName = "";


        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;


    }


    // 通过Resources获取    
    public static String getScreenDensity_ByResources(Context context) {
        DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;

//        Log.d("zhang", "Screen Ratio: [" + width + "x" + height + "],density=" + density + ",densityDpi=" + densityDpi);
//        Log.d("zhang", "Screen mDisplayMetrics: " + mDisplayMetrics);
        return width+"x"+height;


    }
}
