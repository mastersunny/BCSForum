package com.inflack.bcsforum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Utils {

    public static void startNotificationActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, NotificatonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mActivity.startActivity(intent);
    }
}
