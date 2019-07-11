package zcy.applibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 自定义Toast
 * Created by zcy on 2017/6/5.
 */
public class MyToast {

    //开关
    private static boolean flag;
    //Toast对象
    private static Toast mToast;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void init(Context context){
        MyToast.context = context;
        flag = true;
    }

    //自定义时间
    private static void showToast(String text, int length) {
        if (null == context)return;
        if (flag) {
            if (mToast != null)
                mToast.cancel();
            mToast = Toast.makeText(context, text, length);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }

    //短时间的Toast
    public static void makeTextShort(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void makeTextShort(@StringRes int resId){
        if (null != context)makeTextShort(context.getResources().getString(resId));
    }

    public static void makeTextLong(@StringRes int resId){
        if (null != context)makeTextLong(context.getResources().getString(resId));
    }

    //长时间的Toast
    public static void makeTextLong(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

}
