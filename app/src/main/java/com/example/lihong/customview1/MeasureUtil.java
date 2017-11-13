package com.example.lihong.customview1;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * Created by lihong on 2017/11/10.
 */

public final class MeasureUtil {
    public static int[] getScreenSize(Activity activity){
        DisplayMetrics metrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[]{metrics.widthPixels,metrics.heightPixels};
    }
}
