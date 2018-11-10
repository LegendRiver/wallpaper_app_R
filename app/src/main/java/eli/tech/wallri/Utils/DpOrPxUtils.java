package eli.tech.wallri.Utils;

import android.content.Context;

/**
 * Created by 张学雷 on 2017/11/9.
 * 这个工具类是dp转px px转dp的
 */

public class DpOrPxUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
