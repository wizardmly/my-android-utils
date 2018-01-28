package com.mly.customview;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());


        findViewById(R.id.getAxias).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View contentView = getWindow().getDecorView().findViewById(android.R.id.content);
                System.out.println("getTop = " + contentView.getTop());
                System.out.println("getBottom = " + contentView.getBottom());

                System.out.println("getLeft = " + contentView.getLeft());
                System.out.println("getRight = " + contentView.getRight());

                System.out.println("getHeight = " + contentView.getHeight());

                Rect globalVisableRect = new Rect();
                contentView.getGlobalVisibleRect(globalVisableRect);

                System.out.println("over!");

                System.out.println("total height = " + ScreenUtils.getTotalHeight(MainActivity.this));
                System.out.println("status bar height = " + ScreenUtils.getTopStatusBarHeight(MainActivity.this.getApplicationContext()));
                System.out.println("title bar height = " + ScreenUtils.getTitleBarHeight(MainActivity.this));
                System.out.println("content height = " + ScreenUtils.getContentHeight(MainActivity.this));
                System.out.println("bottom bar height = " + ScreenUtils.getBottomVirtaulBarHeight(MainActivity.this));

                /**************/

                System.out.println("*******************");

                //获取屏幕区域的宽高等尺寸获取
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int heightPixels = metrics.heightPixels;
                System.out.println("heightPixels = " + heightPixels);

                //应用程序App区域宽高等尺寸获取
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                System.out.println(rect);

                //获取状态栏高度
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                int statusBarHeight = rect.top;
                System.out.println(statusBarHeight);

                //View布局区域宽高等尺寸获取
                getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
                System.out.println(rect);


            }
        });

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
