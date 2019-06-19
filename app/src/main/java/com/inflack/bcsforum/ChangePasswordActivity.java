package com.inflack.bcsforum;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.inflack.bcsforum.model.UserResponse;
import com.inflack.bcsforum.rest.ApiClient;
import com.inflack.bcsforum.rest.ApiInterface;
import com.ornach.richtext.RichEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    public String TAG = "ChangePasswordActivity";

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

        edt_new_password.setOnEditorActionListener(new RichEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    changePassword();
                    return true;
                }
                return false;
            }
        });
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
        apiInterface.changePassword(idNo, password, newPassword).enqueue(new Callback<JsonNode>() {
            @Override
            public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                Constants.debugLog(TAG, response + "");
                progressDialog.cancel();
                if (response.isSuccessful()) {
                    Toasty.success(ChangePasswordActivity.this, "Password changed successfully", Toasty.LENGTH_SHORT)
                            .show();
                    finish();
                } else {
                    Toasty.error(ChangePasswordActivity.this, "Password change error", Toasty.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<JsonNode> call, Throwable t) {
                progressDialog.cancel();
                Toasty.error(ChangePasswordActivity.this, "Password change error", Toasty.LENGTH_SHORT)
                        .show();
            }
        });

    }
}
