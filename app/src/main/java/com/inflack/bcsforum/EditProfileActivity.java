package com.inflack.bcsforum;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.accountkit.AccountKit;
import com.fasterxml.jackson.databind.JsonNode;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.model.UserResponse;
import com.inflack.bcsforum.rest.ApiClient;
import com.inflack.bcsforum.rest.ApiInterface;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ornach.bitpermission.BitPermission;
import com.ornach.bitpermission.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    public String TAG = "EditProfileActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_designation)
    TextView tv_designation;

    @BindView(R.id.tv_company)
    TextView tv_company;

    @BindView(R.id.tv_phone_no)
    TextView tv_phone_no;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.img_profile)
    CircularImageView img_profile;

    MemberDTO memberDTO;

    ApiInterface apiInterface;

    private final int PICK_PHOTO_FOR_AVATAR = 999;
    private final int UPDATE_PROFILE_INFO = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("প্রোফাইল");
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void updateLayout() {
        memberDTO = MemberDTO.getMember();
        if (memberDTO != null) {
            tv_name.setText(memberDTO.getName());
            tv_designation.setText(memberDTO.getDesignation());
            tv_company.setText(memberDTO.getCompany());
            tv_phone_no.setText(memberDTO.getPhoneNo());
            tv_email.setText(memberDTO.getEmail());
            Constants.debugLog(TAG, memberDTO.getProfilePicture());
            if (memberDTO.getProfilePicture() != null) {
                Glide.with(this).load(ApiClient.BASE_URL + "storage" + "/" + memberDTO.getProfilePicture())
                        .into(img_profile);
            }
        }
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
                MemberDTO.deleteAll(MemberDTO.class);
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
                intent.putExtra(EditProfileActivity2.NAME, true);
                intent.putExtra(MemberDTO.TAG, memberDTO);
                startActivityForResult(intent, UPDATE_PROFILE_INFO);
                break;
            case R.id.img_edit_desig:
                intent = new Intent(EditProfileActivity.this, EditProfileActivity2.class);
                intent.putExtra(EditProfileActivity2.OTHER_INFO, true);
                intent.putExtra(MemberDTO.TAG, memberDTO);
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
            FileOutputStream out = null;
            InputStream in = null;
            File compressedImageFile = null;
            try {
                String rootPath = Constants.getRootDirectory();
                File destFile = new File(rootPath + File.separator + "img.jpg");

                in = getContentResolver().openInputStream(data.getData());
                out = new FileOutputStream(destFile);
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                bitmap.recycle();

                compressedImageFile = new Compressor(EditProfileActivity.this)
                        .setDestinationDirectoryPath(rootPath)
                        .compressToFile(destFile, "compressed_2.jpg");

                updatePhoto(compressedImageFile);

            } catch (Exception e) {
                Constants.debugLog(TAG, e.getMessage());
            } finally {
                try {
                    out.flush();
                    out.close();
                    in.close();
                } catch (IOException e) {
                    Constants.debugLog(TAG, e.getMessage());
                }
            }
        }
    }

    ProgressDialog progressDialog;

    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void cancelProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    private void updatePhoto(final File destFile) {
        showProgress();
        Constants.debugLog(TAG, destFile.getAbsolutePath());
        try {
//            progressBar.setVisibility(View.VISIBLE);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), destFile);
            MultipartBody.Part image = MultipartBody.Part.createFormData("profile_picture", destFile.getName(), reqFile);
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), memberDTO.getUserId() + "");
            apiInterface.updatePhoto(image, body).enqueue(new Callback<JsonNode>() {
                @Override
                public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                    cancelProgress();
                    Constants.debugLog(TAG, response + "");
                    if (response.isSuccessful()) {
                        String status = "";
                        try {
                            status = response.body().get("status").asText();
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                        if (status.equalsIgnoreCase("success")) {
                            Toasty.success(EditProfileActivity.this, "Profile updated successfully").show();
                            getProfile();
                        } else {
                            Toasty.error(EditProfileActivity.this, "Cannot update now").show();
                        }
                    } else {
                        Toasty.error(EditProfileActivity.this, "Cannot update now").show();
                    }
                }

                @Override
                public void onFailure(Call<JsonNode> call, Throwable e) {
                    cancelProgress();
                    Constants.debugLog(TAG, e.toString());
                    Toasty.error(EditProfileActivity.this, "Cannot update now").show();
                }
            });
        } catch (Exception e) {
            cancelProgress();
            Constants.debugLog(TAG, e.getMessage());
            Toasty.error(EditProfileActivity.this, "Cannot update now").show();
        }
    }

    private void getProfile() {
        showProgress();
        apiInterface.getProfile(memberDTO.getIdNo()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                cancelProgress();
                Log.d(TAG, response + "");
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body() + "");
                    if (response.body().getUser() != null &&
                            response.body().getUser().size() > 0) {
                        MemberDTO.deleteAll(MemberDTO.class);
                        MemberDTO memberDTO = response.body().getUser().get(0);
                        memberDTO.save();
                        updateLayout();
                    } else {
                        Toasty.error(EditProfileActivity.this, "Cannot update now").show();
                    }
                } else {
                    Toasty.error(EditProfileActivity.this, "Cannot update now").show();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                cancelProgress();
                Toasty.error(EditProfileActivity.this, "Cannot update now").show();
            }
        });
    }
}
