package zcy.applibrary.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;



/**
 * Created by zcy on 2017/9/10.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private int screenHeight;

    @Override
    public void onClick(View v) {

    }

    protected void setOnClick(@IdRes int... ids){
        for (int id : ids){
            findViewById(id).setOnClickListener(this);
        }
    }

//    @SuppressLint("MissingSuperCall")
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
////        super.onSaveInstanceState(outState);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarTxtBlack(true);
        super.onCreate(savedInstanceState);
        MIUISetStatusBarLightMode(this.getWindow(), true);
        registerKeyboard();//注册软键盘状态监听
        if (null != iAppLanguage)iAppLanguage.upLanguage(this);
    }

    private static IAppLanguage iAppLanguage;

    public static void init(IAppLanguage iAppLanguage){
        BaseActivity.iAppLanguage = iAppLanguage;
    }

    public interface IAppLanguage{
        void upLanguage(Context context);
    }

    //小米手机
    public static void MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        //for new api versions.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        //                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onDestroy() {
        removeKeyboard();
        super.onDestroy();
    }

//    //-------------------------------------------------------------------------------------------
//    static IAppBackGround iAppBackGround;
//
//    public static void setAppBackGround(IAppBackGround _iAppBackGround) {
//        iAppBackGround = _iAppBackGround;
//    }
//
//    public interface IAppBackGround {
//        void onBackground(boolean isBackground);
//    }


    //设置状态栏为黑色字体，背景全透明
    public void setStatusBarTxtBlack(boolean isBlack) {
        if (Build.VERSION.SDK_INT>21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (isBlack) {
            //状态栏透明，黑色字体
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }

    //-------------------------------------------- 软键盘状态相关 -----------------------------------------------
    private ArrayList<OnSoftKeyboardStateChangedListener> mKeyboardStateListeners;      //软键盘状态监听列表
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean mIsSoftKeyboardShowing;

    public interface OnSoftKeyboardStateChangedListener {
        void OnSoftKeyboardStateChanged(boolean isKeyBoardShow, int keyboardHeight);
    }

    //注册软键盘状态变化监听
    public void addSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (null != listener && !mKeyboardStateListeners.contains(listener)) {
            mKeyboardStateListeners.add(listener);
        }
    }

    //取消软键盘状态变化监听
    public void removeSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (null != listener) {
            mKeyboardStateListeners.remove(listener);
        }
    }


    //注册布局变化监听(主要是监听软键盘状态变化)
    private void registerKeyboard() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenHeight = Objects.requireNonNull(windowManager).getDefaultDisplay().getHeight();
        mIsSoftKeyboardShowing = false;
        mKeyboardStateListeners = new ArrayList<>();
        mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //判断窗口可见区域大小
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
                //                int heightDifference = screenHeight - (r.bottom - r.top);
                int heightDifference = screenHeight - r.bottom;//沉浸式状态栏有区别
                isKeyboardShowing = heightDifference > screenHeight / 3;

                bottomY = r.bottom;

                //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
                if ((mIsSoftKeyboardShowing && !isKeyboardShowing) || (!mIsSoftKeyboardShowing && isKeyboardShowing)) {
                    mIsSoftKeyboardShowing = isKeyboardShowing;
                    for (int i = 0; i < mKeyboardStateListeners.size(); i++) {
                        OnSoftKeyboardStateChangedListener listener = mKeyboardStateListeners.get(i);
                        listener.OnSoftKeyboardStateChanged(mIsSoftKeyboardShowing, heightDifference);
                    }
                }
            }
        };
        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
    }

    private boolean isKeyboardShowing;
    private int bottomY;

    public boolean isKeyboardShowing() {
        return isKeyboardShowing;
    }

    private void removeKeyboard() {
        //移除布局变化监听
        getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutChangeListener);
        if (null != mKeyboardStateListeners)
            mKeyboardStateListeners.clear();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return isKeyboardShowing && MotionEvent.ACTION_DOWN == ev.getAction() || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction()){
            //监听到抬起动作
            if (event.getRawY() <= bottomY ){
                hideKeyboard();
            }
        }
        return super.onTouchEvent(event);
    }

    public void hideKeyboard(){
        if (isKeyboardShowing){
            InputMethodManager systemService = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = getCurrentFocus();
            if (null != view && null != systemService) systemService.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
