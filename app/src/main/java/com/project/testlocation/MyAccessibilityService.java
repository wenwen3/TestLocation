package com.project.testlocation;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 这个服务是不需要你在activity里去开启的，属于系统级别辅助服务 需要在设置里去手动开启 和我们平常app里
 * 经常使用的service 是有很大不同的 非常特殊
 * 你可以在 \sdk\samples\android-23\legacy\ApiDemos 这样的目录下 找到这个工程 这个工程下面有一个accessibility
 * 包 里面有关于这个服务的demo 当然他们那个demo 非常复杂，但是信息量很大，有兴趣深入研究的同学可以多看demo
 * 我这里只实现最基本的功能 且没有做冗余和异常处理，只包含基础功能，不能作为实际业务上线！
 */
public class MyAccessibilityService extends AccessibilityService {

    private Handler handler;

    public MyAccessibilityService() {
    }

    /**
     * AccessibilityService 这个服务可以关联很多属性，这些属性 一般可以通过代码在这个方法里进行设置，
     * 我这里偷懒 把这些设置属性的流程用xml 写好 放在manifest里，如果你们要使用的时候需要区分版本号
     * 做兼容，在老的版本里是无法通过xml进行引用的 只能在这个方法里手写那些属性 一定要注意.
     * 同时你的业务如果很复杂比如需要初始化广播啊之类的工作 都可以在这个方法里写。
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    /**
     * 当你这个服务正常开启的时候，就可以监听事件了，当然监听什么事件，监听到什么程度 都是由给这个服务的属性来决定的，
     * 我的那些属性写在xml里了。
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        /**
         * 事件是分很多种的，我这里是最简单的那种，只演示核心功能，如果要做成业务上线 这里推荐一个方法可以快速理解这里的type属性。
         * 把这个type的int 值取出来 并转成16进制，然后去AccessibilityEvent 源码里find。顺便看注释 ，这样是迅速理解type类型的方法
         */
        if (event == null) {
            Log.d("xgw", "event == null:");
            return;
        }
        int eventType = event.getEventType();
        Log.d("xgw", "eventType:" + eventType);
        if (event.getSource() == null) {
            Log.d("xgw", "event.getSource():");
            return;
        }
        //这个地方没什么好说的 你就理解成 找到当前界面 包含有安装 这个关键词的 所有节点就可以了。返回这些节点的list
        //注意这里的find 其实是contains的意思，比如你界面上有2个节点，一个节点内容是安装1 一个节点内容是安装2，那这2个节点是都会返回过来的
        //除了有根据Text找节点的方法 还有根据Id找节点的方法。考虑到众多手机rom都不一样，这里需要大家多测试一下，有的rom packageInstall
        //定制的比较深入，可能和官方rom里差的很远 这里就要做冗余处理，可以告诉大家一个小技巧 你就把这些rom的 安装器打开 然后
        //通过ddms里 看view结构的按钮 直接进去看就行了，可以直接看到那个界面属于哪个包名，也可以看到你要捕获的那个按钮的id是什么 很方便！

        if(Utils.getInstance().getMinute() == 1) {
            List<AccessibilityNodeInfo> list = event.getSource().findAccessibilityNodeInfosByText("美国");
            for (AccessibilityNodeInfo info : list) {
                if (info == null || info.getText() == null) {
                    continue;
                }
                if (info.getText().toString().equals("美国")) {
                    //找到你的节点以后 就直接点击他就行了
                    Log.d("xgw", "info:" + info.getText().toString());
                    info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }else if(Utils.getInstance().getMinute() == 2) {
            List<AccessibilityNodeInfo> list5 = event.getSource().findAccessibilityNodeInfosByText("矿山");
            for (AccessibilityNodeInfo info : list5) {
                if (info == null || info.getText() == null) {
                    continue;
                }
                if (info.getText().toString().equals("矿山")) {
                    //找到你的节点以后 就直接点击他就行了
                    Log.d("xgw", "info:" + info.getText().toString());
                    info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }else if(Utils.getInstance().getMinute() == 3) {
            List<AccessibilityNodeInfo> list6 = event.getSource().findAccessibilityNodeInfosByText("社区");
            for (AccessibilityNodeInfo info : list6) {
                if (info == null || info.getText() == null) {
                    continue;
                }
                if (info.getText().toString().equals("社区")) {
                    //找到你的节点以后 就直接点击他就行了
                    Log.d("xgw", "info:" + info.getText().toString());
                    info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }else if(Utils.getInstance().getMinute() == 4) {
            List<AccessibilityNodeInfo> list7 = event.getSource().findAccessibilityNodeInfosByText("购物中心");
            for (AccessibilityNodeInfo info : list7) {
                if (info == null || info.getText() == null) {
                    continue;
                }
                if (info.getText().toString().equals("购物中心")) {
                    //找到你的节点以后 就直接点击他就行了
                    Log.d("xgw", "info:" + info.getText().toString());
                    info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }else if(Utils.getInstance().getMinute() == 5) {
            List<AccessibilityNodeInfo> list4 = event.getSource().findAccessibilityNodeInfosByText("香港");
            if (null != list4) {
                for (AccessibilityNodeInfo info : list4) {
                    if (info == null || info.getText() == null) {
                        continue;
                    }
                    if (info.getText().toString().equals("香港")) {
                        //找到你的节点以后 就直接点击他就行了
                        Log.d("xgw", "info:" + info.getText().toString());
                        info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }else if(Utils.getInstance().getMinute() == 6) {
            List<AccessibilityNodeInfo> list3 = event.getSource().findAccessibilityNodeInfosByText("法国");
            if (null != list3) {
                for (AccessibilityNodeInfo info : list3) {
                    if (info == null || info.getText() == null) {
                        continue;
                    }
                    if (info.getText().toString().equals("法国")) {
                        //找到你的节点以后 就直接点击他就行了
                        Log.d("xgw", "info:" + info.getText().toString());
                        info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }else if(Utils.getInstance().getMinute() == 7) {
            List<AccessibilityNodeInfo> list2 = event.getSource().findAccessibilityNodeInfosByText("埃及");
            if (null != list2) {
                for (AccessibilityNodeInfo info : list2) {
                    if (info == null || info.getText() == null) {
                        continue;
                    }
                    if (info.getText().toString().equals("埃及")) {
                        //找到你的节点以后 就直接点击他就行了
                        Log.d("xgw", "info:" + info.getText().toString());
                        info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }
    }

    private void sendClick() {
//        Message msg = handler.obtainMessage();
//        msg.what = 1;
//        handler.sendMessageDelayed(msg, 3000);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(new MyBroadCastReceiver(),new IntentFilter("com.back"));
        handler = new Handler() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Toast.makeText(getApplicationContext(), "点击了", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }

    public class MyBroadCastReceiver extends BroadcastReceiver{

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
    }

    private String shop_bgm = "interview.vio.com.shop";
    private String bgm = "io.dcloud.H51E9B491";

    @Override
    public void onInterrupt() {

    }
}