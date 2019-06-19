package com.inflack.bcsforum;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class Constants {

    public static final String MY_PREF = "com.inflack.bcsforum.prefs";
    public static final String LAST_LOGIN_TIME = "last_login_time";

    public static boolean debugOn = true;

    public static void debugLog(String TAG, String message) {
        if (debugOn) {
            Log.d(TAG, "" + message);
        }
    }

    public static String getRootDirectory() {
        String rootPath = "";
        try {
            rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "bcs";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rootPath;
        }
    }
}
