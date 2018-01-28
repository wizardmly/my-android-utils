package com.mly.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by JakeMo on 18-1-27.
 */

public class ScreenUtils {

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getTotalHeight(Context context) {
        int heightPixels = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Display display = windowManager.getDefaultDisplay();
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                @SuppressWarnings("rawtypes")
                Class c;
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, displayMetrics);
            } else {
                display.getRealMetrics(displayMetrics);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        heightPixels = displayMetrics.heightPixels;
        return heightPixels;
    }


    /***
     * 这种方式，当全屏状态下，
     * 状态栏不展示的时候，获取到的是0， 获取不到高度
     * 当状态栏展示的时候，才能获取到正真的高度
     *
     * @param activity
     * @return
     */
    public static int getTopStatusBarHeight(Activity activity) {

        // 获得状态栏的高度，方法3
//        DisplayMetrics dm = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        //应用区域
//        Rect outRect1 = new Rect();
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
//        int statusBarHeight = dm.heightPixels - outRect1.height();  //状态栏高度=屏幕高度-应用区域高度

        // 获得状态栏的高度，方法4
        Rect rectangle = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    /**
     * 获得状态栏的高度
     * 这种方法，获取的是系统配置的文件，与状态栏是否展示无关
     *
     * @param context
     * @return
     */
    public static int getTopStatusBarHeight(Context context) {
//        /**
//         * 获取状态栏高度——方法 1
//         * */
//        int statusHeight = -1;
//        try {
//            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//            Object object = clazz.newInstance();
//            int height = Integer.parseInt(clazz.getField("status_bar_height")
//                    .get(object).toString());
//            statusHeight = context.getResources().getDimensionPixelSize(height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 获取状态栏高度——方法 2
         * */
        int statusHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        return statusHeight;
    }

    /**
     * 获取Activity的标题栏高度
     *
     * @return
     */
    public static int getTitleBarHeight(Activity activity) {
        //getTop()，即content view在父View的高度，也就是状态栏的高度
        //因为状态栏 和 ContentView 是线性布局，依顺序排列AppCompatActivity
//        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        try {
            if (activity instanceof AppCompatActivity) {
                return ((AppCompatActivity) activity).getSupportActionBar().getHeight();
            } else {
                return activity.getActionBar().getHeight();
            }
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * 获取Activity的内容栏高度
     *
     * @param activity
     * @return
     */
    public static int getContentHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
        return rect.height();
    }


    /**
     * 获得屏幕高度，包含： 状态栏 + 标题栏 + Activity内容栏
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomVirtaulBarHeight(Context context) {
        int totalHeight = getTotalHeight(context);

        int screenHeight = getScreenHeight(context);

        return totalHeight - screenHeight;
    }

}
