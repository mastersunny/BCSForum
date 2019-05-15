package com.inflack.bcsforum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.model.UserResponse;
import com.inflack.bcsforum.rest.ApiClient;
import com.inflack.bcsforum.rest.ApiInterface;
import com.ornach.richtext.RichEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity2 extends AppCompatActivity {

    public String TAG = "SplashActivity2";

    ApiInterface apiInterface;

    @BindView(R.id.edt_id_no)
    RichEditText edt_id_no;

    @BindView(R.id.edt_password)
    RichEditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

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
                login();
                break;
        }

        //Handle new or logged out user
//        phoneLogin();

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

    private void login() {
        String idNo = edt_id_no.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        if (TextUtils.isEmpty(idNo)) {
            edt_id_no.setError("Cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edt_password.setError("Cannot be empty");
            return;
        }
        showProgress();
        apiInterface.login(idNo, password).enqueue(new Callback<UserResponse>() {
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
                        goToMyLoggedInActivity();
                    } else {
                        Toasty.error(SplashActivity2.this, "Username or password incorrect").show();
                    }
                } else {
                    Toasty.error(SplashActivity2.this, "Username or password incorrect").show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                cancelProgress();
                Log.e(TAG, t.getMessage());
                Toasty.error(SplashActivity2.this, "Username or password incorrect").show();
            }
        });
    }

    private void changePassword() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    public static int APP_REQUEST_CODE = 99;

    public void phoneLogin() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                toastMessage = "Login Success";
//                if (loginResult.getAccessToken() != null) {
//                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
//                } else {
//                    toastMessage = String.format(
//                            "Success:%s...",
//                            loginResult.getAuthorizationCode().substring(0, 10));
//                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                goToMyLoggedInActivity();
            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void goToMyLoggedInActivity() {
        Intent intent = new Intent(SplashActivity2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
