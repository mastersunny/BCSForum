package com.inflack.bcsforum;

import android.app.Activity;
import android.content.Intent;

import com.inflack.bcsforum.model.CommitteeDTO;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<CommitteeDTO> committeeDTOS = new ArrayList<>();

    public static void startNotificationActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mActivity.startActivity(intent);
    }
}
