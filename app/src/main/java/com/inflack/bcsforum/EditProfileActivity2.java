package com.inflack.bcsforum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.model.UserResponse;
import com.inflack.bcsforum.rest.ApiClient;
import com.inflack.bcsforum.rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity2 extends AppCompatActivity {

    public String TAG = "EditProfileActivity2";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.edt_name)
    EditText edt_name;

    @BindView(R.id.edt_designation)
    EditText edt_designation;

    @BindView(R.id.edt_company)
    EditText edt_company;

    @BindView(R.id.edt_phone_no)
    EditText edt_phone_no;

    @BindView(R.id.edt_email)
    EditText edt_email;

    @BindView(R.id.edt_name_layout)
    LinearLayout edt_name_layout;

    @BindView(R.id.edt_designation_layout)
    LinearLayout edt_designation_layout;

    @BindView(R.id.edt_company_layout)
    LinearLayout edt_company_layout;

    @BindView(R.id.edt_phone_layout)
    LinearLayout edt_phone_layout;

    @BindView(R.id.edt_email_layout)
    LinearLayout edt_email_layout;

    private MemberDTO memberDTO;

    public static final String NAME = "name";
    public static final String OTHER_INFO = "other_info";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getIntentData();
        initLayout();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(MemberDTO.TAG)) {
            memberDTO = (MemberDTO) intent.getSerializableExtra(MemberDTO.TAG);
        }

        try {
            edt_name.setText(memberDTO.getName());
            edt_designation.setText(memberDTO.getDesignation());
            edt_company.setText(memberDTO.getCompany());
            edt_phone_no.setText(memberDTO.getPhoneNo());
            edt_email.setText(memberDTO.getEmail());
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }

        edt_name_layout.setVisibility(View.GONE);
        edt_designation_layout.setVisibility(View.GONE);
        edt_company_layout.setVisibility(View.GONE);
        edt_phone_layout.setVisibility(View.GONE);
        edt_email_layout.setVisibility(View.GONE);


        if (intent.hasExtra(NAME)) {
            edt_name_layout.setVisibility(View.VISIBLE);
        } else if (intent.hasExtra(OTHER_INFO)) {
            edt_designation_layout.setVisibility(View.VISIBLE);
            edt_company_layout.setVisibility(View.VISIBLE);
        } else if (intent.hasExtra(PHONE)) {
            edt_phone_layout.setVisibility(View.VISIBLE);
        } else if (intent.hasExtra(EMAIL)) {
            edt_email_layout.setVisibility(View.VISIBLE);
        }
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

    @OnClick({R.id.btn_update})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                editProfile();
//                Intent intent = new Intent();
//                intent.putExtra("OK", "");
//                setResult(RESULT_OK, intent);
//                Toasty.success(EditProfileActivity2.this, "Profile updated successfully!", Toast.LENGTH_SHORT, true).show();
//                finish();
                break;
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

    private void editProfile() {

        if (memberDTO == null) {
            return;
        }

        String name = edt_name.getText().toString().trim();
        String designation = edt_designation.getText().toString().trim();
        String company = edt_company.getText().toString().trim();
        String phoneNo = edt_phone_no.getText().toString().trim();
        String email = edt_email.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edt_name.setError("Cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(designation)) {
            edt_designation.setError("Cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(company)) {
            edt_company.setError("Cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(phoneNo)) {
            edt_phone_no.setError("Cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            edt_email.setError("Cannot be empty");
            return;
        }

        memberDTO.setName(name);
        memberDTO.setDesignation(designation);
        memberDTO.setCompany(company);
        memberDTO.setPhoneNo(phoneNo);
        memberDTO.setEmail(email);

        showProgress();
        apiInterface.editProfile(memberDTO).enqueue(new Callback<JsonNode>() {
            @Override
            public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                cancelProgress();
                Constants.debugLog(TAG, response + "");
                if (response.isSuccessful()) {
                    String status = "";
                    try {
                        status = response.body().get("status").asText();
                    } catch (Exception e) {
                        Constants.debugLog(TAG, e.getMessage());
                    }
                    if (response.body() != null && status.equalsIgnoreCase("success")) {
                        Toasty.success(EditProfileActivity2.this, "Profile updated successfully").show();
                        memberDTO.update();
                        finish();
                    } else {
                        Toasty.error(EditProfileActivity2.this, "Cannot update now").show();
                    }
                } else {
                    Toasty.error(EditProfileActivity2.this, "Cannot update now").show();
                }
            }

            @Override
            public void onFailure(Call<JsonNode> call, Throwable t) {
                cancelProgress();
                Constants.debugLog(TAG, t.getMessage());
                Toasty.error(EditProfileActivity2.this, "Cannot update now").show();
            }
        });

    }
}
