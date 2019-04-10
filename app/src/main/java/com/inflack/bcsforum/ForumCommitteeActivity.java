package com.inflack.bcsforum;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

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

    @BindView(R.id.text_view)
    TextView text_view;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String category = "";
    public static final String CATEGORY = "category";
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_committee);

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

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
        getSupportActionBar().setTitle(category.equals("committee") ? getResources().getString(R.string.forum_committee) :
                getResources().getString(R.string.forum_structure));
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void getData() {
        apiInterface.getCommitteeForums().enqueue(new Callback<List<CommitteeDTO>>() {
            @Override
            public void onResponse(Call<List<CommitteeDTO>> call, Response<List<CommitteeDTO>> response) {
                if (response.isSuccessful()) {
                    List<CommitteeDTO> committeeDTOS = response.body();
                    for (int i = 0; i < committeeDTOS.size(); i++) {
                        if (committeeDTOS.get(i).getCategory().equals(category)) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                text_view.setText(Html.fromHtml(committeeDTOS.get(i).getContent(), Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                text_view.setText(Html.fromHtml(committeeDTOS.get(i).getContent()));
                            }
                            break;
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<CommitteeDTO>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
