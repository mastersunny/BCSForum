package com.inflack.bcsforum;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.rest.ApiClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberProfileActivity extends AppCompatActivity {

    public static final String TAG = "MemberProfileActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.img_profile)
    CircularImageView img_profile;

    MemberDTO memberDTO;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_designation1)
    TextView tv_designation1;

    @BindView(R.id.tv_company)
    TextView tv_company;

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

        if (!TextUtils.isEmpty(memberDTO.getName())) {
            tv_name.setText(memberDTO.getName());
        }

        if (!TextUtils.isEmpty(memberDTO.getDesignation())) {
            tv_designation1.setText(memberDTO.getDesignation());
        }

        if (!TextUtils.isEmpty(memberDTO.getCompany())) {
            tv_company.setText(memberDTO.getCompany());
        }

        if (!TextUtils.isEmpty(memberDTO.getIdNo())) {
            tv_id_no.setText("পরিচিতি নম্বরঃ " + memberDTO.getIdNo());
        }

        if (!TextUtils.isEmpty(memberDTO.getCurrentEmployment())) {
            tv_current_employment.setText("বর্তমান কর্মস্থলঃ " + memberDTO.getCurrentEmployment());
        }

        if (!TextUtils.isEmpty(memberDTO.getPreviousEmployment())) {
            tv_previous_employment.setText("পূর্বের কর্মস্থলঃ " + memberDTO.getPreviousEmployment());
        }

        if (!TextUtils.isEmpty(memberDTO.getDistrict())) {
            tv_district.setText("নিজ জেলাঃ " + memberDTO.getDistrict());
        }

        if (!TextUtils.isEmpty(memberDTO.getDateOfBirth())) {
            tv_dob.setText("জন্ম তারিখঃ " + memberDTO.getDateOfBirth());
        }

        if (!TextUtils.isEmpty(memberDTO.getPhoneNo())) {
            tv_phone_no.setText("মোবাইলঃ " + memberDTO.getPhoneNo());
        }

        if (!TextUtils.isEmpty(memberDTO.getEmail())) {
            tv_email.setText("ই-মেইলঃ " + memberDTO.getEmail());
        }

        if (!TextUtils.isEmpty(memberDTO.getBloodGroup())) {
            tv_blood_group.setText("রক্তের গ্রুপঃ " + memberDTO.getBloodGroup());
        }

        Linkify.addLinks(tv_phone_no, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(tv_email, Linkify.EMAIL_ADDRESSES);


        findViewById(R.id.img_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startNotificationActivity(MemberProfileActivity.this);
            }
        });

    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("প্রোফাইল");
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        int res = getResources().getIdentifier(getPackageName() + ":drawable/" + memberDTO.getProfilePicture(), null, null);
//        img_profile.setImageResource(res);
        Constants.debugLog(TAG, memberDTO.getProfilePicture());
        if (memberDTO.getProfilePicture() != null) {
            Glide.with(this).load(ApiClient.BASE_URL + "storage" + "/" + memberDTO.getProfilePicture())
                    .into(img_profile);
//            mainHolder.room_image.setImageResource(res);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick({R.id.tv_phone_no, R.id.tv_email})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_phone_no:
                makePhoneCall();
                break;
            case R.id.tv_email:
                break;
        }
    }

    private void makePhoneCall() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        String uri = "tel:" + tv_phone_no.getText().toString().trim();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();
    }

}
