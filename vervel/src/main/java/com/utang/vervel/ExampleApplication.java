package com.utang.vervel;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import static com.squareup.leakcanary.LeakCanary.install;

/**
 * Created by user on 2017/5/8.
 */

public class ExampleApplication extends Application {
    private static RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getmRefWatcher() {
        return mRefWatcher;
    }
}
