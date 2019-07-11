package zcy.applibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Create 2019/3/9 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public abstract class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private SurfaceHolder mHolder;
    private Canvas mCanvas;//绘图的画布
    private boolean mIsDrawing;//控制绘画线程的标志位

    public BaseSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing){
            long startTime = System.currentTimeMillis();
            draw();
            while (System.currentTimeMillis() - startTime < 30L){
                Thread.yield();//耗时少于刷新间隔，线程等待
            }
        }
    }

    protected void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != mCanvas){
                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                upUi(mCanvas);
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    protected abstract void upUi(Canvas canvas);

}
