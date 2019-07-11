package zcy.applibrary.dialog;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zcy.applibrary.holder.ViewHolder;

/**
 * BaseDialogHolder
 * Create 2019/7/1 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public abstract class BDVH{

    protected abstract @LayoutRes int layoutId();
    private ViewHolder holder;
    private BaseDialog dialog;

    private int gravity = Gravity.BOTTOM;
    private boolean isBgTransparent;//背景是否全透明（默认false）
    private boolean isFullScreen;//高度是否全屏(true全屏，false非全屏)
    private boolean cancel;//外部点击取消，false - 不要该事件（默认不要该事件）
    private boolean isBlack;//状态栏字体颜色

    public View init(@NonNull BaseDialog dialog){
        this.dialog = dialog;
        holder = new ViewHolder(LayoutInflater.from(dialog.getContext()).inflate(layoutId(),null));
        initView(holder);
        return holder.getT();
    }

    public abstract void initView(ViewHolder holder);

    public int getGravity() {
        return gravity;
    }

    public BDVH setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public boolean isBgTransparent() {
        return isBgTransparent;
    }

    public BDVH setBgTransparent(boolean bgTransparent) {
        isBgTransparent = bgTransparent;
        return this;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public BDVH setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        return this;
    }

    public boolean isCancel() {
        return cancel;
    }

    public BDVH setCancel(boolean cancel) {
        this.cancel = cancel;
        return this;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public BDVH setBlack(boolean black) {
        isBlack = black;
        return this;
    }


    private void dismiss(){
        dialog.dismiss();
    }

    protected void onDismiss(){
        holder.clear();
    }

}
