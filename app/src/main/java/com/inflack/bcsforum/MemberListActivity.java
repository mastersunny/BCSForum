package com.inflack.bcsforum;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.inflack.bcsforum.model.CategoryDTO;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.rest.ApiClient;
import com.inflack.bcsforum.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberListActivity extends AppCompatActivity {

    public static final String TAG = "MemberListActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_member_name)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private MemberListAdapter memberListAdapter;
    private List<MemberDTO> memberDTOS = new ArrayList<>();
    private List<MemberDTO> memberDTOSCopy = new ArrayList<>();
    private ApiInterface apiInterface;
    private CategoryDTO categoryDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        ButterKnife.bind(this);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getIntentData();
        initLayout();
    }

    private void getIntentData() {
        categoryDTO = (CategoryDTO) getIntent().getSerializableExtra(CategoryDTO.TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (memberDTOS.size() <= 0) {
            getData();
        }
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        memberDTOS.clear();
        memberDTOSCopy.clear();
        apiInterface.getMembers(categoryDTO.getCategoryId()).enqueue(new Callback<List<MemberDTO>>() {
            @Override
            public void onResponse(Call<List<MemberDTO>> call, Response<List<MemberDTO>> response) {
                progressBar.setVisibility(View.GONE);
                Constants.debugLog(TAG, response + "");
                if (response != null && response.isSuccessful()) {
                    memberDTOS.addAll(response.body());
                    memberDTOSCopy.addAll(memberDTOS);
                    if (memberListAdapter != null) {
                        memberListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MemberDTO>> call, Throwable t) {
//                Log.d(TAG, t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toasty.error(MemberListActivity.this, "Could not fetch data", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(categoryDTO.getName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        memberListAdapter = new MemberListAdapter(this, memberDTOS);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memberListAdapter);
        memberListAdapter.setOnItemSelectListener(new MemberListAdapter.OnItemSelectListener() {
            @Override
            public void onItemSelected(int position) {
                Intent intent = new Intent(MemberListActivity.this, MemberProfileActivity.class);
                intent.putExtra("MEMBER", memberDTOS.get(position));
                startActivity(intent);
            }
        });

        findViewById(R.id.img_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startNotificationActivity(MemberListActivity.this);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                memberDTOS.clear();
                for (int i = 0; i < memberDTOSCopy.size(); i++) {
                    if (memberDTOSCopy.get(i).getName()
                            .toLowerCase().contains(s.toLowerCase()) || memberDTOSCopy.get(i).getIdNo()
                            .toLowerCase().contains(s.toLowerCase())) {
                        memberDTOS.add(memberDTOSCopy.get(i));
                    }
                }
                memberListAdapter.notifyDataSetChanged();
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
