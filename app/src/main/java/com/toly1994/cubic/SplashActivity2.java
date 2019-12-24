package com.toly1994.cubic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.toly1994.cubic.view.CircleLogoView;
import com.toly1994.cubic.view.CustomPropertyAnimationView;

public class SplashActivity2 extends AppCompatActivity {

    private CustomPropertyAnimationView circleLogoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        circleLogoView = findViewById(R.id.circlelogo);

    }

    @Override
    public void onBackPressed() {
        circleLogoView.startCustomAnim();
    }

    @Override
    protected void onDestroy() {
        circleLogoView.stopCustomAnim();
        super.onDestroy();
    }
}
