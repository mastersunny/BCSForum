package com.inflack.bcsforum;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.inflack.bcsforum.model.MemberDTO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.child_view)
    View child_view;

    @BindView(R.id.parent_view)
    View parent_view;

    @BindView(R.id.img_logo)
    ImageView img_logo;

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

        if (Constants.type == Constants.BCS_22) {
            Glide.with(this).load(Constants.getImage(this, "ic_logo_22"))
                    .into(img_logo);
        } else if (Constants.type == Constants.BCS_15) {
            Glide.with(this).load(Constants.getImage(this, "ic_logo_15"))
                    .into(img_logo);
        }

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
            MemberDTO memberDTO = MemberDTO.getMember();
            if (memberDTO != null) {
                //Handle Returning User
                goToMyLoggedInActivity();
            } else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }


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

    private void goToMyLoggedInActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
