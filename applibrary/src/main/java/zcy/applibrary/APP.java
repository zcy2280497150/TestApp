package zcy.applibrary;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDexApplication;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;

import zcy.applibrary.utils.BitmapUtils;
import zcy.applibrary.utils.MyLog;
import zcy.applibrary.utils.MyToast;

/**
 * Create 2019/6/11 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class APP extends MultiDexApplication {

    private static APP app;

    public static APP getInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        BitmapUtils.init(getResources().getDisplayMetrics().density);
        MyToast.init(this);
        MyLog.init(isApkInDebug(this) , getString(R.string.log_tag));
        initOkGo();

    }

    //判断当前应用是否是debug状态
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void initOkGo() {
        //设置统一请求头
        HttpHeaders headers = new HttpHeaders();
        headers.put(getString(R.string.client_key),getString(R.string.client_value));
        OkGo.getInstance().init(this).addCommonHeaders(headers);
    }

    public static int getVersionCode() {
        PackageInfo info = getPackageInfo();
        return null == info ? -1 : info.versionCode;
    }

    public static String getVersionName() {
        PackageInfo info = getPackageInfo();
        return null == info ? null : info.versionName;
    }

    private static PackageInfo getPackageInfo() {
        try {
            return app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
