package zcy.applibrary.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 自定义的日志输出类，添加开关，使用的时候在项目初始化的时候调用一次就可以了
 * Created by zcy on 2017/7/5.
 */
public class MyLog {

    private static String TAG = MyLog.class.getSimpleName();
    private static boolean isOpenDebug;

    public static void init(boolean isOpenDebug){
        MyLog.isOpenDebug = isOpenDebug;
    }
    public static void init(boolean isOpenDebug , String tag){
        if (!TextUtils.isEmpty(tag)){
            TAG = tag;
        }
        init(isOpenDebug);
    }

    public static void v(String msg){
        v(TAG, msg);
    }
    public static void v(String tag , String msg){
        if (isOpenDebug) Log.v(tag, null == msg ? "" : msg);
    }

    public static void d(String msg){
        d(TAG, msg);
    }
    public static void d(String tag , String msg){
        if (isOpenDebug) Log.d(tag, null == msg ? "" : msg);
    }

    public static void i(String msg){
        i(TAG, msg);
    }
    public static void i(String tag , String msg){
        if (isOpenDebug) Log.i(tag, null == msg ? "" : msg);
    }

    public static void w(String msg){
        w(TAG, msg);
    }
    public static void w(String tag , String msg){
        if (isOpenDebug) Log.w(tag, null == msg ? "" : msg);
    }

    public static void e(String msg){
        e(TAG, msg);
    }
    public static void e(String tag , String msg){
        if (isOpenDebug) Log.e(tag, null == msg ? "" : msg);
    }

}
