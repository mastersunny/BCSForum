package com.inflack.bcsforum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView edt_name;

    @BindView(R.id.edt_designation)
    TextView edt_designation;

    @BindView(R.id.edt_company)
    TextView edt_company;

    @BindView(R.id.edt_name_layout)
    LinearLayout edt_name_layout;

    @BindView(R.id.edt_designation_layout)
    LinearLayout edt_designation_layout;

    @BindView(R.id.edt_company_layout)
    LinearLayout edt_company_layout;

    private MemberDTO memberDTO;

    public static final String NAME = "name";
    public static final String DESIGNATION = "designation";
    public static final String COMPANY = "company";

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

        edt_name.setText(memberDTO.getName());
        edt_designation.setText(memberDTO.getDesignation());
        edt_company.setText(memberDTO.getCompany());

        if (intent.hasExtra(NAME)) {
            edt_name_layout.setVisibility(View.VISIBLE);
            edt_designation_layout.setVisibility(View.GONE);
            edt_company_layout.setVisibility(View.GONE);
        } else {
            edt_name_layout.setVisibility(View.GONE);
            edt_designation_layout.setVisibility(View.VISIBLE);
            edt_company_layout.setVisibility(View.VISIBLE);
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

    private void editProfile() {

        if (memberDTO == null) {
            return;
        }

        String name = edt_name.getText().toString().trim();
        String designation = edt_designation.getText().toString().trim();
        String company = edt_company.getText().toString().trim();

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

        memberDTO.setName(name);
        memberDTO.setDesignation(designation);
        memberDTO.setCompany(company);

        apiInterface.editProfile(memberDTO).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, response + "");
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().equalsIgnoreCase("success")) {
                        Toasty.success(EditProfileActivity2.this, "Profile updated successfully");
                        memberDTO.update();
                    } else {
                        Toasty.error(EditProfileActivity2.this, "Cannot update now");
                    }
                } else {
                    Toasty.error(EditProfileActivity2.this, "Cannot update now");
                }
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toasty.error(EditProfileActivity2.this, "Cannot update now");
            }
        });

    }
}
