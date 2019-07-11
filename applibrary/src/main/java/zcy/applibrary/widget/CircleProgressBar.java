package zcy.applibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import zcy.applibrary.R;

public class CircleProgressBar extends View{

    private int progressBgColor;
    private int progressColor;
    private float radius;
    private float progress;
    private Paint paint;
    private Path path;
    private RectF rectF;
    private float strokeWidth;

    public CircleProgressBar(Context context) {
        super(context);
        init(context,null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (null != context){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
            progressBgColor = ta.getColor(R.styleable.CircleProgressBar_cpb_progress_bar_bg_color, Color.GRAY);
            progressColor = ta.getColor(R.styleable.CircleProgressBar_cpb_progress_bar_color, Color.RED);
            radius = ta.getDimension(R.styleable.CircleProgressBar_cpb_progress_radius, getResources().getDimension(R.dimen.dp_50));
            strokeWidth = ta.getDimension(R.styleable.CircleProgressBar_cpb_progress_stroke_width, getResources().getDimension(R.dimen.dp_2));
            int progress = ta.getInteger(R.styleable.CircleProgressBar_cpb_progress, 0);
            this.progress = progress < 0f ? 0f : progress > 100f ? 1f: progress/100f;
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (radius > 0f ){
            setMeasuredDimension((int) radius * 2 , (int) radius * 2);
        }
    }

    public void setProgress(float progress){
        this.progress = progress < 0f ? 0f : progress > 1f ? 1f : progress;
        postInvalidate();
    }

    public void setProgress(long progress , long totalProgress){
        setProgress(0 == totalProgress ? 0f : (float) progress/(float) totalProgress);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == paint){
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(strokeWidth);
            path = new Path();
            rectF = new RectF(strokeWidth,strokeWidth,getWidth()-strokeWidth,getHeight()-strokeWidth);
        }

        paint.setColor(progressBgColor);
        canvas.drawCircle(radius , radius , radius - strokeWidth , paint);


        path.reset();
        path.addArc( rectF , -90 ,progress * 360);
        paint.setColor(progressColor);
        canvas.drawPath(path,paint);

    }
}
