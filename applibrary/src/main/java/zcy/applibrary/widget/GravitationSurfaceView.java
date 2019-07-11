package zcy.applibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create 2019/3/8 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class GravitationSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private List<Po> pos;//点集
    private Paint p;//画笔
    private Random random;

    private SurfaceHolder mHolder;
    private Canvas mCanvas;//绘图的画布
    private boolean mIsDrawing;//控制绘画线程的标志位
    private float density;

    public GravitationSurfaceView(Context context) {
        super(context);
        init();
    }

    public GravitationSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GravitationSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        density = getResources().getDisplayMetrics().density;
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(density*4);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setColor(Color.WHITE);
        random = new Random();
        pos = new ArrayList<>();

        //模拟随机生成50个点
        for (int i = 0 ; i < 50 ; i++ ){
            Po po = new Po(nextFloat(),nextFloat(),nextFloat(),random.nextBoolean() ? nextFloat()/1000f : -nextFloat()/1000f,random.nextBoolean() ? nextFloat()/1000f : -nextFloat()/1000f );
            pos.add(po);
        }

    }

    private float nextFloat(){
        return random.nextFloat();
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
            for (Po o: pos){
                o.run();
            }
            draw();
            while (System.currentTimeMillis() - startTime < 30L){
                Thread.yield();//耗时少于刷新间隔，线程等待
            }
        }
    }

    private void draw(){
        try {
            mCanvas = mHolder.lockCanvas();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != mCanvas){

                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                float width = getWidth();
                float height = getHeight();
                //外层循环绘制每一个点

                //手指触摸的点，取局部变量赋值，以防止在绘制的过程中坐标和状态已经发生了改变，而出现错乱
                boolean isd = isDown;
                float x = this.x;
                float y = this.y;
                float unitX = x/width;
                float unitY = y/height;


                for (int i = 0 ; i < pos.size() ; i++){
                    Po po = pos.get(i);
                    p.setStrokeWidth(4*density);//设置点的大小
                    mCanvas.drawPoint(po.x * width , po.y* height ,p);//绘制点
                    //内层循环会将该点与后面的点逐一比较，计算距离
                    for (int j = i+ 1; j < pos.size() ; j++){
                        Po poj = pos.get(j);
                        float lenght = po.lenght(poj);
                        //距离在0f 和 0.2f之间的点就会被连线（这个值可根据需求随意调整）
                        if (lenght > 0f && lenght < 0.2f){
                            p.setStrokeWidth(5f*(0.2f - lenght));//距离越近，线越粗
                            mCanvas.drawLine(po.x*width,po.y*height,poj.x*width,poj.y*height,p);//绘制线
                        }
                    }

                    //如果是按下状态，还需要将该点与触摸点比较，满足条件需要连接
                    if (isd){
                        float lenght = po.lenght(unitX, unitY);
                        if (lenght > 0f && lenght < 0.3f){
                            p.setStrokeWidth(5f*(0.3f - lenght));
                            mCanvas.drawLine(po.x*width,po.y*height,x,y,p);
                        }
                    }
                }

                p.setStrokeWidth(5*density);//设置点的大小
                if (isd) mCanvas.drawPoint(x,y ,p);

                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                break;
        }
        return true;
    }

    private boolean isDown;
    private float x;
    private float y;

}
