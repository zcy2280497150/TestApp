package zcy.applibrary.holder;

import android.support.annotation.NonNull;

/**
 * Create 2019/7/1 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class Holder<T> {

    private T t;

    public T getT() {
        return t;
    }

    public Holder(@NonNull T t) {
        this.t = t;
    }
}
