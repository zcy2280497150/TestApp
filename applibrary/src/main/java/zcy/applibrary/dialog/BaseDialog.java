package zcy.applibrary.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import zcy.applibrary.activity.BaseActivity;

public class BaseDialog extends DialogFragment implements BaseActivity.OnSoftKeyboardStateChangedListener {

    private BDVH holder;

    public BaseDialog setHolder(BDVH holder) {
        this.holder = holder;
        return this;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (null == getDialog() || !getDialog().isShowing()) {
            super.show(manager, tag);
        }
    }

    @Override
    public void dismiss() {
        if (null != getDialog() && getDialog().isShowing()) {
            if (isOnResume){
                isDismiss = false;
            }else {
                isDismiss = true;
            }
        }
    }

    private boolean isDismiss;//是否需要进行Dismiss
    private boolean isOnResume;
    @Override
    public void onResume() {
        super.onResume();
        isOnResume = true;
        if (isDismiss){
            dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isOnResume = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity){
            ((BaseActivity) activity).addSoftKeyboardChangedListener(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity){
            ((BaseActivity) activity).removeSoftKeyboardChangedListener(this);
        }
    }

    @Override
    public void OnSoftKeyboardStateChanged(boolean isKeyBoardShow, int keyboardHeight) {
        if (null != getDialog() && getDialog().isShowing() && holder instanceof BaseActivity.OnSoftKeyboardStateChangedListener){
            ((BaseActivity.OnSoftKeyboardStateChangedListener) holder).OnSoftKeyboardStateChanged(isKeyBoardShow,keyboardHeight);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return holder.init(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(holder.isCancel());//设置外部点击取消

        Window win = dialog.getWindow();

        //必须设置bg，要不然其他属性不生效
        Objects.requireNonNull(win).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DisplayMetrics dm = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = holder.getGravity();//设置布局位置
        params.width = WindowManager.LayoutParams.MATCH_PARENT;//宽度充满全屏
        params.height = holder.isFullScreen() ? WindowManager.LayoutParams.MATCH_PARENT : WindowManager.LayoutParams.WRAP_CONTENT;//高度包裹内容
        if (holder.isBgTransparent()) {
            params.dimAmount = 0.0f;
        }
        if (holder.isBgTransparent() || holder.isFullScreen()){
            setStatusBarTxtBlack(win,holder.isBlack());
        }
        win.setAttributes(params);
        dialog.setOnKeyListener(null != onKeyListener ? onKeyListener : new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (holder.isCancel()){
                        holder.onDismiss();
                    }
                    return !holder.isCancel();
                }
                return false;
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                holder.onDismiss();
            }
        });
    }

    private DialogInterface.OnKeyListener onKeyListener;

    public BaseDialog setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
        return this;
    }

    //设置状态栏为黑色字体，背景全透明
    private void setStatusBarTxtBlack(Window win , boolean isBlack) {
        if (Build.VERSION.SDK_INT>21){
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            win.setStatusBarColor(Color.TRANSPARENT);
        }else {
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (isBlack) {
            //状态栏透明，黑色字体
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }

    /*********IllegalStateException: Can not perform this action after onSaveInstanceState**********/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
    }

    private Method noteStateNotSavedMethod;
    private Object fragmentMgr;
    private String[] activityClassName = {"Activity", "FragmentActivity"};

    private void invokeFragmentManagerNoteStateNotSaved() {
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod.invoke(fragmentMgr);
                return;
            }
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!(activityClassName[0].equals(cls.getSimpleName())
                    || activityClassName[1].equals(cls.getSimpleName())));

            Field fragmentMgrField = prepareField(cls, "mFragments");
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(this);
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr, "noteStateNotSaved");
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod.invoke(fragmentMgr);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Field prepareField(Class<?> c, String fieldName) throws NoSuchFieldException {
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } finally {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }

    private Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
