package com.example.xiechunlei.javahook.util;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookUtil {

    public void startJavaHook() {
        hookActivityManager();
    }

    private void hookActivityManager() {
        try {
            Class activityManagerNative_cls = Class.forName("android.app.ActivityManagerNative");
            if (activityManagerNative_cls != null) {
                Field gDefault_Field = activityManagerNative_cls.getDeclaredField("gDefault");
                gDefault_Field.setAccessible(true);
                Object gDefault = gDefault_Field.get(null);
                Class singleton_cls = Class.forName("android.util.Singleton");
                Field mInstance = singleton_cls.getDeclaredField("mInstance");
                mInstance.setAccessible(true);
                Object IActivityManager = mInstance.get(gDefault);
                Class IActivityManager_cls = Class.forName("android.app.IActivityManager");
                Object hookedIActivityManager = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class[]{IActivityManager_cls}, new AMInvocationHandler(IActivityManager));
                mInstance.set(gDefault, hookedIActivityManager);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public class AMInvocationHandler implements InvocationHandler {

        private Object iActivityManager;

        private AMInvocationHandler(Object iActivityManager) {
            this.iActivityManager = iActivityManager;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws InvocationTargetException, IllegalAccessException {
            Log.i("HookUtil", method.getName());
            //我要在这里搞点事情
            if ("startActivity".contains(method.getName())) {
                Log.e("HookUtil", "Activity已经开始启动");
                Log.e("HookUtil", "小弟到此一游！！！");
                for (int i = 0; i < objects.length; i++) {
                    Object o1 = objects[i];
                    if (o1 instanceof Intent) {
                        Intent intent = (Intent) o1;
                    }
                }
//                return 0;
            }

            return method.invoke(iActivityManager, objects);
        }
    }
}
