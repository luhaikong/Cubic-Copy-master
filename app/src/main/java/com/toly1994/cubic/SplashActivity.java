package com.toly1994.cubic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.toly1994.cubic.view.CircleLogoView;
import com.toly1994.cubic.view.CircleLogoView2;

public class SplashActivity extends AppCompatActivity implements CircleLogoView2.OnPlayFinishListener {

    private CircleLogoView2 circleLogoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash4);
//        circleLogoView = findViewById(R.id.circlelogo);
//        circleLogoView.setFinishListener(this);
//        circleLogoView.start();
    }

    private int getScreenHeight(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        return height;
    }

    private int getScreenWidth(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        return width;
    }

    @Override
    protected void onDestroy() {
//        circleLogoView.destory();
        super.onDestroy();
    }

    @Override
    public void onFinish() {
        Toast.makeText(getApplicationContext(),"动画播放完毕",Toast.LENGTH_LONG).show();
    }
}
