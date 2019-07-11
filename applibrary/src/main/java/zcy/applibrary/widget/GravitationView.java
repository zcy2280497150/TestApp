package zcy.applibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Create 2019/3/8 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class GravitationView extends View implements Runnable {

    private List<Po> pos;//点集
    private Paint p;//画笔
    private Random random;

    protected void init(Context context, AttributeSet attrs) {
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(10);
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

    public GravitationView(Context context) {
        super(context);
        init(context,null);
    }

    public GravitationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public GravitationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        //外层循环绘制每一个点
        for (int i = 0 ; i < pos.size() ; i++){
            Po po = pos.get(i);
            p.setStrokeWidth(10);//设置点的大小
            canvas.drawPoint(po.x * width , po.y* height ,p);//绘制点
            //内层循环会将该点与后面的点逐一比较，计算距离
            for (int j = i+ 1; j < pos.size() ; j++){
                Po poj = pos.get(j);
                float lenght = po.lenght(poj);
                //距离在0f 和 0.2f之间的点就会被连线（这个值可根据需求随意调整）
                if (lenght > 0f && lenght < 0.2f){
                    p.setStrokeWidth(5*(0.2f - lenght));//距离越近，线越粗
                    canvas.drawLine(po.x*width,po.y*height,poj.x*width,poj.y*height,p);//绘制线
                }
            }
        }
    }

    @Override
    public void run() {
        for (Po o : pos)o.run();
        postInvalidate();
    }

}
