package com.inflack.bcsforum;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.child_view)
    View child_view;

    @BindView(R.id.parent_view)
    View parent_view;

    private ObjectAnimator animation;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {

        parent_view.post(new Runnable() {
            @Override
            public void run() {
                animation = ObjectAnimator.ofFloat(child_view, "translationX", (parent_view.getWidth() + child_view.getWidth()));
                animation.setDuration(1250);
                animation.setRepeatCount(Animation.INFINITE);
                animation.start();
            }
        });

        handler.postDelayed(splashRunnable, 2500);

    }

    Runnable splashRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private void cancelAnimation() {
        if (animation != null) {
            animation.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(splashRunnable);
        cancelAnimation();
    }
}
