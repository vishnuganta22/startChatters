package com.company.vishnu.starchaters;

import android.app.Application;
import android.util.Log;

/**
 * Created by vishnu on 18/9/16.
 */

public class Global extends Application{
    private static final String LOG_LABEL = "global";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Util.TAG, LOG_LABEL + " onCreate Called");
    }
}
