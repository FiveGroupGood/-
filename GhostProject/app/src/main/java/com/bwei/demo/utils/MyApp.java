package com.bwei.demo.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by ${李晨阳} on 2017/12/13.
 */

public class MyApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
