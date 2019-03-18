package com.inflack.bcsforum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

//    NavigationView navigationView;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
////
////        drawer = findViewById(R.id.drawer_layout);
////        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////        drawer.addDrawerListener(toggle);
////        toggle.syncState();
////
////        navigationView = (NavigationView) findViewById(R.id.nav_view);
////        navigationView.setNavigationItemSelectedListener(this);
////
////        initLayout();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ListView rvNumbers = (ListView) findViewById(R.id.list);
        findViewById(R.id.nav_member_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemberListActivity.class);
                startActivity(intent);
            }
        });


//        String[] numbers = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
//        ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(this, R.layout.list_item, numbers);
//        rvNumbers.setAdapter(itemArrayAdapter);

    }

//    public class ItemArrayAdapter extends ArrayAdapter<String> {
//        String[] itemList;
//        private int listItemLayout;
//
//        public ItemArrayAdapter(Context context, int layoutId, String[] itemList) {
//            super(context, layoutId, itemList);
//            listItemLayout = layoutId;
//            this.itemList = itemList;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            int pos = position;
//            final String item = getItem(pos);
//
//            ViewHolder viewHolder;
//            if (convertView == null) {
//                viewHolder = new ViewHolder();
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                convertView = inflater.inflate(listItemLayout, parent, false);
////                viewHolder.item = (TextView) convertView.findViewById(R.id.tv_text);
//                convertView.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//
//            viewHolder.item.setText(item);
//            viewHolder.item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    tvMsg.setText(item);
//                    drawer.closeDrawer(Gravity.LEFT);
//                }
//            });
//            return convertView;
//        }
//
//        class ViewHolder {
//            TextView item;
//        }
//    }

    //    private void initLayout() {
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle("Home");
//
//        View view = navigationView.getHeaderView(0);
//        view.findViewById(R.id.img_close_drawer).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawer.isDrawerOpen(GravityCompat.START)) {
//                    drawer.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
