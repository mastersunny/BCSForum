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
        memberDTOS.clear();
        memberDTOSCopy.clear();

        memberDTOS.add(new MemberDTO("মোহাম্মদ আব্দুল হাই","প্রকল্প পরিচালক (ডেপুটি সেক্রেটারি)","স্কিল ডেভেলপমেন্ট ফর মোবাইল গেইম এন্ড এপ্লিকেশন প্রজেক্ট", "১৫৪১৫","তথ্য ও যোগাযোগ প্রযুক্তি বিভাগ","নির্বাচন কমিশন, বাংলাদেশ","ফেনী","০১ জুলাই, ১৯৭৬","+৮৮০১৯২১৬৯১৮৭৮","hye22bcs@gmail.com","B+", "abdul"));
        memberDTOS.add(new MemberDTO("মোহাাম্মদ মমিনুর রহমান", "পরিচালক (উপ-সচিব)","প্রধানমন্ত্রীর কার্যালয়","১৫২০২","প্রধানমন্ত্রীর কার্যালয় ","","ময়মনসিংহ","০১ সেপ্টেম্বর, ১৯৭৩", "+৮৮০১৭১১২৬৯৮৬১","ominur15202@yahoo.com","A+","abdul"));
        memberDTOS.add(new MemberDTO("আ ফ ম ফজলে রাব্বী","প্রথম সচিব, বাংলাদেশ হাই কমিশন", "বাংলাদেশ হাই কমিশন","১৫২০৩","যুক্তরাজ্য","","লক্ষ্মীপুর","০৪ আগষ্ট, ১৯৭২","+৪৪৭৪৫৯৫৪৫১৭৭","rabbi15203@gmail.com","B+", "abdul"));
        memberDTOS.add(new MemberDTO("কাজী নিশাত রসুল","মাননীয় প্রধানমন্ত্রীর সহকারী একান্ত সচিব (উপ-সচিব)","প্রধানমন্ত্রীর কার্যালয়", "১৫৩২৫", "প্রধানমন্ত্রীর কার্যালয়","","নোয়াখালী","০১ জানুয়ারী","+৮৮০১৭০৮১৩৩২১২","nishat15325@gmail.com","B+", "abida"));
        memberDTOS.add(new MemberDTO("ড. আশরাফুল আলম","উপ-সচিব","মন্ত্রীপরিষদ বিভাগ ", "১৫২০৪","মন্ত্রীপরিষদ বিভাগ","","কিশোরগঞ্জ","০১ জানুয়ারী, ১৯৭৫","+৮৮০১৭১১০৩০৩৯১১","ashraful851@gmail.com","B+", "abdul"));

        memberDTOSCopy.addAll(memberDTOS);


//        for (int i = 0; i < 20; i++) {
//            MemberDTO memberDTO = new MemberDTO();
//            memberDTO.setName("Name");
//            if (i % 2 == 0) {
//                memberDTO.setImgUrl("abdul");
//            } else {
//                memberDTO.setImgUrl("abida");
//            }
//            memberDTO.setId(12121212);
//
//            memberDTOS.add(memberDTO);
//            memberDTOSCopy.add(memberDTO);
//        }

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
                intent.putExtra("MEMBER", memberDTOS.get(position));
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
                memberDTOS.clear();
                for (int i = 0; i < memberDTOSCopy.size(); i++) {
                    Log.d("TAG", "dfdsf");
                    if (memberDTOSCopy.get(i).getName().toLowerCase().contains(s.toLowerCase())) {
                        memberDTOS.add(memberDTOSCopy.get(i));
                    }
                }
                memberListAdapter.notifyDataSetChanged();
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
