package com.inflack.bcsforum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inflack.bcsforum.model.MemberDTO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class EditProfileActivity2 extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);

        ButterKnife.bind(this);

        getIntentData();
        initLayout();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(MemberDTO.TAG)) {
            memberDTO = (MemberDTO) intent.getSerializableExtra(MemberDTO.TAG);
        }
        if (intent.hasExtra(NAME)) {
            edt_name.setText(memberDTO.getName());
            edt_name_layout.setVisibility(View.VISIBLE);
            edt_designation_layout.setVisibility(View.GONE);
            edt_company_layout.setVisibility(View.GONE);
        } else {
            edt_name_layout.setVisibility(View.GONE);
            edt_designation_layout.setVisibility(View.VISIBLE);
            edt_company_layout.setVisibility(View.VISIBLE);
            if (intent.hasExtra(DESIGNATION)) {
                edt_designation.setText(memberDTO.getDesignation());
            }
            if (intent.hasExtra(COMPANY)) {
                edt_company.setText(memberDTO.getCompany());
            }
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
                Intent intent = new Intent();
                intent.putExtra("OK", "");
                setResult(RESULT_OK, intent);
                Toasty.success(EditProfileActivity2.this, "Profile updated successfully!", Toast.LENGTH_SHORT, true).show();
                finish();
                break;
        }

    }
}
