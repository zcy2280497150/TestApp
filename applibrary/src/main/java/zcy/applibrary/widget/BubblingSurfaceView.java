package zcy.applibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import zcy.applibrary.R;

/**
 * Create 2019/3/9 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class BubblingSurfaceView extends BaseSurfaceView {

    public BubblingSurfaceView(Context context) {
        super(context);
    }

    public BubblingSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubblingSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        bubblings = new ArrayList<>();
        random = new Random(9);
        for (int i = 0 ; i < 30 ; i++){
            bubblings.add(new Bubbling(0.8f + 0.2f*random.nextFloat() , 2f * random.nextFloat() , 0.01f + 0.03f*(random.nextFloat())  , 0.002f + 0.002f*random.nextFloat() , -0.03f - 0.01f*random.nextFloat() , 0.8f,1f));
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private List<Bubbling> bubblings;
    private Paint paint;
    private RectF rectF;
    private Random random;

    @Override
    protected void draw() {
        super.draw();
    }

    @Override
    protected void upUi(Canvas canvas) {
        float width = getWidth();
        if (null == rectF){
            rectF = new RectF();
        }
        for (Bubbling b : bubblings){
            b.calc();
            rectF.set(b.left()*width , b.top()*width , b.right()*width , b.bottom()*width);
            canvas.drawBitmap(getBitmap(),null ,rectF,paint);
        }
    }

    private static Bitmap bitmap;

    private Bitmap getBitmap(){
        if (null == bitmap)bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_pp);
        return bitmap;
    }

    static class Bubbling{

        float x;
        float y;
        float r;
        float vx;
        float vy;

        float startX;
        float endX;

        float left(){
            return x-r;
        }
        float top(){
            return y-r;
        }
        float right(){
            return x+r;
        }
        float bottom(){
            return y+r;
        }

        public Bubbling(float x, float y, float r, float vx, float vy, float startX, float endX) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.vx = vx;
            this.vy = vy;
            this.startX = startX;
            this.endX = endX;
        }

        public void calc(){
            x += vx;
            y += vy;
            if (x < startX || x > endX)vx = -vx;
            if (y < 0f)y = 2f;
        }

    }

}
