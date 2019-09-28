package com.project.testlocation;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/**
 * Created by xgw on 2019/9/22.
 */

public class CusApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashManager crashHandler = new CrashManager(this);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }


}
