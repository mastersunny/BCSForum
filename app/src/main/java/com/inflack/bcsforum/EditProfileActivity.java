package com.inflack.bcsforum;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.ornach.bitpermission.BitPermission;
import com.ornach.bitpermission.PermissionListener;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private final int PICK_PHOTO_FOR_AVATAR = 999;
    private final int UPDATE_PROFILE_INFO = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("প্রোফাইল");
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick({R.id.btn_logout, R.id.img_notification, R.id.img_choose_photo, R.id.img_edit_name, R.id.img_edit_desig})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                AccountKit.logOut();
                Intent intent = new Intent(EditProfileActivity.this, SplashActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_choose_photo:
                requestPermission();
                break;
            case R.id.img_notification:
                Utils.startNotificationActivity(EditProfileActivity.this);
                break;
            case R.id.img_edit_name:
                intent = new Intent(EditProfileActivity.this, EditProfileActivity2.class);
                intent.putExtra(EditProfileActivity2.NAME, "মোহাম্মদ আব্দুল হাই, পি এ এ");
                startActivityForResult(intent, UPDATE_PROFILE_INFO);
                break;
            case R.id.img_edit_desig:
                intent = new Intent(EditProfileActivity.this, EditProfileActivity2.class);
                intent.putExtra(EditProfileActivity2.DESIGNATION, "প্রকল্প পরিচালক (ডেপুটি সেক্রেটারি)");
                intent.putExtra(EditProfileActivity2.COMPANY, "স্কিল ডেভেলপমেন্ট ফর মোবাইল গেইম এন্ড এপ্লিকেশন প্রজেক্ট");
                startActivityForResult(intent, UPDATE_PROFILE_INFO);
                break;
        }
    }

    private void requestPermission() {
        ArrayList<String> list = new ArrayList<>();
        list.add(Manifest.permission.CAMERA);
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);


        BitPermission bitPermission = BitPermission.with(this)
                .setPermissionListener(listener)
                .setPermissions(list)
                .build();
        bitPermission.request();
    }

    PermissionListener listener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            pickImage();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }
    };

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
            } catch (Exception e) {

            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }

        if (requestCode == UPDATE_PROFILE_INFO && requestCode == RESULT_OK) {
//            Toasty.success(EditProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT, true).show();
//            Toast.makeText(EditProfileActivity.this, "Profile updated successfully!",Toast.LENGTH_LONG).show();
        }
    }
}
