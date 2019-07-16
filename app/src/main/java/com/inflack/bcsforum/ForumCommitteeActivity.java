package com.inflack.bcsforum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.inflack.bcsforum.model.CommitteeDTO;
import com.inflack.bcsforum.rest.ApiClient;
import com.inflack.bcsforum.rest.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForumCommitteeActivity extends AppCompatActivity {

    public String TAG = "ForumCommitteeActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    WebView webview;

    private String category = "";
    public static final String CATEGORY = "category";
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_committee);

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        webview = (WebView) findViewById(R.id.webview);

        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);

        getIntentData();
        initLayout();
    }

    private void getIntentData() {
        category = getIntent().getStringExtra(CATEGORY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.committeeDTOS.size() <= 0) {
            getData();
        }
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(category.equals("committee") ? getResources().getString(R.string.left_menu_forum_committee) :
                getResources().getString(R.string.left_menu_forum_structure));
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void getData() {
        apiInterface.getCommitteeForums().enqueue(new Callback<List<CommitteeDTO>>() {
            @Override
            public void onResponse(Call<List<CommitteeDTO>> call, Response<List<CommitteeDTO>> response) {
                Constants.debugLog(TAG, response + "");
                if (response.isSuccessful()) {
                    List<CommitteeDTO> committeeDTOS = response.body();
                    for (int i = 0; i < committeeDTOS.size(); i++) {
                        if (committeeDTOS.get(i).getCategory().equals(category)) {
                            webview.loadData(committeeDTOS.get(i).getContent(), "text/html", "UTF-8");
                            break;
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<CommitteeDTO>> call, Throwable t) {
                Constants.debugLog(TAG, t.toString() + "");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
