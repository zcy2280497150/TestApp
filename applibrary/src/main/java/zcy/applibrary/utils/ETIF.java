package zcy.applibrary.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 可以用枚举来实现ETUtils的内容
 * Create 2019/7/3 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public enum ETIF {

    NO_SPACE{
        @Override
        public InputFilter filter() {
            return new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    return source.equals(" ") ? "" : null;
                }};
        }
    };

    public abstract InputFilter filter();

}
