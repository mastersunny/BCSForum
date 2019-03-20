package com.inflack.bcsforum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.inflack.bcsforum.model.MemberDTO;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberProfileActivity extends AppCompatActivity {

    @BindView(R.id.img_profile)
    CircularImageView img_profile;

    MemberDTO memberDTO;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_position)
    TextView tv_position;

    @BindView(R.id.tv_employment)
    TextView tv_employment;

    @BindView(R.id.tv_id_no)
    TextView tv_id_no;

    @BindView(R.id.tv_current_employment)
    TextView tv_current_employment;

    @BindView(R.id.tv_previous_employment)
    TextView tv_previous_employment;

    @BindView(R.id.tv_district)
    TextView tv_district;

    @BindView(R.id.tv_dob)
    TextView tv_dob;

    @BindView(R.id.tv_phone_no)
    TextView tv_phone_no;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.tv_blood_group)
    TextView tv_blood_group;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);
        ButterKnife.bind(this);

        getIntentData();

        initLayout();
    }

    private void getIntentData() {
        memberDTO = (MemberDTO) getIntent().getSerializableExtra("MEMBER");

         tv_name.setText(memberDTO.getName());
         tv_position.setText(memberDTO.getPosition());
         tv_employment.setText(memberDTO.getEmployment());
         tv_id_no.setText("পরিচিতি নম্বরঃ "+memberDTO.getIdNo());
         tv_current_employment.setText("বর্তমান কর্মস্থলঃ "+memberDTO.getCurrentEmployment());
         tv_previous_employment.setText("পূর্বের কর্মস্থলঃ "+memberDTO.getPreviousEmployment());
         tv_district.setText("নিজ জেলাঃ "+memberDTO.getDistrict());
         tv_dob.setText("জন্ম তারিখঃ "+memberDTO.getDateOfBirth());
         tv_phone_no.setText("মোবাইলঃ "+memberDTO.getPhoneNo());
         tv_email.setText("ই-মেইলঃ "+memberDTO.getEmail());
         tv_blood_group.setText("রক্তের গ্রুপঃ "+memberDTO.getBloodGroup());


    }

    private void initLayout(){
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
