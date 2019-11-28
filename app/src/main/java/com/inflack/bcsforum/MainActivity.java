package com.inflack.bcsforum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.model.NewsDTO;
import com.inflack.bcsforum.rest.ApiClient;
import com.inflack.bcsforum.rest.ApiInterface;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public String TAG = "MainActivity";
    Toolbar toolbar;

    DrawerLayout drawer;

    @BindView(R.id.MarqueeText)
    TextView MarqueeText;

    @BindView(R.id.img_logo)
    ImageView img_logo;

    @BindView(R.id.img_admin_profile)
    CircularImageView img_admin_profile;

    @BindView(R.id.president_name)
    TextView president_name;

    @BindView(R.id.president_designation1)
    TextView president_designation1;

    @BindView(R.id.president_designation2)
    TextView president_designation2;

    @BindView(R.id.president_designation3)
    TextView president_designation3;

    @BindView(R.id.president_designation4)
    TextView president_designation4;

    @BindView(R.id.proposer_name)
    TextView proposer_name;

    @BindView(R.id.proposer_designation_1)
    TextView proposer_designation_1;

    @BindView(R.id.proposer_designation_2)
    TextView proposer_designation_2;

    @BindView(R.id.proposer_designation_3)
    TextView proposer_designation_3;

    @BindView(R.id.proposer_designation_4)
    TextView proposer_designation_4;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        initLayout();
        updateUserInfo();
    }

    private void updateUserInfo() {
        apiInterface.getNewsUpdate().enqueue(new Callback<List<NewsDTO>>() {
            @Override
            public void onResponse(Call<List<NewsDTO>> call, Response<List<NewsDTO>> response) {
                Constants.debugLog(TAG, response + "");
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().size() > 0 &&
                            response.body().get(0).getCategory().equalsIgnoreCase("news")) {
                        MarqueeText.setText(response.body().get(0).getContent());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NewsDTO>> call, Throwable t) {
                Constants.debugLog(TAG, t.getMessage());
            }
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initLayout() {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (Constants.type == Constants.BCS_22) {
            getSupportActionBar().setTitle(getResources().getString(R.string.bcs_22_name));
            Glide.with(this).load(Constants.getImage(this, "ic_logo_22"))
                    .into(img_logo);
            Glide.with(this).load(Constants.getImage(this, "nishat"))
                    .into(img_admin_profile);
            president_name.setText(getResources().getString(R.string.president_name_22));
            president_designation1.setText(getResources().getString(R.string.president_designation1_22));
            president_designation2.setText(getResources().getString(R.string.president_designation2_22));
            president_designation3.setText(getResources().getString(R.string.president_designation3_22));
            president_designation4.setVisibility(View.GONE);

            proposer_name.setText(getResources().getString(R.string.proposer_name_22));
            proposer_designation_1.setText(getResources().getString(R.string.proposer_designation1_22));
            proposer_designation_2.setText(getResources().getString(R.string.proposer_designation2_22));
            proposer_designation_3.setVisibility(View.GONE);
            proposer_designation_4.setVisibility(View.GONE);

        } else if (Constants.type == Constants.BCS_15) {
            getSupportActionBar().setTitle(getResources().getString(R.string.bcs_15_name));
            Glide.with(this).load(Constants.getImage(this, "ic_logo_15"))
                    .into(img_logo);
            Glide.with(this).load(Constants.getImage(this, "monirul"))
                    .into(img_admin_profile);
            president_name.setText(getResources().getString(R.string.president_name_15));
            president_designation1.setText(getResources().getString(R.string.president_designation1_15));
            president_designation2.setText(getResources().getString(R.string.president_designation2_15));
            president_designation3.setText(getResources().getString(R.string.president_designation3_15));
            president_designation4.setText(getResources().getString(R.string.president_designation4_15));

            proposer_name.setText(getResources().getString(R.string.proposer_name_15));
            proposer_designation_1.setText(getResources().getString(R.string.proposer_designation1_15));
            proposer_designation_2.setText(getResources().getString(R.string.proposer_designation2_15));
            proposer_designation_3.setText(getResources().getString(R.string.proposer_designation3_15));
            proposer_designation_4.setText(getResources().getString(R.string.proposer_designation4_15));


        }

        MarqueeText.setSelected(true);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.img_edit_profile,
            R.id.img_edit_profile_home,
            R.id.img_notification,
            R.id.nav_cadre_cetegory,
            R.id.nav_forum_committee,
            R.id.nav_forum_structure,
            R.id.img_close_drawer,
            R.id.btn_member_list,
            R.id.btn_logout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_cadre_cetegory:
                Intent intent;
                if (Constants.type == Constants.BCS_15) {
                    intent = new Intent(MainActivity.this, CategoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if (Constants.type == Constants.BCS_22) {
                    intent = new Intent(MainActivity.this, MemberListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                break;
            case R.id.btn_logout:
                Constants.logOut(MainActivity.this);
                break;
            case R.id.img_edit_profile_home:
            case R.id.img_edit_profile:
                closeDrawer();
                intent = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_member_list:
                intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.img_notification:
                Utils.startNotificationActivity(MainActivity.this);
                break;
            case R.id.nav_forum_committee:
                closeDrawer();
                intent = new Intent(MainActivity.this, ForumCommitteeActivity.class);
                intent.putExtra(ForumCommitteeActivity.CATEGORY, "committee");
                startActivity(intent);
                break;
            case R.id.nav_forum_structure:
                closeDrawer();
                intent = new Intent(MainActivity.this, ForumCommitteeActivity.class);
                intent.putExtra(ForumCommitteeActivity.CATEGORY, "forum");
                startActivity(intent);
                break;
            case R.id.img_close_drawer:
                closeDrawer();
                break;
        }

    }

    private void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

}
