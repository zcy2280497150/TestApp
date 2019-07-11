package zcy.applibrary.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import zcy.applibrary.R;

/**
 * 剪切板工具
 * Create 2019/2/27 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class ClipUtils  {

    public static void copySave(Context context , String text){
        ClipboardManager cm = getClipboardManager(context);
        cm.setPrimaryClip(ClipData.newPlainText("label",text));
    }

    private static ClipboardManager getClipboardManager(Context context){
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }


}
