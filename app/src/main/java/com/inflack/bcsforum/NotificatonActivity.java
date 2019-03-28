package com.inflack.bcsforum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.inflack.bcsforum.R;
import com.inflack.bcsforum.model.MemberDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificatonActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_notification)
    RecyclerView recyclerView;

    NotificationListAdapter notificationListAdapter;
    List<MemberDTO> memberDTOS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaton);
        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("নোটিফিকেশন");
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        notificationListAdapter = new NotificationListAdapter(this, memberDTOS);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notificationListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (memberDTOS.size() <= 0) {
            getData();
        }
    }

    private void getData() {
        memberDTOS.clear();
        memberDTOS.add(new MemberDTO("মোহাম্মদ আব্দুল হাই", "প্রকল্প পরিচালক (ডেপুটি সেক্রেটারি)", "স্কিল ডেভেলপমেন্ট ফর মোবাইল গেইম এন্ড এপ্লিকেশন প্রজেক্ট", "১৫৪১৫", "তথ্য ও যোগাযোগ প্রযুক্তি বিভাগ", "নির্বাচন কমিশন, বাংলাদেশ", "ফেনী", "০১ জুলাই, ১৯৭৬", "+৮৮০১৯২১৬৯১৮৭৮", "hye22bcs@gmail.com", "B+", "abdul"));
        memberDTOS.add(new MemberDTO("মোহাাম্মদ মমিনুর রহমান", "পরিচালক (উপ-সচিব)", "প্রধানমন্ত্রীর কার্যালয়", "১৫২০২", "প্রধানমন্ত্রীর কার্যালয় ", "", "ময়মনসিংহ", "০১ সেপ্টেম্বর, ১৯৭৩", "+৮৮০১৭১১২৬৯৮৬১", "ominur15202@yahoo.com", "A+", "abdul"));
        memberDTOS.add(new MemberDTO("আ ফ ম ফজলে রাব্বী", "প্রথম সচিব, বাংলাদেশ হাই কমিশন", "বাংলাদেশ হাই কমিশন", "১৫২০৩", "যুক্তরাজ্য", "", "লক্ষ্মীপুর", "০৪ আগষ্ট, ১৯৭২", "+৪৪৭৪৫৯৫৪৫১৭৭", "rabbi15203@gmail.com", "B+", "abdul"));
        memberDTOS.add(new MemberDTO("কাজী নিশাত রসুল", "মাননীয় প্রধানমন্ত্রীর সহকারী একান্ত সচিব (উপ-সচিব)", "প্রধানমন্ত্রীর কার্যালয়", "১৫৩২৫", "প্রধানমন্ত্রীর কার্যালয়", "", "নোয়াখালী", "০১ জানুয়ারী", "+৮৮০১৭০৮১৩৩২১২", "nishat15325@gmail.com", "B+", "abida"));
        memberDTOS.add(new MemberDTO("ড. আশরাফুল আলম", "উপ-সচিব", "মন্ত্রীপরিষদ বিভাগ ", "১৫২০৪", "মন্ত্রীপরিষদ বিভাগ", "", "কিশোরগঞ্জ", "০১ জানুয়ারী, ১৯৭৫", "+৮৮০১৭১১০৩০৩৯১১", "ashraful851@gmail.com", "B+", "abdul"));


        if (notificationListAdapter != null) {
            notificationListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
