package zcy.applibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import zcy.applibrary.R;

/**
 * Create 2019/3/8 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class DynamicHoneycombView extends View{

    public DynamicHoneycombView(Context context) {
        super(context);
        init();
    }

    public DynamicHoneycombView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicHoneycombView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Hexagon hexagon;

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.dp_2));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                if (count >= 1000)count=0;
                postInvalidate();
            }
        },20 , 20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cx = getWidth()/2f;
        float cy = getHeight()/2f;

        if (null == hexagon){
            hexagon = new Hexagon(cx , cy , Math.min(getWidth(),getHeight())/4f);
        }

        canvas.save();
        canvas.drawPath(hexagon.path,paint);
        canvas.rotate(60 , cx,cy);
        canvas.drawPath(hexagon.path,paint);
        canvas.rotate(60 , cx,cy);
        canvas.drawPath(hexagon.path,paint);
        canvas.rotate(60 , cx,cy);
        canvas.drawPath(hexagon.path,paint);
        canvas.rotate(60 , cx,cy);
        canvas.drawPath(hexagon.path,paint);
        canvas.rotate(60 , cx,cy);
        canvas.drawPath(hexagon.path,paint);
        canvas.rotate(60 , cx,cy);
        canvas.translate(-(count/1000f) * hexagon.rtx , 0f);
        canvas.drawPath(hexagon.path,paint);
        canvas.restore();

    }

    Paint paint;

    public static final float SQRT3 = (float) Math.sqrt(3);

    public Timer timer = new Timer();

    public int count;


    class Hexagon{

        float x;
        float y;
        float r;
        float rtx;

        Path path;

        public Hexagon(float x, float y , float r) {

            path = new Path();
            this.x = x;
            this.y = y;
            this.r = r;
            rtx = r/3f;

            path.moveTo(x - r/2f , y - r/2f*SQRT3);
            path.rLineTo(r/6f , 0);
            path.rMoveTo(r/6f , 0);
            path.rLineTo(r/6f , 0);
            path.rMoveTo(r/6f , 0);
            path.rLineTo(r/6f , 0);
            path.rMoveTo(r/6f , 0);
            path.rLineTo(r/6f , 0);
            path.rMoveTo(r/6f , 0);

        }


    }




}
