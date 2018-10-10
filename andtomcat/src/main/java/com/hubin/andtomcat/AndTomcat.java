package com.hubin.andtomcat;

import android.app.Application;
import android.content.Context;

/**
 * @项目名： WebServer
 * @包名： com.hubin.andtomcat
 * @文件名: AndTomcat
 * @创建者: 胡英姿
 * @创建时间: 2018/10/9 10:10
 * @描述： 入口 初始化
 */
public class AndTomcat {
    private static Application mApp;
    private static Context mContext;
    private static Boolean DEBUG = true;

    public static void init(Application app, boolean isDebug) {
        mApp=app;
        init(app.getApplicationContext(),isDebug);
    }

    public static void init(Context context, boolean isDebug) {
        mContext=context;
        DEBUG = isDebug;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Boolean isDebug() {
        return DEBUG;
    }
}
