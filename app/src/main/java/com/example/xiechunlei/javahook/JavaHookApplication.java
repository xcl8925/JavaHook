package com.example.xiechunlei.javahook;

import android.app.Application;
import android.content.Context;

import com.example.xiechunlei.javahook.util.HookUtil;

public class JavaHookApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        new HookUtil().startJavaHook();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}

