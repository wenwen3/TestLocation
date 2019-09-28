package com.project.testlocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
        findViewById(R.id.openApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOtherApp();
            }
        });
        Intent intent1 = new Intent(this, TimerService.class);
        startService(intent1);

        // 申请文件读写权限（部分朋友遇到相册选图需要读写权限的情况，这里一并写一下）
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WAKE_LOCK}, 123);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.DISABLE_KEYGUARD) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.DISABLE_KEYGUARD}, 133);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
                    // 申请权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WAKE_LOCK}, 123);
                }
                }
                break;
                case 123:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.DISABLE_KEYGUARD) != PackageManager.PERMISSION_GRANTED) {
                            // 申请权限
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.DISABLE_KEYGUARD}, 133);
                        }
                    }
                break;
                case 133:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RESTART_PACKAGES) != PackageManager.PERMISSION_GRANTED) {
                            // 申请权限
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RESTART_PACKAGES}, 144);
                        }
                    }
                break;
        }
    }

    private void startOtherApp() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("io.dcloud.H51E9B491");
        if (intent != null) {
            intent.putExtra("type", "110");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


}
