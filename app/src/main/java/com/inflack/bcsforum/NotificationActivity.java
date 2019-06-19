package com.inflack.bcsforum;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.inflack.bcsforum.model.NotificationDTO;
import com.inflack.bcsforum.rest.ApiClient;
import com.inflack.bcsforum.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    public String TAG = "NotificationActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_notification)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    NotificationListAdapter notificationListAdapter;
    List<NotificationDTO> notificationDTOS = new ArrayList<>();

    private ApiInterface apiInterface;

    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaton);
        ButterKnife.bind(this);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        editor = getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE).edit();
        prefs = getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);


        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("নোটিফিকেশন");
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        notificationListAdapter = new NotificationListAdapter(this, notificationDTOS);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notificationListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (notificationDTOS.size() <= 0) {
            getData();
        }
    }

    private void getData() {
        long lastLoginTime = prefs.getLong(Constants.LAST_LOGIN_TIME, 0);
        if (lastLoginTime == 0) {
            lastLoginTime = System.currentTimeMillis() / 1000L;
        }
        editor.putLong(Constants.LAST_LOGIN_TIME, (System.currentTimeMillis() / 1000L)).apply();

        progressBar.setVisibility(View.VISIBLE);
        notificationDTOS.clear();
        apiInterface.getNotifications(lastLoginTime).enqueue(new Callback<List<NotificationDTO>>() {
            @Override
            public void onResponse(Call<List<NotificationDTO>> call, Response<List<NotificationDTO>> response) {
                Constants.debugLog(TAG, response + "");
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    notificationDTOS.addAll(response.body());
                    if (notificationListAdapter != null) {
                        notificationListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NotificationDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toasty.error(NotificationActivity.this, "Could not fetch data", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
