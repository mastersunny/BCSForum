package com.inflack.bcsforum;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.inflack.bcsforum.model.UserResponse;
import com.inflack.bcsforum.rest.ApiClient;
import com.inflack.bcsforum.rest.ApiInterface;
import com.ornach.richtext.RichEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.edt_id_no)
    RichEditText edt_id_no;

    @BindView(R.id.edt_password)
    RichEditText edt_password;

    @BindView(R.id.edt_new_password)
    RichEditText edt_new_password;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @OnClick({R.id.btn_change_password, R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_password:
                changePassword();
                break;
            case R.id.btn_login:
                finish();
                break;
        }
    }

    private void changePassword() {
        String idNo = edt_id_no.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String newPassword = edt_new_password.getText().toString().trim();
        if (TextUtils.isEmpty(idNo)) {
            edt_id_no.setError("Cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edt_password.setError("Cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edt_new_password.setError("Cannot be empty");
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        apiInterface.login(idNo, password).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.cancel();

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.cancel();

            }
        });

    }
}
