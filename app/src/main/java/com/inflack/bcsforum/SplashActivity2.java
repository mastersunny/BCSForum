package com.inflack.bcsforum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login})
    public void onClick(View v) {
        Intent intent = new Intent(SplashActivity2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
