package com.inflack.bcsforum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.inflack.bcsforum.model.CategoryDTO;
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

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_category)
    RecyclerView rv_category;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    CategoryListAdapter categoryListAdapter;
    List<CategoryDTO> categoryDTOS = new ArrayList<>();
    List<CategoryDTO> categoryDTOSCopy = new ArrayList<>();

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (categoryDTOS.size() <= 0) {
            getData();
        }
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        categoryDTOS.clear();
        categoryDTOSCopy.clear();
        apiInterface.getCategories().enqueue(new Callback<List<CategoryDTO>>() {
            @Override
            public void onResponse(Call<List<CategoryDTO>> call, Response<List<CategoryDTO>> response) {
                progressBar.setVisibility(View.GONE);
//                Log.d(TAG, response + "");
                if (response != null && response.isSuccessful()) {
                    categoryDTOS.addAll(response.body());
                    categoryDTOSCopy.addAll(categoryDTOS);
                    if (categoryListAdapter != null) {
                        categoryListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CategoryDTO>> call, Throwable t) {
//                Log.d(TAG, t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toasty.error(CategoryActivity.this, "Could not fetch data", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.left_menu_cadre));
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        categoryListAdapter = new CategoryListAdapter(categoryDTOS);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_category.setLayoutManager(layoutManager);
        rv_category.setAdapter(categoryListAdapter);
        categoryListAdapter.setOnItemSelectListener(new CategoryListAdapter.OnItemSelectListener() {
            @Override
            public void onItemSelected(int position) {
                Intent intent = new Intent(CategoryActivity.this, MemberListActivity.class);
                intent.putExtra(CategoryDTO.TAG, categoryDTOS.get(position));
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
                categoryDTOS.clear();
                for (int i = 0; i < categoryDTOSCopy.size(); i++) {
                    if (categoryDTOSCopy.get(i).getName()
                            .toLowerCase().contains(s.toLowerCase())) {
                        categoryDTOS.add(categoryDTOSCopy.get(i));
                    }
                }
                categoryListAdapter.notifyDataSetChanged();
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
