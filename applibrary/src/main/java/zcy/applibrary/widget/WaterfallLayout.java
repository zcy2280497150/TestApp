package zcy.applibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import zcy.applibrary.R;

/**
 * Create 2019/4/23 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class WaterfallLayout extends ViewGroup {

    private float spacingH;
    private float spacingV;

    public WaterfallLayout(Context context) {
        super(context);
        init(context,null);
    }

    public WaterfallLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public WaterfallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context , AttributeSet attrs){
        if (null != context && null != attrs){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaterfallLayout);
            spacingH = ta.getDimension(R.styleable.WaterfallLayout_layout_spacing_h,0);
            spacingV = ta.getDimension(R.styleable.WaterfallLayout_layout_spacing_v,0);
            ta.recycle();
        }
    }

    private OnClickListener onChildViewClickListener;

    public void setOnChildViewClickListener(OnClickListener onChildViewClickListener) {
        this.onChildViewClickListener = onChildViewClickListener;
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
    }

    private OnClickListener childClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (null != onChildViewClickListener) onChildViewClickListener.onClick(v);
        }
    };

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int pl = Math.max(getPaddingStart(),getPaddingLeft());
        int pt = getPaddingTop();
        int pr = Math.max(getPaddingEnd() , getPaddingRight());
        int pb = getPaddingBottom();

        int count = getChildCount();
        int gl = pl;
        int gt = pt;
        int lineMaxHeight=0;

        int cWidth = 0;
        int cHeight = 0;
        for (int i = 0 ; i < count ; i++ ){
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();

            int cl =0 , ct = 0 , cr = 0 , cb = 0;

            if (0 != i && cWidth + gl > getWidth() - pr){
                gt += lineMaxHeight + spacingV;
                lineMaxHeight = 0;
                gl = pl;
            }

            cl = gl;
            ct = gt;
            cr = cl + cWidth;
            cb = ct + cHeight;

            childView.layout(cl,ct,cr,cb);
            childView.setOnClickListener(childClickListener);

            gl += cWidth + spacingH;
            lineMaxHeight = Math.max(cHeight,lineMaxHeight);

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

//        int width = 0;
//        int height = 0;
////        MarginLayoutParams cParams = null;
//
//        int cCount = getChildCount();
//        for (int i = 0;i<cCount;i++){
//            View childView = getChildAt(i);
//            width = childView.getMeasuredWidth();
//            height = childView.getMeasuredHeight();
////            cParams = (MarginLayoutParams) childView.getLayoutParams();
//
//
//        }

//        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
//                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
//                : height);
        setMeasuredDimension(sizeWidth,sizeHeight);



    }
}
