package zcy.applibrary.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

/**
 * Create 2019/4/23 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class ETUtils {

    //EditText过滤器 过滤空格
    public static InputFilter noSpace(){
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return source.equals(" ") ? "" : null;
            }
        };
    }

    //EditText过滤器 过滤回车
    public static InputFilter noEnter(){
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return source.toString().contentEquals("\n") ? "" : null;
            }
        };
    }

    //EditText长度过滤器
    public static InputFilter maxLength(final int length){
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return source.length() + dest.length() > length ? "" : null;
            }
        };
    }

    public static String text(EditText editText){
        return null == editText ? null : editText.getText().toString();
    }

}
