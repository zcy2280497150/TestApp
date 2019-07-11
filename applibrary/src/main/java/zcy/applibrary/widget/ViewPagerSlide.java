package zcy.applibrary.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

public class ViewPagerSlide extends ViewPager {

    //是否可以进行滑动
    private boolean isSlide;

    public void setSlide(boolean slide) {
        isSlide = slide;
    }

    public ViewPagerSlide(@NonNull Context context) {
        super(context);
    }

    public ViewPagerSlide(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return isSlide && super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSlide && super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        lastAnimation = System.currentTimeMillis();
        requestDisallowInterceptTouchEvent(!isSlide);
        return super.dispatchTouchEvent(ev);
    }

    private long lastAnimation;
    private Timer timer;

    public void start(){
        if (null != timer)return;
        PagerAdapter adapter = getAdapter();
        if (null == adapter)return;
        final int count = adapter.getCount();
        if (count < 2)return;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l = System.currentTimeMillis();
                if (l - lastAnimation > 3000L){
                    lastAnimation = l;
                    post(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentItem((getCurrentItem() + 1)%count);
                        }
                    });
                }
            }
        },2000L , 1000L);
    }



}
