package com.inflack.bcsforum;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.inflack.bcsforum.model.MemberDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_member_name)
    RecyclerView recyclerView;

    MemberListAdapter memberListAdapter;

    List<MemberDTO> memberDTOS = new ArrayList<>();
    List<MemberDTO> memberDTOSCopy = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        for (int i = 0; i < 20; i++) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setName("Name");
            memberDTO.setId(12121212);

            memberDTOS.add(memberDTO);
            memberDTOSCopy.add(memberDTO);
        }

        if (memberListAdapter != null) {
            memberListAdapter.notifyDataSetChanged();
        }
    }

    private void initLayout() {
        memberListAdapter = new MemberListAdapter(this, memberDTOS);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memberListAdapter);
        memberListAdapter.setOnItemSelectListener(new MemberListAdapter.OnItemSelectListener() {
            @Override
            public void onItemSelected(int position) {
                Intent intent = new Intent(MemberListActivity.this, MemberProfileActivity.class);
                startActivity(intent);
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
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @OnClick({R.id.img_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
