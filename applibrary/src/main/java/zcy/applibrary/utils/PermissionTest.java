package zcy.applibrary.utils;

import android.Manifest;
import android.content.Context;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

/**
 * Create 2019/7/2 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class PermissionTest {

    public static void test(final Context context){
        AndPermission.with(context)
                .runtime()
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        MyLog.i("onGranted  onAction: " + data);
                        //权限申请成功
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        //权限申请失败
                        for (String permission : data) {
                            if (AndPermission.hasAlwaysDeniedPermission(context, permission)) {
                                MyToast.makeTextShort("您拒绝了相关权限，如要继续使用该功能，请前往设置中心手动为本应用打开该权限");
                                return;
                            }
                        }
                        MyToast.makeTextShort("您需要授予相关权限");
                    }
                }).start();
    }
}
