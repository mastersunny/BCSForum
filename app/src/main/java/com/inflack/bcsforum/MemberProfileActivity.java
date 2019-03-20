package com.inflack.bcsforum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.inflack.bcsforum.model.MemberDTO;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberProfileActivity extends AppCompatActivity {

    @BindView(R.id.img_profile)
    CircularImageView img_profile;

    MemberDTO memberDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);
        ButterKnife.bind(this);

        getIntentData();
    }

    private void getIntentData() {
        memberDTO = (MemberDTO) getIntent().getSerializableExtra("MEMBER");

        int res = getResources().getIdentifier(getPackageName() + ":drawable/" + memberDTO.getImgUrl(), null, null);
        img_profile.setImageResource(res);
    }

    @OnClick({R.id.img_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
