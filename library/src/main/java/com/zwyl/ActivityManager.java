package com.zwyl;

import android.app.Activity;

import com.litesuits.android.log.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Activity管理，
 */
public enum ActivityManager {
    INSTANCE;
    private List<Activity> mList = new LinkedList<Activity>();

    public static ActivityManager getInstance() {
        return INSTANCE;
    }

    public void add(Activity activity) {
        mList.add(activity);
    }

    public void remove(Activity activity) {
        mList.remove(activity);
//        Log.i("ActivityManager","remove: " + activity.getLocalClassName());
    }

    public void exitAll() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
//            Log.i("ActivityManager","exitAll: " );
            mList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
