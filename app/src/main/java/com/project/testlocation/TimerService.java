package com.project.testlocation;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xgw on 2019/9/21.
 */

public class TimerService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTimer();
    }

    private String shop_bgm = "interview.vio.com.shop";

    private int position = 1;
    private int backPosition = 1;
    private int time = 10;
    private Timer timer;

    private int number = 0;
    private int currentHour = 0;

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                //小时
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                Log.d("xgw","hour:"+hour);
                //分钟
                final int minute = calendar.get(Calendar.MINUTE);
                if (isStartApp) {
                    if (backPosition < 2) {
                        backPosition++;
                    } else {
                        Intent intent = new Intent("com.back");
                        sendBroadcast(intent);
                        isStartApp = false;
                        backPosition = 1;
                    }
                } else {
                    if (position < 5) {
                        position++;
                    } else {
                        wakeUpAndUnlock();
                        if (hour == 6 || hour == 12 || hour == 18 || hour == 24 || hour == 0) {
                            if (minute < 10) {
                                Utils.getInstance().setMinute(minute);
                                startOtherApp();
                            }
                        }
                        position = 1;
                    }
                }
            }
        }, time * 1000, time * 1000);
    }

    private String bgm = "io.dcloud.H51E9B491";
    private boolean isStartApp;
    private void startOtherApp() {
        Intent intent = getPackageManager().getLaunchIntentForPackage(bgm);
        if (intent != null) {
            intent.putExtra("type", "110");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            isStartApp = true;
        }


    }

    /**
     * 唤醒手机屏幕并解锁
     */
    @SuppressLint("InvalidWakeLockTag")
    public void wakeUpAndUnlock() {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) getApplicationContext()
                .getSystemService(Context.POWER_SERVICE);
        boolean screenOn = false;
        if (pm != null) {
            screenOn = pm.isScreenOn();
        }
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            PowerManager.WakeLock wl = null;
            if (pm != null) {
                wl = pm.newWakeLock(
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            }
            if (wl != null) {
                wl.acquire(10000); // 点亮屏幕
                wl.release(); // 释放
            }
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext()
                .getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = null;
        if (keyguardManager != null) {
            keyguardLock = keyguardManager.newKeyguardLock("unLock");
        }
        // 屏幕锁定
        if (keyguardLock != null) {
            keyguardLock.reenableKeyguard();
            keyguardLock.disableKeyguard(); // 解锁
        }
    }
}
